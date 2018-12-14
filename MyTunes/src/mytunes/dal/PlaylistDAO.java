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
        String a = "INSERT INTO Playlist (Title, SongId) VALUES (?,?);";

        try (Connection con = conProvider.getConnection())
        {
            for (Song song : songs)
            {
                PreparedStatement pstmt = con.prepareStatement(a);
                pstmt.setString(1, playlist.getTitle());
                pstmt.setInt(2, song.getId());
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
    // Tilslutter, eller kører vi the julekalender stil?
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
        bliver connectet til databasen kan tage en liste af sange og opdatere dem i playlisten 
     */
    public Playlist getPlaylist(Playlist plist)
    {
        List<Song> playlistSongs = new ArrayList();
        List<Song> allSongs = sDAO.getAllSongsFromDB();

        List<Integer> tempId = new ArrayList();
        try (Connection con = conProvider.getConnection())
        {
            String a = "SELECT * FROM Playlist;";
            PreparedStatement pstmt = con.prepareStatement(a);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (rs.getString("Title").equals(plist.getTitle()))
                {
                    tempId.add(rs.getInt("SongId"));
                }
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
            if (allPlaylist.getTitle().equals(plist.getTitle()))
            {
                plist.setPlaylist(playlistSongs);
                return plist;
            }
        }
        return null;
    }

    /*
    connectes til databasen og kan tage fat i alle playlists og laver en ny list
     */
    public List<Playlist> getAllPlaylists()
    {

        List<Playlist> playlists = new ArrayList<>();

        try (Connection con = conProvider.getConnection())
        {

            String a = "SELECT * FROM Playlists;";
            PreparedStatement pstmt = con.prepareStatement(a);
            ResultSet rs = pstmt.executeQuery();
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
    //Der er noget glat med denne funktion og jeg er ikke helt klar over hvad vi vil have den til at gøre
    //Hvis den skal slætte en hel playlist, skal den også slette den i DB Playlists? 
    public void deletePlaylist(String title) throws IOException, SQLException
    {
        try (Connection con = conProvider.getConnection())
        {
            String a = "DELETE FROM Playlists WHERE Title = (?);";
            PreparedStatement pstmt = con.prepareStatement(a);
            pstmt.setString(1, title);
            pstmt.execute();
            pstmt.close();

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select * FROM Playlist;");
            while (rs.next())
            {
                a = "DELETE FROM Playlist WHERE Title = (?);";
                pstmt = con.prepareStatement(a);
                pstmt.setString(1, title);
                pstmt.execute();
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

        try (Connection con = conProvider.getConnection())
        {
            String a = "UPDATE Playlists SET Title = (?) WHERE Title = (?);";
            PreparedStatement pstmt = con.prepareStatement(a);
            pstmt.setString(1, newTitle);
            pstmt.setString(2, title);
            pstmt.execute();
            pstmt.close();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select * FROM Playlist;");
            while (rs.next())
            {
                a = "UPDATE Playlist SET Title = (?) WHERE Title = (?) ;";
                pstmt = con.prepareStatement(a);
                pstmt.setString(1, newTitle);
                pstmt.setString(2, title);

                pstmt.execute();
                pstmt.close();
            }

        } catch (SQLServerException ex)
        {
            ex.printStackTrace();
        }
    }

    /*
    
     */
    public void createPlaylist(Playlist plist) throws IOException
    {

        try (Connection con = conProvider.getConnection())
        {
            try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO Playlists (Title) VALUES (?)"))
            {
                pstmt.setString(1, plist.getTitle());
                pstmt.execute();
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void deleteSongsFromAllPlaylists(List<Song> selectedSongs)
    {
        try (Connection con = conProvider.getConnection())
        {
            String a = "SELECT * FROM Playlist;";
            PreparedStatement pstmt = con.prepareStatement(a);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                for (Song song : selectedSongs)
                {
                    if (song.getId() == rs.getInt("SongId"))
                    {
                        a = "DELETE FROM Playlist WHERE SongId = " + song.getId() + ";";
                        PreparedStatement prst = con.prepareStatement(a);
                        prst.execute();
                    }
                }
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public void deleteSongsFromPlaylist(List<Song> songs, Playlist playlist)
    {
        try (Connection con = conProvider.getConnection())
        {
            String a = "SELECT * FROM Playlist;";
            PreparedStatement pstmt = con.prepareStatement(a);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                for (Song song : songs)
                {
                    if (song.getId() == rs.getInt("SongId"))
                    {
                        a = "DELETE FROM Playlist WHERE SongId = " + song.getId() + " AND Title = '" + playlist.getTitle() + "';";
                        PreparedStatement prst = con.prepareStatement(a);
                        prst.execute();
                    }
                }
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
}
