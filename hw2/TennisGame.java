package hw2;

import static hw2.BallDirection.*;

/**
 * Models a game of tennis. There are two players, a server and a receiver,
 * whose roles remain the same throughout the game. The server hits or serves
 * the ball toward the receiver. If the server makes an error in serving the
 * ball, called a service fault, then the ball is served again. However, if
 * there are two service faults in a row, the receiver is awarded a point and
 * the server starts again with the count of faults reset to zero. As soon as
 * the ball is served without a service fault, it is said to be in play, and a
 * rally begins in which the players hit the ball back an forth towards one
 * another. The rally ends when either one of them faults in some way, e.g.,
 * allows the ball to bounce twice, hits the ball into the net or the ceiling or
 * something, or one of them hits the ball on an out-of-bounds trajectory, and
 * the other player does not try to hit it back before it bounces, allowing it
 * to land out of bounds The game ends when one player has more than three
 * points with a margin of at least two points more than the other player.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 *
 */
public class TennisGame {
	/**
	 * Tracks the servers score.
	 */
	private int serverScore;
	/**
	 * Tracks of the receiver score.
	 */
	private int receiverScore;
	/**
	 * Tracks the direction of the ball whether it is NOT_IN_PLAY, TOWARD_RECEIVER,
	 * or TOWARD SERVER.
	 */
	private BallDirection ball;
	/**
	 * Determines whether the ball is heading out or not.
	 */
	private boolean out;
	/**
	 * Tracks amount of faults in two serving attempts, returns to 0 after two.
	 */
	private int faults;

	/**
	 * Constructs a new TennisGame in which both players have zero points and the
	 * ball is initially not in play.
	 */
	public TennisGame() {
		serverScore = 0;
		receiverScore = 0;
		ball = NOT_IN_PLAY;
	}

	/**
	 * Directly sets the scores to the given amounts and sets the ball's status to
	 * NOT_IN_PLAY. Note that this operation may cause the scores to go down, or
	 * result in unrealistic values. This method is intended for testing only.
	 * 
	 * @param newServerScore   initial server score.
	 * @param newReceiverScore initial receiver score.
	 */
	public void setScore(int newServerScore, int newReceiverScore) {
		serverScore = newServerScore;
		receiverScore = newReceiverScore;
		ball = NOT_IN_PLAY;
		faults = 0;
	}

	/**
	 * @return returns the current number of points for the receiver.
	 */
	public int getReceiverPoints() {
		return receiverScore;
	}

	/**
	 * @return returns the current number of points for the server.
	 */
	public int getServerPoints() {
		return serverScore;
	}

	/**
	 * @return returns the current status of the ball (traveling toward the
	 *         receiver, traveling toward the server, or not in play).
	 */
	public BallDirection getBallStatus() {
		return ball;
	}

	/**
	 * @return returns true if the game is over, which occurs when one player has
	 *         more than three points AND has a margin of at least two points over
	 *         the other player.
	 */
	public boolean isOver() {
		return receiverScore >= 4 && receiverScore >= serverScore + 2
				|| serverScore >= 4 && serverScore >= receiverScore + 2;
	}

	/**
	 * @return returns true if the game is over and the server has won.
	 */
	public boolean serverWon() {
		if (serverScore >= 4 && serverScore >= receiverScore + 2)
			return true;
		else
			return false;
	}

	/**
	 * @return returns true if the game is over and the receiver has won.
	 */
	public boolean receiverWon() {
		if (receiverScore >= 4 && receiverScore >= serverScore + 2)
			return true;
		else
			return false;

	}

	/**
	 * Simulates the server serving the ball. If the serviceFault parameter is
	 * false, then the ball's status will be TOWARD_RECEIVER. If the serviceFault
	 * parameter is true, the number of faults is incremented, and if the number of
	 * faults reaches two, it is reset to zero and a point is awarded to the
	 * receiver. This method does nothing if the game is over or if the ball status
	 * isn't NOT_IN_PLAY.
	 * 
	 * @param serviceFault determines whether or not a fault occurs.
	 */
	public void serve(boolean serviceFault) {
		if (ball == NOT_IN_PLAY) {
			if (serviceFault == true) {
				++faults;
				ball = NOT_IN_PLAY;
			}
			if (faults == 2) {
				faults = 0;
				++receiverScore;
			}
			if (serviceFault == false) {
				ball = TOWARD_RECEIVER;
			}

		}
		if (isOver() == true) {
			ball = NOT_IN_PLAY;
		}
	}

	/**
	 * Simulates a hit of the ball by the player toward whom the ball is currently
	 * moving. If the fault parameter is true, indicates that the hit results in a
	 * fault; then the rally ends with the other player getting a point, and the
	 * ball's status becomes NOT_IN_PLAY. If the fault parameter is false, then the
	 * ball's status just switches direction. The outOfBounds parameter indicates
	 * whether or not the trajectory of the ball would land it out of bounds, if the
	 * other player misses it. (The other player could elect to hit the ball before
	 * it bounces.) This method does nothing if the ball is not in play.
	 * 
	 * @param fault             determines whether of not a fault occurs.
	 * @param headedOutOfBounds determines if the ball is in a out-of-bounds
	 *                          trajectory.
	 */
	public void hit(boolean fault, boolean headedOutOfBounds) {
		out = headedOutOfBounds;
		if (ball == NOT_IN_PLAY) {
			ball = NOT_IN_PLAY;
		} else if (fault == true) {
			if (ball == TOWARD_RECEIVER) {
				++serverScore;
				ball = NOT_IN_PLAY;
			} else if (ball == TOWARD_SERVER) {
				++receiverScore;
				ball = NOT_IN_PLAY;
			}

		} else if (headedOutOfBounds == true) {
			if (ball == TOWARD_RECEIVER) {
				ball = TOWARD_SERVER;
				if (fault == true) {
					++serverScore;
				}
			}
		} else {
			if (ball == TOWARD_RECEIVER) {
				ball = TOWARD_SERVER;
			} else if (ball == TOWARD_SERVER)
				ball = TOWARD_RECEIVER;
		}

	}

	/**
	 * Simulates a miss of the ball by the player toward whom the ball is currently
	 * traveling. If the ball is on an out-of-bounds trajectory, that player gets a
	 * point, otherwise the other player gets a point. This method always ends the
	 * rally, i.e., after this method executes, the ball is no longer in play. This
	 * method does nothing if the ball is not in play.
	 */
	public void miss() {
		if (ball == TOWARD_RECEIVER) {
			if (out == true) {
				++receiverScore;
			} else if (out == false) {
				++serverScore;
			}
		} else if (ball == TOWARD_SERVER) {
			if (out == true) {
				++serverScore;
			} else if (out == false) {
				++receiverScore;
			}
		}
		ball = NOT_IN_PLAY;
	}

	/**
	 * @return returns a string representation of the raw points for each player, in
	 *         the form "x-y" where x is the number of points for the server and y
	 *         is the number of points for the receiver.
	 */
	public java.lang.String getScore() {
		return String.format("%01d-%01d", serverScore, receiverScore);
	}

	/**
	 * @return returns a string representation of the score using the bizarre
	 *         conventions of tennis. If the game is over, the returned string is
	 *         always of the form "x-y", where x is the server's score and y is the
	 *         receiver's score. When the game is not over, the following rules
	 *         apply: If the server's score is at least 4 and is exactly one more
	 *         than the receiver's score, then the string is "advantage in" If the
	 *         receiver's score is at least 4 and is exactly one more than the
	 *         receiver's score, then the string is "advantage out" If the scores
	 *         are equal and at least 3, the string is "deuce" If the scores are
	 *         equal and the value is 0, 1, or 2, the string is "love-all",
	 *         "15-all", or "30-all", respectively In all other cases, the string is
	 *         of the form "a-b", where a is a string describing the server's score
	 *         and b is a string describing the receiver's score, using "love" for 0
	 *         points, "15" for 1 point, "30" for 2 points, and "40" for three
	 *         points.
	 */
	public java.lang.String getCallString() {
		if (isOver() == true) {
			return String.format("%01d-%01d", serverScore, receiverScore);
		} else if (serverScore == 4 && serverScore == receiverScore + 1) {
			return "advantage in";
		} else if (receiverScore >= 4 && receiverScore == serverScore + 1) {
			return "advantage out";
		} else if (serverScore == receiverScore && serverScore == 3) {
			return "deuce";
		} else if (serverScore == receiverScore && serverScore == 0) {
			return "love-all";
		} else if (serverScore == receiverScore && serverScore == 1) {
			return "15-all";
		} else if (serverScore == receiverScore && serverScore == 2) {
			return "30-all";
		} else if (serverScore == 0) {
			if (receiverScore == 1) {
				return "love-15";
			} else if (receiverScore == 2) {
				return "love-30";
			} else
				return "love-40";
		} else if (serverScore == 1) {
			if (receiverScore == 0) {
				return "15-love";
			} else if (receiverScore == 2) {
				return "15-30";
			} else
				return "15-40";
		} else if (serverScore == 2) {
			if (receiverScore == 0) {
				return "30-love";
			} else if (receiverScore == 1) {
				return "30-15";
			} else
				return "30-40";
		} else if (receiverScore == 0) {
			return "40-love";
		} else if (receiverScore == 1) {
			return "40-15";
		} else
			return "deuce";
	}
}
