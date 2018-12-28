import java.util.*;

class Detective {
    StopWatch stopWatch = new StopWatch();
    private Random rng = new Random();

    // You might want this function
    // Fisher-Yates shuffle, randomly permutes an array of length n
    // in O(n) time
    private <T> void shuffle(T[] array){
        if(array==null || array.length < 2){
            return;
        }

        for(int i=1;i<array.length;i++){
            int a = rng.nextInt(i+1);
            T temp   = array[a];
            array[a] = array[i];
            array[i] = temp;
        }
    }

    /**
     * Question 1.1
     *
     * Test whether the sorter works correctly by sorting *one* input of the
     * specified size and checking if it is sorted.
     */
    boolean checkSorted(ISort sorter, int size) {
      /*
      Algorithm: Create an array and call the sorter on the array. Subsequently
      check if the array is sorted after calling the
      */
        boolean isSorted = true;

        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rng.nextInt(size / 11);
        }
        shuffle(arr);
        stopWatch.start();
        sorter.sort(arr);
        stopWatch.stop();
        System.out.println("Duration: " + stopWatch.getTime());
        stopWatch.reset();
        for (int i = 1; i < size; i++) {
            if (arr[i].compareTo(arr[i - 1]) < 0) isSorted = false;
        }
        return isSorted;
    }

    /**
     * Question 1.2
     *
     * Test whether a given sort is stable by testing it on *one* input of the
     * specified size. You do not need to check if it is sorted!
     */
    boolean isStable(ISort sorter, int size) {
        Pair[] arr = new Pair[size];
        for (int i = 0; i < size; i++) {
            arr[i] = new Pair(rng.nextInt(size/11), i);
        }
        shuffle(arr);
        sorter.sort(arr);

        for (int i = 1; i < size; i++) {
            if (arr[i].compareTo(arr[i-1]) == 0 && arr[i].second < arr[i-1]
                .second) return false;
        }

        return true;
    }
}
