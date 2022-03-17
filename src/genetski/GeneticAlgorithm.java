package genetski;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.*;

public class GeneticAlgorithm {
	public Random rand = new Random();
	private Population population = new Population();
	public static int count = 9;
	public static int vertex_num = 5;
	public int n;

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
			boolean b = rand.nextBoolean();
			if (b) {
				this.fitness = (double) (Math.random() * 100);
			}
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
		n = rand.nextInt(vertex_num);
		System.out.println("N: " + n);
		Collections.shuffle(population.parents);
		for (int i = 0; i < population.parents.size() - 1; i++) {
			crossover2(population.parents.get(i), population.parents.get(i + 1));
		}
		System.out.println("Poslije ukrstanja;");
		for (Individual in : population.children2) {
			System.out.println(in);
			System.out.println();
		}

	}

	public void crossover2(Individual p1, Individual p2) {
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

	}

	public static void main(String[] args) {
		GeneticAlgorithm ga = new GeneticAlgorithm();
		ga.evaluate();
		while (count < 10) {
			/*
			 * ga.population.writeIndividualsGenes();
			 * System.out.println("----------------------------------");
			 */
			ga.selection();
			count++;
			ga.crossover();
			ga.mutation();
			// ga.population.writeIndividualsGenes();
		}
	}
}
