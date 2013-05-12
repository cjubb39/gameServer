package games.blackjack;

/* 
 * Class for the "Hand" object for both player and dealer
 * Holds at minimum two cards
 * Contains methods to print and score the hand
 * 
 * Chae Jubb \\ecj2122
 * 11/05/2012
 */
import java.util.ArrayList;
import java.io.PrintWriter;

public class Hand {

	private ArrayList<Card> cards = new ArrayList<Card>();
	private boolean player;
	private double bet;
	private PrintWriter output;

	public Hand(ArrayList<Card> cards, boolean player, PrintWriter out) {
		this.cards = cards;
		this.player = player;
		this.output = out;
	}

	// add card to hand
	public void addCard(Deck deck) {
		this.cards.add(deck.hit());
	}

	// controller for print method
	public void print(boolean first) {
		if (this.player == true || first == false) {
			this.nominalPrint();
		} else {
			this.dealerPrint();
		}
	}

	// prints all cards face up
	private void nominalPrint() {
		for (int i = 0; i < this.cards.size(); i++) {
			cards.get(i).print();
		}
	}

	// prints with first card face down
	private void dealerPrint() {
		this.output.print("XX ");
		this.cards.get(1).print();
	}

	// returns score
	public int score() {
		int score = 0;

		// Ace = 11
		for (int i = 0; i < this.cards.size(); i++) {
			score += cards.get(i).getValue1();
		}

		// Ace = 1
		if (score > 21) {
			score = 0;
			for (int i = 0; i < this.cards.size(); i++) {
				score += cards.get(i).getValue2();
			}
		}

		return score;
	}

	// access specific card
	public Card getCard(int i) {
		return this.cards.get(i);
	}

	// remove card: used for splitting
	public void removeCard(int i) {
		this.cards.remove(i);
	}

	// clears hand
	public void clearHand() {
		this.cards.clear();
	}
	
	//get count of cards
	public int countCards(){
		return this.cards.size();
	}

	public double getBet() {
		return bet;
	}

	public void setBet(double bet) {
		this.bet = bet;
	}
}
