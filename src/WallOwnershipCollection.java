import java.util.ArrayList;

public class WallOwnershipCollection {

	private ArrayList<WallOwnershipRecord> ownershipRecords;

	public WallOwnershipCollection() {
		this.ownershipRecords = new ArrayList<WallOwnershipRecord>();
	}

	public void addRecord(WallOwnershipRecord record) {
		this.ownershipRecords.add(record);
	}

}
