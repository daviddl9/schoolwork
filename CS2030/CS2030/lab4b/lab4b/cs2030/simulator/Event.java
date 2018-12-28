package cs2030.simulator;

import java.util.function.Function;

/**
 * The Event class encapsulates information and methods pertaining to a
 * Simulator event.  This is an abstract class that should be subclassed
 * into a specific event in the simulator.  The {@code simulate} method
 * must be written.
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
public class Event implements Comparable<Event> {
  /** The time this event occurs at. */
  private double time;

  /** The distinguishing function of events. */
  protected Function<SimState, SimState> task;

  /**
   * Creates an event and initialises it.
   * @param time The time of event.
   * @param task The distinguishing function of each event.
   */
  public Event(double time, Function<SimState, SimState> task) {
    this.time = time;
    this.task = task;
  }

  /**
   * Defines natural ordering of events by their time.
   * Events ordered in ascending order of their timestamps.
   *
   * @param other Another event to compare against.
   * @return 0 if two events occur at same time, a positive number if
   *     this event has later than other event, a negative number otherwise.
   */
  public int compareTo(Event other) {
    return (int)Math.signum(this.time - other.time);
  }

}
