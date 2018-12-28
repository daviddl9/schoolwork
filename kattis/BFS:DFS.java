class Graph {
	private int V; // No.of vertices
	private LinkedList<Integer> adj[]; //Adjacency list
}

void BFS(int s) {
	boolean visited = new boolean[V];

	LinkedList<Integer> queue = new LinkedList<>();
	visited[s] = true;
	queue.add(s);
	
}

void BFS(int s) {
	boolean visited = new boolean[V]; // V = no. of vertex

	LinkedList<Integer> queue = new LinkedList<>();

	visited[s] = true;
	queue.add(s);

	while (queue.size() != 0) {
		s = queue.poll();
		Iterator<Integer> i = adj[s].listIterator();
		while (i.hasNext()) {
			int n = i.next();
			if (!visited[n]) {
				visited[n] = true;
				queue.add(n);
			}
		}
	}
}

void DFS(int v) {
	boolean visited[] = new boolean[V];

	DFSUtil(v, visited);
}

void DFSUtil(int v, boolean visited[]) {
	visited[v] = true;
	Iterator<Integer> i = adj[v].listIterator();
	while (i.hasNext()) {
		int n = i.next();
		if (!visited[n]) DFSUtil(n, visited);
	}
}

void DFSIterative(int s) {
	boolean visited[] = new boolean[V];

	Stack<Integer> stack = new Stack<>();

	stach.push(s);

	while (!stack.empty()) {
		s = stack.peek();
		stack.pop();

		// account for similar vertices
		if (!visited[s]) {
			visited[s] = true;
		}

		Iterator<Integer> itr = adj[s].iterator();
		while (itr.hasNext()) {
			int next = itr.next();
			if (!visited[next]) stack.push(next);
		}
	}
}