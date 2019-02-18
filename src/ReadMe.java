import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadMe {
	public static void main(String[] args) {
	char matriz [][] = null;
	
	BufferedReader reader;
	try {
		reader = new BufferedReader(new FileReader(
				"C:\\Users\\Oleg\\Desktop\\Eclipse J2EE-workspace\\PizzaReader\\c_medium.in"));
		String line = reader.readLine();
		int lineN = 0;
		int rows = 0;
		while (line != null) {
			//read first line to take args
			if(lineN==0) {
				String[] splited = line.split("\\s+");
				lineN++;
				int row =Integer.parseInt(splited[0]);
				int column = Integer.parseInt(splited[1]);
				matriz = new char [row][column];	
			}
			else {
				for (int i = 0; i < line.length(); i++) {
					matriz[rows][i]=line.charAt(i);
				}
				rows++;
			}
			// read next line
			line = reader.readLine();
		}
		//read our matrix
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				System.out.print(matriz[i][j]);
			}
			System.out.print("\n");
		}
		reader.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	  
	  

	
	 
	  

}
