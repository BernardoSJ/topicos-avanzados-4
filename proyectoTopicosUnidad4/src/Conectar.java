import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
public class Conectar {
    Connection con=null;
    
    public Connection conexion(){
        try{
        Class.forName("com.mysql.jdbc.Driver");
        con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/residencias","root","");
        //JOptionPane.showMessageDialog(null,"Conectado");
        }catch(ClassNotFoundException | SQLException e){
            
        }
        //JOptionPane.showMessageDialog(null,"La conexion se llevo con exito");
        return con;
    }
    
    
}
