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
public class CruceEnteros implements Cruce<Integer[]>{

    Integer[] hijoUno;
    Integer[] hijoDos;
    
    public Integer[][] aplicar(Integer[] uno, Integer[] dos) {
        int pos = (int)((uno.length-1)*Math.random()) + 1;
        hijoUno = new Integer[uno.length];
        hijoDos = new Integer[dos.length];
        for(int i = 0 ; i<pos ;i++){
            hijoUno[i] = uno[i];
            hijoDos[i] = dos[i];
        }
        for(int i = pos ; i<uno.length ;i++){
            hijoUno[i] = dos[i];
            hijoDos[i] = uno[i];
        }
        return new Integer [][]{hijoUno,hijoDos};
    }
}
