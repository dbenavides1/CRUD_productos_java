/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group.taller1;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class cnxPostgreSql {
    Connection conectar = null;
    String usuario = "postgres";
    String contrasena = "pgdavid1";
    String bd = "java_productos";
    String ip = "localhost";
    String puerto = "5432";
    
    String cadena = "jdbc:postgresql://"+ip+":"+puerto+"/"+bd;
    
    public Connection establecerConexion(){
        try {
            Class.forName("org.postgresql.Driver");
                conectar = DriverManager.getConnection(cadena, usuario, contrasena);
                //JOptionPane.showMessageDialog(null, "Se conectó a la bd postgres");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se conectó a la bd, error: "+e.toString());
        }
        return conectar;
    }
}
