package games.blackjack;

/* 
 * Class for the "Game" object
 * Controls game: Scores result of game and pays out winnings
 * Creates deck, player, dealer objects
 * Continues until less money than minimum bet or manual quit
 * 
 * Chae Jubb \\ecj2122
 * 11/05/2012
 */
import java.io.PrintWriter;
import java.util.Scanner;

public class Game implements server.Game{

	private boolean playMore;
	private boolean aBlackjack;
	private PrintWriter output;
	private Scanner input;

	public Game(Scanner in, PrintWriter pw) {
		this.playMore = true;
		this.aBlackjack = false;
		this.input = in;
		this.output = pw;
	}

	// controls game
	public void play() {

		// create deck and players
		Deck deck = new Deck(this.output);
		Player player = new Player(deck, this.buyin(), this.input, this.output);
		Dealer dealer = new Dealer(deck, this.output);

		deck.shuffle();

		// repeating gameplay
		while (this.playMore == true) {
			this.aBlackjack = false;

			deck.checkShuffle();

			player.inputBet();

			// deals two cards to each player
			deck.deal(player.getHand(), dealer.getHand());

			this.blackjackcheck(player, player.getHand(), dealer);

			// if player has no blackjack, we continue
			if (this.aBlackjack == false) {

				// show dealer's card
				this.output.print("Dealer: ");
				dealer.getHand().print(true);
				this.output.println("");

				// player takes his turn
				player.play();

				// dealer takes his turn
				dealer.play();

				// determine winner and control winnings
				this.score(player, dealer);
			}

			// makes sure player has at least minimum bet
			if (player.getBalance() < 10) {
				this.output.println("Not Enough Money to Bet; Goodbye");
				this.playMore = false;
				break;
			}

			// repeat?
			this.playAgain();
		}
	}

	// checks for a player blackjack
	private void blackjackcheck(Player player, Hand hand, Dealer dealer) {

		// player blackjack without dealer blackjack
		if (player.blackjackCheck() && !dealer.blackjackCheck()) {

			// prints hands
			this.output.print("Player Hand: ");
			player.getHand().print(false);
			this.output.print("Dealer Hand: ");
			dealer.getHand().print(false);
			this.output.println("");

			// result of hand
			this.output.println("Player has Blackjack!");
			this.output.println("Player wins " + 1.5 * hand.getBet());
			player.deposit(1.5 * hand.getBet());
			this.output.println("You now have: " + player.getBalance());

			this.aBlackjack = true;

			// player and dealer have blackjack
		} else if (player.blackjackCheck() && dealer.blackjackCheck()) {

			// prints hands
			this.output.print("Player Hand: ");
			player.getHand().print(false);
			this.output.print("Dealer Hand: ");
			dealer.getHand().print(false);
			this.output.println("");

			// result of hand
			this.output.println("Player and Dealer have Blackjack!");
			this.output.println("Push. No money lost or gained");
			this.output.println("You now have: " + player.getBalance());

			this.aBlackjack = true;
		}
	}

	// controls initial buy-in
	private double buyin() {
		double amount = 0;

		// first time check
		try {
			//Scanner input = new Scanner(System.in);
			this.output.println("Enter Buy-in Amount (>= $100): ");
			amount = input.nextDouble();
		} catch (Exception e) {
		}

		// subsequent checks
		while (amount < 100) {
			try {
				//Scanner input = new Scanner(System.in);
				this.output.println("Please enter number >= 100: ");
				amount = input.nextDouble();
			} catch (Exception e) {
			}
		}

		return amount;

	}

	// finds result of hand
	private void score(Player player, Dealer dealer) {
		int dealerScore = dealer.getScore();

		// set up results if split occurred
		if (player.getSplit() == true) {
			this.output.println("Hand 1");
		}

		// computes winner on hand 1
		this.winner(player, player.getHand(), player.getScore(), dealerScore);

		// computes winner on hand 2 (if exists)
		if (player.getSplit() == true) {
			this.output.println("Hand 2");
			this.winner(player, player.getHand2(), player.getScore2(), dealerScore);
		}

		this.output.println("You now have: " + player.getBalance());
	}

	// finds winner and prints appropriate winnings/loss
	private void winner(Player player, Hand hand, int playerScore, int dealerScore) {
		if (dealerScore > 21 && playerScore <= 21) {
			this.output.println("Player Wins! Collect " + hand.getBet());
			player.deposit(hand.getBet());

		} else if (playerScore > 21 && dealerScore <= 21) {
			this.output.println("Player Loses. Lose " + hand.getBet());
			player.withdraw(hand.getBet());

		} else if ((playerScore > 21 && dealerScore > 21)
				|| playerScore == dealerScore) {
			this.output.println("Push. No money gained or lost.");

		} else if (playerScore > dealerScore) {
			this.output.println("Player Wins! Collect " + hand.getBet());
			player.deposit(hand.getBet());

		} else if (dealerScore > playerScore) {
			this.output.println("Player Loses. Lose " + hand.getBet());
			player.withdraw(hand.getBet());
		}
	}

	// asks user to play again
	private void playAgain() {
		this.output.println("Play again? (y/n)");
		//Scanner input = new Scanner(System.in);
		String choice = input.next();

		if (choice.equalsIgnoreCase("y")) {
		} else if (choice.equalsIgnoreCase("n")) {
			this.playMore = false;
		} else {
			this.playAgain();
		}
	}
}
