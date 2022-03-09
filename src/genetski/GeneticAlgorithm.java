package genetski;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class GeneticAlgorithm {
   private Set<Integer> oznaceni = new LinkedHashSet<Integer>();
   Random rand = new Random();
   
   public void generisiNizOznacenih() {
	   int brOznacenih = rand.nextInt(20);
	   System.out.println("Broj oznacenih: "+brOznacenih);
	   while(oznaceni.size() < brOznacenih) {
		   Integer s = rand.nextInt(20)+1;
		   oznaceni.add(s);
	   }
	   System.out.println(oznaceni);
   }
   public static void main(String[] args) {
	   GeneticAlgorithm ga = new GeneticAlgorithm();
	   ga.generisiNizOznacenih();
   }
}
