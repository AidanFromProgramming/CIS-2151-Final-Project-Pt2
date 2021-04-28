package cards;

import cards.enums.cardFace;
import cards.enums.cardSuit;

import java.io.Serializable;

public class Card implements Serializable {
    //Fields
    private final cardFace face;
    private final cardSuit suit;

    //Constructor
    public Card(cardFace face, cardSuit suit) {
        this.face = face;
        this.suit = suit;
    }

    //Methods to reverse the .ordinal function of enums (why doesn't it exist already)
    public static cardFace getCardFace(int ordinal) {
        switch (ordinal) {
            case 0 -> {
                return cardFace.two;
            }
            case 1 -> {
                return cardFace.three;
            }
            case 2 -> {
                return cardFace.four;
            }
            case 3 -> {
                return cardFace.five;
            }
            case 4 -> {
                return cardFace.six;
            }
            case 5 -> {
                return cardFace.seven;
            }
            case 6 -> {
                return cardFace.eight;
            }
            case 7 -> {
                return cardFace.nine;
            }
            case 8 -> {
                return cardFace.jack;
            }
            case 9 -> {
                return cardFace.queen;
            }
            case 10 -> {
                return cardFace.king;
            }
            case 11 -> {
                return cardFace.ace;
            }
        }
        return null;
    }

    public static cardSuit getCardSuit(int ordinal) {
        switch (ordinal) {
            case 0 -> {
                return cardSuit.clubs;
            }
            case 1 -> {
                return cardSuit.diamonds;
            }
            case 2 -> {
                return cardSuit.hearts;
            }
            case 3 -> {
                return cardSuit.spades;
            }
        }
        return null;
    }

    //Getters
    public cardFace getFace() {
        return face;
    }

    public cardSuit getSuit() {
        return suit;
    }
}
