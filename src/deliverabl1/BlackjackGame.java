package deliverabl1;

import java.util.Scanner;

class BlackjackGame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private Scanner scanner;

    public BlackjackGame() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
        scanner = new Scanner(System.in);
    }

    public void playGame() {
        deck.shuffle();
        dealInitialCards();

        while (true) {
            System.out.println("Player's hand:");
            System.out.println(playerHand);
            System.out.println("Player's hand value: " + playerHand.getHandValue());

            if (playerHand.getHandValue() > 21) {
                System.out.println("Player busts! You lose.");
                break;
            }
            System.out.println("Do you want to hit or stand? (h/s)");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("h")) {
                hitPlayer();
            } else if (choice.equalsIgnoreCase("s")) {
                playDealerTurn();
                break;
            } else {
                System.out.println("Invalid choice. Please enter 'h' to hit or 's' to stand.");
            }
        }

        System.out.println("Player's hand:");
        System.out.println(playerHand);
        System.out.println("Player's hand value: " + playerHand.getHandValue());
        System.out.println("Dealer's hand:");
        System.out.println(dealerHand);
        System.out.println("Dealer's hand value: " + dealerHand.getHandValue());

        if (playerHand.getHandValue() <= 21 && dealerHand.getHandValue() <= 21) {
            if (playerHand.getHandValue() > dealerHand.getHandValue()) {
                System.out.println("Player wins!");
            } else if (playerHand.getHandValue() < dealerHand.getHandValue()) {
                System.out.println("Dealer wins!");
            } else {
                System.out.println("It's a tie!");
            }
        } else if (playerHand.getHandValue() <= 21 && dealerHand.getHandValue() > 21) {
            System.out.println("Dealer busts! Player wins!");
        } else if (playerHand.getHandValue() > 21 && dealerHand.getHandValue() <= 21) {
            System.out.println("Player busts! Dealer wins!");
        } else {
            System.out.println("Both player and dealer bust. It's a tie!");
        }
    }

    private void dealInitialCards() {
        Card playerCard1 = deck.drawCard();
        Card dealerCard1 = deck.drawCard();
        Card playerCard2 = deck.drawCard();
        Card dealerCard2 = deck.drawCard();

        playerHand.addCard(playerCard1);
        playerHand.addCard(playerCard2);
        dealerHand.addCard(dealerCard1);
        dealerHand.addCard(dealerCard2);

        System.out.println("Player's hand:");
        System.out.println(playerHand);
        System.out.println("Player's hand value: " + playerHand.getHandValue());
        System.out.println("Dealer's hand:");
        System.out.println(dealerCard1);
        System.out.println("Unknown Card");
    }

    private void hitPlayer() {
        Card card = deck.drawCard();
        if (card != null) {
            playerHand.addCard(card);
            System.out.println("Player draws: " + card);
        } else {
            System.out.println("No more cards in the deck!");
        }
    }

    private void playDealerTurn() {
        System.out.println("Dealer's turn:");
        System.out.println("Dealer's hand:");
        System.out.println(dealerHand);
        while (dealerHand.getHandValue() < 17) {
            Card card = deck.drawCard();
            if (card != null) {
                dealerHand.addCard(card);
                System.out.println("Dealer draws: " + card);
            } else {
                System.out.println("No more cards in the deck!");
                break;
            }
        }

        System.out.println("Dealer's hand value: " + dealerHand.getHandValue());
    }
}
