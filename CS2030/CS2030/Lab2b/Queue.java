//import java.util.LinkedList;
//import java.util.List;
//
///**
//* The Queue class encapsulated information pertaining to the
//* customers in a queue in a shop.
//*/
//class Queue {
//	private List<Customer> list;
//	private int maxQlength;
//	private int length;
//
//	/**
//	* This Queue constructor creates a queue of customers.
//	*/
//	Queue(int maxQlength) {
//		this.list = new LinkedList<>();
//		this.maxQlength = maxQlength;
//		this.length = 0;
//	}
//
//  /**
//   * Adds a customer to the given queue.
//   *
//   * @param c Customer to be added into the queue.
//   */
//	public void enqueue(Customer c) {
//    list.add(c);
//    this.length++;
//  }
//
//  /**
//   * Checks if a given queue is full.
//   *
//   * @return true if queue is full, false otherwise.
//   */
//  public boolean isFull() {
//    return this.length == maxQlength;
//  }
//
//  /**
//   * Returns the first customer in the queue.
//   *
//   * @return first customer in the queue.
//   */
//  public Customer getFirstCustomer() {
//    if (this.length >= 1) {
//      Customer c = list.remove(0);
//      this.length--;
//      return c;
//    } else {
//      return null;
//    }
//  }
//
//  /**
//   * Checks if a given queue is empty.
//   *
//   * @return true if empty, false otherwise.
//   */
//  public boolean isEmpty() {
//    return this.length == 0;
//  }
//
//  public int getLength() {
//    return this.length;
//  }
//
//  @Override
//  public String toString() {
//    return this.list.toString();
//  }
//}