package boids2.src;

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

    static int numberOfBirds = 20;
    static int numberOfPredators = 3;
    static int numberOfObstacles = 5;

    static List<Bird> birds;
    static List<Predator> predators;
    static List<Obstacle> obstacles;
    static List<Food> foods;

    static double seperationParameter = 1.0;
    static double alignmentParameter = 1.0;
    static double cohesionParameter = 1.0;

    static int maxVelocity = 5;
    static int maxVelocityPredators = (int)2*maxVelocity/* + (maxVelocity - 2 >= 0 ? maxVelocity - 2 : 0)*/;
    static int maxCloseness = 30; //separation
    static int nbAlignment = 200;
    static int nbCohesion = 170;
    static int nbFood = 170;
    static int width = 1200;
    static int height = 700;
    static int birdRadius = 10;

    static int tamanoComida = 5;
    static int tamanoComidaBird = 50;

    ProyectoArena pr = new ProyectoArena(width, height);
    int ejecuciones = 0;

    public BoidsAnimation() {
        birds = Collections.synchronizedList(new ArrayList<Bird>());
        obstacles = Collections.synchronizedList(new ArrayList<Obstacle>());
        predators = Collections.synchronizedList(new ArrayList<Predator>());
        foods = Collections.synchronizedList(new ArrayList<Food>());

        Random random = new Random();
        for (int i = 0; i < numberOfBirds; i++) {
            Bird b = new Bird(random.nextInt(width), random.nextInt(height), birdRadius, i);
            birds.add(b);
        }
        for (int i = 0; i < numberOfPredators; i++) {
            Predator p = new Predator(random.nextInt(width), random.nextInt(height), birdRadius * 2, 1);
            predators.add(p);
        }

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
//            System.out.println("hilo " + Thread.currentThread().getId());
            while (true) {
                synchronized (birds) {
//                    System.out.println("hilo dos " + Thread.currentThread().getId());
                    Iterator<Bird> iterator = birds.iterator();
                    while (iterator.hasNext()) {
                        Bird b = iterator.next();
                        if (b.tiempoVida <= 0) {
                            iterator.remove();
                        }
                    }
                }
                synchronized (birds) {
                    Iterator<Bird> iterator = birds.iterator();
                    while (iterator.hasNext()) {
                        Bird b = iterator.next();
//                        System.out.println("posicion x:"+(int)b.getX()+" y:"+(int)b.getY());
                        b.calculatePosition(this.width, this.height, birds,
                                seperationParameter, alignmentParameter, cohesionParameter,
                                maxVelocity, maxCloseness, nbAlignment, nbCohesion,
                                obstacles, predators, foods, nbFood);
                        overlapFood(b, (int) b.getX(), (int) b.getY(), b.radius);
                    }
                }
                synchronized (predators) {
                    Iterator<Predator> iterator = predators.iterator();
                    while (iterator.hasNext()) {
                        Predator p = iterator.next();
                        if (p.tiempoVida <= 0) {
                            iterator.remove();
                        }
                    }
                }
                synchronized (predators) {
                    Iterator<Predator> iterator = predators.iterator();
                    while (iterator.hasNext()) {
                        Predator p = iterator.next();
                        p.calculatePosition(width, height, birds,
                                seperationParameter, alignmentParameter, cohesionParameter,
                                maxVelocityPredators, maxCloseness, nbAlignment, nbCohesion,
                                obstacles, predators, nbFood);
                        overlap(p, (int) p.getX(), (int) p.getY(), p.radius);
                    }
                }

                Thread.sleep(1000 / (5 * animationSpeed + 1));
                repaint();

            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
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
        //sección aves
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
//        System.out.println("hilo paint " + Thread.currentThread().getId());
        /*Seccion Comida*/
        synchronized (foods) {
            Iterator<Food> iterator = foods.iterator();
            while (iterator.hasNext()) {
                Food f = iterator.next();
                if (f.tiempoVida <= 0) {
//                    System.out.println("tiempoVida: " + f.tiempoVida);
                    iterator.remove();
                }
            }
        }
        synchronized (foods) {
            Iterator<Food> iterator = foods.iterator();
            while (iterator.hasNext()) {
                Food f = iterator.next();
//            System.out.println("tamaño: " + f.getTamaño() + " c " + f.c + " position: " + f.position);
                Double dy = f.position.get(1);
                Double dx = f.position.get(0);
                Integer x = dx.intValue();
                Integer y = dy.intValue();
                f.tiempoVida--;
                g2D.drawImage(f.imagen.getImage(), x, y, this);
            }
        }
        Vector<Double> v = new Vector<Double>();

//        System.out.println("ejecuciones: " + ejecuciones);
        if (ejecuciones < 500) {
            v.add((double) width * Math.random() / 2);
            v.add((double) Math.random() * height / 2);
//            System.out.println("if arriba x: " + v.get(0) + " y: " + v.get(1));
        }
        if (ejecuciones >= 500 && ejecuciones <= 1000) {
            v.add((double) (width * Math.random() / 2) + (width / 2));
            v.add((double) (Math.random() * height / 2) + (height / 2));
//            System.out.println("if abajo x: " + v.get(0) + " y: " + v.get(1));
            if (ejecuciones == 1000) {
                ejecuciones = 0;
            }
        }

        Food f = new Food(4, v, 2);
        foods.add(f);
    }

    public void pintarPredadores(Graphics2D g2D) {
        //seccion depredadores
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

    public static void removePredators() {
        predators = new ArrayList<Predator>();
    }

    private static boolean overlap(Predator p, int a, int b, int s) {
        double x = (double) a;
        double y = (double) b;
        double size = (double) s;
        for (Obstacle o : obstacles) {
            double dist = Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2));
            //adding the bird radius will prevent birds from getting stuck between objects
            if (dist >= Math.abs(size - o.radius) && dist <= Math.abs(size + o.radius + birdRadius * 2)) {
                return true;
            }
        }
        synchronized (birds) {
            Iterator<Bird> iterator = birds.iterator();

            while (iterator.hasNext()) {
                Bird o = iterator.next();
                int dist = (int) (Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2)));
                if (p.tiempoVida < p.hambre && dist >= Math.abs(size - o.radius) && dist <= Math.abs(size + o.radius)) {
                    //eliminar el boid o
//                    System.out.println("o: " + o.color + " if birds ");
                    iterator.remove();
                    p.tiempoVida += tamanoComidaBird;
//                    System.out.println("birds.size: " + birds.size());
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean overlapFood(Bird bird, int a, int b, int s) {
        double x = (double) a;
        double y = (double) b;
        double size = (double) 20;

        synchronized (foods) {
            Iterator<Food> iterator = foods.iterator();
            while (iterator.hasNext()) {
                Food f = iterator.next();

//                System.out.println("fx: " + f.getX() + " fy:"+f.getY());
                int dist = (int) (Math.sqrt(Math.pow(x - f.getX(), 2) + Math.pow(y - f.getY(), 2)));
                double condicion1 = Math.abs(size - f.radius);

//                System.out.println("d:"+dist+ " cC1: " + condicion1 );
                if (dist <= condicion1) {
                    //eliminar la comida
                    iterator.remove();
                    bird.tiempoVida += tamanoComida;
//                    System.out.println("if overlaf foods.size: " + foods.size());
                    return true;
                }
            }
        }
        return false;
    }

    public static void removeObstacles() {
        obstacles = new ArrayList<Obstacle>();
    }

    public static void saveParameters() {
        PrintWriter writer;
        try {
            writer = new PrintWriter("parameters.txt", "UTF-8");
            writer.println(seperationParameter);
            writer.println(alignmentParameter);
            writer.println(cohesionParameter);
            writer.println(nbAlignment);
            writer.println(nbCohesion);
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            File f = new File("parameters.txt");
            try {
                f.createNewFile();
                saveParameters();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void loadParameters() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("parameters.txt"));
            String line = br.readLine();
            seperationParameter = Double.parseDouble(line);
            line = br.readLine();
            alignmentParameter = Double.parseDouble(line);
            line = br.readLine();
            cohesionParameter = Double.parseDouble(line);
            line = br.readLine();
            nbAlignment = Integer.parseInt(line);
            line = br.readLine();
            nbCohesion = Integer.parseInt(line);
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //it will just leave default params
        }
    }

    public static void main(String[] args) {
        loadParameters();
        JFrame f = new JFrame("Boids");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new BoidsAnimation());
        f.setSize(width, height);
        f.setVisible(true);

//        JFrame s = new JFrame("Parameters");
//        s.add(new ParamSlider(seperationParameter, alignmentParameter, cohesionParameter,
//                nbAlignment, nbCohesion, maxVelocity, animationSpeed));
//        s.setSize(280, 700);
//        s.setVisible(true);
    }

}
