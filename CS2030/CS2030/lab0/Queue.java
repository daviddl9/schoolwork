import java.lang.IllegalStateException;

public class Queue<T> {
  private T[] array;
  private int numOfElements = 0;
  private int head;
  private int tail;
  private int size;

  public void enqueue(T o) {
    array[numOfElements] = o;
    this.numOfElements++;
    this.tail = numOfElements - 1;
    this.size = size;
  }
  
//  public T dequeue() {
//    if (numOfElements == 0) {
//         throw new IllegalStateException();
//      }
//    else {
//      int i = 0;
//      T temp = array[0];
//      for (i = 0; i < numOfElements - 1; i++) {
//         array[i] = array[i+1];
//      }
//      array[numOfElements-1] = null;
//      numOfElements--;
//    }
//    return temp;
//
//  }
   
  @SuppressWarnings("unchecked")
  public Queue(int size) {
    this.numOfElements = 0;
    this.array = (T[]) new Object[size];
    this.head = 0;
    this.tail = 0;
  }

  public boolean isEmpty() {
    return (this.numOfElements == 0);
  }

  public boolean isFull() {
   return numOfElements == size;
  }

  @Override
  public String toString() {
    return "Number of elements: " + numOfElements;
  }
}
