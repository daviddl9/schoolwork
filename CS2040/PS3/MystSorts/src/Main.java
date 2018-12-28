import java.util.Random;

public class Main {
  public static void main(String[] args) {
    Detective detective = new Detective();
    StopWatch stopWatch = new StopWatch();
    int size = 100000;
    // First, check default sort

    System.out.println("Calling Asort");
    System.out.println("Test 1: Size: " + size + " Sorted: " + detective
        .checkSorted(new
            SorterA(),
        size));
    System.out.println("Test 2: Size: " + size*2 + " Sorted: " + detective
        .checkSorted(new SorterA(), 2 *
        size));

    System.out.println("Calling Bsort");
    System.out.println("Test 1: Size: " + size + " Sorted: " + detective
        .checkSorted(new SorterB(), size
    ));
    System.out.println("Test 2: Size: " + size*2 + " Sorted: " + detective
        .checkSorted(new SorterB(), size
    * 2));

    System.out.println("Calling Csort");
    System.out.println("Test 1: Size: " + size + " Sorted: " + detective
        .checkSorted(new SorterC(), size
    ));
    System.out.println("Test 2: Size: " + size*2 + " Sorted: " + detective
        .checkSorted(new SorterC(), size
    * 2));

    System.out.println("Calling Dsirt");
    System.out.println("Test 1: Size: " + size + " Sorted: " + detective
        .checkSorted(new SorterD(), size
    ));
    System.out.println("Test 2: Size: " + size*2 + " Sorted: " + detective
        .checkSorted(new SorterD(), size
    * 2));

    System.out.println("Calling Esirt");
    System.out.println("Test 1: Size: " + size + " Sorted: " + detective
        .checkSorted(new SorterE(), size
        ));
    System.out.println("Test 2: Size: " + size*2 + " Sorted: " + detective
        .checkSorted(new SorterE(), size
        * 2));




  }
}
