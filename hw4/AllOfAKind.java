package hw4;

/**
 * Scoring category for a "yahtzee". A hand with N dice satisfies this category
 * only if all N values are the same. For a hand that satisfies this category,
 * the score is a fixed value specified in the constructor; otherwise, the score
 * is zero.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class AllOfAKind extends SetScores {

	/**
	 * Calls to SetScores constructor to construct a AllOfAKind category with a
	 * given display name and score.
	 * 
	 * @param name   - name of this category
	 * @param points - points awarded for a hand that satisfies this category
	 */
	public AllOfAKind(java.lang.String name, int points) {
		super(name, points);
	}

	/**
	 * /** Determines whether the given hand satisfies the defined criteria for this
	 * scoring category (if all N values are the same). The criteria are determined
	 * by the concrete type implementing the interface. This method does not modify
	 * the state of this category and does not modify the hand.
	 * 
	 * @param hand - hand to check
	 * @return true - if the given hand satisfies the defined criteria for the
	 *         category, false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Hand hand) {
		int[] result = hand.getAllValues();
		int first = result[0];
		int last = result[hand.getNumDice() - 1];
		if (first == last) {
			return true;
		} else
			return false;

	}
}
