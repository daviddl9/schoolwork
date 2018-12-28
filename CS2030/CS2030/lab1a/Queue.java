package com.company;

public class Queue {
    private Customer[] customers;
    static int numOfCustomers = 0;
    static double totalWaitingTime = 0.0;
    Queue(Customer[] customers) {
        this.customers  = customers;
    }
    public void addCustomer(Customer c) {
        if (numOfCustomers < 2) {
            customers[numOfCustomers++] = c;
        }
        else c.customerLeaves();
    }
    public void customerLeaves() {
        this.numOfCustomers--;
        customers[0] = customers[1];
    }
    public void serveCust(double time) {
        this.customers[0].getServed(time);
        totalWaitingTime += time - this.customers[0].getTimeStartedWaiting();
    }
    public double getTotalWaitingTime() {
        return totalWaitingTime;
    }
    public double getFirstCustomerArrivalTime() {
        return this.customers[0].getTimeStartedWaiting();
    }

}
