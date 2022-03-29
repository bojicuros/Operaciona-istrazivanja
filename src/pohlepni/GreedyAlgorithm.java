package pohlepni;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import pohlepni.WeightedGraph.Edge;
import pohlepni.WeightedGraph.Vertex;

public class GreedyAlgorithm {

	public static WeightedGraph findMinTotalDomSubgraph(WeightedGraph G) {
		WeightedGraph D = new WeightedGraph();
		D.addVertex(firstNode(G));
		while (!isWTD(G, D)) {
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

	public static Boolean isWTD(WeightedGraph G, WeightedGraph D) { // does node without
		return G.allVertices().size() == nodesAndNeighbors(G, D).size(); // neighbors in D exists
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
		Vertex to = new Vertex(edge.getTo().getLabel(), edge.getTo().getWeight());
		from.addEdge(new Edge(to, edge.getWeight(), D.getNumOfEdges() + ""));
		to.addEdge(new Edge(from, edge.getWeight(), D.getNumOfEdges() + ""));
		D.setNumOfEdges(D.getNumOfEdges() + 1);
		D.addVertex(to);
	}

	public static int evaluate(WeightedGraph G, WeightedGraph D) {
		int sum = 0;
		int sum2 = 0;
		for (Vertex v : D.getVertices()) {
			sum += v.getWeight();
			for (Edge e : v.getEdges())
				sum2 += e.getWeight();
		}
		sum += sum2 / 2;
		double min;
		for (Vertex v : nodesThatArentInD(G, D)) {
			min = Double.MAX_VALUE;
			for (Edge e : v.getEdges()) {
				if (D.getVertices().contains(e.getTo())) {
					if (e.getWeight() < min)
						min = e.getWeight();
				}
			}
			for (Vertex vert : G.getVertices())
				for (Edge e : vert.getEdges())
					if (e.getTo().getLabel().equals(v.getLabel()) && D.getVertices().contains(e.getFrom()))
						if (e.getWeight() < min)
							min = e.getWeight();
			sum += min;
		}
		return sum;
	}

	public static Set<Vertex> nodesThatArentInD(WeightedGraph G, WeightedGraph D) { // nodes from G that arent
		Set<Vertex> rtrn = new HashSet<>(); // of nodes in D
		for (Vertex v : G.getVertices())
			if (D.getVertex(v.getLabel()) == null)
				rtrn.add(v);
		return rtrn;
	}

	public static void main(String[] args) {
		WeightedGraph g = new WeightedGraph();

		// construct vertices
		Vertex v0 = new Vertex("0", 2);
		Vertex v1 = new Vertex("1", 3);
		Vertex v2 = new Vertex("2", 7);
		Vertex v3 = new Vertex("3", 6);
		Vertex v4 = new Vertex("4", 1);
		Vertex v5 = new Vertex("5", 4);

		v0.addEdge(new Edge(v1, 1, "0")); // connect v1 v2
		v1.addEdge(new Edge(v0, 1, "0"));

		v1.addEdge(new Edge(v2, 2, "1")); // connect v2 v3
		v2.addEdge(new Edge(v1, 2, "1"));

		v1.addEdge(new Edge(v3, 3, "2")); // connect v2 v4
		v3.addEdge(new Edge(v1, 3, "2"));

		v3.addEdge(new Edge(v4, 1, "3")); // connect v4 v5
		v4.addEdge(new Edge(v3, 1, "3"));

		v5.addEdge(new Edge(v0, 1, "4")); // connect v4 v5
		v0.addEdge(new Edge(v5, 1, "4"));

		v5.addEdge(new Edge(v4, 4, "5")); // connect v4 v5
		v4.addEdge(new Edge(v5, 4, "5"));

		v0.addEdge(new Edge(v4, 8, "6")); // connect v4 v5
		v4.addEdge(new Edge(v0, 8, "6"));

		g.addVertex(v0);
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);

		WeightedGraph d = findMinTotalDomSubgraph(g);
		System.out.println(d);
		System.out.println("Evaluate: " + evaluate(g, d));

	}

}