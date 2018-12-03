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
import javafx.scene.media.MediaView;
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
    boolean onRepeat = false;
    double volume;
    boolean shuffle = false;
    int i = 0;
    int j = 0;

    public Player(Playlist play) {

    }

    public Player() {

        queue = new Queue();
        List<Song> songs = new ArrayList();
        songs.add(new Song("Sang nummer 1", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Coone  E Life   Riot (Official Music Video)[1].mp3", 1));
        songs.add(new Song("Sang nummer 2", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Cyber   A New World (Official HQ Preview)[1].mp3", 2));
        songs.add(new Song("Sang nummer 3", "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Sephyx   Save Me (Official Video Clip)[1].mp3",3));
        queue.addSelection(songs);
        mp = new MediaPlayer(queue.getMedia(songIndex));
    }

    public void playSong() {
        if (mp.getStatus() != Status.PLAYING) {

            mp.play();
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
