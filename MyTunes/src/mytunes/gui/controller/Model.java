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
import mytunes.dal.SettingsDAO;
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
    private PlayerManager playerManager;
    private Search songsearcher;
    private Player player;
    private BorderPane borderPane;
    private List<Song> empty;
    private List<Playlist> addPlaylist;
    private SettingsDAO setDAO;

    public Model() throws IOException
    {
        songinfo = FXCollections.observableArrayList();
        playlist = FXCollections.observableArrayList();
        songs = FXCollections.observableArrayList();
        songsearcher = new Search();
        playerManager = new PlayerManager();
        sDAO = new SongDAO();
        pDAO = new PlaylistDAO();
        empty = new ArrayList();
        //songinfo = FXCollections.observableArrayList(playerManager.getSongInfo());
        songs = FXCollections.observableArrayList(playerManager.getAllSongs());
        playlist = FXCollections.observableArrayList(playerManager.getAllPlaylists());
        addPlaylist = new ArrayList();
        setDAO = new SettingsDAO();
        
        System.out.println(setDAO.lastSetVolume());
    }

    /**
     *
     */
    public ObservableList<String> getSongInfo()
    {
        //return playerManager.getSongInfo();
        return null;
    }

    public List<Playlist> addPlaylist()
    {
        return addPlaylist;
    }

    public ObservableList<Playlist> getPlayList()
    {
        return playlist;
    }

    public ObservableList<Song> getQuedSongs()
    {
        return playerManager.getQueuedSongs();
    }

    public ObservableList<Song> getSongs()
    {

        for (Song s : sDAO.getAllSongsFromDB())
        {
            System.out.println(s.getTitle());
            //songs.add(s);
        }
        return songs;
    }
    public ObservableList<String> getNowPlaying()
    {
       return playerManager.getNowPlaying();
    }

    /*
    Alle vores Knapper
     */
    public void playSong()
    {
        playerManager.playSong();
    }

    public void pauseSong()
    {
        playerManager.pauseSong();
    }

    public void changeVolume(double vol)
    {
        //System.out.println("model change volume " + vol);
        playerManager.changeVolume(vol/100);
    }

    public void UpdateVolume(double vol)
    {
        setDAO.updateVolume(vol / 100);
    }
    
    public double getSliderVolumeFromDB()
    {
        return setDAO.lastSetVolume();
    }

    public void playPrevSong()
    {
        playerManager.playPrevSong();
    }

    public void playNextSong()
    {
        playerManager.playNextSong();
    }

    public void repeatHandler()
    {
        playerManager.repeatHandler();
    }

    public void shuffleHandler()
    {
        playerManager.shuffleHandler();
    }

    public void searcher(String query) throws IOException
    {
       songs.setAll(songsearcher.searcher(query));
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
        
    }

    public void addSongToQue()
    {
        playerManager.tmpTester();
        //queues.addAll(songs);
        //player.addSongsToQueue(getSongs());
//       queues.addAll(getSongs());
//        System.out.println("Queue size out here " + queues.size());
//       player.addSongsToQueue(queues);
    }

    public void removeSongFromQue()
    {
        playerManager.removeSongFromQue();
    }

    public void queComboBox()
    {
        playerManager.queMisc();

    }

    public void SelectFolder(Stage stage) throws IOException
    {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory == null)
        {
            //No Directory selected
        } else
        {
            String path = selectedDirectory.getAbsolutePath();
            File file = new File(path);
            songs.addAll(sDAO.addFolder(file));
            
            
        //sdao.writeChanges();
        }

        playerManager.getAllSongs();
        for (Song s : playerManager.getAllSongs())
        {
            System.out.println(s.getTitle());
        }

        //Do something with view here
    }

    

}
