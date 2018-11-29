/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.ArrayList;
import java.util.List;
import mytunes.be.Song;
import mytunes.dal.SongDAO;

/**
 *
 * @author Wezzy Laptop
 */
public class Search {
    
    
    /**
     * Searches the list of songs for input title, author, categori, release year
     * or album name. If value is found
     * returns a list if all songs containing the title input
    */
    public List<Song> searcher(String query){
        
        SongDAO sdao = new SongDAO();

        List<Song> song = new ArrayList();
        List<Song> searchResult = new ArrayList();
        song = sdao.getAllSongs();
        
        for (Song song1 : song) {
            String releaseYear = "" + song1.getReleaseYear(); //The only way we could get change an int to a String.
            if (song1.getTitle().toLowerCase().contains(query.toLowerCase())) {
                searchResult.add(song1);
            }
            else if(song1.getAlbum().toLowerCase().contains(query.toLowerCase())) {
                searchResult.add(song1);
            }
            else if(song1.getAuthor().toLowerCase().contains(query.toLowerCase())){
                searchResult.add(song1);
            }
            else if(song1.getCategori().toLowerCase().contains(query.toLowerCase())){
                searchResult.add(song1);
            }
            else if(releaseYear.contains(query)){
                searchResult.add(song1);
            }               
        }
        return searchResult;
    }
    
}
