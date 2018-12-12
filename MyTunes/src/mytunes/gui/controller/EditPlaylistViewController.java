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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Playlist;

/**
 * FXML Controller class
 *
 * @author andreas
 */
public class EditPlaylistViewController implements Initializable
{

    @FXML
    private TextField txtTitle;
    @FXML
    private Label lblTitle;
    
    private Model model;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void onHandleSave(ActionEvent event) throws IOException, SQLException
    {
        System.out.println("save data" );
        String title = txtTitle.getText();
        Playlist plist = new Playlist(title);
        model.renamePlaylist(lblTitle.getText(), title);
    }

    @FXML
    private void onHandleCancel(ActionEvent event)
    {
        Stage stage = (Stage) lblTitle.getScene().getWindow();
        stage.close();
    }
    public void setPlistTitle(Playlist plist)
    {
        txtTitle.setText(plist.getTitle());
        lblTitle.setText(plist.getTitle());
    }
    
}
