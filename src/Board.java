

public class Board {

	private Player player1;
	private Player player2;

	private Player play[];

	// 2D array for Position
	private Position poss[][];

	private int turn = 0;

	/*
	 * Constructor for a 2 players board
	 * 
	 */
	public Board() {

		play = new Player[2];

		player1 = new Player(4, 0);
		play[0] = player1;
		player2 = new Player(4, 8);
		play[1] = player2;

		poss = new Position[9][9];
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				poss[x][y] = new Position(x, y);
			}
		}
	}

	public void moveRight() {
		play[turn].setX(play[turn].getX() + 1);
		turn = (turn + 1) % 2;
	}

	public void moveLeft() {
		play[turn].setX(play[turn].getX() - 1);
		turn = (turn + 1) % 2;
	}

	public void moveUp() {
		play[turn].setY(play[turn].getY() - 1);
		turn = (turn + 1) % 2;
	}

	public void moveDown() {
		play[turn].setY(play[turn].getY() - 1);
		turn = (turn + 1) % 2;
	}

	public void placeTopWall(int x, int y) {
		poss[x][y].placeTopWall();
		if(y != 9){poss[x][y+1].placeBotWall();}
		turn = (turn + 1) % 2;
	}

	public void placeBotWall(int x, int y) {
		poss[x][y].placeBotWall();		
		if(y != 0){poss[x][y-1].placeTopWall();}		
		turn = (turn + 1) % 2;
	}

	public void placeRightWall(int x, int y) {
		poss[x][y].placeRightWall();		
		if(x != 9){poss[x+1][y].placeLeftWall();}		
		turn = (turn + 1) % 2;
	}

	public void placeLefttWall(int x, int y) {
		poss[x-1][y-1].placeLeftWall();		
		if(x != 0){poss[x-1][y].placeRightWall();}		
		turn = (turn + 1) % 2;
	}
}
