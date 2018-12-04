/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.Player;

/**
 * FXML Controller class
 *
 * @author Wezzy Laptop
 */
public class MyTunesMainViewController implements Initializable {

    @FXML
    private Slider sliderVol;
    @FXML
    private ListView<Playlist> listViewLibrary;
    @FXML
    private ListView<Song> listViewSongInfo;
    @FXML
    private ListView<Song> listViewQueue;
    @FXML
    private ComboBox<String> comboBoxMisc;
    @FXML
    private TextField txtFieldSearch;
    @FXML
    private ListView<Song> listViewAllSongs;
    @FXML
    private BorderPane borderPane;

    Model model;
    @FXML
    private ListView<?> listNowPlaying;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new Model();
    }

    @FXML
    private void onHandleSliderVol(MouseEvent event) {
        model.changeVolume(sliderVol.getValue());
    }

    @FXML
    private void onHandleShuffe(ActionEvent event) {
        model.shuffleHandler();
    }

    @FXML
    private void onHandleRepeat(ActionEvent event) {
        model.repeatHandler();
    }

    @FXML
    private void onHandlePrev(ActionEvent event) {
        model.playPrevSong();
    }

    @FXML
    private void onHandlePlay(ActionEvent event) {

        model.playSong();
    }

    @FXML
    private void onHandlePause(ActionEvent event) {
        model.pauseSong();
    }

    @FXML
    private void onHandleNext(ActionEvent event) {
        model.playNextSong();
    }

    @FXML
    private void onHandleAdd(ActionEvent event) throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        model.SelectFolder(stage);
    }

    @FXML
    private void onHandleRemove(ActionEvent event) {

    }

    @FXML
    private void onHandleMisc(ActionEvent event) {
        comboBoxMisc.setItems(FXCollections.observableArrayList("reverseList", "randomiseList", "sortByTitle"));
        comboBoxMisc.setVisibleRowCount(3);
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

    @FXML
    private void HandleDragDone(DragEvent event) {
        //event.getDragboard().getFiles().clear();
    }

}
