/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author Wezzy Laptop
 */
public class Player {

    String bip;
    Media hit;
    MediaPlayer mp;
    MediaPlayer currentPlayer;
    Status currentStatus;

    public Player(Playlist play) {
        
    }

    public Player()
    {
        
    }

    public void addSong() {
        bip = "C:\\Users\\Wezzy\\Desktop\\Test folder\\Sephyx   Save Me (Official Video Clip)[1].mp3";
        hit = new Media(new File(bip).toURI().toString());
        mp = new MediaPlayer(hit);
    }

    public void playSong() {
        //String a = (String) mp.getMedia().getMetadata().get("album");
        //Her er det gode shit.
        hit.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            String a = (String) mp.getMedia().getMetadata().get("album");
            System.out.println(a);
        });
        
        //System.out.println(a);
      mp.play();
    }

    public void pauseSong() {
        mp.pause();
    }
    public void nextSong()
    {
        
    }
}
