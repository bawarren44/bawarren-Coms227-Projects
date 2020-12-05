package hw4;

import java.util.Comparator;

import hw4.api.Die;

/**
 * Comparator for ordering Die objects. Dice are ordered first by value; dice
 * with the same value are ordered by their max value, and dice with the same
 * value and the same max value are ordered by whether they are available, with
 * available dice preceding non-available dice.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 */
public class DieComparator implements Comparator<Die> {

	/**
	 * Compare in interface java.util.Comparator<Die>
	 */
	@Override
	public int compare(Die left, Die right) {
		if (!(left.value() == right.value())) {
			return left.value() - right.value();
		} else if (!(left.maxValue() == right.maxValue())) {
			return left.maxValue() - right.maxValue();
		} else if (left.isAvailable() == right.isAvailable()) {
			return 0;
		} else if (left.isAvailable()) {
			return -1;
		} else
			return 1;
	}
}
