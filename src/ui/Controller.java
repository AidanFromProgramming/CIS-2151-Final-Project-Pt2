package ui;

import game.GameState;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.LogRecord;

public class Controller extends Thread{
    
    public TextField game_save_path_entry;
    public Button game_load_button;
    public Button load_game_option_button;
    public Button new_game_option_button;
    public AnchorPane main_stage;
    public Text player_name_0;
    public Text player_name_1;
    public Text player_name_2;
    public Text player_name_3;
    public TextField player_namebox_1;
    public TextField player_namebox_2;
    public TextField player_namebox_3;
    public TextField player_namebox_4;
    public TabPane tab_plane;
    public Tab splash_tab;
    public Tab game_tab;
    public Button stand_button_0;
    public Button stand_button_1;
    public Button stand_button_2;
    public Button stand_button_3;
    public Button hit_button_0;
    public Button hit_button_1;
    public Button hit_button_2;
    public Button hit_button_3;
    public Button double_up_0;
    public Button double_up_1;
    public Button double_up_2;
    public Button double_up_3;
    public Button player_dont_0;
    public Button player_dont_1;
    public Button player_dont_2;
    public Button player_dont_3;
    public Text player_hand_0;
    public Text player_hand_1;
    public Text player_hand_2;
    public Text player_hand_3;
    public Text dealer_hand;
    public Text player_money_0;
    public Text player_money_1;
    public Text player_money_2;
    public Text player_money_3;
    public Text current_player_text;
    public Text current_pot_text;
    public Text card_value_0;
    public Text card_value_1;
    public Text card_value_2;
    public Text card_value_3;
    public Text current_hilo_text;

    File gameFile = null;
    FileOutputStream fileStreamOut = null;
    FileInputStream fileStreamIn = null;
    ObjectOutputStream gameStreamOut = null;
    ObjectInputStream gameStreamIn = null;

    GameState game = null;

    @FXML
    public void initialize() {
        BackgroundFill background_fill = new BackgroundFill(Paint.valueOf(Color.color(0.275,0.604, 0.196).toString()),
                CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        main_stage.setBackground(background);
        tab_plane.setBackground(background);
    }

    public void startGame() {
        // Configure player names
        player_name_0.setText(game.getPlayers().get(0).toString());
        player_name_1.setText(game.getPlayers().size() > 1 ? game.getPlayers().get(1).toString() : "");
        player_name_2.setText(game.getPlayers().size() > 2 ? game.getPlayers().get(2).toString() : "");
        player_name_3.setText(game.getPlayers().size() > 3 ? game.getPlayers().get(3).toString() : "");

        //Configure player buttons
        playerControlsConfig(stand_button_1, stand_button_2, stand_button_3);
        playerControlsConfig(hit_button_1, hit_button_2, hit_button_3);
        playerControlsConfig(double_up_1, double_up_2, double_up_3);
        playerControlsConfig(player_dont_1, player_dont_2, player_dont_3);
        this.start();
    }

    private void playerControlsConfig(Button button_1, Button button_2, Button button_3) {
        button_1.setVisible(game.getPlayers().size() > 1);
        button_2.setVisible(game.getPlayers().size() > 2);
        button_3.setVisible(game.getPlayers().size() > 3);
    }

    private void playerButtonEnables(Player player, Button hit, Button stand, Button doubleUp, Button notDoubleUp){
        if (player == null){
            return;
        }
        hit.setDisable(!(game.turn >= 0 && game.getPlayers().get(game.turn).name.equals(player.name)));
        stand.setDisable(!(game.turn >= 0 && game.getPlayers().get(game.turn).name.equals(player.name)));
        doubleUp.setDisable(!(game.turn == -1 && player.doubleUp == 0));
        notDoubleUp.setDisable(!(game.turn == -1 && player.doubleUp == 0));
    }

    @Override
    public void run() {
        tab_plane.getSelectionModel().select(game_tab);
        splash_tab.setDisable(true);
        game_tab.setDisable(false);

        int gameCycleCounter = 0;

        try {
            saveGame();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(game.isRunning()) {

            // Update Hands
            dealer_hand.setText(game.dealer.toString());
            player_hand_0.setText(game.getPlayers().get(0).hand.toString());
            player_hand_1.setText(game.getPlayers().size() > 1 ? game.getPlayers().get(1).hand.toString() : "");
            player_hand_2.setText(game.getPlayers().size() > 2 ? game.getPlayers().get(2).hand.toString() : "");
            player_hand_3.setText(game.getPlayers().size() > 3 ? game.getPlayers().get(3).hand.toString() : "");

            // Update money values
            player_money_0.setText("Money: " + game.getPlayers().get(0).money);
            player_money_1.setText(game.getPlayers().size() > 1 ? "Money: " + game.getPlayers().get(1).money : "");
            player_money_2.setText(game.getPlayers().size() > 2 ? "Money: " + game.getPlayers().get(2).money : "");
            player_money_3.setText(game.getPlayers().size() > 3 ? "Money: " + game.getPlayers().get(3).money : "");
            current_player_text.setText("Current Player: " + (game.turn >= 0 ? game.getPlayers().get(game.turn).name : "Choose double up"));
            current_pot_text.setText("Current Pot: " + game.potValue);
            current_hilo_text.setText("Current Hi-Lo Value: " + game.deck.hiLoValue);

            //Update card values
            String formatString = "Card Values: %1s/21";
            card_value_0.setText(game.getPlayers().size() > 0 ? String.format(formatString, game.getPlayers().get(0).hand.value()) : "");
            card_value_1.setText(game.getPlayers().size() > 1 ? String.format(formatString, game.getPlayers().get(1).hand.value()) : "");
            card_value_2.setText(game.getPlayers().size() > 2 ? String.format(formatString, game.getPlayers().get(2).hand.value()) : "");
            card_value_3.setText(game.getPlayers().size() > 3 ? String.format(formatString, game.getPlayers().get(3).hand.value()) : "");

            //Update buttons
            playerButtonEnables(game.getPlayers().get(0), hit_button_0, stand_button_0, double_up_0, player_dont_0);
            playerButtonEnables(game.getPlayers().size() > 1 ? game.getPlayers().get(1) : null, hit_button_1, stand_button_1, double_up_1, player_dont_1);
            playerButtonEnables(game.getPlayers().size() > 2 ? game.getPlayers().get(2) : null, hit_button_2, stand_button_2, double_up_2, player_dont_2);
            playerButtonEnables(game.getPlayers().size() > 3 ? game.getPlayers().get(3) : null, hit_button_3, stand_button_3, double_up_3, player_dont_3);

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(gameCycleCounter > 30 * 4){  // Run autosave every 30 seconds
                try {
                    saveGame();
                    gameCycleCounter = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            gameCycleCounter++;
        }
    }

    private void unlockCreateGame(){
        player_namebox_1.setVisible(true);
        player_namebox_2.setVisible(true);
        player_namebox_3.setVisible(true);
        player_namebox_4.setVisible(true);
        game_load_button.setVisible(true);
        load_game_option_button.setVisible(false);
        new_game_option_button.setVisible(false);
    }

    public void saveGame() throws IOException {
        System.out.println("Saving game....");
        fileStreamOut = new FileOutputStream(gameFile.getAbsolutePath());
        gameStreamOut = new ObjectOutputStream(fileStreamOut);
        gameStreamOut.writeObject(game);
        gameStreamOut.close();
        fileStreamOut.close();
        System.out.println("Game saved");
    }

    public void create_game() {
        ArrayList<String> players = new ArrayList<String>();
        if(!player_namebox_1.getText().equals("")) players.add(0, player_namebox_1.getText());
        if(!player_namebox_2.getText().equals("")) players.add(1, player_namebox_2.getText());
        if(!player_namebox_3.getText().equals("")) players.add(2, player_namebox_3.getText());
        if(!player_namebox_4.getText().equals("")) players.add(3, player_namebox_4.getText());
        game = new GameState(players.toArray(new String[0]));
        game.startNewRound();
        startGame();
    }

    public void load_game_requested() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Blackjack Game");
        fileChooser.setInitialFileName("jackblack");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Blackjack Game", "*.blkjk"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        gameFile = fileChooser.showOpenDialog(main_stage.getScene().getWindow());
        if(gameFile == null) {displayError("No game file selected"); return;} // Exit if no game file selected
        fileStreamIn = new FileInputStream(gameFile.getAbsolutePath());
        try {
            gameStreamIn = new ObjectInputStream(fileStreamIn);
        } catch (StreamCorruptedException e){
            displayError("Invalid Save File Selected!");
            return;
        }catch (EOFException e){
            displayError(String.format("Save File Corrupted! (%1s)", e));
            return;
        }
        try {
            game = (GameState) gameStreamIn.readObject();
        }catch (InvalidClassException e){
            displayError(String.format("Outdated Save File Selected! (%1s)", e));
            return;
        }
        startGame();
    }

    public void new_game_requested() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create Blackjack Game");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Blackjack Game", "*.blkjk"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        gameFile = fileChooser.showSaveDialog(main_stage.getScene().getWindow());
        if(gameFile == null) {displayError("No game file selected"); return;} // Exit if no game file selected
        fileStreamOut = new FileOutputStream(gameFile.getAbsolutePath());
        unlockCreateGame();
    }

    //Button press passthroughs handlers

    public void on_double_up_0() {
        game.doubleUpPressed(0);
    }

    public void on_double_up_1() {
        game.doubleUpPressed(1);
    }

    public void on_double_up_2() {
        game.doubleUpPressed(2);
    }

    public void on_double_up_3() {
        game.doubleUpPressed(3);
    }

    public void on_player_stand_pressed_0() {
        game.standPressed(0);
    }

    public void on_player_stand_pressed_1() {
        game.standPressed(1);
    }

    public void on_player_stand_pressed_2() {
        game.standPressed(2);
    }

    public void on_player_stand_pressed_3() {
        game.standPressed(3);
    }

    public void player_hit_pressed_0() {
        game.hitPressed(0);
    }

    public void player_hit_pressed_1() {
        game.hitPressed(1);
    }

    public void player_hit_pressed_2() {
        game.hitPressed(2);
    }

    public void player_hit_pressed_3() {
        game.hitPressed(3);
    }

    public void player_dont_pressed_0() {
        game.dontDoubleUpPressed(0);
    }

    public void player_dont_pressed_1() {
        game.dontDoubleUpPressed(1);
    }

    public void player_dont_pressed_2() {
        game.dontDoubleUpPressed(2);
    }

    public void player_dont_pressed_3() {
        game.dontDoubleUpPressed(3);
    }

    public static class GlobalExceptionHandler {

        public static void main(String[] args) {

            Handler globalExceptionHandler = new Handler();
            Thread.setDefaultUncaughtExceptionHandler(globalExceptionHandler);
            new GlobalExceptionHandler().performArithmeticOperation(10, 0);
        }

        public int performArithmeticOperation(int num1, int num2) {
            return num1/num2;
        }
    }

    // https://www.baeldung.com/java-global-exception-handler
    static class Handler extends java.util.logging.Handler implements Thread.UncaughtExceptionHandler {

        public void uncaughtException(Thread t, Throwable e) {
            displayError(String.format("Uncaught Exception!\n%1s", e));
        }

        @Override
        public void publish(LogRecord record) {

        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    }

    //Error dialog
    private static void displayError(String error){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Error!");
        dialog.setResizable(true);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.setContentText(String.format("Error: %1s", error));
        dialog.showAndWait();
    }

}
