/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wezzy
 */
public class Queue
{
    List<Song> queue = new ArrayList();
    public void addSelection(List<Song> songs)
    {
        for (Song s : songs)
        {
            queue.add(s);
        }
        
    }
    
    public void removeSelection()
    {
        
    }
    
    public Song getSong(int index)
    {
        return queue.get(index);
    }
    
    public boolean endOfQueue(int index)
    {
        if (queue.size()-1 == index)
            return true;
        else return false;
    }
    
    public int queueSize()
    {
        return queue.size();
    }
}