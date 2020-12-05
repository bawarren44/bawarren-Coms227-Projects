package hw4;

/**
 * Scoring category for all primes. A hand satisfies this category only if all
 * die values are prime numbers. For a hand that satisfies this category, the
 * score the sum of all die values; otherwise the score is zero. The display
 * name for this category is always the exact string "All Primes".
 * 
 * @author Brad Warren: bawarren@iastate
 */
public class AllPrimes extends SumScore {

	/**
	 * Constructs an AllPrimes category.
	 */
	public AllPrimes() {
		super(null);
	}

	/**
	 * Determines whether the given hand satisfies the defined criteria for this
	 * scoring category (if all die values are prime numbers). The criteria are
	 * determined by the concrete type implementing the interface. This method does
	 * not modify the state of this category and does not modify the hand.
	 * 
	 * @param hand - hand to check
	 * @return true - if the given hand satisfies the defined criteria for the
	 *         category, false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Hand hand) {
		int[] result = hand.getAllValues();
		boolean prime = true;
		for (int j = 0; j < result.length; j++) {
			int i;
			for (i = 2; i <= result[j] / 2; i++) {
				if (result[j] % i == 0) {
					prime = false;
				}
			}
		}
		if (prime == true)
			return true;
		else
			return false;
	}
}
