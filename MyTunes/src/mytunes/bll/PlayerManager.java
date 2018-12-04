/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.IOException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.dal.PlaylistDAO;
import mytunes.dal.SongDAO;

/**
 *
 * @author Wezzy Laptop
 */
public class PlayerManager
{

    private ComboBox<String> comboBoxMisc;
    private SongDAO sDAO;
    private PlaylistDAO pDAO;

    public PlayerManager() throws IOException
    {
        sDAO = new SongDAO();
        pDAO = new PlaylistDAO();
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

    public List<Playlist> getAllPlaylists()
    {
    return pDAO.getAllPlaylists();
    }

    public List<Song> getAllSongs()
    {
       return sDAO.getAllSongs();
    }
               
    public List<String> getMetaData()
    {
        List<String> MetaList = new ArrayList();
        sDAO.getSong(null);// input SONG HERE
        Song son = sDAO.getSong(null);
        if (son.getTitle() != null)
        {
            MetaList.add("title;" + son.getTitle());
        }
        if (son.getAuthor() != null)
        {
            MetaList.add("author;" + son.getAuthor());
        }
        if (son.getCategori() != null)
        {
            MetaList.add("categori;" + son.getCategori());
        }
        if (son.getReleaseYear() != null)
        {
            MetaList.add("releaseyear;" + son.getReleaseYear());
        }
        if (son.getAlbum() != null)
        {
            MetaList.add("album;" + son.getAlbum());
        }
        if (son.getLength() != null)
        {
            MetaList.add("length;" + son.getLength());
        }

        return MetaList;
    }
}
