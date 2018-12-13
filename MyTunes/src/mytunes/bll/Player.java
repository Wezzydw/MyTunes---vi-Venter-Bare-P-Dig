/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
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
    private ObservableList<String> nowPlayingTitle;

    public Player(List<Song> songs) {
        queue = new Queue(songs);
        mp = new MediaPlayer(queue.getMedia(songIndex));
        nowPlaying = FXCollections.observableArrayList();
        nowPlayingTitle = FXCollections.observableArrayList();
        nowPlaying();
    }

    public Player(Song song) {
        mp = new MediaPlayer(new Media(new File(song.getFilePath()).toURI().toString()));
        nowPlaying = FXCollections.observableArrayList();
        nowPlayingTitle = FXCollections.observableArrayList();
        nowPlayingFromInterrupt(song);
        queue = new Queue();
    }

    /**
     * Denne metode står for at afspille en sang. SetOnReady laves hver gang
     * sang afspilles, da mediaplayeren loader langsommere end koden. Der
     * tjekkes om en sang er færdig, hvis den er, afspilles den næste
     * automatisk, hvis ikke shuffle eller repeat er aktiveret.
     */
    public void playSong() {
        if (mp.getStatus() != Status.PLAYING && queue.queueSize() > 0) {
            mp.play();
            nowPlaying();

            mp.setOnReady(new Runnable() {
                @Override
                public void run() {
                    sliderPlayback.setMax(mp.getTotalDuration().toSeconds());
                    sliderPlayback.setBlockIncrement(mp.getTotalDuration().toSeconds());
                }
            });

            sliderPlayback.setOnMouseClicked((MouseEvent event)
                    -> {
                mp.seek(Duration.seconds(sliderPlayback.getValue()));

            });
            
            mp.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
                    -> {
                sliderPlayback.setValue(newValue.toSeconds());
            });

            mp.setOnEndOfMedia(new Runnable() {
                @Override

                public void run() {
                    mp.stop();

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

        if (queue.endOfQueue(songIndex)) {
            songIndex = 0;
        } else {
            songIndex++;
        }
        mp = new MediaPlayer(queue.getMedia(songIndex));
        mp.pause();
        return mp;
    }

    /**
     * @param vol Skifter volumen i mediaplayeren
     */
    public void changevolume(double vol) {
        mp.setVolume(vol);
    }

    /**
     * Spiller forrige sang. Der tjekkes at shuffle ikke er aktiveret. Hvis
     * ikke, går den en plads tilbage i listen og afspiller elementet på denne
     * plads.
     */
    public void playPrevSong() {
        if (queue.queueSize() > 0) {
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
    }

    /**
     * Spiller næste sang. Der tjekkes at shuffle ikke er aktiveret. Hvis enden
     * af køen er nået, starter queuen forfra. Ellers afspilles elementet af
     * næste index.
     *
     */
    public void playNextSong() {

        if (queue.queueSize() > 0) {
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
    }

    /**
     * Sætter mediaplayeren til at afspille samme element i det index den er
     * nået.
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
        if (queue != null) {
            nowPlaying.clear();
            nowPlaying.addAll(getMetaData(queue.getSong(songIndex)));
            nowPlayingTitle.clear();
            nowPlayingTitle.addAll(getTitle(queue.getSong(songIndex)));
        }
    }

    public void nowPlayingFromInterrupt(Song song) {
        nowPlaying.clear();
        nowPlaying.setAll(getMetaData(song));
        nowPlayingTitle.clear();
        nowPlayingTitle.setAll(getTitle(song));
    }

    public List<String> getTitle(Song titleSong) {
        List<String> MetaList = FXCollections.observableArrayList();
        if (titleSong.getTitle() != null) {
            MetaList.add(titleSong.getTitle());
        }
        return MetaList;
    }

    /**
     * @param Song
     * @returnerer en MetaList Tjekker at title, author, categori, releaseyear,
     * album og length ikke er null. Hvis disse ikke er null, tilføjes de
     * metalisten.
     */
    public List<String> getMetaData(Song son) {
        List<String> MetaList = FXCollections.observableArrayList();
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
        return nowPlaying;
    }

    public ObservableList<String> getNowPlayingTitle() {
        return nowPlayingTitle;
    }

    /**
     * @param sliderPlayback = progress bar så brugeren kan se hvor lang, og
     * tilbageværende tid af mediet.
     */
    public void makeSliderForPlayback(Slider sliderPlayback) {
        this.sliderPlayback = sliderPlayback;
    }

    public void removeSongsFromQueue(List<Song> songsToBeRemoved) {
        queue.setNewQueue(songsToBeRemoved);
        if (songIndex > queue.queueSize()) {
            songIndex = 0;
            mp = new MediaPlayer(queue.getMedia(songIndex));
        }
    }

    public void playIncomingSong(Song song) {
        mp.stop();
        mp = new MediaPlayer(new Media(new File(song.getFilePath()).toURI().toString()));
        mp.setOnReady(new Runnable() {

            @Override
            public void run() {
                nowPlayingFromInterrupt(song);
                mp.play();
                sliderPlayback.setMax(mp.getTotalDuration().toSeconds());
                sliderPlayback.setBlockIncrement(mp.getTotalDuration().toSeconds());
                sliderPlayback.setOnMouseClicked((MouseEvent event)
                        -> {
                    mp.seek(Duration.seconds(sliderPlayback.getValue()));

                });
                mp.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
                        -> {
                    sliderPlayback.setValue(newValue.toSeconds());
                });

                mp.setOnEndOfMedia(new Runnable() {
                    @Override

                    public void run() {
                        mp = new MediaPlayer(queue.getMedia(songIndex));
                        playSong();
                    }
                });
            }
        });
    }

    public void changeToThisSong(Song song) {
        if (mp.getStatus() == Status.PLAYING) {
            mp.stop();
            mp.setOnStopped(new Runnable() {
                @Override
                public void run() {
                    songIndex = queue.getIndex(song);
                    playSong();
                }
            });;
        } else {
            songIndex = queue.getIndex(song);
            playSong();
        }
    }

}
