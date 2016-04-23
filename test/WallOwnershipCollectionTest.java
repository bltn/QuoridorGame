import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WallOwnershipCollectionTest {

	private WallOwnershipRecord record;
	private Position topLeft;
	private WallOwnershipCollection collection;

	@Before
	public void setUp() {
		topLeft = new Position(5, 5);
		record = new WallOwnershipRecord(1, topLeft, WallPlacement.HORIZONTAL);
		collection = new WallOwnershipCollection();
	}

	@Test
	public void addRecordTest() {
		int originalSize = collection.size();
		collection.addRecord(record);
		assertEquals(true, ((collection.size() == (originalSize + 1))));
	}

	@Test
	public void removeRecordTest() {
		collection.addRecord(record);
		int originalSize = collection.size();
		collection.removeRecord(record.getTopLeftCoveredPosition(), record.getWallOrientation());
		assertEquals(true, ((collection.size() == (originalSize -1 ))));
	}

	@Test
	public void getRecordByCoordinatesTest() {
		WallOwnershipRecord sampleRecord = new WallOwnershipRecord(2, new Position(1, 3), WallPlacement.VERTICAL);
		collection.addRecord(record);
		collection.addRecord(sampleRecord);
		assertEquals(collection.getRecordByCoordinates(5, 5), record);
		assertEquals(collection.getRecordByCoordinates(1, 3), sampleRecord);
	}

}
