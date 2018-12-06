/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Queue;
import mytunes.be.Song;
import mytunes.bll.Player;

/**
 *
 * @author andreas
 */
public class SettingsDAO
{
    DatabaseConnection conProvider;
    Player player;
    SongDAO sDAO;
    public void updateVolume()
    {
        try (Connection con = conProvider.getConnection()) {
            PreparedStatement statement = (PreparedStatement) con.createStatement();
            statement.executeQuery("SELECT * FROM Songs");
            statement.executeQuery("UPDATE Settings SET Volume = "
                    + "player.getvolume eller noget" + ";");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    public double lastSetVolume()
    {
        double vol = 0;
        try (Connection con = conProvider.getConnection()) {
            //PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Settings;");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Settings;");
            while (rs.next()) {
                vol = rs.getDouble("Volume");
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vol;
    }
    public int lastPlayedSong()
    {   
        int songId = 0;
        try (Connection con = conProvider.getConnection()) {
            //PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Settings;");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Settings;");
            while (rs.next()) {
                songId = rs.getInt("lastSong");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return songId;
    }
    public Playlist lastPlayedPlaylist()
    {
        return null;
    }
    public String lastPlayedQueue()
    {
        String queueList = "";
        try (Connection con = conProvider.getConnection()) {
            //PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Settings;");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Settings;");
            while (rs.next()) {
                queueList = rs.getString("lastQueue");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return queueList;
    }
    public List<Song> queueList(String str)
    {
        String[] a = str.split(",");
        for (String string : a)
        {
            System.out.println(string);
        }
        List<Song> playlistSongs = new ArrayList();
        List<Song> allSongs = sDAO.getAllSongs();
        List<Integer> tempId = new ArrayList();
        try(Connection con = conProvider.getConnection()) {
            PreparedStatement statement = (PreparedStatement) con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Playlist;");
            while(rs.getString("Title")== str) {
                tempId.add(rs.getInt("SongId"));
                
        }
            for (Song song : allSongs) {
                for (Integer integer : tempId) {
                    if(integer == song.getId()){
                        playlistSongs.add(song);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        List<Playlist> allPlaylists = getAllPlaylists();
//        for (Playlist allPlaylist : allPlaylists)
//        {
//            if (allPlaylist.getTitle()==query)
//            
//        return null;{
//                allPlaylist.setPlaylist(playlistSongs);
//                return allPlaylist;
//            }
//        }
        return null;
    }
    
}
