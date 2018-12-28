package com.company;

import java.lang.reflect.Constructor;

/**
 * This class represents an event in the simulator.
 * There are currently 2 types of events - customer arriving and customer
 * leaving.
 *
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
public class Event {
  public static final int CUSTOMER_ARRIVE = 1;
  public static final int CUSTOMER_DONE = 2;
  private double when;
  private int type;

  /**
   * This constructor creates a new event, with a specified time and type.
   *
   * @param when refers to the time of event.
   * @param type 1 refers to a customer arriving and 2 refers to a customer
   *             leaving.
   */
  Event(double when, int type) {
    this.when = when;
    this.type = type;
  }

  /**
   * This method returns the type of the event.
   *
   * @return 1 if customer arrives, 2 if customer leaves.
   */
  public int eventType() {
    return type;
  }

  /**
   * This method returns the time of a specific event.
   *
   * @return time of event.
   */
  public double getTime() {
    return when;
  }
}
