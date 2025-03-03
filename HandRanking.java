package poker;

import poker.Card.Rank;

public enum HandRanking {
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIR(3),
    THREE_OF_A_KIND(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_A_KIND(8),
    STRAIGHT_FLUSH(9),
    ROYAL_FLUSH(10);

    private final int value;
    private Rank highestCard; // To store the highest card rank relevant to this hand
    private Rank secondHighestCardRank;
    private Rank kicker;

    // Constructor
    HandRanking(int value) {
        this.value = value;
        this.highestCard = null; // Default value when no highest card is set
        this.secondHighestCardRank = null;
        this.kicker = null;
    }

    // Getter for the ranking value
    public int getValue() {
        return value;
    }

    // Getter and setter for the highest card rank
    public Rank getHighestCard() {
        return highestCard;
    }

    public void setHighestCard(Rank highestCard) {
        this.highestCard = highestCard;
    }
    
    public Rank getSecondHighestCard() {
        return secondHighestCardRank;
    }

    public void setSecondHighestCard(Rank secondHighestCard) {
        this.secondHighestCardRank = highestCard;
    }
    
    public Rank getKicker() {
        return kicker;
    }

    public void setKicker(Rank kicker) {
        this.kicker = kicker;
    }   

    // Method to compare two hand rankings, considering the highest card
    public boolean isStrongerThan(HandRanking other) {
        if (this.value > other.value) {
            return true;
        } else if (this.value < other.value) {
            return false;
        } else {
            // If hand rankings are equal, compare the highest card
            if (this.highestCard != null && other.highestCard != null) {
                return this.highestCard.getValue() > other.highestCard.getValue();
            }
            return false; // Default case if highestCard is not set
        }
    }
}
