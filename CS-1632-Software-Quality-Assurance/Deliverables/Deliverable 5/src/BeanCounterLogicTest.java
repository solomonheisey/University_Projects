import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import gov.nasa.jpf.vm.Verify;
import sun.awt.image.ImageWatched;

/**
 * Code by @author Wonsun Ahn
 * 
 * <p>Uses the Java Path Finder model checking tool to check BeanCounterLogic in
 * various modes of operation. It checks BeanCounterLogic in both "luck" and
 * "skill" modes for various numbers of slots and beans. It also goes down all
 * the possible random path taken by the beans during operation.
 */

public class BeanCounterLogicTest {
	private static BeanCounterLogic logic; // The core logic of the program
	private static Bean[] beans; // The beans in the machine
	private static LinkedList<Bean> inFlightBeans; //The beans falling
	private static String failString; // A descriptive fail string for assertions

	private static int slotCount; // The number of slots in the machine we want to test
	private static int beanCount; // The number of beans in the machine we want to test
	private static int inFlightBeanCount; //The number of beans currently falling
	private static int remainingBeans; //The number of beans in the machine not inserted
	private static boolean isLuck; // Whether the machine we want to test is in "luck" or "skill" mode


	/**
	 * Sets up the test fixture.
	 */
	@BeforeClass
	public static void setUp() {
		if (Config.getTestType() == TestType.JUNIT) {
			slotCount = 5;
			beanCount = 3;
			remainingBeans = beanCount;
			isLuck = true;
		} else if (Config.getTestType() == TestType.JPF_ON_JUNIT) {
			/*
			 * beanCount, and isLuck: slotCount should take values 1-5, beanCount should
			 * take values 0-3, and isLucky should be either true or false. For reference on
			 * how to use the Verify API, look at:
			 * https://github.com/javapathfinder/jpf-core/wiki/Verify-API-of-JPF
			 */
			int slotCount = Verify.getInt(1,5);
			int beanCount = Verify.getInt(0,3);
			boolean isLuck = Verify.getBoolean();
		} else {
			assert (false);
		}

		// Create the internal logic
		logic = BeanCounterLogic.createInstance(slotCount);
		// Create the beans
		beans = new Bean[beanCount];
		for (int i = 0; i < beanCount; i++) {
			beans[i] = Bean.createInstance(slotCount, isLuck, new Random(42));
		}

		inFlightBeans = new LinkedList<>();
		insertBean();

		// A failstring useful to pass to assertions to get a more descriptive error.
		failString = "Failure in (slotCount=" + slotCount
				+ ", beanCount=" + beanCount + ", isLucky=" + isLuck + "):";
	}

	@AfterClass
	public static void tearDown() {
	}

	/**
	 * Test case for void void reset(Bean[] beans).
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 * Invariants: If beanCount is greater than 0,
	 *             remaining bean count is beanCount - 1
	 *             in-flight bean count is 1 (the bean initially at the top)
	 *             in-slot bean count is 0.
	 *
	 *             If beanCount is 0,
	 *             remaining bean count is 0
	 *             in-flight bean count is 0
	 *             in-slot bean count is 0.
	 */
	@Test
	public void testReset() {
		// Preconditions

		// Execution Steps
		logic.reset(beans);
		remainingBeans = beanCount;
		inFlightBeanCount = 0;
		inFlightBeans = new LinkedList<>();
		insertBean();

		// Post-Condition Invariants
		if(beanCount > 0) {
			assertEquals(beanCount - 1, logic.getRemainingBeanCount());
			assertEquals(1, inFlightBeanCount);
			for(int i = 0; i < slotCount; i++) {
				assertEquals(0, logic.getSlotBeanCount(i));
			}
		} else if (beanCount == 0){
			assertEquals(0, logic.getRemainingBeanCount());
			assertEquals(0, inFlightBeanCount);
			for(int i = 0; i < slotCount; i++) {
				assertEquals(0, logic.getSlotBeanCount(i));
			}
		} else {
			assert(false);
		}

		/*
		 * Currently, it just prints out the failString to demonstrate to you all the
		 * cases considered by Java Path Finder. If you called the Verify API correctly
		 * in setUp(), you should see all combinations of machines
		 * (slotCount/beanCount/isLucky) printed here:
		 * 
		 * Failure in (slotCount=1, beanCount=0, isLucky=false):
		 * Failure in (slotCount=1, beanCount=0, isLucky=true):
		 * Failure in (slotCount=1, beanCount=1, isLucky=false):
		 * Failure in (slotCount=1, beanCount=1, isLucky=true):
		 * ...
		 * 
		 * PLEASE REMOVE when you are done implementing.
		 */
		//System.out.println(failString);
	}

	/**
	 * Test case for boolean advanceStep().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After each advanceStep(),
	 *             all positions of in-flight beans are legal positions in the logical coordinate system.
	 */
	@Test
	public void testAdvanceStepCoordinates() {
		//Preconditions

		//Execution Steps
		logic.reset(beans);
		BeanImpl testBean;
		int xPos, yPos;
		while (logic.advanceStep()) {
			ListIterator listIterator = inFlightBeans.listIterator(0);
			while(listIterator.hasNext()){
				testBean = (BeanImpl) listIterator.next();
				xPos = testBean.getXPos();
				yPos = testBean.getYPos();
				//X can never be greater than Y
				if(xPos > yPos) {
					assert (false);
				}
				//X and Y can never be less than 0
				else if (xPos < 0 || yPos < 0){
					assert (false);
				}
				//X and Y can never be greater than slotCount
				else if (xPos > slotCount || yPos > slotCount){
					assert (false);
				}
			}
		}
	}
	/**
	 * Test case for boolean advanceStep().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After each advanceStep(),
	 *             the sum of remaining, in-flight, and in-slot beans is equal to beanCount.
	 */
	@Test
	public void testAdvanceStepBeanCount() {

		// Preconditions

		// Execution steps
		logic.reset(beans);
		while(logic.advanceStep()){

			int slotBeanCount = 0;

			for(int i = 0; i < slotCount; i++){
				slotBeanCount += logic.getSlotBeanCount(i);
			}

			// Post-Condition Invarients
			assertEquals(remainingBeans + inFlightBeanCount + slotBeanCount, beanCount);
		}
	}

	/**
	 * Test case for boolean advanceStep().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After the machine terminates,
	 *             remaining bean count is 0
	 *             in-flight bean count is 0
	 *             in-slot bean count is beanCount.
	 */
	@Test
	public void testAdvanceStepPostCondition() {
		// TODO: Implement
	}
	
	/**
	 * Test case for void lowerHalf()().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Call logic.lowerHalf().
	 * Invariants: After the machine terminates,
	 *             remaining bean count is 0
	 *             in-flight bean count is 0
	 *             in-slot bean count is beanCount.
	 *             After calling logic.lowerHalf(),
	 *             slots in the machine contain only the lower half of the original beans.
	 *             Remember, if there were an odd number of beans, (N+1)/2 beans should remain.
	 *             Check each slot for the expected number of beans after having called logic.lowerHalf().
	 */
	@Test
	public void testLowerHalf() {
		// TODO: Implement
	}
	
	/**
	 * Test case for void upperHalf().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Call logic.upperHalf().
	 * Invariants: After the machine terminates,
	 *             remaining bean count is 0
	 *             in-flight bean count is 0
	 *             in-slot bean count is beanCount.
	 *             After calling logic.upperHalf(),
	 *             slots in the machine contain only the upper half of the original beans.
	 *             Remember, if there were an odd number of beans, (N+1)/2 beans should remain.
	 *             Check each slot for the expected number of beans after having called logic.upperHalf().
	 */
	@Test
	public void testUpperHalf() {
		// TODO: Implement
	}
	
	/**
	 * Test case for void repeat().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Call logic.repeat();
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: If the machine is operating in skill mode,
	 *             bean count in each slot is identical after the first run and second run of the machine. 
	 */
	@Test
	public void testRepeat() {
		// TODO: Implement
	}

	public static void insertBean(){
		if (beanCount > 0) {
			// Place one bean at the top
			int currIndex = beanCount - remainingBeans;

			inFlightBeans.add(beans[currIndex]);
			if(inFlightBeanCount == slotCount) {
				inFlightBeans.remove();
			} else{
				inFlightBeanCount++;
			}
			remainingBeans--;
		}
	}
}

