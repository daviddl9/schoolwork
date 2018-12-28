package com.company;

import javafx.concurrent.Service;

public class Simulator {
    private Event[] events;
    public static final int CUSTOMER_ARRIVE = 1;
    public static final int CUSTOMER_DONE = 2;
    static int totalWaitingTime = 0;
    static int totalNumOfLostCustomer = 0;
    static int totalNumOfServedCustomer = 0;
    private int numOfEvents;
    private boolean customerWaiting;
    private boolean customerBeingServed;
    private int lastCustomerId;
    private int servedCustomerId;
    private int waitingCustomerId;
    private static final int MAX_NUMBER_OF_EVENTS = 100;
    private static final double SERVICE_TIME = 1.0;
    private Customer cust;
    private Server server = new Server(true);
    /**
     * Create a simulator, initialize the value and return it.
     *
     * @return A newly created simulator.
     */
    Simulator() {
        this.events = new Event[this.MAX_NUMBER_OF_EVENTS];
        this.numOfEvents = 0;
        this.cust = new Customer(0,0.0);
        this.customerWaiting = false;
        this.customerBeingServed = false;
        this.lastCustomerId = 0;
        this.servedCustomerId = -1;
        this.waitingCustomerId = -1;
    }
    /**
     * Schedule the event with the simulator.  The simulator maintains
     * an array of event (in arbitrary order) and this method simply
     * appends the given event to the end of the array.
     *
     * @return true if the event is added successfully; false otherwise.
     */
    public boolean scheduleEventInSimulator(Event e) {
        if (numOfEvents >= MAX_NUMBER_OF_EVENTS) {
            return false;
        } else {
            // append e as the last element in array sim.events.
            events[numOfEvents] = e;
            numOfEvents++;
            return true;
        }
    }
//    /**
//     * Make the current customer with given id wait starting at given time in the given simulator.
//     * Precondition: someone is being served but noone is waiting
//     * Postcondition: someone is being served, and someone is waiting
//     */
//    static void makeCustomerWait(double time, int id) {
//        assert customerBeingServed == true;
//        assert customerWaiting == false;
//        waitingCustomerId = id;
//        System.out.printf("%6.3f %d waits\n", time, id);
//        customerWaiting = true;
//        timeStartedWaiting = time;
//        assert customerBeingServed == true;
//        assert customerWaiting == true;
//    }
//    /**
//     * Make the current customer with given id wait, starting at given time in the given simulator.
//     * Precondition: someone must be waiting, and noone is being served.
//     * Postcondition: noone is waiting, and someone is being served.
//     */
//    static void serveWaitingCustomer(double time) {
//        assert customerBeingServed == false;
//        assert sim.customerWaiting == true;
//        sim.customerWaiting = false;
//        serveCustomer(sim, time, sim.waitingCustomerId);
//        sim.totalWaitingTime += (time - sim.timeStartedWaiting);
//        assert sim.customerBeingServed == true;
//        assert sim.customerWaiting == false;
//    }

//   /**
//     * Make the current customer with given id leave, at given time in the given simulator.
//     * Precondition: someone must be waiting, and someone is being served.
//     * Postcondition: someone must be waiting, and someone is being served.
//     */
//    static void customerLeaves(Simulator sim, double time, int id) {
//        assert sim.customerBeingServed == true;
//        assert sim.customerWaiting == true;
//        System.out.printf("%6.3f %d leaves\n", time, id);
//        sim.totalNumOfLostCustomer++;
//        assert sim.customerBeingServed == true;
//        assert sim.customerWaiting == true;
//    }
    /**
     * Run the simulator until there is no more events scheduled.
     */
    public void runSimulator() {
        while (numOfEvents > 0) {
            Event e = getNextEarliestEvent();
            simulateEvent(e);
        }
    }
    /**
     * Simulate the event based on event type.
     */
    public void simulateEvent(Event e) {
        switch (e.eventType()) {
            case CUSTOMER_ARRIVE:
                // A customer has arrived.  Increase the ID and assign it to this customer.
                lastCustomerId++;
                Customer c = new Customer(lastCustomerId, e.getTime());
                System.out.printf("%6.3f %d arrives\n", e.getTime(), lastCustomerId);

                // If there is no customer currently being served.  Serve this one.
                if (this.server.isIdle()) {
                    this.server.incrementCustomers(c);
                    this.server.serveCustomer(e.getTime());
                    totalNumOfServedCustomer++;

                } else if (!this.server.isIdle() && !this.server.hasQueue()) {
                    // If there is a customer currently being served, and noone is waiting, wait.
                    this.server.makeCustomerWait(c, e.getTime());
                    this.server.incrementCustomers(c);
                    this.server.setHasQueue();
                } else {
                    // If there is a customer currently being served, and someone is waiting, the
                    // customer just leaves and go elsewhere (maximum only one waiting customer).
                    c.customerLeaves();
                    totalNumOfLostCustomer++;
                }
                break;
            case CUSTOMER_DONE:
                // A customer is done being served.
                System.out.printf("%6.3f %d done\n", e.getTime(), servedCustomerId);
                if (this.server.hasQueue()) {
                    // Someone is waiting, serve this waiting someone.
                   this.server.serveNextCustomer(e.getTime());
                   totalWaitingTime += (e.getTime() - this.server.getArrivalTimeOfNextCustomer());
                } else {
                    // Server idle
                   this.server.makeIdle();
                }
                break;
            default:
                System.err.printf("Unknown event type %d\n", e.eventType());
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
    public Event getNextEarliestEvent() {
        int nextEventIndex = -1;

        // Scan linearly through the array to find the event
        // with earliest (smallest) timestamp.
        double minTime = Double.MAX_VALUE;
        for (int i = 0; i < numOfEvents; i++) {
            if (events[i].getTime() < minTime) {
                minTime = events[i].getTime();
                nextEventIndex = i;
            }
        }

        // Get the earliest event
        Event e = events[nextEventIndex];

        // Replace the earliest event with the last element in
        // the array.
        events[nextEventIndex] = events[numOfEvents - 1];
        numOfEvents--;
        return e;
    }


//    /**
//     * Serve the current customer with given id at given time in the given simulator.
//     * Precondition: noone must be served at this time.
//     */
//    public void serveCustomer(double time, int id) {
////        assert sim.customerBeingServed == false;
////        sim.customerBeingServed = true;
////        sim.servedCustomerId = id;
//        System.out.printf("%6.3f %d served\n", time, id);
//        boolean ok = scheduleEventInSimulator(new Event(time + SERVICE_TIME, CUSTOMER_DONE));
//        if (!ok) {
//            System.err.println("Warning: too many events.  Simulation result will not be correct.");
//        }
//        totalNumOfServedCustomer++;
////        assert customerBeingServed == true;
//    }

}