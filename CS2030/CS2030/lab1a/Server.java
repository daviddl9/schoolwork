package com.company;

/**
 * This class represents a person in an event. Each person has a unique ID.
 * @author David
 * @version 1.0
 */
public class Server {
    private Queue queue = new Queue(new Customer[2]);
    private boolean isIdle;
    public static final double serviceTime = 1.0;
    private static int totalNumOfServedCustomers = 0;
    private boolean hasQueue = false;
    Server(boolean isIdle) {
        this.isIdle = isIdle;
    }
    public boolean isIdle() {
        return isIdle;
    }
    public double getServiceTime() {
        return serviceTime;
    }

    public void makeCustomerWait(Customer cust, double time) {
        cust.startWaiting(time);
    }
    public int getTotalNumOfServedCustomers() {
        return totalNumOfServedCustomers;
    }
    public boolean hasQueue() {
        return hasQueue;
    }
    public void incrementCustomers(Customer c) {
            this.queue.addCustomer(c);
    }
    public void setHasQueue() {
        this.hasQueue = true;
    }
    public void serveCustomer(double time) {
        this.isIdle = false;
        this.queue.serveCust(time);
        totalNumOfServedCustomers++;
    }
    public void serveNextCustomer(double time) {
        this.isIdle = false;
        this.queue.customerLeaves();
        this.queue.serveCust(time);
        totalNumOfServedCustomers++;

    }
    public void makeIdle() {
        this.isIdle = true;
    }
    public double getArrivalTimeOfNextCustomer() {
        return this.queue.getFirstCustomerArrivalTime();
    }

}