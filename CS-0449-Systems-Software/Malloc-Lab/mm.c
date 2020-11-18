/*-------------------------------------------------------------------
 *  Malloc Lab Starter code:
 *        single doubly-linked free block list with LIFO policy
 *        with support for coalescing adjacent free blocks
 *
 * Terminology:
 * o We will implement an explicit free list allocator.
 * o We use "next" and "previous" to refer to blocks as ordered in
 *   the free list.
 * o We use "following" and "preceding" to refer to adjacent blocks
 *   in memory.
 *-------------------------------------------------------------------- */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <unistd.h>

#include "memlib.h"
#include "mm.h"

/* Macros for unscaled pointer arithmetic to keep other code cleaner.
   Casting to a char* has the effect that pointer arithmetic happens at
   the byte granularity (i.e. POINTER_ADD(0x1, 1) would be 0x2).  (By
   default, incrementing a pointer in C has the effect of incrementing
   it by the size of the type to which it points (e.g. BlockInfo).)
   We cast the result to void* to force you to cast back to the
   appropriate type and ensure you don't accidentally use the resulting
   pointer as a char* implicitly.
*/
#define UNSCALED_POINTER_ADD(p, x) ((void*)((char*)(p) + (x)))
#define UNSCALED_POINTER_SUB(p, x) ((void*)((char*)(p) - (x)))


/******** FREE LIST IMPLEMENTATION ***********************************/


/* A BlockInfo contains information about a block, including the size
   and usage tags, as well as pointers to the next and previous blocks
   in the free list.  This is exactly the "explicit free list" structure
   illustrated in the lecture slides.

   Note that the next and prev pointers and the boundary tag are only
   needed when the block is free.  To achieve better utilization, mm_malloc
   should use the space for next and prev as part of the space it returns.

   +--------------+
   | sizeAndTags  |  <-  BlockInfo pointers in free list point here
   |  (header)    |
   +--------------+
   |     next     |  <-  Pointers returned by mm_malloc point here
   +--------------+
   |     prev     |
   +--------------+
   |  space and   |
   |   padding    |
   |     ...      |
   |     ...      |
   +--------------+
   | boundary tag |
   |  (footer)    |
   +--------------+
*/
struct BlockInfo {
  // Size of the block (in the high bits) and tags for whether the
  // block and its predecessor in memory are in use.  See the SIZE()
  // and TAG macros, below, for more details.
  size_t sizeAndTags;
  // Pointer to the next block in the free list.
  struct BlockInfo* next;
  // Pointer to the previous block in the free list.
  struct BlockInfo* prev;
};
typedef struct BlockInfo BlockInfo;


/* Pointer to the first BlockInfo in the free list, the list's head.

   A pointer to the head of the free list in this implementation is
   always stored in the first word in the heap.  mem_heap_lo() returns
   a pointer to the first word in the heap, so we cast the result of
   mem_heap_lo() to a BlockInfo** (a pointer to a pointer to
   BlockInfo) and dereference this to get a pointer to the first
   BlockInfo in the free list. */
#define FREE_LIST_HEAD *((BlockInfo **)mem_heap_lo())

/* Size of a word on this architecture. */
#define WORD_SIZE sizeof(void*)

/* Minimum block size (to account for size header, next ptr, prev ptr,
   and boundary tag) */
#define MIN_BLOCK_SIZE (sizeof(BlockInfo) + WORD_SIZE)

/* Alignment of blocks returned by mm_malloc. */
#define ALIGNMENT 8

/* SIZE(blockInfo->sizeAndTags) extracts the size of a 'sizeAndTags' field.
   Also, calling SIZE(size) selects just the higher bits of 'size' to ensure
   that 'size' is properly aligned.  We align 'size' so we can use the low
   bits of the sizeAndTags field to tag a block as free/used, etc, like this:

      sizeAndTags:
      +-------------------------------------------+
      | 63 | 62 | 61 | 60 |  . . . .  | 2 | 1 | 0 |
      +-------------------------------------------+
        ^                                       ^
      high bit                               low bit

   Since ALIGNMENT == 8, we reserve the low 3 bits of sizeAndTags for tag
   bits, and we use bits 3-63 to store the size.

   Bit 0 (2^0 == 1): TAG_USED
   Bit 1 (2^1 == 2): TAG_PRECEDING_USED
*/
#define SIZE(x) ((x) & ~(ALIGNMENT - 1))

/* TAG_USED is the bit mask used in sizeAndTags to mark a block as used. */
#define TAG_USED 1

/* TAG_PRECEDING_USED is the bit mask used in sizeAndTags to indicate
   that the block preceding it in memory is used. (used in turn for
   coalescing).  If the previous block is not used, we can learn the size
   of the previous block from its boundary tag */
#define TAG_PRECEDING_USED 2

/* Print the heap by iterating through it as an implicit free list. */
static void examine_heap() {
  BlockInfo *block;

  /* print to stderr so output isn't buffered and not output if we crash */
  fprintf(stderr, "FREE_LIST_HEAD: %p\n", (void *)FREE_LIST_HEAD);

  for (block = (BlockInfo *)UNSCALED_POINTER_ADD(mem_heap_lo(), WORD_SIZE); /* first block on heap */
      SIZE(block->sizeAndTags) != 0 && block < (BlockInfo *)mem_heap_hi();
      block = (BlockInfo *)UNSCALED_POINTER_ADD(block, SIZE(block->sizeAndTags))) {

    /* print out common block attributes */
    fprintf(stderr, "%p: %ld %ld %ld\t",
    (void *)block,
    SIZE(block->sizeAndTags),
    block->sizeAndTags & TAG_PRECEDING_USED,
    block->sizeAndTags & TAG_USED);

    /* and allocated/free specific data */
    if (block->sizeAndTags & TAG_USED) {
      fprintf(stderr, "ALLOCATED\n");
    } else {
      fprintf(stderr, "FREE\tnext: %p, prev: %p\n",
      (void *)block->next,
      (void *)block->prev);
    }
  }
  fprintf(stderr, "END OF HEAP\n\n");
}

/* Find a free block of at least the requested size in the free list.  Returns
   NULL if no free block is large enough. */
static BlockInfo* searchFreeList(size_t req_size) {
  BlockInfo* free_block = FREE_LIST_HEAD;
  size_t curr_block_size;

  //Checks if no free block is avaliable 
  if (free_block)
    do {

      //if current block can fit requested size, then return that block
      curr_block_size = SIZE((*free_block).sizeAndTags);
      if (req_size <= curr_block_size) return free_block;
      
      //otherwise move onto the next block until there is not another one available
      free_block = (*free_block).next;
    
    } while(free_block); 

  return NULL;
}

/* Insert freeBlock at the head of the list.  (LIFO) */
static void insertFreeBlock(BlockInfo* free_block) {
  BlockInfo* old_head = FREE_LIST_HEAD;

  (*free_block).next = old_head;
  (old_head) ? ((*old_head).prev = free_block) : 0;
  FREE_LIST_HEAD = free_block;
}

/* Remove a free block from the free list. */
static void removeFreeBlock(BlockInfo* free_block) {
  BlockInfo *next_free, *prev_free;

  next_free = (*free_block).next;
  prev_free = (*free_block).prev;


  (!next_free) ? (0) : ((*next_free).prev = prev_free);

  (free_block == FREE_LIST_HEAD) ? (FREE_LIST_HEAD = next_free) : ((*prev_free).next = next_free);
}

/* Coalesce 'oldBlock' with any preceeding or following free blocks. */
static void coalesceFreeBlock(BlockInfo* oldBlock) {
  BlockInfo *blockCursor;
  BlockInfo *newBlock;
  BlockInfo *freeBlock;
  // size of old block
  size_t oldSize = SIZE(oldBlock->sizeAndTags);
  // running sum to be size of final coalesced block
  size_t newSize = oldSize;

  // Coalesce with any preceding free block
  blockCursor = oldBlock;
  while ((blockCursor->sizeAndTags & TAG_PRECEDING_USED) == 0) {
    // While the block preceding this one in memory (not the
    // prev. block in the free list) is free:

    // Get the size of the previous block from its boundary tag.
    size_t size = SIZE(*((size_t*)UNSCALED_POINTER_SUB(blockCursor, WORD_SIZE)));
    // Use this size to find the block info for that block.
    freeBlock = (BlockInfo*)UNSCALED_POINTER_SUB(blockCursor, size);
    // Remove that block from free list.
    removeFreeBlock(freeBlock);

    // Count that block's size and update the current block pointer.
    newSize += size;
    blockCursor = freeBlock;
  }
  newBlock = blockCursor;

  // Coalesce with any following free block.
  // Start with the block following this one in memory
  blockCursor = (BlockInfo*)UNSCALED_POINTER_ADD(oldBlock, oldSize);
  while ((blockCursor->sizeAndTags & TAG_USED) == 0) {
    // While the block is free:

    size_t size = SIZE(blockCursor->sizeAndTags);
    // Remove it from the free list.
    removeFreeBlock(blockCursor);
    // Count its size and step to the following block.
    newSize += size;
    blockCursor = (BlockInfo*)UNSCALED_POINTER_ADD(blockCursor, size);
  }

  // If the block actually grew, remove the old entry from the free
  // list and add the new entry.
  if (newSize != oldSize) {
    // Remove the original block from the free list
    removeFreeBlock(oldBlock);

    // Save the new size in the block info and in the boundary tag
    // and tag it to show the preceding block is used (otherwise, it
    // would have become part of this one!).
    newBlock->sizeAndTags = newSize | TAG_PRECEDING_USED;
    // The boundary tag of the preceding block is the word immediately
    // preceding block in memory where we left off advancing blockCursor.
    *(size_t*)UNSCALED_POINTER_SUB(blockCursor, WORD_SIZE) = newSize | TAG_PRECEDING_USED;

    // Put the new block in the free list.
    insertFreeBlock(newBlock);
  }
  return;
}

/* Get more heap space of size at least reqSize. */
static void requestMoreSpace(size_t reqSize) {
  size_t pagesize = mem_pagesize();
  size_t numPages = (reqSize + pagesize - 1) / pagesize;
  BlockInfo *newBlock;
  size_t totalSize = numPages * pagesize;
  size_t prevLastWordMask;

  void* mem_sbrk_result = mem_sbrk(totalSize);
  if ((size_t)mem_sbrk_result == -1) {
    printf("ERROR: mem_sbrk failed in requestMoreSpace\n");
    exit(0);
  }
  newBlock = (BlockInfo*)UNSCALED_POINTER_SUB(mem_sbrk_result, WORD_SIZE);

  /* initialize header, inherit TAG_PRECEDING_USED status from the
     previously useless last word however, reset the fake TAG_USED
     bit */
  prevLastWordMask = newBlock->sizeAndTags & TAG_PRECEDING_USED;
  newBlock->sizeAndTags = totalSize | prevLastWordMask;
  // Initialize boundary tag.
  ((BlockInfo*)UNSCALED_POINTER_ADD(newBlock, totalSize - WORD_SIZE))->sizeAndTags =
    totalSize | prevLastWordMask;

  /* initialize "new" useless last word
     the previous block is free at this moment
     but this word is useless, so its use bit is set
     This trick lets us do the "normal" check even at the end of
     the heap and avoid a special check to see if the following
     block is the end of the heap... */
  *((size_t*)UNSCALED_POINTER_ADD(newBlock, totalSize)) = TAG_USED;

  // Add the new block to the free list and immediately coalesce newly
  // allocated memory space
  insertFreeBlock(newBlock);
  coalesceFreeBlock(newBlock);
}


/* Initialize the allocator. */
int mm_init() {
  // Head of the free list.
  BlockInfo *firstFreeBlock;

  // Initial heap size: WORD_SIZE byte heap-header (stores pointer to head
  // of free list), MIN_BLOCK_SIZE bytes of space, WORD_SIZE byte heap-footer.
  size_t initSize = WORD_SIZE+MIN_BLOCK_SIZE+WORD_SIZE;
  size_t totalSize;

  void* mem_sbrk_result = mem_sbrk(initSize);
  //  printf("mem_sbrk returned %p\n", mem_sbrk_result);
  if ((ssize_t)mem_sbrk_result == -1) {
    printf("ERROR: mem_sbrk failed in mm_init, returning %p\n",
           mem_sbrk_result);
    exit(1);
  }

  firstFreeBlock = (BlockInfo*)UNSCALED_POINTER_ADD(mem_heap_lo(), WORD_SIZE);

  // Total usable size is full size minus heap-header and heap-footer words
  // NOTE: These are different than the "header" and "footer" of a block!
  // The heap-header is a pointer to the first free block in the free list.
  // The heap-footer is used to keep the data structures consistent (see
  // requestMoreSpace() for more info, but you should be able to ignore it).
  totalSize = initSize - WORD_SIZE - WORD_SIZE;

  // The heap starts with one free block, which we initialize now.
  firstFreeBlock->sizeAndTags = totalSize | TAG_PRECEDING_USED;
  firstFreeBlock->next = NULL;
  firstFreeBlock->prev = NULL;
  // boundary tag
  *((size_t*)UNSCALED_POINTER_ADD(firstFreeBlock, totalSize - WORD_SIZE)) = totalSize | TAG_PRECEDING_USED;

  // Tag "useless" word at end of heap as used.
  // This is the is the heap-footer.
  *((size_t*)UNSCALED_POINTER_SUB(mem_heap_hi(), WORD_SIZE - 1)) = TAG_USED;

  // set the head of the free list to this new free block.
  FREE_LIST_HEAD = firstFreeBlock;
  return 0;
}


// TOP-LEVEL ALLOCATOR INTERFACE ------------------------------------

/* Allocate a block of size size and return a pointer to it. If size is zero,
 * returns null.
 */
void* mm_malloc(size_t size) {
  size_t req_size;
  BlockInfo* ptr_free_block = NULL;
  size_t block_size;
  size_t preceding_block_use_tag;

  if (!size)
    return NULL;
  
  size += WORD_SIZE;

  if (size <= MIN_BLOCK_SIZE)
    req_size = MIN_BLOCK_SIZE;
  else 
    req_size = ALIGNMENT * ((size + ALIGNMENT - 1) / ALIGNMENT);
  
  //Look for a fit in list
  ptr_free_block = searchFreeList(req_size);

  //If there is no fit in the heap, more is requested
  (!ptr_free_block) ? (requestMoreSpace(req_size), ptr_free_block = searchFreeList(req_size), preceding_block_use_tag = TAG_PRECEDING_USED) :
   (preceding_block_use_tag = (*ptr_free_block).sizeAndTags & TAG_PRECEDING_USED);

   //Calculates size of block
  block_size = SIZE((*ptr_free_block).sizeAndTags);

  // Remove the block from the free list
  removeFreeBlock(ptr_free_block);

  int split_block;
  (MIN_BLOCK_SIZE > (block_size - req_size)) ? (split_block = 0) : (split_block = 1);

  // Split block
  if (!split_block) {  

    //Creates new block and sets previous used tag
    BlockInfo* new_block = (BlockInfo*) UNSCALED_POINTER_ADD(ptr_free_block, block_size);
    (*new_block).sizeAndTags |= TAG_PRECEDING_USED;

  }else {

    //Sets following free block's next used tag
    BlockInfo* block = (BlockInfo*) UNSCALED_POINTER_ADD(ptr_free_block, req_size);
    (*block).sizeAndTags = (block_size - req_size) | TAG_PRECEDING_USED;

    //Sets following free block's next boundary tag
    BlockInfo* block_boundary = (BlockInfo*) UNSCALED_POINTER_ADD(ptr_free_block, block_size - WORD_SIZE);
    (*block_boundary).sizeAndTags = (block_size - req_size) | TAG_PRECEDING_USED;
  
    // Set the block's size and tags
    (*ptr_free_block).sizeAndTags = req_size | preceding_block_use_tag;
     
    //Inserts block in free list
    insertFreeBlock(block);
  }

  //Set used tag
  (*ptr_free_block).sizeAndTags |= TAG_USED; 

  //if free block does not exist return null, otherwise retrun block(no header)
  if (!ptr_free_block) return NULL;
  return UNSCALED_POINTER_ADD(ptr_free_block, WORD_SIZE);
}


/* Free the block referenced by ptr. */
void mm_free(void *ptr) {
  size_t payload_size;
  BlockInfo * block_info;

  //Header of block
  block_info = (BlockInfo*) UNSCALED_POINTER_SUB(ptr, WORD_SIZE);

  //Block size
  payload_size = SIZE((*block_info).sizeAndTags) - WORD_SIZE;
 
  //Tag is marked as used
  (*block_info).sizeAndTags &= ~TAG_USED;
  
  //Inserts block and coalesces 
  insertFreeBlock(block_info);
  coalesceFreeBlock(block_info);

  //Copies block header to footer
  BlockInfo* block_footer = UNSCALED_POINTER_ADD(block_info, payload_size);
  (*block_footer).sizeAndTags = (*block_info).sizeAndTags;
}


// Implement a heap consistency checker as needed.
int mm_check() { return 0; }
