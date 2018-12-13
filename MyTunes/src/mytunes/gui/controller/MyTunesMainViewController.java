/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import static java.awt.event.KeyEvent.VK_DELETE;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.text.Position;
import mytunes.be.Playlist;
import mytunes.be.Song;

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

    @FXML
    private ListView<String> listNowPlaying;
    @FXML
    private Button btnRemoveSongQue;
    @FXML
    private Button btnRemoveSong;

    private static final DataFormat customFormat = new DataFormat("Song.custom");
    Model model;
    Long lastTime;
    @FXML
    private Slider sliderPlayback;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            model = new Model();
        } catch (IOException ex) {
            Logger.getLogger(MyTunesMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //        Stage stage = (Stage) borderPane.getScene().getWindow();
        listViewAllSongs.setItems(model.getSongs());
        listViewQueue.setItems(model.getQuedSongs());
        listViewLibrary.setItems(model.getPlayLists());
        listNowPlaying.setItems(model.getNowPlaying());
        listViewAllSongs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewQueue.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        sliderVol.setValue(model.getSliderVolumeFromDB() * 100);
        System.out.println("Slider " + sliderVol.getValue());
        model.changeVolume(model.getSliderVolumeFromDB());
        model.sendSliderForPlayback(sliderPlayback);
        listViewLibrary.editableProperty().setValue(Boolean.TRUE);
        lastTime = 0L;
        listViewSongInfo.setItems(model.getSongName());
    }

    @FXML
    private void onHandleSliderVol(MouseEvent event) {
        model.changeVolume(sliderVol.getValue());
        //sliderVol.setValue(sliderVol.getValue());
        System.out.println();
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
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
        model.addSongToQue(listViewAllSongs.getSelectionModel().getSelectedItems());
        listNowPlaying.setItems(model.getSongName());
        listViewSongInfo.setItems(model.getNowPlaying());
        //listViewQueue.getItems().addAll(model.getQuedSongs());

    }

    @FXML
    private void onHandleRemove(ActionEvent event) throws IOException {
        model.removeSongsFromQue(listViewQueue.getSelectionModel().getSelectedItems());
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
    private void onHandleAddFolder(ActionEvent event) throws IOException, InterruptedException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        model.SelectFolder(stage);

    }

    private void HandleDragDone(DragEvent event) {
        event.getDragboard().getFiles().clear();

    }

    @FXML
    private void onSongRemove(ActionEvent event) {
        try {
            model.removeSongs(listViewAllSongs.getSelectionModel().getSelectedItems(), listViewLibrary.getSelectionModel().getSelectedItem());
        } catch (IOException ex) {

            System.out.println("fejl 115");
        }
        
    }

//    private void dragSelected(DragEvent event) {
//        
//        for (Song song : listViewAllSongs.getSelectionModel().getSelectedItems())
//                {
//            listViewQueue.getItems().add(song);
//        }
//    }
    private void allsongsdragdected(MouseEvent event) {
        System.out.println("songs drag detected");

        Dragboard db = listViewAllSongs.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        //content.put(listViewAllSongs.getSelectionModel().getSelectedItems());
        content.putString("hej");
        db.setContent(content);
    }

    private void MouseSelection(MouseEvent event) {
        System.out.println(listViewQueue.getSelectionModel().getSelectedItems());
    }

    @FXML

    private void onHandleSongEdit(ActionEvent event) throws IOException {

        Song s = listViewAllSongs.getSelectionModel().getSelectedItem();
        int songIndex = listViewAllSongs.getSelectionModel().getSelectedIndex();
        System.out.println("dette er titlen for s " + s.getTitle());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mytunes/gui/view/editSongView.fxml"));

        loader.load();

        EditSongViewController display = loader.getController();
        display.setSong(s, songIndex);
        display.setModel(model);
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.showAndWait();

    }

    public void updateView(Song song, int songIndex) {
//        System.out.println(songIndex);
//        System.out.println(song.getTitle());
//        
//        List<Song> tmpSongs = model.getSongs();
//        tmpSongs.set(songIndex, song);
//        System.out.println(tmpSongs.get(songIndex).getTitle());
////        tmpSongs.set(songIndex, song);
////        System.out.println(tmpSongs.get(songIndex).getTitle());
//        model.getSongs().set(songIndex, song);
//        try
//        {
//            model.removeSong(song);
//        } catch (IOException ex)
//        {
//            System.out.println("fejl 11102");
//        }
//        
//        listViewAllSongs.setItems(model.getSongs());
//        

    }

    @FXML
    private void onHandlePlaylistEdit(ActionEvent event) {
        Playlist plist = listViewLibrary.getSelectionModel().getSelectedItem();
        System.out.println("dette er titlen for p " + plist.getTitle());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mytunes/gui/view/EditPlaylistView.fxml"));

        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MyTunesMainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        EditPlaylistViewController display = loader.getController();
        display.setPlistTitle(plist);
        display.setModel(model);
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.showAndWait();
    }

    @FXML
    private void onHandlePlaylistRemove(ActionEvent event) {
        try {
            model.removePlaylist(listViewLibrary.getSelectionModel().getSelectedItem());
        } catch (IOException ex) {
            System.out.println("could not find playlist");
        } catch (SQLException ex) {
            System.out.println("could not connect to server, fejl 102");
        }
    }

    @FXML
    private void onHandlePlaylistAdd(ActionEvent event) {
        model.addToPlaylist(listViewLibrary.getSelectionModel().getSelectedItem(), listViewAllSongs.getSelectionModel().getSelectedItems());
        //model.addPlaylistToDB(listViewAllSongs.getSelectionModel().getSelectedItems(), listViewLibrary.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void queueLookForDeleteKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            model.removeSongsFromQue(listViewQueue.getSelectionModel().getSelectedItems());
        }
    }

    private void queueMouseReleased(MouseEvent event) {

        System.out.println("We got into queue");
    }

    private void queueMouseExit(MouseEvent event) {
        System.out.println("queueMouseExit");
    }

    private void queueMouseEnter(MouseEvent event) {
        System.out.println("queueMouseEnter");
    }

    private void allMouseReleased(MouseEvent event) {
        System.out.println("allMouseReleased");
        System.out.println(event.getSceneX());
        System.out.println(event.getSceneY());

        System.out.println(listViewQueue.getLayoutX());
        System.out.println("øhm" + listViewQueue.isHover());
        System.out.println("øhm" + listViewAllSongs.isHover());
        if (listViewQueue.isHover()) {
            System.out.println("this could work");
        }
    }

    @FXML
    private void PlaylistsMouseClick(MouseEvent event) {

        long timeDiff = 0;
        long currentTime = System.currentTimeMillis();

        if (lastTime != 0 && currentTime != 0) {
            timeDiff = currentTime - lastTime;
            if (timeDiff <= 215) {
                model.librarySelection(listViewLibrary.getSelectionModel().getSelectedItem());
            } else {
                System.out.println("singleclick");
            }
        }
        lastTime = currentTime;

    }

    private void lstTextChanged(InputMethodEvent event) {
        System.out.println("Tester lige her tak");
    }

    private void lstViewTextChanged(InputMethodEvent event) {
        System.out.println("Tester lige her takny");
    }

    @FXML
    private void queueMouseClick(MouseEvent event) {
        long timeDiff = 0;
        long currentTime = System.currentTimeMillis();

        if (lastTime != 0 && currentTime != 0) {
            timeDiff = currentTime - lastTime;
            if (timeDiff <= 215) {
                model.changeToThisSong(listViewQueue.getSelectionModel().getSelectedItem());
            } else {
                System.out.println("singleclick");
            }
        }
        lastTime = currentTime;
    }

    @FXML
    private void allSongsMouseClicked(MouseEvent event) {
        long timeDiff = 0;
        long currentTime = System.currentTimeMillis();

        if (lastTime != 0 && currentTime != 0) {
            timeDiff = currentTime - lastTime;
            if (timeDiff <= 215) {
                model.playNowSelectedSong(listViewAllSongs.getSelectionModel().getSelectedItem());
                listNowPlaying.setItems(model.getSongName());
                listViewSongInfo.setItems(model.getNowPlaying());
            } else {
                System.out.println("singleclick");
            }
        }
        lastTime = currentTime;
    }

    @FXML
    private void onHandlePlaylistCreate(ActionEvent event) {
        TextField txtTitle = new TextField();
        txtTitle.setText("Playlist name");
        System.out.println(txtTitle.getBaselineOffset());

        Button btn = new Button();
        btn.setText("Create playlist");
        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        txtTitle.setTranslateX(100);
//        txtTitle.setTranslateY(100);

        root.setAlignment(txtTitle, Pos.TOP_CENTER);
        root.setAlignment(btn, Pos.BOTTOM_CENTER);
        Pos p1 = btn.getAlignment();
        root.getChildren().addAll(txtTitle, btn);

        Scene scene = new Scene(root, 200, 50);
        Stage stage = new Stage();
        stage.setTitle("create playlist");
        stage.setScene(scene);
        stage.show();
        List<Playlist> allPlaylists = model.getPlayLists();
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println(txtTitle.getText());
                System.out.println("Hello World!");
                try {

                    model.createPlaylist(new Playlist(txtTitle.getText()));
                } catch (IOException ex) {
                    System.out.println("fejl 1111111");
                }
                Stage stage = (Stage) txtTitle.getScene().getWindow();
                stage.close();
            }
        });

    }

    @FXML
    private void playlistLookForDeleteKey(KeyEvent event) throws IOException, SQLException {
        if (event.getCode().equals(KeyCode.DELETE)) {
            model.removePlaylist(listViewLibrary.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void allSongsLookForDeleteKey(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.DELETE)) {
            model.removeSongs(listViewAllSongs.getSelectionModel().getSelectedItems(), listViewLibrary.getSelectionModel().getSelectedItem());        
        }
    }

}
