class Pair<T,U> {
  public T first;
  public U second;

  public Pair(T first, U second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public String toString() {
    return "(" + first + ", " + second + ")";
  }

  @Override 
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } 
    if (!(o instanceof Pair)) {
      return false;
    }
    @SuppressWarnings("unchecked")
    Pair<T,U> pair = (Pair)o;
    return (pair.first.equals(this.first) && pair.second.equals(this.second));
  }

  @Override
  public int hashCode() {
    return first.hashCode() ^ second.hashCode();
  }
}
