import java.util.ArrayList;

public class WallOwnershipCollection {

	private ArrayList<WallOwnershipRecord> ownershipRecords;

	public WallOwnershipCollection() {
		this.ownershipRecords = new ArrayList<WallOwnershipRecord>();
	}

	public void addRecord(WallOwnershipRecord record) {
		this.ownershipRecords.add(record);
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
