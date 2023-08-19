package deliverabl1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

abstract class Card {
    abstract int getValue();
}

class NumberCard extends Card {
    private int value;

    public NumberCard(int value) {
        this.value = value;
    }

    @Override
    int getValue() {
        return value;
    }
}

class FaceCard extends Card {
    private int value = 10;

    @Override
    int getValue() {
        return value;
    }
}

class AceCard extends Card {
    private int value = 11;

    @Override
    int getValue() {
        return value;
    }
}

class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            cards.add(new NumberCard(i));
        }
        for (int i = 0; i < 4; i++) {
            cards.add(new FaceCard());
        }
        for (int i = 0; i < 4; i++) {
            cards.add(new AceCard());
        }
    }

    public Card drawCard() {
        Random rand = new Random();
        int index = rand.nextInt(cards.size());
        return cards.remove(index);
    }
}

abstract class Player {
    private List<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void drawCard(Deck deck) {
        Card card = deck.drawCard();
        hand.add(card);
    }

    public int calculateScore() {
        int score = 0;
        int numAces = 0;

        for (Card card : hand) {
            score += card.getValue();
            if (card instanceof AceCard) {
                numAces++;
            }
        }

        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }

    public void showHand() {
        System.out.print("Hand: ");
        for (Card card : hand) {
            System.out.print(card.getValue() + " ");
        }
        System.out.println("(" + calculateScore() + " points)");
    }

    abstract boolean wantsToHit(Scanner scanner);
}

class HumanPlayer extends Player {
    @Override
    boolean wantsToHit(Scanner scanner) {
        System.out.print("Do you want to hit? (y/n): ");
        String response = scanner.next();
        return response.equalsIgnoreCase("y");
    }
}

class ComputerPlayer extends Player {
    @Override
    boolean wantsToHit(Scanner scanner) {
        return calculateScore() < 17;
    }
}

public class BlackJackGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        HumanPlayer player = new HumanPlayer();
        ComputerPlayer dealer = new ComputerPlayer();

        player.drawCard(deck);
        player.drawCard(deck);
        dealer.drawCard(deck);
        dealer.drawCard(deck);

        while (player.wantsToHit(scanner)) {
            player.drawCard(deck);
            player.showHand();
            if (player.calculateScore() > 21) {
                System.out.println("Bust! You lose.");
                return;
            }
        }

        while (dealer.wantsToHit(scanner)) {
            dealer.drawCard(deck);
        }

        player.showHand();
        dealer.showHand();

        int playerScore = player.calculateScore();
        int dealerScore = dealer.calculateScore();

        if (dealerScore > 21 || playerScore > dealerScore) {
            System.out.println("You win!");
        } else if (playerScore < dealerScore) {
            System.out.println("You lose.");
        } else {
            System.out.println("It's a tie!");
        }
    }
}

