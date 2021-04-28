package cards;

import cards.enums.cardFace;
import cards.enums.cardSuit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Hand implements Serializable {
    //Fields
    private final List<Card> cards;
    private final StringBuilder display = new StringBuilder();

    //Constructor
    public Hand() {
        cards = new ArrayList<>();
    }

    //Methods
    public void drawCard(Deck deck) {
        this.cards.add(deck.getCards().get(0));
        deck.getCards().remove(0);

        sortHand();
    }

    public int calculateHandValue() {
        int handValue = 0;
        for (Card card : cards) {
            switch (card.getFace().ordinal()) {
                case 0 -> handValue += 2;
                case 1 -> handValue += 3;
                case 2 -> handValue += 4;
                case 3 -> handValue += 5;
                case 4 -> handValue += 6;
                case 5 -> handValue += 7;
                case 6 -> handValue += 8;
                case 7 -> handValue += 9;
                case 8, 9, 10 -> handValue += 10;
                case 11 -> {
                    if (handValue + 11 > 21) {
                        handValue++;
                    } else {
                        handValue += 11;
                    }
                }
            }
        }
        return handValue;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Card card : cards) {
            stringBuilder.append(card.getFace().toString());
            stringBuilder.append(" of ");
            stringBuilder.append(card.getSuit().toString());
            stringBuilder.append(", ");
        }

        return stringBuilder.toString();
    }

    private void sortHand() {
        cards.sort(Comparator.comparing(Card::getFace));
        cards.sort(Comparator.comparing(Card::getSuit));
    }

    //Getters and Setters
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
