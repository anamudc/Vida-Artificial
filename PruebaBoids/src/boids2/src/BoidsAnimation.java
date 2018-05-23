package boids2.src;

import evolucion.CruceEnteros;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;
import sandpile.ProyectoArena;

public class BoidsAnimation extends JComponent implements Runnable {

    static int animationSpeed = 7;
    static int width = 1200;
    static int height = 700;

    static int numberOfBirds = 14;
    static int numberOfPredators = 6;

    static List<Bird> birds;
    static List<Predator> predators;
    static List<Obstacle> obstacles;
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
    static int birdRadius = 10;

    static int tamanoComida = 5;
    static int tamanoComidaBird = 50;

    int ejecuciones = 0;
    static int idBirds = 1;
    static int idPredator = 1;
    static ArrayList<Integer[]> hijosGato = new ArrayList<Integer[]>();
    static ArrayList<Integer[]> hijosRaton = new ArrayList<Integer[]>();

    public BoidsAnimation() {
        birds = Collections.synchronizedList(new ArrayList<Bird>());
        obstacles = Collections.synchronizedList(new ArrayList<Obstacle>());
        predators = Collections.synchronizedList(new ArrayList<Predator>());
        foods = Collections.synchronizedList(new ArrayList<Food>());

        Random random = new Random();
        /* Genera los ratones iniciales para la simulación */
        for (int i = 0; i < numberOfBirds; i++) {
            idBirds++;
            Bird b = new Bird(random.nextInt(width), random.nextInt(height), birdRadius, idBirds);
            birds.add(b);
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
                synchronized (birds) {
                    Iterator<Bird> iterator = birds.iterator();
                    while (iterator.hasNext()) {
                        Bird b = iterator.next();
                        if (b.tiempoVida <= 0) {
                            iterator.remove();
                        }
                    }
                }
                /* calculo de las siguientes posiciones de los ratones */
                synchronized (birds) {
                    Iterator<Bird> iterator = birds.iterator();
                    while (iterator.hasNext()) {
                        Bird b = iterator.next();
                        b.calculatePosition(this.width, this.height, birds,
                                seperationParameter, alignmentParameter, cohesionParameter,
                                maxVelocity, maxCloseness, nbAlignment, nbCohesion,
                                obstacles, predators, foods, nbFood);
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
                        p.calculatePosition(width, height, birds,
                                seperationParameter, alignmentParameter, cohesionParameter,
                                maxVelocityPredators, maxCloseness, nbAlignment, nbCohesion,
                                obstacles, predators, nbFood);
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
        pintarAves(g2D);
        pintarPredadores(g2D);
        pintarPlantas(g);
    }

    //Sección de pintar dividido por elelmento
    public void pintarAves(Graphics2D g2D) {
        generarHijosRaton();
        hijosRaton = new ArrayList<Integer[]>();
        synchronized (birds) {
            Iterator<Bird> iterator = birds.iterator();
            while (iterator.hasNext()) {
                Bird b = iterator.next();
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
        }
        if (ejecuciones >= 500 && ejecuciones <= 1000) {
            v.add((double) (width * Math.random() / 2) + (width / 2));
            v.add((double) (Math.random() * height / 2) + (height / 2));
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
                Integer x = dx.intValue();
                Integer y = dy.intValue();
                p.tiempoVida--;
                p.edad++;
                g2D.drawImage(p.imagen.getImage(), x, y, this);
            }
        }
    }

    public void pintarPlantas(Graphics g) {
        /*Seccion L-System*/
 /*Seccion planta 1*/
        String aux = "";
        String axioma = "F";
        int iteraciones = 4;
        DOLSystem sys = new DOLSystem();
        String res = axioma;

        for (int j = 0; j < iteraciones; j++) {
            res = sys.GenerarRama(res);
        }
//        System.out.println(res);
        String axiomaTurtle = res;

        Planta1 ti = new Planta1();
        ti.n = 500;
        ti.m = 500;
        ti.llenarMatriz();
        ti.distacia = 5;
        ti.delta = 27;
        Turtle turtle = new Turtle((int) width / 2, (int) height / 2, 270);
        for (int i = 0; i < axiomaTurtle.length(); i++) {
            aux = axiomaTurtle.substring(i, i + 1);
            int x = turtle.xPosition;
            int y = turtle.yPosition;
            turtle = ti.siguientePaso(aux, turtle, ti.distacia, ti.delta);
            g.setColor(Color.ORANGE);
            g.drawLine(x, y, turtle.xPosition, turtle.yPosition);

            ti.distacia = (int) (5 + (Math.random() * 2));
            ti.delta = (int) (27 + (Math.random() * 4));
        }

        /*Seccion planta 2*/
        String aux2 = "";
        String axioma2 = "X";
        int iteraciones2 = 6;
        DOLSystem sys2 = new DOLSystem();
        String res2 = axioma2;
        for (int j = 0; j < iteraciones2; j++) {
            res2 = sys2.GenerarRama3(res2);
        }
//        System.out.println(res2);
        String axiomaTurtle2 = res2;
        Planta1 ti2 = new Planta1();
        ti2.n = 500;
        ti2.m = 500;
        ti2.distacia = 2;
        ti2.delta = 26;
        Turtle turtle2 = new Turtle((int) width / 4, (int) height / 2, 270);
        for (int i = 0; i < axiomaTurtle2.length(); i++) {
            aux2 = axiomaTurtle2.substring(i, i + 1);
            int x = turtle2.xPosition;
            int y = turtle2.yPosition;
            turtle2 = ti.siguientePaso(aux2, turtle2, ti2.distacia, ti2.delta);
            g.setColor(Color.RED);
            g.drawLine(x, y, turtle2.xPosition, turtle2.yPosition);
            ti2.distacia = (int) (2 + (Math.random() * 2));
            ti2.delta = (int) (26 + (Math.random() * 4));
        }
        g.setColor(Color.RED);

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
        synchronized (birds) {
            Iterator<Bird> iterator = birds.iterator();
            while (iterator.hasNext()) {
                Bird o = iterator.next();
                int dist = (int) (Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2)));
                if (p.tiempoVida < p.hambre && dist >= Math.abs(size - o.radius) && dist <= Math.abs(size + o.radius)) {
                    //eliminar el boid o
                    System.out.println(" comiendo birds ");
                    iterator.remove();
                    p.tiempoVida += tamanoComidaBird;
                    return true;
                }
            }
        }
        return false;
    }

    /* revisión de solapamiento, con otros ratones y con comida */
    private static boolean overlapFood(Bird bird, int a, int b, int s) {
        double x = (double) a;
        double y = (double) b;
        double size = (double) 20;
        /* si el solape es con otro ratones, determinar si se reproduce o no */
        synchronized (birds) {
            Iterator<Bird> iterator = birds.iterator();
            while (iterator.hasNext()) {
                Bird o = iterator.next();
                if (!bird.equals(o)) {
                    double dist = Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2));
                    if (dist < Math.abs(bird.radioReprod + (o.radioReprod))
                            && o.color == bird.color && !bird.isPadre && !o.isPadre ) {
                        System.out.println("reproducirRaton()");
                        bird.isPadre = true;
                        o.isPadre = true;
                        bird.otroHijo = false;
                        o.otroHijo = false;
                        reproducir(bird, o);
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
                    bird.tiempoVida += tamanoComida;
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
        synchronized (birds) {
            Iterator<Integer[]> iterator = hijosRaton.iterator();
            while (iterator.hasNext()) {
                System.out.println("generarhijosRaton:" + hijosRaton.size());
                Integer[] hijos = iterator.next();
                idBirds++;
                Bird ph1 = new Bird(random.nextInt(width), random.nextInt(height), (int) (maxVelocity * Math.random() + maxVelocity + 1), idBirds, hijos[0], hijos[1], hijos[2], hijos[3], hijos[4]);
                birds.add(ph1);
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
        if ("class boids2.src.Predator".equals(p.getClass().toString())) {
            hijosGato.add(hijos[0]);
            hijosGato.add(hijos[1]);
        }
        if ("class boids2.src.Bird".equals(p.getClass().toString())) {
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
