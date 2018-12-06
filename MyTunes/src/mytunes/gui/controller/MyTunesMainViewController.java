/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.Player;
import mytunes.dal.SongDAO;

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
    

    Model model;
    @FXML
    private ListView<String> listNowPlaying;
    @FXML
    private Button btnRemoveSongQue;
    @FXML
    private Button btnRemoveSong;
    @FXML
    private MediaView mvMediaView;
    
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            model = new Model();
        } catch (IOException ex) {
            Logger.getLogger(MyTunesMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(listViewAllSongs.getItems().size());
        listViewAllSongs.setItems(model.getSongs());
        listViewSongInfo.setItems(model.getSongInfo());
        listViewQueue.setItems(model.getQuedSongs());
        listViewLibrary.setItems(model.getPlayList());
        listNowPlaying.setItems(model.getSongInfo());
        listViewAllSongs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewQueue.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewQueue.setItems(model.getSongs());
        

    }

    @FXML
    private void onHandleSliderVol(MouseEvent event) {
        model.changeVolume(sliderVol.getValue());
    Stage stage = (Stage) borderPane.getScene().getWindow();
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event)      
            {
                System.out.println("Stage is Closing");
                model.UpdateVolume(sliderVol.getValue());
            }
        });
        
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
    private void onHandleRemove(ActionEvent event) throws IOException {
        model.removeSong(null); //flyttes en sang i fra gui'en.
    }

    @FXML
    private void onHandleMisc(ActionEvent event) {
        comboBoxMisc.setItems(FXCollections.observableArrayList("reverseList", "randomiseList", "sortByTitle"));
        comboBoxMisc.setVisibleRowCount(3);
    }

    @FXML
    private void onHandleSearch(KeyEvent event) throws IOException {
        model.searcher(txtFieldSearch.getText());
        
    }

    @FXML
    private void onHandlePlaylistEdit(ActionEvent event) {
        model.editSong();
    }

    @FXML
    private void onHandlePlaylistAdd(ActionEvent event) {
        model.addPlaylist();
    }


    @FXML

    private void HandleDragDone(DragEvent event) {
        event.getDragboard().getFiles().clear();

    }
    
    @FXML
    private void onSongRemove(ActionEvent event)
    {
        
    }

//    private void dragSelected(DragEvent event) {
//        
//        for (Song song : listViewAllSongs.getSelectionModel().getSelectedItems())
//                {
//            listViewQueue.getItems().add(song);
//        }
        
        
//    }

    @FXML
    private void queentered(MouseDragEvent event) {
        System.out.println("que entered 1");
    }

    @FXML
    private void queentered(DragEvent event) {
        System.out.println("que entered 2");
    }

    @FXML
    private void quedetected(MouseEvent event) {
        System.out.println("que detected");
    }

    @FXML
    private void quedone(DragEvent event) {
        System.out.println("que done");
    }

    @FXML
    private void queexited(DragEvent event) {
        System.out.println("que exited");
    }

    @FXML
    private void quereleased(MouseDragEvent event) {
        System.out.println("que released");
    }

    @FXML
    private void queover(DragEvent event) {
        System.out.println("que over");
    }

    @FXML
    private void queexited(MouseDragEvent event) {
        System.out.println("que exited");
    }

    @FXML
    private void queover(MouseDragEvent event) {
        System.out.println("que over");
    }

    @FXML
    private void quedropped(DragEvent event) {
        System.out.println("que dropped");
    }

    @FXML
    private void allsongsdragentered(MouseDragEvent event) {
        System.out.println("songs mousedrag entered");
    }

    @FXML
    private void allsongsdragentered(DragEvent event) {
        System.out.println("songs drag entered");
    }

    @FXML
    private void allsongsdragdected(MouseEvent event) {
        System.out.println("0 "+event.MOUSE_RELEASED.getName());
        System.out.println(" 1 "+event.getTarget().toString());
        System.out.println("songs drag detected");
    }

    @FXML
    private void allsongsdragdone(DragEvent event) {
        System.out.println("songs drag done");
    }

    @FXML
    private void allsongsdragexited(DragEvent event) {
        System.out.println("songs drag exit");
    }

    @FXML
    private void allsongsdragreleased(MouseDragEvent event) {
        System.out.println("songs drag released");
    }

    @FXML
    private void allsongsdragover(DragEvent event) {
        System.out.println("songs dragEvent over");
    }

    @FXML
    private void allsongsdragexoted(MouseDragEvent event) {
        System.out.println("songs drag exited");
    }

    @FXML
    private void allsongsdragover(MouseDragEvent event) {
        System.out.println("songs Mousedrag over");
    }

    @FXML
    private void allsongsdragdropped(DragEvent event) {
        System.out.println("songs drag dropped");
    }

    @FXML
    private void queMouseRelease(MouseEvent event) {
        System.out.println("hej ");
    }

    
}
