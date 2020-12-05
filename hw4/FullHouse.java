package hw4;

import hw4.api.Die;

/**
 * Scoring category for a generalized full house. A hand with N dice satisfies
 * this category only in the following cases: If N is even, there are two
 * different values, each occurring exactly N/2 times. If N is odd, there are
 * two different values, one of them occurring N/2 times and the other occurring
 * N/2 + 1 times. For a hand that satisfies this category, the score is a fixed
 * value specified in the constructor; otherwise, the score is zero.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class FullHouse extends SetScores {

	/**
	 * Calls to the SetScores constructor to construct a FullHouse category with the
	 * given display name and score.
	 * 
	 * @param name   - name for this category
	 * @param points - points awarded for this category
	 */
	public FullHouse(java.lang.String name, int points) {
		super(name, points);
	}

	/**
	 * Determines whether the given hand satisfies the defined criteria for this
	 * scoring category(if N is even, there are two different values, each occurring
	 * exactly N/2 times. If N is odd, there are two different values, one of them
	 * occurring N/2 times and the other occurring N/2 + 1 times.). This method does
	 * not modify the state of this category and does not modify the hand.
	 * 
	 * @param hand - hand to check
	 * @return true - if the given hand satisfies the defined criteria for the
	 *         category, false otherwise
	 */
	@Override
	public boolean isSatisfiedBy(Hand hand) {
		int num1 = 0;
		int num2 = 0;
		int count1 = 0;
		int count2 = 0;
		int total = 0;
		boolean spot = false;
		boolean fullHouse = false;
		int[] hand1 = hand.getAllValues();
		num1 = hand1[0];
		for (int i = 0; i < hand.getNumDice(); i++) {
			if (hand1[i] == num1) {
				count1++;
			} else if (!(hand1[i] == num1) && spot == false) {
				num2 = hand1[i];
				spot = true;
			}
			if (hand1[i] == num2) {
				count2++;
			}
		}
		total = count1 + count2;
		// If number of dice is even
		if (hand.getNumDice() % 2 == 0) {
			if (count1 == count2 && total == hand.getNumDice()) {
				fullHouse = true;
			} else
				fullHouse = false;
			// If number of dice is odd.
		} else if (!(hand.getNumDice() % 2 == 0)) {
			if (total == hand.getNumDice()) {
				if (count1 + 1 == count2 || count1 == count2 + 1) {
					fullHouse = true;
				}
			} else
				fullHouse = false;
		}
		return fullHouse;
	}
}
