import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class cutBigPizzaOleg {
	@SuppressWarnings("null")
	
	public static void main(String[] args) {
	
	char matriz [][] = null;
	BufferedReader reader;
	try {
		reader = new BufferedReader(new FileReader(
				"//Users//oleg//git//CapHashCode//d_big.in"));
		String line = reader.readLine();
		int lineN = 0;
		int rows = 0;
		int ingredient = 0;
		int maxCells = 0;
		//read the file ant put it to the matrix
		while (line != null) {
			//read first line to take args
			if(lineN==0) {
				String[] splited = line.split("\\s+");
				lineN++;
				int row =Integer.parseInt(splited[0]);
				int column = Integer.parseInt(splited[1]);
				ingredient = Integer.parseInt(splited[2]);
				maxCells = Integer.parseInt(splited[3]);
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
		//definition of patterns 
		int [] patron1 = {1,14};
		int [] patron2 = {1,12};
		int [] patron3 = {2,7};
		int [] patron4 = {2,6};
		int [] patron5 = {3,4};
		int [] patron6 = {4,3};
		int [] patron7 = {2,6};
		int [] patron8 = {2,7};
		int [] patron9 = {14,1};
		int [] patron10 = {12,1};

		ArrayList<int[]> respuestas = respuestasValidas(patron1,matriz);
		
	      PrintWriter escribir=new PrintWriter("pizzaCortada.txt");
	       
	    
	      escribir.println(respuestas.size());
	
		for (int i = 0; i < respuestas.size(); i++) {
			for (int j = 0; j < respuestas.get(i).length; j++) {
				//System.out.print(respuestas.get(i)[j]+" ");
				escribir.print(respuestas.get(i)[j]+" ");
			}
			escribir.println();
		//System.out.println();
		}
		  escribir.close();
		reader.close();		
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	
	public static ArrayList <int[]> respuestasValidas(int[] patron, char[][] matriz){
		
		ArrayList<int[]> respuestas = new ArrayList<int[]>();
		
		int patronesValidos = 0;
		
		int posicionH = 0;
		
		for (int posicionV = 0; posicionV < matriz.length; posicionV+=patron[0]) {
			
			for (; posicionH < matriz[posicionV].length;) {
				//si me vale el patron posicionV le sumo todas las posiciones del patron
				if(validarPatron(patron,matriz,posicionH,posicionV)) {
					patronesValidos++;
					int[] respuestas2={posicionV,posicionH,posicionV,posicionH+patron[1]-1};
					//sumamos la posicion vertical
					posicionH+=patron[1];
					respuestas.add(respuestas2);
				}
				else {
				// si no me vale el patron avanzo una celda a la derecha
				 posicionH++; 
				}
			}
		//reinicio las columnas	cuando cambia de fila
		posicionH=0;
		}
		System.out.println(patronesValidos);
		return respuestas;
	}
	public static boolean validarPatron( int[] patron, char[][] matriz, int posicionH, int posicionV ) {
		int countM=0;
		int countT=0;
		
		int posicionMasPatron=posicionH+patron[1];
		int posicionVMasPatron=posicionV+patron[0];
		
		int posicionHorizontalInicial=posicionH;
		
		if(posicionV==posicionVMasPatron)
			posicionV=0;
		
		for (; posicionV < posicionVMasPatron; posicionV++) {
			
			for ( ;posicionH < posicionMasPatron && posicionH<=999 && posicionV<=999; posicionH++) {
				
				if(matriz[posicionV][posicionH]=='T')
					countT++;
				else
					countM++;
			}
			//reset posicion horizontal
			posicionH=posicionHorizontalInicial;
		}
		
		if (countT>=6 && countM>=6)
			return true;
		else
			return false;
		
	}
}
