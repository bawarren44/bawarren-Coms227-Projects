package hw4;

/**
 * Scoring category that is based on counting occurrences of a particular target
 * value (specified in the constructor). This category is satisfied by any hand.
 * The score is the sum of just the die values that match the target value.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class CountOccurrences extends AbstractCategory {

	/**
	 * Specified number given by constructor.
	 */
	private int number;

	/**
	 * Number of times the number occurs.
	 */
	private int occurence;

	/**
	 * Calls to AbstractCategory constructor to construct a CountOccurrences
	 * category with the given display name and target value.
	 * 
	 * @param name   - name of this category
	 * @param number - target value that must be matched for a die to count toward
	 *               the score
	 */
	public CountOccurrences(java.lang.String name, int number) {
		super(name);
		this.number = number;
	}

	/**
	 * If the category has been filled, returns the score for the permanently saved
	 * hand that was used to fill it; otherwise returns 0. Score for this category
	 * is given by the number specified, multiplied by the occurrence of that
	 * number.
	 * 
	 * @return score of the category or zero if not filled
	 */
	public int getScore() {
		if (isFilled() == false) {
			return 0;
		} else
			return getPotentialScore(getHand());
	}

	/**
	 * Determines whether the given hand satisfies the defined criteria for this
	 * scoring category (satisfied by any hand). The criteria are determined by the
	 * concrete type implementing the interface. This method does not modify the
	 * state of this category and does not modify the hand. Score for this category
	 * is given by the number specified, multiplied by the occurrence of that
	 * number.
	 * 
	 * @param hand - hand to check
	 * @return true - if the given hand satisfies the defined criteria for the
	 *         category, false otherwise
	 */
	public boolean isSatisfiedBy(Hand hand) {
		occurence = 0;
		int[] result = hand.getAllValues();
		for (int i = 0; i < result.length; i++) {
			if (result[i] == number) {
				occurence++;
			}
		}
		if (occurence > 0) {
			return true;
		} else
			return false;
	}

	/**
	 * Returns the potential score that would result from using the given hand to
	 * fill this category. Always returns zero if the isSatisfiedBy() method returns
	 * false for the given hand. This method does not modify the state of this
	 * category and does not modify the hand.
	 * 
	 * @param hand - hand to check
	 * @return potential score for the given hand
	 */
	public int getPotentialScore(Hand hand) {
		if (isSatisfiedBy(hand) == false) {
			return 0;
		} else
			return occurence * number;
	}
}
