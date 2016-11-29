package pweb.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pweb.business.Libro;

public class LibroDB {         
    
    public static Libro selectLibro(String codigo) 
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
            String query = "SELECT * FROM LIBROS "
                         + "WHERE CODIGO = ?";
            
            ps = connection.prepareStatement(query);
            ps.setString(1, codigo);            
            rs = ps.executeQuery();
            
            Libro libro = null;
            
            if (rs.next()) {
                libro = new Libro();
                libro.setCodigo(rs.getString("CODIGO"));
                libro.setTitulo(rs.getString("TITULO"));
                libro.setAutor(rs.getString("AUTOR"));
                libro.setGenero(rs.getString("GENERO"));
                libro.setPrecio(rs.getDouble("PRECIO"));                               
            }
            return libro;            
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
    
    public static ArrayList<Libro> selectLibros() 
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
            String query = "SELECT * FROM LIBROS";            
            ps = connection.prepareStatement(query);            
            rs = ps.executeQuery();            
            
            ArrayList<Libro> libros = new ArrayList<Libro>();
            Libro libro = null;            
            
            while (rs.next()) {                
                libro = new Libro();
                libro.setCodigo(rs.getString("CODIGO"));
                libro.setTitulo(rs.getString("TITULO"));
                libro.setAutor(rs.getString("AUTOR"));
                libro.setGenero(rs.getString("GENERO"));
                libro.setPrecio(rs.getDouble("PRECIO"));                               
                libros.add(libro);
            }
            return libros;            
        
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
