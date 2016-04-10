
public class WallOwnershipRecord {

	private int playerID;

	private Position coveredPos;
	private PositionWallLocation posBorder;

	public WallOwnershipRecord(int playerID, Position coveredPos, PositionWallLocation posBorder) {
		this.playerID = playerID;

		this.coveredPos = coveredPos;
		this.posBorder = posBorder;
	}

	public int getPlayerID() {
		return this.playerID;
	}

	public Position getCoveredPosition() {
		return this.coveredPos;
	}

	public PositionWallLocation getPositionBorderValue() {
		return this.posBorder;
	}
}
