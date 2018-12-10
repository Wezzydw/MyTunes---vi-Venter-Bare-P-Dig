/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.Player;

/**
 *
 * @author andreas
 */
public class SettingsDAO
{

    private DatabaseConnection conProvider;
    private SongDAO sDAO;

    
    public SettingsDAO() throws IOException
    {
        conProvider = new DatabaseConnection();
        sDAO = new SongDAO();
    }
    
    /**
     * 
     * @param vol 
     * Opdatere volumen til databasen, i settingstabellen, 
     * så denne huskes hvis programmet lukkes.
     */
    public void updateVolume(double vol)
    {

        try (Connection con = conProvider.getConnection())
        {
            Statement statement = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Settings SET Volume = (?)");
            pstmt.setDouble(1, vol);
            pstmt.execute();
            pstmt.close();
            System.out.println("Diller found - and updated!");
        } catch (SQLException ex)
        {

            ex.printStackTrace();
        }
    }
    
    /**
     * Henter sidste værdi for volumen i settingstabellen fra databasen.
     */
    public double lastSetVolume()
    {
        double vol = 0;
        try (Connection con = conProvider.getConnection())
        {
            //PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Settings;");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Settings;");
            while (rs.next())
            {
                vol = rs.getDouble("Volume");

            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return vol;
    }
    
    /**
     * 
     * @returnerer songId, for den sidstafspillede sang, hentet fra settingstabellen,
     * i databasen.
     */
    public int lastPlayedSong()
    {
        int songId = 0;
        try (Connection con = conProvider.getConnection())
        {
            //PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Settings;");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Settings;");
            while (rs.next())
            {
                songId = rs.getInt("lastSong");
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return songId;
    }
    
    /**
     * 
     * @returnerer den sidst valgte playliste.
     */
    public Playlist lastPlayedPlaylist()
    {
        return null;
    }
    
    /**
     * 
     * @returnerer queue-listen fra den sidste queue, hentet fra databasen.
     */
    public String lastPlayedQueue()
    {
        String queueList = "";
        try (Connection con = conProvider.getConnection())
        {
            //PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Settings;");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Settings;");
            while (rs.next())
            {
                queueList = rs.getString("lastQueue");
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return queueList;
    }

    /**
     * 
     * @returnerer en liste af sange fra queuen.
     */
    public List<Song> queueList()
    {
        //String[] a = str.split(",");
        //Tænker vel bare at det her er fra database, og ikke fra metode input
        //Btw alle comments her er for at undgå redLines
        List<Song> queSongs = new ArrayList();
        List<Song> allSongs = sDAO.getAllSongsFromDB();
        List<Integer> tempId = new ArrayList();
//        for (String string : a)
//        {
//            tempId.add(Integer.valueOf(string));
//        }

        for (Song song : allSongs)
        {
            for (Integer integer : tempId)
            {
                if (integer == song.getId())
                {
                    queSongs.add(song);
                }
            }
        }
        return queSongs;
    }

}
