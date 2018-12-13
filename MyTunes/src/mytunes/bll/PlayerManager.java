/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.IOException;
import java.sql.SQLException;
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
public class PlayerManager {

    private ObservableList<Song> songQueue;
    private ObservableList<String> nowPlaying;
    private ObservableList<Playlist> playlists;
    private ObservableList<String> getSongName;
    private ComboBox<String> comboBoxMisc;
    private SongDAO sdao;
    private PlaylistDAO pDAO;
    private Player player;
    private SettingsDAO setdao;
    private double volumeFromDB;
    private Slider sliderPlayback;

    public PlayerManager() throws IOException {
        songQueue = FXCollections.observableArrayList();
        nowPlaying = FXCollections.observableArrayList();
        getSongName = FXCollections.observableArrayList();
        setdao = new SettingsDAO();
        sdao = new SongDAO();
        pDAO = new PlaylistDAO();
        songQueue.addAll(setdao.queueList());
        checkForSongsSomewhere();
        playlists = FXCollections.observableArrayList();
        playlists.addAll(getSavedPlaylists());
    }

    public List<Playlist> getSavedPlaylists() {
        return pDAO.getAllPlaylists();
    }

    private void checkForSongsSomewhere() {
        if (player == null) {
            if (!songQueue.isEmpty()) {
                player = new Player(songQueue);
                player.changevolume(volumeFromDB);
                nowPlaying.addAll(player.getNowPlaying());
                getSongName.addAll(player.getNowPlayingTitle());
                player.makeSliderForPlayback(sliderPlayback);
            }
        }
    }

    public ObservableList<Song> getQueuedSongs() {
        return songQueue;
    }

    /*
        returner den sang som spiller lige nu
     */
    public ObservableList<String> getNowPlaying() {
        if (player == null) {
            return nowPlaying;
        } else {
            return player.getNowPlaying();
        }
    }

    public ObservableList<String> getNowPlaylingTitle() {
        if (player == null) {
            return getSongName;
        } else {
            return player.getNowPlayingTitle();
        }
    }

    /*
        Tilføjer den valgte sang til queuedsongs
     */
    public void addSongToQue(List<Song> songs) {
        if (player != null) {
            player.addSongsToQueue(songs);
        }
        songQueue.addAll(songs);
        checkForSongsSomewhere();
    }

    /*
        fjerner den valgte sang fra Queuen
     */
    public void removeSongFromQue(List<Song> toRemove) {
        for (int i = toRemove.size() - 1; i >= 0; i--) {
            songQueue.remove(toRemove.get(i));
        }
        player.removeSongsFromQueue(songQueue);
    }

    /*
        giver muligheden for at ændrer opsætningen af ens playlist
     */
    public void queMisc() {
        comboBoxMisc.setItems(FXCollections.observableArrayList("reverseList", "randomiseList", "sortByTitle"));
        comboBoxMisc.setVisibleRowCount(3);
    }


    /*
    Henter alle playlister ned
     */
    public List<Playlist> getAllPlaylists() {
        return playlists;
    }

    /*
    Henter alle sange ned
     */
    public List<Song> getAllSongs() {
        if (sdao.getAllSongsFromDB() != null) {
            return sdao.getAllSongsFromDB();
        } else {
            return null;
        }
    }

    public void updateSong(Song song) {
        sdao.updateSong(song);
    }

    /*
    Afspiller en sang, hvis en sang ikke spilles.
     */
    public void playSong() {
        if (player != null) {
            player.playSong();
        }
    }

    /*
    Sætter den sang der spiller på pause
     */
    public void pauseSong() {
        if (player != null) {
            player.pauseSong();
        }
    }

    /*
    ændrer volumen på lyden er sat på ved brug af en slider
     */
    public void changeVolume(double vol) {
        if (player != null) {
            player.changevolume(vol);
        } else {
            volumeFromDB = vol * 100;
        }
    }

    /*
    går 1 sang tilbage og afspiller den igen
     */
    public void playPrevSong() {
        if (player != null) {
            player.playPrevSong();
        }
    }

    /*
    Går videre til næste sang og afspiller den
     */
    public void playNextSong() {
        if (player != null) {
            player.playNextSong();
        }
    }

    /*
    Får den nuværende sang til at afspille igen
     */
    public void repeatHandler() {
        if (player != null) {
            player.repeatHandler();
        }
    }

    /*
    Kalder Shuffle metoden, som gør at playlisten bliver randomised ved sang
    skift
     */
    public void shuffleHandler() {
        if (player != null) {
            player.shuffleHandler();
        }
    }

    /*
    kalder slideren der viser hvor langt sangens spilletid er nået
     */
    public void makeSliderForPlayBack(Slider sliderPlayback) {
        this.sliderPlayback = sliderPlayback;
    }

    public void playlistToDB(Playlist plist, List<Song> selectedSongs) {
        pDAO.addSelection(selectedSongs, plist);
    }

    public void playIncomingSong(Song song) {
        if (player == null) {
            player = new Player(song);
            player.makeSliderForPlayback(sliderPlayback);
        }
        player.playIncomingSong(song);
    }

    public void changeToThisSong(Song song) {
        player.changeToThisSong(song);
    }

    public void renamePlaylist(String title, String newTitle) throws IOException, SQLException {
        pDAO.renamePlaylist(title, newTitle);
    }

    public void createPlaylist(Playlist plist) throws IOException {
        pDAO.createPlaylist(plist);
    }

    public void removePlaylist(Playlist plist) throws IOException, SQLException {
        pDAO.deletePlaylist(plist.getTitle());
    }
    
    public void currentQueueToString()
    {
        String ids = "";
        for(Song s : player.getWholeQueue())
        {
            ids += "," + s.getId();
        }
        
    }
}
