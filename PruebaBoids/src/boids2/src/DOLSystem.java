/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boids2.src;

/**
 *
 * @author angie
 */
public class DOLSystem {
    
    public String siguientePaso(char axioma){
        String paso = "";
        if(axioma=='a'){
            paso="ab";            
        }if(axioma=='b'){
            paso="a";
        }
        return paso;
    }
    
    public String siguientePasoTurtle1(char axioma){
        String paso = "";
        if(axioma=='F'){
            paso="F-F+F+FF-F-F+F";            
        }if(axioma=='-'){
            paso="-";
        }if(axioma=='+'){
            paso="+";
        }
        return paso;
    }
    
    public String siguientePasoRama(char axioma){
        String paso = "";
        if(axioma=='F'){
            paso="F[+F]F[-F]F";            
        }if(axioma=='-'){
            paso="-";
        }if(axioma=='+'){
            paso="+";
        }if(axioma=='['){
            paso="[";
        }if(axioma==']'){
            paso="]";
        }
        return paso;
    }
    
    public String siguientePasoRama2(char axioma){
        String paso = "";
        if(axioma=='F'){
            paso="FF-[-F+F+F]+[+F-F-F]";            
        }if(axioma=='-'){
            paso="-";
        }if(axioma=='+'){
            paso="+";
        }if(axioma=='['){
            paso="[";
        }if(axioma==']'){
            paso="]";
        }
        return paso;
    }
    
    public String siguientePasoRama3(char axioma){
        String paso = "";
        if(axioma=='X'){
            paso="F[+X][-X]FX";            
        }if(axioma=='F'){
            paso="FF";            
        }if(axioma=='-'){
            paso="-";
        }if(axioma=='+'){
            paso="+";
        }if(axioma=='['){
            paso="[";
        }if(axioma==']'){
            paso="]";
        }
        return paso;
    }
    
    public String GenerarPalabra(String axioma){
        String resultado = "";
        char aux;
        for(int i=0;i<axioma.length();i++){
            aux = axioma.charAt(i);
            resultado = resultado+siguientePasoTurtle1(aux);
//            System.out.println("i= "+i+" axioma= "+axioma+" aux= "+aux+" resultado= "+resultado);
        }
        return resultado;
    }   
    
    public String GenerarRama(String axioma){
        String resultado = "";
        char aux;
        for(int i=0;i<axioma.length();i++){
            aux = axioma.charAt(i);
            resultado = resultado+siguientePasoRama(aux);
//            System.out.println("i= "+i+" axioma= "+axioma+" aux= "+aux+" resultado= "+resultado);
        }
        return resultado;
    }
    public String GenerarRama2(String axioma){
        String resultado = "";
        char aux;
        for(int i=0;i<axioma.length();i++){
            aux = axioma.charAt(i);
            resultado = resultado+siguientePasoRama2(aux);
//            System.out.println("i= "+i+" axioma= "+axioma+" aux= "+aux+" resultado= "+resultado);
        }
        return resultado;
    }
    
    public String GenerarRama3(String axioma){
        String resultado = "";
        char aux;
        for(int i=0;i<axioma.length();i++){
            aux = axioma.charAt(i);
            resultado = resultado+siguientePasoRama3(aux);
//            System.out.println("i= "+i+" axioma= "+axioma+" aux= "+aux+" resultado= "+resultado);
        }
        return resultado;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String axioma = "b";
        int iteraciones = 5;
        
        DOLSystem sys = new DOLSystem();
        String res = axioma;
        System.out.println(res);
        for (int j=0;j<iteraciones;j++){
            res = sys.GenerarPalabra(res);
            System.out.println(res);
        }
    }
}
