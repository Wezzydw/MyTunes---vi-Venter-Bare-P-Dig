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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
        
//        txtEditAlbum.setText(model.getSongInfo("Album"));
//        txtEditArtist.setText(value);
//        txtEditTitle.setText(value);
//        txtEditYear.setText(value);
//        comboCategory.setItems(value);
//        lblFilepath.setText(value);
//        
//        
    }    

    @FXML
    private void onHandleCancelEdit(ActionEvent event) {
        
        
    }

    @FXML
    private void onHandleSaveEdit(ActionEvent event) {
        
        
    }
    
    
    
    
    
    
    
    
    
}
