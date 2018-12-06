/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
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
public class Model {

    private SongDAO sDAO;
    private PlaylistDAO pDAO;
    private ObservableList<Song> songs;
    private ObservableList<Song> queues;
    private ObservableList<String> songinfo;
    private ObservableList<Playlist> playlist;
    private PlayerManager logiclayer;
    private Search songsearcher;
    private Player player;
    private BorderPane borderPane;
    private List<Song> empty;
    private List<Playlist> addPlaylist;
    
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
        addPlaylist = new ArrayList();
    }

    /**
     *
     */

    public ObservableList<String> getSongInfo()
    {
        return songinfo;
    }
    
    public List<Playlist> addPlaylist(){
        return addPlaylist;
    }

    public ObservableList<Playlist> getPlayList() {
        return playlist;
    }

    public ObservableList<Song> getQuedSongs()
    {
        return queues;
    }

    public ObservableList<Song> getSongs() {
        
        
        for(Song s : sDAO.getAllSongs())
        {
            System.out.println(s.getTitle());
            //songs.add(s);
        }
        return songs;
    }
    /*
    Alle vores Knapper
     */
    public void playSong() {
        System.out.println(songs.size());
        player.playSong();
    }

    public void pauseSong() {
        player.pauseSong();
    }

    public void changeVolume(double vol) {
        player.changevolume(vol / 100);
        //System.out.println("vol: " + vol);
    }

    public void playPrevSong() {
        player.playPrevSong();
    }

    public void playNextSong() {
        player.playNextSong();
    }

    public void repeatHandler() {
        player.repeatHandler();
    }

    public void shuffleHandler() {
        player.shuffleHandler();
    }

    public List<Song> searcher(String query) throws IOException {
        return songsearcher.searcher(query);
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) throws IOException
    {
        sDAO.deleteSong(song);
        songs.remove(song);
    }

    public void editSong() {
        logiclayer.editSong();
    }

    public void addSongToQue() {
        logiclayer.addSongToQue();
    }

    public void removeSongFromQue() {
        logiclayer.removeSongFromQue();
    }

    public void queComboBox() {
        logiclayer.queMisc();

    }

    public void SelectFolder(Stage stage) throws IOException {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            //No Directory selected
        } else {
            String path = selectedDirectory.getAbsolutePath();
             songs.addAll(logiclayer.SelectedFolder(path));
        }
        
        logiclayer.getAllSongs();
        for(Song s : logiclayer.getAllSongs())
        {
            System.out.println(s.getTitle());
        }
        

        //Do something with view here
       
    }

}
