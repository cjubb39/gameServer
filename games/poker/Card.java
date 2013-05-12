package games.poker;

/**
 * BORROWED CODE
 */
//****************************************
// ao2402
// This class creates a blank card object, with a possibility of having a suit and a rank
//****************************************

public class Card implements Comparable<Card> {
	// face card values
	public static final int ACE = 1;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;

	// suit values
	public static final int SPADES = 4;
	public static final int HEARTS = 3;
	public static final int DIAMONDS = 2;
	public static final int CLUBS = 1;

	private int suit, rank;

	// create a new card with the specified information
	public Card(int suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	// compares cards to see if rank greater/less than
	public int compareTo(Card card) {
		if (this.rank < card.rank) {
			return -1;
		} else if (this.rank > card.rank) {
			return 1;
		} else
			return 0;

	}

	// compares cards to see if ranks are equal
	public boolean compareEqual(Card card) {
		if (this.rank == card.rank) {
			return true;
		} else
			return false;
	}
	
	//get Rank
	public int getRank() {
		return this.rank;
	}

	//get Suit
	public int getSuit() {
		return this.suit;
	}
	// compares cards to see if suits are equal
	public boolean compareSuit(Card card) {
		if (this.suit == card.suit) {
			return true;
		} else
			return false;
	}

	// creates a string with the card name
	public String toString() {
		String card = "";
		switch (this.rank) {
		case ACE:
			card += "A";
			break;
		case JACK:
			card += "J";
			break;
		case QUEEN:
			card += "Q";
			break;
		case KING:
			card += "K";
			break;
		default:
			card += this.rank;
			break;
		}
		switch (this.suit) {
		case HEARTS:
			card += " of Hearts";
			break;
		case CLUBS:
			card += " of Clubs";
			break;
		case SPADES:
			card += " of Spades";
			break;
		case DIAMONDS:
			card += " of Diamonds";
			break;
		}
		return card;
	}
}