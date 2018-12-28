package cs2030.lambda;

/**
 * A simple class that wraps around some value.
 * This class is used to simulate mutable references.
 */
class Wrap<T> {
  public T value;
  
  public Wrap(T t) {
    value = t;
  }
}
