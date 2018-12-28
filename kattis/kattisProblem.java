import java.util.*;

public class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numOfHouses = sc.nextInt();
		int numOfNetworkCables = sc.nextInt();
		boolean isConnected = true;
		Set<Node> allHouses = new HashSet<>();
		for (int i = 0; i < numOfNetworkCables; i++) {
			Node house = new Node(sc.nextInt());
			Node neighbor = new Node(sc.nextInt());
			house.addNeighbor(neighbor);
		}

		if (!allHouses.isEmpty()) {
			Node house = allHouses.get(0);
			house.BFS();
		}

		for (Node house : allHouses) {
			if (!house.isConnected) {
				System.out.println(house);
				isConnected = false;
			}
		}

		if (isConnected) {
			System.out.println("Connected");
		}
	}
}

class Node {
	private int id;
	private ArrayList<Node> connectedNodes;
	private boolean isConnected;
	private boolean isVisited;

	public Node(int id) {
		this.id = id;
		this.connectedNodes = new ArrayList<>();
		this.isConnected = false;
		this.isVisited = false;
	}

	public void addNeighbor(Node neighbor) {
		this.connectedNodes.add(neighbor);
		neighbor.addNeighbor(this);
	}

	public void isVisited() {
		return this.isVisited;
	}

	public void getId() {
		return this.id;
	}

	public void visit() {
		this.isVisited = true;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o instanceof Node) {
			Node temp = (Node) o;
			return temp.id == this.id;
		}
		return false;
	}

	public String toString() {
		return "" + this.id;
	}

	public void BFS(ArrayList<boolean> arr) {
		Queue<Node> q = new Queue<>();
		q.add(this);
		while (!q.isEmpty) {
			Node temp = q.dequeue();
			temp.visit();
			temp.setConnected(true);
			for (Node x : temp.connectedNodes) {
				if (!x.isVisited) {
					q.enqueue(x);
				}
			}
		}

	}

}