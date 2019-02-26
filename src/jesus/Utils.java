package jesus;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Utils {
	//método que valida si hay intersección entre dos porciones
	public static boolean intersection(Slice a, Slice b){
		//Vertices del slice b incluidos en a
		if(b.getTopLeft().getY()>= a.getTopLeft().getY() &&
				b.getTopLeft().getY()<= a.getBottomRight().getY() &&
				b.getTopLeft().getX()>= a.getTopLeft().getX() &&
				b.getTopLeft().getX()<= a.getBottomRight().getX()) {
			return true;
		}
		if(b.getBottomRight().getY()>= a.getTopLeft().getY() &&
				b.getBottomRight().getY()<= a.getBottomRight().getY() &&
				b.getTopLeft().getX()>= a.getTopLeft().getX() &&
				b.getTopLeft().getX()<= a.getBottomRight().getX()) {
			return true;
		}
		if(b.getBottomRight().getY()>= a.getTopLeft().getY() &&
				b.getBottomRight().getY()<= a.getBottomRight().getY() &&
				b.getBottomRight().getX()>= a.getTopLeft().getX() &&
				b.getBottomRight().getX()<= a.getBottomRight().getX()) {
			return true;
		}
		if(b.getTopLeft().getY()>= a.getTopLeft().getY() &&
				b.getTopLeft().getY()<= a.getBottomRight().getY() &&
				b.getBottomRight().getX()>= a.getTopLeft().getX() &&
				b.getBottomRight().getX()<= a.getBottomRight().getX()) {
			return true;
		}
		
		//Vertices del slice a incluidos en b
		if(a.getTopLeft().getY()>= b.getTopLeft().getY() &&
				a.getTopLeft().getY()<= b.getBottomRight().getY() &&
				a.getTopLeft().getX()>= b.getTopLeft().getX() &&
				a.getTopLeft().getX()<= b.getBottomRight().getX()) {
			return true;
		}
		if(a.getBottomRight().getY()>= b.getTopLeft().getY() &&
				a.getBottomRight().getY()<= b.getBottomRight().getY() &&
				a.getTopLeft().getX()>= b.getTopLeft().getX() &&
				a.getTopLeft().getX()<= b.getBottomRight().getX()) {
			return true;
		}
		if(a.getBottomRight().getY()>= b.getTopLeft().getY() &&
				a.getBottomRight().getY()<= b.getBottomRight().getY() &&
				a.getBottomRight().getX()>= b.getTopLeft().getX() &&
				a.getBottomRight().getX()<= b.getBottomRight().getX()) {
			return true;
		}
		if(a.getTopLeft().getY()>= b.getTopLeft().getY() &&
				a.getTopLeft().getY()<= b.getBottomRight().getY() &&
				a.getBottomRight().getX()>= b.getTopLeft().getX() &&
				a.getBottomRight().getX()<= b.getBottomRight().getX()) {
			return true;
		}
		
		//Sin puntos de a en b ni viceversa. Estan haciendo una cruz
		if(b.getTopLeft().getY()<= a.getTopLeft().getY() &&
				a.getBottomRight().getY()<= b.getBottomRight().getY() &&
				a.getTopLeft().getX()<= b.getTopLeft().getX() &&
				b.getBottomRight().getX()<= a.getBottomRight().getX()) {
			return true;
		}
		if(a.getTopLeft().getY()<= b.getTopLeft().getY() &&
				b.getBottomRight().getY()<= a.getBottomRight().getY() &&
				b.getTopLeft().getX()<= a.getTopLeft().getX() &&
				a.getBottomRight().getX()<= b.getBottomRight().getX()) {
			return true;
		}
		return false;
	}
	
	public static List<Slice> intersectionList(List<Slice> slice, Slice bigSlice){
		List<Slice> returnList = new ArrayList<Slice>();
		for(Slice s: slice) {
			if(intersection(s, bigSlice)) {
				returnList.add(s);
			}
		}
		return returnList;
	}
	
	//Método que devuelve todas las porciones posibles(válidas) partiendo de un punto dado posX, posY
	public static List<Slice> giveMeSlices(int posX, int posY, int numIngredients, int maxSize, char matriz [][]) {
		List<Slice> slices = new ArrayList<Slice>();
		List<Point> listHelper = Utils.helperPossibleSlice(numIngredients, maxSize);
		Point origin = new Point(posX,posY);
		Point aux;
		for(Point p: listHelper) {
			aux = origin.sumPoint(p);
			if(aux.getX()>=matriz[0].length || aux.getY()>=matriz.length) {
				continue;
			}else {
				Slice slice = new Slice(origin, aux);
				if(Utils.checkSlice(slice, numIngredients, maxSize, matriz)) {
					slices.add(slice);
				}
			}
		}
		return slices;
	}
	
	public static List<Slice> giveMeSlicesInclusive(int posX, int posY, int numIngredients, int maxSize, char matriz [][], Slice bigSlice) {
		List<Slice> slices = new ArrayList<Slice>();
		List<Point> listHelper = Utils.helperPossibleSlice(numIngredients, maxSize);
		Point origin = new Point(posX,posY);
		Point aux;
		for(Point p: listHelper) {
			aux = origin.sumPoint(p);
			if(aux.getX()>bigSlice.getBottomRight().getX() || aux.getY()>=bigSlice.getBottomRight().getY()) {
				continue;
			}else {
				Slice slice = new Slice(origin, aux);
				if(Utils.checkSlice(slice, numIngredients, maxSize, matriz)) {
					slices.add(slice);
				}
			}
		}
		return slices;
	}
	
	public static boolean checkSlice(Slice slice, int numIngredients, int maxSize, char matriz [][]) {
		int tomatoCount = 0;
		int mushroomCount = 0;
		for(int i=slice.getTopLeft().getX(); i<= slice.getBottomRight().getX(); i++) {
			for(int j=slice.getTopLeft().getY(); j<= slice.getBottomRight().getY(); j++) {
				if(matriz[j][i]=='T') {
					tomatoCount+=1;
				}else {
					mushroomCount+=1;
				}
			}
		}
		if(tomatoCount>=numIngredients && mushroomCount>=numIngredients
				&& tomatoCount+mushroomCount<=maxSize) {
			return true;
		}
		return false;
	}
	
	
	public static List<Point>  helperPossibleSlice(int numIngredients, int maxSize){
		List<Point> list = new ArrayList<Point>();
		//Small pizza
		if(numIngredients == 1 && maxSize== 5) {
			list.add(new Point(0,1));
			list.add(new Point(0,2));
			list.add(new Point(0,3));
			list.add(new Point(0,4));
			list.add(new Point(1,0));
			list.add(new Point(2,0));
			list.add(new Point(3,0));
			list.add(new Point(4,0));
			list.add(new Point(1,1));
		}
		//Medium pizza
		if(numIngredients == 4 && maxSize== 12) {
			list.add(new Point(0,7));
			list.add(new Point(0,8));
			list.add(new Point(0,9));
			list.add(new Point(0,10));
			list.add(new Point(0,11));
			list.add(new Point(7,0));
			list.add(new Point(8,0));
			list.add(new Point(9,0));
			list.add(new Point(10,0));
			list.add(new Point(11,0));
			list.add(new Point(3,1));
			list.add(new Point(4,1));
			list.add(new Point(5,1));
			list.add(new Point(1,3));
			list.add(new Point(1,4));
			list.add(new Point(1,5));
		}
		//Big pizza
		if(numIngredients == 6 && maxSize== 14) {
			list.add(new Point(0,13));
			list.add(new Point(0,12));
			//list.add(new Point(0,11));
			//list.add(new Point(1,6));
			list.add(new Point(1,5));
			list.add(new Point(13,0));
			list.add(new Point(12,0));
			//list.add(new Point(11,0));
			//list.add(new Point(6,1));
			list.add(new Point(5,1));
			//list.add(new Point(3,2));
			//list.add(new Point(2,3));
		}
		return list;
	}
	//Método que valida si un punto está en una porción
	public static boolean pointInSlice(Slice s, Point p) {
		if(p.getX()>=s.getTopLeft().getX() && p.getX()<= s.getBottomRight().getX()
				&& p.getY()>=s.getTopLeft().getY() && p.getY()<= s.getBottomRight().getY()) {
			return true;
		}else{
			return false;
		}
	}
	
	//Método que devuelve todos los puntos que no se están usando de una porción en una selección de porciones
	public static List<Point> pointsNotInUse (List<Slice> slicesList, Slice slice){
		List<Point> pointList = new ArrayList<Point>();
		boolean includePoint = true;
		for(int i = slice.getTopLeft().getX();i<=slice.getBottomRight().getX();i++) {
			for(int j = slice.getTopLeft().getY();j<=slice.getBottomRight().getY();j++) {
				Point pointToCheck = new Point(i,j);
				includePoint=true;
				for(Slice s: slicesList) {
					if(pointInSlice(s,pointToCheck)) {
						includePoint = false;
						break;
					}
				}
				if(includePoint) {
					pointList.add(pointToCheck);
				}
			}
		}
		return pointList;
	}
	//Método que devuelve todos los puntos que no se están usando de una porción en una selección de porciones que 
	//no disten mucho de la última porción incluida
	public static List<Point> pointsNotInUseKeyPoints (List<Slice> slicesList, Slice slice, Slice lastSliceIncluded, List<Slice> existingSolution){
		List<Slice> list = new ArrayList<Slice>();
		list.addAll(slicesList);
		list.addAll(existingSolution);
		List<Point> listKeyPoint = new ArrayList<Point>();
		int limitPoints = 50;
		boolean includePoint = true;
		for(int i = slice.getTopLeft().getX();i<=slice.getBottomRight().getX();i++) {
			if(listKeyPoint.size()>=limitPoints) {
				break;
			}
			for(int j = slice.getTopLeft().getY();j<=slice.getBottomRight().getY();j++) {
				if(listKeyPoint.size()>=limitPoints) {
					break;
				}
				Point pointToCheck = new Point(i,j);
				includePoint=true;
				for(Slice s: list) {
					if(pointInSlice(s,pointToCheck)) {
						includePoint = false;
						break;
					}
				}
				if(includePoint) {
					if(taxiDistance(pointToCheck, lastSliceIncluded.getTopLeft())<15 || taxiDistance(pointToCheck, lastSliceIncluded.getBottomRight())<15) {
						listKeyPoint.add(pointToCheck);
						continue;
					}
					//Para asegurarnos continuidad al terminar horizontalmente/verticalmente incluimos algunos puntos de la siguiente linea aunque esten lejos
					if(pointToCheck.getX() == lastSliceIncluded.getBottomRight().getX()+1
							&& pointToCheck.getY() == lastSliceIncluded.getBottomRight().getY()+1) {
						listKeyPoint.add(pointToCheck);
						continue;
					}
				}
			}
		}
		
		return listKeyPoint;
	}
	
	public static int taxiDistance(Point a, Point b) {
		return Math.abs(a.getX()-b.getX())+Math.abs(a.getY()-b.getY());
	}
	
	//Método que evalua la calidad de un conjunto de porciones sobre una porción mayor
	public static int evaluateSliceList (List<Slice> slicesList, Slice slice){
		int numPointsNotInUse = 0;
		boolean includePoint = true;
		for(int i = slice.getTopLeft().getX();i<=slice.getBottomRight().getX();i++) {
			for(int j = slice.getTopLeft().getY();j<=slice.getBottomRight().getY();j++) {
				includePoint= true;
				Point pointToCheck = new Point(i,j);
				for(Slice s: slicesList) {
					if(pointInSlice(s,pointToCheck)) {
						includePoint = false;
						break;
					}
				}
				if(includePoint) {
					numPointsNotInUse+=1;
				}
			}
		}
		//Area-numPointNotInUse=PointInUse
		return (slice.getBottomRight().getX()-slice.getTopLeft().getX()+1)*(slice.getBottomRight().getY()-slice.getTopLeft().getY()+1)-numPointsNotInUse;
	}
	
	//Método que evalua la calidad de un conjunto de porciones sobre una porción mayor
	//Suma las areas de las intersecciones con la porcion mayor ya que los slices son disjuntos
	//Además sabemos que las partes sobrantes de los slices son de la parte abajo derecha.
	public static int evaluateSliceListByAreasBottomRight (List<Slice> slicesList, Slice bigSlice){
		int value = 0;
		for(Slice s: slicesList) {
			value += (Math.min(s.getBottomRight().getX(), bigSlice.getBottomRight().getX())-s.getTopLeft().getX()+1)*
					(Math.min(s.getBottomRight().getY(), bigSlice.getBottomRight().getY())-s.getTopLeft().getY()+1);
		}
		return value;
	}
	
	//Método que evalua la calidad de un conjunto de porciones sobre una porción mayor
	//Suma las areas de las intersecciones con la porcion mayor ya que los slices son disjuntos
	//Además sabemos que las partes sobrantes de los slices son de la parte arriba a la izquierda.
	public static int evaluateSliceListByAreasTopLeft (List<Slice> slicesList, Slice bigSlice){
		int value = 0;
		for(Slice s: slicesList) {
			value +=(1+Math.max(s.getTopLeft().getX(), bigSlice.getTopLeft().getX())-s.getBottomRight().getX())*
					(s.getBottomRight().getY()-Math.max(s.getTopLeft().getY(), bigSlice.getTopLeft().getY())-+1);
		}
		return value;
	}
	
	public static int evaluateSliceListByAreasTotallyIncluded (List<Slice> slicesList){
		int value = 0;
		for(Slice s: slicesList) {
			value += (s.getBottomRight().getX()-s.getTopLeft().getX()+1)*
					(s.getBottomRight().getY()-s.getTopLeft().getY()+1);
		}
		return value;
	}
	
	
	
	//Suma las areas de las intersecciones con la porcion mayor ya que los slices son disjuntos
	//Además sabemos que las partes sobrantes de los slices son de la parte ariba izquierda.
	public static int evaluateSliceListByAreasExistingSolution (List<Slice> slicesList, Slice bigSlice){
		int value = 0;
		for(Slice s: slicesList) {
			if(s.getBottomRight().getX()>= bigSlice.getTopLeft().getX() &&
					s.getBottomRight().getY()>= bigSlice.getTopLeft().getY()) {
				value += (Math.max(s.getBottomRight().getX(), bigSlice.getTopLeft().getX())-bigSlice.getTopLeft().getX()+1)*
						(Math.max(s.getBottomRight().getY(), bigSlice.getTopLeft().getY())-bigSlice.getTopLeft().getY()+1);
			}
		}
		return value;
	}
	
	
	public static List<Slice> cloneList(List<Slice> list){
		List<Slice> returnList = new ArrayList<Slice>();
		for(Slice s: list) {
			returnList.add(s.clone());
		}
		return returnList;
	}
	
	//Método recursivo que, dada una matriz, itera en cuadrados M x M calculando la solución optima entre 
	//los cortes de slices posibles
	/*
	 * topLeft y bottomRight delimitan la porcion mayor sobre la que estamos buscando la solución óptima
	 * startPoint es el punto sobre el que vamos a añadir una porción más
	 * matriz es la pizza
	 * 
	 */
	public static List<Slice> bestSliceSolutionForPortion(Slice bigSlice, List<Slice> slices,List<Slice> slicesSolution, int numIngredients, int maxSize, char[][] matriz, int stopCondition) {
		if(evaluateSliceList(slicesSolution, bigSlice)>=stopCondition) {
			return slicesSolution!=null?slicesSolution:slices;
		}
		List<Point> pointsToIterate = pointsNotInUse(slices, bigSlice);
		for(Point point: pointsToIterate) {
			if(evaluateSliceList(slicesSolution, bigSlice)>=stopCondition) {
				return slicesSolution!=null?slicesSolution:slices;
			}
			//Se obtienen las posibles porciones para el punto de partida
			List<Slice> possibleSlices = giveMeSlices(point.getX(), point.getY(), numIngredients, maxSize, matriz);
			boolean validSlice;
			List<Slice> newList;
			for(Slice possibleSlice: possibleSlices) {
				if(evaluateSliceList(slicesSolution, bigSlice)>=stopCondition) {
					return slicesSolution!=null?slicesSolution:slices;
				}
				validSlice= true;
				for(Slice slice: slices) {
					if(intersection(slice, possibleSlice)) {
						validSlice = false;
						break;
					}
				}
				//Añadimos el slice
				if(validSlice) {
					newList = cloneList(slices);
					newList.add(possibleSlice);					
					//Nuevo
					List<Slice> possibleBestSolution = bestSliceSolutionForPortion(bigSlice,  newList, slicesSolution, numIngredients, maxSize, matriz, stopCondition);
					if(evaluateSliceList(possibleBestSolution, bigSlice)>evaluateSliceList(slicesSolution, bigSlice)) {
						slicesSolution = possibleBestSolution;
						break;
					}
				}
			}
		}
		return slices;
	}
	public static List<Slice> bestSliceSolutionForPortionInclusive(Slice bigSlice, List<Slice> slices,List<Slice> slicesSolution, int numIngredients, int maxSize, char[][] matriz, int stopCondition) {
		if(evaluateSliceList(slicesSolution, bigSlice)>=stopCondition) {
			return slicesSolution;
		}
		List<Point> pointsToIterate = pointsNotInUse(slices, bigSlice);
		for(Point point: pointsToIterate) {
			if(evaluateSliceList(slicesSolution, bigSlice)>=stopCondition) {
				return slicesSolution;
			}
			//Se obtienen las posibles porciones para el punto de partida
			List<Slice> possibleSlices = giveMeSlicesInclusive(point.getX(), point.getY(), numIngredients, maxSize, matriz, bigSlice);
			boolean validSlice;
			List<Slice> newList;
			for(Slice possibleSlice: possibleSlices) {
				if(evaluateSliceList(slicesSolution, bigSlice)>=stopCondition) {
					return slicesSolution;
				}
				validSlice= true;
				for(Slice slice: slices) {
					if(intersection(slice, possibleSlice)) {
						validSlice = false;
						break;
					}
				}
				//Añadimos el slice
				if(validSlice) {
					newList = cloneList(slices);
					newList.add(possibleSlice);					
					//Nuevo
					List<Slice> possibleBestSolution = bestSliceSolutionForPortionInclusive(bigSlice,  newList, slicesSolution, numIngredients, maxSize, matriz, stopCondition);
					if(evaluateSliceList(possibleBestSolution, bigSlice)>evaluateSliceList(slicesSolution, bigSlice)) {
						slicesSolution = possibleBestSolution;
						actualSolution = possibleBestSolution;
						break;
					}
				}
			}
		}
		return slices;
	}
	
	public static List<String> writeSlices(List<Slice> slices) {
		List<String> lines = new ArrayList<String>();
		lines.add(String.valueOf(slices.size()));
		for(Slice slice: slices) {
			lines.add(slice.toString());
		}
		return lines;
	}
	
	public static void writeFile(String path, List<Slice> slices) {
		List<String> lines = writeSlices(slices);
		Path file = Paths.get(path);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean validSliceSolution(List<Slice> slices, int numIngredients, int maxSize, char[][] matriz) {
		for(int i = 0; i<slices.size();i++) {
			if(!checkSlice(slices.get(i), numIngredients, maxSize, matriz)) {
				return false;
			}
			for(int j = i+1; j<slices.size();j++) {
				if(intersection(slices.get(i), slices.get(j))) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static List<Slice> returnSliceSolution(List<Slice> slices, int numIngredients, int maxSize, char[][] matriz) {
		List<Slice> returnList = new ArrayList<Slice>();
		boolean sliceValida = true;
		for(int i = 0; i<slices.size();i++) {
			sliceValida= true;
			if(!checkSlice(slices.get(i), numIngredients, maxSize, matriz)) {
				sliceValida = false;
				System.out.println("Quitamos un slice:" + slices.get(i).toString());
				break;
			}
			for(int j = slices.size()-1; j>i;j--) {
				if(intersection(slices.get(i), slices.get(j))) {
					sliceValida = false;
					System.out.println("Quitamos un slice:" + slices.get(j).toString());
					j--;
					break;
				}
			}
			if(sliceValida){
				returnList.add(slices.get(i));
			}
		}

		return returnList;
	}
	public static List<Slice> actualSolution = new ArrayList<Slice>();
	public static void resetSolution() {
		actualSolution = new ArrayList<Slice>();
	}
	public static List<Slice> bestSliceSolutionForPortionKeyPoints(Slice bigSlice, List<Slice> slices, List<Slice> existingSolution,
			List<Slice> bestSolutionFound ,int numIngredients, int maxSize, char[][] matriz, int stopCondition, Slice lastSliceIncluded,
			int prevValue, Date startDate, int secondsPerSlice){
		int valueSolutionActual = evaluateSliceListByAreasBottomRight(bestSolutionFound, bigSlice);
		//Cambiar para forzar salida
		if((valueSolutionActual+prevValue>=stopCondition) || getDateDiff(startDate, new Date())>=secondsPerSlice) {
			return bestSolutionFound;
		}
		List<Point> pointsToIterate = pointsNotInUseKeyPoints(slices, bigSlice, lastSliceIncluded, existingSolution);
		for(Point point: pointsToIterate) {
			if((valueSolutionActual+prevValue>=stopCondition) || getDateDiff(startDate, new Date())>=secondsPerSlice) {
				return bestSolutionFound;
			}
			//Se obtienen las posibles porciones para el punto de partida
			List<Slice> possibleSlices = giveMeSlices(point.getX(), point.getY(), numIngredients, maxSize, matriz);
			boolean validSlice;
			List<Slice> newList;
			for(Slice possibleSlice: possibleSlices) {
				if((valueSolutionActual+prevValue>=stopCondition) || getDateDiff(startDate, new Date())>=secondsPerSlice) {
					return bestSolutionFound;
				}
				validSlice= true;
				for(Slice slice: slices) {
					if(intersection(slice, possibleSlice)) {
						validSlice = false;
						break;
					}
				}
				for(Slice slice: existingSolution) {
					if(intersection(slice, possibleSlice)) {
						validSlice = false;
						break;
					}
				}
				//Añadimos el slice
				if(validSlice) {
					int newValue = 0;
					newList = cloneList(slices);
					newList.add(possibleSlice);					
					//Nuevo
					List<Slice> possibleBestSolution = bestSliceSolutionForPortionKeyPoints(bigSlice,  newList, existingSolution, bestSolutionFound, 
							numIngredients, maxSize, matriz, stopCondition, possibleSlice, prevValue, startDate,secondsPerSlice);
					if((valueSolutionActual+prevValue>=stopCondition) || getDateDiff(startDate, new Date())>=secondsPerSlice) {
						return existingSolution;
					}
					if((newValue = evaluateSliceListByAreasBottomRight(possibleBestSolution, bigSlice))>valueSolutionActual) {
						bestSolutionFound = possibleBestSolution;
						actualSolution = possibleBestSolution;
						valueSolutionActual = newValue;
						break;
					}
				}
			}
		}
		return slices;
	}
	
	public static long getDateDiff(Date date1, Date date2) {
	    return (long)((date2.getTime() - date1.getTime()) / (1000));
	}

}
