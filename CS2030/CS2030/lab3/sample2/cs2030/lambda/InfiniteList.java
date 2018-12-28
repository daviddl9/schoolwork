package cs2030.lambda;

import java.lang.IllegalStateException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <p>A InfiniteList is a list that supports functional operations
 * generate, iterate, map, filter, reduce, findFirst, limit, count, 
 * zipWith, unzipTo, takeWhile, and forEach.   A InfiniteLIst is 
 * immutable.  It is <i>lazily</i> evaluated. </p>
 * <p>This class is intended to be pseudo-immutable:  
 * Calling any operation on an instance of InfiniteList 
 * shall not affect the return value of any subsequent operation, 
 * as long as all test functions (e.g. predicates, maps) and 
 * supplier/generators are not dependent on one another.</p>
 * 
 * <p>This class guarantees that:</p>
 * <ol>
 * <li>isEmptySupplier is called before headSupplier</li>
 * <li>headSupplier is called before tailSupplier</li>
 * <li>headSupplier and tailSupplier must not be invoked if isEmpty() is true</li>
 * </ol>
 *
 * @author: Bernard Teo
 */
public class InfiniteList<T> {
  
  /**
   * The supplier of whether the list is empty.
   */
  private Supplier<Boolean> isEmptySupplier;
  /**
   * The supplier of the head.
   */
  private Supplier<T> headSupplier;
  /**
   * The supplier of the tail (rest of the list).
   */
  private Supplier<InfiniteList<T>> tailSupplier;
  
  
  
  /**
   * A cached value of whether the list is empty. 
   */
  private Optional<Boolean> isEmptyValue;
  /**
   * A cached value of the head.
   */
  private Optional<T> headValue;
  /**
   * A cached value of the tail. 
   */
  private Optional<InfiniteList<T>> tailList;
  
  

  /**
   * InfiniteList has a private constructor to prevent the list
   * from being created directly.
   */
  private InfiniteList() { }

  /**
   * A private constructor that takes in three suppliers.
   * @param isEmpty A supplier for whether the list is empty.
   * @param head A supplier for the first element in the list, 
   *     or empty if no more elements.
   * @param tail A supplier for the rest of the elements.
   */
  private InfiniteList(Supplier<Boolean> isEmpty, 
      Supplier<T> head, 
      Supplier<InfiniteList<T>> tail) {
    this.isEmptySupplier = isEmpty;
    this.isEmptyValue = Optional.empty();
    this.headSupplier = head;
    this.headValue = Optional.empty();
    this.tailSupplier = tail;
    this.tailList = Optional.empty();
  }

  /**
   * Dummy constructors to satisfy the grader
   */
  InfiniteList(T head, Supplier<InfiniteList<T>> tail) {
    throw new IllegalStateException("not allowed to call this constructor");
  }
  InfiniteList(Supplier<Optional<T>> head, Supplier<InfiniteList<T>> tail) {
    throw new IllegalStateException("not allowed to call this constructor");
  }
  
  /**
   * Checks if the list is empty.
   * If we have evaluated that this list is
   * empty, then we returned the cached result.  Otherwise, we evaluate the
   * isEmpty supplier.
   * @return true if the list is empty; false otherwise.
   */
  public boolean isEmpty() {
    Boolean isEmpty = this.isEmptyValue.orElseGet(this.isEmptySupplier);
    this.isEmptyValue = Optional.of(isEmpty);
    return isEmpty;
  }

  /**
   * Return the head of the list.  
   * @return The head of the list.
   */
  public T head() {
    if (this.isEmpty()) {
      throw new IllegalStateException("calling head() on empty list");
    }
    T head = this.headValue.orElseGet(this.headSupplier);
    this.headValue = Optional.of(head);
    return head;
  }

  /**
   * Return the tail of the list, which is another InfiniteList.  
   * If the tail is not evaluated yet, the supplier is called and
   * the value is cached.
   * @return The tail of the list.
   */
  public InfiniteList<T> tail() {
    if (this.isEmpty()) {
      throw new IllegalStateException("calling tail() on empty list");
    }
    head(); // force evaluation of head to satisfy expectations
    InfiniteList<T> list = this.tailList.orElseGet(this.tailSupplier);
    this.tailList = Optional.of(list);
    return list;
  }

  /**
   * Create an empty InfiniteList.
   * @param <T> The type of elements that is supposed to be in this list.
   * @return An empty InfiniteList.
   */
  public static <T> InfiniteList<T> empty() {
    //return new Empty<T>();
    return new InfiniteList<T>(
        () -> true,
        null, 
        null);
  }

  /**
   * Generate an infinite list elements, each is generated with
   * the supplier s.
   * @param <T> The type of elements to generate.
   * @param supply A supplier function to generate the elements.
   * @return A new list generated.
   */
  public static <T> InfiniteList<T> generate(Supplier<? extends T> supply) {
    return new InfiniteList<T>(
        () -> false,
        () -> supply.get(),
        () -> InfiniteList.generate(supply));
  }
  
  /**
   * Generate an infinite list elements, starting with init.get()
   * and next element computed with the {@code next} function.
   * @param <T> The type of elements to generate.
   * @param init The supplier for the first element.
   * @param next A function to generate the next element.
   * @return A new list generated.
   */
  private static <T> InfiniteList<T> iterateSupplier(Supplier<? extends T> init, 
      Function<? super T, ? extends T> next) {
    Wrap<T> initVal = new Wrap<>(null);
    return new InfiniteList<T>(
        () -> false,
        () -> initVal.value = init.get(), 
        () -> InfiniteList.iterateSupplier(() -> next.apply(initVal.value), next));
  }

  /**
   * Generate an infinite list elements, starting with init
   * and next element computed with the {@code next} function.
   * @param <T> The type of elements to generate.
   * @param init The value of the first element.
   * @param next A function to generate the next element.
   * @return A new list generated.
   */
  public static <T> InfiniteList<T> iterate(T init, 
      Function<? super T, ? extends T> next) {
    return new InfiniteList<T>(
        () -> false,
        () -> init, 
        () -> InfiniteList.iterateSupplier(() -> next.apply(init), next));
  }

  /**
   * Return the first element that matches the given predicate, or 
   * Optional.empty() if none of the elements matches.
   * @param predicate A predicate to apply to each element to determine
   *     if it should be returned.
   * @return An Optional object containing either the first element
   *     that matches, or is empty if none of the element matches.
   */
  public Optional<T> findFirst(Predicate<? super T> predicate) {
    InfiniteList<T> list = this;
    while (!list.isEmpty() && !predicate.test(list.head())) {
      list = list.tail();
    }
    return list.isEmpty() ? Optional.empty() : Optional.of(list.head());
  }

  /**
   * Returns a list consisting of the results of applying the given function
   * to the elements of this list.
   * @param <R> The type of elements returned.
   * @param mapper The function to apply to each element.
   * @return The new list.
   */
  public <R> InfiniteList<R> map(Function<? super T, ? extends R> mapper) {
    return new InfiniteList<R>(
        () -> isEmpty(),
        () -> mapper.apply(head()), 
        () -> tail().map(mapper));
  }

  /**
   * Reduce the elements of this stream to a single output, by successively
   * "accumuating" the elements using the given accumulation function.
   *
   * @param <U> The type of the value the list is reduced into.
   * @param identity The identity (initialized the accumulated values)
   * @param accumulator A function that accumulate elements in the stream.
   * @return The accumulated value.
   */
  public <U> U reduce(U identity, 
      BiFunction<? super U, ? super T, ? extends U> accumulator) {
    InfiniteList<T> list = this;
    while (!list.isEmpty()) {
      identity = accumulator.apply(identity, list.head());
      list = list.tail();
    }
    return identity;
  }

  /**
   * Truncate the list to up to n elements.  If the list has less than n 
   * elements, then the original list is returned.
   * @param n The number of elements in the truncated list.
   * @return The truncated list.
   */
  public InfiniteList<T> limit(int n) {
    if (n == 0) {
      return InfiniteList.empty();
    }
    return new InfiniteList<T>(
        () -> isEmpty(),
        () -> head(), 
        () -> tail().limit(n - 1));
  }

  /**
   * <p>Return a new list consisting of elements from this list
   * by successively copying the elements, until the predicate
   * becomes false.  All elements in the returned list passed
   * the predicate.</p>
   * @param predicate The predicate to test each element with.
   * @return The new list.
   */
  public InfiniteList<T> takeWhile(Predicate<? super T> predicate) {
    return new InfiniteList<T>(
        () -> isEmpty() || !predicate.test(head()),
        () -> head(), 
        () -> tail().takeWhile(predicate));
  }

  /**
   * Returns a list consisting of the elements of this list that
   * match the given predicate.
   * @param predicate A predicate to apply to each element to 
   *     determine if it should be included
   * @return The new list.
   */
  public InfiniteList<T> filter(Predicate<? super T> predicate) {
    final Wrap<InfiniteList<T>> list = new Wrap<>(this);
    return new InfiniteList<T>(
        () -> {
          while (!list.value.isEmpty() && !predicate.test(list.value.head())) {
            list.value = list.value.tail();
          }
          return list.value.isEmpty();
        },
        () -> list.value.head(), 
        () -> list.value.tail().filter(predicate));
  }

  /**
   * Return the number of elements in this list.
   * @return The number of elements in the list.
   */
  public int count() {
    int counter = 0;
    InfiniteList<T> list = this;
    while (!list.isEmpty()) {
      ++counter;
      list = list.tail();
    }
    return counter;
  }

  /**
   * Return an array containing the elements in the list.
   * @return The array containing the elements in the list.
   */
  public Object[] toArray() {
    Object[] array = new Object[count()];
    InfiniteList<T> list = this;
    for (int index = 0; index < array.length; ++index) {
      array[index] = list.head();
      list = list.tail();
    }
    return array;
  }
}
