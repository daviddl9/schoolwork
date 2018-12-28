/**
 * The Server class keeps track of who is the customer being served (if any)
 * and who is the customer waiting to be served (if any).
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @author David
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
class Server extends ServiceProvider {

  /** The unique ID of this server. */
  private final int id;

  /** The state of a server. */
  private boolean isTakingBreak;

  /**
   * Creates a server and initializes it with a unique id. This constructor is
   * used when each server does not have its own unique queue.
   */
  public Server(int maxQueueLength) {
    super(null, null, maxQueueLength);
    this.id = ServiceProvider.lastServerId;
    ServiceProvider.lastServerId++;
    this.isTakingBreak = false;
  }

  /**
   * This method resumes the service of a server, after taking a break.
   *
   * @param sim the simulator at which the simulation is happening.
   * @param time the time at which the server returns from a break.
   */
  public void resumeService(Simulator sim, double time) {
    Customer c = super.queue.remove();
    if (c != null) {
      this.serve(c);
      c.serveBegin(time, this);
      System.out.printf("%s has service time of %.3f %n", c, c.getServiceTime
          ());
      sim.createDoneEvent(time, this, c);
    } else {
      this.makeIdle();
      this.isTakingBreak = false;
    }
  }

  /**
   * Return a string representation of this server.
   *
   * @return A string representation of this server.
   */
  @Override
  public String toString() {
    return "HS" + this.id + " (Q: " + super.queue + ")";
  }

  /**
   * Makes the current server Idle.
   */
  @Override
  public void makeIdle() {
    this.currentCustomer = null;
  }

  public void takeBreak() {
    this.isTakingBreak = true;
  }

  public boolean isIdle() {
    return (this.currentCustomer == null && !isTakingBreak);
  }

  public void backFromBreak() {
    this.isTakingBreak = false;
  }

  /**
   * Returns the state of the server.
   *
   * @return true if the server is taking a break, false otherwise.
   */
  public boolean isTakingBreak() {
    return isTakingBreak;
  }
}
