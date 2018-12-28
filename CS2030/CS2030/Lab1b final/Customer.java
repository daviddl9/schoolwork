package cs2030.simulator;

/**
 * This class represents the customers in a Queue.
 * Each Customer has a unique ID, time of arrival and a state.
 * Customer is either waiting or being served.
 */
public class Customer {
  private int id;
  private double timeStartedWaiting;
  static int totalNumOfLostCustomers = 0;
  private double serviceTime;


  /**
   * Creates a new customer with a unique ID and arrival time.
   *
   * @param id represents the unique ID of the customer.
   * @param time represents the time of arrival.
   */
  Customer(int id, double time, double serviceTime) {
    this.id = id;
    this.timeStartedWaiting = time;
    this.serviceTime = serviceTime;
  }

  /**
   * This method makes the customer wait, at a given time.
   *
   * @param time represents the time the customer starts waiting.
   */
  public void startWaiting(double time) {
    this.timeStartedWaiting = time;
  }

  /**
   * This method makes a given customer leave.
   */
  public void customerLeaves(double time) {
    totalNumOfLostCustomers++;
    System.out.printf("%6.3f %d leaves\n", time, this.id);
  }

  /**
   * This method returns the time at which a given customer has started
   * waiting.
   *
   * @return time the customer started waiting.
   */
  public double getTimeStartedWaiting() {
    return this.timeStartedWaiting;
  }

  /**
   * This method returns the ID of a given customer.
   *
   * @return ID of customer.
   */
  public int getID() {
    return this.id;
  }
}
