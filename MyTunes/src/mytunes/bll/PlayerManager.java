/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Wezzy Laptop
 */
public class PlayerManager {

    private ComboBox<String> comboBoxMisc;
    
    
    public PlayerManager()
    {
        
    }
    
    public void addSongToQue()
    {
        
    }

    public void removeSongFromQue()
    {
        
    }

    public void queMisc()
    {
        comboBoxMisc.setItems(FXCollections.observableArrayList("reverseList", "randomiseList", "sortByTitle"));
        comboBoxMisc.setVisibleRowCount(3);
    }
    
    public void editSong()
    {

    }
    
}
