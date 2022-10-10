/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group.taller1;

import com.mysql.cj.protocol.Resultset;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author David
 */
public class CProducto {

    int idProducto;
    String nombreProducto;
    int cantidadProducto;
    long valorProducto;
    String baseDatos;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public long getValorProducto() {
        return valorProducto;
    }

    public void setValorProducto(long valorProducto) {
        this.valorProducto = valorProducto;
    }
    
    public String getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }

    public void InsertarProducto(JTextField paramNombre, JTextField paramCantidad, JTextField paramValor) {
        setNombreProducto(paramNombre.getText());
        setCantidadProducto(Integer.parseInt(paramCantidad.getText()));
        setValorProducto(Integer.parseInt(paramValor.getText()));

        try {
            if (this.valorProducto <= 100000) {
                String consulta = "INSERT INTO public.productos(nombre, cantidad, valor_unidad) VALUES (?, ?, ?);";
                cnxPostgreSql conexionPostgresql = new cnxPostgreSql();
                CallableStatement cnx = conexionPostgresql.establecerConexion().prepareCall(consulta);

                cnx.setString(1, getNombreProducto());
                cnx.setInt(2, getCantidadProducto());
                cnx.setLong(3, getValorProducto());
                cnx.execute();
            } else {
                String consulta = "INSERT INTO productos(nombre, cantidad, valor_unidad) VALUES (?, ?, ?);";
                cnxMySql conexionMySql = new cnxMySql();
                CallableStatement cnx = conexionMySql.establecerConexion().prepareCall(consulta);

                cnx.setString(1, getNombreProducto());
                cnx.setInt(2, getCantidadProducto());
                cnx.setLong(3, getValorProducto());
                cnx.execute();
            }

            JOptionPane.showMessageDialog(null, "Registro guardado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error de registro, " + e.toString());
        }
    }

    public void MostrarProductos(JTable paramTablaProductos, JLabel paramCantTotal, JTextField paramValTotal) {
        cnxPostgreSql conexionPostgreSql = new cnxPostgreSql();
        cnxMySql conexionMySql = new cnxMySql();
        
        int cantTotal = 0;
        long valTotal = 0;

        DefaultTableModel modelo = new DefaultTableModel();

        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        paramTablaProductos.setRowSorter(OrdenarTabla);

        String pgsql = "SELECT * FROM public.productos";
        String mysql = "SELECT * FROM productos";

        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Valor Unidad");
        modelo.addColumn("BD");

        paramTablaProductos.setModel(modelo);

        String[] datos = new String[5];
        Statement pgst;
        Statement myst;

        try {
            pgst = conexionPostgreSql.establecerConexion().createStatement();
            myst = conexionMySql.establecerConexion().createStatement();

            ResultSet pgrs = pgst.executeQuery(pgsql);
            ResultSet myrs = myst.executeQuery(mysql);

            while (pgrs.next()) {
                datos[0] = pgrs.getString(1);
                datos[1] = pgrs.getString(2);
                datos[2] = pgrs.getString(3);
                datos[3] = pgrs.getString(4);
                datos[4] = "PostgreSql";
                
                cantTotal = cantTotal + Integer.parseInt(datos[2]);
                valTotal = valTotal + (Integer.parseInt(datos[2]) * Integer.parseInt(datos[3]));

                modelo.addRow(datos);
            }

            while (myrs.next()) {
                datos[0] = myrs.getString(1);
                datos[1] = myrs.getString(2);
                datos[2] = myrs.getString(3);
                datos[3] = myrs.getString(4);
                datos[4] = "MySql";
                
                cantTotal = cantTotal + Integer.parseInt(datos[2]);
                valTotal = valTotal + (Integer.parseInt(datos[2]) * Integer.parseInt(datos[3]));

                modelo.addRow(datos);
            }

            paramTablaProductos.setModel(modelo);
            
            paramCantTotal.setText("" + cantTotal);
            paramValTotal.setText("" + valTotal);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo mostrar los registros" + e.toString());
        }
    }

    public void SeleccionarProducto(JTable paramTablaProductos, JTextField paramId, JTextField paramNombre, JTextField paramCantidad, JTextField paramValor, JTextField paramBaseDatos) {
        try {
            int fila = paramTablaProductos.getSelectedRow();

            if (fila >= 0) {
                paramId.setText(paramTablaProductos.getValueAt(fila, 0).toString());
                paramNombre.setText(paramTablaProductos.getValueAt(fila, 1).toString());
                paramCantidad.setText(paramTablaProductos.getValueAt(fila, 2).toString());
                paramValor.setText(paramTablaProductos.getValueAt(fila, 3).toString());
                paramBaseDatos.setText(paramTablaProductos.getValueAt(fila, 4).toString());
            } else {
                JOptionPane.showMessageDialog(null, "Fila No seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de seleccion, " + e.toString());
        }
    }

    public void modificarProducto(JTextField paramIdProducto, JTextField paramNombre, JTextField paramCantidad, JTextField paramValor) {
        setIdProducto(Integer.parseInt(paramIdProducto.getText()));
        setNombreProducto(paramNombre.getText());
        setCantidadProducto(Integer.parseInt(paramCantidad.getText()));
        setValorProducto(Integer.parseInt(paramValor.getText()));

        try {
            if (this.valorProducto <= 100000) {
                String consulta = "UPDATE public.productos SET nombre = ?, cantidad = ?, valor_unidad = ? WHERE id_producto = ?;";
                cnxPostgreSql conexionPostgresql = new cnxPostgreSql();
                CallableStatement cnx = conexionPostgresql.establecerConexion().prepareCall(consulta);

                cnx.setString(1, getNombreProducto());
                cnx.setInt(2, getCantidadProducto());
                cnx.setLong(3, getValorProducto());
                cnx.setInt(4, getIdProducto());
                cnx.execute();
            } else {
                String consulta = "UPDATE productos SET nombre = ?, cantidad = ?, valor_unidad = ? WHERE id_producto = ?;";
                cnxMySql conexionMySql = new cnxMySql();
                CallableStatement cnx = conexionMySql.establecerConexion().prepareCall(consulta);

                cnx.setString(1, getNombreProducto());
                cnx.setInt(2, getCantidadProducto());
                cnx.setLong(3, getValorProducto());
                cnx.setInt(4, getIdProducto());
                cnx.execute();
            }

            JOptionPane.showMessageDialog(null, "Registro modificado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error de registro, " + e.toString());
        }

    }

    public void EliminarProducto(JTextField paramIdProducto, JTextField paramBaseDatos) {
        setIdProducto(Integer.parseInt(paramIdProducto.getText()));
        setBaseDatos(paramBaseDatos.getText());

        try {
            if ("PostgreSql".equals(this.baseDatos)) {
                String consulta = "DELETE FROM public.productos WHERE id_producto = ?;";
                cnxPostgreSql conexionPostgresql = new cnxPostgreSql();
                CallableStatement cnx = conexionPostgresql.establecerConexion().prepareCall(consulta);

                cnx.setInt(1, getIdProducto());
                cnx.execute();
            } else {
                String consulta = "DELETE FROM productos WHERE id_producto = ?;";
                cnxMySql conexionMySql = new cnxMySql();
                CallableStatement cnx = conexionMySql.establecerConexion().prepareCall(consulta);

                cnx.setInt(1, getIdProducto());
                cnx.execute();
            }
            JOptionPane.showMessageDialog(null, "Registro Eliminado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error de registro, " + e.toString());
        }
    }
}
