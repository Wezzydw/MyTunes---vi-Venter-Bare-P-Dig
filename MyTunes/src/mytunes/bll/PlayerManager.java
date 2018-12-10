/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.dal.PlaylistDAO;
import mytunes.dal.SettingsDAO;
import mytunes.dal.SongDAO;

/**
 *
 * @author Wezzy Laptop
 */
public class PlayerManager
{

    private ObservableList<Song> songQueue;
    private ObservableList<String> nowPlaying;
    private ComboBox<String> comboBoxMisc;
    private SongDAO sdao;
    private PlaylistDAO pDAO;
    private Player player;
    private SettingsDAO setdao;
    private double volumeFromDB;
    private Slider sliderPlayback;

    public PlayerManager() throws IOException
    {
        songQueue = FXCollections.observableArrayList();
        nowPlaying = FXCollections.observableArrayList();
        setdao = new SettingsDAO();
        sdao = new SongDAO();
        pDAO = new PlaylistDAO();
        songQueue.addAll(setdao.queueList());
        
        checkForSongsSomewhere();
    }

    private void checkForSongsSomewhere()
    {
        
        if (player == null)
        {
            if (!songQueue.isEmpty())
            {
                player = new Player(songQueue);
                player.changevolume(volumeFromDB);
                System.out.println("Er det her?");
                nowPlaying.addAll(player.getNowPlaying());
                player.makeSliderForPlayback(sliderPlayback);
            }
        }
    }

    public ObservableList<Song> getQueuedSongs()
    {
        return songQueue;
    }
    
    public ObservableList<String> getNowPlaying()
    { 
        if (player == null)
            return nowPlaying;
        else return player.getNowPlaying();
                    
    }

    public void addSongToQue(List<Song> songs)
    {
        System.out.println("hejlle");
        songQueue.addAll(songs);
        player.addSongsToQueue(songs);
    }

    public void removeSongFromQue()
    {
            //To be done
    }

    public void queMisc()
    {
        comboBoxMisc.setItems(FXCollections.observableArrayList("reverseList", "randomiseList", "sortByTitle"));
        comboBoxMisc.setVisibleRowCount(3);
    }




    public List<Playlist> getAllPlaylists()
    {
        return pDAO.getAllPlaylists();
    }

    public List<Song> getAllSongs()
    {
        return sdao.getAllSongsFromDB();
    }
    public void updateSong(Song song)
    {
        sdao.updateSong(song);
    }
    

  
    
//    public void getSongInfo()
//    {
////        System.out.println("in getSongInfo");
////        if(player != null)
////        {
////        if (!player.nowPlaying().isEmpty())
////        {
////            System.out.println("futher in getsonginfo");
////        //Song song = player.nowPlaying().get(0);
////        nowPlaying.addAll(getMetaData(player.nowPlaying().get(0)));
////        }
////        }
//        return nowPlaying;
//    }

    public void playSong()
    {
        if (player != null)
        {
            player.playSong();
        }
    }

    public void pauseSong()
    {
        if (player != null)
        {
            player.pauseSong();
        }
    }

    public void changeVolume(double vol)
    {
        if (player != null)
        {
            player.changevolume(vol);
        }
        else {
            
            volumeFromDB = vol*100;
            System.out.println("saved volume from db " + volumeFromDB);
        }
    }

    public void playPrevSong()
    {
        if (player != null)
        {
            player.playPrevSong();
        }
    }

    public void playNextSong()
    {
        if (player != null)
        {
            player.playNextSong();
        }
    }

    public void repeatHandler()
    {
        if (player != null)
        {
            player.repeatHandler();
        }
    }

    public void shuffleHandler()
    {
        if (player != null)
        {
            player.shuffleHandler();
        }
    }
    
    public void tmpTester()
    {
        System.out.println("test");
        songQueue.addAll(sdao.getAllSongsFromDB());
        checkForSongsSomewhere();
    }
    
    public void makeSliderForPlayBack(Slider sliderPlayback)
    {
        this.sliderPlayback = sliderPlayback;
    }
}
