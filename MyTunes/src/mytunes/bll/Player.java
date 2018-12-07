/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
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

    private MediaPlayer mp;
    private Queue queue;
    private int songIndex;
    private boolean onRepeat = false;
    private double volume;
    private boolean shuffle = false;
    private ObservableList<String> nowPlaying;

    private Slider sliderPlayback;

    public Player(List<Song> songs) {
        System.out.println("tester");
        queue = new Queue();
        queue.addSelection(songs);
        mp = new MediaPlayer(queue.getMedia(songIndex));
        nowPlaying = FXCollections.observableArrayList();
        nowPlaying();

    }

    public void playSong() {
        System.out.println("status" + mp.getStatus());
        if (mp.getStatus() != Status.PLAYING) {

            mp.play();
            System.out.println(mp.getStatus());
            mp.setVolume(volume);
            nowPlaying();
            System.out.println("total duration" + mp.getTotalDuration().toSeconds());

            mp.setOnReady(new Runnable() {
                @Override
                public void run() {
                    sliderPlayback.setMax(mp.getTotalDuration().toSeconds());
                    sliderPlayback.setBlockIncrement(mp.getTotalDuration().toSeconds());
                }
            });

            sliderPlayback.setOnMouseClicked((MouseEvent event) -> {
                mp.seek(Duration.seconds(sliderPlayback.getValue()));

            });
            mp.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
                sliderPlayback.setValue(newValue.toSeconds());
            });

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

    private MediaPlayer nextSong() {
        songIndex++;
        mp = new MediaPlayer(queue.getMedia(songIndex));
        mp.pause();
        return mp;
    }

    public void changevolume(double vol) {
        System.out.println("incoming vol " + vol);
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

    private void playOnRepeat() {
        mp = new MediaPlayer(queue.getMedia(songIndex));
    }

    public void repeatHandler() {
        onRepeat = !onRepeat;
    }

    public void shuffleHandler() {
        shuffle = !shuffle;
    }

    private int getRandom() {
        return (int) (Math.random() * queue.queueSize());
    }

    public void makeView(MediaView mv) {
        mv = new MediaView(mp);
    }

    public void addSongsToQueue(List<Song> songs) {
        queue.addSelection(songs);
    }

    public void nowPlaying() {
        System.out.println("nowplaying and songIndex : " + songIndex);
        nowPlaying.clear();
        nowPlaying.addAll(getMetaData(queue.getSong(songIndex)));
        System.out.println("getNowPlaying" + nowPlaying.get(0));
    }

    public List<String> getMetaData(Song son) {
        List<String> MetaList = FXCollections.observableArrayList();
        System.out.println("in getmetadata");
        if (son.getTitle() != null) {
            MetaList.add("title;" + son.getTitle());
        }
        if (son.getAuthor() != null) {
            MetaList.add("author;" + son.getAuthor());
        }
        if (son.getCategori() != null) {
            MetaList.add("categori;" + son.getCategori());
        }
        if (son.getReleaseYear() != null) {
            MetaList.add("releaseyear;" + son.getReleaseYear());
        }
        if (son.getAlbum() != null) {
            MetaList.add("album;" + son.getAlbum());
        }
        if (son.getLength() != null) {
            MetaList.add("length;" + son.getLength());
        }

        return MetaList;
    }

    public ObservableList<String> getNowPlaying() {
        System.out.println("getNowPlaying" + nowPlaying.get(0));
        return nowPlaying;
    }

    public void makeSliderForPlayback(Slider sliderPlayback) {
        this.sliderPlayback = sliderPlayback;
        //this.sliderPlayback.setMax(mp.getTotalDuration().toSeconds());
    }

}
