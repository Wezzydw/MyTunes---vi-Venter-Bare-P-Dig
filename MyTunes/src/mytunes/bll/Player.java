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
        //
        String path =  "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Coone  E Life   Riot (Official Music Video)[1].mp3";
        if (mp == null) {
            List<Song> songs = new ArrayList();
            
            songs.add(new Song("Sang nummer 1", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Coone  E Life   Riot (Official Music Video)[1].mp3"));
            songs.add(new Song("Sang nummer 2", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Cyber   A New World (Official HQ Preview)[1].mp3"));
            songs.add(new Song("Sang nummer 3", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Sephyx   Save Me (Official Video Clip)[1].mp3"));

            queue.addSelection(songs);
            bip = queue.getSong(songIndex).getFilePath();
            hit = new Media(new File(bip).toURI().toString());
            mp = new MediaPlayer(hit);
        }
        //String a = (String) mp.getMedia().getMetadata().get("album");
        //Her er det gode shit.
//        hit.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
//            int a = (int) mp.getMedia().getMetadata().get("duration");
//            //double b = hit.getDuration().toHours();
//            Duration b = hit.getDuration();
//            File file = new File(path);
//            
//            System.out.println(file.getPath());
//            File test = new File(file.toString());
//            System.out.println(test.toPath());
//            
           // System.out.println(a);
//            
//        });
//        
        //System.out.println(a);
        mp.setOnReady(new Runnable() {
            @Override
            public void run() {
                
                System.out.println(mp.getMedia().getDuration().toMinutes());
        
            }
            });
        
        
        System.out.println(mp.getStatus());
        if (mp.getStatus() != Status.PLAYING) {

            mp.play();
            System.out.println("Print index " + songIndex);
            mp.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    if (queue.endOfQueue(songIndex)) {

                        System.out.println("Song index before " + songIndex);
                        songIndex = 0;
                        System.out.println("Song index after " + songIndex);
                    }
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

    public void prevSong() {
        if (songIndex == 0) {
            System.out.println("Here 1");
            songIndex = queue.queueSize() - 1;
        } else {
            System.out.println("Here 2");
            songIndex--;
        }
        mp.stop();
        bip = queue.getSong(songIndex).getFilePath();
        hit = new Media(new File(bip).toURI().toString());
        mp = new MediaPlayer(hit);

        System.out.println("status: " + mp.getStatus());
        playSong();

    }

    public void playNextSong() {
        if (songIndex == queue.queueSize() - 1) {
            songIndex = 0;
        } else {
            songIndex++;
        }

        mp.stop();
        bip = queue.getSong(songIndex).getFilePath();
        hit = new Media(new File(bip).toURI().toString());
        mp = new MediaPlayer(hit);

        System.out.println("status: " + mp.getStatus());
        playSong();
    }
}
