package jesus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ValidaFichero {

	public static void main(String[] args) {
		List<Slice> slices = new ArrayList<Slice>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(//b_small.in//c_medium.in//d_big.in
					"/Users/Jesus/Downloads/c_medium 46313.out"));
			String line = reader.readLine();
			while (line != null) {
				//read first line to take args
				String[] splited = line.split("\\s+");
				try {
					slices.add(new Slice(new Point(Integer.parseInt(splited[1]),Integer.parseInt(splited[0])),
							new Point(Integer.parseInt(splited[3]),Integer.parseInt(splited[2]))));
				} catch(Exception e) {
					//System.out.println("Error en linea");
				}
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
			
			reader = new BufferedReader(new FileReader(//b_small.in//c_medium.in//d_big.in
					"/Users/Jesus/Downloads/c_medium.in"));
			char[][] matriz = null;
			int lineN = 0;
			int rows = 0;
			int row = 0;
			int column = 0;
			int numIngredients=0;
			int maxSize=0;
			line =  reader.readLine();
			while (line != null) {
				//read first line to take args
				if(lineN==0) {
					String[] splited = line.split("\\s+");
					lineN++;
					row =Integer.parseInt(splited[0]);
					column = Integer.parseInt(splited[1]);
					matriz = new char [row][column];	
					numIngredients = Integer.parseInt(splited[2]);
					maxSize = Integer.parseInt(splited[3]);
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
			
			//slices = Utils.returnSliceSolution(slices, numIngredients, maxSize, matriz);
			if(Utils.validSliceSolution(slices, numIngredients, maxSize, matriz)){
				System.out.println("SOLUCION GENERAL VALIDA!!");
				System.out.println("SCORE->"+Utils.evaluateSliceListByAreasTotallyIncluded(slices));
				//Utils.writeFile("/Users/Jesus/Downloads/c_medium.out", slices);
			}else{
				System.out.println("SOLUCION GENERAL NO VALIDA");
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
