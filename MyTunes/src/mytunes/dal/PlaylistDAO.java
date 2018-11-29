/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
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

/**
 *
 * @author Wezzy Laptop
 */
public class PlaylistDAO {
DatabaseConnection dc;

    public PlaylistDAO() throws IOException
    {
        this.dc = new DatabaseConnection();
    }

    private DatabaseConnection conProvider;
    
    public Playlist addSelection() {
        
        return null;
    }

    public Playlist mergePlaylist(Playlist A, Playlist B, String Title) 
    {
        Playlist np = new Playlist(Title);
        for(int i = 0; 1 < A.getSize(); i++)
        {
        np.addSong(A.getSong(i));
        }
        return np;
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
        
        List<Playlist> playlists = new ArrayList<>();
        
        try (Connection con = conProvider.getConnection())
        {
            
            PreparedStatement statement = (PreparedStatement) con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Playlists;");
            while(rs.next())
            {
                
                String title = rs.getString("title");
                Playlist playlist = new Playlist(title);
                playlists.add(playlist);
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return playlists;
    }
    
    public void deletePlaylist(String title) throws IOException, SQLException
        {
        try (Connection con = dc.getConnection())
        {
           Statement statement = con.createStatement();
           ResultSet rs = statement.executeQuery("Select * FROM Playlist;");
             {
                 {
                     PreparedStatement pstmt = con.prepareStatement("DELETE FROM Playlist WHERE Title=()");
                     pstmt.setString(1, title);
                     pstmt.execute();
                     pstmt.close();
                 }
             }
            
        } catch (SQLServerException ex)
        {
        }}
    public void renamePlaylist(String title, String newTitle ) throws SQLException, IOException
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
           
    }
}
    

