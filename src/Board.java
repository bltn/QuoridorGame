import java.util.Arrays;

public class Board {

	private HumanPawn player1;
	private HumanPawn player2;
	private HumanPawn player3;
	private HumanPawn player4;

	// 2D array to keep tracks where the walls are
	private boolean walls[][];

	/*
	 * Constructor for a 2 players board
	 * 
	 */
	public Board(String name1, String name2) {

		player1 = new HumanPawn(5, 1, 10, name1);
		player2 = new HumanPawn(5, 9, 10, name2);

		walls = new boolean[9][9];
		Arrays.fill(walls, Boolean.FALSE);
	}

	/*
	 * Constructor for a 4 players board
	 * 
	 */
	public Board(String name1, String name2, String name3, String name4) {

		player1 = new HumanPawn(5, 1, 5, name1);
		player2 = new HumanPawn(5, 9, 5, name2);
		player3 = new HumanPawn(1, 5, 5, name3);
		player4 = new HumanPawn(9, 5, 5, name4);

		walls = new boolean[9][9];
		Arrays.fill(walls, Boolean.FALSE);
	}

	public void moveRight(HumanPawn player) {
		if (walls[player.getX()][player.getY()] = false) {
			player.moveX(player.getX() + 1);
		} else {
			System.out.println("Cant move right. Theres a wall");
		}
	}

	public void moveLeft(HumanPawn player) {
		if (walls[player.getX() - 1][player.getY()] = false) {
			player.moveX(player.getX() - 1);
		} else {
			System.out.println("Cant move left. Theres a wall");
		}
	}

	public void moveUp(HumanPawn player) {
		if (walls[player.getX()][player.getY()] = false) {
			player.moveX(player.getY() + 1);
		} else {
			System.out.println("Cant move up. Theres a wall");
		}
	}

	public void moveDown(HumanPawn player) {
		if (walls[player.getX()][player.getY() - 1] = false) {
			player.moveX(player.getY() + 1);
		} else {
			System.out.println("Cant move down. Theres a wall");
		}
	}

	public void placeWall(HumanPawn player, int x1, int y1, int x2, int y2) {

		if (walls[x2][y2] == false || walls[x1][y1] == false) {
			walls[x2][y2] = true;
			walls[x1][y1] = true;
			player.setWalls(player.getWalls() - 1);
		} else {
			System.out.println("Cant place wall there. Theres already one");
		}
	}

}
