import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import sun.applet.Main;

/**

 * @author Thai Hoang
 *
 * @version 06/04/2016
 */


public class Utility {
	
	public static void main(String[] args) {
		
		Utility u = new Utility();
		
		Position start = new Position(16, 10);
		Position goal = new Position(0, 0);
		System.out.println("start");
		u.DepthFirstSearch(grid1,start,goal);
		System.out.println("end");
    }
	
	
	
	private static int[][] grid1 =
		 { 
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		 };
		
		
	public static boolean DepthFirstSearch(int[][] grid,Position start,Position finish) {
		 Stack<Position> stack = new Stack(); 
		
		 boolean done = false; // set true when the finish position is reached
		 Position pos;
		 stack.push(start);
		 
		 while (!stack.isEmpty()) {
		 pos = stack.pop();
		 grid[pos.getX()][pos.getY()] = 2; // this cell has been tried
		 if (pos.getX() == finish.getX()) //&& pos.getY() == finish.getY())
		 done = true; 
		 else {

		 push_new_pos(grid, stack, pos.getX(), pos.getY() - 1);
		 push_new_pos(grid, stack, pos.getX(), pos.getY() + 1);
		 push_new_pos(grid, stack, pos.getX() - 1, pos.getY());
		 push_new_pos(grid, stack, pos.getX() + 1, pos.getY());
		 }
		} 
		 reset(grid);
		 System.out.println(done);
		 System.out.println(toString(grid));
		 return done;
	}

	private static void reset(int[][] grid){

		  for (int row = 0; row < grid.length; row++) {
		  for (int column = 0; column < grid[row].length; column++)
			 if(grid[row][column] == 2) 
			  grid[row][column] = 1; 
		  }
	}
		
	
	 private static void push_new_pos(int[][] grid,Stack<Position> stack,int x, int y) {
		 if (valid(grid,x, y)) {
		 stack.push(new Position(x, y));}
		 }
	
	 private static boolean valid(int[][] grid,int row, int column) {
		  boolean result = false;
		 
		  /* Check if cell is in the bounds of the maze */
		  if (row >= 0 && row < grid.length && column >= 0 && column < grid[row].length)
		 
		  /* Check if cell is not blocked and not previously tried */
		  if (grid[row][column] == 1)
			  result = true;
		 
		  return result;
		  }
	 
	 public static String toString(int[][] grid) {
		  String result = "\n";
		 
		  // Print the grid line by line
		  for (int row = 0; row < grid.length; row++) {
		  // Print each element in a line
		  for (int column = 0; column < grid[row].length; column++)
		  result += grid[row][column] + "";
		 
		  result += "\n";
		  }
		  return result;
		   }

 
	 public static boolean BreadthFirst(int[][] map,Position start,Position goal){

		 boolean result=false;
	        if(start.getX()==goal.getX() && start.getY()==goal.getY()){
	        	System.out.println("dung roi");
	        	result = true;
	        	//return true;
	        }

	        Queue<Position> queue = new LinkedList<>();
	        ArrayList<Position> explored = new ArrayList<>();
	        queue.add(start);
	        explored.add(start);

	        while(!queue.isEmpty()){
	            Position current = queue.remove();
	            if(current.equals(goal)) {
	            	System.out.println("dung roi");
	            	result = true;
	            	//return true;
	            }
	            else{
	                
	                if(hasAdjacent(map,current)){
	                	System.out.println("sai roi");
	                	result = false;
	                	//return false;
	                }	
	                else
	                    //queue.addAll(current.getChildren());
	                add_new_pos(map, queue, current.getX(), current.getY() - 1);
	                add_new_pos(map, queue, current.getX(), current.getY() + 1);
	                add_new_pos(map, queue, current.getX() - 1, current.getY());
	                add_new_pos(map, queue, current.getX() + 1, current.getY());

	            }
	            explored.add(current);
	        }
	        System.out.println("sai roi");
			return result;
	        //return false;

	    }
	 
	 private static void add_new_pos(int[][] map,Queue<Position> queue,int x, int y) {
		  if (valid(map,x, y)) {
			  queue.add(new Position(x, y));
		  }
	 	  }
	 private static boolean hasAdjacent(int[][] map,Position position){
		 
		 ArrayList<Position> adjacent = new ArrayList<>();
		 
		 if(valid(map,position.getX() + 1, position.getY())) {
			 adjacent.add(new Position(position.getX() + 1, position.getY()));
		}
		 
		 if(valid(map,position.getX() - 1, position.getY())) {
			 adjacent.add(new Position(position.getX() + 1, position.getY()));
		}
		 
		 if(valid(map,position.getX(), position.getY()- 1)) {
			 adjacent.add(new Position(position.getX() + 1, position.getY()));
		}
		 
		 if(valid(map,position.getX(), position.getY() + 1)) {
			 adjacent.add(new Position(position.getX() + 1, position.getY()));
		}
		 
		 return adjacent.isEmpty();
		 	 
	 }



}
