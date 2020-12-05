package Tests;

import java.util.Arrays;
import java.util.Random;

import hw4.CountOccurrences;
import hw4.DiceGame;
import hw4.FullHouse;
//import hw4.CountOccurrences;
import hw4.Hand;
import hw4.LargeStraight;
import hw4.api.Die;
import hw4.api.ScoringCategory;
import hw4.AllButOneOfAKind;
import hw4.AllButTwoOfAKind;
import hw4.AllOfAKind;
import hw4.AllPrimes;
import hw4.Chance;
import hw4.SmallStraight;

public class SimpleTest {

	public static void main(String[] args) {
		// creating a hand
		Hand hand = new Hand(5, 6, 3);
		System.out.println(hand.getNumDice()); // expected 5
		System.out.println(hand.getMaxValue()); // expected 6
		Die[] dice = hand.getAllDice();
		System.out.println(Arrays.toString(dice)); // [1, 1, 1, 1, 1]
		int[] values = hand.getAllValues();
		System.out.println(Arrays.toString(values)); // [1, 1, 1, 1, 1]

		// creating a hand with specified initial values
		int[] test = { 4, 3, 3, 1, 3 };
		hand = new Hand(5, 6, 3, test);
		dice = hand.getAllDice();
		System.out.println(Arrays.toString(dice)); // [1, 3, 3, 3, 4]

		// example of a scoring category
//    ScoringCategory cat = new CountOccurences("Count threes", 3);
//    System.out.println(cat.isSatisfiedBy(hand));     // should be true
//    System.out.println(cat.getPotentialScore(hand)); // should be 9
//    System.out.println(cat.getScore());              // should be zero
//    hand.keepAll();
//    System.out.println(hand.isComplete());           // should be true
//    cat.fill(hand);
//    System.out.println(cat.getScore());              // should be nine

		// fixed vs available dice
		test = new int[] { 4, 3, 3, 1, 3 };
		hand = new Hand(5, 6, 3, test);
		Die[] avail = hand.getAvailableDice();
		System.out.println(Arrays.toString(avail)); // [1, 3, 3, 3, 4]
		Die[] fixed = hand.getFixedDice();
		System.out.println(Arrays.toString(fixed)); // []
		hand.keep(3);
		hand.keep(1);
		avail = hand.getAvailableDice();
		System.out.println(Arrays.toString(avail)); // [3, 3, 4]
		fixed = hand.getFixedDice();
		System.out.println(Arrays.toString(fixed)); // [1, 3]
		Die[] all = hand.getAllDice();
		System.out.println(Arrays.toString(all)); // [1*, 3*, 3, 3, 4]
		System.out.println(TextUI.handToString(hand)); // 3 3 4 (1 3)

		// try rolling the hand above
		Random rand = new Random(99);
		hand.roll(rand);
		all = hand.getAllDice();
		System.out.println(Arrays.toString(all)); // [1*, 2, 3, 3*, 4]
		System.out.println(TextUI.handToString(hand)); // 2 3 4 (1 3)

		// if we roll the hand two more times, all dice should
		// automatically be fixed
		hand.roll(rand);
		hand.roll(rand);
		all = hand.getAllDice();
		System.out.println(Arrays.toString(all)); // [1*, 3*, 3*, 3*, 5*]
		System.out.println(TextUI.handToString(hand)); // (1 3 3 3 5)
		System.out.println(hand.isComplete()); // true

//    ScoringCategory b =  new CountOccurences("brad", 3);
//    System.out.println(b.getPotentialScore(hand));
//    System.out.println(b.getScore());
//    b.fill(hand);
//    System.out.println(b.getScore());

//    ScoringCategory c =  new Chance("brad");
//    System.out.println(c.getPotentialScore(hand)); // 15
//    System.out.println(c.getScore());  // 0
//    c.fill(hand);
//    System.out.println(c.getScore());  // 15

		int[] t = { 3, 1, 6, 6, 6 };
		hand = new Hand(5, 13, 3, t);
		dice = hand.getAllDice();
		System.out.println(Arrays.toString(dice));
		hand.keepAll();
		System.out.println(TextUI.handToString(hand));
		ScoringCategory g = new AllButTwoOfAKind("name");
		System.out.println(g.getPotentialScore(hand));
		System.out.println(g.getScore());
		g.fill(hand);
		System.out.println(g.getScore());
//    DiceGame c = new DiceGame(5, 6, 6);
//    TextUI b = new TextUI(c, rand);
//    b.runGame();

	}

}
