import java.io.*;
import java.util.*;


class Main
{
static Scanner in = new Scanner(System.in);
//	System.out.print("Please enter puzzle dimension: \n Ex: '3' for 8-puzzle, '4' for 15-puzzle, '5' for 24-puzzle\n");
static int puzzleDim = 3;
static class State{
  int[][] puzzle;
  int emptyI;
  int emptyJ;
  int depth;
  
  State(int[][] p){
        this.puzzle = p;
        a:
        for(int i = 0; i < puzzleDim; i++){
                for(int j = 0; j < puzzleDim; j++){
                        if(puzzle[i][j] == 0){
                                this.emptyI = i;
                                this.emptyJ = j;
                                break a;
                        }
                }
        }
  }

}

	static State swap(State curr,  int a, int b, int ai, int aj, int bi, int bj){
		int temp = a;
		curr.puzzle[ai][aj] = b;
		curr.puzzle[bi][bj] = temp;
		if(a == 0) {
			curr.emptyI = bi;
			curr.emptyJ = bj;
		}else if(b == 0) {
			curr.emptyI = ai;
			curr.emptyJ = aj;
		}
		
		return curr;
		
		
	}

	static boolean isGoal(int[][] puzzle, int[][] goal){
		for(int i = 0; i < puzzleDim; i++){
			for(int j = 0; j < puzzleDim; j++){
				if(puzzle[i][j] != goal[i][j]) return false;
			}
		}
		
		return true;
	}
	
	

	public static void main(String[] args) {
//		int[][] puzzle = { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };
		int[][] puzzle = new int[puzzleDim][puzzleDim];
		int[][] solved = new int[puzzleDim][puzzleDim];
		int goalNum = 1;
		for(int i = 0; i < puzzleDim; i++){
			for(int j = 0; j < puzzleDim; j++){
				if(i == j && j == puzzleDim - 1){
					solved[i][j] = 0;
				}else{
				solved[i][j] = goalNum;
				}
				goalNum++;
				System.out.print(solved[i][j] + "  ");
				}
		System.out.println();
		}
		State goal = new State(solved);
		Set<State> seen = new HashSet<State>();	
		Queue<State> cost = new LinkedList<>();
		
		System.out.print("Please enter the puzzle start from L to R, Top to Bottom. Enter '0' to represent the empty space\n\n");
	for(int i = 0; i < puzzleDim; i++){
		for(int j = 0; j < puzzleDim; j++){
			puzzle[i][j] = in.nextInt();
		}
}
				

State root = new State(puzzle);
		root.depth = 1;
		State curr = root;
		
		while(!isGoal(curr.puzzle, solved)){
		seen.add(curr);	
		System.out.print("Depth: " + curr.depth + "\n");
	   if(curr.emptyI < puzzleDim - 1){
			System.out.print("here");
			State nextState = swap(curr, puzzle[curr.emptyI][curr.emptyJ], puzzle[curr.emptyI + 1][curr.emptyJ], curr.emptyI, curr.emptyJ, curr.emptyI + 1, curr.emptyJ);
			nextState.depth = curr.depth + 1;

	//		if(!seen.contains(nextState)){
                        System.out.print("Adding to Q\n");

			cost.add(nextState);
	//	   }
		}

		 if(curr.emptyI > 0){
                        State nextState = swap(curr, puzzle[curr.emptyI][curr.emptyJ], puzzle[curr.emptyI - 1][curr.emptyJ], curr.emptyI, curr.emptyJ, curr.emptyI - 1, curr.emptyJ);
            			nextState.depth = curr.depth + 1;

                        if(!seen.contains(nextState)){
                        System.out.print("Adding to Q\n");

                        cost.add(nextState);
                   }
                }

		 if(curr.emptyJ < puzzleDim - 1){
                        State nextState = swap(curr, puzzle[curr.emptyI][curr.emptyJ], puzzle[curr.emptyI][curr.emptyJ + 1], curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ + 1);
            			nextState.depth = curr.depth + 1;

                        if(!seen.contains(nextState)){
		                        System.out.print("Adding to Q\n");

                        cost.add(nextState);

                   }
                }

		 if(curr.emptyJ > 0){
                        State nextState = swap(curr, puzzle[curr.emptyI][curr.emptyJ], puzzle[curr.emptyI][curr.emptyJ-1], curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ - 1);
            			nextState.depth = curr.depth + 1;

                        if(!seen.contains(nextState)){
			System.out.print("Adding to Q\n");
                        cost.add(nextState);
                   }
                }
		
		curr = cost.remove();
	}


		for(int i = 0; i < puzzleDim; i++){
			for(int j = 0; j < puzzleDim; j++){
				System.out.print(puzzle[i][j] + "  ");
	}
				System.out.println();

	}



	}
}


