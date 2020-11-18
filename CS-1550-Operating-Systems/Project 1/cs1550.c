#include <linux/cs1550.h>

DEFINE_SPINLOCK(lock);

struct cs1550_sem* semaphore_list_tail;
struct cs1550_sem* semaphore_list_head;
static int i = 0;

//adds semaphore to global list of semaphores
asmlinkage long sys_cs1550_create(int value, char name[32], char key[32]) {
	struct cs1550_sem* semaphore;

	//locks global semaphore
	spin_lock(&lock);

	semaphore = kmalloc(sizeof(struct cs1550_sem), GFP_KERNEL);

	//no space avaliable
	if(!semaphore) {
		spin_unlock(&lock);
		return -1;
	}

	//initialzes new semaphore
	semaphore->value = value;
	strcpy(semaphore->name, name);
	strcpy(semaphore->key, key);
	semaphore->sem_id = i;
	spin_lock_init(&(semaphore->lock));
	semaphore->semaphore_queue = NULL;
	i+=1;

	//checks if there is currently a list head
	if(semaphore_list_head) {
		semaphore_list_tail->next_semaphore = semaphore;
		semaphore_list_tail = semaphore;
		semaphore_list_tail->next_semaphore = NULL;
	}
	else {
		semaphore_list_head = semaphore_list_tail = semaphore;
		semaphore_list_head->next_semaphore = semaphore_list_tail->next_semaphore = NULL;
	}
	
	
	spin_unlock(&lock);
	return semaphore->sem_id;
}

//if name and key matches then the ID is returned, otherwise -1 is returned
asmlinkage long sys_cs1550_open(char name[32], char key[32]) {
	struct cs1550_sem* temp_head;

	spin_lock(&lock);
	temp_head = semaphore_list_head;

	//match
	if ((strcmp(temp_head->key, key) == 0) && (strcmp(temp_head->name, name)==0)) {
		spin_unlock(&lock);
		return temp_head->sem_id;
	}

	//key or name doesnt match
	while ((strcmp(temp_head->key, key) != 0) && (strcmp(temp_head->name, name)) != 0) {
		temp_head = temp_head->next_semaphore;
		if(!temp_head) {
			spin_unlock(&lock);
			return -1;
		}
	}

	spin_unlock(&lock);
	return temp_head->sem_id;
}

//iterates through semaphore list and searches for sem_id
struct cs1550_sem* get_semaphore (long sem_id) {
	struct cs1550_sem* temp_head = semaphore_list_head;

	while(temp_head != NULL){
		if (temp_head->sem_id == sem_id)
			return temp_head;
		temp_head = temp_head->next_semaphore;
	}
	return NULL;
}

asmlinkage long sys_cs1550_down(long sem_id) {
	struct cs1550_sem* curr_semaphore;
	struct semaphore_node* temp_last;
	struct semaphore_node* new_node;

	//locks global spinlock
	spin_lock(&lock);

	//gets current semaphore based on given ID
	curr_semaphore = get_semaphore(sem_id);

	//checks for invalid ID
	if(!curr_semaphore) {
		spin_unlock(&lock);
		return -1;
	}

	//unlocks global spinlock
	spin_unlock(&lock);

	//locks current semaphore
	spin_lock(&(curr_semaphore->lock));

	//decrements value
	(curr_semaphore->value) -= 1;

	if(curr_semaphore->value < 0) {

		//creates new node and adds it to the back of the queue 
		new_node = kmalloc(sizeof(struct semaphore_node), GFP_KERNEL);

		//if no memory
		if(!new_node) {
			spin_unlock(&(curr_semaphore->lock));
			return -1;
		}

		new_node->data = current;
		new_node->next_node = NULL;

		//adds new node to tail of queue
		if(curr_semaphore->semaphore_queue == NULL)
			curr_semaphore->semaphore_queue = new_node;
		else{
			temp_last = curr_semaphore->semaphore_queue;
			while(temp_last->next_node != NULL)
				temp_last = temp_last->next_node;
			temp_last->next_node = new_node;
		}

		//puts task to sleep
		spin_unlock(&(curr_semaphore->lock));
		set_current_state(TASK_INTERRUPTIBLE);
		schedule();
		return 0;
	}
	spin_unlock(&(curr_semaphore->lock));
	return 0;
}

asmlinkage long sys_cs1550_up(long sem_id) {
	struct cs1550_sem* curr_semaphore;
	struct task_struct* temp;
	struct semaphore_node* temp_node;

	//locks global spinlock
	spin_lock(&lock);

	//gets current semaphore based on given ID
	curr_semaphore = get_semaphore(sem_id);

	//checks for invalid ID
	if(!curr_semaphore) {
		spin_unlock(&lock);
		return -1;
	}

	//unlocks global spinlock
	spin_unlock(&lock);

	//locks current semaphore
	spin_lock(&(curr_semaphore->lock));

	//increments value
	(curr_semaphore->value) += 1;

	if(curr_semaphore->value <= 0) {

		//empty queue
		if (!(curr_semaphore->semaphore_queue)) {
			spin_unlock(&(curr_semaphore->lock));
			return -1;
		}

		//gets node from front of queue
		temp = curr_semaphore->semaphore_queue->data;
		temp_node = curr_semaphore->semaphore_queue;
		curr_semaphore->semaphore_queue = curr_semaphore->semaphore_queue->next_node;

		//wakes up process and frees memory
		wake_up_process(temp);
		kfree(temp_node);
	}
	spin_unlock(&(curr_semaphore->lock));
	return 0;
}

struct cs1550_sem* free_semaphore(struct cs1550_sem* curr, long sem_id) {
	struct cs1550_sem* next;

	//end of list
	if (!curr) 
		return NULL;
	else if (curr->sem_id == sem_id) { //match
		next = curr->next_semaphore;
		kfree(curr);
		return next;
	} else {
		curr->next_semaphore = free_semaphore(curr->next_semaphore, sem_id);
		return curr;
	}
}

//removing semaphore from list
asmlinkage long sys_cs1550_close(long sem_id) {
	struct cs1550_sem* curr_semaphore;

	spin_lock(&lock);

	curr_semaphore = get_semaphore(sem_id);

	//invalid ID
	if(!curr_semaphore) {
		spin_unlock(&lock);
		return -1;
	}

	//semaphore process queue is not empty
	if(curr_semaphore->semaphore_queue) {
		spin_unlock(&lock);
		return -1;
	}

	//frees semaphore with matching ID
	semaphore_list_head = free_semaphore(semaphore_list_head, curr_semaphore->sem_id);
	kfree(curr_semaphore->semaphore_queue);
	spin_unlock(&lock);
	return 0;
}