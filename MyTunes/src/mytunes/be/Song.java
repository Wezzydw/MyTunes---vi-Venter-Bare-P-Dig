/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

/**
 *
 * @author Wezzy Laptop
 */
public class Song {
    
    String title;
    String author;
    String album;
    String length;
    String releaseYear;
    String categori;
    String filePath;
    int id;
    
    public Song(String title, String author, String length, String releaseYear, String categori, String filePath, String album,int id)
    {
        this.title = title;
        this.author = author;
        this.length = length;
        this.categori = categori;
        this.filePath = filePath;
        this.album = album;
        this.releaseYear = releaseYear;
        this.id = id;
    }
    public Song(String title, String filePath)
    {
        this.title = title;
        this.filePath = filePath;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getCategori() {
        return categori;
    }

    public void setCategori(String categori) {
        this.categori = categori;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getId()
    {
        return id;
        
    }
}
    
    
    

