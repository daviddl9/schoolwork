import java.util.*;

public class Main {
    // Simple edge class
    public static void main(String[] args) {
      // Scanner sc = new Scanner(System.in);
      // int numOfNodes = sc.nextInt();
      // int numOfEdges = sc.nextInt();
      //
      // for (int i = 0; i < numOfEdges; i++) {
      //
      // }





    }


    public static class Edge {
        GraphVertex v;  // this edge is connected to
        int weight;     // with weight

        Edge(GraphVertex v, int weight) {
            this.v = v;
            this.weight  = weight;
        }

    }

    // TODO: add your new class here

    // Simple vertex class
    public static class GraphVertex{
        int id = -1;     // id for this vertex
        ArrayList< Edge > edges = new ArrayList<>(); // an arraylist of edges
        boolean isVisited;

        // TODO: add your code here
        // additional variables can come here

        // simple constructor
        GraphVertex(int id) {
            this.id = id;
            this.isVisited = false;
        }

        public void visit() {
          this.isVisited = true;
        }

        public boolean isVisited() {
          return this.isVisited;
        }

        public boolean equals(Object obj) {
          if (obj == this) return true;
          if (obj instanceof GraphVertex) {
            GraphVertex temp = (GraphVertex) obj;
            return temp.id == this.id;
          }
          return false;
        }

    }

    public static void getHops(ArrayList<GraphVertex> vertices, GraphVertex s, GraphVertex t) {
        // TODO: Add your code here.
        int numHops = 0;
        boolean isFirst = true;
        int[] distances = new int[vertices.size()];
        GraphVertex[] edgeTo = new GraphVertex[vertices.size()];
        Arrays.fill(distances, 0);
        Queue<GraphVertex> q = new LinkedList<>();
        q.add(s);
        s.visit();
        System.out.print("[");
        while (!q.isEmpty()) {
          GraphVertex curr = q.poll();
          for (Edge e : curr.edges) {
            if (e.equals(t)) return;
            GraphVertex neighbor = e.v;
            if (!neighbor.isVisited()) {
              edgeTo[neighbor.id] = curr;
              q.add(neighbor);
              neighbor.visit();
              distances[neighbor.id] +=  distances[curr.id] + 1;
            }
          }
        }

        Deque<GraphVertex> queue = new LinkedList<>();
        GraphVertex temp = t;
        while (temp != t) {
          queue.addFirst(temp);
          temp = edgeTo[temp.id];
        }
        queue.addFirst(temp);

        for (int i = 0; i < queue.size() - 1; i++) {
          temp = queue.pollFirst();
          System.out.print(temp.id + ",");
        }
        temp = queue.pollFirst();
        System.out.println(temp.id + "]");
        // Algorithm: Run BFS to find the number of hops
        // TODO: in the end print the number of hops.
    }


    public static void ShortestPathsAndHops(ArrayList<GraphVertex> vertices, GraphVertex s, GraphVertex t) {
        // TODO: add your code here

        // TODO: In the end, print your path in this format: [source, intermediate,... , destination]. For example, shortest path from 0 to 5: [0, 8, 6, 5]
        // This statement might help System.out.println(Arrays.toString(path.toArray())); if your path is a LinkedList, ArrayList, etc.
    }
}
