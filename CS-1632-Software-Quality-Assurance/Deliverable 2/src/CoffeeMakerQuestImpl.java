import java.lang.reflect.Array;
import java.util.*;

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {

	// TODO: Add more member variables and methods as needed.
	ArrayList<Room> cmq;
	Player player;
	Room currRoom;
	int currRoomIndex;

	CoffeeMakerQuestImpl() {
		cmq = new ArrayList<>();
	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() {
		boolean isGameOver;
		isGameOver = player.checkCoffee() && player.checkCream() && player.checkSugar();
		return isGameOver;
	}

	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) {
		player = p;
	}
	
	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) {
		boolean isSuccessful = false;
		if ( isRoomValid(room) && (cmq.isEmpty()) ){
			cmq.add(room);
			isSuccessful = true;
		}
		return isSuccessful;
	}

	/**
	 * If room is null then false is returned, true otherwise.
	 *
	 * @param room the room being check if it's null
	 * @return true if room is not null, false otherwise
	 */
	private boolean isRoomValid(Room room) {
		return room != null;
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * Of course, the north door of the new room is still null because there is
	 * no room to the north of the new room.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the current northern-most room
	 * @param southDoor string to label the south door of the newly added room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) {
		boolean isSuccessful = false;
		if(room == null || northDoor == null || southDoor == null) {
		} else if (cmq.isEmpty()){
		} else {
			boolean isValid = true;
			ListIterator<Room> tempListItr = cmq.listIterator();
			Room nextRoom = null;
			while(tempListItr.hasNext()) {
				//Checks if the room being passed to the method has an adjective
				//or furnishing matching an already existing room
				nextRoom = tempListItr.next();
				if (nextRoom.getAdjective().equalsIgnoreCase(room.getAdjective()) ||
						nextRoom.getFurnishing().equalsIgnoreCase(room.getFurnishing())) {
					isValid = false;
				}
			}
			//The passed room is unique
			if(isValid){
				//Adds new room
				Room newRoom = room;
				newRoom.setSouthDoor(southDoor);
				cmq.add(newRoom);

				//Updates northern-most room
				nextRoom.setNorthDoor(northDoor);

				isSuccessful = true;
			}
		}
		return isSuccessful;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */ 
	public Room getCurrentRoom() {
		Room returnedRoom = null;
		if (currRoom != null){
			returnedRoom = currRoom;
		}
		return returnedRoom;
	}
	
	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		boolean isSuccessful = false;
		if (room != null) {
			boolean isRoomValid = false;
			ListIterator<Room> tempListItr = cmq.listIterator();
			int tempCurrRoomIndex = 0;

			//Checks if the room exists in the game
			while (!isRoomValid){
				if(tempListItr.hasNext()){
					isRoomValid = (room == tempListItr.next());
					tempCurrRoomIndex++;
				} else {
					break;
				}
			}
			//The room exists in the game
			if (isRoomValid){
				currRoom = room;
				currRoomIndex = tempCurrRoomIndex;
				isSuccessful = true;
			}
		}
		return isSuccessful;
	}
	
	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() {
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}
	
	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game. Make
     * sure you use Player.getInventoryString() whenever you need to display
     * the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {
		String message = "";

		if(cmd.length() != 1)
			message = "What?";
		else {
			char command = cmd.toUpperCase().charAt(0);

			switch(command) {
				case 'N':
					if (currRoomIndex < cmq.size()-1) {
						//Moves player forward one room
						setCurrentRoom(cmq.get(currRoomIndex));
					} else {
						message = "A door in that direction does not exist.\n";
					}
					break;

				case 'S':
					if (currRoomIndex > 1) {
						//Moves player back one room
						setCurrentRoom(cmq.get(currRoomIndex-2));
					} else {
						message = "A door in that direction does not exist.\n";
					}
					break;

				case 'L':
					Item item = currRoom.getItem();
					player.addItem(item);

					switch(item) {
						case CREAM:
							message = "There might be something here...\nYou found some creamy cream!\n";
							break;
						case SUGAR:
							message = "There might be something here...\nYou found some sweet sugar!\n";
							break;
						case COFFEE:
							message = "There might be something here...\nYou found some caffeinated coffee!\n";
							break;
						default:
							message = "You don't see anything out of the ordinary.\n";
					}
					break;
				case 'I':
					message = player.getInventoryString();
					break;
				case 'D':
					if(isGameOver()){
						//All Items
						message = player.getInventoryString() + "\nYou drink the beverage and are ready to study!\nYou win!\n";
					} else {
						boolean coffee = player.checkCoffee();
						boolean cream = player.checkCream();
						boolean sugar = player.checkSugar();

						//No Items
						if(!coffee && !cream && !sugar)
							message = player.getInventoryString() + "\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";

						//Coffee and Cream
						else if(coffee && cream && !sugar)
							message = player.getInventoryString() + "\nWithout sugar, the coffee is too bitter. You cannot study.\nYou lose!\n";

						//Coffee
						else if(coffee && !cream && !sugar)
							message = player.getInventoryString() + "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";

						//Cream
						else if(!coffee && cream && !sugar)
							message = player.getInventoryString() + "\nYou drink the cream, but without caffeine, you cannot study.\nYou lose!\n";

						//Sugar
						else if(!coffee && !cream && sugar)
							message = player.getInventoryString() + "\nYou eat the sugar, but without caffeine, you cannot study.\nYou lose!\n";

						//Cream and Sugar
						else if(!coffee && cream && sugar)
							message = player.getInventoryString() + "\nYou drink the sweetened cream, but without caffeine you cannot study.\nYou lose!\n";

						//Coffee and Sugar
						else
							message = player.getInventoryString() + "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";
					}
					break;

				case 'H':
					message = "N - Go north\n" +
							  "S - Go south\n" +
							"L - Look and collect any items in the room\n"+
							"I - Show inventory of items collected\n" +
							"D - Drink coffee made from items in inventory";
					break;
				default:
					message = "What?";
			}
		}
		return message;
	}
	
}
