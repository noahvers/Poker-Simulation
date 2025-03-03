package poker;

import poker.Card;
import poker.Deck;
import poker.Player;
import poker.Table;
import poker.HandRanking;
import poker.HandEvaluator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		Deck deck = new Deck();
		System.out.println(deck.draw().toString());
		Player noahPlayer = new Player(1000);
		noahPlayer.setButton(false);
		noahPlayer.setHand(deck.draw(), deck.draw());
		// Card[2] test = noahPlayer.getHand();
		Card[] test = new Card[2];
		test = noahPlayer.getHand();
		System.out.println(test[0].toString());
		System.out.println(test[1].toString());
		*/
		/**
		if(Card.Rank.A.getValue() > Card.Rank.EIGHT.getValue()) {
			System.out.println("test true");
		}
		*/
		Table pokerTable = new Table();
		Player noahPlayer = new Player(1000);
		Deck deck = new Deck();
		
		
        long startTimeOptimized = System.nanoTime();
		// Optimized solution
		for(int i = 0; i < 100; i++) {
		
		deck.shuffle();
		
		pokerTable.addCommunityCard(deck.draw());
		pokerTable.addCommunityCard(deck.draw());
		pokerTable.addCommunityCard(deck.draw());
		pokerTable.addCommunityCard(deck.draw());
		pokerTable.addCommunityCard(deck.draw());
		
		noahPlayer.setHand(deck.draw(), deck.draw());
		
		HandRanking bestHandRanking;
		
		bestHandRanking = HandEvaluator.getBestHand(HandEvaluator.getCombinedCards(pokerTable.getCommunityCards(), noahPlayer.getHand()));
		// System.out.println(bestHandRanking.getValue());
		// System.out.println(bestHandRanking.getHighestCard());
		
		deck.addCard(pokerTable.popCommunityCard());
		deck.addCard(pokerTable.popCommunityCard());
		deck.addCard(pokerTable.popCommunityCard());
		deck.addCard(pokerTable.popCommunityCard());
		deck.addCard(pokerTable.popCommunityCard());
		
		deck.addCard(noahPlayer.popCard(0));
		deck.addCard(noahPlayer.popCard(1));
		}
        long endTimeOptimized = System.nanoTime();
        long durationOptimized = endTimeOptimized - startTimeOptimized;

}
}
