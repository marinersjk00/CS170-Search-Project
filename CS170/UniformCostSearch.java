import java.io.*;
import java.util.*;
import java.time.*;



class Main
{
static Scanner in = new Scanner(System.in);
static int puzzleDim = 3; //This is the only value we have to change to adjust for a different size of puzzle
static class State{
  ArrayList<ArrayList<Integer>> puzzle;
  int emptyI;
  int emptyJ;
  int depth;
  
  
  
  State(ArrayList<ArrayList<Integer>> p, int d){ //We have to find the empty space for the initial state
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
  
  State(ArrayList<ArrayList<Integer>> p, int d, int i, int j){ //for subsequent states we can define the empty space as a function of the previous state
      this.puzzle = p;
      this.depth = d;
      this.emptyI = i;
      this.emptyJ = j;
}
  
  

}

static class StateCompare implements Comparator<State>{ //Comparator for priority queue. Prioritizes min depth
	public int compare(State s, State p) {
		if(s.depth < p.depth) {
			return -11;
		}else if(s.depth > p.depth) {
			return 1;
		}else {
			return 0;
		}
	}
}



	
	static ArrayList<ArrayList<Integer>> swap(ArrayList<ArrayList<Integer>> arr, int a, int b, int ai, int aj, int bi, int bj){ //simple swapping function for swapping two elements in a 2D vector
		
		
	
	

		arr.get(ai).set(aj, b);
		arr.get(bi).set(bj, a);
		
	
		
		
		
	
		
		return arr;
		
		
	}

	static boolean isGoal(ArrayList<ArrayList<Integer>> puzzle, ArrayList<ArrayList<Integer>> goal){ //function used to check if current state is the goal. Called on every state
		for(int i = 0; i < puzzleDim; i++){
			for(int j = 0; j < puzzleDim; j++){
				if(puzzle.get(i).get(j) != goal.get(i).get(j)) return false;
			}
		}
		
		
		System.out.print("\n\nSolved puzzle: \n"); //if it makes it out of the for loop then it will return true.
		//printing the current state to visually verify that it is the goal state
		for(int i = 0; i < puzzleDim; i++) {
			for(int j = 0; j < puzzleDim; j++) {
				System.out.print(puzzle.get(i).get(j) + "  ");
			}
			System.out.println();
		}
		
		return true;
	}
	
	//BFS searching function. Checks in the following order relative to the movement of the blank space
	//Up, Down, Right, Left [ (i+1, j), (i-1, j), (i, j+1), (i, j-1)
	static void search(State curr, HashMap<ArrayList<ArrayList<Integer>>, Integer> seen, Queue<State> next) {
		
		if(curr.emptyI < puzzleDim - 1) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < puzzleDim; i++) { //creating a copy of the current state to enter in the swap function so as not to corrupt current state
				ArrayList<Integer> t = new ArrayList<>();
				temp.add(t);
				for(int j = 0; j < puzzleDim; j++) {
					t.add(curr.puzzle.get(i).get(j));
				}
			}

			State nextState = new State(swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI + 1).get(curr.emptyJ), curr.emptyI, curr.emptyJ, curr.emptyI + 1, curr.emptyJ), curr.depth + 1, curr.emptyI + 1, curr.emptyJ);
			if(!seen.containsKey(nextState.puzzle)) { //if the nextState stored as a key in the hashmap then this is a repeated state and we can stop traversing this branch of the tree
				seen.put(nextState.puzzle, nextState.depth);//if it is a new state then  hash it and queue it
				next.add(nextState);
				
			}
		}
		
		if(curr.emptyI > 0) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < puzzleDim; i++) {
				ArrayList<Integer> t = new ArrayList<>();
				temp.add(t);
				for(int j = 0; j < puzzleDim; j++) {
					t.add(curr.puzzle.get(i).get(j));
				}
			}			
			State nextState = new State(swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI - 1).get(curr.emptyJ), curr.emptyI, curr.emptyJ, curr.emptyI - 1, curr.emptyJ), curr.depth + 1, curr.emptyI - 1, curr.emptyJ);
			if(!seen.containsKey(nextState.puzzle)) {
				seen.put(nextState.puzzle, nextState.depth);

				next.add(nextState);
			}
		}
		
		if(curr.emptyJ < puzzleDim - 1) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < puzzleDim; i++) {
				ArrayList<Integer> t = new ArrayList<>();
				temp.add(t);
				for(int j = 0; j < puzzleDim; j++) {
					t.add(curr.puzzle.get(i).get(j));
				}
			}
			State nextState = new State(swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI).get(curr.emptyJ + 1), curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ + 1), curr.depth + 1, curr.emptyI, curr.emptyJ + 1);
			if(!seen.containsKey(nextState.puzzle)) {
				seen.put(nextState.puzzle, nextState.depth);

				next.add(nextState);
				
			}
		}
		
		if(curr.emptyJ > 0) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < puzzleDim; i++) {
				ArrayList<Integer> t = new ArrayList<>();
				temp.add(t);
				for(int j = 0; j < puzzleDim; j++) {
					t.add(curr.puzzle.get(i).get(j));
				}
			}
			State nextState = new State(swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI).get(curr.emptyJ - 1), curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ - 1), curr.depth + 1, curr.emptyI, curr.emptyJ - 1);
			if(!seen.containsKey(nextState.puzzle)) {
				seen.put(nextState.puzzle, nextState.depth);

				next.add(nextState);
				
			}
		}
		
	}
	
	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>>  puzzle = new ArrayList<ArrayList<Integer>>() ; //this will be the initial state
		ArrayList<ArrayList<Integer>>  solved = new ArrayList<ArrayList<Integer>>() ; //this is the goal state
		HashMap<ArrayList<ArrayList<Integer>>, Integer> seen = new HashMap<>();	//this will contain previously seen states
		PriorityQueue<State> next = new PriorityQueue<State>(new StateCompare()); //this orders the next state based on lowest depth
		int maxDepth = 0;
		
		
		
		boolean isSolvable = false;
		
		int goalNum = 1; //used to build the goal state for any size puzzle
		for(int i = 0; i < puzzleDim; i++){ //displaying the goal state at the start of the program
			
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
	for(int i = 0; i < puzzleDim; i++){ //The User enters in the initial state here, 1 number per line with 0 representing the blank space
		ArrayList<Integer> temp = new ArrayList<Integer>();
		puzzle.add(temp);
		for(int j = 0; j < puzzleDim; j++){
			temp.add(in.nextInt());
		}
		
	}
	
	System.out.println("Starting puzzle: ");
	for(int i = 0; i < puzzleDim; i++){ //printing the initial state to help the user verify proper entry
		for(int j = 0; j < puzzleDim; j++){
			System.out.print(puzzle.get(i).get(j) + "  ");
		}
		System.out.println();
	}
		System.out.println();
		State start = new State(puzzle, 0);
		State finished = null;
		next.add(start);
Instant insta = Instant.now();
		
		long startTime = insta.toEpochMilli();
		System.out.print("\nstart time = " + startTime + "\n");

		//loop displays each state and current depth on each iteration
		while(!next.isEmpty() && !isSolvable) { //loops until queue is empty (unsolvable) or goal state is achieved (solvable)
			State curr = next.remove();
			maxDepth = Math.max(maxDepth,  curr.depth);
			System.out.print("Depth: " + curr.depth + "\n");
			System.out.print("\nState:\n\n");
			for(int i = 0; i < puzzleDim; i++) {
				for(int j = 0; j < puzzleDim; j++) {
					System.out.print(curr.puzzle.get(i).get(j) + "  " );
				}
				System.out.println();
			}
			isSolvable = isGoal(curr.puzzle, solved);
			if(isSolvable) {
				finished = curr;
				
			}
			
			search(curr, seen, next);	
		}
		
		insta = Instant.now();
		long endTime = insta.toEpochMilli();
		
		if(isSolvable) { //if we can solve the puzzle we will print some stats for it
			
			System.out.print("\nSolved!\nDepth of Solution: " + finished.depth + "\nTime Elapsed ≈ " + (double)(endTime - startTime)/1000 + " sec\n");
			System.out.println("Starting puzzle: ");
			for(int i = 0; i < puzzleDim; i++){ //printing the initial state as additional info
				for(int j = 0; j < puzzleDim; j++){
					System.out.print(puzzle.get(i).get(j) + "  ");
				}
				System.out.println();
			}
		
		}else System.out.print("\nNo solution for given input.\nMax Depth Reached: " + maxDepth + "\nTime Elapsed ≈ " + (double)(endTime - startTime)/1000); //otherwise inform the user it is not solvable, say how deep it searched and for how long
		
		
	


	

		



	}
}

