package hw4;

/**
 * Sub-super class that extends AbsractCategory. Purpose is to group scoring
 * categories that count the score using the sum of the die.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public abstract class SumScore extends AbstractCategory {

	/**
	 * Calls to the AbstractCategory constructor to form a new scoring category with
	 * a given name.
	 * 
	 * @param name - given name by the user
	 */
	protected SumScore(String name) {
		super(name);
	}

	/**
	 * If the category has been filled, returns the score for the permanently saved
	 * hand that was used to fill it; otherwise returns 0. Score for the categories
	 * under this method is given by the sum of the hand.
	 * 
	 * @return score for the category or zero if not filled
	 */
	@Override
	public int getScore() {
		if (isFilled() == false) {
			return 0;
		} else
			return getPotentialScore(getHand());
	}

	/**
	 * Determines whether the given hand satisfies the defined criteria for this
	 * scoring category. The criteria are determined by the concrete type
	 * implementing the interface. This method does not modify the state of this
	 * category and does not modify the hand. Score for the categories under this
	 * method is given by the sum of the hand.
	 * 
	 * @param hand - hand to check
	 * @return true if the given hand satisfies the defined criteria for the
	 *         category, false otherwise
	 */
	@Override
	public abstract boolean isSatisfiedBy(Hand hand);

	/**
	 * Returns the potential score that would result from using the given hand to
	 * fill this category. Always returns zero if the <code>isSatisfiedBy()</code>
	 * method returns false for the given hand. This method does not modify the
	 * state of this category and does not modify the hand. Score for the categories
	 * under this method is given by the sum of the hand.
	 * 
	 * @param hand - hand to check
	 * @return potential score for the given hand
	 */
	@Override
	public int getPotentialScore(Hand hand) {
		int score = 0;
		int[] values = hand.getAllValues();
		if (isSatisfiedBy(hand) == false) {
			return 0;
		} else
			for (int i = 0; i < values.length; i++) {
				score = score + values[i];
			}
		return score;
	}

}
