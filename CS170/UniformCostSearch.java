import java.io.*;
import java.util.*;
import java.time.*;



class Main
{
static Scanner in = new Scanner(System.in);
static int puzzleDim = 4; //This is the only value we have to change to adjust for a different size of puzzle
static class State{
  ArrayList<ArrayList<Integer>> puzzle;
  int emptyI;
  int emptyJ;
  int depth;
  int numMisplaced;
  int manhattan;
  
  
  
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
  
  State(ArrayList<ArrayList<Integer>> p, int d, int mis, int man, int aa, int b, int c, int dd){ //We have to find the empty space for the initial state. Useless variables aa, b, c, dd added to avoid constructor conflict
      this.puzzle = p;
      this.depth = d;
      this.numMisplaced = mis;
      this.manhattan = man;
      aa = b;
      c = dd;
      
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
  
  State(ArrayList<ArrayList<Integer>> p, int d, int i, int j){ //for use in UniformSearch
      this.puzzle = p;
      this.depth = d;
      this.emptyI = i;
      this.emptyJ = j;
}
  
  State(ArrayList<ArrayList<Integer>> p, int d, int i, int j, int mis){ //for use in MisplacedSearch
      this.puzzle = p;
      this.depth = d;
      this.emptyI = i;
      this.emptyJ = j;
      this.numMisplaced = mis;
}
  
  
  
  State(ArrayList<ArrayList<Integer>> p, int d, int i, int j,  int mis, int man){ //for use in ManhattanSearch
      this.puzzle = p;
      this.depth = d;
      this.emptyI = i;
      this.emptyJ = j;
      this.manhattan = man;
      this.numMisplaced = mis;
}
  
 
  
  

}

static class StateCompareDepth implements Comparator<State>{ //Comparator for priority queue. Prioritizes min depth
	public int compare(State s, State p) {
		if(s.depth < p.depth) {
			return -1;
		}else if(s.depth > p.depth) {
			return 1;
		}else {
			return 0;
		}
	}
}

static class StateCompareMisplace implements Comparator<State>{ //Comparator for priority queue. Prioritizes min(depth + numMisplaced)
	public int compare(State s, State p) {
		if((s.depth + s.numMisplaced) < (p.depth + p.numMisplaced)) {
			return -1;
		}else if((s.depth + s.numMisplaced) > (p.depth + p.numMisplaced)) {
			return 1;
		}else {
			return 0;
		}
	}
}

static class StateCompareManhattan implements Comparator<State>{ //Comparator for priority queue. Prioritizes min(depth + numMisplaced)
	public int compare(State s, State p) {
		if((s.depth + s.manhattan) < (p.depth + p.manhattan)) {
			return -1;
		}else if((s.depth + s.manhattan) > (p.depth + p.manhattan)) {
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
	static void uniformSearch(State curr, HashMap<ArrayList<ArrayList<Integer>>, Integer> seen, Queue<State> next) {

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
	
	static void misplacedSearch(State curr, HashMap<ArrayList<ArrayList<Integer>>, Integer> seen, Queue<State> next, ArrayList<ArrayList<Integer>> goal) { //contains a goal parameter for using in the countMisplaced function

		if(curr.emptyI < puzzleDim - 1) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < puzzleDim; i++) { //creating a copy of the current state to enter in the swap function so as not to corrupt current state
				ArrayList<Integer> t = new ArrayList<>();
				temp.add(t);
				for(int j = 0; j < puzzleDim; j++) {
					t.add(curr.puzzle.get(i).get(j));
				}
			}
			swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI + 1).get(curr.emptyJ), curr.emptyI, curr.emptyJ, curr.emptyI + 1, curr.emptyJ);
			State nextState = new State(temp, curr.depth + 1, curr.emptyI + 1, curr.emptyJ, countMisplaced(goal, temp));
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
			swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI - 1).get(curr.emptyJ), curr.emptyI, curr.emptyJ, curr.emptyI - 1, curr.emptyJ);
			State nextState = new State(temp, curr.depth + 1, curr.emptyI - 1, curr.emptyJ, countMisplaced(goal, temp));
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
			swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI).get(curr.emptyJ + 1), curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ + 1);
			State nextState = new State(temp, curr.depth + 1, curr.emptyI, curr.emptyJ + 1, countMisplaced(goal, temp));
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
			swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI).get(curr.emptyJ - 1), curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ - 1);
			State nextState = new State(temp, curr.depth + 1, curr.emptyI, curr.emptyJ - 1, countMisplaced(goal, temp));
			if(!seen.containsKey(nextState.puzzle)) {
				seen.put(nextState.puzzle, nextState.depth);

				next.add(nextState);
				
			}
		}
		
	}
	static void manhattanSeek(State curr, HashMap<ArrayList<ArrayList<Integer>>, Integer> seen, Queue<State> next, ArrayList<ArrayList<Integer>> goal) {
		if(curr.emptyI < puzzleDim - 1) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < puzzleDim; i++) { //creating a copy of the current state to enter in the swap function so as not to corrupt current state
				ArrayList<Integer> t = new ArrayList<>();
				temp.add(t);
				for(int j = 0; j < puzzleDim; j++) {
					t.add(curr.puzzle.get(i).get(j));
				}
			}
			swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI + 1).get(curr.emptyJ), curr.emptyI, curr.emptyJ, curr.emptyI + 1, curr.emptyJ);
			State nextState = new State(temp, curr.depth + 1, curr.emptyI + 1, curr.emptyJ, -1, countManhattan(goal, temp));
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
			swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI - 1).get(curr.emptyJ), curr.emptyI, curr.emptyJ, curr.emptyI - 1, curr.emptyJ);
			State nextState = new State(temp, curr.depth + 1, curr.emptyI - 1, curr.emptyJ, -1, countManhattan(goal, temp));
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
			swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI).get(curr.emptyJ + 1), curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ + 1);
			State nextState = new State(temp, curr.depth + 1, curr.emptyI, curr.emptyJ + 1, -1, countManhattan(goal, temp));
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
			swap(temp, temp.get(curr.emptyI).get(curr.emptyJ), temp.get(curr.emptyI).get(curr.emptyJ - 1), curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ - 1);
			State nextState = new State(temp, curr.depth + 1, curr.emptyI, curr.emptyJ - 1, -1, countManhattan(goal, temp));
			if(!seen.containsKey(nextState.puzzle)) {
				seen.put(nextState.puzzle, nextState.depth);

				next.add(nextState);
				
			}
		}
	}
	public static int countMisplaced(ArrayList<ArrayList<Integer>> goal, ArrayList<ArrayList<Integer>> curr) { //simple function that iterates through current state and increments number of misplaced tiles each time a tile is not in its goal position
		int numMisplaced = 0;
		for(int i = 0; i < puzzleDim; i++) {
			for(int j = 0; j < puzzleDim; j++) {
				if(curr.get(i).get(j) != goal.get(i).get(j) && curr.get(i).get(j) != 0) {
					numMisplaced++;
				}
			}
		}
		
		return numMisplaced;
	}
	
	public static int countManhattan(ArrayList<ArrayList<Integer>> goal, ArrayList<ArrayList<Integer>> curr) {
		int manhattan = 0;
		for(int i = 0; i < puzzleDim; i++) {
			for(int j = 0; j < puzzleDim; j++) {
				if(curr.get(i).get(j) == 0 || curr.get(i).get(j) == goal.get(i).get(j)) continue;
				else {
					switch(curr.get(i).get(j)) {
					
					case 1:
						manhattan += (i + 0 + j + 0);
						break;
					case 2:
						manhattan += (i + 0 + j + 1);
						break;
					case 3:
						manhattan += (i + 0 + j + 2);
						break;
					case 4:
						manhattan += (i + 1 + j + 0);
						break;
					case 5:
						manhattan += (i + 1 + j + 1);
						break;
					case 6:
						manhattan += (i + 1 + j + 2);
						break;
					case 7:
						manhattan += (i + 2 + j + 0);
						break;
					case 8:
						manhattan += (i + 2 + j + 1);
						break;
					default:
						break;
					}
				}
			}
		}
		
		return manhattan;
	}
	public static void UniformCostSearch(ArrayList<ArrayList<Integer>> goalState, HashMap<ArrayList<ArrayList<Integer>>, Integer> seenStates, PriorityQueue<State> next) {

		boolean isSolvable = false;
		int maxDepth = 0;
		State finished = null;
		int numExpansions = 0; //each while loop iteration is an expanded node
		//loop displays each state and current depth on each iteration
		Instant insta = Instant.now();
		long startTime = insta.toEpochMilli();
		State start = next.peek();
		
		while(!next.isEmpty() && !isSolvable) { //loops until queue is empty (unsolvable) or goal state is achieved (solvable)
			State curr = next.remove();
			maxDepth = Math.max(maxDepth,  curr.depth);
			System.out.print("Depth: " + curr.depth + "\n");
			System.out.print("\nExpanding State with lowest depth:\n\n");
			for(int i = 0; i < puzzleDim; i++) {
				for(int j = 0; j < puzzleDim; j++) {
					System.out.print(curr.puzzle.get(i).get(j) + "  " );
				}
				System.out.println();
			}
			isSolvable = isGoal(curr.puzzle, goalState);
			if(isSolvable) finished = curr;
			uniformSearch(curr, seenStates, next);	
			numExpansions++;
		}
		insta = Instant.now();
		long endTime = insta.toEpochMilli();
		if(isSolvable) { //if we can solve the puzzle we will print some stats for it
			
			
			System.out.print("\nSolved!\nDepth of Solution: " + finished.depth + "\nTime Elapsed ≈ " + (double)(endTime - startTime)/1000 + " sec\nNumber of Nodes Expanded = " + numExpansions + "\n");
			System.out.println("Starting puzzle: ");
			for(int i = 0; i < puzzleDim; i++){ //printing the initial state as additional info
				for(int j = 0; j < puzzleDim; j++){
					System.out.print(start.puzzle.get(i).get(j) + "  ");
				}
				System.out.println();
			}
		
		}else System.out.print("\nNo solution for given input.\nMax Depth Reached: " + maxDepth + "\nTime Elapsed ≈ " + (double)(endTime - startTime)/1000);
		
		
	}
	
	public static void MisplacedTileSearch(ArrayList<ArrayList<Integer>> goalState, HashMap<ArrayList<ArrayList<Integer>>, Integer> seenStates, PriorityQueue<State> next){ //works the same as Uniform but calls misplacedSearch instead
		
		boolean isSolvable = false;
		int maxDepth = 0;
		State finished = null;
		int numExpansions = 0; //each while loop iteration is an expanded node
		//loop displays each state and current depth on each iteration
		Instant insta = Instant.now();
		long startTime = insta.toEpochMilli();
		State start = next.peek();
		
		while(!next.isEmpty() && !isSolvable) { //loops until queue is empty (unsolvable) or goal state is achieved (solvable)
			State curr = next.remove();
			maxDepth = Math.max(maxDepth,  curr.depth);
			System.out.print("Depth: " + curr.depth + "\n");
			System.out.print("\nExpanding State with lowest heuristic:\n\n");
			for(int i = 0; i < puzzleDim; i++) {
				for(int j = 0; j < puzzleDim; j++) {
					System.out.print(curr.puzzle.get(i).get(j) + "  " );
				}
				System.out.println();
			}
			isSolvable = isGoal(curr.puzzle, goalState);
			if(isSolvable) finished = curr;
			misplacedSearch(curr, seenStates, next, goalState);	
			numExpansions++;
		}
		insta = Instant.now();
		long endTime = insta.toEpochMilli();
		if(isSolvable) { //if we can solve the puzzle we will print some stats for it
			
			
			System.out.print("\nSolved!\nDepth of Solution: " + finished.depth + "\nTime Elapsed ≈ " + (double)(endTime - startTime)/1000 + " sec\nNumber of Nodes Expanded = " + numExpansions + "\n");
			System.out.println("Starting puzzle: ");
			for(int i = 0; i < puzzleDim; i++){ //printing the initial state as additional info
				for(int j = 0; j < puzzleDim; j++){
					System.out.print(start.puzzle.get(i).get(j) + "  ");
				}
				System.out.println();
			}
		
		}else System.out.print("\nNo solution for given input.\nMax Depth Reached: " + maxDepth + "\nTime Elapsed ≈ " + (double)(endTime - startTime)/1000);
	}
	
	public static void ManhattanSearch(ArrayList<ArrayList<Integer>> goalState, HashMap<ArrayList<ArrayList<Integer>>, Integer> seenStates, PriorityQueue<State> next) {
		boolean isSolvable = false;
		int maxDepth = 0;
		State finished = null;
		int numExpansions = 0; //each while loop iteration is an expanded node
		//loop displays each state and current depth on each iteration
		Instant insta = Instant.now();
		long startTime = insta.toEpochMilli();
		State start = next.peek();
		
		while(!next.isEmpty() && !isSolvable) { //loops until queue is empty (unsolvable) or goal state is achieved (solvable)
			State curr = next.remove();
			maxDepth = Math.max(maxDepth,  curr.depth);
			System.out.print("Depth: " + curr.depth + "\n");
			System.out.print("\nExpanding State with lowest heuristic:\n\n");
			for(int i = 0; i < puzzleDim; i++) {
				for(int j = 0; j < puzzleDim; j++) {
					System.out.print(curr.puzzle.get(i).get(j) + "  " );
				}
				System.out.println();
			}
			isSolvable = isGoal(curr.puzzle, goalState);
			if(isSolvable) finished = curr;
			manhattanSeek(curr, seenStates, next, goalState);	
			numExpansions++;
		}
		insta = Instant.now();
		long endTime = insta.toEpochMilli();
		if(isSolvable) { //if we can solve the puzzle we will print some stats for it
			
			
			System.out.print("\nSolved!\nDepth of Solution: " + finished.depth + "\nTime Elapsed ≈ " + (double)(endTime - startTime)/1000 + " sec\nNumber of Nodes Expanded = " + numExpansions + "\n");
			System.out.println("Starting puzzle: ");
			for(int i = 0; i < puzzleDim; i++){ //printing the initial state as additional info
				for(int j = 0; j < puzzleDim; j++){
					System.out.print(start.puzzle.get(i).get(j) + "  ");
				}
				System.out.println();
			}
		
		}else System.out.print("\nNo solution for given input.\nMax Depth Reached: " + maxDepth + "\nTime Elapsed ≈ " + (double)(endTime - startTime)/1000);
	}
	
	public static void main(String[] args) {
		PriorityQueue<State> next = null;
		ArrayList<ArrayList<Integer>>  solved = new ArrayList<ArrayList<Integer>>() ; //this is the goal state
		HashMap<ArrayList<ArrayList<Integer>>, Integer> seen = new HashMap<>();	//this will contain previously seen states
		int input = -1;
		int desiredSearch = -1;
		System.out.println("Welcome to Jordan Kuschner's 8-puzzle solver for Professor Keogh's CS170 in Fall 2022");
		System.out.println("Desired Goal State: \n");
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

		System.out.print("\nSelect a search algorithm. Enter 1 for Uniform Cost Search, 2 for Misplaced Tile Search, or 3 for Manhattan Search.\n");
		desiredSearch = in.nextInt();
		
		//sets the right Comparator for the Priority Queue
		if(desiredSearch == 1) {
			next = new PriorityQueue<State>(new StateCompareDepth());
		}else if(desiredSearch == 2) {
			next = new PriorityQueue<State>(new StateCompareMisplace());
		}else if(desiredSearch == 3) {
			next = new PriorityQueue<State>(new StateCompareManhattan());
		}
		
		
		System.out.print("Enter 1 to enter a custom puzzle or 2 to use a default puzzle\n");
		input = in.nextInt();
		
		if(input == 2) {
			
			System.out.print("You selected the default input option.\n Enter a difficulty level from 1-5 with 1 being 'Trivial' and 5 being 'Oh Boy' difficulty \n");
			input = in.nextInt();
			if(input == 1) {
				System.out.print("You selected Trivial difficulty");
		ArrayList<ArrayList<Integer>> trivial = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		ArrayList<Integer> temp2 = new ArrayList<Integer>(Arrays.asList(4, 5, 6));
		ArrayList<Integer> temp3 = new ArrayList<Integer>(Arrays.asList(7, 8, 0));
		trivial.add(temp1);
		trivial.add(temp2);
		trivial.add(temp3);
		State start = new State(trivial, 0, 2, 2, countMisplaced(solved, trivial), countManhattan(solved, trivial));
		next.add(start);
			}else if(input == 2) {
				System.out.print("You selected Very Easy difficulty\n");
				ArrayList<ArrayList<Integer>> veryEasy = new ArrayList<ArrayList<Integer>>();
				ArrayList<Integer> temp1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
				ArrayList<Integer> temp2 = new ArrayList<Integer>(Arrays.asList(4, 5, 6));
				ArrayList<Integer> temp3 = new ArrayList<Integer>(Arrays.asList(7, 0, 8));
				veryEasy.add(temp1);
				veryEasy.add(temp2);
				veryEasy.add(temp3);
				State start = new State(veryEasy, 0, 2, 1,countMisplaced(solved, veryEasy), countManhattan(solved, veryEasy));
				next.add(start);
			}else if(input == 3) {
				System.out.print("You selected Easy difficulty\n");
				ArrayList<ArrayList<Integer>> easy = new ArrayList<ArrayList<Integer>>();
				ArrayList<Integer> temp1 = new ArrayList<Integer>(Arrays.asList(1, 2, 0));
				ArrayList<Integer> temp2 = new ArrayList<Integer>(Arrays.asList(4, 5, 3));
				ArrayList<Integer> temp3 = new ArrayList<Integer>(Arrays.asList(7, 8, 6));
				easy.add(temp1);
				easy.add(temp2);
				easy.add(temp3);
				State start = new State(easy, 0, 0, 2, countMisplaced(solved, easy), countManhattan(solved, easy));
				next.add(start);
			}else if(input == 4) {
				System.out.print("You selected Doable difficulty\n");
				ArrayList<ArrayList<Integer>> doable = new ArrayList<ArrayList<Integer>>();
				ArrayList<Integer> temp1 = new ArrayList<Integer>(Arrays.asList(0, 1, 2));
				ArrayList<Integer> temp2 = new ArrayList<Integer>(Arrays.asList(4, 5, 3));
				ArrayList<Integer> temp3 = new ArrayList<Integer>(Arrays.asList(7, 8, 6));
				doable.add(temp1);
				doable.add(temp2);
				doable.add(temp3);
				State start = new State(doable, 0, 0, countMisplaced(solved, doable), countManhattan(solved, doable));
				next.add(start);
			}else if(input == 5) {
				System.out.print("You selected Oh Boy difficulty\n");
				ArrayList<ArrayList<Integer>> ohBoy = new ArrayList<ArrayList<Integer>>();
				ArrayList<Integer> temp1 = new ArrayList<Integer>(Arrays.asList(8, 7, 1));
				ArrayList<Integer> temp2 = new ArrayList<Integer>(Arrays.asList(6, 0, 2));
				ArrayList<Integer> temp3 = new ArrayList<Integer>(Arrays.asList(5, 4, 3));
				ohBoy.add(temp1);
				ohBoy.add(temp2);
				ohBoy.add(temp3);
				State start = new State(ohBoy, 0, 1, 1, countMisplaced(solved, ohBoy), countManhattan(solved, ohBoy));
				next.add(start);
			}
		}else if(input == 1) {
			System.out.print("\nYou selected the custom input option.\n");
			System.out.print("Please enter the puzzle start from L to R, Top to Bottom. Enter '0' to represent the empty space. One value per line.\n\n");
			ArrayList<ArrayList<Integer>>  puzzle = new ArrayList<ArrayList<Integer>>() ; //this will be the initial state
			for(int i = 0; i < puzzleDim; i++){ //The User enters in the initial state here, 1 number per line with 0 representing the blank space
				ArrayList<Integer> temp = new ArrayList<Integer>();
				puzzle.add(temp);
				for(int j = 0; j < puzzleDim; j++){
					temp.add(in.nextInt());
				}
				
			}
			State start = new State(puzzle, 0, countMisplaced(solved, puzzle), countManhattan(solved, puzzle), 0, 0, 0, 0);
			next.add(start);
		}
		
		
		
		
		
		
		
		
	
	
		if(desiredSearch == 1) {
			UniformCostSearch(solved, seen, next);
		}else if(desiredSearch == 2) {
			MisplacedTileSearch(solved, seen, next);
		}else if(desiredSearch == 3) {
			ManhattanSearch(solved, seen, next);
		}
		
		
		
		
		 //otherwise inform the user it is not solvable, say how deep it searched and for how long
		
		
	


	

		



	}
}

