package poker;

import java.awt.Window.Type;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import poker.Card.Rank;

public class HandEvaluator {
    public static ArrayList<Card> getCombinedCards(Stack<Card> communityCards, Card[] playerCards) {
        ArrayList<Card> combined = new ArrayList<>();

        // Add community cards
        combined.addAll(communityCards);

        // Add player's hole cards
        Collections.addAll(combined, playerCards);

        return combined;
    }
    
    public static void sortCardsByRank(ArrayList<Card> cards) {
        cards.sort((c1, c2) -> Integer.compare(c1.getRank().getValue(), c2.getRank().getValue()));
    }    
    
    public static HandRanking getBestHand(ArrayList<Card> combinedCards) {
    	sortCardsByRank(combinedCards);
    	HandRanking returnHandRanking;
    	
        // Step 1: Create the HashMaps
        HashMap<Card.Suit, Integer> suitCount = new HashMap<>();
        HashMap<Card.Rank, Integer> rankCount = new HashMap<>();
        
        Boolean hasFlush = false;

        // Populate the HashMaps
        for (Card card : combinedCards) {
            // Update suit count
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);

            // Update rank count
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
        }
        
        if (suitCount.containsValue(5)) {
			hasFlush = true;
		}
        
        // Debug: Print the HashMaps
        // System.out.println("Suit Count: " + suitCount);
        // System.out.println("Rank Count: " + rankCount);
        
        // Hand evaluation logic
        // Royal Flush
        if (hasFlush) { // 
        	for(int i = 0; i < 3; i++) {
        		if (combinedCards.get(i).getRank() == Rank.TEN
        				&& combinedCards.get(i+1).getRank() == Rank.J
        				&& combinedCards.get(i+2).getRank() == Rank.Q 
        				&& combinedCards.get(i+3).getRank() == Rank.K 
        				&& combinedCards.get(i+4).getRank() == Rank.A) {
        			returnHandRanking = HandRanking.ROYAL_FLUSH;
        			returnHandRanking.setHighestCard(Rank.A);
        			return returnHandRanking;
				}
        	}
		}
        // Straight Flush
        if (hasFlush) {
			for(int i = 0; i < 3; i++) {
				if (combinedCards.get(i).getRank().getValue() == combinedCards.get(i+1).getRank().getValue() + 1
						&& combinedCards.get(i).getRank().getValue() == combinedCards.get(i+2).getRank().getValue() + 2 
						&& combinedCards.get(i).getRank().getValue() == combinedCards.get(i+3).getRank().getValue() + 3 
						&& combinedCards.get(i).getRank().getValue() == combinedCards.get(i+4).getRank().getValue() + 4
						&& combinedCards.get(i).getSuit() == combinedCards.get(i+1).getSuit()
						&& combinedCards.get(i).getSuit() == combinedCards.get(i+2).getSuit()
						&& combinedCards.get(i).getSuit() == combinedCards.get(i+3).getSuit()
						&& combinedCards.get(i).getSuit() == combinedCards.get(i+4).getSuit()) {
					returnHandRanking = HandRanking.STRAIGHT_FLUSH;
					returnHandRanking.setHighestCard(combinedCards.get(i+4).getRank());
					return returnHandRanking;
				}
			}
		}
        // Four of a Kind
        if (rankCount.containsValue(4)) {
			for(Map.Entry<Card.Rank, Integer> entry : rankCount.entrySet()) {
				if (entry.getValue() == 4) {
					returnHandRanking = HandRanking.FOUR_OF_A_KIND;
					returnHandRanking.setHighestCard(entry.getKey());
					returnHandRanking.setKicker(findKicker(rankCount, entry.getKey()));
					return returnHandRanking;
				}
			}
		}
        // Full House
        if (rankCount.containsValue(3) && rankCount.containsValue(2)) {
        	HandRanking fullHouseRanking = HandRanking.FULL_HOUSE;
			for(Map.Entry<Card.Rank, Integer> entry : rankCount.entrySet()) {
				if (entry.getValue() == 3) {
					fullHouseRanking.setHighestCard(entry.getKey());
				}
				if (entry.getValue() == 2) {
					fullHouseRanking.setSecondHighestCard(entry.getKey());
				}
			}
			// returnHandRanking = HandRanking.FULL_HOUSE;
			return fullHouseRanking;
		}
        // Flush
        if (hasFlush) {
            Card.Suit flushSuit = null;
            Card.Rank highestRank = Rank.TWO;

            // Find the suit with 5 or more cards
            for (Map.Entry<Card.Suit, Integer> entry : suitCount.entrySet()) {
                if (entry.getValue() >= 5) {
                    flushSuit = entry.getKey();
                    break;
                }
            }
            for(Card card : combinedCards) {
            	if (card.getSuit() == flushSuit) {
					if (highestRank.getValue() < card.getRank().getValue()) {
						highestRank = card.getRank();
					}
				}
            }
            returnHandRanking = HandRanking.FLUSH;
            returnHandRanking.setHighestCard(highestRank);
            return returnHandRanking;
		}
        // Straight
			for(int i = 0; i < 3; i++) {
				if (combinedCards.get(i).getRank().getValue() == combinedCards.get(i+1).getRank().getValue() + 1
						&& combinedCards.get(i).getRank().getValue() == combinedCards.get(i+2).getRank().getValue() + 2 
						&& combinedCards.get(i).getRank().getValue() == combinedCards.get(i+3).getRank().getValue() + 3 
						&& combinedCards.get(i).getRank().getValue() == combinedCards.get(i+4).getRank().getValue() + 4) {
					returnHandRanking = HandRanking.STRAIGHT;
					returnHandRanking.setHighestCard(combinedCards.get(i+4).getRank());
					return returnHandRanking;
				}
			}
			
        // Three of a Kind
        if (rankCount.containsValue(3)) {
			for(Map.Entry<Card.Rank, Integer> entry : rankCount.entrySet()) {
				if (entry.getValue() == 3) {
					returnHandRanking = HandRanking.THREE_OF_A_KIND;
					returnHandRanking.setHighestCard(entry.getKey());
					returnHandRanking.setKicker(findKicker(rankCount, entry.getKey()));
					return returnHandRanking;
				}
			}
		}
        
        // Two Pair       
        int pairCount = 0;
        	
        for(int count : rankCount.values()) {
        	if (count == 2) {
				pairCount++;
			}
        }               	
		if (pairCount >= 2) {
		    Card.Rank highestPair = null;
		    Card.Rank secondHighestPair = null;

		    for (Map.Entry<Card.Rank, Integer> entry : rankCount.entrySet()) {
		        if (entry.getValue() == 2) {
		            if (highestPair == null || entry.getKey().ordinal() > highestPair.ordinal()) {
		                secondHighestPair = highestPair; // Move current highest down
		                highestPair = entry.getKey(); // New highest pair
		            } else if (secondHighestPair == null || entry.getKey().ordinal() > secondHighestPair.ordinal()) {
		                secondHighestPair = entry.getKey(); // Update second highest
		            }
		        }
		    }

		    if (highestPair != null && secondHighestPair != null) {
		    	returnHandRanking = HandRanking.TWO_PAIR;
		        returnHandRanking.setHighestCard(highestPair); // Highest pair
		        returnHandRanking.setKicker(find2Kicker(rankCount, highestPair, secondHighestPair)); // Highest card excluding pairs
		        return returnHandRanking;
		    }			
		}
		
        // Pair
         if (rankCount.containsValue(2)) {
        	    for (Map.Entry<Card.Rank, Integer> entry : rankCount.entrySet()) {
        	        if (entry.getValue() == 2) {
        	        	returnHandRanking = HandRanking.ONE_PAIR;
        	            returnHandRanking.setHighestCard(entry.getKey()); // Pair rank
        	            returnHandRanking.setKicker(findKicker(rankCount, entry.getKey())); // Highest kicker
        	            return returnHandRanking;
        	        }
        	    }
		}
        // High Card
        else {
			returnHandRanking = HandRanking.HIGH_CARD;
			returnHandRanking.setHighestCard(combinedCards.get(6).getRank());
			returnHandRanking.setKicker(find2Kicker(rankCount, combinedCards.get(6).getRank()));
			return returnHandRanking;
		}
         return null;
    }   

    private static Card.Rank findKicker(HashMap<Card.Rank, Integer> rankCount, Card.Rank excludedRank) {
        Card.Rank kicker = null;
        for (Card.Rank rank : rankCount.keySet()) {
            if (!rank.equals(excludedRank)) { // Exclude the main rank (e.g., quads, trips, etc.)
                if (kicker == null || rank.ordinal() > kicker.ordinal()) {
                    kicker = rank;
                }
            }
        }
        return kicker;
    }
    private static Card.Rank find2Kicker(HashMap<Card.Rank, Integer> rankCount, Card.Rank... excludedRanks) {
        HashSet<Card.Rank> excludedSet = new HashSet<>(Arrays.asList(excludedRanks));
        Card.Rank kicker = null;

        for (Card.Rank rank : rankCount.keySet()) {
            if (!excludedSet.contains(rank)) { // Exclude specified ranks
                if (kicker == null || rank.ordinal() > kicker.ordinal()) {
                    kicker = rank;
                }
            }
        }
        return kicker;
    }
}
