/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pweb.data;

import java.sql.Connection;
import java.sql.SQLException;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

/**
 *
 * @author Cesar
 */
public class ConnectionPool {
    private static ConnectionPool pool = null;
    private static SQLiteConnectionPoolDataSource dataSource = null;    

    private ConnectionPool() {                        
        dataSource = new SQLiteConnectionPoolDataSource();
        dataSource.setUrl("jdbc:sqlite:D:/sqlite3/LibrosDB.db");                       
    }

    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() {
        try {            
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
