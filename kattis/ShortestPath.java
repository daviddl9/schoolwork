import java.util.*;

public class ShortestPath {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		do {
			int numOfNodes = sc.nextInt(); // numOfNodes
			int numOfEdges = sc.nextInt(); // numOfEdges

			Node[] nodes = new Node[numOfNodes];
			Edge[] edges = new Edge[numOfNodes];

			int numQueries = sc.nextInt(); // queries
			int startIndex = sc.nextInt(); // index of starting node

			for (int i = 0; i < numOfEdges; i++) {
				int from = sc.nextInt();
				int to = sc.nextInt();
				int weight = sc.nextInt();

				Edge e = new Edge(to, weight);
				Node node = new Node(from);
				Node toNode = new Node(to);

				nodes[from] = node;
				nodes[to] = toNode;
				nodes[from].adjacencies.add(to);
			}
			compute(nodes[startIndex]);

			for (j = 0; j < numQueries; j++) {
				int result = nodes[sc.nextInt()].shortestDistance;
				if (result == Double.POSITIVE_INFINITY) {
					System.out.println("Impossible");
				} else {
					System.out.println(result);
				}
			}

		} while (!(n == 0 && m == 0 && q == 0 && s == 0));
		



	}

	public static void compute(Node source) {
		source.shortestDistance = 0;
		PriorityQueue<Node> q = new PriorityQueue<>();
		q.add(source);

		while (!q.isEmpty()) {
			Node n = q.poll();
			for (Edge e : n.adjacencies) {
				Node next = e.target;
				double dist = n.shortestDistance + e.weight;
				if (dist < next.shortestDistance) {
					q.remove(next);
					next.shortestDistance = dist;
					next.parent = n;
					q.add(next);
				}
			}
		}
	}
}

//define Node
class Node implements Comparable<Node>{
	
	public final int value;
	public List<Edge> adjacencies = new ArrayList<>();
	public double shortestDistance = Double.POSITIVE_INFINITY;
	public Node parent;


	public Node(int val){
		value = val;
	}


	public String toString(){
		return "" + value;
	}


	public int compareTo(Node other){
		return Double.compare(shortestDistance, other.shortestDistance);
	}


}


//define Edge
class Edge{
	public final Node target;
	public final double weight;
	public Edge(Node targetNode, double weightVal){
		target = targetNode;
		weight = weightVal;
	}
}




