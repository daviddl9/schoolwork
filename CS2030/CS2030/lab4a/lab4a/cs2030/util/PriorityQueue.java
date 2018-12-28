package cs2030.util;

import java.util.Optional;

public class PriorityQueue<T> {
  java.util.PriorityQueue<T> pq;

  public PriorityQueue() {
    pq = new java.util.PriorityQueue<T>();
  }

  public PriorityQueue<T> add(T object) {
    this.pq.add(object);
    return this;
  }

  public Pair<T, PriorityQueue<T>> poll() {
    T t = this.pq.poll();
    return new Pair<>(t, this);
  }
}