/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Wezzy Laptop
 */
public class MyTunesMainViewController implements Initializable {

    @FXML
    private TextArea txtTopleft;
    @FXML
    private Slider sliderVol;
    @FXML
    private ListView<?> listViewLibrary;
    @FXML
    private ListView<?> listViewSongInfo;
    @FXML
    private ListView<?> listViewQueue;
    @FXML
    private ComboBox<?> comboBoxMisc;
    @FXML
    private TextField txtFieldSearch;
    @FXML
    private ListView<?> listViewAllSongs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onHandleSliderVol(MouseEvent event) {
        System.out.println("VOLUME!");
    }

    @FXML
    private void onHandleShuffe(ActionEvent event) {
    }

    @FXML
    private void onHandleRepeat(ActionEvent event) {
    }

    @FXML
    private void onHandlePrev(ActionEvent event) {
    }

    @FXML
    private void onHandlePlay(ActionEvent event) {
    }

    @FXML
    private void onHandlePause(ActionEvent event) {
    }

    @FXML
    private void onHandleNext(ActionEvent event) {
    }

    @FXML
    private void onHandleAdd(ActionEvent event) {
    }

    @FXML
    private void onHandleRemove(ActionEvent event) {
    }

    @FXML
    private void onHandleMisc(ActionEvent event) {
    }

    @FXML
    private void onHandleSearch(KeyEvent event) {
    }

    @FXML
    private void onHandlePlaylistEdit(ActionEvent event) {
    }

    @FXML
    private void onHandlePlaylistAdd(ActionEvent event) {
    }

    @FXML
    private void onHandlePlaylistRemove(ActionEvent event) {
    }
    
}
