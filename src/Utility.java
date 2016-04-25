import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import sun.applet.Main;

/**
 * @author Thai Hoang
 * @version 06/04/2016
 */

public class Utility {

	
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


	private static Position clone(Position pos){
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

	private static Position[][] clone(Position[][] grid){
		Position[][] clone = new Position[grid.length][grid[0].length];

		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[0].length;column++) {
				clone[row][column]=clone(grid[row][column]);
			}
		}
		return clone;
	}

	public static StandardBoard clone(StandardBoard Board) {

		StandardBoard clone = new StandardBoard(2);
		Position[][] clonePositions = Utility.clone(Board.getPositions());
		clone.setPositions(clonePositions);

		clone.setPlayer1(new Player(Board.getPlayer1().getPosition(), Board.getPlayer1().getID()));
		clone.getPlayer1().setWallCount(Board.getPlayer1().getWallCount());
		clone.setPlayer2(new Player(Board.getPlayer2().getPosition(), Board.getPlayer2().getID()));
		clone.getPlayer2().setWallCount(Board.getPlayer2().getWallCount());
		if (Board.getCurrentPlayer() == Board.getPlayer1()) {
			clone.setCurrentPlayer(clone.getPlayer1());
		} else {
			clone.setCurrentPlayer(clone.getPlayer2());
		}
		return clone;
	}
	private static class PositionComparator implements Comparator<Position>
	{
		public static int goal;

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
