/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Slider;
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
    private ObservableList<Playlist> playlists;
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
        playlists = FXCollections.observableArrayList();
        songs = FXCollections.observableArrayList();
        songsearcher = new Search();
        playerManager = new PlayerManager();
        sDAO = new SongDAO();
        pDAO = new PlaylistDAO();
        empty = new ArrayList();
        //songinfo = FXCollections.observableArrayList(playerManager.getSongInfo());
        //songs = FXCollections.observableArrayList(playerManager.getAllSongs());
        playlists = FXCollections.observableArrayList(playerManager.getAllPlaylists());
        addPlaylist = new ArrayList();
        setDAO = new SettingsDAO();
        playlistInitFilling();
        System.out.println(setDAO.lastSetVolume());
//        playlists.get(0).addSongSelection(playerManager.getAllSongs());
//        songs = FXCollections.observableArrayList(playlists.get(0).getSongsInPlaylist());
    }

    private void playlistInitFilling()
    {
        Playlist tester = new Playlist("TesterAllSogns");
        System.out.println("Size of getallsongs " + playerManager.getAllSongs().size());
        playlists.add(tester);
        tester.addSongSelection(playerManager.getAllSongs());
        playlists.add(new Playlist("All Songs"));
        playlists.add(new Playlist("tester"));
        //playlists.get(0).addSongSelection(playerManager.getAllSongs());
        System.out.println(playlists.size());
        songs = FXCollections.observableArrayList(playlists.get(0).getSongsInPlaylist());

    }

    public void librarySelection(Playlist selectedPlaylist)
    {
        songs.setAll(selectedPlaylist.getSongsInPlaylist());
    }

    /**
     *
     */
    public ObservableList<String> getSongInfo()
    {
        //return playerManager.getSongInfo();
        return null;
    }
    
   

    public void createPlaylist(Playlist plist) throws IOException
    {


        playerManager.createPlaylist(plist);

        songs.clear();


    }

    public ObservableList<Playlist> getPlayLists()
    {
        return playlists;
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
        playerManager.changeVolume(vol / 100);
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
        System.out.println("er det her delete ligger");
        pDAO.deleteSongFromAllPlaylists(song);
        sDAO.deleteSong(song);
        songs.remove(song);
    }

    public void editSong()
    {

    }

    public void addSongToQue(ObservableList<Song> toAdd) throws IOException
    {
        playerManager.addSongToQue(toAdd);
        //queues.addAll(songs);
        //player.addSongsToQueue(getSongs());
//       queues.addAll(getSongs());
//        System.out.println("Queue size out here " + queues.size());
//       player.addSongsToQueue(queues);
    }

    public void removeSongsFromQue(List<Song> toRemove)
    {
        playerManager.removeSongFromQue(toRemove);
    }

    public void queComboBox()
    {
        playerManager.queMisc();

    }

    public void SelectFolder(Stage stage) throws IOException, InterruptedException
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
            List<Song> toBeRenamed = sDAO.addFolder(file);

            Thread t = new Thread(new Runnable()
            {

                /*
            ???++
                 */
                @Override
                public void run()
                {
                    while (sDAO.getNumberOfUnReadySongs() != 0)
                    {
                        //System.out.println("inModel " + sDAO.getNumberOfUnReadySongs());
                        songs.addAll(toBeRenamed);
                    }
                }
            });
            t.start();

            //sdao.writeChanges();
        }

        playerManager.getAllSongs();
        for (Song s : playerManager.getAllSongs())
        {
            System.out.println(s.getTitle());
        }

        //Do something with view here
    }

    public void sendSliderForPlayback(Slider sliderPlayback)
    {
        playerManager.makeSliderForPlayBack(sliderPlayback);
    }

    public void updateSong(Song song)
    {
        playerManager.updateSong(song);
    }
    
    public void addToPlaylist(Playlist selectedPlaylist, List<Song> songSelection)
    {
        for (Playlist playlist : playlists)
        {
            if (playlist.equals(selectedPlaylist))
            {
                playlist.addSongSelection(songSelection);
            }
        }
    }
    public void addPlaylistToDB(List<Song> selectedSongs, Playlist plist)
    {
        playerManager.playlistToDB(plist, selectedSongs);
    }
    
    public void playNowSelectedSong(Song song)
    {
        playerManager.playIncomingSong(song);
    }
    
    public void changeToThisSong(Song song)
    {
        playerManager.changeToThisSong(song);
    }


    public void renamePlaylist(String title, String newTitle) throws IOException, SQLException
    {
        playerManager.renamePlaylist(title, newTitle);
    }


}
