package cs2030.simulator;

import java.util.Optional;

/**
 * The Server class keeps track of who is the customer being served (if any)
 * and who is the customer waiting to be served (if any).
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */

class Server implements Comparable<Server> {

  /** The unique ID of this server. */
  private final int id;

  /** The customer currently being served, if any. */
  private Optional<Customer> currentCustomer;

  /** The customer currently waiting, if any. */
  private Optional<Customer> waitingCustomer;

  /**
   * Creates a server and initialises it with a unique id.
   * @param id The unique id of the server.
   */
  Server(int id) {
    this.waitingCustomer = Optional.empty();
    this.currentCustomer = Optional.empty();
    this.id = id;
  }

  /**
   * Creates a server based on the information passed in.
   * @param currentCustomer The current customer being served by the server,
   *                        wrapped around an optional.
   * @param waitingCustomer The customer waiting to be served by the server,
   *                        wrapped around an optional.
   * @param id The unique ID of the server.
   */
  private Server(Optional<Customer> currentCustomer, Optional<Customer> waitingCustomer, int id) {
    this.currentCustomer = currentCustomer;
    this.waitingCustomer = waitingCustomer;
    this.id = id;
  }

  /**
   * Change this server's state to idle by removing its current customer.
   * @return A new server with the current customer removed.
   */
  public Server makeIdle() {
    return new Server(Optional.empty(), Optional.empty(), this.id);
  }

  /**
   * Checks if the current server is idle.
   * @return true if the server is idle (no current customer); false otherwise.
   */
  public boolean isIdle() {
    return !this.currentCustomer.isPresent();
  }

  /**
   * Checks if there is a customer waiting for given server.
   * @return true if a customer is waiting for given server; false otherwise.
   */
  public boolean customerWaiting() {
    return this.waitingCustomer.isPresent();
  }

  /**
   * Returns waiting customer for given server.
   * @return customer waiting for given server.
   */
  public Optional<Customer> getWaitingCustomer() {
    return this.waitingCustomer;
  }

  /**
   * Removes the customer waiting for given server.
   * @return The new server with waiting customer removed.
   */
  public Server removeWaitingCustomer() {
    return new Server(this.currentCustomer, Optional.empty(), this.id);
  }

  /**
   * Serve a customer.
   * @param customer The customer to be served.
   * @return The new server serving this customer.
   */
  public Server serve(Customer customer) {
    return new Server(Optional.of(customer), this.waitingCustomer, this.id);
  }

  /**
   * Make a customer wait for this server.
   * @param customer The customer who will wait for this server.
   * @return The new server with a waiting customer.
   */
  public Server askToWait(Customer customer) {
    return new Server(this.currentCustomer, Optional.of(customer), this.id);
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
    return obj instanceof Server && (this.id == ((Server) obj).id);
  }

  /**
   * Compares the servers based on their IDs, so they can be sorted in order.
   * @param s The server to be compared to.
   * @return 1 if the server has a higher id than the server passed in, -1 if
   *     the server has a lower id than the server passed in, and 0 otherwise.
   */
  public int compareTo(Server s) {
    if (this.id > s.id) {
      return 1;
    } else if (this.id < s.id) {
      return -1;
    }
    return 0;
  }

  /**
   * Return the hashcode for this server.
   * @return the ID of this server as its hashcode.
   */
  public int hashCode() {
    return this.id;
  }
}
