package hw2;

/**
 * Models a set of games in tennis. A set consists of a sequence of games with
 * two players alternating in the role of server, known as player 0 and player
 * 1. A player wins the set by winning a minimum number of games and having at
 * least two more wins than the other player. Traditionally, the minimum number
 * is six, but in this model the number is configured in the constructor. Note
 * also that there is no "tiebreak" game when the score reaches 6-6, as occurs
 * in some traditional forms of the game.
 * 
 * @author Brad Warren: bawarren@iastate.edu
 *
 */
public class Set {
	/**
	 * Tracks the games won by player zero.
	 */
	private int pZeroGames;
	/**
	 * Tracks the games won by player one.
	 */
	private int pOneGames;
	/**
	 * Determines how many more games are needed to win a set.
	 */
	private int gamesToWin;
	/**
	 * Determines who is serving.
	 */
	private int serving;
	/**
	 * Constructs a Tennis game.
	 */
	private TennisGame g;

	/**
	 * Constructs a set with the given minimum number of games. If the parameter
	 * player1ServesFirst is true, then player 1 will be the server in the initial
	 * current game; otherwise player 0 will start out as server.
	 * 
	 * @param minimumGamesToWin   set number of games a player has to win to win a
	 *                            set.
	 * @param player1ServersFirst determines whether or not player one serves first.
	 */
	public Set(int minimumGamesToWin, boolean player1ServersFirst) {
		gamesToWin = minimumGamesToWin;
		if (player1ServersFirst == true) {
			serving = 1;
		} else
			serving = 0;
		g = new TennisGame();
	}

	/**
	 * Invokes hit on the current game and updates the winner's count of games won
	 * if the action ends the current game. Does nothing if the set is over or if
	 * the current game is over.
	 * 
	 * @param serviceFault determines whether or not a fault occurs.
	 */
	public void serve(boolean serviceFault) {
		if (isSetOver() == false) {
			if (g.isOver() == false) {
				g.serve(serviceFault);
				}
			}
			if (g.isOver() == true) {
				if (g.serverWon() == true) {
					if (serving == 0) {
						++pZeroGames;
					} else
						++pOneGames;
				} else if (serving == 0) {
					++pOneGames;
				} else
					++pZeroGames;
			}
		}

	/**
	 * Invokes hit on the current game and updates the winner's count of games won
	 * if the action ends the current game. Does nothing if the set is over or if
	 * the current game is over.
	 * 
	 * @param fault       determines whether or not a fault occurs.
	 * @param outOfBounds tracks if ball is in out-of-bounds trajectory.
	 */
	public void hit(boolean fault, boolean outOfBounds) {
		if (isSetOver() == false) {
			if (g.isOver() == false) {
				g.hit(fault, outOfBounds);
			}
			if (g.isOver() == true) {
				if (g.serverWon() == true) {
					if (serving == 0) {
						++pZeroGames;
					} else
						++pOneGames;
				} else if (serving == 0) {
					++pOneGames;
				} else
					++pZeroGames;
			} else if (serving == 0) {
			}
		}
	}

	/**
	 * Invokes miss on the current game and updates the winner's count of games won
	 * if the action ends the current game. Does nothing if the set is over or if
	 * the current game is over.
	 */
	public void miss() {
		if (isSetOver() == false) {
			if (g.isOver() == false) {
				g.miss();
			}
			if (g.isOver() == true) {
				if (g.serverWon() == true) {
					if (serving == 0) {
						++pZeroGames;
					} else
						++pOneGames;
				} else if (serving == 0) {
					++pOneGames;
				} else
					++pZeroGames;
			}
		}

	}

	/**
	 * Invokes setScore on the current game and updates the winner's count of games
	 * won if the action ends the current game. Does nothing if the set is over or
	 * if the current game is over. This method is intended for testing and does not
	 * check that the given scores are realistic.
	 * 
	 * @param serverScore   tracks servers score.
	 * @param receiverScore tracks receivers score.
	 */
	public void fastForward(int serverScore, int receiverScore) {
		if (isSetOver() == false) {
			if (g.isOver() == false) {
				g.setScore(serverScore, receiverScore);
				if (g.isOver() == true) {
					if (g.serverWon() == true) {
						if (serving == 0) {
							++pZeroGames;
						} else
							++pOneGames;
					} else if (serving == 0) {
						++pOneGames;
					} else
						++pZeroGames;
				}
			}
		}
	}

	/**
	 * Starts a new game in this set, switching the service to the opposite player.
	 * This method does nothing if the current game is still in progress, or if the
	 * set is over.
	 */
	public void newGame() {
		if (isSetOver() == false) {
			if (g.isOver() == true) {
				g = new TennisGame();
				if (serving == 0) {
					serving = 1;
				} else
					serving = 0;
			}
		}

	}

	/**
	 * @return returns true if the current game is over.
	 */
	public boolean isCurrentGameOver() {
		if (g.isOver() == true) {
			return true;
		} else
			return false;
	}

	/**
	 * @return returns true if the set is over. The set ends when a player has won
	 *         the minimum number of games AND has won at least two games more than
	 *         the other player.
	 */
	public boolean isSetOver() {
		if ((pZeroGames >= gamesToWin || pOneGames >= gamesToWin) && Math.abs(pZeroGames - pOneGames) > 1) {
			return true;
		} else
			return false;
	}

	/**
	 * @param useCallString If the parameter useCallString is false, then the string
	 *                      score is formatted as in TennisGame.getScore();
	 *                      otherwise, the string score is formed according to the
	 *                      conventions of TennisGame.getCallString().
	 * @return returns a string representation of the current status of the set in
	 *         the form "Set: x-y Game: ss". Here x is the number of games won by
	 *         the currently serving player, y is the number of games won by the
	 *         other player, and ss is the score string for the current game.
	 */
	public java.lang.String getCurrentStatus(boolean useCallString) {
		String set;
		String scoreString;
		if (serving == 0) {
			set = "Set: " + pZeroGames + "-" + pOneGames;
		} else {
			set = "Set: " + pOneGames + "-" + pZeroGames;
		}
		if (useCallString == true) {
			scoreString = " Game: " + g.getCallString();
		} else {
			scoreString = " Game: " + g.getScore();
		}
		return set + scoreString;
	}

	/**
	 * @return returns the player (0 or 1) who is the server in the current game.
	 */
	public int whoIsServing() {
		return serving;
	}

	/**
	 * @return returns the number of games won by player 0.
	 */
	public int player0GamesWon() {
		return pZeroGames;
	}

	/**
	 * @return returns the number of games won by player 1.
	 */
	public int player1GamesWon() {
		return pOneGames;
	}

}
