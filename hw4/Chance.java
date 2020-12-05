package hw4;

/**
 * Scoring category that is satisfied by any hand. The score is the sum of all
 * die values.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class Chance extends SumScore {

	/**
	 * Calls to SumScores constructor to construct a Chance category with a given
	 * display name.
	 * 
	 * @param name - name of this category
	 */
	public Chance(java.lang.String name) {
		super(name);
	}

	/**
	 * Determines whether the given hand satisfies the defined criteria for this
	 * scoring category (satisfied by any hand). The criteria are determined by the
	 * concrete type implementing the interface. This method does not modify the
	 * state of this category and does not modify the hand.
	 * 
	 * @param hand - hand to check
	 * @return true - if the given hand satisfies the defined criteria for the
	 *         category, false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Hand hand) {
		return true;
	}
}
