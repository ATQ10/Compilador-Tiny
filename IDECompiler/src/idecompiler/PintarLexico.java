/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idecompiler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author HP
 */
public class PintarLexico {
    PintarPalabras pintar;
    public PintarLexico(String codigo) {
        if(codigo.length() >= 1){    
            pintar = new PintarPalabras();
            pintar.darEstilo(codigo);
            String regex = "program|if|else|fi|do|until|while|read|wrire|float|int|bool|not|and|or";
            Pattern pat;
            pat = Pattern.compile(regex);
            Matcher m = pat.matcher(codigo);
            int vuelta=0,ultimaPos=0;
            while (m.find()) {
                if(vuelta!=0){
                    pintar.pintaNegro(ultimaPos, m.start());
                    System.err.println(ultimaPos+"|"+m.start()+"|Negro");
                }
                vuelta++;
                pintar.pintaAzul(m.start(), m.end());
                ultimaPos=m.end();
                System.err.println(m.start()+"|"+m.end()+"|Azul");
            }
            if(vuelta!=0){
               pintar.pintaNegro(ultimaPos, codigo.length());
               System.err.println(ultimaPos+"|"+codigo.length()+"|Negro");
            }
        }
    }
}
