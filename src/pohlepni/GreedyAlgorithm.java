package pohlepni;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import pohlepni.WeightedGraph.Edge;
import pohlepni.WeightedGraph.Vertex;

public class GreedyAlgorithm {

	public static WeightedGraph greedy(WeightedGraph G) {
		WeightedGraph D = new WeightedGraph();
		D.addVertex(firstNode(G));
		while (nodeWithoutNeighborsInD(G, D)) {
			addNode(G, D);
		}
		return D;
	}

	private static Vertex firstNode(WeightedGraph G) {
		Map<Vertex, Double> map = new HashMap<>();
		for (Vertex v : G.getVertices()) {
			Vertex temp = v;
			map.put(temp, ((double) temp.getWeight()) / ((double) temp.getEdges().size()));
		}
		Vertex bestPick = null;
		Double smallest = Double.MAX_VALUE;
		for (Entry<Vertex, Double> entry : map.entrySet()) {
			if (entry.getValue() < smallest) {
				smallest = entry.getValue();
				bestPick = entry.getKey();
			} else if (entry.getValue().equals(smallest)) {
				if (entry.getKey().getEdges().size() > bestPick.getEdges().size()) {
					smallest = entry.getValue();
					bestPick = entry.getKey();
				}
			}
		}
		return new Vertex(bestPick.getLabel(), bestPick.getWeight());
	}

	private static Boolean nodeWithoutNeighborsInD(WeightedGraph G, WeightedGraph D) { // does node without
		return G.allVertices().size() != nodesAndNeighbors(G, D).size(); // neighbors in D exists
	}

	private static Set<Vertex> nodesAndNeighbors(WeightedGraph G, WeightedGraph D) { // nodes from D and neighbor
		Set<Vertex> rtrn = D.allVertices(); // of nodes in D
		for (Vertex vrt : D.getVertices()) {
			Vertex temp = G.getVertex(vrt.getLabel());
			for (Vertex v : temp.getNeighbors())
				if (D.getVertex(v.getLabel()) == null)
					rtrn.add(v);
		}
		return rtrn;
	}

	public static void addNode(WeightedGraph G, WeightedGraph D) {
		Vertex from = null;
		Edge edge = null;
		double smallest = Double.MAX_VALUE;
		for (Vertex v : D.allVertices()) {
			for (Edge e : G.getVertex(v.getLabel()).getEdges()) {
				if (D.getVertex(e.getTo().getLabel()) == null) {
					double value = ((double) e.getTo().getWeight()) / e.getTo().getEdges().size() + e.getWeight();
					if (smallest > value) {
						smallest = value;
						from = v;
						edge = e;
					}
				}

			}
		}
		Vertex to = new Vertex(edge.getTo().getLabel(), edge.getWeight());
		from.addEdge(new Edge(to, edge.getWeight()));
		to.addEdge(new Edge(from, edge.getWeight()));
		D.addVertex(to);
	}

	public static void main(String[] args) {
		WeightedGraph graph = new WeightedGraph();

		// construct vertices
		Vertex v1 = new Vertex("1", 2);
		Vertex v2 = new Vertex("2", 3);
		Vertex v3 = new Vertex("3", 7);
		Vertex v4 = new Vertex("4", 6);
		Vertex v5 = new Vertex("5", 1);

		v1.addEdge(new Edge(v2, 1)); // connect v1 v2
		v2.addEdge(new Edge(v1, 1));

		v2.addEdge(new Edge(v3, 2)); // connect v2 v3
		v3.addEdge(new Edge(v2, 2));

		v2.addEdge(new Edge(v4, 3)); // connect v2 v4
		v4.addEdge(new Edge(v2, 3));

		v4.addEdge(new Edge(v5, 1)); // connect v4 v5
		v5.addEdge(new Edge(v4, 1));

		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);
		graph.addVertex(v4);
		graph.addVertex(v5);

		System.out.println(greedy(graph));

	}

}