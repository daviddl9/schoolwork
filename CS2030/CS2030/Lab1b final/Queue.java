package cs2030.simulator;

/**
 * This class represents the Queue that a server has.
 * Each queue has an array of customers and it's own number of Customers.
 *
 * @author David
 * @version CS2030 AY17/18 Sem2 Lab1b
 */
public class Queue {
  private Customer[] customers;
  private int numOfCustomers = 0;

  /**
   * This constructor takes in an array of customers to generate a queue.
   *
   * @param customers represents an array of customers.
   */
  Queue(Customer[] customers) {
    this.customers  = customers;
  }

  /**
   * This function adds a customer to the queue.
   *
   * @param cust represents the customer to be added to the queue.
   */
  public void addCustomer(Customer cust) {
    if (numOfCustomers < 2) {
      customers[numOfCustomers++] = cust;
    } else {
      System.out.println("This server has exceeded, customers: " +
          numOfCustomers);
      int i;
      for (i = 0; i < numOfCustomers; i++) {
        System.out.print(customers[i].getID() + ", ");
      } //for checking purposes.
    }
  }

  /**
   * This method represents a customer leaving from the queue, and simulates
   * the queue moving forward (i.e next customer gets served).
   */
  public void customerLeaves() {
    if (numOfCustomers == 1) {
      this.customers[0] = null;
    }
    else if (numOfCustomers == 2) {
      this.customers[0] = this.customers[1];
      this.customers[1] = null;
    }
    numOfCustomers--;
  }

  /**
   * Returns the arrival time of the first customer in the queue.
   *
   * @return arrival time of first customer in the queue.
   */
  public double getFirstCustomerArrivalTime() {
    return this.customers[0].getTimeStartedWaiting();
  }

  /**
   * This method returns the ID of the first customer in the queue of a server.
   *
   * @return ID of first customer in queue.
   */
  public int getFirstCustomerID() {
    return this.customers[0].getID();
  }

}
