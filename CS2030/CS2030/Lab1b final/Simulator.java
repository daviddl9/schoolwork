package cs2030.simulator;

//import javafx.concurrent.Service;

/**
 * This class serves as a simulator of events.
 *
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
public class Simulator {
  // Number of events in the simulator.
  private int numOfEvents;
  // Simulation in the sumlator. 
  private Simulation simulation;
  // Priority Queue of events in the simulator. 
  private PriorityQueue<Event> events;

  /**
   * Create a simulator, initialize the values.
   *
   * @param numOfServers Number of servers to be created.
   * @param numOfEvents the Number of customers to be simulated.
   * @param seed the seed of the random generator. 
   * @param lambda arrival rate.
   * @param mu service rate. 
   */
  Simulator(int numOfServers, int numOfEvents, int seed, double lambda, double mu) {
    this.events = new PriorityQueue<Event>(new Event.EventComparator());
    this.numOfEvents = numOfEvents;
    this.simulation = new Simulation(numOfServers, new RandomGenerator(seed, lambda, mu));
  }

  /**
   * Schedule the event with the simulator.  The simulator maintains
   * an array of event (in arbitrary order) and this method simply
   * appends the given event to the end of the array.
   *
   * @param e represents the event you want to schedule into the Simulator.
   */
  public void scheduleEventInSimulator(Event e) {
    events.add(e);
  }

  /**
   * Run the simulator until there is no more events scheduled.
   */
  public void runSimulator() {
    while (events.peek() != null || numOfEvents - 1 > 0) {
      Event e = getNextEarliestEvent();
      if (e.eventType() == EventType.CUSTOMER_ARRIVE) {
        numOfEvents--;
      }
      simulation.runSimulation(this, e);
    }
  }

  /**
   * Find the next event with the earliest timestamp, breaking
   * ties arbitrarily.  The event is then deleted from the array.
   * This is an O(n) algorithm.  Better algorithm exists.  To be
   * improved in later labs using a min heap.
   *
   * @return the next event
   */
  private Event getNextEarliestEvent() {
    return this.events.poll();
  }

}
