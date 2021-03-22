public class IntegerOps {
	/**
	 * Computes the sum of x and y. On integer overflow, where the return value
	 * becomes larger than Integer.MAX_VALUE, 0 is returned instead.
	 * 
	 * @param x First integer
	 * @param y Second integer
	 * @return Sum of x and y, or 0 if integer overflow
	 */
	public static int add(int x, int y) {
		long sum = (long)x + (long)y;
		if (sum >= Integer.MAX_VALUE)
			return 0;
		return x + y;
	}

	/**
	 * Computes the different of x and y. On integer overflow, where the return
	 * value becomes larger than Integer.MAX_VALUE, 0 is returned instead.
	 * 
	 * @param x First integer
	 * @param y Second integer
	 * @return Difference between x and y, or 0 if integer overflow
	 */
	public static int subtract(int x, int y) {
		long difference = (long)x - (long)y;
		if (difference >= Integer.MAX_VALUE)
			return 0;
		return x - y;
	}
}
