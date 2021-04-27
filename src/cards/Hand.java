package cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand implements Serializable {
    //Fields
    private List<Card> cards;

    //Constructor
    public Hand() {
        cards = new ArrayList<>();
    }

    //Methods
    public void drawCard(Deck deck) {
        this.cards.add(deck.getCards().get(0));
        deck.getCards().remove(0);
    }

    //Getters and Setters
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
