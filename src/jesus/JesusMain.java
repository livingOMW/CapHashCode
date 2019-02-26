package jesus;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JesusMain {
	public static void main(String[] args) {
		char matriz [][] = null;
		int numIngredients=0;
		int maxSize=0;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(//b_small.in//c_medium.in//d_big.in
					"/Users/Jesus/Downloads/c_medium.in"));
			String line = reader.readLine();
			int lineN = 0;
			int rows = 0;
			int row = 0;
			int column = 0;
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
			//read our matrix
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < matriz[i].length; j++) {
					System.out.print(matriz[i][j]);
				}
				System.out.print("\n");
			}
			reader.close();
			//Transformamos matriz
			//Para la pizza pequeña
			/*
			char[][] matrizT = new char[row][column];
			for(int i=0;i<row;i++) {
				matrizT [i]= matriz[row-i-1];
			}
			reader.close();
			Slice bigSlice =  new Slice(new Point(0,0),new Point(column-1,row-1));
			List<Slice> slices = new ArrayList<Slice>();
			List<Slice> solution = new ArrayList<Slice>();
			solution = Utils.bestSliceSolutionForPortion(bigSlice, slices, solution, numIngredients, maxSize, matrizT, row*column);
			//Transformamos las porciones solucion
			//Para la pizza pequeña
			List<Slice> solutionT = new ArrayList<Slice>();
			for(Slice s: solution) {
				solutionT.add(new Slice(new Point(s.getTopLeft().getX(),row-1-s.getBottomRight().getY()),
						new Point(s.getBottomRight().getX(),row-1-s.getTopLeft().getY())));
			}
			System.out.println(solutionT);
			System.out.println("Cuyo score es:"+ Utils.evaluateSliceList(solution, bigSlice));
			
			if(Utils.validSliceSolution(solutionT, numIngredients, matriz)){
				Utils.writeFile("/Users/Jesus/Downloads/d_small.out", solutionT);
			}
			*/
			
			//Para la pizza mediana
			int x = 50;
			//Acumulador de slices
			List<Slice>[][] listaAcumuladora = new ArrayList[column/x][row/x];
			//Pemisibidad cuando se atasca probando
			/*int [][] help = new int [column/x][row/x];
			for(int i = 0; i<row/x; i++) {
				for(int j = 0; j<column/x;j++) {
					help[j][i]=0;
				}
			}
			help[21][9]= 20;*/
			//Fraccionamos en una cuadrícula la pizza en trozos de lado x
			for(int i = 0; i<row/x; i++) {
				for(int j = 0; j<column/x;j++) {
					Slice bigSlice =  new Slice(new Point(j*x,i*x),new Point((j+1)*x-1,(i+1)*x-1));
					Slice blankSlice = new Slice(new Point(j*x,i*x), new Point(j*x,i*x));
					List<Slice> slices = new ArrayList<Slice>();
					List<Slice> solution = new ArrayList<Slice>();
					//Se rellena con los Slices que tienen intersección con el proximo bigSlice a tratar
					List<Slice> existingSolution = new ArrayList<Slice>();
					List<Slice> bestSolutionFound = new ArrayList<Slice>();

					if(i>0) {
						existingSolution.addAll(listaAcumuladora[j][i-1]);
					}
					if(j>0) {
						existingSolution.addAll(listaAcumuladora[j-1][i]);
					}
					if(i>0 && j>0) {
						existingSolution.addAll(listaAcumuladora[j-1][i-1]);
					}
					if(j>1) {
						existingSolution.addAll(listaAcumuladora[j-2][i]);
					}
					if(i>1) {
						existingSolution.addAll(listaAcumuladora[j][i-2]);
					}
					if(i>0 && j>1) {
						existingSolution.addAll(listaAcumuladora[j-2][i-1]);
					}
					if(i>1 && j>0) {
						existingSolution.addAll(listaAcumuladora[j-1][i-2]);
					}
					
					//Para evitar intersecciones de soluciones anteriores
					if(i>0 && j<column/x-1) {
						existingSolution.addAll(listaAcumuladora[j+1][i-1]);
					}
					if(i>1 && j<column/x-1) {
						existingSolution.addAll(listaAcumuladora[j+1][i-2]);
					}
					existingSolution = Utils.intersectionList(existingSolution, bigSlice);
					
					int prevValue = Utils.evaluateSliceListByAreasTopLeft(existingSolution, bigSlice);
					
					Utils.bestSliceSolutionForPortionKeyPoints(bigSlice, slices, existingSolution, bestSolutionFound,  numIngredients, 
							maxSize, matriz, x*x, blankSlice, prevValue, new Date(),90);
					
					System.out.println(Utils.actualSolution);
					System.out.println("Cuyo score es:"+ Utils.evaluateSliceListByAreasBottomRight(Utils.actualSolution, bigSlice));
					
					if(Utils.validSliceSolution(solution, numIngredients, maxSize, matriz)){
						//Utils.writeFile("/Users/Jesus/Downloads/c_medium.out", solution);
						System.out.println(j+","+i+"-->Es valido!!" + new Date());
						listaAcumuladora[j][i]= Utils.actualSolution;
					}else{
						System.out.println(j+","+i+"-->NO VALIDO");
					}
					Utils.resetSolution();
				}
			}
			List<Slice> generalSolution=new ArrayList<Slice>();
			for(int i = 0; i<row/x; i++) {
				for(int j = 0; j<column/x;j++) {
					generalSolution.addAll(listaAcumuladora[j][i]);
				}
			}
			List<Slice> finalSolution = new ArrayList<Slice>();
			finalSolution = Utils.returnSliceSolution(generalSolution, numIngredients, maxSize, matriz);
			if(Utils.validSliceSolution(finalSolution, numIngredients, maxSize, matriz)){
				System.out.println("SOLUCION GENERAL VALIDA!!");
				int valor = Utils.evaluateSliceListByAreasTotallyIncluded(finalSolution);
				Utils.writeFile("/Users/Jesus/Downloads/c_medium "+valor+".out", finalSolution);
				System.out.println("SCORE->"+Utils.evaluateSliceListByAreasTotallyIncluded(finalSolution));
			}else{
				System.out.println("SOLUCION GENERAL NO VALIDA");
				Utils.writeFile("/Users/Jesus/Downloads/c_mediumConError.out", finalSolution);
			}
					
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
