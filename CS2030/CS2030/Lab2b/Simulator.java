/**
 * The Simulator class encapsulates information and methods pertaining to a
 * Simulator.
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */

import java.util.Comparator;
import java.util.PriorityQueue;

class Simulator {

  /** The priority queue of events. Events are ordered according to time. */
  private PriorityQueue<Event> events;

  /** The shop of servers being simulated. */
  private final Shop shop;

  /** The statistics being maintained. */
  public static Statistics stats = new Statistics();

  /** The random generator of the simulator. */
  private RandomGenerator rng;

  /** The total number of customer arrivals to simulate */
  private int numOfCustomers;

  /** The probability of a server resting. */
  private double pRest;

  /** The probability of a customer being greedy. */
  private double pGreedy;

  /** The time beyong which the customer joins the self-checkout queue. */
  private double tSelf; 


  /**
   * Creates a Simulator and initialises it.
   *
   * @param seed the seed for the random generator.
   * @param numOfServers the number of servers in the simulation.
   * @param selfCheckoutCounters the number of self-checkout counters in the
   *                             simulation.
   * @param maxQlength the maximum length of a queue a server can have.
   * @param numOfCustomers the number of customers to simulate.
   * @param lambda the arrival rate of customers.
   * @param mu the service rate.
   * @param rho the resting rate of servers.
   * @param pRest the probability of a server resting.
   * @param pGreedy the probability of a customer being greedy.
   * @param tSelf the time beyond which the customer joins the self-checkout
   *              queue.
   */
  public Simulator(int seed, int numOfServers, int selfCheckoutCounters, int
      maxQlength, int numOfCustomers, double lambda, double mu, double rho,
                   double pRest, double pGreedy, double tSelf) {
    this.rng = new RandomGenerator(seed, lambda, mu, rho);
    this.shop = new Shop(numOfServers, selfCheckoutCounters, maxQlength);
    this.events = new PriorityQueue<>(new Event.EventComparator());
    this.numOfCustomers = numOfCustomers;
    this.pRest = pRest;
    this.pGreedy = pGreedy;
    this.tSelf = tSelf;
  }

  /**
   * Schedules an event with this simulator. The simulator maintains an array
   * of events (in arbitrary order). This method appends the given event at
   * the end of the array.
   *
   * @param event The event to be scheduled for simulation.ar
   * @return Returns true if the event is added successfully; false otherwise.
   */
  public boolean scheduleEvent(Event event) {
    return events.add(event);
  }

  /**
   * Run the Simulator until all scheduled events are simulated.
   */
  public void run() {
    // Runs simulations while there are still events to be simulated.
    while (this.numOfCustomers > 1 || events.peek() != null) {
      Event event = getNextEarliestEvent();
      // decrement the number of customers expected if it is an arrival event.
      if (event instanceof ArrivalEvent) {
        numOfCustomers--;
      }
      event.simulate(this);
    }
  }

  /**
   * Get the next event with the earliest timestamp, breaking ties between
   * events happening at the same time arbitrarily. The event is then deleted
   * from the array. This is an O(n) algorithm and better algorithms exists.
   * This will be improved in later labs using a min heap.
   *
   * @return The next event with the earliest timestamp.
   */
  private Event getNextEarliestEvent() {
    Event event = this.events.poll();
    return event;
  }


  /**
   * Create a DoneEvent for the given server and customer to be simulated.
   *
   * @param time The time this event is scheduled to occur.
   * @param server The server to serve the customer.
   * @param customer The customer being served.
   */
  public void createDoneEvent(double time, ServiceProvider server, Customer
      customer) {
    Event event = new DoneEvent(time + customer.getServiceTime(),
        server, customer);
    this.events.add(event);
  }

  /**
   * Simulate the arrival of a customer.
   *
   * @param time The time this event is scheduled to occur.
   */
  public void simulateArrival(double time) {
    if (numOfCustomers - 1 > 0) {
      double arrivalTime = rng.genInterArrivalTime();
      this.scheduleEvent(new ArrivalEvent(arrivalTime + time));
    }
    double serviceTime = rng.genServiceTime();
    boolean isGreedy = rng.genCustomerType() < pGreedy;
    Customer customer = new Customer(time, serviceTime, isGreedy);
    customer.arrive(time);
    if (serviceTime < tSelf) { // If eligible for self-checkout
      if (!customer.isGreedy()) { // If customer is not greedy.
        Counter counter; // Find idle self-checkout
        // counter first.
        counter = shop.findIdleCounter();
        if (counter != null) {
          this.serveCustomer(time, counter, customer);
          return;
        }
        // if all self-checkout counters are engaged, queue up at the first
        // idle server.
        Server server = shop.findIdleServer();
        if (server != null) {
          this.serveCustomer(time, server, customer);
          return;
        }
        // If there are no idle servers around, find the nearest counter.
        counter = shop.findNearestCounter();
        if (counter != null) {
          this.makeCustomerWait(time, counter, customer);
          return;
        }
        // If there are no counters available, find the nearest server.
        server = shop.findNearestServer();
        if (server != null) {
          this.makeCustomerWait(time, server, customer);
          return;
        }

        // If idle server not found, and if server with no customer waiting not
        // found, customer leaves (maximum of one waiting customer per server).
        this.customerLeaves(time, customer);
      } else { // Else if customer is greedy and eligible for self-checkout
        // Customer finds idle counter first
        Counter counter = shop.findIdleCounter(); // Find idle self-checkout
        // counter first.
        if (counter != null) {
          this.serveCustomer(time, counter, customer);
          return;
        }
        // if all self-checkout counters are engaged, queue up at the first
        // idle server.
        Server server = shop.findIdleServer();
        if (server != null) {
          this.serveCustomer(time, server, customer);
          return;
        }
        // Next, if there are no idle servers, find the counter with the
        // shortest queue to queue up at.
        counter = shop.findShortestQCounter();
        if (counter != null) {
          this.makeCustomerWait(time, counter, customer);
          return;
        }
        // else, if all counters are busy, greedy customers go to find the
        // server with the shortest queue and wait.
        server = shop.findShortestQServer();
        if (server != null) {
          this.makeCustomerWait(time, server, customer);
          return;
        }

        // If all servers are fully occupied, customer leaves.
        customerLeaves(time, customer);
      }
    } else { // If ineligible for self-checkout
      if (customer.isGreedy()) {
        // First, find idle server to get served.
        Server server = shop.findIdleServer();
        if (server != null) {
          this.serveCustomer(time, server, customer);
          return;
        }
        // If no idle servers available, find the server with the shortest
        // queue.
        server = shop.findShortestQServer();
        if (server != null) {
          this.makeCustomerWait(time, server, customer);
          return;
        }
        // Customer leaves if unable to find any servers.
        customerLeaves(time, customer);
        return;
      } else { // If customer not greedy
        Server server = shop.findIdleServer();
        if (server != null) {
          this.serveCustomer(time, server, customer);
          return;
        }
        // If server with no full queue is found, wait for this server.
        server = shop.findNearestServer();
        if (server != null) {
          this.makeCustomerWait(time, server, customer);
          return;
        }
        // If idle server not found, and if server with no customer waiting not
        // found, customer leaves (maximum of one waiting customer per server).
        this.customerLeaves(time, customer);
      }
    }
  }

  /**
   * Simulate that the server is done serving a customer.
   *
   * @param time The time this event is scheduled to occur.
   * @param server The server to serve the customer.
   * @param customer The customer being served.
   */
  public void simulateDone(double time, ServiceProvider server,  Customer
      customer) {
    customer.serveEnd(time, server);
    if (server instanceof Server) {
      boolean serverGoingtoRest = (rng.genRandomRest() < pRest);
      if (serverGoingtoRest) {
        Server s = (Server) server;
        double restTime = rng.genRestPeriod();
        System.out.printf("Server %s will be taking a rest for %.3f. %n", server
            .toString(), restTime);
        s.takeBreak();
        Event e = new ServerDoneEvent(time + restTime, s);
        this.scheduleEvent(e);
        return;
      }
    }
    if (server.customerWaiting()) {
      // Someone is waiting, serve this waiting someone
      this.serveWaitingCustomer(time, server);
    } else {
      // Server idle
      server.makeIdle();
    }
  }

  /**
   * Simulate a server starting to serve a customer at the given time.
   * Precondition: No one else must be served at this time.
   *
   * @param time The {@code time} at which the {@code server} serves the
   *     {@code customer}.
   * @param server The {@code server} who would be serving the {@code customer}
   *     at {@code time}.
   * @param customer The {@code customer} to be served by the {@code server} at
   *     {@code time}.
   */
  public void serveCustomer(double time, ServiceProvider server, Customer customer) {
    server.serve(customer);
    customer.serveBegin(time, server);
    this.createDoneEvent(time, server, customer);
  }

  /**
   * Simulate a customer starting to wait for a server at time.
   * Precondition: All customers are busy serving and there is at least one
   * server with no customer waiting for him.
   *
   * @param time The {@code time} at which the {@code customer} starts
   *     waiting for the {@code server}.
   * @param server The server whom the customer is waiting for at time.
   * @param customer The customer who is waiting for the server at time.
   */
  public void makeCustomerWait(double time, ServiceProvider server, Customer
      customer) {
    server.askToWait(customer);
    customer.waitBegin(time, server);
  }

  /**
   * Simulate a server serving his waiting customer at time.
   *
   * @param time The {@code time} at which the {@code server} serves
   *     his waiting customer.
   * @param server The {@code server} who is to serve his waiting customer.
   */
  public void serveWaitingCustomer(double time, ServiceProvider server) {
    Customer customer = server.removeWaitingCustomer();
    this.serveCustomer(time, server, customer);
  }

  /**
   * Simulate a customer leaving because all servers are busy serving and
   * every server has a customer waiting.
   *
   * @param time The {@code time} at which the {@code customer} leaves.
   * @param customer The {@code customer} which leaves at {@code time}.
   */
  public void customerLeaves(double time, Customer customer) {
    customer.leave(time);
  }

  public void simulateServerDone(double time, Server server) {
    System.out.printf(" %.3f %s is back from rest. %n", time, server);
    if (server.customerWaiting()) {
      server.resumeService(this, time);
    } else {
      server.makeIdle();
      server.backFromBreak();
    }
  }
}
