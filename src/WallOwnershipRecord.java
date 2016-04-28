/**
 * WallOwnershipRecord stores the position to the top left of the wall, the orientation
 * of the wall, and the ID of the player who placed the wall.
 *
 * @author Ben Lawton
 */
public class WallOwnershipRecord {

	private int playerID;

	private Position topLeftCoveredPos;
	private WallPlacement orientation;

	public WallOwnershipRecord(int playerID, Position topLeftCoveredPos, WallPlacement orientation) {
		this.playerID = playerID;

		this.topLeftCoveredPos = topLeftCoveredPos;
		this.orientation = orientation;
	}

	public int getPlayerID() {
		return this.playerID;
	}

	public Position getTopLeftCoveredPosition() {
		return this.topLeftCoveredPos;
	}

	public WallPlacement getWallOrientation() {
		return this.orientation;
	}
}
