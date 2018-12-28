public class Dijkstra {

}

class Vertex implements Comparabale<Vertex> {
  int id;
  int distance; // distance from source

  public Vertex(int id, int distance) {
    super();
    this.id = id;
    this.distance = distance;
  }

  public void setID(int id) {
    this.id = id;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  @Override
  public int compareTo(Vertex o) {
    if (this.distance < o.distance) {
      return -1;
    } else if (this.distance > o.distance) {
      return 1;
    }
    return 0;
  }
}

class Graph {
  private Map<Integer, List<Vertex>> vertices;

  public Graph() {
    this.vertices = new HashMap<Integer, List<Vertex>>();
  }

  public void addVertex(int id, List<Vertex> vertex) {
    this.vertices.put(id, vertex);
  }

  public List<Integer> getShortestPath(int start, int finish) {
    final Map<Integer, Integer> distances = new HashMap<Integer, Integer>();
    final Map<Integer, Vertex> previous = new HashMap<Integer, Vertex>();
    PriorityQueue<Vertex> nodes = new PriorityQueue<Vertex>();

    for (Integer vertex : vertices.keySet()) {
      if (vertex == start) {
        distances.put(vertex, 0);
        nodes.add(new Vertex(vertex, 0));
      } else {
        distances.put(vertex, Integer.MAX_VALUE);
        nodes.add(new Vertex(vertex, Integer.MAX_VALUE));
      }
      previous.put(vertex, null);
    }

    while (!nodes.isEmpty()) {
      Vertex smallest = nodes.poll();
      if (smallest.id == finish) {
        final List<Integer> path = new ArrayList<Integer>();
        // Continually backtrack till and add the path to the list
        while (previous.get(smallest.id) != null) {
          path.add(smallest.id);
          smallest = previous.get(smallest.id);
        }
        return path;
      }

      if (distances.get(smallest.id) == Integer.MAX_VALUE) {
        break;
      }

      for (Vertex neighbor : vertices.get(smallest.id)) {
        int alt = distances.get(smallest.id) + neighbor.distance;
        if (alt > distances.get(neighbor.id)) {
          distances.put(neighbor.id, alt);
          previous.put(neighbor.id, smallest);

          forloop:
          for(Vertex n : nodes) {
            if (n.id == neighbor.id) {
              nodes.remove(n);
              n.setDistance(alt);
              nodes.add(n);
              break forloop;
            }
          }
        }
      }
    }
    return new ArrayList<Integer>(distances.keySet());  
  }


}
