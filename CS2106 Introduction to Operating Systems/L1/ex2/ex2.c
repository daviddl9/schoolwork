/*************************************
* Lab 1 Exercise 2
* Name: D David Livingston  
* Matric No: A0168218Y
* Lab Group: 4
*************************************/

#include <stdio.h>
#include <stdlib.h> //for malloc() and free()

//Declaration of a Linked List Node

typedef struct NODE{
    int data;
    struct NODE* next;
} node;

//Function Prototypes
node* insertAt(node*, int, int, int);
node* addToHead(node* head, int newData);

void printList(node*);
void destroyList(node*, int*);



int main()
{
    node* myList = NULL;    //Empty List
    int position;
    int copies = 0;
    int num = 0;
    int size = 0;
    //Fill in code for input and processing
    while (scanf("%i %i %i", &position, &num, &copies) == 3) {
        if (position > size) position = size;
        myList = insertAt(myList, position, copies, num);
        size += copies;
    }
    
    //Output code coded for you
    printf("My List:\n");
    printList(myList);
    
    destroyList(myList, &size);
    myList = NULL;

    
    printf("My List After Destroy:\n");
    printList(myList);
 
    return 0;
}

//Actual Function Implementations
node* insertAt(node* head, int position, int copies, int newValue)
{
    int index = 1;
    node* current = head;
    node* next;

    if (position == 0) {
        while (copies > 0) {
            head = addToHead(head, newValue);
            copies--;
        }
        return head;
    }
    //set current to the node before the position
    while (current != NULL && index < position) {
        current = current->next;
        index++;
    }


    next = current->next;
    while (copies > 0) {
        next = addToHead(next, newValue);
        copies--;
    }
    current->next = next;
    //Fill in your code here
    return head;    //change this!
}

// Adds the new data to the head of the linked list, and returns the head of the linked list.
node* addToHead(node* head, int newData)
{

    node* new_node;
    new_node = (node*) malloc(sizeof(node));

    new_node->data = newData;
    new_node->next = head;

    return new_node;
}
 
void printList(node* head)
//Purpose: Print out the linked list content
//Assumption: the list is properly null terminated
{
    //This function has been implemented for you
    node* ptr = head;

    while (ptr != NULL)  {    //or you can write while(ptr)
        printf("%i ", ptr->data);
        ptr = ptr->next;
    }
    printf("\n");
}

void destroyList(node* head, int* size)
{
    *size = 0;
    node* current = head;
    node* next;
    while (current != NULL) {
        next = current->next;
        free(current);
        current = next;
    }
    head = NULL;
}
