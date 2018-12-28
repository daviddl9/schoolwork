package com.company;
/**
 * This class represents each customer in the queue. Each customer
 * has an unique ID and a respective state.
 * @author David
 * @version 1.0
 */
public class Customer {
    private int ID;
    private boolean customerWaiting;
    private boolean customerBeingServed;
    private double timeStartedWaiting = 0.0;
    static double totalWaitingTime = 0.0;
    static int totalNumOfLostCustomer = 0;
    Customer(int ID, double time) {
        this.ID = ID;
        this.customerBeingServed = true;
        this.customerWaiting = false;
        this.timeStartedWaiting = time;
    }

    public boolean isCustomerBeingServed() {
        return customerBeingServed;
    }
    public boolean isCustomerWaiting() {
        return customerWaiting;
    }
    public void startWaiting(double time) {
        this.customerWaiting = true;
        this.timeStartedWaiting = time;
    }

    public void customerLeaves() {
        totalNumOfLostCustomer++;
    }
    public void getServed(double time) {
        this.customerBeingServed = true;
        this.customerWaiting = false;
        totalWaitingTime += (time - this.timeStartedWaiting);
    }
    public double getTimeStartedWaiting() {
        return this.timeStartedWaiting;
    }

}
