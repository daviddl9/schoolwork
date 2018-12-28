/**
 * The Customer class encapsulates information and methods pertaining to a
 * Customer in a simulation.  A customer in the simulator goes through either
 * one of the following two paths: (i) arrives and leaves, or (ii) arrives,
 * waits, starts service, ends service, and leaves.
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
class Customer {
  /** The unique ID of the last created customer.  */
  private static int lastCustomerId = 0;

  /** The unique ID of this customer. */
  private int id;

  /** The time this customer arrives. */
  private double timeArrived;

  /** The unique service time of the customer. */
  private double serviceTime;

  /** The type of customer. */
  private boolean isGreedy;

  /**
  * This constructor creates a customer with a given time arrived and service 
  * time. The {@code id} of the customer is set.
  * 
  * @param timeArrived the time at which a customer arrives
  * @param serviceTime the given service time of a customer. 
  */
  public Customer(double timeArrived, double serviceTime) {
    this.timeArrived = timeArrived;
    this.serviceTime = serviceTime;
    this.id = Customer.lastCustomerId;
    this.isGreedy = false;
    Customer.lastCustomerId++;
  }

  /**
   * This constructor creates a greedy customer with a given time arrived and
   * service time. The {@code id} of the customer is set.
   *
   * @param timeArrived the time at which a customer arrives
   * @param serviceTime the given service time of a customer.
   * @param isGreedy the type of customer.
   */
  public Customer(double timeArrived, double serviceTime, boolean isGreedy) {
    this.timeArrived = timeArrived;
    this.serviceTime = serviceTime;
    this.id = Customer.lastCustomerId;
    this.isGreedy = isGreedy;
    Customer.lastCustomerId++;
  }

  /**
   * This method checks if a customer is greedy or not.
   *
   * @return true if the customer is greedy, false otherwise.
   */
  public boolean isGreedy() {
    return this.isGreedy;
  }

  /**
   * Mark the arrival of the customer at the given time.
   *
   * @param time The time at which this customer arrives.
   */
  public void arrive(double time) {
    this.timeArrived = time;
    System.out.printf("%6.3f %s arrives\n", time, this);
  }

  /**
   * Mark that this customer is waiting for a given server at the given time.
   *
   * @param time The time at which this customer's service begins.
   * @param server The server this customer is waiting for.
   */
  public void waitBegin(double time, ServiceProvider server) {
    System.out.printf("%6.3f %s waits for %s\n", time, this, server);
  }

  /**
   * Mark the start of this customer's service at the given time.
   *
   * @param time The time at which this customer's service begins.
   * @param server The server that serves this customer.
   */
  public void serveBegin(double time, ServiceProvider server) {
    Simulator.stats.serveOneCustomer();
    Simulator.stats.customerWaitedFor(time - this.timeArrived);
    System.out.printf("%6.3f %s served by %s\n", time, this, server);
  }

  /**
   * Mark the end of this customer's service at the given time.
   *
   * @param time The time at which this customer's service begins.
   * @param server The server that serves this customer.
   */
  public void serveEnd(double time, ServiceProvider server) {
    System.out.printf("%6.3f %s done served by %s\n", time, this, server);
  }

  /**
   * Mark that this customer leaves without being served.
   *
   * @param time The time at which this customer leaves.
   */
  public void leave(double time) {
    Simulator.stats.lostOneCustomer();
    System.out.printf("%6.3f %s leaves\n", time, this);
  }

  /**
  * Gets the service time of a particular customer. 
  *
  * @return service time of customer.
  */
  public double getServiceTime() {
    return serviceTime;
  }

  /**
   * Return a string representation of this customer.
   *
   * @return A string representation of this customer.
   */
  public String toString() {
    if (isGreedy) {
      return "GC" + this.id;
    } else {
      return "TC" + this.id;
    }
  }
}
