package games.poker;

/**
 * BORROWED CODE.  Modified slightly to fit interfaces and general gameServer setup.
 */

//****************************************
// ao2402
// This class allows the Player class to interact 
// with the rest of the game, and carries out most 
//of the game rules, including creating a game-specific
//deck, shuffling it, making a hand, and scoring it,
//as well as in-game scores and tallies. 
//****************************************

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Game implements server.Game{

	// instantiate variables
	// private ArrayList<Card> hand;
	Deck myDeck = new Deck();
	Player you;
	int score;
	int game;
	private PrintWriter output;
	private Scanner input;

	// constructor
	public Game(Scanner in, PrintWriter pw) {
		this.you = new Player(in, pw);
		this.output = pw;
		this.input = in;
	}

	public void play() {

		// declaring variables to be used throughout the method
		boolean end = false;
		//Scanner input = new Scanner(System.in);
		score = 25;
		game = 0;
		// ArrayList<Card> myHand = new ArrayList<Card>();

		while (!end) {

			this.output.println("Do you want to play poker? (y/n)");
			
			String response = input.next();
			if (response.equalsIgnoreCase("y")) {

				this.score--;

				// creates freshly shuffled deck for new round
				myDeck.shuffleDeck();

				// creates, orders, and displays hand for player
				if (response.equals("y")) {
					you.makeHand(myDeck);
				} else {
					you.makeCheaterHand();
				}
				you.sortHand();
				you.showHand();

				// allows player to replace cards in the hand
				you.replaceHand(myDeck);

				// scores the final hand
				this.scoreHand(you);

				// clears the hand
				you.clearHand();

				// adds to the game count
				game++;

			} else {
				end = true;
				this.output.println("You played " + game + " games.");
				this.output.println("Your final score was " + score + ".");

			}
		}
	}

	public void scoreHand(Player myHand) {
		you.sortHand();
		you.showHand();
		boolean goOn = true;

		goOn = checkRoyalFlush(myHand);

		if (goOn == false) {
			goOn = checkStraightFlush(myHand);
		}

		if (goOn == false) {
			goOn = checkFourKind(myHand);
		}

		if (goOn == false) {
			goOn = checkFullHouse(myHand);
		}

		if (goOn == false) {
			goOn = checkFlush(myHand);
		}

		if (goOn == false) {
			goOn = checkStraight(myHand);
		}

		if (goOn == false) {
			goOn = checkThreeKind(myHand);
		}

		if (goOn == false) {
			goOn = checkTwoPair(myHand);
		}

		if (goOn == false) {
			goOn = checkOnePair(myHand);
		}

		if (goOn == false) {
			this.output.println("You don't have any pairs!");
		}
		this.output.println("Your score is: " + this.score);
	}

	// checks for a Royal Flush
	public boolean checkRoyalFlush(Player myHand) {
		if (checkSuits(myHand) == true) {
			if (checkContinuous(myHand) == true) {
				int rank1 = myHand.getCard(0).getRank();
				int rank2 = myHand.getCard(4).getRank();
				if (rank1 == 1 && rank2 == 13) {
					this.score += 250;
					this.output.println("You have a royal flush!");
					return true;
				}
			}
		}
		return false;
	}

	// checks for a Straight Flush
	public boolean checkStraightFlush(Player myHand) {
		if (checkSuits(myHand) == true) {
			if (checkContinuous(myHand) == true) {
				this.score += 50;
				this.output.println("You have a straight flush!");
				return true;
			}
		}
		return false;
	}

	// checks for 4 of a kind
	public boolean checkFourKind(Player myHand) {
		int rank1 = myHand.getCard(1).getRank();
		int rank2 = myHand.getCard(2).getRank();
		int rank3 = myHand.getCard(3).getRank();
		if ((rank1 == rank2) && (rank2 == rank3)) {
			int rank4 = myHand.getCard(0).getRank();
			int rank5 = myHand.getCard(4).getRank();
			if ((rank1 == rank4) || (rank3 == rank5)) {
				this.score += 25;
				this.output.println("You have 4 of a kind!");
				return true;
			}
		}
		return false;
	}

	// checks for a Full House
	@SuppressWarnings("unchecked")
	public boolean checkFullHouse(Player myHand) {
		Object[] pairs = checkPairs(myHand);
		boolean pair = (Boolean) pairs[0];
		if (pair == true) {
			if (((ArrayList<String>) pairs[1]).size() == 2) {
				int rank1 = myHand.getCard(0).getRank();
				int rank2 = myHand.getCard(2).getRank();
				int rank3 = myHand.getCard(4).getRank();
				if ((rank1 == rank2) || (rank2 == rank3)) {
					this.score += 6;
					this.output.println("You have a Full House!");
					return true;
				}
			}
		}
		return false;
	}

	// checks for Flush
	public boolean checkFlush(Player myHand) {
		if (checkSuits(myHand) == true) {
			this.score += 5;
			this.output.println("You have a Flush!");
			return true;
		}
		return false;
	}

	// checks for Straight
	public boolean checkStraight(Player myHand) {
		if (checkContinuous(myHand) == true) {
			this.score += 4;
			this.output.println("You have a Straight!");
			return true;
		}
		return false;
	}

	// checks for Three of a Kind
	public boolean checkThreeKind(Player myHand) {
		Object[] pairs = checkPairs(myHand);
		boolean pair = (Boolean) pairs[0];
		if (pair == true) {
			int rank1 = myHand.getCard(0).getRank();
			int rank2 = myHand.getCard(2).getRank();
			int rank3 = myHand.getCard(4).getRank();
			if ((rank1 == rank2) || (rank2 == rank3)) {
				this.score += 3;
				this.output.println("You have Three of a Kind!");
				return true;
			}
		}
		return false;
	}

	// checks for two pairs
	@SuppressWarnings("unchecked")
	public boolean checkTwoPair(Player myHand) {
		Object[] pairs = checkPairs(myHand);
		boolean pair = (Boolean) pairs[0];
		if (pair == true) {
			if (((ArrayList<String>) pairs[1]).size() == 2) {
				this.score += 2;
				this.output.println("You have two pairs!");
				return true;
			}
		}
		return false;
	}

	// checks for one pair
	@SuppressWarnings("unchecked")
	public boolean checkOnePair(Player myHand) {
		Object[] pairs = checkPairs(myHand);
		boolean pair = (Boolean) pairs[0];
		if (pair == true) {
			int j = Integer.parseInt(((ArrayList<String>) pairs[1]).get(0));
			if (j >= 11) {
				this.score += 1;
			}
			this.output.println("You have one pair!");
			return true;
		}
		return false;
	}

	// check suit
	public boolean checkSuits(Player myHand) {
		int j = 0;
		boolean equalSuit = true;

		while (equalSuit && (j < 3)) {

			int Suit1 = myHand.getCard(j).getSuit();
			// this.output.println(Suit1);
			int Suit2 = myHand.getCard(j + 1).getSuit();
			// this.output.println(Suit2);

			if (Suit1 == Suit2) {
				equalSuit = true;
				// this.output.println("E1");
			} else {
				equalSuit = false;
			}

			// this.output.println(equalSuit);
			j++;
		}
		// this.output.println("WWW");
		return equalSuit;
	}

	// check continuous
	public boolean checkContinuous(Player myHand) {
		boolean continuous = true;
		int i = 0;
		while (continuous = true && i <= 3) {
			int rank1 = myHand.getCard(i).getRank();
			int rank2 = myHand.getCard(i + 1).getRank();
			if ((rank1++) == rank2) {
				continuous = true;
			} else {
				continuous = false;
			}
			// this only works because it runs the loop again, after throwing
			// the A to the end after checking for the king at the end.
			/*
			 * if (myHand.getCard(0).getRank() == 1) { if
			 * (myHand.getCard(4).getRank() == 13) { continuous = true; } }
			 */
			i++;
		}

		//checks specifically for a royal flush
		if (!continuous) {
			i = 0;
			int j = myHand.getCard(0).getRank();
			int k = myHand.getCard(1).getRank();
			int l = myHand.getCard(2).getRank();
			int m = myHand.getCard(3).getRank();
			int n = myHand.getCard(4).getRank();
			if ((j == 1) && (k == 10) && (l == 11) && (m == 12) && (n == 13)) {
				continuous = true;
			}
		}
		return continuous;
	}

	// checks for pairs
	public Object[] checkPairs(Player myHand) {
		boolean pair = false;
		int rank1 = 0;
		ArrayList<String> pairRank = new ArrayList<String>();
		for (int i = 0; i <= 3; i++) {
			rank1 = myHand.getCard(i).getRank();
			int rank2 = myHand.getCard(i + 1).getRank();
			if (rank1 == rank2) {
				if (!pairRank.contains(Integer.toString(rank1))) {
					pairRank.add(Integer.toString(rank1));
					pair = true;
				}
			}
		}
		Object[] pairs = { pair, pairRank };
		return pairs;
	}
}