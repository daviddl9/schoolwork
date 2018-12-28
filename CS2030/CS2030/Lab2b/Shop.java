/**
 * A shop object maintains the list of servers and support queries
 * for server.
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
class Shop {
  /** List of servers. */
  private final Server[] servers;

  /** List of self-checkout counters. */
  private final Counter[] counters;

  /**
   * Create a new shop with a given number of servers.
   *
   * @param numOfServers The number of servers.
   */
  Shop(int numOfServers, int numOfCounters, int maxQlength) {
    this.servers = new Server[numOfServers];
    int i;
    for (i = 0; i < numOfServers; i++) {
      this.servers[i] = new Server(maxQlength);
    }
    System.out.println(numOfServers + " Servers created in shop.");
    this.counters = new Counter[numOfCounters];
    for (i = 0; i < numOfCounters; i++) {
      this.counters[i] = new Counter(maxQlength);
    }
    System.out.println(numOfCounters + " Counters created in shop.");

  }

  /**
   * Return the first idle server in the list.
   *
   * @return An idle server, or {@code null} if every server is busy.
   */
  public Server findIdleServer() {
    for (Server server: this.servers) {
      if (server.isIdle()) {
        return server;
      }
    }
    return null;
  }

  /**
   * Return the first idle counter in the list.
   *
   * @return An idle server, or {@code null} if every server is busy.
   */
  public Counter findIdleCounter() {
    for (Counter counter: this.counters) {
      if (!counter.customerBeingServed()) {
        return counter;
      }
    }
    return null;
  }

  /**
   * Finds and returns the self-checkout counter with the shortest queue.
   *
   * @return the counter with the shortest queue, and returns null if there
   * are no counters available.
   */
  public Counter findShortestQCounter() {
    int min = Integer.MAX_VALUE;
    Counter shortestQ = null;
    if (counters.length > 0) {
      min = counters[0].queue.size();
      shortestQ = counters[0];
    }
    for (Counter c: counters) {
      if (c.queue.size() < min && c.canAddCustomers()) {
        min = c.queue.size();
        shortestQ = c;
      }
    }
    return shortestQ;
  }

  /**
   * Finds and returns the server with the shortest queue.
   *
   * @return the server with the shortest queue available, and null if there
   * are no servers available.
   */
  public Server findShortestQServer() {
    int min = Integer.MAX_VALUE;
    Server shortestQ = null;
    if (servers.length > 0 && servers[0].canAddCustomers()) {
      min = servers[0].queue.size();
      shortestQ = servers[0];
    }
    for (Server c: servers) {
      if (c.queue.size() < min && c.canAddCustomers()) {
        min = c.queue.size();
        shortestQ = c;
      }
    }
    return shortestQ;
  }

  /**
   * Return the first server with no waiting customer.
   * @return A server with no waiting customer, or {@code null} is every
   *     server already has a waiting customer.
   */
  public Server findNearestServer() {
    for (Server server: this.servers) {
      if (server.canAddCustomers()) {
        return server;
      }
    }
    return null;
  }

  /**
   * Return the first server with no waiting customer.
   * @return A counter with no waiting customer, or {@code null} is every
   *     counter already has a waiting customer.
   */
  public Counter findNearestCounter() {
    for (Counter counter: this.counters) {
      if (counter.canAddCustomers()) {
        return counter;
      }
    }
    return null;
  }
}
