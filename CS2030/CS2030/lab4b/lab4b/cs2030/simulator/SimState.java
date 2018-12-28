package cs2030.simulator;

import cs2030.util.PriorityQueue;
import cs2030.util.Pair;
import java.util.Optional;
import java.util.List;

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

  /** The log of events of the simulator. */
  private StringBuilder eventLog;

  /** The last customer ID currently in the simulation. */
  private int lastCustomerId;

  /**
   * Constructor for creating a simulation state.
   * @param numOfServers The number of servers in the simulation state.
   * @param eventList The list of events in the simulation state.
   */
  SimState(int numOfServers, List<Event> eventList) {
    java.util.PriorityQueue<Event> newQ = new java.util.PriorityQueue<>(eventList);
    this.shop = new Shop(numOfServers);
    this.stats = new Statistics();
    this.events = new PriorityQueue<>(newQ);
    this.eventLog = new StringBuilder();
    this.lastCustomerId = 0;
  }

  /**
   * Creates a new simulation state based on the parameters passed in.
   * @param shop The Shop to be updated in the simulation state.
   * @param oldState The previous state of the simulation.
   */
  private SimState(Shop shop, SimState oldState) {
    this.shop = shop;
    this.events = new PriorityQueue<>(oldState.events);
    this.stats = new Statistics(oldState.stats);
    this.eventLog = new StringBuilder(oldState.eventLog);
    this.lastCustomerId = oldState.lastCustomerId;
  }

  /**
   * Private constructor to create a new simulation state, based on the
   * parameters passed in.
   * @param shop The shop in the simulation state,
   * @param stats The statistics of the simulation state.
   * @param events The events in the Simulation state.
   * @param eventLog The event log of the simulation state.
   * @param lastCustomerId The ID of the last customer in the simulation state.
   */
  private SimState(Shop shop, Statistics stats, PriorityQueue<Event> events,
                   StringBuilder eventLog, int lastCustomerId) {
    this.shop = new Shop(shop);
    this.stats = new Statistics(stats);
    this.events = new PriorityQueue<>(events);
    this.eventLog = new StringBuilder(eventLog);
    this.lastCustomerId = lastCustomerId;
  }

  /**
   * Add an event to the simulation's event queue.
   * @param  e The event to be added to the queue.
   * @return The new simulation state.
   */
  private SimState addEvent(Event e) {
    PriorityQueue<Event> newQ = new PriorityQueue<>(this.events);
    return new SimState(this.shop, this.stats, newQ.add(e), this.eventLog,
        this.lastCustomerId);
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
    return new SimState(this.shop, this.stats, this.events, new StringBuilder(
        this.eventLog).append(String.format("%6.3f" + " %s " + "arrives\n",
        time, c)), this.lastCustomerId + 1);
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
    return new SimState(this.shop, this.stats, this.events, new StringBuilder(this
        .eventLog).append(String.format("%6.3f %s waits for %s\n", time,
    c, s)), this.lastCustomerId);
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
    return new SimState(this.shop, this.stats.serveOneCustomer()
        .customerWaitedFor(time - c.timeArrived()), this.events, new
        StringBuilder(this.eventLog).append(String.format("%6.3f %s served by" +
        " %s\n", time, c, s)), this.lastCustomerId);
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
    return new SimState(this.shop.updateServer(s), this.stats, this.events, new
        StringBuilder(this.eventLog).append(String
        .format(" %6.3f %s done " + "served by %s\n",time, c, s)), this.lastCustomerId);
  }

  /**
   * Called when a customer leaves the shops without service.
   * Update the log and statistics.
   * @param  time  The time this customer leaves.
   * @param  customer The customer who leaves.
   * @return A new state of the simulation.
   */
  private SimState customerLeaves(double time, Customer customer) {
    return new SimState(this.shop, this.stats.lostOneCustomer(), this.events,
        new StringBuilder(this.eventLog).append(String.format(
            "%6.3f %s leaves\n", time, customer)), this.lastCustomerId);
  }

  /**
   * Simulates the logic of what happened when a customer arrives.
   * The customer is either served, waiting to be served, or leaves.
   * @param time The time the customer arrives.
   * @return A new state of the simulation.
   */
  public SimState simulateArrival(double time) {
    Customer customer = new Customer(time, this.lastCustomerId);
    return this.customerArrives(time, customer).servedOrLeave(time, customer);
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
    return shop.findIdleServer().map(s -> serveCustomer(time, s, customer))
                                .orElseGet(() -> shop.findServerWithNoWaitingCustomer()
                                .map(s -> makeCustomerWait(time, s, customer))
                                .orElseGet(() -> customerLeaves(time, customer)));
  }

  /**
   * Simulates the logic of what happened when a customer is done being
   * served.  The server either serve the next customer or becomes idle.
   * @param time The time the service is done.
   * @param server The server serving the customer.
   * @param customer The customer being served.
   * @return A new state of the simulation.
   */
  private SimState simulateDone(double time, Server server, Customer customer) {
    // Return the updated simulation state if there is a server to be updated
    // or the same simulation state if there is no server to be updated. 
    Optional<Server> s = this.shop.update(server);
    return s.map(ser -> {
      SimState updatedState = this.customerDone(time, ser, customer);
      return updatedState.serveNextOrIdle(time, ser);
    }).orElse(this);
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
    // This statement checks if the server done has a waiting customer, and
    // proceeds to update the sate of the simulation and simultaneously serve
    // the next customer if the server has a waiting customer. If the server
    // has no waiting customers, the server is made idle and the state of the
    // simulation is updated.
    return server.getWaitingCustomer().map(c -> {
      Server updatedServer = server.removeWaitingCustomer();
      SimState updatedState = serveCustomer(time, updatedServer, c);
      return new SimState(updatedState.shop.updateServer(updatedServer), updatedState);
    }).orElseGet(() -> new SimState(this.shop.updateServer(server.makeIdle()), this));
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
    Server serving = server.serve(customer);
    SimState updated = addEvent(new Event(doneTime, s -> s.simulateDone(doneTime,
        serving, customer)));
    return new SimState(updated.shop.updateServer(serving), updated.stats,
        updated.events, updated.eventLog, updated.lastCustomerId).customerServed(time,
        serving, customer);
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
    Server updatedServer = server.askToWait(customer);
    SimState newState = customerWaits(time, updatedServer, customer);
    return new SimState(newState.shop.updateServer(updatedServer), newState.stats,
        newState.events, newState.eventLog, newState.lastCustomerId);
  }

  /**
   * Return a string representation of the simulation state, which
   * consists of all the logs and the stats.
   * @return A string representation of the simulation.
   */
  public String toString() {
    return this.eventLog.append(stats.toString()).toString();
  }
}
