import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First mock cat object
	Cat c2; // Second mock cat object
	Cat c3; // Third mock cat object

	@Before
	public void setUp() throws Exception {
		// Turn on automatic bug injection in the Cat class, to emulate a buggy Cat.
		// Your unit tests should work regardless of these bugs if you mock all Cats.
		Cat._bugInjectionOn = true;

		// INITIALIZE THE TEST FIXTURE
		// 1. Create a new RentACat object and assign to r
		r = RentACat.createInstance();

		// 2. Create a mock Cat with ID 1 and name "Jennyanydots", assign to c1
		c1 = mock(Cat.class);

		//stubbing
		when(c1.getName()).thenReturn("Jennyanydots");
		when(c1.getId()).thenReturn(1);
		
		// 3. Create a mock Cat with ID 2 and name "Old Deuteronomy", assign to c2
		c2 = mock(Cat.class);

		//stubbing
		when(c2.getName()).thenReturn("Old Deuteronomy");
		when(c2.getId()).thenReturn(2);

		// 4. Create a mock Cat with ID 3 and name "Mistoffelees", assign to c3
		c3 = mock(Cat.class);

		//stubbing
		when(c3.getName()).thenReturn("Mistoffelees");
		when(c3.getId()).thenReturn(3);
		
		// Hint: You will have to stub the mocked Cats to make them behave as if the ID
		// is 1 and name is "Jennyanydots", etc.
	}

	@After
	public void tearDown() throws Exception {
		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 */

	@Test
	public void testGetCatNullNumCats0(){
		//Preconditions

		//Execution Steps
		Cat c = r.getCat(2);

		//Postconditions
		assertNull(c);
	}

	/**
	 * Test case for Cat getCat(int id).
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 */
	
	@Test
	public void testGetCatNumCats3() {
		//Preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		//Execution Steps
		Cat retCat = r.getCat(2);

		//Postconditions
		assertNotNull(retCat);
		assertEquals(retCat.getId(), 2);
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * Preconditions: r has no cats.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 */

	@Test
	public void testCatAvailableFalseNumCats0() {
		assertFalse(r.catAvailable(2));
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c3 is rented.
	 *                c1 and c2 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is true.
	 */

	@Test
	public void testCatAvailableTrueNumCats3() {
		//Preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		when(c1.getRented()).thenReturn(false);
		when(c2.getRented()).thenReturn(false);
	 	when(c3.getRented()).thenReturn(true);

	 	//Execution Steps
		boolean catIsAvailable = r.catAvailable(2);

		//Postconditions
		assertTrue(catIsAvailable);
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 *                c1 and c3 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 */
	
	@Test
	public void testCatAvailableFalseNumCats3() {
		//Preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		when(c1.getRented()).thenReturn(false);
		when(c2.getRented()).thenReturn(true);
		when(c3.getRented()).thenReturn(false);

		//Execution Steps
		boolean isCatAvailable = r.catAvailable(2);

		//Postconditions
		assertFalse(isCatAvailable);
	}

	/**
	 * Test case for boolean catExists(int id).
	 * Preconditions: r has no cats.
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is false.
	 */

	@Test
	public void testCatExistsFalseNumCats0() {
		//Preconditions

		//Execution Steps
		boolean catDNE = r.catExists(2);

		//Postconditions
		assertFalse(catDNE);
	}

	/**
	 * Test case for boolean catExists(int id).
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is true.
	 */
	
	@Test
	public void testCatExistsTrueNumCats3() {
		//Preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		//Execution Steps
		boolean catExists = r.catExists(2);

		//Postconditions
		assertTrue(catExists);
	}

	/**
	 * Test case for String listCats().
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 */

	@Test
	public void testListCatsNumCats0() {
		//Preconditions

		//Execution Steps
		String list = r.listCats();

		//Postconditions
		assertEquals(list, "");
	}

	/**
	 * Test case for String listCats().
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 */
	
	@Test
	public void testListCatsNumCats3() {
		//Preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		//Execution Steps
		String list = r.listCats();

		//Postconditions
		assertEquals(list, "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n");
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * Preconditions: r has no cats.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 */

	@Test
	public void testRentCatFailureNumCats0() {
		//Preconditions

		//Execution Steps
		boolean isRented = r.rentCat(2);

		//Postconditions
		assertFalse(isRented);
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c1.rentCat(), c2.rentCat(), c3.rentCat() are never called.
	 *                 
	 * Hint: See Example/NoogieTest.java in testBadgerPlayCalled method to see an
	 * example of behavior verification.
	 */
	
	@Test
	public void testRentCatFailureNumCats3() {
		//Preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		when(c2.getRented()).thenReturn(true);

		//Execution Steps
		boolean isRented = r.rentCat(2);

		//Postconditions
		assertFalse(isRented);
		Mockito.verify(c1, Mockito.times(0)).rentCat();
		Mockito.verify(c2, Mockito.times(0)).rentCat();
		Mockito.verify(c3, Mockito.times(0)).rentCat();
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * Preconditions: r has no cats.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 */

	@Test
	public void testReturnCatFailureNumCats0() {
		//Preconditions

		//Execution Steps
		boolean returnCat = r.returnCat(2);

		//Postconditions
		assertFalse(returnCat);
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2.returnCat() is called exactly once.
	 *                 c1.returnCat() and c3.returnCat are never called.
	 *                 
	 * Hint: See Example/NoogieTest.java in testBadgerPlayCalled method to see an
	 * example of behavior verification.
	 */
	
	@Test
	public void testReturnCatNumCats3() {
		//Preconditions
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		when(c2.getRented()).thenReturn(true);

		//Execution Steps
		boolean isReturned = r.returnCat(2);

		//Postconditions
		assertTrue(isReturned);
		Mockito.verify(c2, Mockito.times(1)).returnCat();
		Mockito.verify(c1, Mockito.times(0)).returnCat();
		Mockito.verify(c3, Mockito.times(0)).returnCat();
	}
}
