package edu.dmacc.codedsm.blackjack;

import java.util.*;

public class BlackJack {

    public static void main(String[] args) {
        Map<String, List<Integer>> deck = initializeDeck();
        List<Card> playerHand = new ArrayList<>();

        List<Card> chosenCards = DeckRandomizer.chooseRandomCards(deck, 2);
        playerHand.addAll(chosenCards);
        for (Card card : chosenCards) {
            removeCardFromDeck(deck, card);
        }
        System.out.println("Player's hand is: ");
        showHand(playerHand);

        boolean continueGame = true;
        Scanner in = new Scanner(System.in);
        while (continueGame) {
            System.out.println("Enter 1 to Hit, 2 to Stand");
            String input = in.next();
            if (input.equals("1")) {
                List<Card> nextPlayerCard = DeckRandomizer.chooseRandomCards(deck, 1);
                playerHand.addAll(nextPlayerCard);
                removeCardFromDeck(deck, nextPlayerCard.get(0));
            } else if (input.equals("2")) {
                continueGame = false;
            } else {
                showErrorMessage();
            }

            System.out.println("Player's hand is: ");
            showHand(playerHand);
        }

        int sumOfHand = 0;
        for (Card card : playerHand) {
            sumOfHand = sumOfHand + card.value;
        }
        System.out.printf("Player\'s Hand was %d points.\n", sumOfHand);
    }

    private static Map<String, List<Integer>> initializeDeck() {
        Map<String, List<Integer>> deck = new HashMap<>();
        deck.put("Clubs", createCards());
        deck.put("Diamonds", createCards());
        deck.put("Spades", createCards());
        deck.put("Hearts", createCards());

        return deck;
    }

    private static List<Integer> createCards() {
        List<Integer> cards = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            cards.add(i);
        }
        return cards;
    }

    private static void removeCardFromDeck(Map<String, List<Integer>> deck, Card card) {
        List<Integer> cardsInSuit = deck.get(card.suit);
        cardsInSuit.remove(card.value);
    }

    private static void showHand(List<Card> playerHand) {
        for (Card card : playerHand) {
            System.out.printf("%s - %d, ", card.suit, card.value);
        }
        System.out.println("\n");
    }

    private static void showErrorMessage() {
        System.out.println("Invalid input");
    }

}
