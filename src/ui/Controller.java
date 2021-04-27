package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller {
    
    public TextField game_save_path_entry;
    public Button game_load_button;
    public Button load_game_option_button;
    public Button new_game_option_button;
    public TextField game_save_path;


    @FXML
    public void initialize() {
    }


    public void load_game_requested(MouseEvent mouseEvent) {
    }

    public void new_game_requested(MouseEvent mouseEvent) {
    }
}
