package ui;

import game.GameState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

import java.io.*;

public class Controller {
    
    public TextField game_save_path_entry;
    public Button game_load_button;
    public Button load_game_option_button;
    public Button new_game_option_button;
    public TextField game_save_path;
    public AnchorPane main_stage;

    File gameFile = null;
    FileOutputStream fileStreamOut = null;
    FileInputStream fileStreamIn = null;
    ObjectOutputStream gameStreamOut = null;
    ObjectInputStream gameStreamIn = null;

    GameState game = null;

    @FXML
    public void initialize() {


    }

    public void start_game(){

    }

    public void load_game_requested(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Blackjack Game");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Blackjack Game", "*.blkjk"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        gameFile = fileChooser.showOpenDialog(main_stage.getScene().getWindow());
        assert gameFile != null;
        fileStreamIn = new FileInputStream(gameFile.getAbsolutePath());
        gameStreamIn = new ObjectInputStream(fileStreamIn);
        game = (GameState) gameStreamIn.readObject();
    }

    public void new_game_requested(MouseEvent mouseEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create Blackjack Game");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Blackjack Game", "*.blkjk"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        gameFile = fileChooser.showSaveDialog(main_stage.getScene().getWindow());
        assert gameFile != null;
        fileStreamOut = new FileOutputStream(gameFile.getAbsolutePath());
    }
}
