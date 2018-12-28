import java.util.*;

public class CookieSelection {
	Scanner sc = new Scanner(System.in);
	static PriorityQueue<Integer> minHeap = new PriorityQueue<>();
	static PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
	static int currentMedian = 0;
	public static void main(String[] args) {
		// Scanner sc = new Scanner(System.in);
		String line;
		int cookieSize;
		while (sc.hasNext()) {
			line = sc.nextLine();
			System.out.println(line);
			if (line.equals("#")) {
				cookieSize = sendCookie();
				System.out.println(cookieSize);
			} else {
				addCookie(line);
			}
		}
		sc.close();
	}

	static int sendCookie() {
		if (minHeap.size() == maxHeap.size()) {
			System.out.println(maxHeap.poll());
		} else {
			System.out.println(minHeap.poll());
		}
	}

	static void addCookie(String line) {
		int cookieSize = Integer.parseInt(line);
		if (currentMedian == 0) {
			currentMedian = cookieSize;
			minHeap.add(currentMedian);
		} else if (cookieSize > currentMedian) {
			minHeap.add(cookieSize);
			if (maxHeap.size() - minHeap.size() < 0) {
				currentMedian = minHeap.poll();
				maxHeap.add(currentMedian);
			}
		} else {
			maxHeap.add(cookieSize);
			if (maxHeap.size() - minHeap.size() > 1) {
				currentMedian = maxHeap.poll();
				minHeap.add(currentMedian);
			}

		}

	}
}