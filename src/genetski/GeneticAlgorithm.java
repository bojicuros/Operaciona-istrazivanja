package genetski;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class GeneticAlgorithm {
	public Random rand = new Random();
	private Population population = new Population();
	double fitness, secondFitness;
	int count = 0;
	public static int vertex_num = 5;

	public class Population {
		int size = 2;
		Individual[] individuals = new Individual[2];

		public Population() {
			individuals[0] = new Individual();
			individuals[0].initialize();
			individuals[1] = new Individual();
			individuals[1].initializeSecondI(individuals[0]); // --> inicijalizujemo dvije pocetne jedinke
		}

		//Funckija za ispis gena (pocetni nizovi 0-1 )
		public void writeIndividualsGenes() {
			for (int i = 0; i < 2; i++) {
				System.out.println(i + ". niz: ");
				for (int j = 0; j < individuals[i].genes.length; j++) {
					System.out.print(individuals[i].genes[j] + " ");
				}
				System.out.println();
			}
		}

	}
    
	//klasa za jedinke
	public class Individual {
		public int fitness;
		public int[] genes = new int[vertex_num];

		public void initialize() {
			for (int i = 0; i < genes.length; i++)
				genes[i] = Math.abs(rand.nextInt() % 2);
		}

		public void initializeSecondI(Individual first) {
			for (int i = 0; i < first.getGenes().length; i++) {
				if (first.getGenes()[i] == 1)
					this.genes[i] = 0;
				else
					this.genes[i] = 1;
			}
		}

		public void calculateF() {
			// izracunavanje fitnes funkcije
		}

		public int[] getGenes() {
			return genes;
		}
	}

	public static void main(String[] args) {
		GeneticAlgorithm ga = new GeneticAlgorithm();
		ga.population.writeIndividualsGenes();
	}
}
