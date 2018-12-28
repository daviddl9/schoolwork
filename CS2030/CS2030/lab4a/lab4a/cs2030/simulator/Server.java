package cs2030.simulator;

/**
 * The Server class keeps track of who is the customer being served (if any)
 * and who is the customer waiting to be served (if any).
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */
class Server {
  /** The unique ID of the last created server. */
  private static int lastServerId = 0;

  /** The unique ID of this server. */
  private final int id;

  /** The customer currently being served, if any. */
  private Customer currentCustomer;

  /** The customer currently waiting, if any. */
  private Customer waitingCustomer;

  /**
   * Creates a server and initalizes it with a unique id.
   */
  public Server() {
    this.currentCustomer = null;
    this.waitingCustomer = null;
    this.id = Server.lastServerId;
    Server.lastServerId++;
  }

  /**
   * Change this server's state to idle by removing its current customer.
   * @return A new server with the current customer removed.
   */
  public Server makeIdle() {
    this.currentCustomer = null;
    return this;
  }

  /**
   * Checks if the current server is idle.
   * @return true if the server is idle (no current customer); false otherwise.
   */
  public boolean isIdle() {
    return this.currentCustomer == null;
  }

  /**
   * Checks if there is a customer waiting for given server.
   * @return true if a customer is waiting for given server; false otherwise.
   */
  public boolean customerWaiting() {
    return this.waitingCustomer != null;
  }

  /**
   * Returns waiting customer for given server.
   * @return customer waiting for given server.
   */
  public Customer getWaitingCustomer() {
    return this.waitingCustomer;
  }

  /**
   * Removes the customer waiting for given server.
   * @return The new server with waiting customer removed.
   */
  public Server removeWaitingCustomer() {
    this.waitingCustomer = null;
    return this;
  }

  /**
   * Serve a customer.
   * @param customer The customer to be served.
   * @return The new server serving this customer.
   */
  public Server serve(Customer customer) {
    this.currentCustomer = customer;
    return this;
  }

  /**
   * Make a customer wait for this server.
   * @param customer The customer who will wait for this server.
   * @return The new server with a waiting customer.
   */
  public Server askToWait(Customer customer) {
    this.waitingCustomer = customer;
    return this;
  }

  /**
   * Return a string representation of this server.
   * @return A string S followed by the ID of the server, followed by the
   *     waiting customer.
   */
  public String toString() {
    return "S" + this.id + " (Q: " +
        ((waitingCustomer != null) ? waitingCustomer : "-") + ")";
  }

  /**
   * Checks if two servers have the same id.
   * @param  obj Another objects to compared against.
   * @return  true if obj is a server with the same id; false otherwise.
   */
  public boolean equals(Object obj) {
    if (!(obj instanceof Server)) {
      return false;
    }
    return (this.id == ((Server)obj).id);
  }

  /**
   * Return the hashcode for this server.
   * @return the ID of this server as its hashcode.
   */
  public int hashCode() {
    return this.id;
  }
}
