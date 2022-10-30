import java.io.*;
import java.util.*;


class Main
{
static Scanner in = new Scanner(System.in);
//	System.out.print("Please enter puzzle dimension: \n Ex: '3' for 8-puzzle, '4' for 15-puzzle, '5' for 24-puzzle\n");
static int puzzleDim = 3;
/*static class State{
  int[][] puzzle;
  int depth;
  
  
  
  State(int[][] p, int d){
        this.puzzle = p;
        this.depth = d;
        
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
  
  

}*/




	
	static int[][] swap(int[][] arr, int a, int b, int ai, int aj, int bi, int bj){
		int temp = a;
		arr[ai][aj] = b;
		arr[bi][bj] = temp;
	
		
		return arr;
		
		
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
				




		for(int i = 0; i < puzzleDim; i++){
			for(int j = 0; j < puzzleDim; j++){
				System.out.print(puzzle.get(i).get(j) + "  ");
	}
				System.out.println();

	}



	}
}


