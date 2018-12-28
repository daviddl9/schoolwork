package cs2030.util;

/**
 * This class serves as a priority queue of Events, to be used with the
 * simulator.
 * @param <T> The parameter of the priority queue.
 */
public class PriorityQueue<T> {
  private java.util.PriorityQueue<T> pq;

  /**
   * Creates a new PriorityQueue based on a queue passed in.
   * @param q The queue to create a priority queue with.
   */
  public PriorityQueue(java.util.PriorityQueue<T> q) {
    this.pq = q;
  }

  /**
   * Adds an object to the priority queue, and returns a new priority queue.
   * @param object The object to be added into the queue.
   * @return A new priority queue with the added object.
   */
  public PriorityQueue<T> add(T object) {
    java.util.PriorityQueue<T> newQ = new java.util.PriorityQueue<>(pq);
    newQ.add(object);
    return new PriorityQueue<>(newQ);
  }

  /**
   * Creates a new Priority Queue with a queue that is passed in.
   * @param q The old queue to be passed in.
   */
  public PriorityQueue(PriorityQueue<T> q) {
    this.pq = new java.util.PriorityQueue<>(q.pq);
  }

  public Pair<T, PriorityQueue<T>> poll() {
    T t = this.pq.poll();
    return new Pair<>(t, this);
  }
}
