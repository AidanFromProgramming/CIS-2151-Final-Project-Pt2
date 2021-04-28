package game;

import cards.Deck;
import exceptions.player.NotEnoughMoneyException;
import player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState extends Thread implements Serializable {
    //Fields
    public int potValue;

    private int turn;
    private List<Player> players;
    private Deck deck;
    private boolean running;
    private int initialBet;

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
        turn = 0;
        potValue = 0;
        initialBet = 1;
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

    public void hitPressed(int playerNumber) {

    }

    public void standPressed(int playerNumber) {

    }

    public void doubleUpPressed(int playerNumber) {
        if (turn == 0) {
            players.get(playerNumber).doubleUp = true;
            initialBet += initialBet;
        }
        boolean allPlayersSelected = true;
        for (Player player : players) {

        }
    }

    public void dontDoubleUpPressed(int playerNumber) {

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

    public boolean isRunning() {
        return running;
    }

    /*
    while (true) {
            if (turn == 0) {

            } else {
                switch (turn % players.size()) {
                    case 0 -> {
                        //TODO: ASK PLAYER 1 TO HIT OR STAND AND EXECUTE PROPERLY
                    }
                    case 1 -> {
                        //TODO: ASK PLAYER 2 TO HIT OR STAND AND EXECUTE PROPERLY
                    }
                    case 2 -> {
                        //TODO: ASK PLAYER 3 TO HIT OR STAND AND EXECUTE PROPERLY
                    }
                    case 3 -> {
                        //TODO: ASK PLAYER 4 TO HIT OR STAND AND EXECUTE PROPERLY
                    }
                }
                //TODO: IF THERE IS NO PLAYER LEFT STANDING, DIVVY THE POT TO THE WINNERS
            }
        }
     */
}
