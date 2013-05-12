package games.blackjack;

/* 
 * Class for the "Deck" object
 * Primary methods are dealing and shuffling
 * Shuffling using complex random algorithm
 * 
 * Chae Jubb \\ecj2122
 * 11/05/2012
 */
import java.io.PrintWriter;
import java.util.ArrayList;

public class Deck {

	private ArrayList<Card> cards = new ArrayList<Card>();
	private int counter = 0;

	public Deck(PrintWriter out) {

		// creates deck with all 52 cards
		for (int i = 1; i <= 4; i++) { // suit
			for (int j = 1; j <= 13; j++) { // value
				Card newCard = new Card(i, j, out);
				this.cards.add(newCard);
			}
		}
	}

	// prints deck in order
	public void print() {
		for (int i = 0; i <= 51; i++)
			this.cards.get(i).print();
	}

	// accesses specific card in deck by index
	public Card get(int i) {
		return this.cards.get(i);
	}

	// deals new hand to player and dealer
	public void deal(Hand player, Hand dealer) {
		// clear old hand
		player.clearHand();
		dealer.clearHand();

		// build new hand
		player.addCard(this);
		dealer.addCard(this);
		player.addCard(this);
		dealer.addCard(this);

	}

	// thoroughly and randomly shuffles
	public void shuffle() {
		Card temp;
		int index;

		// this specific shuffling algorithm needs only one shuffle to be random
		for (int j = this.cards.size() - 1; j >= 0; j--) {

			// exchange two cards
			index = (int) (Math.random() * j);

			temp = this.cards.get(j);
			this.cards.set(j, this.cards.get(index));
			this.cards.set(index, temp);

		}

		// reset counter
		this.counter = 0;
	}

	// check if less than 12 cards remain and shuffle necessary
	public void checkShuffle() {
		if (this.getCounter() > 40) {
			this.shuffle();
		}
	}

	// deals top card
	public Card hit() {
		this.counter++;
		return this.cards.get(this.counter - 1);
	}

	// accessor method for counter
	public int getCounter() {
		return this.counter;
	}

}
