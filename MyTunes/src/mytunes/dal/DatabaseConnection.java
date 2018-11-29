/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

<<<<<<< HEAD
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
=======
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
>>>>>>> c9c5c363426421603a266fa405b317cf7f5ae2fd

/**
 *
 * @author Wezzy Laptop
 */
<<<<<<< HEAD
public class DatabaseConnection 
    {   
        public Connection getConnection() throws SQLServerException{
            Connection CON = null;
        return CON;
    }
=======
public class DatabaseConnection {
    
    private static final String PROP_FILE = "database.setttings";
    private SQLServerDataSource ds;
    
    public DatabaseConnection() throws IOException{
    Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(PROP_FILE));
        ds = new SQLServerDataSource();
        ds.setServerName(databaseProperties.getProperty("Server"));
        ds.setDatabaseName(databaseProperties.getProperty("Database"));
        ds.setUser(databaseProperties.getProperty("User"));
        ds.setPassword(databaseProperties.getProperty("Password"));
    
    }
    
    public Connection getConnection() throws SQLServerException
    {
        return ds.getConnection();
    }
    
>>>>>>> c9c5c363426421603a266fa405b317cf7f5ae2fd
}
