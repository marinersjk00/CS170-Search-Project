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
  
  State(ArrayList<ArrayList<Integer>> p, int d, int i, int j){
      this.puzzle = p;
      this.depth = d;
      this.emptyI = i;
      this.emptyJ = j;
}
  
  

}

static class StateCompare implements Comparator<State>{
	public int compare(State s, State p) {
		if(s.depth < p.depth) {
			return 1;
		}else if(s.depth > p.depth) {
			return -1;
		}else {
			return 0;
		}
	}
}



	
	static ArrayList<ArrayList<Integer>> swap(ArrayList<ArrayList<Integer>> arr, int a, int b, int ai, int aj, int bi, int bj){
		
		
		arr.get(ai).set(aj, b);
		arr.get(bi).set(bj, a);
		
		
	
		
		return arr;
		
		
	}

	static boolean isGoal(ArrayList<ArrayList<Integer>> puzzle, ArrayList<ArrayList<Integer>> goal){
		for(int i = 0; i < puzzleDim; i++){
			for(int j = 0; j < puzzleDim; j++){
				if(puzzle.get(i).get(j) != goal.get(i).get(j)) return false;
			}
		}
		
		
		System.out.print("\n\n");
		for(int i = 0; i < puzzleDim; i++) {
			for(int j = 0; j < puzzleDim; j++) {
				System.out.print(puzzle.get(i).get(j) + "  ");
			}
			System.out.println();
		}
		
		return true;
	}
	
	
	static void search(State curr, HashMap<ArrayList<ArrayList<Integer>>, Integer> seen, Queue<State> next) {
		
		if(curr.emptyI < puzzleDim - 1) {
			State nextState = new State(swap(curr.puzzle, 0, curr.puzzle.get(curr.emptyI + 1).get(curr.emptyJ), curr.emptyI, curr.emptyJ, curr.emptyI + 1, curr.emptyJ), curr.depth + 1, curr.emptyI + 1, curr.emptyJ);
			if(!seen.containsKey(nextState.puzzle)) {
				seen.put(nextState.puzzle, nextState.depth);
				//System.out.print("Q Check");
				next.add(nextState);
				
			}
		}
		
		if(curr.emptyI > 0) {
			State nextState = new State(swap(curr.puzzle, 0, curr.puzzle.get(curr.emptyI - 1).get(curr.emptyJ), curr.emptyI, curr.emptyJ, curr.emptyI - 1, curr.emptyJ), curr.depth + 1, curr.emptyI - 1, curr.emptyJ);
			if(!seen.containsKey(nextState.puzzle)) {
				seen.put(nextState.puzzle, nextState.depth);
				//System.out.print("Q Check");

				next.add(nextState);
			}
		}
		
		if(curr.emptyJ < puzzleDim - 1) {
			State nextState = new State(swap(curr.puzzle, 0, curr.puzzle.get(curr.emptyI).get(curr.emptyJ + 1), curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ + 1), curr.depth + 1, curr.emptyI, curr.emptyJ + 1);
			if(!seen.containsKey(nextState.puzzle)) {
				seen.put(nextState.puzzle, nextState.depth);
				//System.out.print("Q Check");

				next.add(nextState);
				
			}
		}
		
		if(curr.emptyJ > 0) {
			State nextState = new State(swap(curr.puzzle, 0, curr.puzzle.get(curr.emptyI).get(curr.emptyJ - 1), curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ - 1), curr.depth + 1, curr.emptyI, curr.emptyJ - 1);
			if(!seen.containsKey(nextState.puzzle)) {
				seen.put(nextState.puzzle, nextState.depth);
				//System.out.print("Q Check");

				next.add(nextState);
				
			}
		}
		
	}
	
	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>>  puzzle = new ArrayList<ArrayList<Integer>>() ;
		
		ArrayList<ArrayList<Integer>>  solved = new ArrayList<ArrayList<Integer>>() ;
		HashMap<ArrayList<ArrayList<Integer>>, Integer> seen = new HashMap<>();	
		PriorityQueue<State> next = new PriorityQueue<State>(new StateCompare());
		
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
		System.out.println();
		State start = new State(puzzle, 0);
		
		
		
		next.add(start);
		
		while(!next.isEmpty()) {
			State curr = next.remove();
			System.out.print("Depth: " + curr.depth + "\n");
			System.out.print("\nState:\n\n");
			for(int i = 0; i < puzzleDim; i++) {
				for(int j = 0; j < puzzleDim; j++) {
					System.out.print(curr.puzzle.get(i).get(j) + "  " );
				}
				System.out.println();
			}
			
			if(isGoal(curr.puzzle, solved)) break;
			
			search(curr, seen, next);		
		}
		
		
		
	


	

		



	}
}


