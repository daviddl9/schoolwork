package com.company;

/**
 * This class represents the customers in a Queue.
 * Each Customer has a unique ID, time of arrival and a state.
 * Customer is either waiting or being served.
 */
public class Customer {
  private int id;
  private boolean customerWaiting;
  private boolean customerBeingServed;
  private double timeStartedWaiting = 0.0;
  static double totalWaitingTime = 0.0;
  static int totalNumOfLostCustomer = 0;

  /**
   * Creates a new customer with a unique ID and arrival time.
   *
   * @param id represents the unique ID of the customer.
   * @param time represents the time of arrival.
   */
  Customer(int id, double time) {
    this.id = id;
    this.customerBeingServed = true;
    this.customerWaiting = false;
    this.timeStartedWaiting = time;
  }

  /**
   * This method checks if the customer is currently being served.
   *
   * @return true if the customer is currently being served, false otherwise.
   */
  public boolean isCustomerBeingServed() {
    return customerBeingServed;
  }

  /**
   * Checks if the customer is currently waiting.
   *
   * @return true if the customer is currently waiting, false otherwise.
   */
  public boolean isCustomerWaiting() {
    return customerWaiting;
  }

  /**
   * This method makes the customer wait, at a given time.
   *
   * @param time represents the time the customer starts waiting.
   */
  public void startWaiting(double time) {
    this.customerWaiting = true;
    this.timeStartedWaiting = time;
  }

  /**
   * This method makes a given customer leave.
   */
  public void customerLeaves() {
    totalNumOfLostCustomer++;
  }

  /**
   * This function simulates a customer getting served.
   *
   * @param time represents the time at which the customer gets served.
   */
  public void getServed(double time) {
    this.customerBeingServed = true;
    this.customerWaiting = false;
    totalWaitingTime += (time - this.timeStartedWaiting);
  }

  /**
   * This method returns the time at which a given customer has started
   * waiting.
   *
   * @return time the customer started waiting.
   */
  public double getTimeStartedWaiting() {
    return this.timeStartedWaiting;
  }

  /**
   * This method returns the ID of a given customer.
   *
   * @return ID of customer.
   */
  public int getID() {
    return this.id;
  }
}
