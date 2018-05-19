/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LangtonLoop;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author angie
 */
public class ProyectoLangton {

    int n;
    int m;
    int c;
    int matriz[][];
    int matrizAux[][];
    int vecinos[][]; //configuración de la vecindad
    int xInit = (int) n / 2;
    int yInit = (int) m / 2;

    int contError = 0;

    int[][] matrizQ = {{0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0},
    {2, 1, 7, 0, 1, 4, 0, 1, 4, 2, 0, 0, 0, 0},
    {2, 0, 2, 2, 2, 2, 2, 2, 0, 2, 0, 0, 0, 0},
    {2, 7, 2, 0, 0, 0, 0, 2, 1, 2, 0, 0, 0, 0},
    {2, 1, 2, 0, 0, 0, 0, 2, 1, 2, 0, 0, 0, 0},
    {2, 0, 2, 0, 0, 0, 0, 2, 1, 2, 0, 0, 0, 0},
    {2, 7, 2, 0, 0, 0, 0, 2, 1, 2, 0, 0, 0, 0},
    {2, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2},
    {2, 0, 7, 1, 0, 7, 1, 0, 7, 1, 1, 1, 1, 1},
    {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}};

    Hashtable<String, String> reglas = new Hashtable<String, String>();

    public void tablaReglas() {
        reglas.put("00000", "" + 0);
        reglas.put("00001", "" + 2);
        reglas.put("00002", "" + 0);
        reglas.put("00003", "" + 0);
        reglas.put("00005", "" + 0);
        reglas.put("00006", "" + 3);
        reglas.put("00007", "" + 1);
        reglas.put("00011", "" + 2);
        reglas.put("00012", "" + 2);
        reglas.put("00013", "" + 2);
        reglas.put("00021", "" + 2);
        reglas.put("00022", "" + 0);
        reglas.put("00023", "" + 0);
        reglas.put("00026", "" + 2);
        reglas.put("00027", "" + 2);
        reglas.put("00032", "" + 0);
        reglas.put("00052", "" + 5);
        reglas.put("00062", "" + 2);
        reglas.put("00072", "" + 2);
        reglas.put("00102", "" + 2);
        reglas.put("00112", "" + 0);
        reglas.put("00202", "" + 0);
        reglas.put("00203", "" + 0);
        reglas.put("00205", "" + 0);
        reglas.put("00212", "" + 5);
        reglas.put("00222", "" + 0);
        reglas.put("00232", "" + 2);
        reglas.put("00522", "" + 2);
        reglas.put("01232", "" + 1);
        reglas.put("01242", "" + 1);
        reglas.put("01252", "" + 5);
        reglas.put("01262", "" + 1);
        reglas.put("01272", "" + 1);
        reglas.put("01275", "" + 1);
        reglas.put("01422", "" + 1);
        reglas.put("01432", "" + 1);
        reglas.put("01442", "" + 1);
        reglas.put("01472", "" + 1);
        reglas.put("01625", "" + 1);
        reglas.put("01722", "" + 1);
        reglas.put("01725", "" + 5);
        reglas.put("01752", "" + 1);
        reglas.put("01762", "" + 1);
        reglas.put("01772", "" + 1);
        reglas.put("02527", "" + 1);
        reglas.put("10001", "" + 1);
        reglas.put("10006", "" + 1);
        reglas.put("10007", "" + 7);
        reglas.put("10011", "" + 1);
        reglas.put("10012", "" + 1);
        reglas.put("10021", "" + 1);
        reglas.put("10024", "" + 4);
        reglas.put("10027", "" + 7);
        reglas.put("10051", "" + 1);
        reglas.put("10101", "" + 1);
        reglas.put("10111", "" + 1);
        reglas.put("10124", "" + 4);
        reglas.put("10127", "" + 7);
        reglas.put("10202", "" + 6);
        reglas.put("10212", "" + 1);
        reglas.put("10221", "" + 1);
        reglas.put("10224", "" + 4);
        reglas.put("10226", "" + 3);
        reglas.put("10227", "" + 7);
        reglas.put("10232", "" + 7);
        reglas.put("10242", "" + 4);
        reglas.put("10262", "" + 6);
        reglas.put("10264", "" + 4);
        reglas.put("10267", "" + 7);
        reglas.put("10271", "" + 0);
        reglas.put("10272", "" + 7);
        reglas.put("10542", "" + 7);
        reglas.put("11112", "" + 1);
        reglas.put("11122", "" + 1);
        reglas.put("11124", "" + 4);
        reglas.put("11125", "" + 1);
        reglas.put("11126", "" + 1);
        reglas.put("11127", "" + 7);
        reglas.put("11152", "" + 2);
        reglas.put("11212", "" + 1);
        reglas.put("11222", "" + 1);
        reglas.put("11224", "" + 4);
        reglas.put("11225", "" + 1);
        reglas.put("11227", "" + 7);
        reglas.put("11232", "" + 1);
        reglas.put("11242", "" + 4);
        reglas.put("11262", "" + 1);
        reglas.put("11272", "" + 7);
        reglas.put("11322", "" + 1);
        reglas.put("12224", "" + 4);
        reglas.put("12227", "" + 7);
        reglas.put("12243", "" + 4);
        reglas.put("12254", "" + 7);
        reglas.put("12324", "" + 4);
        reglas.put("12327", "" + 7);
        reglas.put("12425", "" + 5);
        reglas.put("12426", "" + 7);
        reglas.put("12527", "" + 5);
        reglas.put("20001", "" + 2);
        reglas.put("20002", "" + 2);
        reglas.put("20004", "" + 2);
        reglas.put("20007", "" + 1);
        reglas.put("20012", "" + 2);
        reglas.put("20015", "" + 2);
        reglas.put("20021", "" + 2);
        reglas.put("20022", "" + 2);
        reglas.put("20023", "" + 2);
        reglas.put("20024", "" + 2);
        reglas.put("20025", "" + 0);
        reglas.put("20026", "" + 2);
        reglas.put("20027", "" + 2);
        reglas.put("20032", "" + 6);
        reglas.put("20042", "" + 3);
        reglas.put("20051", "" + 7);
        reglas.put("20052", "" + 2);
        reglas.put("20057", "" + 5);
        reglas.put("20072", "" + 2);
        reglas.put("20102", "" + 2);
        reglas.put("20112", "" + 2);
        reglas.put("20122", "" + 2);
        reglas.put("20142", "" + 2);
        reglas.put("20172", "" + 2);
        reglas.put("20202", "" + 2);
        reglas.put("20203", "" + 2);
        reglas.put("20205", "" + 2);
        reglas.put("20207", "" + 3);
        reglas.put("20212", "" + 2);
        reglas.put("20215", "" + 2);
        reglas.put("20221", "" + 2);
        reglas.put("20222", "" + 2);
        reglas.put("20227", "" + 2);
        reglas.put("20232", "" + 1);
        reglas.put("20242", "" + 2);
        reglas.put("20245", "" + 2);
        reglas.put("20252", "" + 0);
        reglas.put("20255", "" + 2);
        reglas.put("20262", "" + 2);
        reglas.put("20272", "" + 2);
        reglas.put("20312", "" + 2);
        reglas.put("20321", "" + 6);
        reglas.put("20322", "" + 6);
        reglas.put("20342", "" + 2);
        reglas.put("20422", "" + 2);
        reglas.put("20512", "" + 2);
        reglas.put("20521", "" + 2);
        reglas.put("20522", "" + 2);
        reglas.put("20552", "" + 1);
        reglas.put("20572", "" + 5);
        reglas.put("20622", "" + 2);
        reglas.put("20672", "" + 2);
        reglas.put("20712", "" + 2);
        reglas.put("20722", "" + 2);
        reglas.put("20742", "" + 2);
        reglas.put("20772", "" + 2);
        reglas.put("21122", "" + 2);
        reglas.put("21126", "" + 1);
        reglas.put("21222", "" + 2);
        reglas.put("21224", "" + 2);
        reglas.put("21226", "" + 2);
        reglas.put("21227", "" + 2);
        reglas.put("21422", "" + 2);
        reglas.put("21522", "" + 2);
        reglas.put("21622", "" + 2);
        reglas.put("21722", "" + 2);
        reglas.put("22227", "" + 2);
        reglas.put("22244", "" + 2);
        reglas.put("22246", "" + 2);
        reglas.put("22276", "" + 2);
        reglas.put("22277", "" + 2);
        reglas.put("30001", "" + 3);
        reglas.put("30002", "" + 2);
        reglas.put("30004", "" + 1);
        reglas.put("30007", "" + 6);
        reglas.put("30012", "" + 3);
        reglas.put("30042", "" + 1);
        reglas.put("30062", "" + 2);
        reglas.put("30102", "" + 1);
        reglas.put("30122", "" + 0);
        reglas.put("30251", "" + 1);
        reglas.put("40112", "" + 0);
        reglas.put("40122", "" + 0);
        reglas.put("40125", "" + 0);
        reglas.put("40212", "" + 0);
        reglas.put("40222", "" + 1);
        reglas.put("40232", "" + 6);
        reglas.put("40252", "" + 0);
        reglas.put("40322", "" + 1);
        reglas.put("50002", "" + 2);
        reglas.put("50021", "" + 5);
        reglas.put("50022", "" + 5);
        reglas.put("50023", "" + 2);
        reglas.put("50027", "" + 2);
        reglas.put("50052", "" + 0);
        reglas.put("50202", "" + 2);
        reglas.put("50212", "" + 2);
        reglas.put("50215", "" + 2);
        reglas.put("50222", "" + 0);
        reglas.put("50224", "" + 4);
        reglas.put("50272", "" + 2);
        reglas.put("51212", "" + 2);
        reglas.put("51222", "" + 0);
        reglas.put("51242", "" + 2);
        reglas.put("51272", "" + 2);
        reglas.put("60001", "" + 1);
        reglas.put("60002", "" + 1);
        reglas.put("60212", "" + 0);
        reglas.put("61212", "" + 5);
        reglas.put("61213", "" + 1);
        reglas.put("61222", "" + 5);
        reglas.put("70007", "" + 7);
        reglas.put("70112", "" + 0);
        reglas.put("70122", "" + 0);
        reglas.put("70125", "" + 0);
        reglas.put("70212", "" + 0);
        reglas.put("70222", "" + 1);
        reglas.put("70225", "" + 1);
        reglas.put("70232", "" + 1);
        reglas.put("70252", "" + 5);
        reglas.put("70272", "" + 0);
    }

    public ProyectoLangton(int n, int m) {
        this.n = n;
        this.m = m;
        tablaReglas();
    }

    public void llenarMatriz(int n, int m) {
        matriz = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    public void primerQ(int x, int y) {

        for (int i = 0; i < matrizQ.length; i++) {
            for (int j = 0; j < matrizQ[0].length; j++) {
                matriz[x + i][y + j] = matrizQ[i][j];
            }
        }
    }

    public void vecinosMoore() {
        int vecinosVon[][] = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        vecinos = vecinosVon;
    }

    public void vecinosVonNeuman() {

        //                         l      t       r       b
        int vecinosMoore[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        vecinos = vecinosMoore;
    }

    public void siguienteEstadoArena(int i, int j, int[][] matriz, int[][] vecinos) {

        matrizAux = matriz;
        int nv = vecinos.length;
        int mv = vecinos[0].length;
        int c;

        c = matriz[i][j];
        String regla = "";

        for (int x = 0; x < nv; x++) {
            for (int y = 0; y < mv - 1; y++) {

                regla += "" + matriz[(i + vecinos[x][y] + n) % n][(j + vecinos[x][y + 1] + m) % m];

//                System.out.println("regla:" + regla + " for i:" + i + " j:" + j + " x:" + ((i + vecinos[x][y] + n) % n) + " y:" + ((j + vecinos[x][y + 1] + m) % m));
            }
        }
        int cont = 0;
//        System.out.println("regla:" + regla + " for i:" + i + " j:" + j);
        
        while (reglas.get("" + c + regla) == null && cont < 4) {
            regla = rotarRegla("" + regla);
            cont++;
        }
        cont = 0;
        if (reglas.get("" + c + regla) == null) {
//            matrizAux[i][j] = 0;
            contError++;
//            System.out.println("regla es null:" + ("" + c + regla));

        } else {
//            System.out.println("regla no null:"+("" + c + regla)+" get regla: " + reglas.get("" + c + regla));
            matrizAux[i][j] = Integer.parseInt(reglas.get("" + c + regla));
        }

    }

    public String rotarRegla(String regla) {
        String nuevaRegla;

//        System.out.println("regla: " + regla);
        String parte1 = "" + regla.charAt(0);
        
//        System.out.println("parte1: " + parte1);
        String parte2 = "" + regla.charAt(1);
        
//        System.out.println("parte2: " + parte2);
        String parte3 = "" + regla.substring(2);
        
//        System.out.println("parte3: " + parte3);

        nuevaRegla = "" + parte1 + parte3 + parte2;
//        System.out.println("nuevaRegla: " + nuevaRegla);
        return nuevaRegla;
    }

    public void cambioEstadosArena() {

//        xInit = (int) (Math.random() * n);
//        yInit = (int) (Math.random() * m);
//        xInit = n / 2;
//        yInit = m / 2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                siguienteEstadoArena(i, j, matriz, vecinos);
            }
        }
        matriz = matrizAux;
//        pintarMatriz(matriz);
        System.out.println("____________________________________________________________________________________________________");

    }

    public void pintarMatriz(int matriz[][]) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matriz[i][j] == 0) {

                    System.out.print(" ");
                } else {
                    System.out.print(matriz[i][j]);
                }
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                s += matriz[i][j] + "\t";
            }
            s += "\n";
        }
        return s;
    }

    //matriz de tamaño m x n - toroidal
    //funcion retorne pares ordenados que son sus vecinos
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        int n = 50;
        int m = 50;
        int iteraciones = 10;

        ProyectoLangton pr = new ProyectoLangton(n, m);

        pr.llenarMatriz(n, m);

        pr.vecinosVonNeuman();

        pr.primerQ(n - 20, m - (m - 10));
        System.out.println("______________Matriz_Original_______________________________________________________________________");
        pr.pintarMatriz(pr.matriz);
        System.out.println("____________________________________________________________________________________________________");
        
        while (iteraciones > 0) {
            pr.cambioEstadosArena();
            iteraciones--;
            System.out.println("iteracion: " + iteraciones);
            pr.pintarMatriz(pr.matriz);
        }
        System.out.println(pr.contError);
    }

}
