/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author Wezzy Laptop
 */
public class PlaylistDAO {

    public Playlist addSelection() {
        return null;
    }

    public Playlist mergePlaylist(Playlist p) {
        return null;
    }
    
    public Playlist getPlaylist(String title)
    {
        return null;
    }
    public void removeSelection(List<Song> toBeRemoved)
    {
        
    }
    public List<Playlist> getAllPlaylists()
    {
        return null;
    }
    
    public void deletePlaylist()
    {
        
    }
    public void renamePlaylist(String title, String newTitle )
    {
        Playlist p = getPlaylist(title);
        p.setTitle(newTitle);
    
    }

}
