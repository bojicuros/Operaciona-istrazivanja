package pohlepni;

import java.util.ArrayList;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import pohlepni.WeightedGraph.Edge;
import pohlepni.WeightedGraph.Vertex;

public class Model {

	public static WeightedGraph graph;

	public static double solve() {

		// variables
		int v = graph.allVertices().size();
		int e = graph.getNumOfEdges();
		double objec = Double.MAX_VALUE;
		int[] weightOfNodes = new int[v]; // w(v)
		int[] weightOfEdges = new int[e]; // w(e)
		for (Vertex vertex : graph.allVertices()) {
			int i = Integer.parseInt(vertex.getLabel());
			weightOfNodes[i] = vertex.getWeight();
			for (Edge edge : vertex.getEdges()) {
				int j = Integer.parseInt(edge.getLabel());
				weightOfEdges[j] = edge.getWeight();
			}
		}

		int[][] fromTo = new int[e][2];
		for (int i = 0; i < e; i++) {
			Edge edge = graph.getEdge("" + i);
			Vertex u = edge.getFrom();
			Vertex v1 = edge.getTo();
			fromTo[i][0] = Integer.parseInt(u.getLabel());
			fromTo[i][1] = Integer.parseInt(v1.getLabel());
		}

		// model

		try {
			@SuppressWarnings("resource")
			IloCplex cplex = new IloCplex();

			IloNumVar[] x = new IloNumVar[v];
			for (int i = 0; i < v; i++)
				x[i] = cplex.boolVar();
			IloNumVar[] y = new IloNumVar[e];
			IloNumVar[] z = new IloNumVar[e];
			for (int j = 0; j < e; j++) {
				y[j] = cplex.boolVar();
				z[j] = cplex.boolVar();
			}

			// objective

			IloLinearNumExpr objective = cplex.linearNumExpr();
			for (int i = 0; i < v; i++)
				objective.addTerm(weightOfNodes[i], x[i]);

			for (int j = 0; j < e; j++)
				objective.addTerm(weightOfEdges[j], y[j]);

			cplex.addMinimize(objective);

			// constrains

			// (2) i (8)
			for (int i = 0; i < v; i++) {
				// (2)
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for (Vertex u : graph.getVertex(i + "").getNeighbors()) {
					int j = Integer.parseInt(u.getLabel());
					expr.addTerm(1, x[j]);
				}
				cplex.addGe(expr, 1);

				// (8)
				IloLinearNumExpr expr2 = cplex.linearNumExpr();
				for (Edge edge : graph.getVertex(i + "").getEdges()) {
					int j = Integer.parseInt(edge.getLabel());
					expr2.addTerm(1, y[j]);
				}
				cplex.addGe(cplex.sum(x[i], expr2), 1);
			}

			// (3) - (7)
			for (int i = 0; i < e; i++) {
				// xu xv
				cplex.addGe(cplex.sum(x[fromTo[i][0]], x[fromTo[i][1]]), y[i]);
				cplex.addGe(x[fromTo[i][0]], z[i]);
				cplex.addGe(x[fromTo[i][1]], z[i]);
				cplex.addGe(z[i], cplex.sum(cplex.sum(x[fromTo[i][0]], x[fromTo[i][1]]), -1));
				cplex.addGe(y[i], z[i]);
			}

			if (cplex.solve()) {
				objec = cplex.getObjValue();
			
			}
			ArrayList<Integer> selectedNodes = new ArrayList<>();
			ArrayList<Integer> selectedEdges = new ArrayList<>();
			for (int i = 0; i < x.length; i++) {
				if (cplex.getValue(x[i]) == 1)
					selectedNodes.add(i);
			}
			for (int i = 0; i < y.length; i++) {
				if (cplex.getValue(y[i]) == 1)
					selectedEdges.add(i);
			}

			System.out.println(selectedNodes);
			System.out.println(selectedEdges);

		} catch (IloException e1) {
			e1.printStackTrace();
		}
		return objec;
	}

	public static void main(String[] args) {
		// = new WeightedGraph("MA-20-0.2-5-5-1.wtdp");
		graph = new WeightedGraph();

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

		graph.addVertex(v0);
		graph.addVertex(v1);
		graph.addVertex(v2);
		graph.addVertex(v3);
		graph.addVertex(v4);
		graph.addVertex(v5);
		System.out.println(graph);
		graph.setNumOfEdges(7);
		double rjesenje = solve();
		System.out.println("Rjesenje: "+rjesenje);
	}

}
