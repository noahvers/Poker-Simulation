package poker;

import java.util.Stack;
import java.util.Collections;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();
        for (Card.Rank rank : Card.Rank.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                cards.push(new Card(rank, suit));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
    	System.out.println(cards.peek().getRank() + " " + cards.peek().getSuit());
        return cards.pop();
    }

    public void addCard(Card card) {
        cards.push(card);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }	
}
