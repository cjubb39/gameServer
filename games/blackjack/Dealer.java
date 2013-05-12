package games.blackjack;

/* 
 * Class for the "Dealer" object
 * Built-in rules for hitting and standing
 * 
 * Chae Jubb \\ecj2122
 * 11/05/2012
 */
import java.io.PrintWriter;
import java.util.ArrayList;

public class Dealer {

	private Hand dealerHand;
	private Deck deck;
	private PrintWriter output;

	public Dealer(Deck deck, PrintWriter out) {
		ArrayList<Card> dealerCards = new ArrayList<Card>();
		this.dealerHand = new Hand(dealerCards, false, out);
		this.deck = deck;

		this.output = out;
	}

	// controls dealer's play
	public void play() {
		boolean end = false;

		this.output.println("");
		this.output.print("Dealer:");

		while (end == false) {

			// prints dealer's hand
			this.dealerHand.print(false);
			this.output.println("");

			// intelligence
			if (this.dealerHand.score() == 21) { // check for 21
				this.output.println("Dealer has 21!");
				end = true;

			} else if (this.dealerHand.score() <= 16) {// hit
				this.dealerHand.addCard(this.deck);

			} else if (this.dealerHand.score() > 21) {// bust
				this.output.println("Dealer Busts!");
				end = true;

			} else if (this.dealerHand.score() >= 17) { // stand
				this.output.println("Dealer Stands.");
				end = true;
			}
		}
	}

	// checks for dealer blackjack
	public boolean blackjackCheck() {
		return (this.dealerHand.score() == 21);
	}

	// getter methods
	public Hand getHand() {
		return dealerHand;
	}

	public int getScore() {
		return this.dealerHand.score();
	}

}