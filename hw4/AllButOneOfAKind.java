package hw4;

import hw4.api.Die;

/**
 * Scoring category for (N-1) of a kind. A hand with N dice satisfies this
 * category only if at least N - 1 of the values are the same. For a hand that
 * satisfies this category, the score the sum of all die values; otherwise the
 * score is zero.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class AllButOneOfAKind extends SumScore {

	/**
	 * Calls to SumScores constructor to construct a AllButOneOfAKind category with
	 * a given display name.
	 * 
	 * @param name - name for this category
	 */
	public AllButOneOfAKind(java.lang.String name) {
		super(name);
	}

	/**
	 * Determines whether the given hand satisfies the defined criteria for this
	 * scoring category (if at least N - 1 of the values are the same). The criteria
	 * are determined by the concrete type implementing the interface. This method
	 * does not modify the state of this category and does not modify the hand.
	 * 
	 * @param hand - hand to check
	 * @return true - if the given hand satisfies the defined criteria for the
	 *         category, false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Hand hand) {
		Die[] results = hand.getAllDice();
		int count = 1;
		int count2 = 1;
		for (int i = 0; i < results.length - 1; i++) {
			if (results[i].value() == results[i + 1].value()) {
				count++;
			} else {
				int j;
				for (j = i + 1; j < results.length - 1; j++) {
					if (results[j].value() == results[j + 1].value()) {
						count2++;
					} else
						break;
				}
				break;
			}
		}
		if (count == hand.getNumDice() - 1 || count2 == hand.getNumDice() - 1
				|| results[0].value() == results[results.length - 1].value()) {
			return true;
		} else
			return false;
	}

}
