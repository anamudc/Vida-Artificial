/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolucion;

/**
 *
 * @author angie
 */
public class Hamlet implements Adaptabilidad<String>{
    protected final static String text = "METHINKSITISLIKEAWEASEL";
    @Override
    public double obtener(String x) {
        int f = 0;
        for( int i=0; i<text.length(); i++ ) if( text.charAt(i) == x.charAt(i) ) f++;
        return f;
    }
    
}
