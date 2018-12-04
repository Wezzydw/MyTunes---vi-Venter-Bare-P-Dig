/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.MediaPlayer;
import mytunes.be.Playlist;
import mytunes.bll.PlayerManager;
import mytunes.be.Song;
import mytunes.bll.Player;
import mytunes.bll.Search;
import mytunes.dal.PlaylistDAO;
import mytunes.dal.SongDAO;

/**
 *
 * @author marce
 */
public class Model
{
    private SongDAO sDAO;
    private PlaylistDAO pDAO;
    private ObservableList<Song> songs;
    private ObservableList<Song> queues;
    private ObservableList<String> songinfo;
    private ObservableList<Playlist> playlist;
    private PlayerManager logiclayer;
    private Search songsearcher;
    private Player player;
    private List<Song> empty;
    public Model() throws IOException
    {
        songinfo = FXCollections.observableArrayList();
        playlist = FXCollections.observableArrayList();
        queues = FXCollections.observableArrayList();
        songs = FXCollections.observableArrayList();
        songsearcher = new Search();
        logiclayer = new PlayerManager();
        sDAO = new SongDAO();
        player = new Player();
        pDAO = new PlaylistDAO();
        empty = new ArrayList();
        songinfo = FXCollections.observableArrayList(logiclayer.getMetaData());
        songs = FXCollections.observableArrayList(logiclayer.getAllSongs());
        playlist = FXCollections.observableArrayList(logiclayer.getAllPlaylists());
        queues = FXCollections.observableArrayList(empty);
    }

    /**
     * 
     */
    public ObservableList<String> getSongInfo()
    {
        return songinfo;

    }

    public ObservableList<Playlist> getPlayList()
    {
        return playlist;
    }

    public ObservableList<Song> getQuedSongs()
    {
        return queues;
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
        player.playSong();
    }

    public void pauseSong()
    {
        player.pauseSong();
    }

    public void changeVolume(double vol)
    {
        player.changevolume(vol / 100);
    }

    public void playPrevSong()
    {
        player.playPrevSong();
    }

    public void playNextSong()
    {
        player.playNextSong();
    }

    public void repeatHandler()
    {
        player.repeatHandler();
    }

    public void shuffleHandler()
    {
        player.shuffleHandler();
    }

    public List<Song> searcher(String query)
    {
        return songsearcher.searcher(query);
    }

    public void addSong(Song song)
    {
        songs.add(song);
    }

    public void removeSong(Song song) throws IOException
    {
        sDAO.deleteSong(song);
        songs.remove(song);
    }

    public void editSong()
    {
        logiclayer.editSong();
    }

    public void addSongToQue()
    {
        logiclayer.addSongToQue();
    }

    public void removeSongFromQue()
    {
        logiclayer.removeSongFromQue();
    }

    public void queComboBox()
    {
        logiclayer.queMisc();
        
    }

}
