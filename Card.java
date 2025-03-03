package poker;

public class Card {
	public enum Rank {
	    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), 
	    J(11), Q(12), K(13), A(14);

	    private final int value;

	    // Constructor to assign value to each rank
	    Rank(int value) {
	        this.value = value;
	    }

	    // Getter to retrieve the value
	    public int getValue() {
	        return value;
	    }
	}

	public enum Suit {
        DIAMONDS, SPADES, HEARTS, CLUBS
    }
	
	private final Rank rank;
	private final Suit suit;
	
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
