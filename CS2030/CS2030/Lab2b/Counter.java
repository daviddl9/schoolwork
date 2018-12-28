/**
 * This class encapsulates the information of a self-service counter.
 *
 * @author David
 * @version CS2030 AY 17/18 Lab 2b
 */
public class Counter extends ServiceProvider {
  /** The unique ID of the last created counter. */
  private static int lastCounterId = 0;
  /** The unique ID of this counter. */
  private final int id;

  /**
   * This constructor creates a Queue of customers
   *
   * @param maxQlength
   */
  public Counter(int maxQlength) {
    super(null, null, maxQlength);
    this.id = ServiceProvider.lastServerId;
    ServiceProvider.lastServerId++;
  }

  @Override
  public String toString() {
    return "CS" + this.id + " (Q: " + super.queue + ")";
  }
}
