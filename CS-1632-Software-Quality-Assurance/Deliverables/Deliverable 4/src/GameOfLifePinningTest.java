import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameOfLifePinningTest {
	/*
	 * READ ME: You may need to write pinning tests for methods from multiple
	 * classes, if you decide to refactor methods from multiple classes.
	 * 
	 * In general, a pinning test doesn't necessarily have to be a unit test; it can
	 * be an end-to-end test that spans multiple classes that you slap on quickly
	 * for the purposes of refactoring. The end-to-end pinning test is gradually
	 * refined into more high quality unit tests. Sometimes this is necessary
	 * because writing unit tests itself requires refactoring to make the code more
	 * testable (e.g. dependency injection), and you need a temporary end-to-end
	 * pinning test to protect the code base meanwhile.
	 * 
	 * For this deliverable, there is no reason you cannot write unit tests for
	 * pinning tests as the dependency injection(s) has already been done for you.
	 * You are required to localize each pinning unit test within the tested class
	 * as we did for Deliverable 2 (meaning it should not exercise any code from
	 * external classes). You will have to use Mockito mock objects to achieve this.
	 * 
	 * Also, you may have to use behavior verification instead of state verification
	 * to test some methods because the state change happens within a mocked
	 * external object. Remember that you can use behavior verification only on
	 * mocked objects (technically, you can use Mockito.verify on real objects too
	 * using something called a Spy, but you wouldn't need to go to that length for
	 * this deliverable).
	 */

	/* TODO: Declare all variables required for the test fixture. */
	MainPanel testMP;
	Cell[][] testCells, testBackupCells;

	@Before
	public void setUp() {

		/*
		 * TODO: initialize the text fixture. For the initial pattern, use the "blinker"
		 * pattern shown in:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns
		 * The actual pattern GIF is at:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#/media/File:Game_of_life_blinker.gif
		 * Start from the vertical bar on a 5X5 matrix as shown in the GIF.
		 */

		//Initialize the class we're testing
		testMP = new MainPanel(5);

		//Initialize the array
		testCells = new Cell[5][5];

		//Fill array with mocked Cells
		for( int i = 0; i < 5; i++)
			for( int j = 0; j < 5; j++) {
				testCells[i][j] = mock(Cell.class);
			}

		//Set initial pattern
		when(testCells[1][2].getAlive()).thenReturn(true);
		when(testCells[2][2].getAlive()).thenReturn(true);
		when(testCells[3][2].getAlive()).thenReturn(true);

		//Set the Main Panel's Cells as the mocked Cells above
		testMP.setCells(testCells);
	}

	@After
	public void tearDown(){

	}

	/* TODO: Write the three pinning unit tests for the three optimized methods */

	/**
	 * Test case for boolean iterateCell(int x, int y).
	 * Preconditions: Following Cells are alive: testCells[2][1], testCells[2][2], testCells[2][3]
	 * Execution Steps: Call iterateCell(1, 2)
	 * Post Conditions: iterateCell() returns false
	 */

	@Test
	public void testIterateCellAliveThenDead() {
		//Preconditions

		//Execution Steps
		boolean isAlive = testMP.iterateCell(1,2);


		//Post Conditions
		assertFalse("Cell located at testCells[2][1] should be dead because it only has one neighbor", isAlive);
	}

	/**
	 * Test case for boolean iterateCell(int x, int y).
	 * Preconditions: Following Cells are alive: testCells[2][1], testCells[2][2], testCells[2][3]
	 * Execution Steps: Call iterateCell(2, 1)
	 * Post Conditions: iterateCell() returns true
	 */
	@Test
	public void testIterateCellDeadThenAlive() {
		//Preconditions

		//Execution Steps
		boolean isAlive = testMP.iterateCell(2,1);

		//Post Conditions
		assertTrue("Cell located at testCells[1][2] should be alive because it has three neighbors", isAlive);
	}

	/**
	 * Test case for calculateNextIteration().
	 * Preconditions: Following Cells are alive: testCells[2][1], testCells[2][2], testCells[2][3]
	 * Execution Steps: Call calculateNextIteration()
	 * Post Conditions: Verify that setAlive(boolean bool) attempts to set the follow:
	 * 		_cells[2][1] = true (alive)
	 * 		_cells[2][3] = true (alive)
	 * 		_cells[1][2] = false (dead)
	 * 		_cells[3][2] = false (dead)
	 */
	@Test
	public void testCalculateNextIteration() {
		//Preconditions
		testCells = testMP.getCells();

		//Execution Steps
		testMP.calculateNextIteration();

		//Post Conditions
		Mockito.verify(testCells[1][2], times(1)).setAlive(false);
		Mockito.verify(testCells[3][2], times(1)).setAlive(false);
		Mockito.verify(testCells[2][1], times(1)).setAlive(true);
		Mockito.verify(testCells[2][3], times(1)).setAlive(true);
	}

	/**
	 * Test case for backup().
	 * Preconditions: Following Cells are alive: testCells[1][2], testCells[2][2], testCells[3][2]
	 * Execution Steps: Call backup()
	 * Post Conditions: Assert that _backupCells[2][2] and _cells[2][2] are not equal (different memory locations)
	 */
	@Test
	public void testBackup() {
		//Preconditions
		testCells = testMP.getCells();

		//Execution Steps
		testMP.backup();
		testBackupCells = testMP.getBackupCells();

		//Post Conditions
		assertNotEquals(testBackupCells[2][2], testCells[2][2]);
		assertTrue(testBackupCells[1][2].getAlive());
		assertTrue(testBackupCells[2][2].getAlive());
		assertTrue(testBackupCells[3][2].getAlive());
	}
}
