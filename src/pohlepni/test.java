package pohlepni;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class test {
	public static void main(String[] args) throws IOException {
		File f = new File("t1.txt");

//		File folder = new File(
//				"C:\\Users\\PC\\Documents\\Uros fakultet\\Treca godina\\Prvi semestar\\Operaciona istrazivanja\\Projekat\\WTDP-instance\\Ma");
//		File[] listOfFiles = folder.listFiles();
//		int value = Integer.MAX_VALUE;
//		for (File file : listOfFiles) {
//			final long startTime = System.currentTimeMillis();
//			WeightedGraph g = new WeightedGraph(file.getAbsolutePath());
//			value = GreedyAlgorithm.evaluate(g, GreedyAlgorithm.findMinTotalDomSubgraph(g));
//			final long endTime = System.currentTimeMillis();
//			String s = file.getName() + "	" + value + "	 " + (endTime - startTime) + "\n";
//			Files.write(f.toPath(), s.getBytes(), StandardOpenOption.APPEND);
//		}

//		File folder = new File(
//				"C:\\Users\\PC\\Documents\\Uros fakultet\\Treca godina\\Prvi semestar\\Operaciona istrazivanja\\Projekat\\WTDP-instance\\Ma");
//		File[] listOfFiles = folder.listFiles();
//		int value = Integer.MAX_VALUE;
//		for (File file : listOfFiles) {
//			final long startTime = System.currentTimeMillis();
//			Model.graph = new WeightedGraph(file.getAbsolutePath());
//			value = (int) Model.solve();
//			final long endTime = System.currentTimeMillis();
//			String s = file.getName() + "	" + value + "	 " + (endTime - startTime) + "\n";
//			Files.write(f.toPath(), s.getBytes(), StandardOpenOption.APPEND);
//		}

		File folder = new File(
				"C:\\Users\\PC\\Documents\\Uros fakultet\\Treca godina\\Prvi semestar\\Operaciona istrazivanja\\Projekat\\WTDP-instance\\Ma");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			double sum = 0;
			double time = 0;
			WeightedGraph g = new WeightedGraph(file.getAbsolutePath());
			for (int i = 0; i < 10; i++) {
				final long startTime = System.currentTimeMillis();
				sum += GeneticAlgorithm.solve(g);
				final long endTime = System.currentTimeMillis();
				time += endTime - startTime;
			}
			String s = file.getName() + "	" + (sum / 10) + "	 " + (time / 10) + "\n";
			Files.write(f.toPath(), s.getBytes(), StandardOpenOption.APPEND);
		}

	}
}
