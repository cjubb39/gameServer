HOMEWORK 4
Chae Jubb \\ ecj2122

To Use this Software:
Follow the on-screen prompts. They will force you to enter a valid input.
When cards are printed on-screen, the first character represents the value and the second
	represents the suit.  e.g. QH is the Queen of Hearts; AS is the Ace of Spades.

House Rules:
1) Must buy in with at least $100
2) Must leave the table when balance drops below minimum bet ($10)
3) Maximum bet is $1000
4) Dealer stands on 17
5) Splitting allowed only once, as first action
6) Doubling down allowed only as first action
7) Must have enough money to cover splitting or doubling down
8) Blackjack pays 3:2

Explanation:
The Game class controls the flow of the game.  It creates a deck and player & dealer objects.
	The player and dealer objects each have an associated Hand object, composed of Card
	objects from the Deck object.
The deck ultimately controls dealing and laying down additional cards.  Because the player
	and dealer have similar roles, I chose to put those responsibilities with the deck as to
	make it clearer and more isolated from the players.
The shuffling methodology was chosen as such to loop through the basic shuffling procedure an
	extremely random number of times.  The user is not notified when the deck is shuffled to
	make it more difficult to "count cards".