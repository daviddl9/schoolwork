package cs2030.simulator;

/**
 * This class represents a server in the system. A server has his own service
 * time, and his own queue of customers. A server also has his own state -
 * whether he is currently idle or if he currently has a queue of customers
 * to serve.
 *
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
public class Server {
  private Queue queue;
  private boolean isIdle;
  private static final double SERVICE_TIME = 1.0;
  private boolean hasQueue; // represents if a server currently has a
  // customer waiting to be served.
  private int numOfCustomers;
  static int totalNumOfServedCustomers = 0;

  /**
   * This constructor takes in the current state of a server and creates a new
   * server. A server has his own service queue, which is kept to a maximum
   * of two customers. A server has his own state - whether he is either idle
   * or he has a queue of customers currently waiting to be served.
   *
   * @param isIdle represents the current state of the server. True if the
   *               server is idle (i.e does not have any customer to serve),
   *               and false if the server is not idle (i.e has customers to
   *               serve).
   */
  Server(boolean isIdle) {
    this.isIdle = isIdle;
    this.hasQueue = false;
    this.queue = new Queue(new Customer[2]);
    this.numOfCustomers = 0;
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
   * This method makes a given customer wait, at a given timing.
   *
   * @param cust represents the customer that has to wait.
   * @param time time at which customer starts waiting.
   */
  public void makeCustomerWait(Customer cust, double time) {
    this.hasQueue = true;
    incrementCustomers(cust);
    cust.startWaiting(time);
    System.out.printf("%6.3f %d starts waiting\n", time, cust.getID());
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
   * @param c represents the customer to be added into the queue of the server.
   */
  public void incrementCustomers(Customer c) {
    this.queue.addCustomer(c);
    this.numOfCustomers++;
  }

  /**
   * This function makes the server serve the customer in his queue.
   *
   * @param sim represents the simulator to which the event will be added.
   * @param time represents the time of service.
   * @param c represents the customer to be served.
   */
  public void serveCustomer(Simulator sim, double time, Customer c) {
    this.isIdle = false;
//    this.queue.serveCust(time);
    boolean isPossible = sim.scheduleEventInSimulator(new Event(time +
        c.getServiceTime(), EventType.CUSTOMER_DONE, this));
    if (!isPossible) {
      System.err.println("Warning: too many events.  Simulation result will " +
          "not be correct.");
    }
    totalNumOfServedCustomers++; //takes note of the total number
    // of customers all the servers have served.
  }

  /**
   * This function makes the server serve the customer waiting in line after the
   * earlier customer leaves.
   *
   * @param sim represents the simulator to which the event will be added to.
   * @param time represents time of service of waiting customer.
   */
  public void serveNextCustomer(Simulator sim, double time, Customer c) {
    serveCustomer(sim, time, c);
    this.hasQueue = false;
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

  /**
   * This method simulates a customer leaving a server after he/she is served.
   * The customer leaves the queue and the server becomes idle.
   *
   * @param time refers to the time at which the customer gets served.
   */
  public void customerDone(double time) {
    this.numOfCustomers--;
    this.isIdle = true;
    System.out.printf("%6.3f %d done\n", time,
        this.queue.getFirstCustomerID());
    this.queue.customerLeaves();
  }

  /**
   * This method checks the state of a particular server, useful for
   * debugging purposes.
   *
   * @return String with the information of a given server.
   */
  @Override
  public String toString() {
    return "Num of Customers: " + numOfCustomers + ", isIdle: " + isIdle + "," +
        " hasQueue: " + hasQueue;
  }
}
