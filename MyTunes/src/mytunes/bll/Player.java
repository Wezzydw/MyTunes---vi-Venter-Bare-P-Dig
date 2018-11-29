/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    public Player() {

    }

    public void playSong() {
        bip = "C:/Users/Wezzy Laptop/Desktop/Music/Sephyx   Save Me (Official Video Clip)[1].mp3";
        hit = new Media(new File(bip).toURI().toString());
        mp = new MediaPlayer(hit);
        System.out.println(mp.getMedia().getMetadata().isEmpty());
        mp.play();
        //mp.getCurrentTime()
        currentPlayer = mp;        
    }

    public void pauseSong() {
        
        mp.pause();
    }
}
