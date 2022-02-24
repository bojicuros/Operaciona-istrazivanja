package urediInstance;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Replace {
    public static void main(String[] args) throws IOException {
        File folder = new File("C:\\Users\\PC\\Documents\\Uros fakultet\\Treca godina\\Prvi semestar\\Operaciona istrazivanja\\Projekat\\WTDP-instance\\Ma");
        File[] listOfFiles = folder.listFiles();
        for(File file : listOfFiles) {
        	replaceText(file.getAbsolutePath());
        }
    }


    	public static void replaceText(String fileName) throws IOException {
    	    Path filePath = Paths.get(fileName);
    	    List<String> lines = Files.readAllLines(filePath);
    	    lines.set(0, edit(lines.get(0)));
    	    Files.write(filePath, lines);
    	}
    	
    	private static String edit(String line) {
    		String rtr ="";
    		int brojac=0;
    		for(int i=0;i<line.length();i++) {
    			if(line.charAt(i)==' ')
    				brojac++;
    			if(brojac==2)
    				break;
    			rtr+=line.charAt(i);
    		}
    		return rtr;
    	}

      
}

