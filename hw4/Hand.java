package hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import hw4.api.Die;

/**
 * This class represents values of a group of dice for a dice game such as
 * Yahtzee in which multiple rolls per turn are allowed. The number of faces on
 * the dice, the number of dice in the Hand, and the maximum number of rolls are
 * configurable via the constructor. At any time some of the dice may be
 * <em>available</em> to be rolled, and the other dice are <em>fixed</em>. Calls
 * to the <code>roll()</code> method roll the available dice only. After the
 * maximum number of rolls, all dice are automatically fixed; before that, the
 * client can select which dice to "keep" (change from available to fixed) and
 * which dice to "free" (change from fixed to available).
 * <p>
 * Note that valid die values range from 1 through the given
 * <code>maxValue</code>.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class Hand {
	private ArrayList<Die> hand = new ArrayList<>();
	private int numDice;
	private int maxValue;
	private int maxRolls;
	private int rolls;

	/**
	 * Constructs a new Hand in which each die initially has the value 1.
	 * 
	 * @param numDice  - number of dice in this group
	 * @param maxValue - largest possible die value, where values range from 1
	 *                 through <code>maxValue</code>
	 * @param maxRolls - maximum number of total rolls
	 */
	public Hand(int numDice, int maxValue, int maxRolls) {
		hand.clear();
		this.numDice = numDice;
		this.maxValue = maxValue;
		this.maxRolls = maxRolls;
		while (!(hand.size() == numDice)) {
			Die b = new Die(1, maxValue);
			hand.add(b);
		}
		rolls = 0;

	}

	/**
	 * Constructs a new Hand in which each die initially has the value given by the
	 * <code>initialValues</code> array. If the length of the array is greater than
	 * the number of dice, the extra values are ignored. If the length of the array
	 * is smaller than the number of dice, remaining dice will be initialized to the
	 * value 1.
	 * <p>
	 * This version of the constructor is primarily intended for testing.
	 * 
	 * @param numDice       - number of dice in this hand
	 * @param maxValue      - largest possible die value, where values range from 1
	 *                      through <code>maxValue</code>
	 * @param maxRolls      - maximum number of total rolls
	 * @param initialValues -initial values for the dice
	 */
	public Hand(int numDice, int maxValue, int maxRolls, int[] initialValues) {
		this.numDice = numDice;
		this.maxValue = maxValue;
		this.maxRolls = maxRolls;
		for (int i = 0; i < initialValues.length;) {
			while (!(hand.size() == numDice)) {
				Die b = new Die(initialValues[i], maxValue);
				hand.add(b);
				i++;
				if (i == initialValues.length) {
					break;
				}
			}
			break;
		}
		while (!(hand.size() == numDice)) {
			Die b = new Die(1, maxValue);
			hand.add(b);
		}

		rolls = 0;

	}

	/**
	 * Returns the number of dice in this hand.
	 * 
	 * @return number of dice in this hand
	 */
	public int getNumDice() {
		return numDice;
	}

	/**
	 * Returns the maximum die value in this hand. Valid values start at 1.
	 * 
	 * @return maximum die value
	 */
	public int getMaxValue() {
		return maxValue;
	}

	/**
	 * Rolls all available dice using the given random number generator. If the
	 * number of rolls has reached the maximum, all dice are marked as fixed.
	 * 
	 * @param rand - random number generator to be used for rolling dice
	 */
	public void roll(Random rand) {
		if (rolls == maxRolls) {
			for (int i = 0; i < hand.size(); i++) {
				hand.get(i).setAvailable(false);
			}
		} else {
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).isAvailable() == true) {
					hand.get(i).roll(rand);
				}
			}
			rolls++;
			if (rolls == maxRolls) {
				for (int i = 0; i < hand.size(); i++) {
					hand.get(i).setAvailable(false);
				}
			}
		}
	}

	/**
	 * Selects a single die value to be changed from the available dice to the fixed
	 * dice. If there are multiple available dice with the given value, only one is
	 * changed to be fixed. Has no effect if the given value is not among the values
	 * in the available dice. Has no effect if the number of rolls has reached the
	 * maximum.
	 * 
	 * @param value - die value to be changed from available to fixed
	 */
	public void keep(int value) {
		int i;
		if (!(rolls == maxRolls)) {
			for (i = 0; i < hand.size(); i++) {
				if (hand.get(i).isAvailable() == true && hand.get(i).value() == value) {
					hand.get(i).setAvailable(false);
					break;
				}
			}
		}
	}

	/**
	 * Selects a die value to be moved from the fixed dice to the available dice
	 * (i.e. so it will be re-rolled in the next call to <code>roll()</code>). If
	 * there are multiple fixed dice with the given value, only one is changed be
	 * available. Has no effect if the given value is not among the values in the
	 * fixed dice. Has no effect if the number of rolls has reached the maximum.
	 * 
	 * @param value - die value to be moved
	 */
	public void free(int value) {
		if (!(rolls == maxRolls)) {
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).isAvailable() == false && hand.get(i).value() == value) {
					hand.get(i).setAvailable(true);
					break;
				}
			}
		}
	}

	/**
	 * Causes all die values to be changed from available to fixed. Has no effect if
	 * the number of rolls has reached the maximum.
	 */
	public void keepAll() {
		for (int i = 0; i < hand.size(); i++) {
			hand.get(i).setAvailable(false);
		}
	}

	/**
	 * Causes all die values to be changed from fixed to available. Has no effect if
	 * the number of rolls has reached the maximum.
	 */
	public void freeAll() {
		if (!(rolls == maxRolls)) {
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).isAvailable() == false) {
					hand.get(i).setAvailable(true);
				}
			}
		}
	}

	/**
	 * Determines whether there are any dice available to be rolled in this hand.
	 * 
	 * @return true if there are no available dice, false otherwise
	 */
	public boolean isComplete() {
		int count = 0;
		boolean over = false;
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).isAvailable() == false) {
				count++;
			}
		}
		if (count == numDice) {
			over = true;
		} else
			over = false;
		return over;
	}

	/**
	 * Returns an array of fixed dice, in ascending order.
	 * 
	 * @return array of fixed dice
	 */
	public Die[] getFixedDice() {
		ArrayList<Die> result = new ArrayList<>();
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).isAvailable() == false) {
				result.add(hand.get(i));
			}
		}
		Die[] results = result.toArray(new Die[result.size()]);
		order(results, new DieComparator());
		return results;
	}

	/**
	 * Returns an array of available dice, in ascending order.
	 * 
	 * @return array of available dice
	 */
	public Die[] getAvailableDice() {
		ArrayList<Die> result = new ArrayList<>();
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).isAvailable() == true) {
				result.add(hand.get(i));
			}
		}
		Die[] results = result.toArray(new Die[result.size()]);
		order(results, new DieComparator());
		return results;
	}

	/**
	 * Returns all die values in this hand, in ascending order.
	 * 
	 * @return all die values in this hand
	 */
	public int[] getAllValues() {
		int[] values = new int[hand.size()];
		for (int i = 0; i < hand.size(); i++) {
			int value = hand.get(i).value();
			values[i] = value;
		}
		for (int i = 0; i < values.length; i++) {
			int idx = i;
			for (int j = i; j < values.length; j++) {
				if (values[j] < values[idx]) {
					idx = j;
				}
			}
			int temp = values[i];
			values[i] = values[idx];
			values[idx] = temp;
		}
		return values;
	}

	/**
	 * Returns an array of all the dice in this hand, in ascending order.
	 * 
	 * @return array of all dice
	 */
	public Die[] getAllDice() {
		Die[] results = hand.toArray(new Die[hand.size()]);
		order(results, new DieComparator());
		return results;
	}

	/**
	 * Rearranges the given array of Die b into ascending order. Dice are ordered
	 * first by value; dice with the same value are ordered by their max value, and
	 * dice with the same value and the same max value are ordered by whether they
	 * are available, with available dice preceding non-available dice.
	 * 
	 * @param b - the given array of Die
	 */
	private void order(Die[] b, Comparator<Die> comp) {
		for (int i = 0; i < b.length; i++) {
			int idx = i;
			for (int j = i; j < b.length; j++) {
				if (comp.compare(b[j], b[idx]) < 0) {
					idx = j;
				}
			}
			Die temp = b[i];
			b[i] = b[idx];
			b[idx] = temp;
		}

	}
}
