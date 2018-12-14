/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    List<Song> songs;

    public SongDAO() throws IOException
    {
        conProvider = new DatabaseConnection();
        songs = new ArrayList();
    }

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
            MediaPlayer mp = new MediaPlayer(me);
            mp.setOnReady(new Runnable()
            {
                /*
                Metoden angiver længden af sangen
                 */
                @Override
                public void run()
                {
                    Song currentSong = getMediaSong(me);
                    nonReadySongs--;

                    if (me.getMetadata().get("album artist") != null)
                    {
                        currentSong.setAlbum("" + me.getMetadata().get("album artist"));
                    }
                    if (me.getMetadata().get("genre") != null)
                    {
                        currentSong.setCategori("" + me.getMetadata().get("genre"));
                    }
                    if (me.getMetadata().get("year") != null)
                    {
                        currentSong.setReleaseYear("" + me.getMetadata().get("year"));
                    }
                    currentSong.setLength("" + me.getDuration().toMinutes());

                    if (me.getMetadata().get("title") != null)
                    {
                        currentSong.setTitle("" + me.getMetadata().get("title"));
                    } else
                    {
                        String tmpTitle = currentSong.getFilePath().substring(currentSong.getFilePath().lastIndexOf("\\") + 1, currentSong.getFilePath().length() - 4);
                        currentSong.setTitle(tmpTitle);
                    }
                }
            });
        }

        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    boolean done = false;
                    while (done == false)
                    {
                        if (nonReadySongs == 0)
                        {
                            done = true;
                        } else
                        {
                            Thread.sleep(50);
                        }
                    }

                    writeChanges(songs);
                } catch (IOException ex)
                {
                    System.out.println(" done goofed");
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
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
            if (file.isFile())
            {
                if (file.getName().endsWith(".mp3"))
                {
                    filePaths.add(file.getPath());
                }
            }
        }
        return filePaths;
    }

    /*
        giver deleteSong metoden adgang til databasen
     */
    public void deleteSongs(List<Song> selectedSongs) throws IOException
    {
        try (Connection con = conProvider.getConnection())
        {
            String a = "DELETE FROM Songs WHERE Id =?;";
            PreparedStatement prst = con.prepareStatement(a);
            for (Song song : selectedSongs)
            {
                prst.setInt(1, song.getId());
                prst.addBatch();
            }
            prst.executeBatch();

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
        List<Song> allSongs = new ArrayList();
        try (Connection con = conProvider.getConnection())
        {
            String a = "SELECT * FROM Songs;";
            PreparedStatement prst = con.prepareStatement(a);
            ResultSet rs = prst.executeQuery();

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
                if (new File(filepath).isFile())
                {
                    allSongs.add(song);
                }
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return allSongs;
    }


    /*
    
     */
    public void writeChanges(List<Song> allSongs) throws IOException
    {
        String a = "INSERT INTO Songs (Title, Author, Album, Categori, Filepath, Length, ReleaseYear) VALUES (?,?,?,?,?,?,?);";
        try (Connection con = conProvider.getConnection())
        {
            for (Song song : allSongs)
            {
                PreparedStatement pstmt = con.prepareStatement(a);
                pstmt.setString(1, song.getTitle());
                pstmt.setString(2, song.getAuthor());
                pstmt.setString(3, song.getAlbum());
                pstmt.setString(4, song.getCategori());
                pstmt.setString(5, song.getFilePath());
                pstmt.setString(6, song.getLength());
                pstmt.setString(7, song.getReleaseYear());
                pstmt.execute();
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
