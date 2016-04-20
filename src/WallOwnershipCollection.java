import java.util.ArrayList;
import java.util.Iterator;

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
