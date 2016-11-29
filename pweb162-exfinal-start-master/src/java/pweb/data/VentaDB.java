package pweb.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pweb.business.Libro;

import pweb.business.Venta;

public class VentaDB {
    
    public static int insert(Venta venta) 
    {
        ConnectionPool pool=null;
        Connection connection = null;
        PreparedStatement ps = null;        

        try 
        {
            pool=ConnectionPool.getInstance();
            connection=pool.getConnection();     

            String query = "INSERT INTO " 
                         + "VENTAS (CODLIBRO, TITULAR, NUMTARJETA, TOTAL) "
                         + "VALUES (?, ?, ?, ?)";

            ps = connection.prepareStatement(query);                        
            ps.setString(1, venta.getCodigoLibro());
            ps.setString(2, venta.getTitularTarjeta());
            ps.setString(3, venta.getNumeroTarjeta());
            ps.setDouble(4, venta.getTotalVenta());

            return ps.executeUpdate();            
        } 
        catch (SQLException e) {
            System.out.println(e);
            return 0;
        } 
        finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    } 
}
