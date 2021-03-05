import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.*;
import org.mockito.*;
import static org.mockito.Mockito.*;

public class CoffeeMakerQuestTest {

	CoffeeMakerQuest cmq;
	Player player;
	Room room1;	// Small room
	Room room2;	// Funny room
	Room room3;	// Refinanced room
	Room room4;	// Dumb room
	Room room5;	// Bloodthirsty room
	Room room6;	// Rough room

	@Before
	public void setup() {
		// 0. Turn on bug injection for Player and Room.
		Config.setBuggyPlayer(true);
		Config.setBuggyRoom(true);
		
		// 1. Create the Coffee Maker Quest object and assign to cmq.
		cmq = CoffeeMakerQuest.createInstance();

		// TODO: 2. Create a mock Player and assign to player and call cmq.setPlayer(player).
		// Player should not have any items (no coffee, no cream, no sugar)
		player = mock(Player.class);

		//stubbing
		when(player.checkSugar()).thenReturn(false);
		when(player.checkCream()).thenReturn(false);
		when(player.checkCoffee()).thenReturn(false);

		cmq.setPlayer(player);

		// TODO: 3. Create mock Rooms and assign to room1, room2, ..., room6.
		// Mimic the furnishings / adjectives / items of the rooms in the original Coffee Maker Quest.
		room1 = mock(Room.class);
		when(room1.getAdjective()).thenReturn("Small");
		when(room1.getFurnishing()).thenReturn("Quaint sofa");
		when(room1.getItem()).thenReturn(Item.CREAM);

		room2 = mock(Room.class);
		when(room2.getAdjective()).thenReturn("Funny");
		when(room2.getFurnishing()).thenReturn("Sad record player");
		when(room2.getItem()).thenReturn(Item.NONE);

		room3 = mock(Room.class);
		when(room3.getAdjective()).thenReturn("Refinanced");
		when(room3.getFurnishing()).thenReturn("Tight pizza");
		when(room3.getItem()).thenReturn(Item.COFFEE);

		room4 = mock(Room.class);
		when(room4.getAdjective()).thenReturn("Dumb");
		when(room4.getFurnishing()).thenReturn("Flat energy drink");
		when(room4.getItem()).thenReturn(Item.NONE);

		room5 = mock(Room.class);
		when(room5.getAdjective()).thenReturn("Bloodthirsty");
		when(room5.getFurnishing()).thenReturn("Beautiful bag of money");
		when(room5.getItem()).thenReturn(Item.NONE);

		room6 = mock(Room.class);
		when(room6.getAdjective()).thenReturn("Rough");
		when(room6.getFurnishing()).thenReturn("Perfect air hockey table");
		when(room6.getItem()).thenReturn(Item.SUGAR);

		// TODO: 4. Add the rooms created above to mimic the layout of the original Coffee Maker Quest.
		cmq.addFirstRoom(room1);
		cmq.addRoomAtNorth(room2, "Magenta", "Massive");
		cmq.addRoomAtNorth(room3, "Beige", "Smart");
		cmq.addRoomAtNorth(room4, "Dead", "Slim");
		cmq.addRoomAtNorth(room5, "Vivacious", "Sandy");
		cmq.addRoomAtNorth(room6, "Purple", "Minimalist");
	}

	@After
	public void tearDown() {
	}
	
	/**
	 * Test case for String getInstructionsString().
	 * Preconditions: None.
	 * Execution steps: Call cmq.getInstructionsString().
	 * Postconditions: Return value is " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 */
	@Test
	public void testGetInstructionsString() {
		//Preconditions

		//Execution steps
		String output = cmq.getInstructionsString();

		//Postconditions
		assertEquals(output, "INSTRUCTIONS (N,S,L,I,D,H) > ");
	}
	
	/**
	 * Test case for boolean addFirstRoom(Room room).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock room and assign to myRoom.
	 * Execution steps: Call cmq.addFirstRoom(myRoom).
	 * Postconditions: Return value is false.
	 */
	@Test
	public void testAddFirstRoom() {
		//Preconditions
		Room myRoom = mock(Room.class);

		//Execution steps
		boolean output = cmq.addFirstRoom(myRoom);

		//Postconditions
		assertFalse(output);
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Fake bed" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is true.
	 *                 room6.setNorthDoor("North") is called.
	 *                 myRoom.setSouthDoor("South") is called.
	 */
	@Test
	public void testAddRoomAtNorthUnique() {
		//Preconditions
		Room myRoom = mock(Room.class);
		when(myRoom.getAdjective()).thenReturn("Fake");
		when(myRoom.getFurnishing()).thenReturn("Fake bed");

		//Execution steps
		boolean output = cmq.addRoomAtNorth(myRoom, "North", "South");

		//Postconditions
		assertTrue(output);
		Mockito.verify(room6, times(1)).setNorthDoor("North");
		Mockito.verify(myRoom, times(1)).setSouthDoor("South");
	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Flat energy drink" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is false.
	 *                 room6.setNorthDoor("North") is not called.
	 *                 myRoom.setSouthDoor("South") is not called.
	 */
	@Test
	public void testAddRoomAtNorthDuplicate() {
		//Preconditions
		Room myRoom = mock(Room.class);
		when(myRoom.getAdjective()).thenReturn("Fake");
		when(myRoom.getFurnishing()).thenReturn("Flat energy drink");

		//Execution steps
		boolean output = cmq.addRoomAtNorth(myRoom, "North", "South");

		//Postconditions
		assertFalse(output);
		Mockito.verify(room6, times(0)).setNorthDoor("North");
		Mockito.verify(myRoom, times(0)).setNorthDoor("South");
	}
	
	/**
	 * Test case for Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room) has not yet been called.
	 * Execution steps: Call cmq.getCurrentRoom().
	 * Postconditions: Return value is null.
	 */
	@Test
	public void testGetCurrentRoom() {
		//Precondiitons

		//Execution steps
		Room output = cmq.getCurrentRoom();

		//Postconditions
		assertNull(output);
	}
	
	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room room) has not yet been called.
	 * Execution steps: Call cmq.setCurrentRoom(room3).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(room3) is true. 
	 *                 Return value of cmq.getCurrentRoom() is room3.
	 */
	@Test
	public void testSetCurrentRoom() {
		//Preconditions

		//Execution steps
		boolean output1 = cmq.setCurrentRoom(room3);
		Room output2 = cmq.getCurrentRoom();

		//Postconditions
		assertTrue(output1);
		assertSame(output2, room3);
	}
	
	/**
	 * Test case for String processCommand("I").
	 * Preconditions: Player does not have any items.
	 * Execution steps: Call cmq.processCommand("I").
	 * Postconditions: Return value is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n".
	 */
	@Test
	public void testProcessCommandI() {
		//Preconditions


		//Execution steps
		when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
		String output = cmq.processCommand("I");

		//Postconditions
		assertEquals("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n", output);
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nYou found some creamy cream!\n".
	 *                 player.addItem(Item.CREAM) is called.
	 */
	@Test
	public void testProcessCommandLCream() {
		//Preconditions
		cmq.setCurrentRoom(room1);

		//Execution steps
		String output = cmq.processCommand("l");

		//Postconditions
		assertEquals("There might be something here...\nYou found some creamy cream!\n", output);
		Mockito.verify(player, times(1)).addItem(Item.CREAM);
	}
	
	/**
	 * Test case for String processCommand("n").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room4) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is room5.
	 */
	@Test
	public void testProcessCommandN() {
		//Preconditions
		cmq.setCurrentRoom(room4);

		//Execution steps
		String output1 = cmq.processCommand("n");
		Room output2 = cmq.getCurrentRoom();

		//Postconditions
		assertEquals("", output1);
		assertSame(room5, output2);
	}
	
	/**
	 * Test case for String processCommand("s").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("s") is "A door in that direction does not exist.\n".
	 *                 Return value of cmq.getCurrentRoom() is room1.
	 */
	@Test
	public void testProcessCommandS() {
		//Preconditions
		cmq.setCurrentRoom(room1);

		//Execution steps
		String output1 = cmq.processCommand("s");
		Room output2 = cmq.getCurrentRoom();

		//Postconditions
		assertEquals(output1, "A door in that direction does not exist.\n");
		assertSame(output2, room1);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has no items.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDLose() {
		//Preconditions

		//Execution steps
		String output1 = cmq.processCommand("D");
		boolean output2 = cmq.isGameOver();

		//Postconditions
		assertEquals("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n", output1);
		assertFalse(output2);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDWin() {
		//Preconditions
		when(player.checkSugar()).thenReturn(true);
		when(player.checkCoffee()).thenReturn(true);
		when(player.checkCream()).thenReturn(true);

		//Execution steps
		String output1 = cmq.processCommand("D");
		boolean output2 = cmq.isGameOver();

		//Postconditions
		assertEquals("You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n", output1);
		assertTrue(output2);
	}
	
	// TODO: Put in more unit tests of your own making to improve coverage!
	
}
