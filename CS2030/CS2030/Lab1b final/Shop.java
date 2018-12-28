package cs2030.simulator;

/**
 * This class represents a shop. Each shop has it's own server(s) and
 * customer(s).
 *
 * @author David
 * @version CS2030 Sem 2 AY17/18 Lab1b
 */
public class Shop {
  private Server[] servers;
  public Statistics stats;

  /**
   * This constructor creates a shop, with an array of servers and customers.
   *
   * @param numOfServers refers to the number of servers in the shop.
   */
  Shop(int numOfServers) {
    this.servers = new Server[numOfServers];
    int i;
    for (i = 0; i < numOfServers; i++) {
      this.servers[i] = new Server(true);
    }
    this.stats = new Statistics();
  }

  /**
   * This function adds a customer to an idle server in the shop. If the server is idle,
   * the customer gets served immediately. If all servers are currently serving another 
   * customer, the customer gets added to the queue of the first server with no queue.
   *
   * @param sim represents the simulator to which the customer is added to.
   * @param c represents the customer to be added.
   */
  public void addCustomer(Simulator sim, Customer c) {
    //Boolean flag to check if all servers are occupied.
    boolean isAllOccupied = true;
    /*Boolean flag to check if all servers are full. (ie currently serving a customer
    have a customer waiting in line */
    boolean isAllFull = true;
    //First, loops through all available servers to find the first idle server. 
    for (Server s: servers) {
      if (s.isIdle()) {
        s.incrementCustomers(c); //adds the customer to the first idle server found.
        //Then, simultaneously start serving the customer.
        s.serveCustomer(sim, c.getTimeStartedWaiting()); 
        stats.incrementTotalNumOfServedCustomers();
        isAllOccupied = false;
        isAllFull = false;
        break;
      }
    }
    //If there are no idle servers currently, the customer waits at the first
    // busy server without a waiting customer that he/she found.
    if (isAllOccupied) {
      for (Server s: servers) {
        if (!s.hasQueue()) {
          s.makeCustomerWait(c, c.getTimeStartedWaiting());
          isAllFull = false;
          break;
        }
      }
    }
    //If all servers are currently occupied and have queues, the customer
    // leaves.
    if (isAllFull) {
      c.customerLeaves(c.getTimeStartedWaiting());
      stats.incrementTotalNumOfLostCustomers();
    }

    //Updates the stats of the shop. 
    public void incrementTotalWaitingTime(double time) {
      stats.incrementTotalWaitingTime(time);
    }
  }

//  /**
//   * This function prints out the current states of all the servers in the
//   * shop. Useful for debugging purposes.
//   */
//  public void checkState() {
//    int i = 1;
//    for (Server s: servers) {
//      System.out.println("Server " + i + s.toString());
//      i++;
//    }
//  }

}
