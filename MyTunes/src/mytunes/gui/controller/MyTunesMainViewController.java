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
import javafx.scene.media.MediaView;
import mytunes.bll.Player;

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
    private MediaView mediaView;

    Player p;
    int i;
    @FXML
    private ListView<?> listNowPlaying;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        p = new Player();
        i = 0;
        //mediaView.setMediaPlayer(p.getMediaPlayer());
        
    }

    @FXML
    private void onHandleSliderVol(MouseEvent event)
    {
        double vol = sliderVol.getValue();
        System.out.println(vol);
        p.changevolume(vol / 100);
    }

    @FXML
    private void onHandleShuffe(ActionEvent event)
    {
        p.shuffleHandler();
    }

    @FXML
    private void onHandleRepeat(ActionEvent event)
    {
        p.repeatHandler();
    }

    @FXML
    private void onHandlePrev(ActionEvent event)
    {
        p.playPrevSong();
    }

    @FXML
    private void onHandlePlay(ActionEvent event)
    {

        p.playSong();
    }

    @FXML
    private void onHandlePause(ActionEvent event)
    {
        p.pauseSong();
    }

    @FXML
    private void onHandleNext(ActionEvent event)
    {
        p.playNextSong();
    }

    @FXML
    private void onHandleAdd(ActionEvent event)
    {
    }

    @FXML
    private void onHandleRemove(ActionEvent event)
    {
    }

    @FXML
    private void onHandleMisc(ActionEvent event)
    {
    }

    @FXML
    private void onHandleSearch(KeyEvent event)
    {
    }

    @FXML
    private void onHandlePlaylistEdit(ActionEvent event)
    {
    }

    @FXML
    private void onHandlePlaylistAdd(ActionEvent event)
    {
    }

    @FXML
    private void onHandlePlaylistRemove(ActionEvent event)
    {
    }

}
