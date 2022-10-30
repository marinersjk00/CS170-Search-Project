import java.io.*;
import java.util.*;


class Main
{
static Scanner in = new Scanner(System.in);
//	System.out.print("Please enter puzzle dimension: \n Ex: '3' for 8-puzzle, '4' for 15-puzzle, '5' for 24-puzzle\n");
static int puzzleDim = 3;
static class State{
  ArrayList<ArrayList<Integer>> puzzle;
  int emptyI;
  int emptyJ;
  int depth;
  
  
  
  State(ArrayList<ArrayList<Integer>> p, int d){
        this.puzzle = p;
        this.depth = d;
        
        a:
        for(int i = 0; i < puzzleDim; i++){
                for(int j = 0; j < puzzleDim; j++){
                        if(this.puzzle.get(i).get(j) == 0){
                                this.emptyI = i;
                                this.emptyJ = j;
                                break a;
                        }
                }
        }
  }
  
  

}




	
	static ArrayList<ArrayList<Integer>> swap(ArrayList<ArrayList<Integer>> arr, int a, int b, int ai, int aj, int bi, int bj){
		
		
		arr.get(ai).set(aj, b);
		arr.get(bi).set(bj, a);
		
		
	
		
		return arr;
		
		
	}

	/*static boolean isGoal(ArrayList<ArrayList<Integer>> puzzle, ArrayList<ArrayList<Integer>> goal){
		for(int i = 0; i < puzzleDim; i++){
			for(int j = 0; j < puzzleDim; j++){
				if(puzzle.get(i).get(j) != goal.get(i).get(j)) return false;
			}
		}
		
		return true;
	}
	
	
	static void search(State curr, HashMap<ArrayList<ArrayList<Integer>>, Integer> seen) {
		
		if(curr.emptyI < puzzleDim - 1) {
			State nextState = new State()
		}
		
	}*/
	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>>  puzzle = new ArrayList<ArrayList<Integer>>() ;
		
		ArrayList<ArrayList<Integer>>  solved = new ArrayList<ArrayList<Integer>>() ;
		int goalNum = 1;
		for(int i = 0; i < puzzleDim; i++){
			
			ArrayList<Integer> temp = new ArrayList<>();
			solved.add(temp);
			
			for(int j = 0; j < puzzleDim; j++){
				if(i == j && j == puzzleDim - 1){
					temp.add(0);
				}else{
				temp.add(goalNum);
				}
				
				goalNum++;
				System.out.print(solved.get(i).get(j) + "  ");
				
				}
			
			
		System.out.println();
		
		}
		
		
		
		HashMap<ArrayList<ArrayList<Integer>>, Integer> seen = new HashMap<>();	
		Queue<ArrayList<ArrayList<Integer>>> next = new LinkedList<>();
		
		System.out.print("Please enter the puzzle start from L to R, Top to Bottom. Enter '0' to represent the empty space\n\n");
	for(int i = 0; i < puzzleDim; i++){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		puzzle.add(temp);
		for(int j = 0; j < puzzleDim; j++){
			temp.add(in.nextInt());
		}
		
	}
	
	System.out.println("Starting puzzle: ");
	for(int i = 0; i < puzzleDim; i++){
		for(int j = 0; j < puzzleDim; j++){
			System.out.print(puzzle.get(i).get(j) + "  ");
		}
		System.out.println();
	}
				
	puzzle = swap(puzzle, puzzle.get(0).get(0), puzzle.get(0).get(1), 0, 0, 0, 1);
	


	System.out.println("\n\nSwapped puzzle: ");
	for(int i = 0; i < puzzleDim; i++){
		for(int j = 0; j < puzzleDim; j++){
			System.out.print(puzzle.get(i).get(j) + "  ");
		}
		System.out.println();
	}
System.out.println("\n\n");
		for(int i = 0; i < puzzleDim; i++){
			for(int j = 0; j < puzzleDim; j++){
				System.out.print(puzzle.get(i).get(j) + "  ");
	}
				System.out.println();

	}
		



	}
}


