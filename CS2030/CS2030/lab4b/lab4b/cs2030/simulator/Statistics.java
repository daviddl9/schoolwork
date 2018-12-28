package cs2030.simulator;

/**
 * This is an immutable class that stores stats about the simulation.
 * In particular, the average * waiting time, the number of customer
 * who left, and the number of customers who are served, are stored.
 *
 * @author Ooi Wei Tsang
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */
class Statistics {
  /** Sum of time spent waiting for all customers. */
  private double totalWaitingTime;

  /** Total number of customers who were served. */
  private int totalNumOfServedCustomers;

  /** Total number of customers who left without being served. */
  private int totalNumOfLostCustomers;

  /**
   * Creates a new Statistics object and initialises it.
   */
  Statistics() {
    this.totalWaitingTime = 0;
    this.totalNumOfServedCustomers = 0;
    this.totalNumOfLostCustomers = 0;
  }

  Statistics(Statistics oldStats) {
    this.totalWaitingTime = oldStats.totalWaitingTime;
    this.totalNumOfServedCustomers = oldStats.totalNumOfServedCustomers;
    this.totalNumOfLostCustomers = oldStats.totalNumOfLostCustomers;
  }

  /**
   * Creates a new Statistics object based on the parameters passed in.
   * @param totalWaitingTime The total waiting time of the simulator.
   * @param totalNumOfServedCustomers The total number of served customers
   *                                  kept track by the simulator.
   * @param totalNumOfLostCustomers The total number of lost customers in the
   *                               simulator.
   */
  private Statistics(double totalWaitingTime, int totalNumOfServedCustomers, int
      totalNumOfLostCustomers) {
    this.totalWaitingTime = totalWaitingTime;
    this.totalNumOfLostCustomers = totalNumOfLostCustomers;
    this.totalNumOfServedCustomers = totalNumOfServedCustomers;
  }

  /**
   * Mark that a customer is served.
   * @return A new Statistics object with updated stats
   */
  public Statistics serveOneCustomer() {
    return new Statistics(this.totalWaitingTime, this
        .totalNumOfServedCustomers + 1, this.totalNumOfLostCustomers);
  }

  /**
   * Mark that a customer is lost.
   * @return A new Statistics object with updated stats
   */
  public Statistics lostOneCustomer() {
    return new Statistics(this.totalWaitingTime, this
        .totalNumOfServedCustomers, this.totalNumOfLostCustomers + 1);
  }

  /**
   * Accumulate the waiting time of a customer.
   * @param time The time a customer waited.
   * @return A new Statistics object with updated stats
   */
  public Statistics customerWaitedFor(double time) {
    return new Statistics(this.totalWaitingTime + time, this
        .totalNumOfServedCustomers, this.totalNumOfLostCustomers);
  }

  /**
   * Return a string representation of the staistics collected.
   * @return A string containing three numbers: the average
   *     waiting time, followed by the number of served customer,
   *     followed by the number of lost customer.
   */
  public String toString() {
    return String.format("%.3f %d %d",
        totalWaitingTime / totalNumOfServedCustomers,
        totalNumOfServedCustomers, totalNumOfLostCustomers);
  }
}
