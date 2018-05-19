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
public class CruceHamlet implements Cruce<String>{

    @Override
    public String[] aplicar(String uno, String dos) {
        int pos = (int)((uno.length()-1)*Math.random()) + 1;
        String u = uno.substring(0,pos)+dos.substring(pos);
        String d = dos.substring(0,pos)+uno.substring(pos);
        return new String[]{u,d};
    }
    
}
