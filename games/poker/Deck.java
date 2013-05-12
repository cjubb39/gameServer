package games.poker;

/**
 * BORROWED CODE.
 */
//****************************************
// ao2402
// This class creates a full suit, and carries out important 
//operations within that, including shuffling and drawing a
// card
//
//****************************************

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	// instantiate variables
	private ArrayList<Card> deck;

	// Constructor
	public Deck() {
		deck = new ArrayList<Card>();
		Card cardObject;
		int i, j, suit, rank;

		// Creates a deck (w/o shuffling)
		for (i = 0; i <= 3; i++) {
			suit = i + 1;
			for (j = 0; j <= 12; j++) {
				rank = j + 1;
				cardObject = new Card(suit, rank);
				deck.add(cardObject);
			}
		}
	}

	// Shuffles deck
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}

	// Draws a card
	public Card getCard(int index) {
		return deck.get(index);

	}

}