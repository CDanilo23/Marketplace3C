/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.uniminuto.util;

import co.com.uniminuto.dto.NivelHotelDTO;
import co.com.uniminuto.entities.Ubicacion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class CombosUtil {
     private static Connection con = null;
     private static List<Ubicacion> lu =null; 
    public static List<Ubicacion> getUbicaciones(){
         try {
             Class.forName("com.mysql.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketplace", "root", "root");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("select u.id_ubicacion,u.ciudad from ubicacion u");
             lu = new ArrayList<>();
             Ubicacion ubi;
             while(rs.next()){
                 ubi = new Ubicacion();
                 ubi.setIdUbicacion(rs.getInt(1));
                 ubi.setCiudad(rs.getString(2));
                 lu.add(ubi);
             }
         } catch (ClassNotFoundException | SQLException ex) {
             Logger.getLogger(CombosUtil.class.getName()).log(Level.SEVERE, null, ex);
         } finally{
             try {
                 con.close();
             } catch (SQLException ex) {
                 Logger.getLogger(CombosUtil.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         return lu;
    }
    public static List<Ubicacion> getPaises(){
         try {
             Class.forName("com.mysql.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/marketplace", "root", "root");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT distinct * FROM ubicacion group by pais");
             lu = new ArrayList<>();
             Ubicacion ubi;
             while(rs.next()){
                 ubi = new Ubicacion();
                 ubi.setPais(rs.getString(3));
                 lu.add(ubi);
             }
         } catch (ClassNotFoundException | SQLException ex) {
             Logger.getLogger(CombosUtil.class.getName()).log(Level.SEVERE, null, ex);
         } finally{
             try {
                 con.close();
             } catch (SQLException ex) {
                 Logger.getLogger(CombosUtil.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         return lu;
    }
    
    public static LinkedList<NivelHotelDTO>  getNivelesHotel(){
        LinkedList<NivelHotelDTO> ln = new LinkedList<>();
        ln.add(new NivelHotelDTO(NivelHotelEnum.CINCO_ESTRELLAS.getDescripcion(), NivelHotelEnum.CINCO_ESTRELLAS.getNivel()));
        ln.add(new NivelHotelDTO(NivelHotelEnum.CUATRO_ESTRELLAS.getDescripcion(), NivelHotelEnum.CUATRO_ESTRELLAS.getNivel())); 
        ln.add(new NivelHotelDTO(NivelHotelEnum.TRES_ESTRELLAS.getDescripcion(), NivelHotelEnum.TRES_ESTRELLAS.getNivel()));
        ln.add(new NivelHotelDTO(NivelHotelEnum.DOS_ESTRELLAS.getDescripcion(), NivelHotelEnum.DOS_ESTRELLAS.getNivel()));
        ln.add(new NivelHotelDTO(NivelHotelEnum.UNA_ESTRELLA.getDescripcion(), NivelHotelEnum.UNA_ESTRELLA.getNivel()));
        return ln;
    }
}
