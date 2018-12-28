package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * A shop object maintains the list of servers and support queries
 * for server.
 *
 * @author weitsang
 * @author atharvjoshi
 * @author David
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
    IntStream.iterate(0, x -> x + 1).limit(numOfServers).forEach(id ->
        this.servers.add(new Server(id)));
  }

  /**
   * Creates a new shop based on a list of servers passed in.
   * @param servers The list of servers in the shop.
   */
  private Shop(List<Server> servers) {
    this.servers = servers;
  }

  /**
   * Creates a new shop, based on the servers of the old shop passed in.
   * @param oldShop The old shop to be copied over.
   */
  Shop(Shop oldShop) {
    this.servers = new ArrayList<>();
    this.servers.addAll(oldShop.servers);
  }

  /**
   * Return the first idle server in the list.
   *
   * @return An idle server, wrapped in an optional.
   */
  public Optional<Server> findIdleServer() {
    return servers.stream().filter(Server::isIdle).findFirst();
  }

  /**
   * Return the first server with no waiting customer.
   * @return A server with no waiting customer, wrapped in an optional.
   *     Returns Optional.empty() if there are no such servers available.
   */
  public Optional<Server> findServerWithNoWaitingCustomer() {
    return servers.stream().filter(s -> !s.customerWaiting()).findFirst();
  }

  /**
   * Finds the server based on the server passed in to update the state of
   * the server. Pre-condition: Valid server is passed in. Post condition:
   * The optional returned will always contain a server.
   * @param update The server to be updated.
   * @return A server, wrapped in an Optional.
   */
  public Optional<Server> update(Server update) {
    return servers.stream().filter(s -> s.equals(update)).findFirst();
  }

  /**
   * Updates the shop based on a server update and returns a new shop.
   * @param updatedServer The server to be updated
   * @return The updated Shop.
   */
  public Shop updateServer(Server updatedServer) {
    List<Server> newList = this.servers.stream().map(s -> {
      if (s.equals(updatedServer)) {
        return updatedServer;
      }
      return s;
    }).collect(Collectors.toList());
    return new Shop(newList);
  }

  /**
   * Return a string representation of this shop.
   * @return A string reprensetation of this shop.
   */
  public String toString() {
    return servers.toString();
  }
}
