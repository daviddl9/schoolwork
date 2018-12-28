public class ServerDoneEvent extends Event {
  /** The server that returns from a break. */
  private Server server;

  public ServerDoneEvent(double time, Server server) {
    super(time);
    this.server = server;
  }

  /**
   * The abstract method that simulates this event.
   *
   * @param sim The simulator.
   */
  @Override
  void simulate(Simulator sim) {
    sim.simulateServerDone(time, server);
  }
}
