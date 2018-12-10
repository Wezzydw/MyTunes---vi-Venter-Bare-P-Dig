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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mytunes.be.Song;

/**
 *
 * @author Wezzy Laptop
 */
public class SongDAO
{
    int nonReadySongs;
    DatabaseConnection conProvider;

    public SongDAO() throws IOException
    {
        
        conProvider = new DatabaseConnection();
    }

    //File folder = new File("/Users/andreas/Music/");
    List<Song> songs = new ArrayList<>();
    
    public List<Song> addFolder(File folderPath) throws IOException, InterruptedException
    {
        listFilesForFolder(folderPath);
        List<Media> songsToAdd = new ArrayList<>();
        int counter = 0;
        for (String string : listFilesForFolder(folderPath))
        {
            songs.add(new Song("song nummber " + counter, string, counter));
            Media m1 = new Media(new File(string).toURI().toString());
            songsToAdd.add(m1);
            counter++;
        }
        nonReadySongs = songsToAdd.size();
        for (Media me : songsToAdd)
        {
            //me.getMetadata().size(); Kunne måske være en måde at tjekke for hvornår den er færdig
            me.getMetadata().addListener((MapChangeListener<String, Object>) change ->
            {

                //System.out.println(change.getValueAdded());
                //System.out.println(change.getKey());
                //System.out.println(songsToAdd.size());
                if (change.getKey() == "album artist")
                {
                    String a = (String) me.getMetadata().get("album artist");
                    getMediaSong(me).setAuthor(a);

                }
                if (change.getKey() == "year")
                {
                    String a = "" + me.getMetadata().get("year");
                    getMediaSong(me).setReleaseYear(a);
                    System.out.println("year : " + a);
                }
                if (change.getKey() == "genre")
                {
                    String a = (String) me.getMetadata().get("genre");
                    getMediaSong(me).setCategori(a);
                }
                if (change.getKey() == "title")
                {
                    String a = (String) me.getMetadata().get("title");
                    getMediaSong(me).setTitle(a);
                }

            });
            MediaPlayer mp = new MediaPlayer(me);
            mp.setOnReady(new Runnable()
            {
                /*
                Metoden angiver længden af sangen
                */
                @Override
                public void run()
                {
                    System.out.println("Tester --;");
                    System.out.println(nonReadySongs);
                    nonReadySongs--;
                    String duration = "" + me.getDuration().toMinutes();
                    getMediaSong(me).setLength(duration);
                }
            });
        }
        Thread t = new Thread(new Runnable()
        {
            
            /*
            ???++
            */
            @Override
            public void run()
            {
                System.out.println("Before Time");

                try
                {

                    while (nonReadySongs != 0)
                    {
                        System.out.println("in Thread " + nonReadySongs);
                    }
                    System.out.println("in Thead done");
                    for (Song song : songs)
                    {
                        if (song.getTitle().contains("song nummber"))
                        {
                            String tmpTitle = song.getFilePath().substring(song.getFilePath().lastIndexOf("\\") + 1, song.getFilePath().length() - 4);
                            song.setTitle(tmpTitle);
                        }
                    }

                    System.out.println("Before writechanges");
                    writeChanges(songs);
                } catch (IOException ex)
                {
                    System.out.println(" done goofed");
                }
            }
        });
        t.start();
//        while(tester != 0)
//        {
//            //System.out.println("tester" + tester);
//        }
        return songs;
    }
    
    public int getNumberOfUnReadySongs()
    {
        return nonReadySongs;
    }
    /*
        vi trækker sangen ud af mediet
    */
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
    /*
    
    */
    public List<String> listFilesForFolder(File folder) throws IOException
    {
        File[] listOfFiles = folder.listFiles();
        List<String> filePaths = new ArrayList<>();
        for (File file : listOfFiles)
        {
            if (file.isDirectory())
            {

            }
            if (file.isFile())
            {
                if (file.getName().endsWith(".mp3"))
                {//mp3 istedet for ""
                    filePaths.add(file.getPath());
                }

            }
        }
        return filePaths;
    }
    /*
        giver deleteSong metoden adgang til databasen
    */
    public void deleteSong(Song song) throws IOException
    {
        try (Connection con = conProvider.getConnection())
        {
            String a = "DELETE FROM Songs WHERE Id =" + song.getId() + ";";
            PreparedStatement prst = con.prepareStatement(a);
            prst.execute();
//            Statement statement =  con.createStatement();
//            statement.executeQuery("SELECT * FROM Songs;");
//            statement.executeQuery("DELETE FROM Songs WHERE Id =" + song.getId() + ";");
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    /*
    giver updateSong connection til databasen og og sender metadatene med??????????
    */
    public void updateSong(Song song)
    {

        String a = "UPDATE Songs SET Title = ?, Author = ?, Album = ?, Categori = ?, Filepath = ?, Length = ?, ReleaseYear = ? WHERE Id = ?;";
     
        try (Connection con = conProvider.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement(a);

            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getAuthor());
            pstmt.setString(3, song.getAlbum());
            pstmt.setString(4, song.getCategori());
            pstmt.setString(5, song.getFilePath());
            pstmt.setString(6, song.getLength());
 
            pstmt.setString(7, song.getReleaseYear());
            pstmt.setInt(8, song.getId());
            pstmt.execute();
 
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    /*
    henter alle sangene ned fra databasen
    */
    public List<Song> getAllSongsFromDB()
    {
        //Jeg vil gerne have et check om filen faktisk eksisterer her;
        List<Song> allSongs = new ArrayList();
        try (Connection con = conProvider.getConnection())
        {   
//            String a = "SELECT * FROM Songs;";
//            PreparedStatement prst = con.prepareStatement(a);
//            prst.getMoreResults(); //tror dette vil virke i stedet for rs.next
            
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Songs;");
            
            while (rs.next())
            {
                
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                String album = rs.getString("Album");
                String categori = rs.getString("Categori");
                String filepath = rs.getString("Filepath");
                String length = rs.getString("Length");
                int id = rs.getInt("Id");
                String releaseYear = rs.getString("ReleaseYear");
                Song song = new Song(title, author, length, releaseYear, categori, filepath, album, id);
                allSongs.add(song);
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return allSongs;
    }
    /*
        Henter alle sange fra et arraylist
    */
    public List<Song> getAllSongsFromMem()
    {
        return songs;
    }
    /*
    ???????????
    */
    public Song getSongForPlayback()
    {
        return null;
    }
    /*
    returnere en sang fra databasen????
    */
    public Song getSong(Song song)
    {
        //getAllSongsFromDB();

        for (Song MetaSong : getAllSongsFromDB())
        {
            if (MetaSong.getId() == song.getId())
            {
                return song;
            }
        }
        return null;
    }
    /*
    
    */
    public void writeChanges(List<Song> allSongs) throws IOException
    {
        //List<Song> allSongs = new SongDAO().getAllSongs();
        System.out.println("WRITECHANGES ");
//        SQLServerDataSource ds = new SQLServerDataSource();
//        ds.setServerName("10.176.111.31");
//        ds.setDatabaseName("MyTunes1");
//        ds.setUser("CS2018A_20");
//        ds.setPassword("CS2018A_20");





        String a = "INSERT INTO Songs (Title, Author, Album, Categori, Filepath, Length, ReleaseYear) VALUES (?,?,?,?,?,?,?);";
        try (Connection con = conProvider.getConnection())
        {
            
            
            for (Song song : allSongs)
            {
                System.out.println("WriteChanges plus size: " + allSongs.size());
                PreparedStatement pstmt = con.prepareStatement(a);
                pstmt.setString(1, song.getTitle());
                pstmt.setString(2, song.getAuthor());
                pstmt.setString(3, song.getAlbum());
                pstmt.setString(4, song.getCategori());
                pstmt.setString(5, song.getFilePath());
                pstmt.setString(6, song.getLength());
                //pstmt.setInt(7, song.getId());
                pstmt.setString(7, song.getReleaseYear());
                pstmt.execute();

            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
