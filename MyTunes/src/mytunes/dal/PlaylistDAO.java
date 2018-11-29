/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void renamePlaylist(String title, String newTitle ) throws SQLException
    {
        Playlist p = getPlaylist(title);
        p.setTitle(newTitle);
    
        try
        {
            DatabaseConnection dc = new DatabaseConnection();
            Connection con = dc.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select * FROM Playlist;");
             while (rs.next()) 
             {
                     PreparedStatement pstmt = con.prepareStatement("UPDATE Playlist SET Title = (?) WHERE movie = (?) AND [user] = (?)");
                     pstmt.setString(1, newTitle);
                     pstmt.execute();
                     pstmt.close();
                     System.out.println("Playlist found - and updated!"); 
             }
 
             }
    
        catch (SQLServerException ex)
        {
            ex.printStackTrace(); 
        }
           
    }}
    

