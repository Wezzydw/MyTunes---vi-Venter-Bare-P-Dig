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
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 *
 * @author Wezzy Laptop
 */
public class PlaylistDAO
{

    DatabaseConnection conProvider;
    SongDAO sDAO = new SongDAO();

    public PlaylistDAO() throws IOException
    {
        this.conProvider = new DatabaseConnection();
    }
    /*
        addSelection metoden connectes til databasen og returnere en playlist
    */
    public Playlist addSelection(List<Song> songs, Playlist playlist)
    {

        try (Connection con = conProvider.getConnection())
        {
            
          
            
           
            for (Song song : songs)
            {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO Playlist (Title =" + playlist.getTitle() + ", SongId =" + song.getId() + ";");
                pstmt.execute();
            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return playlist;
    }
    /*
    addPlaylist connectes til databasen
    */
    public void addPlaylist(Playlist plist)
    {

        try (Connection con = conProvider.getConnection())
        {
            for (Song song : plist.getSongsInPlaylist())
            {
                try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Playlist (Title, SongId) VALUES (?,?)"))
                {
                    pstmt.setString(1, plist.getTitle());
                    pstmt.setInt(2, song.getId());
                    pstmt.execute();
                }

            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    /*
    Playlist A og Playlist B danner sammen nyPlaylist
    */
    public Playlist mergePlaylist(Playlist A, Playlist B, String Title)
    {
        Playlist np = new Playlist(Title);
        for (int i = 0; 1 < A.getSize(); i++)
        {
//            np.addSong(A.getSong(i));
        }
        return np;
    }

    /*
        bliver connectet til databasen kan tage en liste af sange og opdatere dem i playlisten 
    */
    public Playlist getPlaylist(String query)

    {
        List<Song> playlistSongs = new ArrayList();
        List<Song> allSongs = sDAO.getAllSongsFromDB();
        List<Integer> tempId = new ArrayList();
        try (Connection con = conProvider.getConnection())
        {
            PreparedStatement statement = (PreparedStatement) con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Playlist;");
            while (rs.getString("Title") == query)
            {
                tempId.add(rs.getInt("SongId"));

            }
            for (Song song : allSongs)
            {
                for (Integer integer : tempId)
                {
                    if (integer == song.getId())
                    {
                        playlistSongs.add(song);
                    }
                }
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        List<Playlist> allPlaylists = getAllPlaylists();
        for (Playlist allPlaylist : allPlaylists)
        {
            if (allPlaylist.getTitle() == query)
            {
                allPlaylist.setPlaylist(playlistSongs);
                return allPlaylist;
            }
        }
        return null;
    }

    /*
        connectes til databasen og kan remove de markerede
    */
    public void removeSelection(List<Song> toBeRemoved)
    {
        try (Connection con = conProvider.getConnection())
        {
            PreparedStatement statement = (PreparedStatement) con.createStatement();
            statement.executeQuery("SELECT * FROM Playlist;");
            for (Song song : toBeRemoved)
            {
                statement.executeQuery("DELETE FROM Playlist WHERE SongId =" + song.getId() + ";");
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    /*
    connectes til databasen og kan tage fat i alle playlists og laver en ny list
    */
    public List<Playlist> getAllPlaylists()
    {

        List<Playlist> playlists = new ArrayList<>();

        try (Connection con = conProvider.getConnection())
        {

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Playlists;");
            while (rs.next())
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

    /*
        connectes til databasen og kan delete playlister 
    */
 
    public void deletePlaylist(String title) throws IOException, SQLException
    {
        try (Connection con = conProvider.getConnection())
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
        }
    }

    /*
    connectes til databasen og gør det muligt at ændrer navnet på en eksisterende
    playlist
    */
    public void renamePlaylist(String title, String newTitle) throws SQLException, IOException
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

        } catch (SQLServerException ex)
        {
            ex.printStackTrace();
        }
    }
    /*
    
    */
    public void writeChanges() throws IOException
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
                try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Playlist (Title, SongId, FilePath ) VALUES ("))
                {
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
