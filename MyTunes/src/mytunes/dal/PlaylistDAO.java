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
            String a = "SELECT * FROM Playlist;";
            PreparedStatement pstmt = con.prepareStatement(a);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (rs.getString("Title") == query)
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
            
            
            for (Song song : toBeRemoved)
            {
                String a = "DELETE FROM Playlist WHERE SongId =" + song.getId() + ";";
                PreparedStatement pstmt = con.prepareStatement(a);
                pstmt.execute();
                pstmt.close();
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
            while(rs.next())
            {
                    a = "DELETE FROM Playlist WHERE Title = (?);";
                    pstmt = con.prepareStatement(a);
                    pstmt.setString(1, title);
                    pstmt.execute();
                    System.out.println("den kommer hertil");
//                    pstmt.close();
                
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

        try(Connection con = conProvider.getConnection())
        {
            String a = "UPDATE Playlists SET Title = (?) WHERE Title = (?);";
            PreparedStatement pstmt = con.prepareStatement(a);
            pstmt.setString(1, newTitle);
            pstmt.setString(2, title);
            pstmt.execute();
            pstmt.close();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select * FROM Playlist;");
            //Behøver vi et whileloop? og skal den rename to steder? altså i DB Playlist og DB Playlists???
            while (rs.next())
            {
                a = "UPDATE Playlist SET Title = (?) WHERE Title = (?) ;";
                pstmt = con.prepareStatement(a);
                pstmt.setString(1, newTitle);
                pstmt.setString(2, title);
                
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
    public void createPlaylist(Playlist plist) throws IOException
    {
//        List<Playlist> allPlaylists = new PlaylistDAO().getAllPlaylists();
        
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

    public void deleteSongFromAllPlaylists(List<Song> selectedSongs)
    {
        try (Connection con = conProvider.getConnection())
        {
            String a = "SELECT * FROM Playlist;";
            PreparedStatement pstmt = con.prepareStatement(a);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                for (Song song : selectedSongs)
                {
                    if(song.getId()==rs.getInt("SongId"))
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
}
