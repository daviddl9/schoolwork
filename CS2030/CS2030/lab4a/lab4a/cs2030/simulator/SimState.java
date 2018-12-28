package cs2030.simulator;

import cs2030.util.PriorityQueue;
import cs2030.util.Pair;
import java.util.Optional;
import java.util.function.Function;

/**
 * This class encapsulates all the simulation states.  There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop
 * (the servers) and (iv) the event logs.
 *
 * @author atharvjoshi
 * @author weitsang
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */
public class SimState {
  /** The priority queue of events. */
  private PriorityQueue<Event> events;

  /** The statistics maintained. */
  private final Statistics stats;

  /** The shop of servers. */
  private final Shop shop;

  /**
   * Constructor for creating the simulation state from scratch.
   * @param numOfServers The number of servers.
   */
  public SimState(int numOfServers) {
    this.shop = new Shop(numOfServers);
    this.stats = new Statistics();
    this.events = new PriorityQueue<Event>();
  }

  /**
   * Add an event to the simulation's event queue.
   * @param  e The event to be added to the queue.
   * @return The new simulation state.
   */
  public SimState addEvent(Event e) {
    events.add(e);
    return this;
  }

  /**
   * Retrieve the next event with earliest time stamp from the
   * priority queue, and a new state.  If there is no more event, an
   * Optional.empty will be returned.
   * @return A pair object with an (optional) event and the new simulation
   *     state.
   */
  public Pair<Event, SimState> nextEvent() {
    Pair<Event, PriorityQueue<Event>> result = this.events.poll();
    return new Pair<>(result.first, this);
  }

  /**
   * Called when a customer arrived in the simulation.
   * @param time The time the customer arrives.
   * @param c The customer that arrrives.
   * @return A new state of the simulation after the customer arrives.
   */
  private SimState customerArrives(double time, Customer c) {
    System.out.printf("%6.3f %s arrives\n", time, c);
    return this;
  }

  /**
   * Called when a customer waits in the simulation.  This methods update
   * the logs of simulation.
   * @param time The time the customer starts waiting.
   * @param s The server the customer is waiting for.
   * @param c The customer who waits.
   * @return A new state of the simulation after the customer waits.
   */
  private SimState customerWaits(double time, Server s, Customer c) {
    System.out.printf("%6.3f %s waits for %s\n", time, c, s);
    return this;
  }

  /**
   * Called when a customer is served in the simulation.  This methods
   * update the logs and the statistics of the simulation.
   * @param time The time the customer arrives.
   * @param s The server that serves the customer.
   * @param c The customer that is served.
   * @return A new state of the simulation after the customer is served.
   */
  private SimState customerServed(double time, Server s, Customer c) {
    System.out.printf("%6.3f %s served by %s\n", time, c, s);
    stats.serveOneCustomer();
    stats.customerWaitedFor(time - c.timeArrived());
    return this;
  }

  /**
   * Called when a customer is done being served in the simulation.
   * This methods update the logs of the simulation.
   * @param time The time the customer arrives.
   * @param s The server that serves the customer.
   * @param c The customer that is served.
   * @return A new state of the simulation after the customer is done being
   *     served.
   */
  private SimState customerDone(double time, Server s, Customer c) {
    System.out.printf("%6.3f %s done served by %s\n", time, c, s);
    return this;
  }

  /**
   * Called when a customer leaves the shops without service.
   * Update the log and statistics.
   * @param  time  The time this customer leaves.
   * @param  customer The customer who leaves.
   * @return A new state of the simulation.
   */
  private SimState customerLeaves(double time, Customer customer) {
    System.out.printf("%6.3f %s leaves\n", time, customer);
    stats.lostOneCustomer();
    return this;
  }

  /**
   * Simulates the logic of what happened when a customer arrives.
   * The customer is either served, waiting to be served, or leaves.
   * @param time The time the customer arrives.
   * @return A new state of the simulation.
   */
  public SimState simulateArrival(double time) {
    Customer customer = new Customer(time);
    customerArrives(time, customer);
    servedOrLeave(time, customer);
    return this;
  }

  /**
   * Called from simulateArrival.  Handles the logic of finding
   * idle servers to serve the customer, or a server that the customer
   * can wait for, or leave.
   * @param time The time the customer arrives.
   * @param customer The customer to be served.
   * @return A new state of the simulation.
   */
  private SimState servedOrLeave(double time, Customer customer) {
    Server s = shop.findIdleServer();
    if (s != null) {
      serveCustomer(time, s, customer);
      return this;
    }
    s = shop.findServerWithNoWaitingCustomer();
    if (s != null) {
      makeCustomerWait(time, s, customer);
      return this;
    }
    customerLeaves(time, customer);
    return this;
  }

  /**
   * Simulates the logic of what happened when a customer is done being
   * served.  The server either serve the next customer or becomes idle.
   * @param time The time the service is done.
   * @param server The server serving the customer.
   * @param customer The customer being served.
   * @return A new state of the simulation.
   */
  public SimState simulateDone(double time, Server server, Customer customer) {
    customerDone(time, server, customer);
    serveNextOrIdle(time, server);
    return this;
  }

  /**
   * Called from simulateDone.  Handles the logic of checking if there is
   * a waiting customer, if so serve the customer, otherwise make the
   * server idle.
   * @param time The time the service is done.
   * @param server The server serving the next customer.
   * @return A new state of the simulation.
   */
  private SimState serveNextOrIdle(double time, Server server) {
    Customer c = server.getWaitingCustomer();
    if (c != null) {
      server.removeWaitingCustomer();
      serveCustomer(time, server, c);
      return this;
    }
    server.makeIdle();
    return this;
  }

  /**
   * Handle the logic of server serving customer.  A new done event
   * is generated and scheduled.
   * @param  time  The time this customer is served.
   * @param  server The server serving this customer.
   * @param  customer The customer being served.
   * @return A new state of the simulation.
   */
  private SimState serveCustomer(double time, Server server, Customer customer) {
    double doneTime = time + Simulator.SERVICE_TIME;
    addEvent(new DoneEvent(doneTime, server, customer));
    server.serve(customer);
    customerServed(time, server, customer);
    return this;
  }

  /**
   * Handle the logic of queueing up customer for server.   Make the
   * customer waits for server.
   * @param  time  The time this customer started waiting.
   * @param  server The server this customer is waiting for.
   * @param  customer The customer who waits.
   * @return A new state of the simulation.
   */
  private SimState makeCustomerWait(double time, Server server, Customer customer) {
    customerWaits(time, server, customer);
    server.askToWait(customer);
    return this;
  }

  /**
   * Return a string representation of the simulation state, which
   * consists of all the logs and the stats.
   * @return A string representation of the simulation.
   */
  public String toString() {
    return stats.toString();
  }
}
