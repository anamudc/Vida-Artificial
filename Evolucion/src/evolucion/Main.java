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
public class Main {
    public static void main( String[] args )
    {
        GA<String> ga = new GA<String>(new Torneo<String>(), new HamletMutation(), new CruceHamlet(), new HamletGenerar());
        ga.aplicar(100, new Hamlet());
//        GA<boolean[]> ga = new GA<boolean[]>(new Torneo(), new MutacionBinario(), new CruceBinario(), new GenerarBinario(1000));
//        ga.aplicar(100, new MiFuncion());
    }
}
