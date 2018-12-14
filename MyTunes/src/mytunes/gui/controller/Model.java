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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
    private BorderPane borderPane;
    private List<Song> empty;
    private List<Playlist> addPlaylist;
    private SettingsDAO setDAO;
    private double volume;
    private Long lastTime;

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
        playlists.setAll(playerManager.getAllPlaylists());
        addPlaylist = new ArrayList();
        setDAO = new SettingsDAO();
        playlistInitFilling();
        volume = 0;
        lastTime = 0L;
    }
    
    public void lookForQueue(Slider sliderPlayback)
    {
        playerManager.lookForQueue(songs, sliderPlayback);
    }

    public void onProgramClose(Stage stage)
    {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent event)
            {
                System.out.println("Ahm");
                UpdateVolume(volume);
                playerManager.currentQueueIndex();
                playerManager.currentQueueToString();
            }
        });
    }

    private void playlistInitFilling()
    {
        playlists.add(0, new Playlist("All Songs"));
        playlists.get(0).addSongSelection(playerManager.getAllSongs());
        for (int i = 1; i < playlists.size(); i++)
        {
            playlists.get(i).getSongsInPlaylist();
            playerManager.getPlaylist(playlists.get(i));
        }
        songs = FXCollections.observableArrayList(playlists.get(0).getSongsInPlaylist());
    }

    public void librarySelection(Playlist selectedPlaylist)
    {
        songs.setAll(selectedPlaylist.getSongsInPlaylist());
    }

    /**
     *
     */
    
    public void createPlaylist(Playlist plist) throws IOException
    {
        playlists.add(new Playlist(plist.getTitle()));
        playerManager.createPlaylist(plist);
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
        return songs;
    }

    public ObservableList<String> getNowPlaying()
    {
        return playerManager.getNowPlaying();
    }

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
        volume = vol /100;
        playerManager.changeVolume(vol / 100);
    }

    public void UpdateVolume(double vol)
    {
        setDAO.updateVolume(vol);
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

    public void removeSongs(List<Song> song, Playlist playlist) throws IOException
    {
        if (playlist == null)
        {
            playlist = playlists.get(0);
        }

        if (playlist.getTitle().equals(playlists.get(0).getTitle()))
        {
            sDAO.deleteSongs(song);
            pDAO.deleteSongsFromAllPlaylists(song);
        } else
        {
            pDAO.deleteSongsFromPlaylist(song, playlist);
        }

        for (Playlist p : playlists)
        {
            if (p.equals(playlist))
            {
                p.deleteSongs(song);
                songs.setAll(p.getSongsInPlaylist());
                return;
            }
        }
    }

    public void addSongToQue(ObservableList<Song> toAdd) throws IOException
    {
        playerManager.addSongToQue(toAdd);
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

        if (selectedDirectory != null)
        {
            String path = selectedDirectory.getAbsolutePath();
            File file = new File(path);
            List<Song> toBeRenamed = sDAO.addFolder(file);

            Thread t = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    boolean done = false;
                    while (done == false)
                    {
                        if (sDAO.getNumberOfUnReadySongs() == 0)
                        {
                            done = true;
                        }
                        else
                        {
                            try
                            {
                                Thread.sleep(50);
                            } catch (InterruptedException ex)
                            {
                                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    songs.addAll(toBeRenamed);
                }
            });
            t.start();
        }
    }

    public void sendSliderForPlayback(Slider sliderPlayback)
    {
        playerManager.makeSliderForPlayBack(sliderPlayback);
    }

    public void updateSong(Song song)
    {
        playerManager.updateSong(song);
        for (Playlist p : playlists)
        {
            for (Song s : p.getSongsInPlaylist())
            {
                if (s.getId() == song.getId())
                {
                    s.setAlbum(song.getAlbum());
                    s.setAuthor(song.getAuthor());
                    s.setCategori(song.getCategori());
                    s.setReleaseYear(song.getReleaseYear());
                    s.setTitle(song.getTitle());
                    songs.setAll(p.getSongsInPlaylist());
                    return;
                }
            }
        }
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

    public ObservableList<String> getSongName()
    {
        return playerManager.getNowPlaylingTitle();
    }

    public void removePlaylist(Playlist plist) throws IOException, SQLException
    {
        if (playlists.get(0).getTitle() != plist.getTitle())
        {
            playlists.remove(plist);
            playerManager.removePlaylist(plist);
        }
    }

    public void renamePlaylist(String title, String newTitle) throws IOException, SQLException
    {
        if (playlists.get(0).getTitle() != title)
        {
            playerManager.renamePlaylist(title, newTitle);
            
            for (Playlist p : playlists)
            {
                if (p.getTitle().equals(title))
                {

                    p.setTitle(newTitle);
                    List<Playlist> t = new ArrayList();
                    t.addAll(playlists);
                    playlists.setAll(t);
                    return;
                }
            }
        }
    }

    public void songEdit(Song s) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mytunes/gui/view/editSongView.fxml"));
        loader.load();
        EditSongViewController display = loader.getController();
        display.setSong(s);
        display.setModel(this);
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.showAndWait();
    }

    public void playlistEdit(Playlist p) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mytunes/gui/view/EditPlaylistView.fxml"));
        loader.load();
        EditPlaylistViewController display = loader.getController();
        display.setPlistTitle(p);
        display.setModel(this);
        Parent pa = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(pa));
        stage.showAndWait();
    }

    public void playlistClicks(Playlist playlist)
    {
        long timeDiff = 0;
        long currentTime = System.currentTimeMillis();
        if (lastTime != 0 && currentTime != 0)
        {
            timeDiff = currentTime - lastTime;
            if (timeDiff <= 215)
            {
                librarySelection(playlist);
            }
        }
        lastTime = currentTime;
    }

    public void queueClicks(Song song)
    {
        long timeDiff = 0;
        long currentTime = System.currentTimeMillis();

        if (lastTime != 0 && currentTime != 0)
        {
            timeDiff = currentTime - lastTime;
            if (timeDiff <= 215)
            {
                changeToThisSong(song);
            }
        }
        lastTime = currentTime;
    }

    public void songListClicks(Song song)
    {
        long timeDiff = 0;
        long currentTime = System.currentTimeMillis();

        if (lastTime != 0 && currentTime != 0)
        {
            timeDiff = currentTime - lastTime;
            if (timeDiff <= 215)
            {
                playNowSelectedSong(song);
            }
        }
        lastTime = currentTime;
    }

    public void tryCreatePlaylist()
    {
        TextField txtTitle = new TextField();
        txtTitle.setText("Playlist name");
        Button btn = new Button();
        btn.setText("Create playlist");
        StackPane root = new StackPane();
        root.setAlignment(txtTitle, Pos.TOP_CENTER);
        root.setAlignment(btn, Pos.BOTTOM_CENTER);
        root.getChildren().addAll(txtTitle, btn);
        Scene scene = new Scene(root, 200, 50);
        Stage stage = new Stage();
        stage.setTitle("create playlist");
        stage.setScene(scene);
        stage.show();
        btn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                try
                {
                    createPlaylist(new Playlist(txtTitle.getText()));
                } catch (IOException ex)
                {
                    System.out.println("fejl 1111111");
                }
                Stage stage = (Stage) txtTitle.getScene().getWindow();
                stage.close();
            }
        });
    }
}
