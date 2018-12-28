package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

/**
 * A shop object maintains the list of servers and support queries
 * for server.
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */
class Shop {
  /** List of servers. */
  private final List<Server> servers;

  /**
   * Create a new shop with a given number of servers.
   * @param numOfServers The number of servers.
   */
  Shop(int numOfServers) {
    this.servers = new ArrayList<>(numOfServers);
    for (int i = 0; i < numOfServers; i++) {
      this.servers.add(new Server());
    }
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
   * Return the first server with no waiting customer.
   * @return A server with no waiting customer, or {@code null} is every
   *     server already has a waiting customer.
   */
  public Server findServerWithNoWaitingCustomer() {
    for (Server server: this.servers) {
      if (!server.customerWaiting()) {
        return server;
      }
    }
    return null;
  }

  /**
   * Return a string representation of this shop.
   * @return A string reprensetation of this shop.
   */
  public String toString() {
    return servers.toString();
  }
}
