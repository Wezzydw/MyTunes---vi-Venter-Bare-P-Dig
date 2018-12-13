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
public class Queue {

    List<Media> queue;
    List<Song> songs;

    public Queue(List<Song> songsToAdd) {
        queue = new ArrayList();
        songs = new ArrayList();
        for (Song s : songsToAdd) {
            queue.add(new Media(new File(s.getFilePath()).toURI().toString()));
        }
        songs.addAll(songsToAdd);
    }

    public Queue() {
        queue = new ArrayList();
        songs = new ArrayList();
    }

    /**
     * @param songs Tilføjer valgte sange til en queue listen, og laver dem om
     * til et media.
     */
    public void addSelection(List<Song> songs) {
        this.songs.addAll(songs);
        for (Song s : songs) {
            queue.add(new Media(new File(s.getFilePath()).toURI().toString()));
        }
    }

    /**
     *
     * @param index
     * @return indexet i queuen.
     */
    public Media getMedia(int index) {
        return queue.get(index);
    }

    /**
     *
     * @param index
     * @returnerer sangen for indexet.
     */
    public Song getSong(int index) {
        return songs.get(index);
    }

    /**
     *
     * @param index Tjekker om queuen er slut.
     */
    public boolean endOfQueue(int index) {
        if (queue.size() - 1 == index) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @returnerer længden på queue-listen.
     */
    public int queueSize() {
        return queue.size();
    }

    public void setNewQueue(List<Song> songsToSet) {
        queue.clear();
        songs.clear();
        for (Song songs : songsToSet) {
            queue.add(new Media(new File(songs.getFilePath()).toURI().toString()));
            this.songs.add(songs);
        }
    }

    public int getIndex(Song song) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).equals(song)) {
                return i;
            }
        }
        return -1;
    }
    
    public List<Song> getWholeQueue()
    {
        return songs;
    }
}
