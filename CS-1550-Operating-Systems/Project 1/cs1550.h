#include <linux/smp_lock.h>

//linked list FIFO queue implementation to keep track of processes
struct semaphore_node {
	struct task_struct* data;
	struct semaphore_node* next_node;
};

struct cs1550_sem {
	int value;
	long sem_id;
	spinlock_t lock;
	char key[32];
	char name[32];
	struct cs1550_sem* next_semaphore;
	struct semaphore_node* semaphore_queue;
};