package games.blackjack;

/* 
 * Class for the "Player" object
 * Primary method to allow player to make blackjack moves 
 * 	including hit, stand, split, double down
 * Allows player to play two hands if original hand split
 * Various setter and getter methods
 * 
 * Chae Jubb \\ecj2122
 * 11/05/2012
 */
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	private Hand playerHand;
	private Hand playerHand2;
	private boolean alreadySplit;
	private boolean end = false;
	private Deck deck;
	private double balance;
	//Scanner input = new Scanner(System.in);
	private Scanner input;
	private PrintWriter output;

	public Player(Deck deck, double balance, Scanner in, PrintWriter out) {
		ArrayList<Card> playerCards = new ArrayList<Card>();
		this.playerHand = new Hand(playerCards, true, out);
		this.deck = deck;
		this.setBalance(balance);

		this.input = in;
		this.output = out;
	}

	// controller for play: plays two hands if split
	public void play() {

		this.alreadySplit = false;
		this.play1(this.playerHand);

		// plays only if hands split
		if (this.alreadySplit == true) {
			this.output.println("");
			this.output.println("Hand 2");
			this.play1(this.playerHand2);
		}

	}

	// leads player through blackjack moves
	private void play1(Hand hand) {
		this.end = false;
		String choice = "S";

		this.output.print("Player: ");

		// Play until bust, 21, double down, stand
		while (this.end == false) {
			hand.print(false);
			this.output.println("");
			this.output.println("Hit: H; Stand: S; Split: P; Double Down: D");
			choice = input.next();

			// options
			if (choice.equalsIgnoreCase("H")) {
				hand.addCard(this.deck);
			} else if (choice.equalsIgnoreCase("S")) {
				this.end = true;
			} else if (choice.equalsIgnoreCase("P")) {
				this.split(hand);
			} else if (choice.equalsIgnoreCase("D")) {
				this.dDown(hand);
			}

			// checks score
			if (hand.score() > 21) {
				this.end = true;
				hand.print(false);
				this.output.println("You Bust!");
			} else if (hand.score() == 21) {
				this.end = true;
				hand.print(false);
				this.output.println("You got 21!");
			}
		}
	}

	// method controlling splits
	private void split(Hand hand) {
		int card1Value = this.playerHand.getCard(0).getValue1();
		int card2Value = this.playerHand.getCard(1).getValue2();

		// money check
		if ((2 * hand.getBet()) <= this.getBalance()) {

			// make sure conditions met
			if ((this.playerHand.countCards() == 2)
					&& (card1Value == card2Value) && !alreadySplit) {
				// create second hand
				ArrayList<Card> card2 = new ArrayList<Card>();
				card2.add(this.playerHand.getCard(1));
				this.playerHand2 = new Hand(card2, true, this.output);

				// create first hand
				this.playerHand.removeCard(1);

				// deal next cards
				this.playerHand.addCard(this.deck);
				this.playerHand2.addCard(this.deck);

				this.output.println("Hand 1");
				this.alreadySplit = true;
				this.playerHand2.setBet(this.playerHand.getBet());

				// restrictions on splitting
			} else if (this.playerHand.countCards() != 2 || alreadySplit) {
				this.output.println("Splitting not allowed here");
			} else if (card1Value != card2Value) {
				this.output
						.println("Splits only allowed with two cards of same value");
			}

		} else {
			this.output.println("Not enough money to split");
		}
	}

	// method controlling doubling down
	private void dDown(Hand hand) {

		// makes sure first move
		if (hand.countCards() == 2) {

			// money check
			if ((2 * hand.getBet()) <= this.getBalance()) {
				hand.setBet(2 * hand.getBet());
				hand.addCard(this.deck);
				hand.print(false);
				this.output.println("Turn Over");
				this.end = true;
			} else {
				this.output.println("Not enough money to Double Down");
			}

		} else {
			this.output.println("Double Down only as first action");
		}
	}

	// checks for blackjack
	public boolean blackjackCheck() {
		return (this.playerHand.score() == 21);
	}

	// method controlling betting
	public void inputBet() {
		double bet = 0;
		boolean accepted = false;

		while (accepted == false) {
			// first time check for number
			try {
				//Scanner input = new Scanner(System.in);
				this.output.println("Enter Bet(10 <= x <= 1000): ");
				bet = this.input.nextDouble();
			} catch (Exception e) {
			}

			// subsequent checks for number
			while (bet < 10 || bet > 1000) {
				try {
					//Scanner input = new Scanner(System.in);
					this.output.println("Please enter number (10 <= x <= 1000): ");
					bet = this.input.nextDouble();
				} catch (Exception e) {
				}
			}

			// balance check
			if (bet <= this.getBalance()) {
				accepted = true;
			} else {
				this.output.println("Not enough money. You have "
						+ this.getBalance());
			}
		}

		// bet finally accepted as valid
		this.playerHand.setBet(bet);

	}

	// getter and setter methods
	public Hand getHand() {
		return playerHand;
	}

	public Hand getHand2() {
		return playerHand2;
	}

	/*
	 * public double getBet() { return bet; }
	 * 
	 * public void setBet(double bet) { this.bet = bet; }
	 */
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void withdraw(double amount) {
		this.balance -= amount;
	}

	public void deposit(double amount) {
		this.balance += amount;
	}

	public int getScore() {
		return this.playerHand.score();
	}

	public int getScore2() {
		return this.playerHand2.score();
	}

	public boolean getSplit() {
		return alreadySplit;
	}
}
