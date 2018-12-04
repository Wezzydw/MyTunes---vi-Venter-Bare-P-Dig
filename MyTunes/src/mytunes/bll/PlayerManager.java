/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import mytunes.be.Song;
import mytunes.dal.SongDAO;

/**
 *
 * @author Wezzy Laptop
 */
public class PlayerManager {

    private ComboBox<String> comboBoxMisc;
    private SongDAO sdao;
    
    
    public PlayerManager()
    {
        sdao = new SongDAO();
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
    
    public void SelectedFolder(String path) throws IOException
    {
        File file = new File(path);
        sdao.addFolder(file);
    }
    
    public List<Song> getAllSongs(){
        return sdao.getAllSongs();
    }
    
}
