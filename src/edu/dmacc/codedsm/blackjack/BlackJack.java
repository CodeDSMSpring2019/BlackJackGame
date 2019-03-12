package edu.dmacc.codedsm.blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackJack {

    public static void main(String[] args) {
        Map<String, List<Integer>> deck = new HashMap<>();
        deck.put("Clubs", createCards());
        deck.put("Diamonds", createCards());
        deck.put("Spades", createCards());
        deck.put("Hearts", createCards());

        for (String suitInDeck : deck.keySet()) {
            List<Integer> cardsInDeck = deck.get(suitInDeck);
            for (Integer cardValue : cardsInDeck) {
                System.out.println(suitInDeck + " - " + cardValue);
            }
        }
    }

    public static List<Integer> createCards() {
        List<Integer> cards = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            cards.add(i);
        }
        return cards;
    }
}
