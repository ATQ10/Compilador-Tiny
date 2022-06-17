/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlexcompiler;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java_cup.runtime.Symbol;

/**
 *
 * @author HP
 */
public class JLexCompiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String ruta1 = "C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/src/jlexcompiler/Lexer.flex";
        String ruta2 = "C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/src/jlexcompiler/LexerCup.flex";
        String[] rutaS = {"-parser", "Sintax", "C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/src/jlexcompiler/Sintax.cup"};
        
        //generar(ruta1, ruta2, rutaS);
        //"C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/src/jlexcompiler/archivo.txt"
        analizardorLexico(args);
        analizadorSintactico(args);
    }
   
    public static void generarLexer(String ruta){
        File archivo = new File (ruta);
        JFlex.Main.generate(archivo);
    }
    
    public static void generar(String ruta1, String ruta2, String[] rutaS) throws IOException, Exception{
        File archivo;
        archivo = new File(ruta1);
        JFlex.Main.generate(archivo);
        archivo = new File(ruta2);
        JFlex.Main.generate(archivo);
        java_cup.Main.main(rutaS);
        
        Path rutaSym = Paths.get("C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/src/jlexcompiler/sym.java");
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get("C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/sym.java"), 
                Paths.get("C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/src/jlexcompiler/sym.java")
        );
        Path rutaSin = Paths.get("C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/src/jlexcompiler/Sintax.java");
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get("C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/Sintax.java"), 
                Paths.get("C:/Users/HP/Documents/NetBeansProjects/JLexCompiler/src/jlexcompiler/Sintax.java")
        );
    }
     
    public static void analizardorLexico(String[] arg){
        if(!(new File(arg[0]).exists())){
            String resultadoE = "Nada que compilar :( \n";
            //System.out.println(resultadoE);
        return;
        }
        String[] ruta = arg[0].split("/");
        int ind = 0;
        if(ruta.length > 0)
            ind = ruta.length-1;
        String rutaLexicos = arg[0].replaceFirst(ruta[ind], "Lexicos.txt");
        String rutaErrores = arg[0].replaceFirst(ruta[ind], "ErroresL.txt");
        //System.out.println(rutaLexicos);
        //System.out.println(rutaErrores);
        try {
            Reader lector = new BufferedReader(new FileReader(arg[0]));
            Lexer lexer = new Lexer(lector);
            String resultado = "";
            String resultadoE = "";
            int numL=0;
            while (true) {
                Tokens tokens = lexer.yylex();
                if (tokens == null) {
                    System.out.println(resultado);
                    Guardar(rutaLexicos, resultado);
                    Guardar(rutaErrores, resultadoE);
                    return;
                }
                numL++;
                int line =lexer.line+1;
                int column =lexer.column+1;
                switch (tokens) {
                    case ERROR:
                        resultadoE += lexer.lexeme+ "\tSimbolo no definido en el lexema "+numL+" ("+line+":"+column+")\n";
                        break;
                    case Reservada:
                        resultado +=numL+"\t"+ lexer.lexeme + "\tEs una palabra " + tokens +"\t"+line+"\t"+column+"\n";
                    break;
                    case Suma: case Resta: case Multiplicacion: case Division: case Potencia: case Igual:
                        resultado +=numL+"\t"+ lexer.lexeme + "\tEs un operador (" + tokens + ")\t"+line+"\t"+column+"\n";;
                    break;
                    case Comentario:
                        if(lexer.lexeme.contains("\r\n")){
                            resultado +=numL+"\t"+ lexer.lexeme.replace("\r\n", "") + "\tEs un " + tokens + "\t"+line+"\t"+column+"\n";
                            System.out.print("Aqui 1 "+lexer.lexeme.replace("\r\n", ""));
                        }
                        else if(lexer.lexeme.contains("\r")){
                            resultado +=numL+"\t"+ lexer.lexeme.replace("\r", "") + "\tEs un " + tokens + "\t"+line+"\t"+column+"\n";
                            System.out.print("Aqui 2");
                        }else
                            resultado +=numL+"\t"+ lexer.lexeme.replace("\n", "") + "\tEs un " + tokens + "\t"+line+"\t"+column+"\n";
                            System.out.print("Aqui 3");
                    break;
                    case Identificador: case Numero:
                        resultado +=numL+"\t"+ lexer.lexeme + "\tEs un " + tokens + "\t"+line+"\t"+column+"\n";
                        break;
                    case Menor_que: case Menor_o_igual: case Mayor_que: case Mayor_o_igual:
                    case Igual_que: case Diferente_que:
                        resultado +=numL+"\t"+ lexer.lexeme + "\tEs un comparador (" + tokens + ")\t"+line+"\t"+column+"\n";
                        break;
                    default:
                        resultado +=numL+"\t"+ lexer.lexeme + "\tEs un caracter especial (" + tokens + ")\t"+line+"\t"+column+"\n";
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        }
    }
    
    public static void analizadorSintactico(String[] arg) throws FileNotFoundException, Exception{
        // TODO add your handling code here:
        String ST = leeBuffer(new BufferedReader(new FileReader(arg[0])));
        System.out.println(ST);
        Sintax s = new Sintax(new jlexcompiler.LexerCup(new StringReader(ST)));
        String[] ruta = arg[0].split("/");
        int ind = 0;
        if(ruta.length > 0)
            ind = ruta.length-1;
        String rutaSintactico = arg[0].replaceFirst(ruta[ind], "Sintactico.txt");
        String rutaErroresS = arg[0].replaceFirst(ruta[ind], "ErroresS.txt");
        try {
            s.parse();
            System.out.println("Analisis sintactico realizado correctamente");
            Guardar(rutaSintactico, "Analisis sintactico realizado correctamente");
            Guardar(rutaErroresS, "");
            //txtAnalizarSin.setForeground(new Color(25, 111, 61));
        } catch (Exception ex) {
            Symbol sym = s.getS();
            String resultadoError = "Error de sintaxis. Linea: " + (sym.right + 1) + " Columna: " + (sym.left + 1 - caracteresAnteriores(sym.right+1,arg[0])) + ", Texto: \"" + sym.value + "\"";
            System.out.println(resultadoError);
            Guardar(rutaErroresS, resultadoError);
            Guardar(rutaSintactico, "Error de sintaxis");
            //txtAnalizarSin.setForeground(Color.red);
        }
    }
    
    public static String leeBuffer(BufferedReader buffer) throws Exception
    {
        String retorno = null;

        String lineaSalida = "";
        StringBuffer contenido = new StringBuffer();
        String separador = "";

        while ((lineaSalida = buffer.readLine()) != null)
        {
            contenido.append(separador + lineaSalida);
            separador = "\n";
        }

        retorno = contenido.toString();

        return retorno;
    }
    
    public static void Guardar(String ruta, String texto) {
        try {
            if (new File(ruta) != null) {
                try (FileWriter guardado = new FileWriter(new File(ruta))) {
                    guardado.write(texto);
                    //JOptionPane.showMessageDialog(rootPane, "Archivo actualizado");
                }
            }
        } catch (IOException ex) {
            //JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private static int caracteresAnteriores(int right, String string) throws IOException {
        int nCaracteres = 0;
        if(right>1){
            BufferedReader buffer = new BufferedReader(new FileReader(string));
            String lineaSalida = "";
            StringBuffer contenido = new StringBuffer();
            String separador = "";
            int line = 1;
            while ((lineaSalida = buffer.readLine()) != null)
            {
                contenido.append(separador + lineaSalida);
                separador = "\n";
                line++;
                if(line>=right){
                    contenido.append("\n");
                    break;
                }
            }

            nCaracteres = contenido.toString().length();
            System.out.println(contenido.toString());
        }    
        System.out.print(nCaracteres);
        return nCaracteres;
    }
    
}
