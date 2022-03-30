package pohlepni;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pohlepni.WeightedGraph.Edge;
import pohlepni.WeightedGraph.Vertex;

public class GeneticAlgorithm {
	public Random rand = new Random();
	private Population population;
	public static int vertex_num;
	private static WeightedGraph G;

	public GeneticAlgorithm(WeightedGraph graph) {
		G = graph;
		vertex_num = G.getVertices().size();
		population = new Population();
	}

	public class Population {
		int size = 20; // novi broj
		ArrayList<Individual> parents = new ArrayList<Individual>();
		ArrayList<Individual> children = new ArrayList<Individual>();
		ArrayList<Individual> children2 = new ArrayList<Individual>();

		public Population() {
			int br = 0;
			while (br < 20) {
				parents.add(new Individual());
				br++;
			}
		}

		// Funckija za ispis gena (pocetni nizovi 0-1 )
		public void writeIndividualsGenes() {
			System.out.println("Roditelji: ");
			for (Individual in : parents) {
				System.out.println(in);
				System.out.println();
			}
			if (children.size() != 0) {
				System.out.println("DJECAAAA: ");
				for (Individual in : children) {
					System.out.println(in);
					System.out.println();
				}
			}
		}

		public Individual getFirstChild() {
			return children.get(0);
		}
	}

	// klasa za jedinke
	public class Individual {
		public double fitness;
		public int[] genes;

		public Individual() {
			this.fitness = Double.MAX_VALUE;
			genes = new int[vertex_num];
			this.initialize();
		}

		public void initialize() {
			for (int i = 0; i < genes.length; i++)
				genes[i] = Math.abs(rand.nextInt() % 2);
		}

		public void calculateF() {
			WeightedGraph D = graphFromArray(G, genes);
			if (isWTD(G, D)) {
				WeightedGraph g = GreedyAlgorithm.findMinTotalDomSubgraph(D);
				WeightedGraph d2 = makeMST(D, g);
				this.fitness = GreedyAlgorithm.evaluate(G, d2);
			}
		}

		public Boolean isWTD(WeightedGraph G, WeightedGraph D) {
			if (!isConnected(D))
				return false;
			return GreedyAlgorithm.isWTD(G, D);
		}

		public boolean isConnected(WeightedGraph graph) {

			Map<String, Boolean> visited = new HashMap<>();

			for (Vertex v : graph.getVertices())
				visited.put(v.getLabel(), false);

			if (graph.getVertices().size() == 0)
				return false;
			DFS(graph.getVertices().get(0).getLabel(), graph, visited);

			for (Map.Entry<String, Boolean> entry : visited.entrySet())
				if (!entry.getValue())
					return false;
			return true;
		}

		public void DFS(String source, WeightedGraph graph, Map<String, Boolean> visited) {

			visited.put(source, true);

			for (Vertex v : graph.getVertex(source).getNeighbors()) {
				if (visited.get(v.getLabel()) == false)
					DFS(v.getLabel(), graph, visited);
			}
		}

		private WeightedGraph makeMST(WeightedGraph G, WeightedGraph D) {
			for (Vertex v : G.getVertices()) {
				if (!D.getVertices().contains(v)) {
					double min = Double.MAX_VALUE;
					Edge minimum = null;
					for (Edge e : v.getEdges()) {
						if (D.getVertices().contains(e.getTo())) {
							if (e.getWeight() < min) {
								min = e.getWeight();
								minimum = e;
							}
						}
					}

					for (Vertex vert : G.getVertices())
						for (Edge e : vert.getEdges())
							if (e.getTo().getLabel().equals(v.getLabel()))
								if (e.getWeight() < min) {
									min = e.getWeight();
									minimum = e;
								}
					Vertex from = minimum.getFrom();
					Vertex to = minimum.getTo();
					if (!contains(D, from))
						D.addVertex(new Vertex(from.getLabel(), from.getWeight()));
					from = D.getVertex(from.getLabel());
					if (!contains(D, to))
						D.addVertex(new Vertex(to.getLabel(), to.getWeight()));
					to = D.getVertex(to.getLabel());

					from.addEdge(new Edge(to, minimum.getWeight(), minimum.getLabel()));
					to.addEdge(new Edge(from, minimum.getWeight(), minimum.getLabel()));
				}
			}

			if (!isConnected(D)) {
				for (Vertex v : G.getVertices()) {
					double min = Double.MAX_VALUE;
					Edge minimum = null;
					for (Edge e : v.getEdges()) {
						if (D.getVertices().contains(e.getFrom()) && D.getVertices().contains(e.getTo())
								&& !contains(D, e)) {
							if (e.getWeight() < min) {
								min = e.getWeight();
								minimum = e;
							}
						}
					}
					if (minimum != null) {
						Vertex from = D.getVertex(minimum.getFrom().getLabel());
						Vertex to = D.getVertex(minimum.getTo().getLabel());
						from.addEdge(new Edge(to, minimum.getWeight(), minimum.getLabel()));
						to.addEdge(new Edge(from, minimum.getWeight(), minimum.getLabel()));
						if (isConnected(D))
							return D;
					}
				}
			}
			return D;
		}

		private boolean contains(WeightedGraph G, Vertex n) {
			for (Vertex v : G.getVertices())
				if (v.equals(n))
					return true;
			return false;
		}

		private boolean contains(WeightedGraph G, Edge edge) {
			for (Vertex v : G.getVertices())
				for (Edge e : v.getEdges())
					if (e.equals(edge))
						return true;
			return false;
		}

		private WeightedGraph graphFromArray(WeightedGraph G, int[] gen) {
			WeightedGraph D = new WeightedGraph();
			for (int i = 0; i < gen.length; i++)
				if (gen[i] == 1)
					D.addVertex(new Vertex(i + "", G.getVertex(i + "").getWeight()));
			for (Vertex v : D.getVertices()) {
				for (Edge e : G.getVertex(v.getLabel()).getEdges()) {
					Vertex v2 = D.getVertex(e.getTo().getLabel());
					if (v2 != null)
						v.addEdge(new Edge(v2, e.getWeight(), e.getLabel()));
				}
			}
			return D;
		}

		public int[] getGenes() {
			return genes;
		}

		public String toString() {
			String rez = " ";
			for (int i = 0; i < genes.length; i++)
				rez += genes[i] + " ";
			rez += " fitnes: " + fitness;
			return rez;
		}

	}

	public void evaluate() {
		for (Individual in : population.children) {
			in.calculateF();
		}
		for (Individual in : population.parents) {
			in.calculateF();
		}
	}

	public void selection() {
		Collections.sort(population.children, new Comparator<Individual>() {
			@Override
			public int compare(Individual a, Individual b) {
				return (int) (a.fitness - b.fitness);
			}
		});

		if (population.children.size() == 0) {
			Collections.sort(population.parents, new Comparator<Individual>() {
				@Override
				public int compare(Individual a, Individual b) {
					return (int) (a.fitness - b.fitness);
				}
			});

			int i = 0;
			while (i < 5) {
				population.children.add(population.parents.get(0));
				population.parents.remove(0);
				i++;
			}
		} else {
			population.parents.clear();
			int i = 5;
			while (population.children.size() > 5) {
				if (i < 20) {
					population.parents.add(population.children.get(5));
					i++;
				}
				population.children.remove(5);
			}
		}

	}

	public void crossover() {
		population.children2.clear();
		int n = rand.nextInt(vertex_num);
		Collections.shuffle(population.parents);
		for (int i = 0; i < population.parents.size() - 1; i++) {
			crossoverParents(population.parents.get(i), population.parents.get(i + 1), n);
		}
//		System.out.println("Poslije ukrstanja;");
//		for (Individual in : population.children2) {
//			System.out.println(in);
//			System.out.println();
//		}

	}

	public void crossoverParents(Individual p1, Individual p2, int n) {
		int i = 0;
		Individual c1 = new Individual();
		Individual c2 = new Individual();
		while (i < n) {
			c1.genes[i] = p1.genes[i];
			c2.genes[i] = p2.genes[i];
			i++;
		}
		while (n < vertex_num) {
			c1.genes[n] = p2.genes[n];
			c2.genes[n] = p1.genes[n];
			n++;
		}
		population.children2.add(c1);
		population.children2.add(c2);
	}

	public void mutation() {
		double pm = (double) (Math.random() * ((double) 1) / 2) + ((double) 1 / vertex_num); // vjerovatnoca ga ce gen
																								// mutirati
		for (Individual c : population.children2) {
			for (int i = 0; i < c.genes.length; i++) {
				int pg = rand.nextInt(100);
				if (pg >= 0 && pg <= (int) (pm * 100)) { // --> izvrsava se mutiranje gena sa vjerovatnocom pm
					if (c.genes[i] == 0)
						c.genes[i] = 1;
					else
						c.genes[i] = 0;
				}
			}
		}
		population.children.addAll(population.children2);
		this.evaluate();
	}

	public static double solve(WeightedGraph g) {
		GeneticAlgorithm ga = new GeneticAlgorithm(g);
		ga.evaluate();
		int count = 0;
		double min_fitness = Double.MAX_VALUE;
		while (count < 10) {
			
//			ga.population.writeIndividualsGenes();
//			System.out.println("----------------------------------");
			ga.selection();
//			ga.population.writeIndividualsGenes();
			ga.crossover();
			ga.mutation();
			if (min_fitness > ga.population.getFirstChild().fitness) {
				count = 0;
				min_fitness = ga.population.getFirstChild().fitness;
			} else
				count++;
		}

//		System.out.println("Rjesenje: " + ga.population.getFirstChild());
		return min_fitness;
	}

	public static void main(String[] args) {

//		WeightedGraph G = new WeightedGraph();
//
//		// construct vertices
//		Vertex v0 = new Vertex("0", 2);
//		Vertex v1 = new Vertex("1", 3);
//		Vertex v2 = new Vertex("2", 7);
//		Vertex v3 = new Vertex("3", 6);
//		Vertex v4 = new Vertex("4", 1);
//		Vertex v5 = new Vertex("5", 4);
//
//		v0.addEdge(new Edge(v1, 1, "0")); // connect v1 v2
//		v1.addEdge(new Edge(v0, 1, "0"));
//
//		v1.addEdge(new Edge(v2, 2, "1")); // connect v2 v3
//		v2.addEdge(new Edge(v1, 2, "1"));
//
//		v1.addEdge(new Edge(v3, 3, "2")); // connect v2 v4
//		v3.addEdge(new Edge(v1, 3, "2"));
//
//		v3.addEdge(new Edge(v4, 1, "3")); // connect v4 v5
//		v4.addEdge(new Edge(v3, 1, "3"));
//
//		v5.addEdge(new Edge(v0, 1, "4")); // connect v4 v5
//		v0.addEdge(new Edge(v5, 1, "4"));
//
//		v5.addEdge(new Edge(v4, 4, "5")); // connect v4 v5
//		v4.addEdge(new Edge(v5, 4, "5"));
//
//		v0.addEdge(new Edge(v4, 8, "6")); // connect v4 v5
//		v4.addEdge(new Edge(v0, 8, "6"));
//
//		G.addVertex(v0);
//		G.addVertex(v1);
//		G.addVertex(v2);
//		G.addVertex(v3);
//		G.addVertex(v4);
//		G.addVertex(v5);
//
		WeightedGraph G = new WeightedGraph("MA-20-0.2-5-5-1.wtdp");
		System.out.println("rjesenje "+solve(G));
	}
}