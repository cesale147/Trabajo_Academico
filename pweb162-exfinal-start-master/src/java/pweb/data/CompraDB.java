/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pweb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pweb.business.Compra;
import pweb.business.Libro;

/**
 *
 * @author Cesar
 */
public class CompraDB {
    public static Compra selectVenta(String codigo) 
    {
        ConnectionPool pool=null;
        Connection connection = null;
        PreparedStatement ps = null;             
        ResultSet rs = null;
        
        try 
        {
            pool=ConnectionPool.getInstance();
            connection=pool.getConnection(); 
            
            // crear consulta
            String query = "SELECT * FROM VENTAS "
                         + "WHERE CODIGO = ?";
            
            ps = connection.prepareStatement(query);
            ps.setString(1, codigo);            
            rs = ps.executeQuery();
            
            Compra compra = null;
            
            if (rs.next()) {
                compra = new Compra();
                compra.setCodigoVenta(rs.getInt("CODIGO"));
                compra.setCodigoLibro(rs.getString("CODLIBRO"));
                compra.setNumeroTarjeta(rs.getString("NUMTARJETA"));
                compra.setTitularTarjeta(rs.getString("TITULAR"));
                compra.setTotalVenta(rs.getDouble("TOTAL"));                              
            }
            return compra;            
        } 
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }             
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }         
    }   
    
    public static ArrayList<Compra> selectVentas() 
    {
        ConnectionPool pool=null;
        Connection connection = null;
        PreparedStatement ps = null;             
        ResultSet rs = null;
        
        try 
        {
            // cargar el controlador
            pool=ConnectionPool.getInstance();
            connection=pool.getConnection(); 

            // crear consulta
            String query = "SELECT * FROM VENTAS";            
            ps = connection.prepareStatement(query);            
            rs = ps.executeQuery();            
            
            ArrayList<Compra> compras = new ArrayList<Compra>();
            Compra compra = null;            
            
            while (rs.next()) {                
                compra = new Compra();
                compra.setCodigoVenta(rs.getInt("CODIGO"));
                compra.setCodigoLibro(rs.getString("CODLIBRO"));
                compra.setTitularTarjeta(rs.getString("TITULAR"));
                compra.setTotalVenta(rs.getDouble("TOTAL"));                               
                compras.add(compra);
            }
            return compras;            
        
        } 
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }             
        finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }       
    }
}
