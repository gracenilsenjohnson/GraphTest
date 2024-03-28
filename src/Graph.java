import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph {

	ArrayList<ArrayList<Vertex>> adjacencyMap;
	int vertexCount;
	public Graph(List<Edge> edges, boolean negate)
	{
		adjacencyMap=new ArrayList<>();
		Set<Integer> vertices = new HashSet<>();
		for (Edge e : edges) {
			vertices.add(e.source);
			vertices.add(e.destination);
		}
		vertexCount=vertices.size();
		for (int i = 0; i < vertices.size(); i++)
		{
			adjacencyMap.add(i, new ArrayList<>());
		}
		if(!negate)
		{
			for (Edge e : edges)
			{
				adjacencyMap.get(e.source).add(new Vertex(e.destination, e.weight));
			}
		}
		else {
			for (Edge e : edges)
			{
				adjacencyMap.get(e.source).add(new Vertex(e.destination, e.weight*-1));
			}
		}
	}
	public Graph(List<Edge> edges)
	{
		new Graph(edges, false);
	}
	void printAdjacencyList()
	{
		for (int i = 0; i < adjacencyMap.size(); i++) {
			System.out.print(i + " -> ");
			for (Vertex v : adjacencyMap.get(i)) {
				System.out.print("(" + v.value + ", " + v.weight + ") ");
			}
			System.out.println();
		}
	}

	public static List<Integer> topologicalSort(Graph graph) {
		List<Integer> sortedList = new ArrayList<>();
		int numVertices = graph.vertexCount;
		int[] incoming = new int[numVertices];

		// Calculate incoming edges count for each vertex
		for (int i = 0; i < numVertices; i++) {
			for (Vertex neighbor : graph.adjacencyMap.get(i)) {
				incoming[neighbor.value]++;
			}
		}

		// Find all vertices with no incoming edges
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < numVertices; i++) {
			if (incoming[i] == 0) {
				queue.add(i);
			}
		}

		// Perform sorting
		while (!queue.isEmpty()) {
			int current = queue.poll();
			sortedList.add(current);

			// Remove outgoing edges from current vertex and update incoming edges count of adjacent vertices
			for (Vertex neighbor : graph.adjacencyMap.get(current)) {
				incoming[neighbor.value]--;
				if (incoming[neighbor.value] == 0) {
					queue.add(neighbor.value);
				}
			}
		}

		// Check for cycles
		for (int degree : incoming) {
			if (degree != 0) {
				// Graph has at least one cycle if the number of incoming edges is non-zero after pruning
				// since it would get subtracted a second time once the cycle makes it back
				return null;
			}
		}

		return sortedList;
	}
	/* This starts with an empty array of vertexes and the distance to reach them from the source.
	 * It then fills in, in the topological sorted order, the distance from the source vertex to
	 * all vertices that the source vertex has an edge to. For each subsequent vertex, it then
	 * fills in what those can see, and if the distance to any already-visted vertex is found
	 * to be lesser than what's stored, replaces it. That ensures that if a vertex has two or more
	 * paths you can reach it by, we're calculating the shorter of the two distances to it.
	 * Since it is acyclic, by the time we start using a vertex as our new source, and since
	 * it's topologically sorted, we're guaranteed that there is no shorter path to that vertex
	 * from our source. We then proceed on with each subsequent vertex and it's edges.
	 *
	 * (Notably, if you run this function on the negation of a graph, it will find the
	 * /longest/ path, rather than the shortest one, since it will have the most-negative
	 * distance, and therefore be 'shortest')
	 */
	public Map<Integer, Integer> shortestPathsDistances(int source) {
		List<Integer> topologicallySorted = topologicalSort(this);
		Map<Integer, Integer> distances = new HashMap<>();
		for (int vertex : topologicallySorted) {
			distances.put(vertex, Integer.MAX_VALUE);
		}
		distances.put(source, 0);

		for (int current : topologicallySorted) {
			if (distances.get(current) != Integer.MAX_VALUE) {
				for (Vertex neighbor : this.adjacencyMap.get(current)) {
					int newDistance = distances.get(current) + neighbor.weight;
					if (newDistance < distances.get(neighbor.value)) {
						distances.put(neighbor.value, newDistance);
					}
				}
			}
		}
		return distances;
	}
	public List<Integer> shortestPath(int source, int destination) {
	    List<Integer> topologicallySorted = topologicalSort(this);
	    Map<Integer, Integer> distances = new HashMap<>();
	    Map<Integer, Integer> predecessors = new HashMap<>(); // Store predecessors to reconstruct the path
	    for (int vertex : topologicallySorted) {
	        distances.put(vertex, Integer.MAX_VALUE);
	        predecessors.put(vertex, -1); // Initialize predecessors as -1
	    }
	    distances.put(source, 0);

	    for (int current : topologicallySorted) {
	        if (current == destination) {
	           break; // Stop once the shortest path to the destination vertex is found
	        }
	        if (distances.get(current) != Integer.MAX_VALUE) {
	            for (Vertex neighbor : this.adjacencyMap.get(current)) {
	                int newDistance = distances.get(current) + neighbor.weight;
	                if (newDistance < distances.get(neighbor.value)) {
	                    distances.put(neighbor.value, newDistance);
	                    predecessors.put(neighbor.value, current); // Update predecessor
	                }
	            }
	        }
	    }

	    // Reconstruct the shortest path
	    List<Integer> shortestPath = new ArrayList<>();
	    int currentVertex = destination;
	    while (currentVertex != -1) {
	        shortestPath.add(currentVertex);
	        currentVertex = predecessors.get(currentVertex);
	    }
	    Collections.reverse(shortestPath);

	    return shortestPath;
	}

	public static int getFurthestDestination(Map<Integer, Integer> longestResults)
	{
	        if (longestResults.isEmpty()) {
	            throw new IllegalArgumentException("Map is empty");
	        }

	        int maxKey = Integer.MAX_VALUE;
	        int maxValue = Integer.MAX_VALUE;

	        for (Map.Entry<Integer, Integer> entry : longestResults.entrySet()) {
	            if (entry.getValue() < maxValue) {
	                maxKey = entry.getKey();
	                maxValue = entry.getValue();
	            }
	        }

	        return maxKey;
	    }
	}

