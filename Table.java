package poker;

import java.util.LinkedList;
import java.util.Stack;

import poker.Card.Rank;

public class Table {
    private LinkedList<Player> players; // Linked list of players
    private int numPlayers;            // Number of players
    private int pot;                   // Total pot value
    private Stack<Card>communityCards; // Stack of 5 community cards
    private int currentBet;            // Current bet amount in the round

    // Constructor
    public Table() {
        this.players = new LinkedList<>();
        this.numPlayers = 0;
        this.pot = 0;
        this.communityCards = new Stack<>(); // Default to null
        this.currentBet = 0;
    }

    // Add a player to the table
    public void addPlayer(Player player) {
        players.add(player);
        numPlayers++;
    }

    // Remove a player from the table
    public void removePlayer(Player player) {
        players.remove(player);
        numPlayers--;
    }

    // Get the list of players
    public LinkedList<Player> getPlayers() {
        return players;
    }
    /**
    // Set the community cards
    public void setCommunityCards(Card[] communityCards) {
        if (communityCards.length == 5) {
            this.communityCards = communityCards;
        } else {
            throw new IllegalArgumentException("Community cards must have exactly 5 cards.");
        }
    }
    */
    public void addCommunityCard(Card card) {
    	if (communityCards.size() < 5) {
			communityCards.push(card);
		}
    	else {
			throw new IllegalStateException("Cannot add more than 5 community cards.");
		}
    }
    
    public Card popCommunityCard() {
    	if (!(communityCards.size() < 0)) {
			return communityCards.pop();
		}
    	else {
			throw new IllegalStateException("Cannot pop empty community cards.");
		}
    }

    // Get the community cards
    /**
    public Card getCommunityCards() {
        if (!communityCards.isEmpty()) {
			return communityCards.pop();
		}
        else {
			throw new IllegalStateException("No community cards to pop.");
		}
    }
    */
    public Stack<Card> getCommunityCards() {
    	return communityCards;
    }

    // Add to the pot
    public void addToPot(int amount) {
        this.pot += amount;
    }

    // Get the pot value
    public int getPot() {
        return pot;
    }

    // Reset the pot
    public void resetPot() {
        this.pot = 0;
    }

    // Get the current bet
    public int getCurrentBet() {
        return currentBet;
    }

    // Set the current bet
    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    // Clear the table (e.g., between rounds)
    public void clearTable() {
        // communityCards = new Card[5]; // Reset community cards
        currentBet = 0;               // Reset current bet
        pot = 0;                      // Reset pot
    }
}