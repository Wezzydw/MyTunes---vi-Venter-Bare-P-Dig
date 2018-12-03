/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.MediaPlayer;
import mytunes.be.Playlist;
import mytunes.bll.PlayerManager;
import mytunes.be.Song;
import mytunes.bll.Search;
import mytunes.dal.SongDAO;

/**
 *
 * @author marce
 */
public class Model
{
    private SongDAO sDAO;
    private ObservableList<Song> songs;
    private ObservableList<Song> ques;
    private ObservableList<Song> songinfo;
    private ObservableList<Playlist> playlist;
    private PlayerManager logiclayer;
    private Search songsearcher;

    public Model()
    {
        songinfo = FXCollections.observableArrayList();
        playlist = FXCollections.observableArrayList();
        ques = FXCollections.observableArrayList();
        songs = FXCollections.observableArrayList();
        songsearcher = new Search();
        logiclayer = new PlayerManager();
        sDAO = new SongDAO();

    }

    /**
     * 
     */
    public ObservableList<Song> getSongInfo()
    {
        return songinfo;

    }

    public ObservableList<Playlist> getPlayList()
    {
        return playlist;
    }

    public ObservableList<Song> getQuedSongs()
    {
        return ques;
    }

    public ObservableList<Song> getSongs()
    {
        return songs;
    }
    /*
    Alle vores Knapper
    */
    public void playSong()
    {
        
    }

    public void pauseSong()
    {

    }

    public void nextSong()
    {

    }

    public void changeVolume()
    {

    }

    public void playPrevSong()
    {

    }

    public void playNextSong()
    {

    }

    public void playOnRepeat()
    {

    }

    public void repeatHandler()
    {

    }

    public void shuffleHandler()
    {

    }

    public List<Song> searcher()
    {
        return null;
    }

    public void addSong(Song song)
    {
        songs.add(song);
    }

    public void removeSong(Song song)
    {
        sDAO.deleteSong(song);
        songs.remove(song);
    }

    public void editSong()
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

    }

}
