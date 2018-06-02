package proyecto;

import lSystem.Turtle;
import lSystem.Planta1;
import lSystem.DOLSystem;
import evolucion.CruceEnteros;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class BoidsAnimation extends JComponent implements Runnable {

    static int animationSpeed = 7;
    static int width = 1200;
    static int height = 700;

    static int numberOfPreys = 20;
    static int numberOfPredators = 4;

    static List<Prey> preys;
    static List<Predator> predators;
    static List<Food> foods;

    static double seperationParameter = 1.0;
    static double alignmentParameter = 1.0;
    static double cohesionParameter = 1.0;

    static int maxVelocity = 5;
    static int maxVelocityPredators = (int) (maxVelocity * Math.random() + maxVelocity + 1);
    static int maxCloseness = 30; //separacion
    static int nbAlignment = 200;
    static int nbCohesion = 170;
    static int nbFood = 170;
    static int preyRadius = 10;

    static int tamanoComida = 5;
    static int tamanoComidaPrey = 50;

    int ejecuciones = 0;
    boolean estacionActiva = false;
    static int idPreys = 1;
    static int idPredator = 1;
    static ArrayList<Integer[]> hijosGato = new ArrayList<Integer[]>();
    static ArrayList<Integer[]> hijosRaton = new ArrayList<Integer[]>();

    public BoidsAnimation() {
        preys = Collections.synchronizedList(new ArrayList<Prey>());
        predators = Collections.synchronizedList(new ArrayList<Predator>());
        foods = Collections.synchronizedList(new ArrayList<Food>());

        Random random = new Random();
        /* Genera los ratones iniciales para la simulación */
        for (int i = 0; i < numberOfPreys; i++) {
            idPreys++;
            Prey b = new Prey(random.nextInt(width), random.nextInt(height), preyRadius, idPreys);
            preys.add(b);
        }
        /* Genera los gatos iniciales para la simulación */
        for (int i = 0; i < numberOfPredators; i++) {
            idPredator++;
            Predator p = new Predator(random.nextInt(width), random.nextInt(height), (int) (maxVelocity * Math.random() + maxVelocity + 1), idPredator);
            predators.add(p);
        }
        /* Genera la comida inicial para la simulación */
        Vector<Double> v = new Vector<Double>();
        v.add((double) width / 2);
        v.add((double) height / 2);
        Food f = new Food(4, v, 2);
        foods.add(f);

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                /* revisión de cantidad de vida de los ratones */
                synchronized (preys) {
                    Iterator<Prey> iterator = preys.iterator();
                    while (iterator.hasNext()) {
                        Prey b = iterator.next();
                        if (b.tiempoVida <= 0) {
                            iterator.remove();
                        }
                    }
                }
                /* calculo de las siguientes posiciones de los ratones */
                synchronized (preys) {
                    Iterator<Prey> iterator = preys.iterator();
                    while (iterator.hasNext()) {
                        Prey b = iterator.next();
                        b.calculatePosition(this.width, this.height, preys,
                                seperationParameter, alignmentParameter, cohesionParameter,
                                maxVelocity, maxCloseness, nbAlignment, nbCohesion,
                                predators, foods, nbFood);
                        overlapFood(b, (int) b.getX(), (int) b.getY(), b.radius);
                    }
                }
                /* revisión de cantidad de vida de los gatos */
                synchronized (predators) {
                    Iterator<Predator> iterator = predators.iterator();
                    while (iterator.hasNext()) {
                        Predator p = iterator.next();
                        if (p.tiempoVida <= 0) {
                            iterator.remove();
                        }
                    }
                }
                /* calculo de las siguientes posiciones de los gatos */
                synchronized (predators) {
                    Iterator<Predator> iterator = predators.iterator();
                    while (iterator.hasNext()) {
                        Predator p = iterator.next();
                        p.calculatePosition(width, height, preys,
                                seperationParameter, alignmentParameter, cohesionParameter,
                                maxVelocityPredators, maxCloseness, nbAlignment, nbCohesion,
                                predators, nbFood);
                        overlap(p, (int) p.getX(), (int) p.getY(), p.radius, p.radioReprod);
                    }
                }
                Thread.sleep(1000 / (5 * animationSpeed + 1));
                repaint();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        pintarComida(g2D);
        pintarPresas(g2D);
        pintarPredadores(g2D);
        pintarPlantas(g);
    }

    //Sección de pintar dividido por elelmento
    public void pintarPresas(Graphics2D g2D) {
        generarHijosRaton();
        hijosRaton = new ArrayList<Integer[]>();
        synchronized (preys) {
            Iterator<Prey> iterator = preys.iterator();
            while (iterator.hasNext()) {
                Prey b = iterator.next();
                Double dy = b.position.get(1);
                Double dx = b.position.get(0);
                Integer x = dx.intValue();
                Integer y = dy.intValue();
                b.tiempoVida--;
                b.edad++;
                g2D.drawImage(b.imagen.getImage(), x, y, this);
            }
        }
    }

    public void pintarComida(Graphics2D g2D) {
        ejecuciones++;
        /* revisión de la fecha de vencimiento de la comida */
        synchronized (foods) {
            Iterator<Food> iterator = foods.iterator();
            while (iterator.hasNext()) {
                Food f = iterator.next();
                if (f.tiempoVida <= 0) {
                    iterator.remove();
                }
            }
        }
        /* pintar la comida */
        synchronized (foods) {
            Iterator<Food> iterator = foods.iterator();
            while (iterator.hasNext()) {
                Food f = iterator.next();
                Double dy = f.position.get(1);
                Double dx = f.position.get(0);
                Integer x = dx.intValue();
                Integer y = dy.intValue();
                f.tiempoVida--;
                g2D.drawImage(f.imagen.getImage(), x, y, this);
            }
        }
        Vector<Double> v = new Vector<Double>();

        /* produccion de comida segun la temporada en ciclos de 500 ejecuciones */
        if (ejecuciones < 500) {
            v.add((double) width * Math.random() / 2);
            v.add((double) Math.random() * height / 2);
            estacionActiva = true;
        }
        if (ejecuciones >= 500 && ejecuciones <= 1000) {
            v.add((double) (width * Math.random() / 2) + (width / 2));
            v.add((double) (Math.random() * height / 2) + (height / 2));
            estacionActiva = false;
            if (ejecuciones == 1000) {
                ejecuciones = 0;
            }
        }

        Food f = new Food(4, v, 2);
        foods.add(f);
    }

    public void pintarPredadores(Graphics2D g2D) {
        generarHijosGato();
        hijosGato = new ArrayList<Integer[]>();
        synchronized (predators) {
            Iterator<Predator> iterator = predators.iterator();
            while (iterator.hasNext()) {
                Predator p = iterator.next();
                Double dy = p.position.get(1);
                Double dx = p.position.get(0);
                
                Double vy = p.velocity.get(1);
                Double vx = p.velocity.get(0);
                
                Integer x = dx.intValue();
                Integer y = dy.intValue();
                p.tiempoVida--;
                p.edad++;
               
                g2D.drawImage(p.imagen.getImage(), x,y, this);
            }
        }
    }

    public void pintarPlantas(Graphics g) {
        /*Seccion L-System*/
 /*Seccion planta 1*/
        String aux = "";
        String axioma = "F";
        int iteraciones = 3;
        DOLSystem sys = new DOLSystem();
        String res = axioma;
        for (int j = 0; j < iteraciones; j++) {
            res = sys.GenerarRama(res);
        }
        String axiomaTurtle = res;
        Planta1 ti = new Planta1();
        ti.setN(500);
        ti.setM(500);
        ti.llenarMatriz();
        ti.setDistacia(5);
        ti.setDelta(27);
        Turtle turtle = new Turtle((int) (width*7/8), (int) (height*7/8), 270);
        for (int i = 0; i < axiomaTurtle.length(); i++) {
            aux = axiomaTurtle.substring(i, i + 1);
            int x = turtle.getxPosition();
            int y = turtle.getyPosition();
            turtle = ti.siguientePaso(aux, turtle, ti.getDistacia(), ti.getDelta());
            if(!estacionActiva){
                g.setColor(Color.GREEN);
            }else{
                g.setColor(Color.ORANGE);
            }
            g.drawLine(x, y, turtle.getxPosition(), turtle.getyPosition());
            ti.setDistacia((int) (5 + (Math.random() * 2)));
            ti.setDelta((int) (27 + (Math.random() * 4)));
        }

        /*Seccion planta 2*/
        String aux2 = "";
        String axioma2 = "X";
        int iteraciones2 = 5;
        DOLSystem sys2 = new DOLSystem();
        String res2 = axioma2;
        for (int j = 0; j < iteraciones2; j++) {
            res2 = sys2.GenerarRama3(res2);
        }
        String axiomaTurtle2 = res2;
        Planta1 ti2 = new Planta1();
        ti2.setN(500);
        ti2.setM(500);
        ti2.setDistacia(2);
        ti2.setDelta(26);
        Turtle turtle2 = new Turtle((int) (width*5/8), (int) (height*5/8), 270);
        for (int i = 0; i < axiomaTurtle2.length(); i++) {
            aux2 = axiomaTurtle2.substring(i, i + 1);
            int x = turtle2.getxPosition();
            int y = turtle2.getyPosition();
            turtle2 = ti.siguientePaso(aux2, turtle2, ti2.getDistacia(), ti2.getDelta());
            if(!estacionActiva){
                g.setColor(Color.GREEN);
            }else{
                g.setColor(Color.ORANGE);
            }
            g.drawLine(x, y, turtle2.getxPosition(), turtle2.getyPosition());
            ti2.setDistacia((int) (2 + (Math.random() * 2)));
            ti2.setDelta((int) (26 + (Math.random() * 4)));
        }
         /*Seccion planta 3*/
        String aux3 = "";
        String axioma3 = "F";
        int iteraciones3 = 4;
        DOLSystem sys3 = new DOLSystem();
        String res3 = axioma3;
        for (int j = 0; j < iteraciones3; j++) {
            res3 = sys3.GenerarRama(res);
        }
        String axiomaTurtle3 = res3;
        Planta1 ti3 = new Planta1();
        ti3.setN(500);
        ti3.setM(500);
        ti3.llenarMatriz();
        ti3.setDistacia(5);
        ti3.setDelta(27);
        Turtle turtle3 = new Turtle((int) (width*3/8), (int) (height*3/8), 270);
        for (int i = 0; i < axiomaTurtle3.length(); i++) {
            aux3 = axiomaTurtle3.substring(i, i + 1);
            int x = turtle3.getxPosition();
            int y = turtle3.getyPosition();
            turtle3 = ti3.siguientePaso(aux3, turtle3, ti3.getDistacia(), ti3.getDelta());
            if(estacionActiva){
                g.setColor(Color.GREEN);
            }else{
                g.setColor(Color.ORANGE);
            }
            g.drawLine(x, y, turtle3.getxPosition(), turtle3.getyPosition());
            ti3.setDistacia((int) (5 + (Math.random() * 2)));
            ti3.setDelta((int) (27 + (Math.random() * 4)));
        }

        /*Seccion planta 4*/
        String aux4 = "";
        String axioma4 = "X";
        int iteraciones4 = 4;
        DOLSystem sys4 = new DOLSystem();
        String res4 = axioma4;
        for (int j = 0; j < iteraciones4; j++) {
            res4 = sys4.GenerarRama3(res4);
        }
        String axiomaTurtle4 = res4;
        Planta1 ti4 = new Planta1();
        ti4.setN(500);
        ti4.setM(500);
        ti4.setDistacia(2);
        ti4.setDelta(26);
        Turtle turtle4 = new Turtle((int) (width*1/8), (int) (height*1/8), 270);
        for (int i = 0; i < axiomaTurtle4.length(); i++) {
            aux4 = axiomaTurtle4.substring(i, i + 1);
            int x = turtle4.getxPosition();
            int y = turtle4.getyPosition();
            turtle4 = ti.siguientePaso(aux4, turtle4, ti4.getDistacia(), ti4.getDelta());
            if(estacionActiva){
                g.setColor(Color.GREEN);
            }else{
                g.setColor(Color.ORANGE);
            }
            g.drawLine(x, y, turtle4.getxPosition(), turtle4.getyPosition());
            ti4.setDistacia((int) (2 + (Math.random() * 2)));
            ti4.setDelta((int) (26 + (Math.random() * 4)));
        }

    }

    /* revisión de solapamiento, con otros gatos y con ratones */
    private static boolean overlap(Predator p, int a, int b, int s, int rb) {
        double x = (double) a;
        double y = (double) b;
        double size = (double) s;
        /* si el solape es con otro gatos, determinar si se reproduce o no */
        synchronized (predators) {
            Iterator<Predator> iterator = predators.iterator();
            while (iterator.hasNext()) {
                Predator o = iterator.next();
                if (!p.equals(o)) {
                    double dist = Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2));
                    if (dist < Math.abs(p.radioReprod + (o.radioReprod))
                            && o.color == p.color && !p.isPadre) {
                        System.out.println("reproducirGatos()");
                        p.isPadre = true;
                        o.isPadre = true;
                        reproducir(p, o);
                    }
                }
            }
        }
        /* si el solape es con ratones, determina si se lo come o no */
        synchronized (preys) {
            Iterator<Prey> iterator = preys.iterator();
            while (iterator.hasNext()) {
                Prey o = iterator.next();
                int dist = (int) (Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2)));
                if (p.tiempoVida < p.hambre && dist >= Math.abs(size - o.radius) && dist <= Math.abs(size + o.radius)) {
                    //eliminar el boid o
                    System.out.println(" comiendo preys ");
                    iterator.remove();
                    p.tiempoVida += tamanoComidaPrey;
                    return true;
                }
            }
        }
        return false;
    }

    /* revisión de solapamiento, con otros ratones y con comida */
    private static boolean overlapFood(Prey prey, int a, int b, int s) {
        double x = (double) a;
        double y = (double) b;
        double size = (double) 20;
        /* si el solape es con otro ratones, determinar si se reproduce o no */
        synchronized (preys) {
            Iterator<Prey> iterator = preys.iterator();
            while (iterator.hasNext()) {
                Prey o = iterator.next();
                if (!prey.equals(o)) {
                    double dist = Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2));
                    if (dist < Math.abs(prey.radioReprod + (o.radioReprod))
                            && o.color == prey.color && !prey.isPadre && !o.isPadre ) {
                        System.out.println("reproducirRaton()");
                        prey.isPadre = true;
                        o.isPadre = true;
                        prey.otroHijo = false;
                        o.otroHijo = false;
                        reproducir(prey, o);
                    }
                }
            }
        }
        /* si el solape es con comida, determina si se lo come o no */
        synchronized (foods) {
            Iterator<Food> iterator = foods.iterator();
            while (iterator.hasNext()) {
                Food f = iterator.next();
                int dist = (int) (Math.sqrt(Math.pow(x - f.getX(), 2) + Math.pow(y - f.getY(), 2)));
                double condicion1 = Math.abs(size - f.radius);
                if (dist <= condicion1) {
                    iterator.remove();
                    prey.tiempoVida += tamanoComida;
                    return true;
                }
            }
        }
        return false;
    }

    /* contruye los hijos de gatos nacidos en la iteración anterior */
    public static void generarHijosGato() {
        Random random = new Random();
        synchronized (predators) {
            Iterator<Integer[]> iterator = hijosGato.iterator();
            while (iterator.hasNext()) {
                System.out.println("generarhijosGato:" + hijosGato.size());
                Integer[] hijos = iterator.next();
                idPredator++;
                Predator ph1 = new Predator(random.nextInt(width), random.nextInt(height), (int) (maxVelocity * Math.random() + maxVelocity + 1), idPredator, hijos[0], hijos[1], hijos[2], hijos[3], hijos[4]);
                predators.add(ph1);
            }
        }
    }
    /* contruye los hijos de ratones nacidos en la iteración anterior */
    public static void generarHijosRaton() {
        Random random = new Random();
        synchronized (preys) {
            Iterator<Integer[]> iterator = hijosRaton.iterator();
            while (iterator.hasNext()) {
                System.out.println("generarhijosRaton:" + hijosRaton.size());
                Integer[] hijos = iterator.next();
                idPreys++;
                Prey ph1 = new Prey(random.nextInt(width), random.nextInt(height), (int) (maxVelocity * Math.random() + maxVelocity + 1), idPreys, hijos[0], hijos[1], hijos[2], hijos[3], hijos[4]);
                preys.add(ph1);
            }
        }
    }

    /* aplica el operador genético de cruce con las cadenas de parametros de 
       los individuos padres y guarda los dos hijos nuevos*/
    public static void reproducir(AnimationObject p, AnimationObject o) {
        CruceEnteros cruce = new CruceEnteros();
        Integer[] padre1 = {p.vidaInicial, p.color, p.amplitud, p.verticesLimit, p.iterTuringMorph};
        Integer[] padre2 = {o.vidaInicial, o.color, o.amplitud, o.verticesLimit, o.iterTuringMorph};
        Integer[][] hijos = cruce.aplicar(padre1, padre2);
        if ("class proyecto.Predator".equals(p.getClass().toString())) {
            hijosGato.add(hijos[0]);
            hijosGato.add(hijos[1]);
        }
        if ("class proyecto.Prey".equals(p.getClass().toString())) {
            hijosRaton.add(hijos[0]);
            hijosRaton.add(hijos[1]);
        }
    }

    public static void main(String[] args) {

        JFrame f = new JFrame("Boids");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new BoidsAnimation());
        f.setSize(width, height);
        f.setVisible(true);
    }

}
