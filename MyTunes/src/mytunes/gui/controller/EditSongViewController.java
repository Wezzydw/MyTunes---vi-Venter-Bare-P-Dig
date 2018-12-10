/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.awt.List;
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
        //ObservableList<Song> allSongs = model.getSongs();
        System.out.println("nu er vi inde i edit" );
        comBox = FXCollections.observableArrayList();
        //txtEditAlbum.setText(model.getSongs().get(0).getAlbum());//get(0) skal peje på den sang du redigerer
       
        comBox.add("Rock");
        comBox.add("Hardstyle");
        comBox.add("Pop");
        comBox.add("Andet");
        comboCategory.setItems(comBox);
        
        
        
    }    

    @FXML
    private void onHandleCancelEdit(ActionEvent event) {
        
        
    }

    @FXML
    private void onHandleSaveEdit(ActionEvent event) {
        
        
        System.out.println("save data" );
        String album = txtEditAlbum.getText();
        String author = txtEditArtist.getText();
        String title = txtEditTitle.getText();
        String year = txtEditYear.getText();
        String categori = comboCategory.getSelectionModel().getSelectedItem();
        String length = d2; 
        int id = Id;
        String filepath = lblFilepath.getText();
        Song s = new Song(title, author, length, year, categori, filepath, album, id);
        model.updateSong(s); //update song
        
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
        display.updateView(s, songIndex);
        System.out.println("album =" + album + ", artist=" + author + ", title=" + title + ", year=" + year + ", length=" + length + ", id=" +id);
        
    }
    public void setSong(Song song, int songIndex)
    {
        selectedSong = song;
        txtEditArtist.setText(selectedSong.getAuthor());
        txtEditAlbum.setText(selectedSong.getAlbum());
        txtEditTitle.setText(selectedSong.getTitle());
        txtEditYear.setText(selectedSong.getReleaseYear());
        lblFilepath.setText(selectedSong.getFilePath());
        this.d2 = selectedSong.getLength();
        System.out.println(d2);
        this.Id = selectedSong.getId();
        this.songIndex = songIndex;
    }
    
    
    
    
    
    
    
    
    
}
