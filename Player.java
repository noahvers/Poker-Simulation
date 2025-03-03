package poker;

public class Player {
    private Card[] hand = new Card[2];
    private int chips;
    private boolean hasButton;
    private boolean inAction;
    private int currentBet;

    public Player(int chips) {
        this.chips = chips;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card card1, Card card2) {
        this.hand[0] = card1;
        this.hand[1] = card2;
    }
    
    public Card popCard(int i) {
    	Card copyCard = this.hand[i];
    	this.hand[i] = null;
    	return copyCard;
    }

    public int getChips() {
        return chips;
    }

    public void bet(int amount) {
        currentBet += amount;
        chips -= amount;
    }

    public boolean hasButton() {
        return hasButton;
    }

    public void setButton(boolean hasButton) {
        this.hasButton = hasButton;
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void resetCurrentBet() {
        currentBet = 0;
    }
}