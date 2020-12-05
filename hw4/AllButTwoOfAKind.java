package hw4;

import hw4.api.Die;

/**
 * Scoring category for (N-2) of a kind. A hand with N dice satisfies this
 * category only if at least N - 2 of the values are the same. For a hand that
 * satisfies this category, the score the sum of all die values; otherwise the
 * score is zero.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class AllButTwoOfAKind extends SumScore {

	/**
	 * Calls to the SumScores constructor to construct an AllButTwoOfAKind category with the
	 * given display name.
	 * 
	 * @param name - name for this category
	 */
	public AllButTwoOfAKind(java.lang.String name) {
		super(name);
	}

	/**
	 * Determines whether the given hand satisfies the defined criteria for this
	 * scoring category(if at least N - 2 of the values are the same). This method
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
		int count3 = 1;
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
				for (j = i + 2; j < results.length - 1; j++) {
					if (results[j].value() == results[j + 1].value()) {
						count3++;
					} else
						break;
				}
				break;
			}
		}
		if (count == hand.getNumDice() - 2 || count2 == hand.getNumDice() - 2 || count3 == hand.getNumDice() - 2
				|| results[0].value() == results[results.length - 1].value()) {
			return true;
		} else
			return false;
	}
}
