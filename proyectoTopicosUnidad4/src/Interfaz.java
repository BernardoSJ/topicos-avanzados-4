
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Bernardo SJ
 */
public class Interfaz extends javax.swing.JFrame {

    /**
     * Creates new form Interfaz
     */
    Conectar con = new Conectar();
    Connection reg = con.conexion();

    public Interfaz() {
        initComponents();
        cargartablaresidencias("", "", "");
        cargarTablaAsesores("", "", "");
        cargarTablaProyectosreg("", "", "");
        cargarTablaProyectosdisp("", "", "");
        cargarTablaResidentes("", "");
        cargarTablaEmpresas("", "");
        cargarComboEmp();
        cargarCombosCate();
        cargarComboProyectos();
        cargarComboAsesores();
        areaob.setLineWrap(true);
        direccion.setLineWrap(true);
        aread.setLineWrap(true);
        areao.setLineWrap(true);
        this.setResizable(false);
        ventanaregistros.setResizable(false);
        ventanainfo.setResizable(false);
        
        jPanel1.setBackground(Color.decode("#92978a"));
        jPanel2.setBackground(Color.decode("#92978a"));
        jPanel3.setBackground(Color.decode("#92978a"));
        jPanel4.setBackground(Color.decode("#92978a"));
        jPanel5.setBackground(Color.decode("#92978a"));
        jPanel6.setBackground(Color.decode("#92978a"));
        ventanaregistros.setBackground(Color.decode("#92978a"));
        ventanainfo.setBackground(Color.decode("#92978a"));
    }

    public void cargarTablaResidentes(String criterio, String dato) {
        DefaultTableModel miModelo = new DefaultTableModel();
        miModelo.addColumn("NoControl");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("Id Asesor");
        miModelo.addColumn("Id Proyecto");
        tablaresidentes.setModel(miModelo);
        String sql = "";
        if (criterio.equals("") && dato.equals("")) {
            sql = "SELECT * FROM residentes";
        } else {
            sql = "SELECT * FROM residentes WHERE " + criterio + "='" + dato + "'";
        }
        String datos[] = new String[4];
        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                miModelo.addRow(datos);
            }
            tablaresidentes.setModel(miModelo);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarTablaProyectosreg(String dato, String criterio, String tabla) {
        DefaultTableModel miModelo = new DefaultTableModel();
        miModelo.addColumn("Id proyecto");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("Id categoria");
        miModelo.addColumn("Id Empresa");
        miModelo.addColumn("Cantidad Est");
        miModelo.addColumn("Categoria");
        miModelo.addColumn("Nombre empresa");
        tablaregistrop.setModel(miModelo);
        String sql = "";
        if (dato.equals("") && criterio.equals("") && tabla.equals("")) {
            sql = "SELECT * FROM proyectos INNER JOIN empresas ON proyectos.idempresa=empresas.idempresa"
                    + " INNER JOIN categoria ON proyectos.idcategoria=categoria.idcategoria";
        } else {
            sql = "SELECT * FROM proyectos INNER JOIN empresas ON proyectos.idempresa=empresas.idempresa"
                    + " INNER JOIN categoria ON proyectos.idcategoria=categoria.idcategoria WHERE " + tabla + "." + criterio + "='" + dato + "'";
        }
        String datos[] = new String[7];

        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(13);
                datos[6] = rs.getString(8);
                miModelo.addRow(datos);
            }
            tablaregistrop.setModel(miModelo);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarTablaProyectosdisp(String dato, String criterio, String tabla) {
        DefaultTableModel miModelo = new DefaultTableModel();
        miModelo.addColumn("Id proyecto");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("Id categoria");
        miModelo.addColumn("Id Empresa");
        miModelo.addColumn("Cantidad Est");
        miModelo.addColumn("Categoria");
        miModelo.addColumn("Nombre empresa");
        tablapl.setModel(miModelo);
        String sql = "";
        if (dato.equals("") && criterio.equals("") && tabla.equals("")) {
            sql = "SELECT * FROM proyectos INNER JOIN empresas ON proyectos.idempresa=empresas.idempresa"
                    + " INNER JOIN categoria ON proyectos.idcategoria=categoria.idcategoria WHERE cantidadest<>0";
        } else {
            sql = "SELECT * FROM proyectos INNER JOIN empresas ON proyectos.idempresa=empresas.idempresa"
                    + " INNER JOIN categoria ON proyectos.idcategoria=categoria.idcategoria WHERE " + tabla + "." + criterio + "='" + dato + "' AND cantidadest<>0";
        }
        String datos[] = new String[7];

        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(13);
                datos[6] = rs.getString(8);
                miModelo.addRow(datos);
            }
            tablapl.setModel(miModelo);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarCombosCate() {
        comboc.removeAllItems();
        comboesp.removeAllItems();
        String sql = "SELECT * FROM categoria";
        String dato[] = new String[2];
        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                comboc.addItem(dato[0] + " " + dato[1]);
                comboesp.addItem(dato[0] + " " + dato[1]);
            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargartablaresidencias(String criterio, String dato, String tabla) {
        DefaultTableModel miModelo = new DefaultTableModel();
        miModelo.addColumn("No Control");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("IdAsesor");
        miModelo.addColumn("IdProyecto");
        miModelo.addColumn("Nombre Proyecto");
        miModelo.addColumn("Nombre Asesor");

        tablaresidencias.setModel(miModelo);
        String sql = "";
        String datos[] = new String[6];
        if (dato.equals("") && criterio.equals("") && tabla.equals("")) {
            sql = "select * from residentes inner join proyectos on residentes.idproyecto=proyectos.idproyecto\n"
                    + "inner join asesores on residentes.idasesor=asesores.idasesor";
        } else {
            sql = "select * from residentes inner join proyectos on residentes.idproyecto=proyectos.idproyecto\n"
                    + "inner join asesores on residentes.idasesor=asesores.idasesor WHERE " + tabla + "." + criterio + "='" + dato + "'";

        }
        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(6);
                datos[5] = rs.getString(12);

                miModelo.addRow(datos);
            }
            tablaresidencias.setModel(miModelo);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarTablaEmpresas(String dato, String criterio) {
        DefaultTableModel miModelo = new DefaultTableModel();
        miModelo.addColumn("Id empresa");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("Telefono");
        miModelo.addColumn("E-Mail");
        miModelo.addColumn("Direccion");
        tablaempresas.setModel(miModelo);
        String datos[] = new String[5];
        String sql = "";
        if (dato.equals("") || criterio.equals("")) {
            sql = "SELECT * FROM empresas";
        } else {
            sql = "SELECT * FROM empresas WHERE " + criterio + "='" + dato + "'";
        }
        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);

                miModelo.addRow(datos);
            }
            tablaempresas.setModel(miModelo);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarTablaAsesores(String dato, String criterio, String tabla) {
        DefaultTableModel miModelo = new DefaultTableModel();
        miModelo.addColumn("Id asesor");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("Id Categoria");
        miModelo.addColumn("Categoria");
        tablaasesores.setModel(miModelo);
        String datos[] = new String[4];
        String sql = "";
        if (dato.equals("") && criterio.equals("") && criterio.equals("")) {
            sql = "SELECT * FROM asesores INNER JOIN categoria ON asesores.idcategoria=categoria.idcategoria";
        } else {
            sql = "SELECT * FROM asesores INNER JOIN categoria ON asesores.idcategoria=categoria.idcategoria WHERE " + tabla + "." + criterio + "='" + dato + "'";
        }

        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(5);

                miModelo.addRow(datos);
            }
            tablaasesores.setModel(miModelo);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarComboEmp() {
        comboe.removeAllItems();
        String datos[] = new String[2];
        String sql = "SELECT idempresa,nombre FROM empresas";
        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                comboe.addItem(datos[0] + " " + datos[1]);

            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ventanaregistros = new javax.swing.JDialog();
        jLabel30 = new javax.swing.JLabel();
        combopc = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        comboase = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        labelnum = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        ventanainfo = new javax.swing.JDialog();
        jLabel33 = new javax.swing.JLabel();
        labelproyecto = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        labelempresa = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        labeltelefono = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        labelemail = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        aread = new javax.swing.JTextArea();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        areao = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablapl = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        buscarpd = new javax.swing.JTextField();
        jButton22 = new javax.swing.JButton();
        combopd = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtnum = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        nombres = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        apaterno = new javax.swing.JTextField();
        amaterno = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaresidentes = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        buscarr = new javax.swing.JTextField();
        comboreb = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        nombrep = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        comboc = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cantalum = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        comboe = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaregistrop = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        buscarp = new javax.swing.JTextField();
        combop = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        areaob = new javax.swing.JTextArea();
        jButton23 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        nombreempresa = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        telefonoem = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        mailemp = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        buscaremp = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaempresas = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        direccion = new javax.swing.JTextArea();
        jButton16 = new javax.swing.JButton();
        comboemp = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaresidencias = new javax.swing.JTable();
        jButton21 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        buscarr3 = new javax.swing.JTextField();
        comboaop1 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        amaterno2 = new javax.swing.JTextField();
        apaterno2 = new javax.swing.JTextField();
        nombres2 = new javax.swing.JTextField();
        numeroasesor = new javax.swing.JTextField();
        jButton18 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        buscaras = new javax.swing.JTextField();
        comboas = new javax.swing.JComboBox<>();
        jButton19 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaasesores = new javax.swing.JTable();
        jButton20 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        comboesp = new javax.swing.JComboBox<>();
        jButton24 = new javax.swing.JButton();

        ventanaregistros.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        ventanaregistros.setBounds(new java.awt.Rectangle(0, 0, 400, 236));
        ventanaregistros.setFocusTraversalPolicyProvider(true);
        ventanaregistros.setModal(true);

        jLabel30.setText("Proyecto:");

        jLabel31.setText("Asesor:");

        jLabel32.setText("Alumno:");

        jButton25.setText("Aceptar");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ventanaregistrosLayout = new javax.swing.GroupLayout(ventanaregistros.getContentPane());
        ventanaregistros.getContentPane().setLayout(ventanaregistrosLayout);
        ventanaregistrosLayout.setHorizontalGroup(
            ventanaregistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaregistrosLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(ventanaregistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ventanaregistrosLayout.createSequentialGroup()
                        .addGroup(ventanaregistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31))
                        .addGap(18, 18, 18)
                        .addGroup(ventanaregistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combopc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ventanaregistrosLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelnum, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ventanaregistrosLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jButton25)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        ventanaregistrosLayout.setVerticalGroup(
            ventanaregistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaregistrosLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(ventanaregistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(labelnum, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(ventanaregistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(combopc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(ventanaregistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(comboase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jButton25)
                .addGap(30, 30, 30))
        );

        ventanainfo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ventanainfo.setBounds(new java.awt.Rectangle(0, 0, 471, 485));
        ventanainfo.setFocusTraversalPolicyProvider(true);
        ventanainfo.setModal(true);

        jLabel33.setText("Proyecto: ");

        labelproyecto.setText("jLabel34");

        jLabel34.setText("Empresa:");

        labelempresa.setText("jLabel35");

        jLabel35.setText("Telefono:");

        labeltelefono.setText("jLabel36");

        jLabel36.setText("E-Mail:");

        labelemail.setText("jLabel37");

        jLabel37.setText("Dirección:");

        aread.setColumns(20);
        aread.setRows(5);
        jScrollPane9.setViewportView(aread);

        jLabel38.setText("Observaciones");

        areao.setColumns(20);
        areao.setRows(5);
        jScrollPane10.setViewportView(areao);

        javax.swing.GroupLayout ventanainfoLayout = new javax.swing.GroupLayout(ventanainfo.getContentPane());
        ventanainfo.getContentPane().setLayout(ventanainfoLayout);
        ventanainfoLayout.setHorizontalGroup(
            ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanainfoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ventanainfoLayout.createSequentialGroup()
                        .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36))
                        .addGap(18, 18, 18)
                        .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelproyecto, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(labelempresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labeltelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelemail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(ventanainfoLayout.createSequentialGroup()
                        .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addComponent(jLabel38))
                        .addGap(32, 32, 32)
                        .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(173, Short.MAX_VALUE))
        );
        ventanainfoLayout.setVerticalGroup(
            ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanainfoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(labelproyecto))
                .addGap(34, 34, 34)
                .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(labelempresa))
                .addGap(37, 37, 37)
                .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(labeltelefono))
                .addGap(43, 43, 43)
                .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(labelemail))
                .addGap(31, 31, 31)
                .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(ventanainfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("PROYECTOS CON ESPACIO");

        tablapl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablapl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaplMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tablapl);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Buscar: ");

        jButton22.setText("Buscar");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        combopd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Id proyecto", "Nombre", "Id categoria", "Categoria", "IdEmpresa", "Nombre empresa", "Cant Estudiantes" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarpd, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combopd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton22)))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel28)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(buscarpd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton22)
                    .addComponent(combopd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inicio", jPanel1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Residentes");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Número de control");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre (s): ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("A. Paterno:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("A. Materno:");

        tablaresidentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaresidentes);

        jButton1.setText("Registrar Residente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Limpiar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Asignar residencia");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Buscar:");

        comboreb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NoControl", "Nombre", "Id proyecto", "Id asesor" }));

        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton14.setText("Cargar informacion");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(70, 70, 70)
                            .addComponent(jLabel2))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel3)
                            .addGap(38, 38, 38)
                            .addComponent(txtnum, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel1)
                            .addGap(34, 34, 34)
                            .addComponent(nombres)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(apaterno))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(amaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(32, 32, 32)
                                .addComponent(jButton2)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton14))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(467, 467, 467)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(buscarr, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboreb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(buscarr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboreb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(nombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(apaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(amaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addGap(38, 38, 38))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton14))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Residentes", jPanel2);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Proyectos");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nombre: ");

        nombrep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombrepKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Categoria");

        jButton6.setText("Añadir categoria");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Cantidad alumno: ");

        cantalum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cantalumKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Empresa a la que pertenece el proyecto:");

        jButton7.setText("Registrar Proyecto");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        tablaregistrop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaregistrop);

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Buscar:");

        combop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Id proyecto", "Nombre", "Id categoria", "Categoria", "IdEmpresa", "Nombre empresa", "Cant Estudiantes" }));

        jButton8.setText("Buscar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Proyecto Terminado");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton15.setText("Cargar informacion proyecto");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Observaciones");

        areaob.setColumns(20);
        areaob.setRows(5);
        jScrollPane6.setViewportView(areaob);

        jButton23.setText("Limpiar");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buscarp, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(combop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addGap(19, 19, 19))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombrep, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(comboc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cantalum, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15))
                .addGap(28, 28, 28))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton7)
                .addGap(27, 27, 27)
                .addComponent(jButton23)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(buscarp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8)
                            .addComponent(jButton9)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel7)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton15))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(nombrep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(comboc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cantalum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(comboe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel25))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton23))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Proyectos", jPanel3);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Empresas");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nombre:");

        nombreempresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreempresaKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Telefono");

        telefonoem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                telefonoemKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("E-Mail");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Direccion");

        jButton10.setText("Registar Empresa");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Limpiar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Buscar:");

        jButton12.setText("Buscar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Romper Vinculo");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        tablaempresas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tablaempresas);

        direccion.setColumns(20);
        direccion.setRows(5);
        jScrollPane4.setViewportView(direccion);

        jButton16.setText("Cargar informacion");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        comboemp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Id empresa", "Nombre", "Telefono", "Mail", "Dirección" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton16)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(410, 410, 410)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buscaremp, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel13))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(38, 38, 38)
                                .addComponent(nombreempresa, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(34, 34, 34)
                                .addComponent(telefonoem)))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addGap(18, 18, 18)
                            .addComponent(mailemp, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel17)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jButton10)
                            .addGap(32, 32, 32)
                            .addComponent(jButton11)))
                    .addContainerGap(749, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(buscaremp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12)
                    .addComponent(jButton13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton16)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90))))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(73, 73, 73)
                    .addComponent(jLabel13)
                    .addGap(25, 25, 25)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(nombreempresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(39, 39, 39)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(telefonoem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(30, 30, 30)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(mailemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(44, 44, 44)
                    .addComponent(jLabel17)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton10)
                        .addComponent(jButton11))
                    .addGap(35, 35, 35)))
        );

        jTabbedPane1.addTab("Empresas", jPanel4);

        tablaresidencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tablaresidencias);

        jButton21.setText("Terminar residencia");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Buscar:");

        comboaop1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NoControl", "Nombre", "Id proyecto" }));

        jButton5.setText("Buscar");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton21)
                        .addGap(85, 85, 85)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarr3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboaop1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton5))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27)
                        .addComponent(buscarr3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboaop1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5))
                    .addComponent(jButton21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );

        jTabbedPane1.addTab("Residencias en Proceso", jPanel6);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Asesores");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Número de Tarjeta");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Nombre (s): ");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("A. Paterno:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("A. Materno:");

        jButton17.setText("Registrar Asesor");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("Limpiar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Buscar:");

        comboas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Id asesor", "Nombre", "Id Categoria", "Categoria" }));

        jButton19.setText("Buscar");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        tablaasesores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tablaasesores);

        jButton20.setText("Cargar informacion");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Especialidad:");

        jButton24.setText("Retirar Asesor");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(comboesp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(845, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(buscaras, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(comboas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jButton19)
                                .addGap(36, 36, 36)
                                .addComponent(jButton24)
                                .addGap(80, 80, 80))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton20)
                                .addGap(357, 357, 357))))))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel19))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(38, 38, 38)
                                .addComponent(numeroasesor, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(34, 34, 34)
                                .addComponent(nombres2)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(apaterno2))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(amaterno2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jButton17)
                            .addGap(32, 32, 32)
                            .addComponent(jButton18)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(29, 29, 29)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19)
                    .addComponent(comboas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscaras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jButton24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 275, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(comboesp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jButton20)
                .addGap(58, 58, 58))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(70, 70, 70)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addGap(25, 25, 25)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel20)
                                .addComponent(numeroasesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(39, 39, 39)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(nombres2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel22)
                                .addComponent(apaterno2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(41, 41, 41)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel23)
                                .addComponent(amaterno2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton17)
                        .addComponent(jButton18))
                    .addGap(35, 35, 35)))
        );

        jTabbedPane1.addTab("Asesores", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String noControl = txtnum.getText().toUpperCase();
        String nombre = nombres.getText().toUpperCase();
        String paterno = apaterno.getText().toUpperCase();
        String materno = amaterno.getText().toUpperCase();
        if (noControl.equals("") || nombre.equals("") || paterno.equals("") || materno.equals("")) {
JOptionPane.showMessageDialog(null,"Asegurate de haber llenado las cajas correctamente");
        } else {
            String sql4 = "INSERT INTO residentes (nocontrol,nombre) VALUES (?,?)";
            try {
                PreparedStatement ps = reg.prepareStatement(sql4);
                ps.setString(1, noControl);
                ps.setString(2, nombre + " " + paterno + " " + materno);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registro de residente hecho");
                limpiarCajasResidentes();
                cargarTablaResidentes("", "");
            } catch (SQLException ex) {
                System.out.println("Hola");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String nombreem = nombreempresa.getText().toUpperCase();
        String telefono = telefonoem.getText().toUpperCase();
        String mail = mailemp.getText();
        String direccione = direccion.getText().toUpperCase();

        if (nombreem.equals("") || telefono.equals("") || mail.equals("") || direccione.equals("")) {
JOptionPane.showMessageDialog(null,"Asegurate de haber llenado las cajas correctamente");
        } else {
            String sql4 = "INSERT INTO empresas (nombre,telefono,mail,direccion) VALUES (?,?,?,?)";
            try {
                PreparedStatement ps = reg.prepareStatement(sql4);
                ps.setString(1, nombreem);
                ps.setString(2, telefono);
                ps.setString(3, mail);
                ps.setString(4, direccione);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registro de empresa hecho");
                cargarTablaEmpresas("", "");
                limpiarCajasEmpresa();
                cargarComboEmp();
            } catch (SQLException ex) {
                System.out.println("Hola");
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String nombre = nombrep.getText().toUpperCase();
        String categoria = comboc.getSelectedItem().toString().toUpperCase();
        String cantidad = cantalum.getText();
        String empresa = comboe.getSelectedItem().toString().toUpperCase();
        String observaciones = areaob.getText().toUpperCase();
        if (nombre.equals("") || cantidad.equals("") || empresa.equals("") || observaciones.equals("")) {
               JOptionPane.showMessageDialog(null,"Asegurate de haber llenado las cajas correctamente");
        } else {
            String sql = "SELECT idcategoria FROM categoria";
            String datos[] = new String[1];
            String id = "";
            try {
                Statement sentencias = reg.createStatement();
                ResultSet rs = sentencias.executeQuery(sql);
                while (rs.next()) {
                    datos[0] = rs.getString(1);

                    if (categoria.contains(datos[0])) {
                        id = datos[0];

                    }
                }

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }

            String sqle = "SELECT idempresa FROM empresas";
            String datoe[] = new String[1];
            String idempresa = "";
            try {
                Statement sentencias = reg.createStatement();
                ResultSet rs = sentencias.executeQuery(sqle);
                while (rs.next()) {
                    datoe[0] = rs.getString(1);

                    if (empresa.contains(datoe[0])) {
                        idempresa = datoe[0];

                    }
                }

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }

            String sql2 = "INSERT INTO proyectos (nombre,idcategoria,idempresa,cantidadest,observaciones) VALUES (?,?,?,?,?)";
            try {
                PreparedStatement ps = reg.prepareStatement(sql2);
                ps.setString(1, nombre);
                ps.setInt(2, Integer.parseInt(id));
                ps.setInt(3, Integer.parseInt(idempresa));
                ps.setInt(4, Integer.parseInt(cantidad));
                ps.setString(5, observaciones);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registro del proyecto con exito");
                limpiarCajasProtyecto();
                cargarTablaProyectosreg("", "", "");
                cargarTablaProyectosdisp("","","");
                cargarComboProyectos();
            } catch (SQLException ex) {
                System.out.println("Hola");
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        boolean sePuede=true;
        String categoria="";
        try{
        categoria = JOptionPane.showInputDialog(null, "Ingresa un nombre").toUpperCase();
        }catch(Exception e){
            sePuede=false;    
                }
        if(categoria.contains("0") || categoria.contains("1") || categoria.contains("2") || categoria.contains("3")
           || categoria.contains("4") || categoria.contains("4") || categoria.contains("5") || categoria.contains("6")
                || categoria.contains("7") || categoria.contains("8") || categoria.contains("9")){
            sePuede=false;
        }
        if(sePuede){
        String sql = "INSERT INTO categoria (nombre) VALUES (?)";
        try {
            PreparedStatement ps = reg.prepareStatement(sql);
            ps.setString(1, categoria);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro de la categoria con exito");

            cargarCombosCate();
        } catch (SQLException ex) {
            System.out.println("Hola");
        }
        }else{
            JOptionPane.showMessageDialog(null,"El nombre de la categoria debe de estar lleno o no contener numeros");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        String numea = numeroasesor.getText().toUpperCase();
        String nombre = nombres2.getText().toUpperCase();
        String apatern = apaterno2.getText().toUpperCase();
        String amatern = amaterno2.getText().toUpperCase();
        String especia = comboesp.getSelectedItem().toString().toUpperCase();
        if (numea.equals("") || nombre.equals("") || apatern.equals("") || amatern.equals("")) {
JOptionPane.showMessageDialog(null,"Asegurate de haber llenado las cajas correctamente");
        } else {
            String sql = "SELECT idcategoria FROM categoria";
            String datos[] = new String[1];
            String id = "";
            try {
                Statement sentencias = reg.createStatement();
                ResultSet rs = sentencias.executeQuery(sql);
                while (rs.next()) {
                    datos[0] = rs.getString(1);

                    if (especia.contains(datos[0])) {
                        id = datos[0];

                    }
                }

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }

            String sql2 = "INSERT INTO asesores (idasesor,nombre,idcategoria) VALUES (?,?,?)";
            try {
                PreparedStatement ps = reg.prepareStatement(sql2);
                ps.setString(1, numea);
                ps.setString(2, nombre + " " + apatern + " " + amatern);
                ps.setInt(3, Integer.parseInt(id));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registro del asesor con exito");
                cargarTablaAsesores("", "", "");
                cargarComboAsesores();
            } catch (SQLException ex) {
                System.out.println("Hola");
            }
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String dato = buscarr.getText().toUpperCase();
        if (dato.equals("")) {
            JOptionPane.showMessageDialog(null, "Asegurate de haber ingresado algo en la caja de texto de la busqueda");
        } else {
            String criterio = "";
            switch (comboreb.getSelectedItem().toString()) {
                case "NoControl":
                    criterio = "nocontrol";
                    break;
                case "Nombre":
                    criterio = "nombre";
                    break;
                case "Id proyecto":
                    criterio = "idproyecto";
                    break;
                case "Id asesor":
                    criterio = "idasesor";
                    break;
            }
            cargarTablaResidentes(criterio, dato);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String dato = buscarp.getText().toUpperCase();
        if (dato.equals("")) {
            JOptionPane.showMessageDialog(null, "Asegurate de ingresar algo en la caja de busqueda");
        } else {
            String tabla = "";
            String criterio = "";
            switch (combop.getSelectedItem().toString()) {
                case "Id proyecto":
                    tabla = "proyectos";
                    criterio = "idproyecto";
                    break;
                case "Nombre":
                    tabla = "proyectos";
                    criterio = "nombre";
                    break;
                case "Id categoria":
                    tabla = "proyectos";
                    criterio = "idcategoria";
                    break;
                case "Categoria":
                    tabla = "categoria";
                    criterio = "nombre";
                    break;
                case "IdEmpresa":
                    tabla = "proyectos";
                    criterio = "idempresa";
                    break;
                case "Nombre empresa":
                    tabla = "empresas";
                    criterio = "nombre";
                    break;
                case "Cant Estudiantes":
                    tabla = "proyectos";
                    criterio = "cantidadest";
                    break;
            }
            cargarTablaProyectosreg(dato, criterio, tabla);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        String dato = buscaremp.getText().toUpperCase();
        if (dato.equals("")) {
            JOptionPane.showMessageDialog(null, "Asegurate de llenar la caja de texto de busqueda");
        } else {
            String criterio = "";
            switch (comboemp.getSelectedItem().toString()) {
                case "Id empresa":
                    criterio = "idempresa";
                    break;
                case "Nombre":
                    criterio = "nombre";
                    break;
                case "Telefono":
                    criterio = "telefono";
                    break;
                case "Mail":
                    criterio = "mail";
                    break;
                case "Dirección":
                    criterio = "direccion";
                    break;
            }
            cargarTablaEmpresas(dato, criterio);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        String dato = buscaras.getText().toUpperCase();
        if (dato.equals("")) {
            JOptionPane.showMessageDialog(null, "Asegurate de haber ingresado algo en la caja de texto de busqueda");
        } else {
            String tabla = "";
            String criterio = "";
            switch (comboas.getSelectedItem().toString()) {
                case "Id asesor":
                    tabla = "asesores";
                    criterio = "idasesor";
                    break;
                case "Nombre":
                    tabla = "asesores";
                    criterio = "nombre";
                    break;
                case "Id Categoria":
                    tabla = "asesores";
                    criterio = "idcategoria";
                    break;
                case "Categoria":
                    tabla = "categoria";
                    criterio = "nombre";
                    break;
            }
            cargarTablaAsesores(dato, criterio, tabla);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    void limpiarCajasResidentes() {
        txtnum.setText("");
        nombres.setText("");
        apaterno.setText("");
        amaterno.setText("");

    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiarCajasResidentes();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        int fila = tablaresidentes.getSelectedRow();
        if (jButton14.getText().equals("Cargar informacion")) {

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "No seleccionaste ninguna fila de la tabla de residentes");
            } else {
                jLabel1.setText("Nombre");
                apaterno.setEditable(false);
                amaterno.setEditable(false);
                jButton4.setEnabled(false);
                jButton3.setEnabled(false);
                jButton1.setEnabled(false);
                tablaresidentes.setEnabled(false);
                txtnum.setEditable(false);
                nombres.setText((String) tablaresidentes.getModel().getValueAt(tablaresidentes.getSelectedRow(), 1));
                jButton14.setText("Modificar informacion");
            }
        } else {
            if(nombres.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Asegurate de haber llenado las cajas de texto correctamente");
            }else{
            String cod = (String) tablaresidentes.getModel().getValueAt(tablaresidentes.getSelectedRow(), 0);
            System.out.println(cod);
            try {
                PreparedStatement ps = reg.prepareStatement("UPDATE residentes SET "
                        + "nombre='" + nombres.getText().toUpperCase() + "' WHERE nocontrol='" + cod + "'");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registro actualizado con exito");
                jLabel1.setText("Nombre (s)");
                apaterno.setEditable(true);
                amaterno.setEditable(true);
                jButton4.setEnabled(true);
                jButton3.setEnabled(true);
                jButton1.setEnabled(true);
                tablaresidentes.setEnabled(true);
                txtnum.setEditable(true);
                cargarTablaResidentes("","");
                jButton14.setText("Cargar informacion");
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }

    }//GEN-LAST:event_jButton14ActionPerformed

    void limpiarCajasProtyecto() {
        nombrep.setText("");
        cantalum.setText("");
        areaob.setText("");
    }
    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        limpiarCajasProtyecto();
    }//GEN-LAST:event_jButton23ActionPerformed

    void limpiarCajasEmpresa() {
        nombreempresa.setText("");
        telefonoem.setText("");
        mailemp.setText("");
        direccion.setText("");
    }
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        limpiarCajasEmpresa();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        int fila = tablaempresas.getSelectedRow();
        if (jButton16.getText().equals("Cargar informacion")) {
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Asegurate de haber seleccionado una fila de la tabla de empresas");
            } else {
                nombreempresa.setText((String) tablaempresas.getModel().getValueAt(tablaempresas.getSelectedRow(), 1));
                telefonoem.setText((String) tablaempresas.getModel().getValueAt(tablaempresas.getSelectedRow(), 2));
                mailemp.setText((String) tablaempresas.getModel().getValueAt(tablaempresas.getSelectedRow(), 3));
                direccion.setText((String) tablaempresas.getModel().getValueAt(tablaempresas.getSelectedRow(), 4));
                jButton10.setEnabled(false);
                jButton12.setEnabled(false);
                tablaempresas.setEnabled(false);
                jButton16.setText("Modificar Informacion");
            }
        } else {
            String nombreem = nombreempresa.getText().toUpperCase();
            String telefono = telefonoem.getText().toUpperCase();
            String mail = mailemp.getText();
            String direccione = direccion.getText().toUpperCase();

            if (nombreem.equals("") || telefono.equals("") || mail.equals("") || direccione.equals("")) {
JOptionPane.showMessageDialog(null,"Asegurate de haber llenado las cajas correctamente");
            } else {
                String cod = (String) tablaempresas.getModel().getValueAt(tablaempresas.getSelectedRow(), 0);
                try {
                    PreparedStatement ps = reg.prepareStatement("UPDATE empresas SET "
                            + "nombre='" + nombreem + "', telefono='" + telefono + "',mail='" + mail + "',direccion='" + direccione + "' WHERE idempresa='" + cod + "'");
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registro actualizado con exito");
                    jButton10.setEnabled(true);
                    jButton12.setEnabled(true);
                    tablaempresas.setEnabled(true);
                    jButton16.setText("Cargar Informacion");

                    cargarTablaEmpresas("", "");
                    limpiarCajasEmpresa();
                } catch (SQLException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        String dato = buscarpd.getText().toUpperCase();
        if (dato.equals("")) {
            JOptionPane.showMessageDialog(null, "Asegurate de ingresar algo en la caja de busqueda");
        } else {
            String tabla = "";
            String criterio = "";
            switch (combopd.getSelectedItem().toString()) {
                case "Id proyecto":
                    tabla = "proyectos";
                    criterio = "idproyecto";
                    break;
                case "Nombre":
                    tabla = "proyectos";
                    criterio = "nombre";
                    break;
                case "Id categoria":
                    tabla = "proyectos";
                    criterio = "idcategoria";
                    break;
                case "Categoria":
                    tabla = "categoria";
                    criterio = "nombre";
                    break;
                case "IdEmpresa":
                    tabla = "proyectos";
                    criterio = "idempresa";
                    break;
                case "Nombre empresa":
                    tabla = "empresas";
                    criterio = "nombre";
                    break;
                case "Cant Estudiantes":
                    tabla = "proyectos";
                    criterio = "cantidadest";
                    break;
            }
            cargarTablaProyectosdisp(dato, criterio, tabla);
        }
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        int fila = tablaregistrop.getSelectedRow();
        String datos[] = new String[7];
        if (jButton15.getText().equals("Cargar informacion proyecto")) {
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "No elegiste alguna fila de la tabla de proyectos");
            } else {
                String sql = "SELECT * FROM proyectos INNER JOIN empresas ON proyectos.idempresa=empresas.idempresa"
                        + " INNER JOIN categoria ON proyectos.idcategoria=categoria.idcategoria WHERE idproyecto='" + (String) tablaregistrop.getModel().getValueAt(tablaregistrop.getSelectedRow(), 0) + "'";

                try {
                    Statement sentencias = reg.createStatement();
                    ResultSet rs = sentencias.executeQuery(sql);
                    while (rs.next()) {
                        datos[0] = rs.getString(1);
                        datos[1] = rs.getString(2);
                        datos[2] = rs.getString(3);
                        datos[3] = rs.getString(4);
                        datos[4] = rs.getString(5);
                        datos[5] = rs.getString(6);
                        datos[6] = rs.getString(8);

                    }

                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
                nombrep.setText(datos[1]);
                cantalum.setText(datos[4]);
                areaob.setText(datos[5]);
                jButton8.setEnabled(false);
                jButton7.setEnabled(false);
                tablaregistrop.setEnabled(false);

                jButton15.setText("Modificar informacion");
            }
        } else {
            String nombre = nombrep.getText().toUpperCase();
            String cantid = cantalum.getText();
            String area = areaob.getText();

            String categoria = comboc.getSelectedItem().toString();
            String empresa = comboe.getSelectedItem().toString();

            if (nombre.equals("") || cantid.equals("") || area.equals("")) {
JOptionPane.showMessageDialog(null,"Asegurate de haber llenado las cajas correctamente");
            } else {
                String sql = "SELECT idcategoria FROM categoria";
                String datosc[] = new String[1];
                String id = "";
                try {
                    Statement sentencias = reg.createStatement();
                    ResultSet rs = sentencias.executeQuery(sql);
                    while (rs.next()) {
                        datosc[0] = rs.getString(1);

                        if (categoria.contains(datosc[0])) {
                            id = datosc[0];

                        }
                    }

                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }

                String sqle = "SELECT idempresa FROM empresas";
                String datoe[] = new String[1];
                String idempresa = "";
                try {
                    Statement sentencias = reg.createStatement();
                    ResultSet rs = sentencias.executeQuery(sqle);
                    while (rs.next()) {
                        datoe[0] = rs.getString(1);

                        if (empresa.contains(datoe[0])) {
                            idempresa = datoe[0];

                        }
                    }

                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }

                String cod = (String) tablaregistrop.getModel().getValueAt(tablaregistrop.getSelectedRow(), 0);
                try {
                    PreparedStatement ps = reg.prepareStatement("UPDATE proyectos SET "
                            + "nombre='" + nombre + "',idcategoria='" + Integer.parseInt(id) + "',idempresa='" + Integer.parseInt(idempresa) + "',cantidadest='" + cantid + "',observaciones='" + area + "' WHERE idproyecto='" + cod + "'");
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registro actualizado con exito");
                    jButton8.setEnabled(true);
                    jButton7.setEnabled(true);
                    tablaregistrop.setEnabled(true);
                    cargarTablaProyectosdisp("", "", "");
                    cargarTablaProyectosreg("", "", "");
                    limpiarCajasProtyecto();
                } catch (SQLException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }

                jButton15.setText("Cargar informacion proyecto");
            }
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int fila = tablaregistrop.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Asegurate de haber seleccionado una fila en la tabla proyectos");
        } else {
            boolean sePuede = true;
            String sql = "SELECT idproyecto FROM residentes";
            String datos[] = new String[1];
            try {
                Statement sentencias = reg.createStatement();
                ResultSet rs = sentencias.executeQuery(sql);
                while (rs.next()) {
                    datos[0] = rs.getString(1);
                    if (tablaregistrop.getModel().getValueAt(fila, 0).equals(datos[0])) {
                        sePuede = false;
                    }
                }

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (sePuede) {
                int ax = JOptionPane.showConfirmDialog(null, "Seguro que deseas eliminar este proyecto");
                if (ax == JOptionPane.YES_OPTION) {

                    String cod = (String) tablaregistrop.getModel().getValueAt(fila, 0);

                    try {
                        PreparedStatement pst = reg.prepareStatement("DELETE FROM proyectos WHERE  idproyecto='" + cod + "'");
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Registro eliminado con exito");
                        cargarTablaProyectosreg("", "", "");

                    } catch (Exception e) {
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No puedes eliminar este proyecto ya que esta siendo realizado");
            }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed

        String nombre = nombres2.getText().toUpperCase();
        String apatern = apaterno2.getText().toUpperCase();
        String amatern = amaterno2.getText().toUpperCase();
        String especia = comboesp.getSelectedItem().toString().toUpperCase();
        int fila = tablaasesores.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Asegurate de haber elegido una fila de la tabla");
        } else {
            if (jButton20.getText().equals("Cargar informacion")) {
                jLabel21.setText("Nombre");
                jButton17.setEnabled(false);
                jButton19.setEnabled(false);
                tablaasesores.setEnabled(false);
                apaterno2.setEditable(false);
                amaterno2.setEditable(false);
                jButton20.setText("Modificar informacion");
                numeroasesor.setEditable(false);
                nombres2.setText((String) tablaasesores.getModel().getValueAt(fila, 1));
            } else {
                if (nombre.equals("")) {
JOptionPane.showMessageDialog(null,"Asegurate de haber llenado las cajas correctamente");
                } else {
                    String sql = "SELECT idcategoria FROM categoria";
                    String datos[] = new String[1];
                    String id = "";
                    try {
                        Statement sentencias = reg.createStatement();
                        ResultSet rs = sentencias.executeQuery(sql);
                        while (rs.next()) {
                            datos[0] = rs.getString(1);

                            if (especia.contains(datos[0])) {
                                id = datos[0];

                            }
                        }

                    } catch (SQLException ex) {
                        java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    String cod = (String) tablaasesores.getModel().getValueAt(tablaasesores.getSelectedRow(), 0);
                    try {
                        PreparedStatement ps = reg.prepareStatement("UPDATE asesores SET "
                                + "nombre='" + nombre + "',idcategoria='" + id + "' WHERE idasesor='" + cod + "'");
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Registro actualizado con exito");

                        cargarTablaAsesores("", "", "");
                        limpiarCajasAsesores();
                    } catch (SQLException ex) {
                        Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    jLabel21.setText("Nombre (s)");
                    jButton17.setEnabled(true);
                    jButton19.setEnabled(true);
                    tablaasesores.setEnabled(true);
                    apaterno2.setEditable(true);
                    amaterno2.setEditable(true);
                    jButton20.setText("Cargar informacion");
                    numeroasesor.setEditable(true);
                }
            }
        }
    }//GEN-LAST:event_jButton20ActionPerformed
    public void limpiarCajasAsesores() {
        numeroasesor.setText("");
        nombres2.setText("");
        apaterno2.setText("");
        amaterno2.setText("");
    }
    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        limpiarCajasAsesores();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        int fila = tablaempresas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Asegurate de haber seleccionado una fila en la tabla empresas");
        } else {
            boolean sePuede = true;
            String sql = "SELECT idempresa FROM proyectos";
            String datos[] = new String[1];
            try {
                Statement sentencias = reg.createStatement();
                ResultSet rs = sentencias.executeQuery(sql);
                while (rs.next()) {
                    datos[0] = rs.getString(1);
                    if (tablaempresas.getModel().getValueAt(fila, 0).equals(datos[0])) {
                        sePuede = false;
                    }
                }

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (sePuede) {
                int ax = JOptionPane.showConfirmDialog(null, "Seguro que deseas eliminar esta empresa");
                if (ax == JOptionPane.YES_OPTION) {

                    String cod = (String) tablaempresas.getModel().getValueAt(fila, 0);

                    try {
                        PreparedStatement pst = reg.prepareStatement("DELETE FROM empresas WHERE idempresa='" + cod + "'");
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Registro eliminado con exito");
                        cargarTablaEmpresas("", "");

                    } catch (Exception e) {
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No puedes eliminar esta empresa ya que aún tiene proyectos en la lista");
            }
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        int fila = tablaasesores.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Asegurate de haber seleccionado una fila en la tabla asesores");
        } else {
            boolean sePuede = true;
            String sql = "SELECT idasesor FROM residentes";
            String datos[] = new String[1];
            try {
                Statement sentencias = reg.createStatement();
                ResultSet rs = sentencias.executeQuery(sql);
                while (rs.next()) {
                    datos[0] = rs.getString(1);
                    if (tablaasesores.getModel().getValueAt(fila, 0).equals(datos[0])) {
                        sePuede = false;
                    }
                }

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (sePuede) {
                int ax = JOptionPane.showConfirmDialog(null, "Seguro que deseas eliminar este asesor");
                if (ax == JOptionPane.YES_OPTION) {

                    String cod = (String) tablaasesores.getModel().getValueAt(fila, 0);

                    try {
                        PreparedStatement pst = reg.prepareStatement("DELETE FROM asesores WHERE  idproyecto='" + cod + "'");
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Registro eliminado con exito");
                        cargarTablaAsesores("", "", "");

                    } catch (Exception e) {
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No puedes eliminar este asesor ya que esta asignado con un alumno");
            }
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int fila = tablaresidentes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Asegurate de haber seleccionado una fila en la tabla de residentes");
        } else {
            String sql = "SELECT * FROM residentes WHERE nocontrol='" + tablaresidentes.getModel().getValueAt(fila, 0) + "'";
            String da[] = new String[4];
            boolean sePuede = false;
            try {
                Statement sentencias = reg.createStatement();
                ResultSet rs = sentencias.executeQuery(sql);
                while (rs.next()) {
                    da[0] = rs.getString(1);
                    da[1] = rs.getString(2);
                    da[2] = rs.getString(3);
                    da[3] = rs.getString(4);
                    System.out.println(da[2]);
                    if (da[2] == null) {
                        sePuede = true;

                    }
                }

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (sePuede) {
                labelnum.setText(da[0]);
                ventanaregistros.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Ese alumno ya tiene un proyecto asignado");
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    public void cargarComboProyectos() {
        combopc.removeAllItems();
        String sql = "SELECT idproyecto,nombre FROM proyectos WHERE cantidadest<>0";
        String datos[] = new String[2];
        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                combopc.addItem(datos[0] + " " + datos[1]);
            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarComboAsesores() {
        comboase.removeAllItems();
        String sql = "SELECT idasesor,nombre FROM asesores";
        String datos[] = new String[2];
        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                comboase.addItem(datos[0] + " " + datos[1]);
            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        String sql = "SELECT idproyecto FROM proyectos";
        String datos[] = new String[1];
        String id = "";
        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);

                if (combopc.getSelectedItem().toString().contains(datos[0])) {
                    id = datos[0];

                }
            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqla = "SELECT idasesor FROM asesores";
        String datosa[] = new String[1];
        String ida = "";
        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sqla);
            while (rs.next()) {
                datosa[0] = rs.getString(1);

                if (comboase.getSelectedItem().toString().contains(datosa[0])) {
                    ida = datosa[0];

                }
            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql2 = "SELECT cantidadest FROM proyectos WHERE idproyecto='" + id + "'";
        String datp[] = new String[1];
        try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql2);
            while (rs.next()) {
                datp[0] = rs.getString(1);
            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        int ax = JOptionPane.showConfirmDialog(null, "El proyecto que esta en el combo es el correcto");
        if (ax == JOptionPane.YES_OPTION) {
            if (datp[0].equals("2")) {

                int ax2 = JOptionPane.showConfirmDialog(null, "Este proyecto es lo suficiente para dos alumnos");
        if (ax2 == JOptionPane.YES_OPTION) {
           
            String cod = labelnum.getText();
                    try {
                        PreparedStatement ps = reg.prepareStatement("UPDATE residentes SET "
                                + "idproyecto='" + Integer.parseInt(id) + "',idasesor='" + Integer.parseInt(ida)+ "' WHERE nocontrol='" + cod + "'");
                        ps.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                    }
               
                    
                String cod2 = id;
                    try {
                        PreparedStatement ps = reg.prepareStatement("UPDATE proyectos SET "
                                + "cantidadest='1' WHERE idproyecto='" + cod2 + "'");
                        ps.executeUpdate();
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(null,"Residencia asignada");
                    cargarTablaProyectosdisp("","","");
                    cargarTablaProyectosreg("","","");
                    cargartablaresidencias("","","");
                    cargarTablaResidentes("","");
            
        }else{
            if(ax2==JOptionPane.NO_OPTION){
                
                String cod = labelnum.getText();
                    try {
                        PreparedStatement ps = reg.prepareStatement("UPDATE residentes SET "
                                + "idproyecto='" + Integer.parseInt(id) + "',idasesor='" + Integer.parseInt(ida)+ "' WHERE nocontrol='" + cod + "'");
                        ps.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                    }
               
                    
                String cod2 = id;
                    try {
                        PreparedStatement ps = reg.prepareStatement("UPDATE proyectos SET "
                                + "cantidadest='0' WHERE idproyecto='" + cod2 + "'");
                        ps.executeUpdate();
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(null,"Residencia asignada");
                    cargarTablaProyectosdisp("","","");
                    cargarTablaProyectosreg("","","");
                    cargartablaresidencias("","","");
                    cargarTablaResidentes("","");
                
            }
        }
                
            } else {
               String cod = labelnum.getText();
                    try {
                        PreparedStatement ps = reg.prepareStatement("UPDATE residentes SET "
                                + "idproyecto='" + Integer.parseInt(id) + "',idasesor='" + Integer.parseInt(ida)+ "' WHERE nocontrol='" + cod + "'");
                        ps.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                    }
               
                    
                String cod2 = id;
                    try {
                        PreparedStatement ps = reg.prepareStatement("UPDATE proyectos SET "
                                + "cantidadest='0' WHERE idproyecto='" + cod2 + "'");
                        ps.executeUpdate();
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(null,"Residencia asignada");
                    cargarTablaProyectosdisp("","","");
                    cargarTablaProyectosreg("","","");
                    cargartablaresidencias("","","");
                    cargarTablaResidentes("","");
            }
        }
        ventanaregistros.dispose();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        int fila=tablaresidencias.getSelectedRow();
        if(fila==-1){
            JOptionPane.showMessageDialog(null,"Asegurate de haber seleccionado algo en la tabla de residencias");
        }else{
            
            String numero=(String) tablaresidencias.getModel().getValueAt(fila, 0);
            String proyecto=(String)tablaresidencias.getModel().getValueAt(fila,3);
            int ax = JOptionPane.showConfirmDialog(null, "El alumno ya entrego todo lo requerido");
        if (ax == JOptionPane.YES_OPTION) {
            try {
                        PreparedStatement pst = reg.prepareStatement("DELETE FROM residentes WHERE nocontrol='" +numero + "'");
                        pst.executeUpdate();
                        

                    } catch (Exception e) {
                    }
            
            try {
                        PreparedStatement pst = reg.prepareStatement("DELETE FROM proyectos WHERE idproyecto='" + proyecto + "'");
                        pst.executeUpdate();
                        

                    } catch (Exception e) {
                    }
            JOptionPane.showMessageDialog(null,"Residencia terminada");
            cargarTablaProyectosreg("","","");
            cargarTablaResidentes("","");
            cargartablaresidencias("","","");
        }
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void tablaplMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaplMouseClicked
       int fila=tablapl.getSelectedRow();
      String id=(String) tablapl.getModel().getValueAt(fila, 0);
      String sql = "SELECT * FROM proyectos INNER JOIN empresas ON proyectos.idempresa=empresas.idempresa"
                    + " INNER JOIN categoria ON proyectos.idcategoria=categoria.idcategoria WHERE idproyecto='"+id+"'";
      
      String datos[]=new String[6];
      try {
            Statement sentencias = reg.createStatement();
            ResultSet rs = sentencias.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(2);//nombre
                datos[1] = rs.getString(8);//empresa
                datos[2] = rs.getString(9);//telefono
                datos[3] = rs.getString(10);//mail
                datos[4] = rs.getString(11);//direccion
                datos[5] = rs.getString(6);//observaciones
               
            }
            labelproyecto.setText(datos[0]);
            labelempresa.setText(datos[1]);
            labeltelefono.setText(datos[2]);
            labelemail.setText(datos[3]);
            aread.setText(datos[4]);
            areao.setText(datos[5]);
            ventanainfo.setVisible(true);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tablaplMouseClicked

    private void cantalumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantalumKeyTyped
       char validar = evt.getKeyChar();
        if (validar < '1' || validar > '2') {
            getToolkit().beep();
            evt.consume();
        }
        if (cantalum.getText().length() == 1) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_cantalumKeyTyped

    private void nombrepKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrepKeyTyped
       char validar = evt.getKeyChar();
        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_nombrepKeyTyped

    private void nombreempresaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreempresaKeyTyped
        char validar = evt.getKeyChar();
        if (Character.isDigit(validar)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_nombreempresaKeyTyped

    private void telefonoemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoemKeyTyped
        char validar = evt.getKeyChar();
        if (validar < '0' || validar > '9') {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_telefonoemKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amaterno;
    private javax.swing.JTextField amaterno2;
    private javax.swing.JTextField apaterno;
    private javax.swing.JTextField apaterno2;
    private javax.swing.JTextArea aread;
    private javax.swing.JTextArea areao;
    private javax.swing.JTextArea areaob;
    private javax.swing.JTextField buscaras;
    private javax.swing.JTextField buscaremp;
    private javax.swing.JTextField buscarp;
    private javax.swing.JTextField buscarpd;
    private javax.swing.JTextField buscarr;
    private javax.swing.JTextField buscarr3;
    private javax.swing.JTextField cantalum;
    private javax.swing.JComboBox<String> comboaop1;
    private javax.swing.JComboBox<String> comboas;
    private javax.swing.JComboBox<String> comboase;
    private javax.swing.JComboBox<String> comboc;
    private javax.swing.JComboBox<String> comboe;
    private javax.swing.JComboBox<String> comboemp;
    private javax.swing.JComboBox<String> comboesp;
    private javax.swing.JComboBox<String> combop;
    private javax.swing.JComboBox<String> combopc;
    private javax.swing.JComboBox<String> combopd;
    private javax.swing.JComboBox<String> comboreb;
    private javax.swing.JTextArea direccion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelemail;
    private javax.swing.JLabel labelempresa;
    private javax.swing.JLabel labelnum;
    private javax.swing.JLabel labelproyecto;
    private javax.swing.JLabel labeltelefono;
    private javax.swing.JTextField mailemp;
    private javax.swing.JTextField nombreempresa;
    private javax.swing.JTextField nombrep;
    private javax.swing.JTextField nombres;
    private javax.swing.JTextField nombres2;
    private javax.swing.JTextField numeroasesor;
    private javax.swing.JTable tablaasesores;
    private javax.swing.JTable tablaempresas;
    private javax.swing.JTable tablapl;
    private javax.swing.JTable tablaregistrop;
    private javax.swing.JTable tablaresidencias;
    private javax.swing.JTable tablaresidentes;
    private javax.swing.JTextField telefonoem;
    private javax.swing.JTextField txtnum;
    private javax.swing.JDialog ventanainfo;
    private javax.swing.JDialog ventanaregistros;
    // End of variables declaration//GEN-END:variables
}
