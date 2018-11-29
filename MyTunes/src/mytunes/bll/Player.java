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
    
    public void playSong()
    {
        String bip = "C:/Users/Wezzy Laptop/Desktop/Music/Sephyx   Save Me (Official Video Clip)[1].mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mp = new MediaPlayer(hit);
        mp.play();
    }
}
