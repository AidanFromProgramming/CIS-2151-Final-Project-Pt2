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
                return cardFace.Two;
            }
            case 1 -> {
                return cardFace.Three;
            }
            case 2 -> {
                return cardFace.Four;
            }
            case 3 -> {
                return cardFace.Five;
            }
            case 4 -> {
                return cardFace.Six;
            }
            case 5 -> {
                return cardFace.Seven;
            }
            case 6 -> {
                return cardFace.Eight;
            }
            case 7 -> {
                return cardFace.Nine;
            }
            case 8 -> {
                return cardFace.Jack;
            }
            case 9 -> {
                return cardFace.Queen;
            }
            case 10 -> {
                return cardFace.King;
            }
            case 11 -> {
                return cardFace.Ace;
            }
        }
        return null;
    }

    public static cardSuit getCardSuit(int ordinal) {
        switch (ordinal) {
            case 0 -> {
                return cardSuit.Clubs;
            }
            case 1 -> {
                return cardSuit.Diamonds;
            }
            case 2 -> {
                return cardSuit.Hearts;
            }
            case 3 -> {
                return cardSuit.Spades;
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
