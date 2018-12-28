import java.util.*;

public class CookieSelection {
  static PriorityQueue<Integer> topHeap = new PriorityQueue<Integer>(); //minHeap
  static PriorityQueue<Integer> bottomHeap = new PriorityQueue<>(Collections.reverseOrder()); //maxHeap
  static int currentMedian = 0;
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String line;
    while (sc.hasNext()) {
      line = sc.nextLine();
      if (line.equals("#")) {
        sendCookie();
      } else {
        addCookie(line);
      }
    }
    sc.close();
  }

  public static void sendCookie() {
    if (topHeap.size() < bottomHeap.size()) {
      System.out.println(bottomHeap.poll());
    } else {
      System.out.println(topHeap.poll());
    }
  }

  public static void addCookie(String line) {
    int cookie = Integer.parseInt(line);
    if (currentMedian == 0) {
      currentMedian = cookie;
      bottomHeap.add(currentMedian);
    } else if (cookie > currentMedian) {
      topHeap.add(cookie);
      if (topHeap.size() - bottomHeap.size() > 1) {
        currentMedian = topHeap.poll();
        bottomHeap.add(currentMedian);
      }
    } else {
      bottomHeap.add(cookie);
      if (bottomHeap.size() - topHeap.size() > 1) {
        currentMedian = bottomHeap.poll();
        topHeap.add(currentMedian);
      }
    }
  }



}
