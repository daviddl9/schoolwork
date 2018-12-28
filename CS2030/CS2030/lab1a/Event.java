package com.company;

import java.lang.reflect.Constructor;
/**
 * This class represents an event and how you can manipulate it.
 * It includes elements like the event type and the time of event.
 *
 * @author David
 * @version 1.0
 */
public class Event {
  public static final int CUSTOMER_ARRIVE = 1;
  public static final int CUSTOMER_DONE = 2;
  private double when;
  private int type;
  private Customer cust;
  static int lastCustomerId = 0;



  Event(double when, int type) {
    this.when = when;
    this.type = type;
  }

  public int eventType() {
    return type;
  }
  public double getTime() {
      return when;
  }
//  public void simulateEvent() {
//    switch (type) {
//      case CUSTOMER_ARRIVE:
//        // A customer has arrived.  Increase the ID and assign it to this customer.
//        lastCustomerId++;
//        Customer cust = new Customer(lastCustomerId, this.when);
//        System.out.printf("%6.3f %d arrives\n", when, lastCustomerId);
//
//        // If there is no customer currently being served.  Serve this one.
//        int currentCustomer = lastCustomerId;
//        if (cust.isCustomerBeingServed() == false) {
//          serveCustomer(when, currentCustomer);
//        } else if (!sim.customerWaiting) {
//          // If there is a customer currently being served, and noone is waiting, wait.
//          makeCustomerWait(when, currentCustomer);
//        } else {
//          // If there is a customer currently being served, and someone is waiting, the
//          // customer just leaves and go elsewhere (maximum only one waiting customer).
//          customerLeaves(sim, e.time, currentCustomer);
//        }
//        break;
//      case CUSTOMER_DONE:
//        // A customer is done being served.
//        System.out.printf("%6.3f %d done\n", e.time, sim.servedCustomerId);
//        if (sim.customerWaiting) {
//          // Someone is waiting, serve this waiting someone.
//          serveWaitingCustomer(sim, e.time);
//        } else {
//          // Server idle
//          sim.customerBeingServed = false;
//        }
//        break;
//      default:
//        System.err.printf("Unknown event type %d\n", type);
//    }
//  }
//  public void serveCustomer(double time, int id) {
//    Event e = new Event(time, id);
//
//  }

}
