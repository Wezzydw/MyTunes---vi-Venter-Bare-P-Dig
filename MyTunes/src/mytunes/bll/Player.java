/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import mytunes.be.Playlist;
import mytunes.be.Queue;
import mytunes.be.Song;

/**
 *
 * @author Wezzy Laptop
 */
public class Player {

    private MediaPlayer mp;
    private Queue queue;
    private int songIndex;
    private boolean onRepeat = false;
    private double volume;
    private boolean shuffle = false;

    public Player(Playlist play) {

    }

    public Player() {
        //Tmp tester code, skal slettes når db kan sende en queue
        queue = new Queue();
        List<Song> songs = new ArrayList();
        songs.add(new Song("Sang nummer 1", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Coone  E Life   Riot (Official Music Video)[1].mp3", 1));
        songs.add(new Song("Sang nummer 2", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Cyber   A New World (Official HQ Preview)[1].mp3", 2));
        songs.add(new Song("Sang nummer 3", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Sephyx   Save Me (Official Video Clip)[1].mp3",3));
        queue.addSelection(songs);
        mp = new MediaPlayer(queue.getMedia(songIndex));
        
<<<<<<< HEAD
//        for (Media me : queue.getAllSongs()) {
//
//            me.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
//                
//                //System.out.println(change.getValueAdded());
//                //System.out.println(change.getKey());
////                System.out.println(change.getKey());
//                if(change.getKey() == "title")
//                {
//                    
//                    String a = (String) me.getMetadata().get("album");
//                    queue.getMediaSong(me).setAlbum(a);
////                    System.out.println(me.getSource().toString());
////                    System.out.println(queue.queueSize());
//                    System.out.println(me.getMetadata().get("title"));
//                    
//                    
//                }
//
//    });}
    }
=======
        for (Media me : queue.getAllSongs()) {

            me.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
                
                //System.out.println(change.getValueAdded());
                //System.out.println(change.getKey());
//                System.out.println(change.getKey());
                if(change.getKey() == "title")
                {
                    
                    String a = (String) me.getMetadata().get("album");
                    queue.getMediaSong(me).setAlbum(a);
//                    System.out.println(me.getSource().toString());
//                    System.out.println(queue.queueSize());
                    System.out.println(me.getMetadata().get("title"));
                    
                }

    });}}
>>>>>>> parent of fb8ccbb... Update Player.java

    public void playSong() {
        
        if (mp.getStatus() != Status.PLAYING) {

            mp.play();
            System.out.println(mp.getStatus());
            mp.setVolume(volume);
            mp.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    if (queue.endOfQueue(songIndex)) {

                        songIndex = 0;
                    }
                    if (onRepeat) {
                        playOnRepeat();
                        playSong();

                    } else if (shuffle) {
                        songIndex = getRandom();
                        playSong();
                    } else {
                        nextSong();
                        playSong();
                    }
                }
            });
        }
    }

    public void pauseSong() {
        mp.pause();
    }

    public MediaPlayer nextSong() {
        songIndex++;
        mp = new MediaPlayer(queue.getMedia(songIndex));
        return mp;
    }

    public void changevolume(double vol) {
        volume = vol;
        mp.setVolume(vol);
    }

    public void playPrevSong() {
        if (!shuffle) {
            if (songIndex == 0) {
                songIndex = queue.queueSize() - 1;
            } else {
                songIndex--;
            }
        } else {
            songIndex = getRandom();
        }
        mp.stop();
        mp = new MediaPlayer(queue.getMedia(songIndex));
        playSong();
    }

    public void playNextSong() {
        if (!shuffle) {
            if (songIndex == queue.queueSize() - 1) {
                songIndex = 0;
            } else {
                songIndex++;
            }
        } else {
            songIndex = getRandom();
        }
        mp.stop();
        mp = new MediaPlayer(queue.getMedia(songIndex));
        playSong();
    }

    public void playOnRepeat() {
        mp = new MediaPlayer(queue.getMedia(songIndex));
    }

    public void repeatHandler() {
        onRepeat = !onRepeat;
    }

    public MediaPlayer getMediaPlayer() {
        return mp;
    }

    public void shuffleHandler() {
        shuffle = !shuffle;
    }

    public int getRandom() {
        return (int) (Math.random() * queue.queueSize());
    }

}
