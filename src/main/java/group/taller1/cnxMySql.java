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
public class cnxMySql {
    Connection conectar = null;
    String usuario = "root";
    String contrasena = "";
    String bd = "java_productos";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection establecerConexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
                conectar = DriverManager.getConnection(cadena, usuario, contrasena);
                //JOptionPane.showMessageDialog(null, "Se conectó a la bd mysql");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se conectó a la bd, error: "+e.toString());
        }
        return conectar;
    }
}
