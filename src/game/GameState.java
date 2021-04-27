package game;

import cards.Deck;
import exceptions.player.NotEnoughMoneyException;
import player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState extends Thread implements Serializable {
    //Fields
    private List<Player> players;
    private Deck deck;
    private int playerTurn;
    public int potValue;

    //Constructor
    public GameState(String... playerNames) {
        //Setting up the deck
        deck = new Deck(6);
        deck.genAndShuffle();

        //Setting up players
        players = new ArrayList<>();
        int numberOfPlayers = playerNames.length;
        int iteration = 0;
        for (int player = 0; player < numberOfPlayers; player++) {
            players.add(new Player(playerNames[iteration]));
            iteration++;
        }

        //Starting Values
        playerTurn = 0;
        potValue = 0;
    }

    //Methods
    @Override
    public void run() {
        if (playerTurn % players.size() == 0) {
            //This means that the current turn is an initial turn, which means that we will ask if they want to double down or not
            playerTurn = 0;

        }
    }

    private void blindBet(int amount) {
        for (Player player : players) {
            try {
                player.bet(this, amount);
            } catch (NotEnoughMoneyException e) {
                player.bankrupt = true;
            }
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
