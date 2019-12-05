import java.util.Scanner;
import java.io.*;

public class LifeGame{

	static Scanner consoleReader = new Scanner(System.in);
	static final int M = 25;
	static final int N = 75;
	static final int MIN_NEI = 2;
	static final int MAX_NEI = 3;
	static final int VAC = 3;

	private static boolean fillWorldArray(String filename, char[][] worldArray){
		File file = new File(filename);
		Scanner fileReader = null;	
		boolean filled = false;	

		try { 
		   fileReader = new Scanner (file);

		   for (int i = 0; i <= M+1; i++) {
			   if(i>0 && i< M+1){
					String line = fileReader.nextLine();
					for(int j = 0; j<= N+1; j++){
						if(j>0 && j< N+1){
							worldArray[0][((N+2)*i)+j] = line.charAt(j-1); 
							worldArray[1][((N+2)*i)+j] = line.charAt(j-1); //at initial stage both 0 and 1 have the same
						}else{
							worldArray[0][((N+2)*i)+j] = '.';
							worldArray[1][((N+2)*i)+j] = '.'; //at initial stage both 0 and 1 have the same
						}
					}
				}else{
					for(int j = 0; j<= N+1; j++){
						worldArray[0][((N+2)*i)+j] = '.';
						worldArray[1][((N+2)*i)+j] = '.'; //at initial stage both 0 and 1 have the same
					}
				}			
  			}
  			filled = !filled;
		}
		catch (Exception e) {
		   System.out.print("file " + file + " does not exist");
		   System.exit(0);		   
		} 
		return filled; 		
	}

	private static int countNeighbors(char[] world, int pos){
		int totNei = 0;		

		if((pos > (N+2)) && (pos < ((M+2)*(N+2)-(N+2)))){
			if((pos%(N+2) > 0) && (pos%(N+2) < 76)){
				int ln = (world[pos-1] == 'X') ? 1 : 0;
				int rn = (world[pos+1] == 'X') ? 1 : 0;
				int un = (world[pos-(N+2)] == 'X') ? 1 : 0;
				int dn = (world[pos+(N+2)] == 'X') ? 1 : 0;
				int uln = (world[pos-(N+2)-1] == 'X') ? 1 : 0;
				int urn = (world[pos-(N+2)+1] == 'X') ? 1 : 0;
				int dln = (world[pos+(N+2)-1] == 'X') ? 1 : 0;
				int drn = (world[pos+(N+2)+1] == 'X') ? 1 : 0;

				totNei = ln + rn + un + dn + uln + urn + dln + drn;
			}
		}		
		return totNei;
	}

	private static int cellNeighbors(char[] world, int xPos, int yPos){
		int totNei = 0;
		int pos = ((N+2)*xPos) + yPos;

		if(world[pos] == 'X')
			totNei = countNeighbors(world, pos);		
		return totNei;
	}

	private static int vacantNeighbors(char[] world, int xPos, int yPos){
		int totNei = 0;
		int pos = ((N+2)*xPos) + yPos;

		if(world[pos] != 'X')
			totNei = countNeighbors(world, pos);		
		return totNei;
	}

	private static boolean isEmpty(char[] world){
		int totCell = 0;

		for (int i = 0; i <= M+1; i++) {
  			for(int j = 0; j<= N+1; j++){
  				totCell += ((world[((N+2)*i)+j] == 'X') ? 1 : 0); 
  			}  			
  		}
  		return !(totCell > 0);
	}

	private static boolean isEqual(char[][] worldArray){
		int totCell = 0;

		for (int i = 0; i <= M+1; i++) {
  			for(int j = 0; j<= N+1; j++){
  				totCell += ((worldArray[0][((N+2)*i)+j] != worldArray[1][((N+2)*i)+j]) ? 1 : 0); 
  			}  			
  		}
  		return !(totCell > 0);
	}
	
	private static void plotWorld(char[] world){		
		for (int i = 1; i < M+1; i++) {
  			for(int j = 1; j< N+1; j++){  				
  				System.out.print(world[((N+2)*i)+j]);
  			}
  			System.out.println();
  		}
  		System.out.println();
	}	

	private static void transition(char[][] worldArray){
		worldArray[0] = worldArray[1].clone();  //method for creating a new array that has the exact copy of worldArray[0]
		for (int i = 0; i <= M+1; i++) {
  			for(int j = 0; j<= N+1; j++){  				
  				int nc = cellNeighbors(worldArray[0], i, j);
  				int nv = vacantNeighbors(worldArray[0], i, j);

  				if(nc >= MIN_NEI && nc <= MAX_NEI)
  					worldArray[1][((N+2)*i)+j] = 'X';
  				else if(nv == VAC)
  					worldArray[1][((N+2)*i)+j] = 'X';
  				else
  					worldArray[1][((N+2)*i)+j] = '.';
  			}  			
  		}
	}

	private static boolean dispMenu0(int ngen, char[] world){
		System.out.println();
		System.out.println("\t\t\tGeneration N. " + ngen);
		plotWorld(world);
		System.out.print("Please press enter to see the next generation or q() to exit: ");
		String ans = consoleReader.nextLine();
		return !(ans.equals("q()"));
	}

	private static void dispMenu1(int ngen, char[] world, String reason){
		System.out.println();
		System.out.println("\t\t\tGeneration N. " + ngen);
		plotWorld(world);
		System.out.print("This is the final generation of the game. " + reason + "Press enter to exit: ");
		String ans = consoleReader.nextLine();
		return;		
	}

	public static void main (String [] args) {		
		
		System.out.print ("Which file do you want to open? ");
		String filename = consoleReader.nextLine();
		char[][] worldArray = new char[2][(M+2)*(N+2)];
		int ngen = 0;
		boolean play = true;
		boolean finalStage = false;
		String reason = "";

		boolean filled = fillWorldArray(filename, worldArray); 

		if(filled){
			while(play){
				if(!finalStage){
					boolean q = dispMenu0(ngen, worldArray[1]);
					if(q){
						transition(worldArray);
						if(isEmpty(worldArray[1])){
							reason = "The world is empty. ";
							finalStage = !finalStage;
						}else if(isEqual(worldArray)){
							reason = "The new stage is equal to the previous one. ";
							finalStage = !finalStage;
						}							
						ngen++;							
					}else{
						play = !play;
						System.out.println("Closing game...");
					}					
				}else{
					dispMenu1(ngen, worldArray[1], reason);
					play = !play;
					System.out.println("Closing game...");	
				}		
			}
		}  		
  	}	
}