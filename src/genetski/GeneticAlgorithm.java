package genetski;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class GeneticAlgorithm {
   private Set<Integer> oznaceni = new LinkedHashSet<Integer>();
   private int[] oznaceni_niz; //niz bitova (1 oznaceno, 0 nije)
   private int br_cvoreva = 20;
   Random rand = new Random();
   
  public void generisiOznacene() {
	   int brOznacenih = rand.nextInt(20);
	   System.out.println("Broj oznacenih: "+brOznacenih);
	   while(oznaceni.size() < brOznacenih) {
		   Integer s = rand.nextInt(20);
		   oznaceni.add(s);
	   }
	  // System.out.println(oznaceni);
	   generisiNiz(oznaceni);
   }
   
  public void generisiNiz(Set<Integer> oznaceni) {
	  oznaceni_niz = new int[br_cvoreva];
	  for(int i = 0; i<br_cvoreva; i++) {
		  if(oznaceni.contains(i)) {
			  oznaceni_niz[i] = 1;
		  }
		  else {
			  oznaceni_niz[i] = 0;
		  }
	  }
	 /* for(Integer i:oznaceni_niz)
		  System.out.println(i);*/
  }

   
/*   public static void main(String[] args) {
	   GeneticAlgorithm ga = new GeneticAlgorithm();
	   ga.generisiOznacene();
   }*/
}