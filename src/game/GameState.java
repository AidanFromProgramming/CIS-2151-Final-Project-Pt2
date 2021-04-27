package game;

import cards.Deck;
import exceptions.player.notEnoughMoneyException;
import player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState extends Thread implements Serializable {
    //Fields
    private List<Player> players;
    private Deck deck;
    public int playerTurn;
    public int potValue;

    //Constructor
    public GameState(int numberOfPlayers, String... playerNames) {
        players = new ArrayList<>();
        deck = new Deck(6);

        deck.genAndShuffle();

        int iteration = 0;
        for (int player = 0; player < numberOfPlayers; player++) {
            players.add(new Player(playerNames[iteration]));
            iteration++;
        }
    }

    //Methods
    @Override
    public void run() {

    }

    private void blindBet(int amount) throws notEnoughMoneyException {
        for (Player player : players) {
            player.bet(this, amount);
        }
    }

    //Getters and setters
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
