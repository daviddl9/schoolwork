/**
 * This abstract class is subclasses by Counter and Server.
 *
 * @author David
 * @version CS2030 AY17/18 Lab2b
 */

import java.util.*;

enum ServiceType {
  SERVER, COUNTER
}

abstract class ServiceProvider {

  protected Queue<Customer> queue;
  protected Customer currentCustomer;
  protected Customer waitingCustomer;
  protected static int lastServerId = 0;
  protected int maxQsize;

  ServiceProvider(Customer currentCustomer, Customer
      waitingCustomer, int maxQsize) {
    this.queue = new LinkedList<>();
    this.currentCustomer = currentCustomer;
    this.waitingCustomer = waitingCustomer;
  }

  /** Changes the state of the service provider to idle. */
  void makeIdle() {
    this.currentCustomer = null;
  }

  /**
   * Checks if a customer is being served by this service provider.
   * @return true if a customer is being served by this counter, false
   * otherwise.
   */
  public boolean customerBeingServed() {
    return this.currentCustomer != null;
  }

  /**
   * Checks if a customer is waiting to be served by this service provider.
   * @return true if a customer is waiting for this counter, false otherwise.
   */
  public boolean customerWaiting() {
    return this.queue.size() != 0;
  }

  /**
   * Removes the customer waiting for this service provider.
   */
  public Customer removeWaitingCustomer() {
    return this.queue.remove();
  }

  /**
   * Serves a customer.
   *
   * @param c the customer to be served.
   */
  public void serve(Customer c) {
    this.currentCustomer = c;
  }

  /**
   * Make a customer wait for this service provider.
   *
   * @param c the customer to ask to wait.
   */
  public void askToWait(Customer c) {
    this.waitingCustomer = c;
    this.queue.add(c);
  }

  /**
   * This method checks if a customer can be added to a particular server.
   *
   * @return true if it is possible, false otherwise.
   */
  public boolean canAddCustomers() {
    return this.queue.size() != maxQsize;
  }

}