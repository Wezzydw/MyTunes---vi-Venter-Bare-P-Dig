/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import mytunes.be.Playlist;
import mytunes.be.Queue;
import mytunes.be.Song;

/**
 *
 * @author andreas
 */
public class SettingsDAO
{
    public double lastSetVolume()
    {
        
        return 2.11;
    }
    public Song lastPlayedSong()
    {
        return null;
    }
    public Playlist lastPlayedPlaylist()
    {
        return null;
    }
    public Queue lastPlayedQueue()
    {
        return null;
    }
    
}
