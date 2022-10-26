import java.io.*;
import java.util.*;


class Main
{
static final int puzzleDim = 3;
static class State{
  int[][] puzzle;
  int emptyI;
  int emptyJ;

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

	static int[][] swap(int[][] puzzle, int a, int b, int ai, int aj, int bi, int bj){
		int temp = a;
		puzzle[ai][aj] = b;
		puzzle[bi][bj] = temp;
		return puzzle;
		
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
	        int[][] solved = { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };
		State goal = new State(solved);
		Set<State> seen = new HashSet<State>();	
		Scanner in = new Scanner(System.in);
		Queue<State> cost = new LinkedList<>();
		
		System.out.print("Please enter the puzzle start from L to R, Top to Bottom. Enter '0' to represent the empty space\n\n");
	for(int i = 0; i < puzzleDim; i++){
		for(int j = 0; j < puzzleDim; j++){
			puzzle[i][j] = in.nextInt();
		}
}
		State root = new State(puzzle);
		State curr = root;
		boolean reachedGoal = false;
		int iterations = 1;
		while(!reachedGoal){
		seen.add(curr);	
		System.out.print(iterations + ": looping\n");
		reachedGoal = isGoal(curr.puzzle, solved);
		if(reachedGoal) break;
	   if(curr.emptyI < puzzleDim - 1){
			State nextState = new State(swap(puzzle, puzzle[curr.emptyI][curr.emptyJ], puzzle[curr.emptyI + 1][curr.emptyJ], curr.emptyI, curr.emptyJ, curr.emptyI + 1, curr.emptyJ));
		   if(!seen.contains(nextState)){
			cost.add(nextState);
		   }
		}

		 if(curr.emptyI > 0){
                        State nextState = new State(swap(puzzle, puzzle[curr.emptyI][curr.emptyJ], puzzle[curr.emptyI - 1][curr.emptyJ], curr.emptyI, curr.emptyJ, curr.emptyI - 1, curr.emptyJ));
                   if(!seen.contains(nextState)){
                        cost.add(nextState);
                   }
                }

		 if(curr.emptyJ < puzzleDim - 1){
                        State nextState = new State(swap(puzzle, puzzle[curr.emptyI][curr.emptyJ], puzzle[curr.emptyI][curr.emptyJ + 1], curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ + 1));
                   if(!seen.contains(nextState)){
                        cost.add(nextState);
                   }
                }

		 if(curr.emptyJ > 0){
                        State nextState = new State(swap(puzzle, puzzle[curr.emptyI][curr.emptyJ], puzzle[curr.emptyI][curr.emptyJ-1], curr.emptyI, curr.emptyJ, curr.emptyI, curr.emptyJ - 1));
                   if(!seen.contains(nextState)){
                        cost.add(nextState);
                   }
                }

		curr = cost.remove();
		reachedGoal = isGoal(curr.puzzle, solved);
		iterations++;	
	}


		for(int i = 0; i < puzzleDim; i++){
			for(int j = 0; j < puzzleDim; j++){
				System.out.print(puzzle[i][j] + "  ");
	}
				System.out.println();

	}



	}
}

