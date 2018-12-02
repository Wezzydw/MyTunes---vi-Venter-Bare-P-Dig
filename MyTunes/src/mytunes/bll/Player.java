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
public class Player
{

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

    public Player(Playlist play)
    {

    }

    public Player()
    {
        
        queue = new Queue();
        List<Song> songs = new ArrayList();

        songs.add(new Song("Sang nummer 1", "C:\\Users\\Wezzy\\Desktop\\Musik2\\Coone  E Life   Riot (Official Music Video)[1].mp3"));
        songs.add(new Song("Sang nummer 2", "C:\\Users\\Wezzy\\Desktop\\Musik2\\Cyber   A New World (Official HQ Preview)[1].mp3"));
        songs.add(new Song("Sang nummer 3", "C:\\Users\\Wezzy\\Desktop\\Musik2\\Sephyx   Save Me (Official Video Clip)[1].mp3"));

        queue.addSelection(songs);
        bip = queue.getSong(songIndex).getFilePath();
        hit = new Media(new File(bip).toURI().toString());
        mp = new MediaPlayer(hit);
        MediaView view = new MediaView(mp);
        view.setMediaPlayer(mp);

    }

    //MÅske skal der laves noget arbejde i constructor, så det hele ikke ligger i playSong()
    //for så slipper vi for så mange checks, hvis f.eks. der bliver trykket på play knappen i træk
//    public void addSong() {
//        bip = "C:\\Users\\Wezzy\\Desktop\\Test folder\\Sephyx   Save Me (Official Video Clip)[1].mp3";
//        hit = new Media(new File(bip).toURI().toString());
//        mp = new MediaPlayer(hit);
//    }
    public void playSong()
    {

        //Temp Testen code under here
        //
        String path = "C:\\Users\\Wezzy Laptop\\Desktop\\Music\\Coone  E Life   Riot (Official Music Video)[1].mp3";
        if (mp == null)
        {

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
        mp.setOnReady(new Runnable()
        {
            @Override
            public void run()
            {

                System.out.println(mp.getMedia().getDuration().toMinutes());

            }
        });

        System.out.println(mp.getStatus());
        if (mp.getStatus() != Status.PLAYING)
        {

            mp.play();
            mp.setVolume(volume);
            System.out.println("Print index " + songIndex);
            mp.setOnEndOfMedia(new Runnable()
            {
                @Override
                public void run()
                {
                    if (queue.endOfQueue(songIndex))
                    {

                        System.out.println("Song index before " + songIndex);
                        songIndex = 0;
                        System.out.println("Song index after " + songIndex);

                    }
                    if (onRepeat)
                    {
                        System.out.println("on repeat");
                        playOnRepeat();
                        playSong();

                    } else if (shuffle)
                    {
                        songIndex = getRandom();
                        playSong();
                    } else
                    {
                        System.out.println("not on repeat");
                        nextSong();
                        playSong();

                    }

                }

            });
        }

    }

    public void pauseSong()
    {
        mp.pause();

    }

    public MediaPlayer nextSong()
    {

        Song q = queue.getSong(songIndex);
        songIndex++;
        bip = q.getFilePath();
        hit = new Media(new File(bip).toURI().toString());
        mp = new MediaPlayer(hit);

        return mp;
    }

    public void changevolume(double vol)
    {
        volume = vol;
        mp.setVolume(vol);
    }

    public void playPrevSong()
    {
        if (!shuffle)
        {
            if (songIndex == 0)
            {
                System.out.println("Here 1");
                songIndex = queue.queueSize() - 1;
            } else
            {
                System.out.println("Here 2");
                songIndex--;
            }
        } else
        {
            songIndex = getRandom();
        }
        mp.stop();
        bip = queue.getSong(songIndex).getFilePath();
        hit = new Media(new File(bip).toURI().toString());
        mp = new MediaPlayer(hit);

        System.out.println("status: " + mp.getStatus());
        playSong();

    }

    public void playNextSong()
    {
        if (!shuffle)
        {
            if (songIndex == queue.queueSize() - 1)
            {
                songIndex = 0;
            } else
            {
                songIndex++;
            }
        } else
        {
            songIndex = getRandom();
        }

        mp.stop();
        bip = queue.getSong(songIndex).getFilePath();
        hit = new Media(new File(bip).toURI().toString());
        mp = new MediaPlayer(hit);

        System.out.println("status: " + mp.getStatus());
        playSong();
    }

    public void playOnRepeat()
    {

        bip = queue.getSong(songIndex).getFilePath();
        hit = new Media(new File(bip).toURI().toString());
        mp = new MediaPlayer(hit);
//        if (onRepeat)
//        {
//            mp.setOnEndOfMedia(new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    mp.seek(Duration.ZERO);
//                    mp.play();
//                    playOnRepeat();
//                }
//            });
//        } else
//        {
//
//            playSong();
//        }

    }

    public void repeatHandler()
    {
        onRepeat = !onRepeat;
        System.out.println(onRepeat);
        //playOnRepeat();
    }

    public MediaPlayer getMediaPlayer()
    {
        return mp;
    }

    public void shuffleHandler()
    {
        shuffle = !shuffle;
        System.out.println(shuffle);
    }

    public int getRandom()
    {
        return (int) (Math.random() * queue.queueSize());
    }

}
