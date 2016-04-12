import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
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

	public static void main(String[] args) {

		Utility u = new Utility();
		Position start = new Position(16, 10);
		Position goal = new Position(0, 0);
		System.out.println("start");
		//u.DepthFirstSearch(grid1, start, goal);
		System.out.println("end");
	}

	private static int[][] grid1 = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
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

	
	public static boolean BreadthFirstSearch(int[][] grid, Position start, int finish) {
		Queue<Position> queue = new LinkedList<Position>();

		boolean done = false; // set true when the finish position is reached
		Position pos;
		queue.add(start);

		while (!queue.isEmpty()) {
			pos = queue.remove();
			grid[pos.getX()][pos.getY()] = 2; // this cell has been tried
			if (pos.getX() == finish) {
				done = true;
				break;
			} else {
				push_new_pos(grid, queue, pos.getX(), pos.getY() - 1);
				push_new_pos(grid, queue, pos.getX(), pos.getY() + 1);
				push_new_pos(grid, queue, pos.getX() - 1, pos.getY());
				push_new_pos(grid, queue, pos.getX() + 1, pos.getY());
			}
		}
		reset(grid);
		//System.out.println(done);
		//System.out.println(toString(grid));
		return done;
	}

	private static void reset(int[][] grid) {
		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[row].length; column++)
				if (grid[row][column] == 2)
					grid[row][column] = 1;
		}
	}

	private static void push_new_pos(int[][] grid, Queue<Position> queue, int x, int y) {
		if (valid(grid, x, y)) {
			queue.add(new Position(x, y));
		}
	}

	private static boolean valid(int[][] grid, int row, int column) {
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


}
