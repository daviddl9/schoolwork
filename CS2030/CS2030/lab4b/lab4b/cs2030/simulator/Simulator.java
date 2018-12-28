package cs2030.simulator;

import cs2030.util.Pair;

import java.util.List;

/**
 * The Simulator class encapsulates information and methods pertaining to a
 * Simulator.
 *
 * @author atharvjoshi
 * @author weitsang
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */
public class Simulator {
  /** The time a server takes to serve a customer. */
  static final double SERVICE_TIME = 1.0;

  /** The current simulation state of the simulator. */
  private SimState state;

  /**
   * Creates a new simulator based on the parameters passed in.
   * @param numOfServers The number of servers in the simulator.
   * @param eventList The list of events to be run by the simulator.
   */
  public Simulator(int numOfServers, List<Event> eventList) {
    this.state = new SimState(numOfServers, eventList);
  }

  /**
   * The main simulation loop.  Repeatedly get events from the event
   * queue, simulate and update the event.  Return the final simulation
   * state.
   * @return The final state of the simulation.
   */
  public SimState run() {
    Pair<Event, SimState> p = state.nextEvent();
    while (p.first != null) {
      p = p.first.task.apply(p.second).nextEvent();
    }
    return p.second;
  }
}
