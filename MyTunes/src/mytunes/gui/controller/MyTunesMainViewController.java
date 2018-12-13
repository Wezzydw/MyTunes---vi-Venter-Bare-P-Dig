/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 * FXML Controller class
 *
 * @author Wezzy Laptop
 */
public class MyTunesMainViewController implements Initializable
{
    @FXML
    private Slider sliderVol;
    @FXML
    private ListView<Playlist> listViewLibrary;
    @FXML
    private ListView<String> listViewSongInfo;
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
    @FXML
    private ListView<String> listNowPlaying;
    @FXML
    private Slider sliderPlayback;
    
    Model model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            model = new Model();
        } catch (IOException ex)
        {
            Logger.getLogger(MyTunesMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listViewAllSongs.setItems(model.getSongs());
        listViewQueue.setItems(model.getQuedSongs());
        listViewLibrary.setItems(model.getPlayLists());
        listNowPlaying.setItems(model.getNowPlaying());
        listViewSongInfo.setItems(model.getSongName());

        listViewAllSongs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewQueue.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        sliderVol.setValue(model.getSliderVolumeFromDB() * 100);
        model.changeVolume(model.getSliderVolumeFromDB());
        model.sendSliderForPlayback(sliderPlayback);
    }

    @FXML
    private void onHandleSliderVol(MouseEvent event)
    {
        model.changeVolume(sliderVol.getValue());
    }

    private void onProgramClose()
    {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        model.onProgramClose(stage);
    }

    @FXML
    private void onHandleShuffe(ActionEvent event)
    {
        model.shuffleHandler();
    }

    @FXML
    private void onHandleRepeat(ActionEvent event)
    {
        model.repeatHandler();
    }

    @FXML
    private void onHandlePrev(ActionEvent event)
    {
        model.playPrevSong();
    }

    @FXML
    private void onHandlePlay(ActionEvent event)
    {

        model.playSong();
    }

    @FXML
    private void onHandlePause(ActionEvent event)
    {
        model.pauseSong();
    }

    @FXML
    private void onHandleNext(ActionEvent event)
    {
        model.playNextSong();
    }

    @FXML
    private void onHandleAdd(ActionEvent event) throws IOException
    {
        model.addSongToQue(listViewAllSongs.getSelectionModel().getSelectedItems());
        listNowPlaying.setItems(model.getSongName());
        listViewSongInfo.setItems(model.getNowPlaying());
    }

    @FXML
    private void onHandleRemove(ActionEvent event) throws IOException
    {
        model.removeSongsFromQue(listViewQueue.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void onHandleMisc(ActionEvent event)
    {
        comboBoxMisc.setItems(FXCollections.observableArrayList("reverseList", "randomiseList", "sortByTitle"));
        comboBoxMisc.setVisibleRowCount(3);
    }

    @FXML
    private void onHandleSearch(KeyEvent event) throws IOException
    {
        model.searcher(txtFieldSearch.getText());
    }

    @FXML
    private void onHandleAddFolder(ActionEvent event) throws IOException, InterruptedException
    {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        model.SelectFolder(stage);

    }

    @FXML
    private void onSongRemove(ActionEvent event)
    {
        try
        {
            model.removeSongs(listViewAllSongs.getSelectionModel().getSelectedItems(), listViewLibrary.getSelectionModel().getSelectedItem());
        } catch (IOException ex)
        {
            System.out.println("fejl 115");
        }
    }

    @FXML
    private void onHandleSongEdit(ActionEvent event) throws IOException
    {
        model.songEdit(listViewAllSongs.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onHandlePlaylistEdit(ActionEvent event) throws IOException
    {
        model.playlistEdit(listViewLibrary.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onHandlePlaylistRemove(ActionEvent event) throws IOException, SQLException
    {
        model.removePlaylist(listViewLibrary.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onHandlePlaylistAdd(ActionEvent event)
    {
        model.addToPlaylist(listViewLibrary.getSelectionModel().getSelectedItem(), listViewAllSongs.getSelectionModel().getSelectedItems());
        model.addPlaylistToDB(listViewAllSongs.getSelectionModel().getSelectedItems(), listViewLibrary.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void queueLookForDeleteKey(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.DELETE))
        {
            model.removeSongsFromQue(listViewQueue.getSelectionModel().getSelectedItems());
        }
    }

    @FXML
    private void PlaylistsMouseClick(MouseEvent event)
    {
        model.playlistClicks(listViewLibrary.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void queueMouseClick(MouseEvent event)
    {
        model.queueClicks(listViewQueue.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void allSongsMouseClicked(MouseEvent event)
    {
        model.songListClicks(listViewAllSongs.getSelectionModel().getSelectedItem());
        listNowPlaying.setItems(model.getSongName());
        listViewSongInfo.setItems(model.getNowPlaying());
    }

    @FXML
    private void onHandlePlaylistCreate(ActionEvent event)
    {
        model.tryCreatePlaylist();
    }

    @FXML
    private void playlistLookForDeleteKey(KeyEvent event) throws IOException, SQLException
    {
        if (event.getCode().equals(KeyCode.DELETE))
        {
            model.removePlaylist(listViewLibrary.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void allSongsLookForDeleteKey(KeyEvent event) throws IOException
    {
        if (event.getCode().equals(KeyCode.DELETE))
        {
            model.removeSongs(listViewAllSongs.getSelectionModel().getSelectedItems(), listViewLibrary.getSelectionModel().getSelectedItem());
        }
    }

}
