package cs2030.simulator;

//import java.lang.reflect.Constructor;

enum EventType {
  CUSTOMER_ARRIVE, CUSTOMER_DONE;
}

/**
 * This class represents an event in the simulator.
 * There are currently 2 types of events - customer arriving and customer
 * leaving.
 *
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
public class Event {
  private double when;
  private EventType eventType;
  private Server server;

  /**
   * This constructor creates a new event, with a specified time and type.
   * This constructor is to be used for when a new customer arrives.
   *
   * @param when refers to the time of event.
   * @param type 1 refers to a customer arriving and 2 refers to a customer
   *             leaving.
   */
  Event(double when, EventType type) {
    this.when = when;
    this.eventType = type;
  }

  /**
   * This constructor creates a new event, with a specified time, type and
   * server creating the event. This constructor is to be used for when a
   * server serves a customer.
   *
   * @param when refers to the time of event.
   * @param type 1 refers to a customer arriving and 2 refers to a customer
   *             leaving.
   * @param s represents the respective server creating the event.
   */
  Event(double when, EventType type, Server s) {
    this.when = when;
    this.eventType = type;
    this.server = s;
  }

  /**
   * This method returns the type of the event.
   *
   * @return 1 if customer arrives, 2 if customer leaves.
   */
  public EventType eventType() {
    return this.eventType;
  }

  /**
   * This method returns the time of a specific event.
   *
   * @return time of event.
   */
  public double getTime() {
    return when;
  }

  /**
   * This method returns the server of a particular event.
   *
   * @return server that created the event.
   */
  public Server getServer() {
    return this.server;
  }

  /**
  * This class serves as a comparator class for events.
  */
  static class EventComparator implements Comparator<Event> {
  	
  	/**
  	* This method takes in two events and returns one if the first 
  	* after the second event, -1 if the second event if after the first,
  	* and 0 if both events occur at the same time. 
  	*
  	* @param first the first event to be compared.
  	* @param second the second event to be compared. 
  	* @return 1 if the first event happened after the second event, -1 
  	* if the first event happened before the second, and zero if both
  	* events happened at the same time. 
  	*/
  	public int compare(Event first, Event second) {
  		if (first.when > second.when) {
  			return 1;
  		} else if (first.when < second.when) {
  			return -1;
  		} else
  			return 0;
  	}
  }
}
