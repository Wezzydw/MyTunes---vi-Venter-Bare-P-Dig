/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.util.List;

/**
 *
 * @author Wezzy Laptop
 */
public class Playlist {
    
    String title;
    List<Song> playlist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Song> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Song> playlist) {
        this.playlist = playlist;
    }

    public Playlist(String title) {
        this.title = title;
        //this.playlist = playlist;
    }
    
    public void addSong(Song song)
    {
        playlist.add(song);
    }
    
    public void deleteSong(Song song)
    {
        playlist.remove(song);
    }
    
    public Song getSong(int i)
    {
        return playlist.get(i);
    }
    
    public int getSize()
    {
        return playlist.size();
    }

    public String getSongId()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getFilePath()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}