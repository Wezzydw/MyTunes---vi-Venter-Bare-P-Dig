/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Collections.list;
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
    
    public Playlist addSelection(List<Song> songs) {
        
        
        
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
    
    public List<Playlist> getPlaylist(String query)
    {
        List<Playlist> playlist = new ArrayList<Playlist>();
        List<Playlist> foundPlaylist = new ArrayList();

        for (Playlist playlist1 : playlist) {

            if (playlist1.getTitle().toLowerCase().contains(query.toLowerCase())) {
                foundPlaylist.add(playlist1);
            }
        }

        return foundPlaylist;
    }
    /**
     * 
     * @param toBeRemoved 
     */
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
    /**
     * 
     * @param title
     * @throws IOException
     * @throws SQLException 
     */
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
    /**
     * 
     * @param title
     * @param newTitle
     * @throws SQLException
     * @throws IOException 
     */
    public void renamePlaylist(String title, String newTitle ) throws SQLException, IOException
    {
       // Playlist p = getPlaylist(title);  //tilføjet typecast for at få denne til at virke
       // p.setTitle(newTitle);
    
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
    
    
    
    
      public void writeChanges()throws IOException
    {
        List<Playlist> allPlaylists = new PlaylistDAO().getAllPlaylists();
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("10.176.111.31");
        ds.setDatabaseName("MyTunes1");
        ds.setUser("CS2018A_20");
        ds.setPassword("CS2018A_20");
        try (Connection con = ds.getConnection())
        {
            for (Playlist playlist : allPlaylists)
            {
               try(PreparedStatement pstmt = con.prepareStatement("INSERT INTO Playlist (Title, SongId, FilePath ) VALUES (")){
                    pstmt.setString(1, playlist.getTitle());
                    pstmt.execute();
               }
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }
}
    

