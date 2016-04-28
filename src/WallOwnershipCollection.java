import java.util.ArrayList;
import java.util.Iterator;

/**
 * WallOwnerShip collection stores every WallOwnershipRecord into an ArrayList. It allows
 * you to add and remove records and retrieve the size of a record. You can also retrieve
 * a record from a given position
 *
 * @author Ben Lawton
 */
public class WallOwnershipCollection {

	private ArrayList<WallOwnershipRecord> ownershipRecords;

	public WallOwnershipCollection() {
		this.ownershipRecords = new ArrayList<WallOwnershipRecord>();
	}

	public void addRecord(WallOwnershipRecord record) {
		if (!ownershipRecords.contains(record)) {
			this.ownershipRecords.add(record);
		}
	}

	public int size() {
		return this.ownershipRecords.size();
	}

	public void removeRecord(Position position, WallPlacement orientation) {
		Iterator<WallOwnershipRecord> iter = ownershipRecords.iterator();
		while (iter.hasNext()) {
			WallOwnershipRecord record = iter.next();

			if ((record.getTopLeftCoveredPosition() == position) && (record.getWallOrientation() == orientation)) {
				iter.remove();
			}
		}
	}

	/**
	 * Get the wall ownership record for a specified position
	 * @param x The X coordinate to the top left of the wall
	 * @param y The Y coordinate to the top left of the wall
     * @return The ownership record for the wall
     */
	public WallOwnershipRecord getRecordByCoordinates(int x, int y) {
		WallOwnershipRecord ownershipRecord = null;
		for (WallOwnershipRecord record : this.ownershipRecords) {
			if ((record.getTopLeftCoveredPosition().getX() == x) && (record.getTopLeftCoveredPosition().getY() == y)) {
				ownershipRecord = record;
			}
		}
		return ownershipRecord;
	}

}
