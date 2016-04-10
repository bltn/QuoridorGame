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

	public void removeRecord(Position position, PositionWallLocation location) {
		Iterator<WallOwnershipRecord> iter = ownershipRecords.iterator();
		while (iter.hasNext()) {
			WallOwnershipRecord record = iter.next();

			if ((record.getCoveredPosition() == position) && (record.getPositionBorderValue() == location)) {
				iter.remove();
			}
		}
	}

	public WallOwnershipRecord getRecordByCoordinates(int x, int y) {
		WallOwnershipRecord ownershipRecord = null;
		for (WallOwnershipRecord record : this.ownershipRecords) {
			if ((record.getCoveredPosition().getX() == x) && (record.getCoveredPosition().getY() == y)) {
				ownershipRecord = record;
			}
		}
		return ownershipRecord;
	}

}
