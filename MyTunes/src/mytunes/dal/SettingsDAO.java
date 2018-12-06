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
    
}
