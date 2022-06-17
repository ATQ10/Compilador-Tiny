/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idecompiler;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author HP
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    
    String COMPILADOR = "C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/dist/JLexCompiler.jar";
    NumeroLinea noLinea;
    String ruta;
    Boolean lineas;
    //Colores de palabras reservadas
    private StyleContext sc;
    private DefaultStyledDocument doc;
    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
        //Centramos ventana
        setSize(1200,800);
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(null);
        //Para numerar lineas
        noLinea = new NumeroLinea(jTextPane1);
        jScrollPane1.setRowHeaderView(noLinea);
        lineas = true;
        ruta = null;
        //Titulo
        setTitle("Compilador Tiny-extended");
        //Colores palabras reservadas
        this.sc = new StyleContext();
        this.doc = new DefaultStyledDocument(sc);
        darEstilo();
    }
    
    /**
     * Abrir enlaces web
     */
    
    private void Abrir_URL(String URL) throws URISyntaxException {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                URI uri = new URI(URL);
                desktop.browse(uri);
            } catch (IOException e) {
                System.err.println("Error: No se pudo abrir el enlace" + e.getMessage() );
            }
        } else {
            System.err.println("Error: No se puede abrir enlaces web.");
        }
    }
    /**
     * Método para abrir archivos
     */
   
    public String Abrir(){
        Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(fileChooser);
        try {
            ruta = fileChooser.getSelectedFile().getAbsolutePath();                                        
            File f = new File(ruta);
            entrada = new Scanner(f);
            CargarTexto(entrada);
            setTitle("Compilador Tiny | "+ruta);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }
        return null;
    }
    public String AbrirLexico(String rutaL){
        Scanner entrada = null;
        try {
            if(!(new File(rutaL).exists())){
            String resultadoE = "Nada que compilar :( \n";
            //System.out.println(resultadoE);
            return null;
            }
            String[] rutaFile = rutaL.split("/");
            int ind = 0;
            if(rutaFile.length > 0)
                ind = rutaFile.length-1;
            //Cargando Lexicos
            String rutaLexicos = rutaL.replaceFirst(rutaFile[ind], "Lexicos.txt");
            File fLexicos = new File(rutaLexicos);
            entrada = new Scanner(fLexicos);
            CargarTextoLexico(entrada);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            //JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }
        return null;
    }
    public String AbrirErroresL(String rutaL){
        Scanner entrada = null;
        try {
            if(!(new File(rutaL).exists())){
            String resultadoE = "Nada que compilar :( \n";
            //System.out.println(resultadoE);
            return null;
            }
            String[] rutaFile = rutaL.split("/");
            int ind = 0;
            if(rutaFile.length > 0)
                ind = rutaFile.length-1;
            //Cargando Errores
            String rutaErrores = rutaL.replaceFirst(rutaFile[ind], "ErroresL.txt");
            File fErrores = new File(rutaErrores);
            entrada = new Scanner(fErrores);
            CargarTextoErroresL(entrada);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            //JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }
        return null;
    }
    
    public String AbrirSintactico(String rutaL){
        Scanner entrada = null;
        try {
            if(!(new File(rutaL).exists())){
            String resultadoE = "Nada que compilar :( \n";
            //System.out.println(resultadoE);
            return null;
            }
            String[] rutaFile = rutaL.split("/");
            int ind = 0;
            if(rutaFile.length > 0)
                ind = rutaFile.length-1;
            //Cargando Errores
            String rutaErrores = rutaL.replaceFirst(rutaFile[ind], "Sintactico.txt");
            File fErrores = new File(rutaErrores);
            entrada = new Scanner(fErrores);
            CargarTextoSintactico(entrada);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            //JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }
        return null;
    }
    
    public String AbrirErroresS(String rutaL){
        Scanner entrada = null;
        try {
            if(!(new File(rutaL).exists())){
            String resultadoE = "Nada que compilar :( \n";
            //System.out.println(resultadoE);
            return null;
            }
            String[] rutaFile = rutaL.split("/");
            int ind = 0;
            if(rutaFile.length > 0)
                ind = rutaFile.length-1;
            //Cargando Errores
            String rutaErrores = rutaL.replaceFirst(rutaFile[ind], "ErroresS.txt");
            File fErrores = new File(rutaErrores);
            entrada = new Scanner(fErrores);
            CargarTextoErroresS(entrada);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            //JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }
        return null;
    }
    /**
     * Método para abrir archivos
     */
   
    public String Refrescar(){
        Scanner entrada = null;
        try {
            File f = new File(ruta);
            entrada = new Scanner(f);
            CargarTexto(entrada);
            setTitle("Compilador R++ | "+ruta);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }
        return null;
    }
    
    
    /**
     * Método para cargar Texto
     */
    
    public String CargarTexto(Scanner entrada){
        jTextPane1.setText(null);
        String texto = "";
        while (entrada.hasNext()) {
                texto += entrada.nextLine()+'\n';
        }
        try {
            doc.insertString(0,texto,null);
        }catch (Exception ex) {
            System.out.println("ERROR: no se pudo establecer estilo de documento");
        }
        return null;
    }
    
    public String CargarTextoLexico(Scanner entrada){
        //jTextAreaLexico.setText(null);
        limpiarTabla(jTable1);
        while (entrada.hasNext()) {
            String data[] = entrada.nextLine().split("\t");
            Object[] row = { data[0], data[1], data[2], data[3], data[4]};
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.addRow(row);          
            //jTextAreaLexico.append(entrada.nextLine());
            //jTextAreaLexico.append("\n");
        }
        return null;
    }
    
    public String CargarTextoErroresL(Scanner entrada){
        jTextAreaErrores.setText(null);
        while (entrada.hasNext()) {
                jTextAreaErrores.append(entrada.nextLine());
                jTextAreaErrores.append("\n");
        }
        return null;
    }
    
    public String CargarTextoSintactico(Scanner entrada){
        jTextAreaSintactico.setText(null);
        jTextAreaResultados.setText(null);
        while (entrada.hasNext()) {
            String texto = entrada.nextLine();
            jTextAreaSintactico.append(texto);
            jTextAreaResultados.append(texto);
            jTextAreaSintactico.append("\n");
            jTextAreaResultados.append("\n");
        }
        return null;
    }
    
    public String CargarTextoErroresS(Scanner entrada){
       // jTextAreaErrores.setText(null);
        while (entrada.hasNext()) {
            String texto = entrada.nextLine();
            jTextAreaErrores.append(texto);
            jTextAreaResultados.append("\n");
        }
        return null;
    }
    
    /**
     * Método para exportar archivos
     */
    
    private void Exportar() {
        try {
            JFileChooser archivo = new JFileChooser(System.getProperty("user.dir"));
            archivo.showSaveDialog(this);
            if (archivo.getSelectedFile() != null) {
                try (FileWriter guardado = new FileWriter(archivo.getSelectedFile())) {
                    guardado.write(jTextPane1.getText());
                    JOptionPane.showMessageDialog(rootPane, "El archivo fue guardado con éxito en la ruta establecida");
                    setTitle("Compilador R++ | "+archivo.getSelectedFile().getPath());
                    ruta = archivo.getSelectedFile().getPath();
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    /**
     * Método para guardar archivos
     */
    
    private void Guardar() {
        if(ruta==null){
            Exportar();
            return ;
        }
        try {
            if (new File(ruta) != null) {
                try (FileWriter guardado = new FileWriter(new File(ruta))) {
                    guardado.write(jTextPane1.getText());
                    JOptionPane.showMessageDialog(rootPane, "Archivo actualizado");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    /**
     * Método para pintar código
     */
    private void Pintar(){
        /*if(!"".equals(jTextPane1.getText())){
            PintarLexico pintarLex = new PintarLexico(jTextPane1.getText());
            
            //pintarLex.pintar.darEstilo(jTextPane1.getText());
            jTextPane1.setDocument(pintarLex.pintar.jTextPane1.getDocument());
        }*/
        if(jTextPane1.getText().length() >= 1){
            String analizar = jTextPane1.getText();
            String regex = "program|if|else|fi|do|until|while|read|wrire|float|int|bool|not|and|or";
            Pattern pat;
            pat = Pattern.compile(regex);
            Matcher m = pat.matcher(analizar);
            int vuelta=0,ultimaPos=0,saltosLinea=0;
            while (m.find()) {
                String buscarEnter = jTextPane1.getText().substring(0, m.start());
                saltosLinea = saltosDeLinea(buscarEnter);
                pintaNegro(ultimaPos, m.start());
                System.err.println(ultimaPos+"|"+m.start()+"|Negro");
                
                vuelta++;
                pintaAzul(m.start()-saltosLinea, m.end()-saltosLinea);
                ultimaPos=m.end()-saltosLinea;
                System.err.println((m.start()-saltosLinea)+"|"+(m.end()-saltosLinea)+"|Azul");
            }
            if(vuelta!=0){
               pintaNegro(ultimaPos, jTextPane1.getText().length());
               System.err.println(ultimaPos+"|"+(jTextPane1.getText().length())+"|Negro");
            }
            System.out.println(saltosLinea);
        }
       
    }
    /*
    * Colores
    */
    public void pintaNegro(int posini,int posfin){
        Style negro = sc.addStyle("ConstantWidth", null);
        StyleConstants.setForeground(negro, Color.black);
        doc.setCharacterAttributes(posini,posfin, negro, false);

    }
    public void pintaAzul(int posini,int posfin){
        Style azul = sc.addStyle("ConstantWidth", null);
        StyleConstants.setForeground(azul, Color.blue);
        doc.setCharacterAttributes(posini,posfin, azul, false);

    } 
    /*
    * Dar formato doc
    */
    public void darEstilo(){
        jTextPane1.setDocument(doc);
        try {
            doc.insertString(0,"",null);
        }catch (Exception ex) {
            System.out.println("ERROR: no se pudo establecer estilo de documento");
        }
        
   }
    /*
    * Limpiar Tabla
    */
    public void limpiarTabla(JTable tabla){
        try {
            DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
            int filas=tabla.getRowCount();
            for (int i = 0;filas>i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenuItem19 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaSintactico = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaSemantico = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextAreaCodInt = new javax.swing.JTextArea();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextAreaResultados = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextAreaErrores = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();

        jMenuItem19.setText("jMenuItem19");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextPane1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextPane1CaretUpdate(evt);
            }
        });
        jTextPane1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextPane1InputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTextPane1CaretPositionChanged(evt);
            }
        });
        jTextPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextPane1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextPane1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTextPane1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Lexema", "Descripción", "Línea", "Columna"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jTabbedPane1.addTab("Léxico", jScrollPane2);

        jTextAreaSintactico.setColumns(20);
        jTextAreaSintactico.setRows(5);
        jScrollPane4.setViewportView(jTextAreaSintactico);

        jTabbedPane1.addTab("Sintáctico", jScrollPane4);

        jTextAreaSemantico.setColumns(20);
        jTextAreaSemantico.setRows(5);
        jScrollPane5.setViewportView(jTextAreaSemantico);

        jTabbedPane1.addTab("Semántico", jScrollPane5);

        jTextAreaCodInt.setColumns(20);
        jTextAreaCodInt.setRows(5);
        jScrollPane6.setViewportView(jTextAreaCodInt);

        jTabbedPane1.addTab("Código intermedio", jScrollPane6);

        jTextAreaResultados.setColumns(20);
        jTextAreaResultados.setRows(5);
        jScrollPane8.setViewportView(jTextAreaResultados);

        jTabbedPane2.addTab("Resultados", jScrollPane8);

        jTextAreaErrores.setColumns(20);
        jTextAreaErrores.setRows(5);
        jScrollPane7.setViewportView(jTextAreaErrores);

        jTabbedPane2.addTab("Errores", jScrollPane7);

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        jLabel1.setText("Código a Compilar");

        jMenu1.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Nuevo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Abrir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Guardar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Guardar como");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Salir");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Cortar");
        jMenu2.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Copiar");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("Pegar");
        jMenu2.add(jMenuItem8);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("Borrar");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuItem15.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem15.setText("Refrescar");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem15);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Formato");

        jMenuItem20.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem20.setText("Fuente");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem20);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PLUS, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText("Aumentar");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuItem18.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_MINUS, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem18.setText("Disminuir");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem18);

        jMenuItem16.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem16.setText("Repintar");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem16);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Compilar");

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem11.setText("Ejecutar");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        jMenu6.setText("Ver");

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem14.setText("No. Líneas");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem14);

        jMenuBar1.add(jMenu6);

        jMenu5.setText("Ayuda");

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem12.setText("Manual de uso");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem12);

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem13.setText("Acerca de nosotros");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem13);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane2)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        jTextPane1.setText(null);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            Abrir_URL("https://drive.google.com/drive/folders/1sFvZ2qX32u8vzJD4r6NW-OxzRuYJ1fpG?usp=sharing");
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        if(lineas)
            lineas = false;
        else
            lineas = true;
        noLinea.setVisible(lineas);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        Abrir();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        Exportar();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        System.exit(1);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        jTextPane1.setText(null);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        Guardar();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        Refrescar();
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        try {
            // TODO add your handling code here:
            Abrir_URL("https://drive.google.com/drive/folders/1sFvZ2qX32u8vzJD4r6NW-OxzRuYJ1fpG?usp=sharing");
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        //java -jar "C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/dist/JLexCompiler.jar" "C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/dist/ejemplo.txt"
        //Guardar();
        try {
            String [] cmd = {
                "java",
                "-jar",
                "\""+COMPILADOR+"\"", 
                "\""+ruta.replace("\\", "/")+"\""}; //Comando para ejecutar Jar de Analizador Lexicografico
            Runtime.getRuntime().exec(cmd);
            System.out.println(cmd[0]+" "+cmd[1]+" "+cmd[2]+" "+cmd[3]);
        } catch (IOException ioe) {
                System.out.println (ioe);
        }
        try {
            Thread.sleep(3000);
            AbrirLexico(ruta.replace("\\", "/"));
            AbrirErroresL(ruta.replace("\\", "/"));
            AbrirSintactico(ruta.replace("\\", "/"));
            AbrirErroresS(ruta.replace("\\", "/"));
        } catch (InterruptedException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        Pintar();
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jTextPane1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextPane1InputMethodTextChanged
        // TODO add your handling code here:
        Pintar();
    }//GEN-LAST:event_jTextPane1InputMethodTextChanged

    private void jTextPane1CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextPane1CaretPositionChanged
        // TODO add your handling code here:
        Pintar();
    }//GEN-LAST:event_jTextPane1CaretPositionChanged

    private void jTextPane1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextPane1CaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextPane1CaretUpdate

    private void jTextPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextPane1KeyPressed

    private void jTextPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            Pintar();
        }
    }//GEN-LAST:event_jTextPane1KeyReleased

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        Font f = new Font(jTextPane1.getFont().getFontName(),jTextPane1.getFont().getStyle(),jTextPane1.getFont().getSize()+1);
        if(!(jTextPane1.getFont().getSize()+1>20))
            jTextPane1.setFont(f);
        if(!(jTable1.getFont().getSize()+1>20)){
            f = new Font(jTable1.getFont().getFontName(),jTable1.getFont().getStyle(),jTable1.getFont().getSize()+1);
            jTable1.setFont(f);
        }
        if(!(jTextAreaErrores.getFont().getSize()+1>20)){
            f = new Font(jTextAreaErrores.getFont().getFontName(),jTextAreaErrores.getFont().getStyle(),jTextAreaErrores.getFont().getSize()+1);
            jTextAreaErrores.setFont(f);
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        Font f = new Font(jTextPane1.getFont().getFontName(),jTextPane1.getFont().getStyle(),jTextPane1.getFont().getSize()-1);
        if(!(jTextPane1.getFont().getSize()-1<10))
            jTextPane1.setFont(f);
        if(!(jTable1.getFont().getSize()-1<10)){
            f = new Font(jTable1.getFont().getFontName(),jTable1.getFont().getStyle(),jTable1.getFont().getSize()-1);
            jTable1.setFont(f);
        }
        if(!(jTextAreaErrores.getFont().getSize()-1<10)){
            f = new Font(jTextAreaErrores.getFont().getFontName(),jTextAreaErrores.getFont().getStyle(),jTextAreaErrores.getFont().getSize()-1);
            jTextAreaErrores.setFont(f);
        }
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
        JFontChooser fd = new JFontChooser(this,jTextPane1.getFont());
        fd.setVisible(true);
        if(fd.getReturnStatus() == fd.RET_OK){
               jTextPane1.setFont(fd.getFont());
}
fd.dispose();
    }//GEN-LAST:event_jMenuItem20ActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextAreaCodInt;
    private javax.swing.JTextArea jTextAreaErrores;
    private javax.swing.JTextArea jTextAreaResultados;
    private javax.swing.JTextArea jTextAreaSemantico;
    private javax.swing.JTextArea jTextAreaSintactico;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables

    private int saltosDeLinea(String buscarEnter) {
        int saltosLinea = 0;
        int indice = buscarEnter.indexOf("\n");
        while (indice >= 0) {
            saltosLinea++;
            indice = buscarEnter.indexOf("\n", indice + 1);
        }
        return saltosLinea;
    }
}
