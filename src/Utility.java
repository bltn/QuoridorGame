import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import sun.applet.Main;

/**
 *
 * @author Thai Hoang
 *
 * @version 06/04/2016
 */

public class Utility {

	public Utility() {}

	public static void main(String[] args) {
		Utility u = new Utility();
		Position start = new Position(10, 10);
		Position goal = new Position(0, 0);
	}

	public static int[][] grid1 = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
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
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, };

	public boolean A(int[][] grid, Position start, int finish) {

		Comparator<Position> comparator = new PositionComparator();
		PositionComparator.goal=finish;

		PriorityQueue<Position> queue = new PriorityQueue<Position>(80, comparator);

		boolean done = false; // set true when the finish position is reached
		Position pos;
		queue.add(start);
		grid[start.getX()][start.getY()] = 2;
		while (!queue.isEmpty()) {
			pos = queue.remove();
			if (pos.getX() == finish) {
				done = true;
				break;
			} else {
				int length = pos.getCostsofar()+1;

				if (grid[pos.getX()][pos.getY() - 1] == 1)
					add_new_pos(grid, queue, pos.getX(), pos.getY() - 2, length);
				if (grid[pos.getX()][pos.getY() + 1] == 1)
					add_new_pos(grid, queue, pos.getX(), pos.getY() + 2, length);
				if (grid[pos.getX() - 1][pos.getY()] == 1)
					add_new_pos(grid, queue, pos.getX() - 2, pos.getY(), length);
				if (grid[pos.getX() + 1][pos.getY()] == 1)
					add_new_pos(grid, queue, pos.getX() + 2, pos.getY(), length);
			}
		}
		reset(grid);
		return done;
	}

	private void reset(int[][] grid) {
		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[row].length; column++)
				if (grid[row][column] > 1)
					grid[row][column] = 1;
		}
	}

	private void add_new_pos(int[][] grid, PriorityQueue<Position> stack, int x, int y, int length) {
		if (valid(grid, x, y)) {
			Position next = new Position(x, y);
			next.setCostsofar(length);
			stack.add(next);
			grid[x][y] = 2;
		}
	}

	private boolean valid(int[][] grid, int row, int column) {
		boolean result = false;
		/* Check if cell is in the bounds of the maze */
		if (row >= 0 && row < grid.length && column >= 0 && column < grid[row].length)
			/* Check if cell is not blocked and not previously tried */
			if (grid[row][column] == 1)
				result = true;
		return result;
	}

	public String toString(int[][] grid) {
		String result = "\n";
		// Print the grid line by line
		for (int row = 0; row < grid.length; row++) {
			// Print each element in a line
			for (int column = 0; column < grid[row].length; column++)
				result += grid[row][column] + ".";
			result += "\n";
		}
		return result;
	}

	public static int shortestPathLenght(Position[][] grid, Position start, int finish) {
		//Stack<Position> stack = new Stack();
		Comparator<Position> comparator = new PositionComparator();
		PositionComparator.goal=finish;
		PriorityQueue<Position> queue = new PriorityQueue<Position>(80, comparator);

		int done = 0; // set true when the finish position is reached
		Position startgrid = grid[start.getY()][start.getX()];
		startgrid.setCostsofar(0);
		queue.add(startgrid);

		while (!queue.isEmpty()) {
			Position pos = queue.remove();
			grid[pos.getY()][pos.getX()].setVisited(true); // this cell has been tried
			if (pos.getY() == finish) {
				done = pos.getCostsofar();
				break;
			} else {
				int length = pos.getCostsofar()+1;
				if (!pos.hasTopWall())
					push_new_pos(grid, queue, pos.getY() - 1, pos.getX(),length);
				if (!pos.hasBottomWall())
					push_new_pos(grid, queue, pos.getY() + 1, pos.getX(),length);
				if (!pos.hasLeftWall())
					push_new_pos(grid, queue, pos.getY(), pos.getX() - 1,length);
				if (!pos.hasRightWall())
					push_new_pos(grid, queue, pos.getY(), pos.getX() + 1,length);
			}
		}
		reset(grid);
		return done;
	}

	public static boolean shortesrPath(int lenght,Position[][] grid, Position start, int finish) {
		Comparator<Position> comparator = new PositionComparator();
		PositionComparator.goal=finish;

		PriorityQueue<Position> queue = new PriorityQueue<Position>(80, comparator);
		Queue<Position> queue1 = new LinkedList<Position>();
		boolean done = false; // set true when the finish position is reached
		Position startgrid = grid[start.getY()][start.getX()];
		startgrid.setCostsofar(0);
		queue.add(startgrid);

		while (!queue.isEmpty()) {
			Position pos = queue.remove();
			grid[pos.getY()][pos.getX()].setVisited(true); // this cell has been tried

			if (pos.getY() == finish) {
				done = true;
				lenght=pos.getCostsofar();
				break;
			} else {
				int length = pos.getCostsofar()+1;
				if (!pos.hasTopWall())
					push_new_pos(grid, queue, pos.getY() - 1, pos.getX(),length);
				if (!pos.hasBottomWall())
					push_new_pos(grid, queue, pos.getY() + 1, pos.getX(),length);
				if (!pos.hasLeftWall())
					push_new_pos(grid, queue, pos.getY(), pos.getX() - 1,length);
				if (!pos.hasRightWall())
					push_new_pos(grid, queue, pos.getY(), pos.getX() + 1,length);
			}
		}
		reset(grid);
		return done;
	}

	public static boolean AstarSearch(Position[][] grid, Position start, int finish) {
		Comparator<Position> comparator = new PositionComparator();
		PositionComparator.goal=finish;

		PriorityQueue<Position> queue = new PriorityQueue<Position>(80, comparator);
		Queue<Position> queue1 = new LinkedList<Position>();
		boolean done = false; // set true when the finish position is reached
		Position startgrid = grid[start.getY()][start.getX()];
		startgrid.setCostsofar(0);
		queue.add(startgrid);

		while (!queue.isEmpty()) {
			Position pos = queue.remove();
			grid[pos.getY()][pos.getX()].setVisited(true); // this cell has been tried

			if (pos.getY() == finish) {
				done = true;
				break;
			} else {
				int length = pos.getCostsofar()+1;

				if (!pos.hasTopWall())
					push_new_pos(grid, queue, pos.getY() - 1, pos.getX(),length);
				if (!pos.hasBottomWall())
					push_new_pos(grid, queue, pos.getY() + 1, pos.getX(),length);
				if (!pos.hasLeftWall())
					push_new_pos(grid, queue, pos.getY(), pos.getX() - 1,length);
				if (!pos.hasRightWall())
					push_new_pos(grid, queue, pos.getY(), pos.getX() + 1,length);
			}
		}
		reset(grid);
		return done;
	}

	private static String indexof(Position[][] arr,Position pos){
		String index="";
		for (int i = 0 ; i < arr.length; i++)
		    for(int j = 0 ; j < arr[i].length ; j++)
		    {
		         if ( arr[i][j] == pos)
		         {		index=i+":"+j+"";
		         	break;
		         }
		    }
		return index;
	}

	private static void reset(Position[][] grid) {
		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[row].length; column++)
				grid[row][column].setVisited(false);
		}
	}

	private static void push_new_pos(Position[][] grid, PriorityQueue<Position> queue, int x, int y,int length) {
		if (valid(grid, x, y)) {
			grid[x][y].setVisited(true);
			grid[x][y].setCostsofar(length);
			queue.add(grid[x][y]);
		}
	}

	private static boolean valid(Position[][] grid, int row, int column) {
		boolean result = false;
		/* Check if cell is in the bounds of the maze */
		if (row >= 0 && row < grid.length && column >= 0 && column < grid[row].length)
			/* Check if cell is not blocked and not previously tried */
			if (grid[row][column].isVisited() == false)
				result = true;
		return result;
	}

	public static String toString(Position[][] grid) {
		String result = "\n";
		// Print the grid line by line
		for (int row = 0; row < grid.length; row++) {
			// Print each element in a line
			for (int column = 0; column < grid[row].length; column++) {
				result += grid[row][column].getY() + "" + grid[row][column].getX();
				result += "|bottom:" + grid[row][column].hasBottomWall();
				result += "|top:" + grid[row][column].hasTopWall();
				result += "|left:" + grid[row][column].hasLeftWall();
				result += "|right:" + grid[row][column].hasRightWall() + "| ";
			}
			result += "\n";
		}
		return result;
	}


	public static Position clone(Position pos){
		Position clone = new Position(pos.getX(),pos.getY());

			if(pos.hasBottomWall())clone.setHasBottomWall(true);
			if(!pos.hasBottomWall())clone.setHasBottomWall(false);
			if(pos.hasTopWall())clone.setHasTopWall(true);
			if(!pos.hasTopWall())clone.setHasTopWall(false);
			if(pos.hasLeftWall())clone.setHasLeftWall(true);
			if(!pos.hasLeftWall())clone.setHasLeftWall(false);
			if(pos.hasRightWall())clone.setHasRightWall(true);
			if(!pos.hasRightWall())clone.setHasRightWall(false);

		return clone;
	}

	public static Position[][] clone(Position[][] grid){
		Position[][] clone = new Position[grid.length][grid[0].length];

		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[0].length;column++) {
				clone[row][column]=clone(grid[row][column]);
			}
		}
		return clone;
	}

	private static class PositionComparator implements Comparator<Position>
	{
		public static int goal;
		public static int costsofar;

		@Override
	    public int compare(Position x, Position y)
	    {
			int Xgoal = Math.abs((goal-x.getY()));

			int X = x.getCostsofar()+Xgoal;//(1)*2+


			int Ygoal = Math.abs((goal-y.getY()));

			int Y = y.getCostsofar()+Ygoal;//(1)*2+

			//-1 then 1 is good
	        if (X < Y)
	        {
	            return -1;
	        }
	        if (X > Y)
	        {
	            return 1;
	        }
	        return 0;
	    }
	}
}
