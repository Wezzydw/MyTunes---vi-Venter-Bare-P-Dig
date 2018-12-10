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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private ComboBox<?> comboCategory;
    @FXML
    private Label lblFilepath;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    
    Model model;

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
        ObservableList<Song> allSongs = model.getSongs();
        //txtEditAlbum.setText(model.getSongs().get(0).getAlbum());//get(0) skal peje på den sang du redigerer
        txtEditArtist.setText(allSongs.get(0).getAuthor());
        txtEditTitle.setText(allSongs.get(0).getTitle());
        txtEditYear.setText(allSongs.get(0).getReleaseYear());
//        comboCategory.setItems(value);
        lblFilepath.setText(allSongs.get(0).getFilePath());
        
        
    }    

    @FXML
    private void onHandleCancelEdit(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void onHandleSaveEdit(ActionEvent event) {
        
        
        System.out.println("save data" );
        String album = txtEditAlbum.getText();
        String author = txtEditArtist.getText();
        String title = txtEditTitle.getText();
        String year = txtEditYear.getText();
//        model.UpdateVolume(0); //update song 
        
    }
    
    
    
    
    
    
    
    
    
}
