import java.util.Comparator;

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
abstract class Event {
  /** The time this event occurs at. */
  protected double time;

  /**
   * Creates an event and initializes it.
   *
   * @param time The time of occurrence.
   */
  public Event(double time) {
    this.time = time;
  }

  /**
   * The abstract method that simulates this event.
   *
   * @param sim The simulator.
   */
  abstract void simulate(Simulator sim);

  /**
   * This class serves as a comparator for the Event class.
   */
  static class EventComparator implements Comparator<Event> {

    /**
     * This function serves compares two events based on their time stamps.
     *
     * @param one The first event to compare.
     * @param two The second event to be compared.
     * @return 1 if the first event occurred later than the second, -1 if the
     * second event occurred later, and 0 if both events occurred at the same
     * time.
     */
    public int compare(Event one, Event two) {
      if (one.time > two.time) {
        return 1;
      } else if (one.time < two.time) {
        return -1;
      } else
        return 0;
    }
  }
}
