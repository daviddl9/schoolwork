import java.util.*;

public class MST {
  public static void main(String args[]) {
    int numOfVertices;
    int numOfEdges;
    boolean isPossible;

    Scanner sc = new Scanner(System.in);

    while (true) {
      numOfVertices = sc.nextInt();
      numOfEdges = sc.nextInt();

      Node[] nodes = new Node[numOfVertices];
      Edge[] edges = new Edge[numOfEdges];
      List<Edge> MST = new ArrayList<>();

      if (numOfEdges == 0 && numOfVertices == 0) {
        break;
      }

      if (numOfEdges == 0) {
        System.out.println("Impossible");
        continue;
      }

      for (int i = 0; i < numOfVertices; i++) {
        nodes[i] = new Node();
      }

      for (int i = 0; i < numOfEdges; i++) {
        int from = sc.nextInt();
        int to = sc.nextInt();
        int weight = sc.nextInt();

        Edge e = new Edge(weight, from, to);
        nodes[from].neighbors.add(e);
        nodes[to].neighbors.add(e);

        edges[i] = e;
      }

      isPossible = true;
      int cost = 0;
      boolean[] visited = new boolean[numOfVertices];

      PriorityQueue<Edge> q = new PriorityQueue<>();
      q.add(edges[0]);
      int start = edges[0].from;
      
      visited[start] = true;

      for (Edge e : nodes[start].neighbors) {
        q.add(e);
      }

      while (!q.isEmpty()) {
        Edge e = q.poll();
        if (visited[e.end]) {
          continue;
        }

        visited[e.end] = true;
        cost += e.weight;
        MST.add(e);

        for (Edge edge : nodes[e.end].neighbors) {
          q.add(edge);
        }
      }

      for (int i = 0; i < visited.length; i++) {
        if (!visited[i]) {
          System.out.println("Impossible");
          isPossible = false;
        }
      }

      if (isPossible) {
        System.out.println(cost);
        for (Edge e : MST) {
          System.out.println(e);
        }
      }

    }
  }
}

class Node {
  List<Edge> neighbors;

  public Node() {
    this.neighbors = new ArrayList<>();
  }
} 

class Edge implements Comparable<Edge> {
  int weight;
  int end;
  int from;

  public Edge(int weight,int from, int end) {
    this.end = end;
    this.weight = weight;
    this.from = from;
  }

  public int compareTo(Edge other) {
    return this.weight - other.weight;
  } 

  public String toString() {
    return this.from + " " + this.end;
  }

}