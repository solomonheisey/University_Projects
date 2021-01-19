import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SortedCollection {

	//LinkedList is used because of it's dynamic sizing, this stores the inputted collection of numbers
	LinkedList<Integer> collection = new LinkedList<>();

	/**
	 * Adds the number n to the collection.
	 * 
	 * @param n the number to add to the collection
	 * @return always returns true
	 */
	public boolean add(int n) {
		collection.add(n);
		return true;
	}

	/**
	 * Removes the smallest number in the collection and returns it.
	 * 
	 * @return the smallest number in the collection
	 */
	public int remove() throws NoSuchElementException {
		int lowerPointer = 0;

		//locates smallest element and saves position
		for (int i = 0; i < collection.size(); i++)
			if(collection.get(i) < collection.get(lowerPointer))
				lowerPointer = i;

		//saves smallest number for output and removes it from the collection
		int lowestNum = collection.get(lowerPointer);
		collection.remove(lowerPointer);

		return lowestNum;
	}

	/**
	 * Prints usage information.
	 */
	public static void showUsage() {
		System.out.println("Usage: java SortedCollection [num1] [num2] [num3] ...");
	}

	/**
	 * Main method. Receives a list of numbers as commandline arguments and prints
	 * out the list in sorted order from smallest to largest.
	 * 
	 * @param args commandline arguments; see showUsage() for detailed information
	 */
	public static void main(String[] args) {
		SortedCollection collection = new SortedCollection();
		if (args.length == 0) {
			showUsage();
			return;
		}


		//iterates through arguments
		for (String arg : args) {
			int parsedInt;

			//tries to parse ints from the arguments. If it's not valid then message is displayed
			try {
				parsedInt = Integer.parseInt(arg);
				collection.add(parsedInt);
			} catch (NumberFormatException e) {
				showUsage();
				return;
			}
		}
		
		System.out.print("sorted: ");
		for (int i = 0; i < args.length; i++) {
			int num = collection.remove();
			System.out.print(num + " ");
		}
		System.out.println();
	}
}
