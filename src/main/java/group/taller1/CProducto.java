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
    
    public void InsertarProducto(JTextField paramNombre, JTextField paramCantidad, JTextField paramValor) {
        setNombreProducto(paramNombre.getText());
        setCantidadProducto(Integer.parseInt(paramCantidad.getText()));
        setValorProducto(Integer.parseInt(paramValor.getText()));
        
        cnxPostgreSql conexionPostgresql = new cnxPostgreSql();
        String consulta = "INSERT INTO public.productos(nombre, cantidad, valor_unidad) VALUES (?, ?, ?);";
        
        try {
            CallableStatement cnx = conexionPostgresql.establecerConexion().prepareCall(consulta);
            
            cnx.setString(1, getNombreProducto());
            cnx.setInt(2, getCantidadProducto());
            cnx.setLong(3, getValorProducto());
            
            cnx.execute();
            
            JOptionPane.showMessageDialog(null, "Registro guardado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error de registro, "+e.toString());
        }
    }
    
    public void MostrarProductos(JTable paramTablaProductos) {
        cnxPostgreSql conexionPostgreSql = new cnxPostgreSql();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        paramTablaProductos.setRowSorter(OrdenarTabla);
        
        String sql = "SELECT * FROM public.productos";
        
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Valor Unidad");
        modelo.addColumn("BD");
        
        paramTablaProductos.setModel(modelo);
                
        String[] datos = new String[5];
        Statement st;
        
        try {
            st = conexionPostgreSql.establecerConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                
                if(Integer.parseInt(datos[3]) <= 100000) {
                    datos[4] = "PostgreSql";
                }
                else{
                    datos[4] = "MySql";
                }
                
                modelo.addRow(datos);
            }
            
            paramTablaProductos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo mostrar los registros"+e.toString());
        }
    }
    
    public void SeleccionarProducto(JTable paramTablaProductos, JTextField paramId, JTextField paramNombre, JTextField paramCantidad, JTextField paramValor) {
        try {
            int fila = paramTablaProductos.getSelectedRow();
            
            if (fila >= 0) {
                paramId.setText(paramTablaProductos.getValueAt(fila, 0).toString());
                paramNombre.setText(paramTablaProductos.getValueAt(fila, 1).toString());
                paramCantidad.setText(paramTablaProductos.getValueAt(fila, 2).toString());
                paramValor.setText(paramTablaProductos.getValueAt(fila, 3).toString());
            }
            else {
                JOptionPane.showMessageDialog(null, "Fila No seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de seleccion, "+e.toString());
        }
    }
    
    public void modificarProducto(JTextField paramIdProducto, JTextField paramNombre, JTextField paramCantidad, JTextField paramValor) {
        setIdProducto(Integer.parseInt(paramIdProducto.getText()));
        setNombreProducto(paramNombre.getText());
        setCantidadProducto(Integer.parseInt(paramCantidad.getText()));
        setValorProducto(Integer.parseInt(paramValor.getText()));
        
        cnxPostgreSql conexionPostgresql = new cnxPostgreSql();
        String consulta = "UPDATE public.productos SET nombre = ?, cantidad = ?, valor_unidad = ? WHERE id_producto = ?;";
        
        try {
            CallableStatement cnx = conexionPostgresql.establecerConexion().prepareCall(consulta);
            
            cnx.setString(1, getNombreProducto());
            cnx.setInt(2, getCantidadProducto());
            cnx.setLong(3, getValorProducto());
            cnx.setInt(4, getIdProducto());
            
            cnx.execute();
            
            JOptionPane.showMessageDialog(null, "Registro modificado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error de registro, "+e.toString());
        }
        
    }
    
    public void EliminarProducto(JTextField paramIdProducto){
        setIdProducto(Integer.parseInt(paramIdProducto.getText()));
        
        cnxPostgreSql conexionPostgresql = new cnxPostgreSql();
        String consulta = "DELETE FROM public.productos WHERE id_producto = ?;";
        
        try {
            CallableStatement cnx = conexionPostgresql.establecerConexion().prepareCall(consulta);

            cnx.setInt(1, getIdProducto());
            cnx.execute();
            
            JOptionPane.showMessageDialog(null, "Registro Eliminado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error de registro, "+e.toString());
        }
    }
}
