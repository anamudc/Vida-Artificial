/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lSystem;

/**
 *
 * @author angie
 */
public class DOLSystem2 {
    
    public String siguientePaso(String axioma){
        String paso = "";
        if("ar".equals(axioma)) paso="albr";
        if("al".equals(axioma)) paso="blar";
        if("br".equals(axioma)) paso="ar";
        if("bl".equals(axioma)) paso="al";
        return paso;
    }
    
    public String GenerarPalabra(String axioma){
        String resultado = "";
        String aux;
        for(int i=0;i<axioma.length();i+=2){
            aux = axioma.substring(i, i+2);
            resultado = resultado+siguientePaso(aux);
//            System.out.println("i= "+i+" axioma= "+axioma+" aux= "+aux+" resultado= "+resultado);
        }
        return resultado;
    }   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String axioma = "ar";
        int iteraciones = 4;
        
        DOLSystem2 sys = new DOLSystem2();
        String res = axioma;
        System.out.println(res);
        for (int j=0;j<iteraciones;j++){
            res = sys.GenerarPalabra(res);
            System.out.println(res);
        }
    }
}
