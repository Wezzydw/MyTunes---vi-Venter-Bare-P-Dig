/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import mytunes.be.Playlist;
import mytunes.be.Queue;
import mytunes.be.Song;

/**
 *
 * @author Wezzy Laptop
 */
public class Player {

    String FOLDER = "C:\\Users\\Wezzy\\Desktop\\Test folder\\";
    String bip;
    Media hit;
    MediaPlayer mp;
    MediaPlayer currentPlayer;
    Status currentStatus;
    Queue queue;
    int songIndex;

    public Player(Playlist play) {

    }

    public Player() {
           queue = new Queue();
    }

//    public void addSong() {
//        bip = "C:\\Users\\Wezzy\\Desktop\\Test folder\\Sephyx   Save Me (Official Video Clip)[1].mp3";
//        hit = new Media(new File(bip).toURI().toString());
//        mp = new MediaPlayer(hit);
//    }
    public void playSong() {

        //Temp Testen code under here
        List<Song> songs = new ArrayList();
        songs.add(new Song("Sang nummer 1", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Coone  E Life   Riot (Official Music Video)[1].mp3"));
        songs.add(new Song("Sang nummer 2", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Cyber   A New World (Official HQ Preview)[1].mp3"));
        songs.add(new Song("Sang nummer 3", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Sephyx   Save Me (Official Video Clip)[1].mp3"));
        
        queue.addSelection(songs);
        
        //
        if (mp == null) {
            nextSong();
        }
        //String a = (String) mp.getMedia().getMetadata().get("album");
        //Her er det gode shit.
//        hit.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
//            String a = (String) mp.getMedia().getMetadata().get("album");
//            System.out.println(a);
//        });
//        
        //System.out.println(a);
        if (mp.getStatus() != Status.PLAYING) {
            mp.play();
            mp.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    nextSong();
                    playSong();
                }

            });
        }

    }

    public void pauseSong() {
        mp.pause();
    }

    public MediaPlayer nextSong() {
        
        Song q = queue.getSong(songIndex);
        songIndex++;
        bip = q.getFilePath();
        hit = new Media(new File(bip).toURI().toString());
        mp = new MediaPlayer(hit);

        return mp;
    }

    public void changevolume(double vol) {

        mp.setVolume(vol);
    }
}
