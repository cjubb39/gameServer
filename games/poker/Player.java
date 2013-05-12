package games.poker;

/**
 * BORROWED CODE. Modified slightly to fit interfaces and general gameServer setup.
 */

//****************************************
// ao2402
// This class creates allows the player to create a hand, 
//sorts it and shows it. 
//****************************************
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Player {

	private Scanner input;
	private PrintWriter output;

	public Player(Scanner in, PrintWriter pw){
		this.input = in;
		this.output = pw;
	}

	ArrayList<Card> myHand = new ArrayList<Card>();

	// deals top 5 cards to a hand for the player
	public ArrayList<Card> makeHand(Deck deck) {
		for (int i = 0; i <= 4; i++) {
			myHand.add(deck.getCard(i));
		}
		return myHand;
	}
	
	//make a hand that the user can input.
	public ArrayList<Card> makeCheaterHand() {
		this.output.println("Face ranked cards are as follows:");
		this.output.println("ACE = 1");
		this.output.println("JACK = 11");
		this.output.println("QUEEN = 12");
		this.output.println("KING = 13");
		
		this.output.println("Suits are as follows:");
		this.output.println("SPADES = 4");
		this.output.println("HEARTS = 3");
		this.output.println("DIAMONDS = 2");
		this.output.println("CLUBS = 1");
		
		int suit, rank;
		//Scanner input = new Scanner(System.in);
		Card cardObject;
		
		for (int i = 0; i<=4; i++){
			this.output.println("Please enter the suit of card " + (i+1));
			suit = input.nextInt();
			this.output.println("Please enter the rank of card " + (i+1));
			rank = input.nextInt();
			cardObject = new Card(suit, rank);
			myHand.add(cardObject);
		}
		return myHand;	
	}

	// sorts the player's hand by rank
	public void sortHand() {
		Collections.sort(myHand);
	}

	// shows the player their hand
	public void showHand() {
		this.output.println("This is your hand:");
		for (int i = 0; i <= 4; i++) {
			Card playingCard = myHand.get(i);
			this.output.println(i + 1 + "." + playingCard.toString());

		}
	}
	
	//gets a card at a specific index
	public Card getCard(int index) {
		return myHand.get(index);

	}
	
	//clears hand
	public void clearHand(){
		myHand.clear();
	}

	// allows the player to reject cards, replaces them
	public ArrayList<Card> replaceHand(Deck deck) {
		//Scanner input = new Scanner(System.in);
		this.output.println("Do you want to reject any cards?(y/n)");
		if (input.next().equalsIgnoreCase("y")) {
			this.output
					.println("Please enter how many cards you want to replace(between 1-5): ");
			int i = input.nextInt();
			for (int j = 0; j < i; j++) {
				this.output
						.println("Please enter which card number you want to replace: ");
				int index = input.nextInt() - 1;
				int k = 5 + j;
				myHand.set(index, deck.getCard(k));
			}
		}
	return myHand;
	}
}