package cards;

import cards.enums.cardFace;
import cards.enums.cardSuit;

import java.io.Serializable;
import java.util.*;

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
        this.cards.add(deck.getCards().remove(0));
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
            assert card != null;
            stringBuilder.append(Objects.requireNonNull(card).getFace().toString());
            stringBuilder.append(" of ");
            stringBuilder.append(card.getSuit().toString());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public void sort() {
        cards.sort(Comparator.comparingInt(o -> o.getFace().ordinal()));
    }

    public void clear() {
        cards.clear();
    }
}
