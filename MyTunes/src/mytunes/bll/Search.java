/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mytunes.be.Song;
import mytunes.dal.SongDAO;

/**
 *
 * @author Wezzy Laptop
 */
public class Search
{
    private SongDAO sdao;
    private List<Song> song;
    public Search() throws IOException
    {
        sdao = new SongDAO();
        song = sdao.getAllSongsFromDB();
    }
    
    /**
     * Søger listen af sange igennem for input: title, author, categori, releaseyear eller album.
     * hvis inputtet er fundet, returnere den elementer fra listen, der matcher input.
     */
    public List<Song> searcher(String query) throws IOException
    {
        
        List<Song> searchResult = new ArrayList();
        
        for (Song song1 : song)
        {
            int counter = 0;
            String releaseYear = "" + song1.getReleaseYear(); //The only way we could get change an int to a String.
            
            if (song1.getTitle().toLowerCase().contains(query.toLowerCase()) && song1.getTitle() != null)
            {
                if(counter == 0){
                    counter ++;
                searchResult.add(song1);}
            }if (song1.getAlbum() != null && song1.getAlbum().toLowerCase().contains(query.toLowerCase()))
            {
                 if(counter == 0){
                    counter ++;
                searchResult.add(song1);}
            } if ( song1.getAuthor()!= null && song1.getAuthor().toLowerCase().contains(query.toLowerCase()))
            {
                 if(counter == 0){
                    counter ++;
                searchResult.add(song1);}
            } if ( song1.getCategori() != null && song1.getCategori().toLowerCase().contains(query.toLowerCase()))
            {
                 if(counter == 0){
                    counter ++;
                searchResult.add(song1);}
            } if ( song1.getReleaseYear() != null && releaseYear.contains(query))
            {
                 if(counter == 0){
                    counter ++;
                searchResult.add(song1); }
            }
        }
        return searchResult;
    }

}
