package games.blackjack;

import java.io.PrintWriter;

/* 
 * Class for the "Card" object
 * Includes methods to print the card and access its value
 * 
 * Chae Jubb \\ecj2122
 * 11/05/2012
 */
public class Card {

	private int suit;
	private int value;
	private PrintWriter output;

	public Card(int suit, int value, PrintWriter out) {
		this.suit = suit;
		this.value = value;
		this.output = out;
	}

	// prints card in readable English
	public void print() {

		// print value
		if (this.value == 1) {
			this.output.print("A");
		} else if (this.value <= 10) {
			this.output.print(this.value);
		} else if (this.value == 11) {
			this.output.print("J");
		} else if (this.value == 12) {
			this.output.print("Q");
		} else if (this.value == 13) {
			this.output.print("K");
		}

		// print suit
		if (this.suit == 1) {
			this.output.print("S ");
		} else if (this.suit == 2) {
			this.output.print("H ");
		} else if (this.suit == 3) {
			this.output.print("D ");
		} else if (this.suit == 4) {
			this.output.print("C ");
		}
	}

	// Card value with ace equals 11
	public int getValue1() {
		if (this.value == 1) {
			return 11;
		} else if (this.value <= 10) {
			return this.value;
		} else {
			return 10;
		}
	}

	// Card value with ace equals 1
	public int getValue2() {
		if (this.value <= 10) {
			return this.value;
		} else {
			return 10;
		}
	}

}