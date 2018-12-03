/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import com.sun.jndi.toolkit.url.Uri;
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
    
    
    public void addSelection(List<Song> songs)
    {
        int i = 0;
        this.songs = songs;
        for (Song s : songs)
        {
            System.out.println(i);
            queue.add(new Media(new File(s.getFilePath()).toURI().toString()));
            i++;
            
        }
        
    }
    
    public void removeSelection()
    {
        
    }
    
    public Media getMedia(int index)
    {
        return queue.get(index);
    }
    
    public Song getSong(int index)
    {
        return songs.get(index);
    }
    
    public boolean endOfQueue(int index)
    {
        if (queue.size()-1 == index)
            return true;
        else return false;
    }
    
    public int queueSize()
    {
        return queue.size();
    }
    
    public List<Media> getAllSongs()
    {
        return queue;
    }
    
    public Song getMediaSong(Media m)
    {
        for (Song s : songs)
        {
            //queue.add(new Media(new File(s.getFilePath()).toURI().toString()));
//            Uri u = Uri.parse(s.getFilePath());
//            s.getFilePath().Uri.parse();
//            s.getFilePath().to
            
            if(new File(s.getFilePath()).toURI().toString().equals( m.getSource()))
            {
                System.out.println("to be sure");
                return s;
            }
        }
        return null;
    }
    

            
}
