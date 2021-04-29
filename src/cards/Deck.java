package cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Deck implements Serializable {
    //Fields
    private final List<Card> cards;
    private final int size; //NOTE: Size means how many decks there are in this deck. Simply the maximum.
    public int hiLoValue;

    //Constructor
    public Deck(int size) {
        this.size = size;
        cards = new ArrayList<>();
        hiLoValue = 0;
    }

    //For every pace in the deck, that card is swapped with another random card in the deck
    //For multiple decks, it does the same, but never swaps cards with a different deck
    public void shuffle() {
        //Unfortunately, a dummy variable is needed to swap two cards
        //(yes I know I technically can make a method for swapping but that's more effort than it's worth)
        Card cardDummy;
        int selectedCardIndex;
        hiLoValue = 0;

        if (size == 1) {
            for (int currentCardIndex = 0; currentCardIndex < 52; currentCardIndex++) {
                //Selecting a card to swap with
                selectedCardIndex = (int) Math.round(Math.random()*52);

                //Swapping the cards
                cardDummy = cards.get(currentCardIndex);
                cards.set(currentCardIndex, cards.get(selectedCardIndex));
                cards.set(selectedCardIndex, cardDummy);
            }
        } else {
            int startingIndex = 0;
            for (int deck = 0; deck < size; deck++) {
                for (int currentCardIndex = startingIndex; currentCardIndex < 52; currentCardIndex++) {
                    //Selecting a card to swap with
                    selectedCardIndex = (int) Math.round(Math.random()*52) + startingIndex;

                    //Swapping the cards
                    cardDummy = cards.get(currentCardIndex);
                    cards.set(currentCardIndex, cards.get(selectedCardIndex));
                    cards.set(selectedCardIndex, cardDummy);
                }
                startingIndex += 52;
            }
        }
    }

    //Generates each card in order, decks first, then suits, then faces
    public void generateCards() {
        cards.clear();
        for (int decks = 0; decks < this.size; decks++) {
            for (int suit = 0; suit < 4; suit++) {
                for (int face = 0; face < 12; face++) {
                    cards.add(new Card(Card.getCardFace(face), Card.getCardSuit(suit)));
                }
            }
        }
    }

    //Simply generates and shuffles
    public void genAndShuffle() {
        generateCards();
        shuffle();
    }

    //Getters + setters
    public List<Card> getCards() {
        return cards;
    }
}
