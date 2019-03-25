package edu.dmacc.codedsm.blackjack;

import java.io.PrintWriter;
import java.util.*;

public class BlackJack {

    public static void main(String[] args) {
        Map<String, List<Integer>> deck = initializeDeck();
        List<Card> playerHand = new ArrayList<>();
        List<Card> dealerHand = new ArrayList<>();

        setupHands(deck, playerHand, dealerHand);
        showInitialHands(playerHand, dealerHand);

        playersTurn(deck, playerHand);
        dealersTurn(deck, dealerHand);

        int sumOfPlayerHand = scoreHand(playerHand);
        System.out.printf("Player\'s Hand was %d points.\n", sumOfPlayerHand);
        int sumOfDealerHand = scoreHand(dealerHand);
        System.out.printf("Dealer\'s Hand was %d points.\n", sumOfDealerHand);

        String resultMessage = determineWinner(sumOfPlayerHand, sumOfDealerHand);
        printResults(playerHand, dealerHand, resultMessage);
    }

    private static void setupHands(Map<String, List<Integer>> deck, List<Card> playerHand, List<Card> dealerHand) {
        List<Card> chosenCards = DeckRandomizer.chooseRandomCards(deck, 4);
        playerHand.add(chosenCards.get(0));
        playerHand.add(chosenCards.get(1));
        dealerHand.add(chosenCards.get(2));
        dealerHand.add(chosenCards.get(3));
        for (Card card : chosenCards) {
            removeCardFromDeck(deck, card);
        }
    }

    private static void showInitialHands(List<Card> playerHand, List<Card> dealerHand) {
        System.out.println("Player's hand is: ");
        System.out.println(showHand(playerHand));
        System.out.println("Dealer's up card is: ");
        System.out.println(printCard(dealerHand.get(0)));
        System.out.println("");
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

    private static void playersTurn(Map<String, List<Integer>> deck, List<Card> playerHand) {
        boolean continueGame = true;
        Scanner in = new Scanner(System.in);
        int sumOfHand = scoreHand(playerHand);
        while (continueGame && sumOfHand < 21) {
            System.out.println("Enter 1 to Hit, 2 to Stand");
            String input = in.next();
            if (input.equals("1")) {
                List<Card> nextCard = DeckRandomizer.chooseRandomCards(deck, 1);
                playerHand.addAll(nextCard);
                sumOfHand = scoreHand(playerHand);
                removeCardFromDeck(deck, nextCard.get(0));
            } else if (input.equals("2")) {
                continueGame = false;
            } else {
                showErrorMessage();
            }

            System.out.println("Player\'s hand is: ");
            System.out.println(showHand(playerHand));
            System.out.println("Player\'s score is");
            System.out.println(scoreHand(playerHand));
        }
    }

    private static void dealersTurn(Map<String, List<Integer>> deck, List<Card> dealersHand) {
        boolean continueGame = true;
        while (continueGame) {
            int dealerSum = scoreHand(dealersHand);
            if (dealerSum <= 16) {
                List<Card> nextCard = DeckRandomizer.chooseRandomCards(deck, 1);
                dealersHand.addAll(nextCard);
                removeCardFromDeck(deck, nextCard.get(0));
            } else {
                continueGame = false;
            }

            System.out.println("Dealer\'s hand is: ");
            System.out.println(showHand(dealersHand));
            System.out.println("Dealer\'s score is");
            System.out.println(scoreHand(dealersHand));
        }
    }

    private static String determineWinner(int sumOfPlayerHand, int sumOfDealerHand) {
        String winningMessageFormat = "%s wins!";
        String message = "";
        if (sumOfPlayerHand > 21
                || sumOfDealerHand == 21
                || (sumOfDealerHand < 21 && sumOfDealerHand > sumOfPlayerHand)) {
            message = String.format(winningMessageFormat, "Dealer");
        } else if (sumOfDealerHand > 21
                || sumOfPlayerHand == 21
                || sumOfPlayerHand > sumOfDealerHand) {
            message = String.format(winningMessageFormat, "Player");
        } else {
            message = "It\'s a tie!";
        }

        System.out.println(message);
        return message;
    }

    private static void printResults(List<Card> playerHand, List<Card> dealerHand, String resultMessage) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("blackjack_log.txt");
            writer.println("Player\'s hand is: ");
            writer.println(showHand(playerHand));
            writer.println("Player\'s score is");
            writer.println(scoreHand(playerHand));
            writer.println("Dealer\'s hand is: ");
            writer.println(showHand(dealerHand));
            writer.println("Dealer\'s score is");
            writer.println(scoreHand(dealerHand));
            writer.println(resultMessage);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private static int scoreHand(List<Card> hand) {
        int sumOfHand = 0;
        for (Card card : hand) {
            int score = 0;
            if (card.value < 10) {
                score = card.value;
            } else {
                score = 10;
            }
            sumOfHand = sumOfHand + score;
        }
        return sumOfHand;
    }

    private static void removeCardFromDeck(Map<String, List<Integer>> deck, Card card) {
        List<Integer> cardsInSuit = deck.get(card.suit);
        cardsInSuit.remove(card.value);
    }

    private static String showHand(List<Card> playerHand) {
        StringBuilder handOutput = new StringBuilder();
        for (Card card : playerHand) {
            handOutput.append(printCard(card));
        }
        return handOutput.toString();
    }

    private static String printCard(Card card) {
        return String.format("%s - %d, ", card.suit, card.value);
    }

    private static void showErrorMessage() {
        System.out.println("Invalid input");
    }

}
