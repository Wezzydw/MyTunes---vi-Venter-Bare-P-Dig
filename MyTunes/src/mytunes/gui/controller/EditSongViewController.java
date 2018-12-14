/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Song;

/**
 * FXML Controller class
 *
 * @author mpoul
 */
public class EditSongViewController implements Initializable {

    @FXML
    private TextField txtEditArtist;
    @FXML
    private TextField txtEditAlbum;
    @FXML
    private TextField txtEditTitle;
    @FXML
    private TextField txtEditYear;
    @FXML
    private ComboBox<String> comboCategory;
    @FXML
    private Label lblFilepath;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    
    Model model;
    private Song selectedSong;
    private String d2;
    private int Id;
    private int songIndex;
    private ObservableList<String> comBox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            model = new Model();
        } catch (IOException ex) {
            Logger.getLogger(EditSongViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        comBox = FXCollections.observableArrayList();
        comBox.add("Rock");
        comBox.add("Hardstyle");
        comBox.add("Pop");
        comBox.add("Andet");
        comboCategory.setItems(comBox);
    }    

    @FXML
    private void onHandleCancelEdit(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onHandleSaveEdit(ActionEvent event) {
        
        String album = txtEditAlbum.getText();
        String author = txtEditArtist.getText();
        String title = txtEditTitle.getText();
        String year = txtEditYear.getText();
        String categori = comboCategory.getSelectionModel().getSelectedItem();
        String length = d2; 
        int id = Id;
        String filepath = lblFilepath.getText();
        Song s = new Song(title, author, length, year, categori, filepath, album, id);
        model.updateSong(s);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mytunes/gui/view/MyTunesMainView.fxml"));
        
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            Logger.getLogger(EditSongViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        MyTunesMainViewController display = loader.getController();
        display.model.updateSong(s);

        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();

        
    }
    public void setSong(Song song)
    {
        selectedSong = song;
        txtEditArtist.setText(selectedSong.getAuthor());
        txtEditAlbum.setText(selectedSong.getAlbum());
        txtEditTitle.setText(selectedSong.getTitle());
        txtEditYear.setText(selectedSong.getReleaseYear());
        lblFilepath.setText(selectedSong.getFilePath());
        this.d2 = selectedSong.getLength();
        this.Id = selectedSong.getId();
        this.songIndex = songIndex;
        
    }
    public void setModel(Model model)
    {
        this.model = model;
    }
    
}
