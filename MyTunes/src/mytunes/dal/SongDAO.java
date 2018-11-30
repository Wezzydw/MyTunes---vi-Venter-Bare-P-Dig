/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import mytunes.be.Song;

/**
 *
 * @author Wezzy Laptop
 */
public class SongDAO {

    public List<Song> addFolder()
    {
      return null;  
    }
    
    public void deleteSong(Song song)
    {
        
    }
    public void removeFolder(String filepath)
    {
        
    }
    
    public void updateSong(Song song)
    {
        
    }
    
    public List<Song> getAllSongs()
    {
        // Endnu mere off the good stuff:
//        File folder = new File("/Users/you/folder/");
//File[] listOfFiles = folder.listFiles();
//
//for (File file : listOfFiles) {
//    if (file.isFile()) {
//        System.out.println(file.getName());
//    }
//}
        return null;
    }
    
    public Song getSongForPlayback()
    {
        return null;   
    }
    
    public void writeChanges()throws IOException
    {
        List<Song> allSongs = new SongDAO().getAllSongs();
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("10.176.111.31");
        ds.setDatabaseName("MovieSys");
        ds.setUser("CS2018A_20");
        ds.setPassword("CS2018A_20");
        try (Connection con = ds.getConnection())
        {
            for (Song song : allSongs)
            {
               try(PreparedStatement pstmt = con.prepareStatement("INSERT INTO Song (Title, Id, Author, Album, Categori, Filepath, Length, ReleaseYear) VALUES (")){
                    pstmt.setString(1, song.getTitle());
                    pstmt.setString(2, song.getAuthor());
                    pstmt.setString(3, song.getAlbum());
                    pstmt.setString(4, song.getCategori());
                    pstmt.setString(5, song.getFilePath());
                    pstmt.setInt(6, song.getLength());
                    pstmt.setInt(7, song.getId());
                    pstmt.setInt(8, song.getReleaseYear());
                    pstmt.execute();
               }
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }
    }

