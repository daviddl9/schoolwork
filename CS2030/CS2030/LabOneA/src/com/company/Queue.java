package com.company;

/**
 * This class represents the Queue that a server has.
 * Each queue has an array of customers and it's own number of Customers.
 */
public class Queue {
  private Customer[] customers;
  static int numOfCustomers = 0;
  static double totalWaitingTime = 0.0;

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
   * @param c represents the customer to be added to the queue.
   */
  public void addCustomer(Customer c) {
    if (numOfCustomers < 2) {
      customers[numOfCustomers++] = c;
    } else {
      c.customerLeaves();
    }
  }

  /**
   * This method represents a customer leaving from the queue, and simulates
   * the queue moving forward (i.e next customer gets served).
   */
  public void customerLeaves() {
    this.numOfCustomers--;
    customers[0] = customers[1];
  }

  /**
   * This function simulates a customer getting served in the queue.
   *
   * @param time refers to the time at which first member in the queue gets served.
   */
  public void serveCust(double time) {
    this.customers[0].getServed(time);
    totalWaitingTime += time - this.customers[0].getTimeStartedWaiting();
  }

  /**
   * This method returns the total waiting time in a given queue.
   *
   * @return total waiting time of all customers that have been in the queue.
   */
  public double getTotalWaitingTime() {
    return totalWaitingTime;
  }

  /**
   * Returns the arrival time of the first customer in the queue.
   *
   * @return arrival time of first customer in the queue.
   */
  public double getFirstCustomerArrivalTime() {
    return this.customers[0].getTimeStartedWaiting();
  }

}
