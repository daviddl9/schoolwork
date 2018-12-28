package cs2030.simulator;

/**
 * This class represents the statistics that is recorded by the application.
 *
 * @author David
 * @version CS2030 AY17/18 Lab1b
 */
public class Statistics {
  private int totalNumOfServedCustomers;
  private double totalWaitingTime;
  private int totalNumOfLostCustomers;

  Statistics() {
    this.totalNumOfLostCustomers = 0;
    this.totalNumOfServedCustomers = 0;
    this.totalWaitingTime = 0.0;
  }

//  /**
//   * This function prints the current statistics of the system.
//   */
//  static void print() {
//    System.out.printf("%.3f %d %d\n", totalWaitingTime /
//            totalNumOfServedCustomers, totalNumOfServedCustomers,
//        totalNumOfLostCustomers);
//  }

  @Override
  public String toString() {
    return String.format("%.3f %d %d\n", totalWaitingTime /
            totalNumOfServedCustomers, totalNumOfServedCustomers,
        totalNumOfLostCustomers);
  }

  public void incrementTotalWaitingTime(double time) {
    totalWaitingTime += time;
  }

  public void incrementTotalNumOfLostCustomers() {
    this.totalNumOfLostCustomers++;
  }

  public void incrementTotalNumOfServedCustomers() {
    this.totalNumOfServedCustomers++;
  }
}
