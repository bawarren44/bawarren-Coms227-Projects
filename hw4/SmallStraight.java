package hw4;

import hw4.api.Die;

/**
 * Scoring category for a "small straight". A hand with N dice satisfies this
 * category only if it includes N - 1 distinct consecutive values. For a hand
 * that satisfies this category, the score is a fixed value specified in the
 * constructor; otherwise, the score is zero.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class SmallStraight extends SetScores {

	/**
	 * Calls to SetScores constructor to construct a SmallStraight category with the
	 * given display name and score.
	 * 
	 * @param name   - name of this category
	 * @param points - points awarded for a dice group that satisfies this category
	 */
	public SmallStraight(java.lang.String name, int points) {
		super(name, points);
	}

	/**
	 * Determines whether the given hand satisfies the defined criteria for this
	 * scoring category (if it includes N - 1 distinct consecutive values). The
	 * criteria are determined by the concrete type implementing the interface. This
	 * method does not modify the state of this category and does not modify the
	 * hand.
	 * 
	 * @param hand - hand to check
	 * @return true - if the given hand satisfies the defined criteria for the
	 *         category, false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Hand hand) {
		Die[] results = hand.getAllDice();
		int count = 1;
		for (int i = 0; i < results.length - 1; i++) {
			if (results[i + 1].value() == results[i].value() + 1) {
				count++;
			}
		}
		if (count == 4) {
			return true;
		} else
			return false;
	}
}
