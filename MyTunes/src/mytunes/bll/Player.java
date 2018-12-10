/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
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
    
    /**
     * Denne metode står for at afspille en sang. 
     * SetOnReady laves hver gang sang afspilles, da mediaplayeren loader langsommere
     * end koden.
     * Der tjekkes om en sang er færdig, hvis den er, afspilles den næste automatisk,
     * hvis ikke shuffle eller repeat er aktiveret.
     */
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
    
    
    /**
     * Sætter mediaplayeren på pause.
     */
    public void pauseSong() {
        mp.pause();
    }
    
    
    /**
     * Går til element i næste index, og fylder i mediaplayeren.
     */
    private MediaPlayer nextSong() {
        songIndex++;
        mp = new MediaPlayer(queue.getMedia(songIndex));
        mp.pause();
        return mp;
    }
    
    /**
     * @param vol 
     * Skifter volumen i mediaplayeren
     */
    public void changevolume(double vol) {
        System.out.println("incoming vol " + vol);
        volume = vol;
        mp.setVolume(vol);
    }
    
    
    /**
     * Spiller forrige sang.
     * Der tjekkes at shuffle ikke er aktiveret.
     * Hvis ikke, går den en plads tilbage i listen og afspiller elementet på denne plads.
     */
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
    
    /**
     * Spiller næste sang.
     * Der tjekkes at shuffle ikke er aktiveret.
     * Hvis enden af køen er nået, starter queuen forfra.
     * Ellers afspilles elementet af næste index.
     * 
     */
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
    
    /**
     * Sætter mediaplayeren til at afspille samme element i det index den er nået.
     */
    private void playOnRepeat() {
        mp = new MediaPlayer(queue.getMedia(songIndex));
    }
    
    /**
     * Toggle repeat.
     */
    public void repeatHandler() {
        onRepeat = !onRepeat;
    }
    
    /**
     * Toggle shuffle.
     */
    public void shuffleHandler() {
        shuffle = !shuffle;
    }
    
    /**
     * @returnerer en tilfældig sang fra queue listen.
     */
    private int getRandom() {
        return (int) (Math.random() * queue.queueSize());
    }
    
    /**
     * @param songs Tilføjer en, eller valgte sange til queue-listen.
     */
    public void addSongsToQueue(List<Song> songs) {
        queue.addSelection(songs);
    }
    
    /**
     * Viser metadata fra igangværende sang.
     */
    public void nowPlaying() {
        System.out.println("nowplaying and songIndex : " + songIndex);
        nowPlaying.clear();
        nowPlaying.addAll(getMetaData(queue.getSong(songIndex)));
        System.out.println("getNowPlaying" + nowPlaying.get(0));
    }
    
    /**
     * @param Song
     * @returnerer en MetaList
     * Tjekker at title, author, categori, releaseyear, album og length ikke er null.
     * Hvis disse ikke er null, tilføjes de metalisten.
     */
    public List<String> getMetaData(Song son) {
        List<String> MetaList = FXCollections.observableArrayList();
        System.out.println("in getmetadata");
        if (son.getTitle() != null) {
            MetaList.add(son.getTitle());
        }
        if (son.getAuthor() != null) {
            MetaList.add(son.getAuthor());
        }
        if (son.getCategori() != null) {
            MetaList.add(son.getCategori());
        }
        if (son.getReleaseYear() != null) {
            MetaList.add(son.getReleaseYear());
        }
        if (son.getAlbum() != null) {
            MetaList.add(son.getAlbum());
        }
        if (son.getLength() != null) {
            MetaList.add(son.getLength());
        }

        return MetaList;
    }
    
    /**
     * @returnerer index 0.
     */
    public ObservableList<String> getNowPlaying() {
        System.out.println("getNowPlaying" + nowPlaying.get(0));
        return nowPlaying;
    }
    
    /**
     * @param sliderPlayback = progress bar så brugeren kan se hvor lang, og tilbageværende
     * tid af mediet.
     */
    public void makeSliderForPlayback(Slider sliderPlayback) {
        this.sliderPlayback = sliderPlayback;
        //this.sliderPlayback.setMax(mp.getTotalDuration().toSeconds());
    }

}
