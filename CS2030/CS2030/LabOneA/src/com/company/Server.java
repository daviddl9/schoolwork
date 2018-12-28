package com.company;

/**
 * This class represents a server in the system.
 *
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
public class Server {
  private Queue queue = new Queue(new Customer[2]);
  private boolean isIdle;
  public static final double serviceTime = 1.0;
  private static int totalNumOfServedCustomers = 0;
  private boolean hasQueue = false;

  /**
   * This constructor takes in the current state of a server and creates a new
   * server.
   *
   * @param isIdle represents the current state of the server. True if the
   *               server is idle, and false if the server is not idle.
   */
  Server(boolean isIdle) {
    this.isIdle = isIdle;
  }

  /**
   * This method checks the state of the server.
   *
   * @return true if the server is idle, false otherwise.
   */
  public boolean isIdle() {
    return isIdle;
  }

  /**
   * This method returns the service time of a server.
   *
   * @return service time of the server.
   */
  public double getServiceTime() {
    return serviceTime;
  }

  /**
   * This method makes a given customer wait, at a given timing.
   *
   * @param cust represents the customer that has to wait.
   * @param time time at which customer starts waiting.
   */
  public void makeCustomerWait(Customer cust, double time) {
    cust.startWaiting(time);
  }

  /**
   * Returns the total number of customers served by the waiter.
   *
   * @return total number of customers served by the waiter.
   */
  public int getTotalNumOfServedCustomers() {
    return totalNumOfServedCustomers;
  }

  /**
   * Checks if a waiter currently has a queue (i.e waiter is currently
   * serving a customer and there are customer(s) waiting in line)
   *
   * @return true if waiter has a queue, false otherwise.
   */
  public boolean hasQueue() {
    return hasQueue;
  }

  /**
   * Adds a given customer to the queue of the server.
   *
   * @param c represents the customer to be added into the queue of the waiter.
   */
  public void incrementCustomers(Customer c) {
    this.queue.addCustomer(c);
  }

  /**
   * This method sets the queue status of the waiter to be true. (i.e
   * indicates that the server has a queue)
   */
  public void setHasQueue() {
    this.hasQueue = true;
  }

  /**
   * This function makes the server serve the customer in his queue.
   *
   * @param time represents the time of service.
   */
  public void serveCustomer(double time) {
    this.isIdle = false;
    this.queue.serveCust(time);
    totalNumOfServedCustomers++;
  }

  /**
   * This function makes the server serve the customer waiting in line after the
   * earlier customer leaves.
   *
   * @param time represents time of service of waiting customer.
   */
  public void serveNextCustomer(double time) {
    this.isIdle = false;
    this.queue.customerLeaves();
    this.queue.serveCust(time);
    totalNumOfServedCustomers++;
  }

  /**
   * This method sets the state of the server as idle.
   */
  public void makeIdle() {
    this.isIdle = true;
  }

  /**
   * This method returns the arrival time of the customer waiting in line.
   *
   * @return arrival time of customer waiting in line.
   */
  public double getArrivalTimeOfNextCustomer() {
    return this.queue.getFirstCustomerArrivalTime();
  }

}
