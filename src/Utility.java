
import java.util.Comparator;
import java.util.PriorityQueue;


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

	public static boolean AstarSearch(Position[][] grid, Position start, int finish) {
		Comparator<Position> comparator = new PositionComparator();
		PositionComparator.goal=finish;

		PriorityQueue<Position> queue = new PriorityQueue<Position>(80, comparator);

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
