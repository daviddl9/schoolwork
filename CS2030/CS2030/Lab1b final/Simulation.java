package cs2030.simulator;

/**
 * This class represents a simulation of a specified event.
 *
 * @author David
 * @version CS2030 AY17/18 Sem2 Lab1b
 */
public class Simulation {
  private static int lastCustomerId;
  private Shop shop;
  public static double totalWaitingTime = 0.0;
  //Random generator for the simulation. 
  private RandomGenerator rng;

  /**
   * this constructor creates a simulation with a given event.
   *
   * @param numOfServers represents the number of servers in the simulation.
   */
  Simulation(int numOfServers, RandomGenerator rng) {
    this.shop = new Shop(numOfServers);
    this.rng = rng;
  }

  /**
   * This method represents the simulation of an event occurring.
   *
   * @param sim represents the simulator at which the simulation is done.
   * @param event represents the event to be simulated.
   */
  public void runSimulation(Simulator sim, Event event) {
    switch (event.eventType()) {
      case EventType.CUSTOMER_ARRIVE:
        // A customer has arrived.  Increase the ID and assign it to this
        // customer.
        double serviceTime = rng.genServiceTime();
        lastCustomerId++;
        Customer c = new Customer(lastCustomerId, event.getTime(), serviceTime);
        System.out.printf("%6.3f %d arrives\n", event.getTime(),
            lastCustomerId);
        this.shop.addCustomer(sim, c);
        break;
      case EventType.CUSTOMER_DONE:
        // A customer is done being served.
//        System.out.printf("%6.3f %d done\n", e.getTime(),
//            lastCustomerId);
        Server lastServer = event.getServer();
        lastServer.customerDone(event.getTime());
        if (lastServer.hasQueue()) {
          lastServer.serveNextCustomer(sim, event.getTime());
          this.shop.incrementTotalWaitingTime((event.getTime() - lastServer
              .getArrivalTimeOfNextCustomer()));

        } else {
          lastServer.makeIdle();
        }
        break;
      default:
        System.err.printf("Unknown event type %d\n", event.eventType());
    }
  }
}
