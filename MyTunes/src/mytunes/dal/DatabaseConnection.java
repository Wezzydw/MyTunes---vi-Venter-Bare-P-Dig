/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;


/**
 *
 * @author Wezzy Laptop
 */
public class DatabaseConnection {
    
  //  private static final String PROP_FILE = "database.settings";
    private SQLServerDataSource ds;
    
    public DatabaseConnection() throws IOException{
    Properties databaseProperties = new Properties();
       // databaseProperties.load(new FileInputStream(PROP_FILE));
        ds = new SQLServerDataSource();
//        ds.setServerName(databaseProperties.getProperty("10.176.111.31"));
//        ds.setDatabaseName(databaseProperties.getProperty("MyTunes1"));
//        ds.setUser(databaseProperties.getProperty("CS2018A_20"));
//        ds.setPassword(databaseProperties.getProperty("CS2018A_20"));
        ds.setServerName("10.176.111.31");
        ds.setDatabaseName("MyTunes1");
        ds.setUser("CS2018A_20");
        ds.setPassword("CS2018A_20");
    }
    
    public Connection getConnection() throws SQLServerException
    {
        return ds.getConnection();
    }
    

}
