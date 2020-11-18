#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <linux/unistd.h>
#include <sys/mman.h>

//variables for arguments
int num_visitors, num_guides, prob_visitor, delay_visitor, prob_guide, delay_guide, visitor_seed, guide_seed;

//global semaphores
long visitor_lock, guide_lock, museum_status, guide_leaves, mutex; 

//time struct
struct timeval t;

//variable to keep track of number of tickets alloted for day
int max_visitors;

//struct for shared memory
struct shared_memory {
  int visitor_count;
  int guide_count;
  int waiting_count;
} *shared_mem;

//wrapper function for up semaphore
void SIGNAL(long sem_id) { syscall(__NR_cs1550_up, sem_id); }

//wrapper function for down semaphore
void WAIT(long sem_id) { syscall(__NR_cs1550_down, sem_id); }

//wrapper function for create semaphore
long create(int value, char name[32], char key[32]) { return syscall(__NR_cs1550_create, value, name, key); } 

//calculates current time of event
int time_spent() {
	long total;
	struct timeval t2;
	gettimeofday(&t2, 0);
	total = t2.tv_sec-t.tv_sec;
	return (int)total;
}

void visitorLeaves(int v) {

	fprintf(stderr,"Visitor %d leaves the museum at time %d.\n", v, time_spent());

	//if all tickets have been claimed
	if(v >= max_visitors)
		return;

	//since visitor is leaving they are removed from inside the museum
	//if no more visitors left inside then guide leaves
	WAIT(mutex);
	  	(shared_mem->visitor_count) -= 1;
		(!shared_mem->visitor_count) ? (SIGNAL(guide_leaves)) : 0;
  	SIGNAL(mutex);
}

void tourguideLeaves(int g) {

	int visitor_count;
	WAIT(guide_leaves);

	//admit more visitors to museum
	WAIT(mutex);
		for(visitor_count = 0; visitor_count < shared_mem->visitor_count; visitor_count++) WAIT(museum_status);
	SIGNAL(mutex);
  
  	//once all visitors have been admitted then tour guide leaves
	SIGNAL(guide_lock);
	fprintf(stderr,"Tour guide %d leaves the museum at time %d.\n", g, time_spent());

	//removes guide from inside of museum
	//if there are no visitors waiting, guides inside, or visitors waiting then museum is empty
	WAIT(mutex);
		(shared_mem->guide_count) -= 1;
		(!shared_mem->visitor_count && !shared_mem->guide_count && !shared_mem->waiting_count) ? (fprintf(stderr,"The museum is now empty.\n")) : 0;
	SIGNAL(mutex);
}

void openMuseum(int g) {

	int visitor_count;
	fprintf(stderr,"Tour guide %d opens the museum for tours at time %d.\n", g, time_spent());

	//allows up to 10 visitors in per guide
	for(visitor_count = 0; visitor_count < 10; visitor_count++) SIGNAL(museum_status);

	//since guide has opened museum they are now inside the museum
	WAIT(mutex);
		(shared_mem->guide_count) += 1;
	SIGNAL(mutex);
}

void tourMuseum(int v) {

	//if all tickets have been claimed
	if(v >= max_visitors)
		return;

	fprintf(stderr,"Visitor %d tours the museum at time %d.\n", v, time_spent());

	//visitor has been picked up by a guide so they are no longer waiting and are now inside the museum
	WAIT(mutex);
		(shared_mem->visitor_count) += 1;
	  	(shared_mem->waiting_count) -= 1;
  	SIGNAL(mutex);

  	//each visitor stays in museum for 2 seconds
	sleep(2);
}

void visitorArrives(int v) {

	//if all tickets have been claimed
	if(v >= max_visitors) {
		fprintf(stderr,"Visitor %d arrives at time %d.\n", v, time_spent());
		return;
	}

	//signal to tourguide that visitor has arrived 
	//visitor must wait for museum to open
	SIGNAL(visitor_lock);
	fprintf(stderr,"Visitor %d arrives at time %d.\n", v, time_spent());
	WAIT(museum_status);

	//visitor arrives so they are counted as waiting until assisted by guide
	WAIT(mutex);
		(shared_mem->waiting_count) += 1;
	SIGNAL(mutex);
}

void tourguideArrives(int g) {

	fprintf(stderr,"Tour guide %d arrives at time %d.\n", g, time_spent());

	//when a guide arrives they must wait for current guide to finish up and must wait for a visitor to be waiting outside
	WAIT(guide_lock);
	WAIT(visitor_lock);
}

//helper function for guide process
void guide(int g) {

	tourguideArrives(g);
	openMuseum(g);
	tourguideLeaves(g);
}

//helper function for visitor process
void visitor(int v) {

	visitorArrives(v);
	tourMuseum(v);
	visitorLeaves(v);
}

int main(int argc, char **argv) {

	int i, value;

	//starts global program timer
	gettimeofday(&t, 0);

	//parses inputs from command line, all commands must be > 0
	num_visitors = atoi(argv[2]);
	if(num_visitors<0)
		exit(1);

	num_guides = atoi(argv[4]);
	if(num_guides < 0)
		exit(1);

	prob_visitor = atoi(argv[6]);
	if(prob_visitor < 0)
		exit(1);

	delay_visitor = atoi(argv[8]);
	if(delay_visitor < 0)
		exit(1);

	visitor_seed = atoi(argv[10]);
	if(visitor_seed < 0)
		exit(1);


	prob_guide = atoi(argv[12]);
	if(prob_guide < 0)
		exit(1);

	delay_guide = atoi(argv[14]);
	if(delay_guide < 0)
		exit(1);

	guide_seed = atoi(argv[16]);
	if(guide_seed < 0)
		exit(1);

	//initializes shared memory pointer
	//shared memory along with mutex prevents deadlocks and race conditions
	shared_mem = mmap(NULL, sizeof(shared_mem), PROT_READ|PROT_WRITE, MAP_SHARED|MAP_ANONYMOUS, 0, 0);
	shared_mem->visitor_count = 0;
	shared_mem->guide_count = 0;
	shared_mem->waiting_count = 0;

	//max number of tickets 
	max_visitors = num_guides * 10;

	//initializes semaphore values 
	//mmap not needed for these values since the space is allocated in the kernel
  	visitor_lock = create(0, "visitor_lock", "1234234");
  	guide_lock = create(1, "guide_lock", "123423214");
  	guide_leaves = create(0, "guide_leaves", "11223344");
  	mutex = create(1, "mutex", "1264");
  	museum_status = create(0, "museum_status", "12342312344");
  	
  	//since forking has not begun, museum is empty
	fprintf(stderr,"The museum is now empty.\n");

	if(fork() == 0) {
		
		srand((unsigned int) visitor_seed);
		for(i = 0; i < num_visitors; i++) {
			if(fork() == 0) {
				visitor(i);
				exit(0);
			} else {
				value = rand() % 100 + 1;
				if(value < (100-prob_visitor))
					sleep(delay_visitor);
			}
		}
		for (i=0; i < num_visitors; i++) wait(NULL);
	} else {
	
		srand((unsigned int) guide_seed);
		for(i = 0; i < num_guides; i++) {
			if(fork() == 0) {
				guide(i);
				exit(0);
			} else {
				value = rand() % 100 + 1;
				if (value < (100-prob_guide))
					sleep(delay_guide);
			}
		}
		for(i = 0; i < num_guides; i++) wait(NULL);
	}

	wait(NULL);
	return 0;
}