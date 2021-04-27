package ui;

import game.GameState;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.*;
import player.Player;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Controller {
    
    public TextField game_save_path_entry;
    public Button game_load_button;
    public Button load_game_option_button;
    public Button new_game_option_button;
    public AnchorPane main_stage;
    public GridPane game_grid_plane;
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
    public Button hit_button_0;
    public Button stand_button_1;
    public Button hit_button_1;
    public Button stand_button_2;
    public Button hit_button_2;
    public Button stand_button_3;
    public Button hit_button_3;

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

    public void start_game(){
        game.start();
        tab_plane.getSelectionModel().select(game_tab);
        splash_tab.setDisable(true);

        // Configure player names
        player_name_0.setText(game.getPlayers().get(0).toString());
        player_name_1.setText(game.getPlayers().size() > 1 ? game.getPlayers().get(1).toString() : "");
        player_name_2.setText(game.getPlayers().size() > 2 ? game.getPlayers().get(2).toString() : "");
        player_name_3.setText(game.getPlayers().size() > 3 ? game.getPlayers().get(3).toString() : "");

        // Configure hands from cold start
        

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

    public void create_game(MouseEvent mouseEvent) {
        ArrayList<String> players = new ArrayList<String>();
        if(!player_namebox_1.getText().equals("")) players.add(0, player_namebox_1.getText());
        if(!player_namebox_2.getText().equals("")) players.add(1, player_namebox_1.getText());
        if(!player_namebox_3.getText().equals("")) players.add(2, player_namebox_1.getText());
        if(!player_namebox_4.getText().equals("")) players.add(3, player_namebox_1.getText());
        game = new GameState(players.toArray(new String[0]));
        start_game();
    }

    public void load_game_requested(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Blackjack Game");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Blackjack Game", "*.blkjk"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        gameFile = fileChooser.showOpenDialog(main_stage.getScene().getWindow());
        assert gameFile != null : "No game file selected";
        fileStreamIn = new FileInputStream(gameFile.getAbsolutePath());
        gameStreamIn = new ObjectInputStream(fileStreamIn);
        game = (GameState) gameStreamIn.readObject();
        start_game();
    }

    public void new_game_requested(MouseEvent mouseEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create Blackjack Game");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Blackjack Game", "*.blkjk"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        gameFile = fileChooser.showSaveDialog(main_stage.getScene().getWindow());
        assert gameFile != null : "No game file selected";
        fileStreamOut = new FileOutputStream(gameFile.getAbsolutePath());
        unlockCreateGame();
    }
}
