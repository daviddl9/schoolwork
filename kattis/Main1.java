import java.util.*;

public class Main1 {
    // Simple edge class
    public static void main(String[] args) { }


    public static class Edge {
        GraphVertex v;  // this edge is connected to
        int weight;     // with weight

        Edge(GraphVertex v, int weight) {
            this.v = v;
            this.weight  = weight;
        }
    }

    // Simple vertex class
    public static class GraphVertex{
        int id = -1;     // id for this vertex
        ArrayList< Edge > edges = new ArrayList<>(); // an arraylist of edges
        boolean isVisited;
        GraphVertex parent;

        // simple constructor
        GraphVertex(int id) {
            this.id = id;
            this.isVisited = false;
            this.parent = null;
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

        Queue<GraphVertex> q = new LinkedList<>();
        q.add(s);
        s.visit();

        GraphVertex res = s;
        while (!q.isEmpty()) {
          GraphVertex curr = q.poll();

          if (curr.equals(t)) {
            res = t;
            break;
          }

          for (Edge e : curr.edges) {
            GraphVertex next = e.v;

            if (next.isVisited()) {
              continue;
            } else {
              next.visit();
              next.parent = curr;
              q.add(next);
            }
          }
        }

        Stack<Integer> out = new Stack<>();
        while (!res.equals(s)) {
          out.push(res.id);
          res = res.parent;
        }

        System.out.print("[");
        while(!out.empty()) {

          if (out.size() == 1)
            System.out.print(out.peek() + "]");
          else
            System.out.print(out.peek() + ",");

          out.pop();
        }
    }


    public static void ShortestPathsAndHops(ArrayList<GraphVertex> vertices, GraphVertex s, GraphVertex t) {
        // TODO: add your code here

        // TODO: In the end, print your path in this format: [source, intermediate,... , destination]. For example, shortest path from 0 to 5: [0, 8, 6, 5]
        // This statement might help System.out.println(Arrays.toString(path.toArray())); if your path is a LinkedList, ArrayList, etc.
    }
}
