/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mytunes.be.Queue;
import mytunes.be.Song;

/**
 *
 * @author Wezzy Laptop
 */
public class SongDAO {
    File folder = new File("/Users/andreas/Music/");
    List<Song> songs = new ArrayList<>();
    public List<Song> addFolder(File folderPath) throws IOException
    {
        listFilesForFolder(folderPath);
        List<Media> songsToAdd = new ArrayList<>();
        int counter = 0;
        for (String string : listFilesForFolder(folder))
        {
            songs.add(new Song("song nummber " + counter, string));
            Media m1 = new Media(new File(string).toURI().toString());
            songsToAdd.add(m1);
            counter++;
        }
        
        for (Media me : songsToAdd) {

            me.getMetadata().addListener((MapChangeListener<String, Object>) change -> {
            
                //System.out.println(change.getValueAdded());
                //System.out.println(change.getKey());
                
                System.out.println(songsToAdd.size());
                if(change.getKey() == "album artist")
                {
                    String a = (String) me.getMetadata().get("album artist");
                    getMediaSong(me).setAuthor(a);
                }
                if(change.getKey() == "year")
                {
                    String a = "" + me.getMetadata().get("year");
                    getMediaSong(me).setReleaseYear(a);
                }
                if(change.getKey() == "genre")
                {
                    String a = (String) me.getMetadata().get("genre");
                    getMediaSong(me).setCategori(a);
                }
                if(change.getKey() == "title")
                {
                    String a = (String) me.getMetadata().get("title");
                    getMediaSong(me).setTitle(a);
                }
            });
            MediaPlayer mp = new MediaPlayer(me);
            mp.setOnReady(new Runnable() {
                @Override
                public void run()
                {
                    String duration = "" + me.getDuration().toMinutes();
                    getMediaSong(me).setLength(duration);
                }
            });
        }
        return null;
    }
    public Song getMediaSong(Media m)
    {
        for (Song song : songs)
        {
            if (new File(song.getFilePath()).toURI().toString().equals(m.getSource()))
            {
                return song;
            }
        }
        return null;
    }
    public List<String> listFilesForFolder(File folder) throws IOException {
        File[] listOfFiles = folder.listFiles();
        List<String> filePaths = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                
            }
            if (file.isFile())
            {
                if (file.getName().contains("mp3")){//mp3 istedet for ""
                    filePaths.add(file.getPath());
                }
                
            }
        }
        return filePaths;
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
                    pstmt.setString(6, song.getLength());
                    pstmt.setInt(7, song.getId());
                    pstmt.setString(8, song.getReleaseYear());
                    pstmt.execute();
               }
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }
    }

