class Vertex {
  int id;
  public Vertex(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj instanceof Vertex) {
      Vertex temp = (Vertex) obj;
      return temp.id == this.id;
    }
    return false;
  }
}

class Edge {
  int id;
  Vertex source;
  Vertex destination;
  int weight;

  public Edge(int id, Vertex source, Vertex destination, int weight) {
    this.id = id;
    this.source = source;
    this.destination = destination;
    this.weight = weight;
  }

  public String getID() {
    return "" + this.id;
  }
}

class Graph {
  List<Vertex> vertices;
  List<Edge> edges;

  Graph(List<Vertex> vertices, List<Edge> edges) {
    this.vertices = vertices;
    this.edges = edges;
  }
}

class BellmanFord {
  List<Vertex> vertices;
  List<Edge> edges;
  boolean hasNegative;
  int d[]; // d[v] stores the distance from the start to v.

  BellmanFord(List<Vertex> vertices, List<Edge> edges) {
    this.vertices = vertices;
    this.edges = edges;
    this.d = new int[vertices.size()];
  }

  public void execute(Vertex start) {
    int s = vertices.indexOf(start);
    Arrays.fill(d, Integer.MAX_VALUE);
    d[s] = 0;

    // Relaxation
    for (int i = 0; i < vertices.size(); i++) {
      for (Edge e : edges) {
        Vertex src = edge.source;
        Vertex dst = edge.destination;
        if (d[vertices.indexOf(dst)] > d[vertices.indexOf(src)] + edge.weight) {
          d[vertices.indexOf(dst)] = d[vertices.indexOf(src)] + edge.weight;
        }
      }
    }

    //Check for negative edges
    for (Edge e : edges) {
      Vertex src = edge.source;
      Vertex dst = edge.destination;
      if (d[vertices.indexOf(dst)] > d[vertices.indexOf(src)] + edge.weight) {
        hasNegative = true;
      }
    }

  }




}
