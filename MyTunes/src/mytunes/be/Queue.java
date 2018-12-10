/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.media.Media;

/**
 *
 * @author Wezzy
 */
public class Queue
{

    List<Media> queue = new ArrayList();
    List<Song> songs = new ArrayList();
    
    /**
     * @param songs
     * Tilføjer valgte sange til en queue listen, og laver dem om til et media.
     */
    public void addSelection(List<Song> songs)
    {
        this.songs = songs;
        for (Song s : songs)
        {
            queue.add(new Media(new File(s.getFilePath()).toURI().toString()));
        }
    }
    
    /**
     * fjerner valgte sange fra listen.
     */
    public void removeSelection()
    {

    }
    
    /**
     * 
     * @param index
     * @return indexet i queuen.
     */
    public Media getMedia(int index)
    {
        return queue.get(index);
    }
    
    /**
     * 
     * @param index
     * @returnerer sangen for indexet.
     */
    public Song getSong(int index)
    {
        return songs.get(index);
    }
    
    /**
     * 
     * @param index
     * Tjekker om queuen er slut.
     */
    public boolean endOfQueue(int index)
    {
        if (queue.size() - 1 == index)
        {
            return true;
        } else
        {
            return false;
        }
    }
    
    /**
     * 
     * @returnerer længden på queue-listen.
     */
    public int queueSize()
    {
        return queue.size();
    }
    
    /**
     * @returnerer alle elementer i queue-listen.
     */
    public List<Media> getAllSongs()
    {
        return queue;
    }
    
    /**
     * 
     * @param m
     * @returnerer et sang-object som et medie.
     */
    public Song getMediaSong(Media m)
    {
        for (Song s : songs)
        {
            //queue.add(new Media(new File(s.getFilePath()).toURI().toString()));
//            Uri u = Uri.parse(s.getFilePath());
//            s.getFilePath().Uri.parse();
//            s.getFilePath().to

            if (new File(s.getFilePath()).toURI().toString().equals(m.getSource()))
            {
                System.out.println("to be sure");
                return s;
            }
        }
        return null;
    }

}
