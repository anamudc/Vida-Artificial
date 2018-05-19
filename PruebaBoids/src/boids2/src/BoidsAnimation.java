package boids2.src;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;
import sandpile.ProyectoArena;

public class BoidsAnimation extends JComponent implements Runnable {

    public BoidsAnimation() {
        birds = new ArrayList<Bird>();
        obstacles = new ArrayList<Obstacle>();
        predators = new ArrayList<Predator>();
        Random random = new Random();
        for (int i = 0; i < numberOfBirds; i++) {
            Bird b = new Bird(random.nextInt(width), random.nextInt(height), birdRadius);
            //Bird b = new Bird(width/2, height/2, birdRadius);
            birds.add(b);
        }

        Predator p = new Predator(random.nextInt(width), random.nextInt(height), birdRadius * 2);
        Predator p2 = new Predator(random.nextInt(width), random.nextInt(height), birdRadius * 2);

        predators.add(p);
        predators.add(p2);

        foods = new ArrayList<Food>();

        Vector<Double> v = new Vector<Double>();
        v.add((double) width / 2);
        v.add((double) height / 2);
        Food f = new Food(4, v, Color.YELLOW);

        foods.add(f);

        for (Bird bs : birds) {
            Bird ba = new Bird(1, 1,1);
            ba=bs;
            arregloAuxBirds.add(ba);
        }
        for (Food fs : foods) {
            Food fa = new Food(1, v, Color.BLACK);
            fa=fs;
            arregloAuxFood.add(fa);
        }
        for (Predator ps : predators) {
            Predator pa = new Predator(1, 1,1);
            pa=ps;
            arregloAuxPredator.add(pa);
        }

        Thread t = new Thread(this);
        t.start();

    }

    static int animationSpeed = 7;

    static int numberOfBirds = 50;
    static int numberOfPredators = 3;
    static int numberOfObstacles = 5;

    static ArrayList<Bird> birds;
    static ArrayList<Predator> predators;
    static ArrayList<Obstacle> obstacles;
    static ArrayList<Food> foods;

    static double seperationParameter = 1.0;
    static double alignmentParameter = 1.0;
    static double cohesionParameter = 1.0;

    static int maxVelocity = 10;
    static int maxVelocityPredators = (maxVelocity - 2 >= 0 ? maxVelocity - 2 : 0);
    static int maxCloseness = 30; //separation
    static int nbAlignment = 200;
    static int nbCohesion = 170;
    static int width = 1200;
    static int height = 700;
    static int birdRadius = 10;

    ProyectoArena pr = new ProyectoArena(width, height);
    int ejecuciones = 0;
    static ArrayList<Bird> arregloAuxBirds = new ArrayList<Bird>();
    static ArrayList<Predator> arregloAuxPredator = new ArrayList<Predator>();
    static ArrayList<Food> arregloAuxFood = new ArrayList<Food>();

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            while (true) {
                for (Bird b : arregloAuxBirds) {
                    if (b.tiempoVida <= 0) {
                        birds.remove(b);
                    }
                }
                for (Bird b : birds) {
                    b.calculatePosition(this.width, this.height, birds,
                            seperationParameter, alignmentParameter, cohesionParameter,
                            maxVelocity, maxCloseness, nbAlignment, nbCohesion,
                            obstacles, predators);
                    overlapFood(b.width, b.height, b.radius);
                }
                for (Predator p : arregloAuxPredator) {
                    if (p.tiempoVida <= 0) {
                        predators.remove(p);
                    }
                }
                for (Predator p : predators) {
                    p.calculatePosition(width, height, birds,
                            seperationParameter, alignmentParameter, cohesionParameter,
                            maxVelocityPredators, maxCloseness, nbAlignment, nbCohesion,
                            obstacles, predators);
                    overlap(p.width, p.height, p.radius);
                }

//                for (Food f : foods) {
//                    f.sandPile(4, foods, 4);
//
//                    System.out.println("tamaño: " + f.getTamaño() + " c " + f.c);
//                }
//                Food f = new Food(4, new Vector((int)(width/2 * Math.random()), (int)(height/2 *Math.random())), Color.YELLOW);
//                Vector v = new Vector();
//                v.add((int) Math.random()*width/2);
//                v.add((int) Math.random()*height/2);
//
//                Food f = new Food(4, v, Color.YELLOW);
//                foods.add(f);
                repaint();
                Thread.sleep(1000 / (5 * animationSpeed + 1));

            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        ejecuciones++;

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
        System.out.println(res);
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
        System.out.println("fin");

        /*Seccion planta 2*/
        String aux2 = "";
        String axioma2 = "X";
        int iteraciones2 = 6;
        DOLSystem sys2 = new DOLSystem();
        String res2 = axioma2;
        for (int j = 0; j < iteraciones2; j++) {
            res2 = sys2.GenerarRama3(res2);
        }
        System.out.println(res2);
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
        System.out.println("fin");
        g.setColor(Color.RED);

        /*Seccion Comida*/
        for (Food f : foods) {
            f.sandPile(4, foods, 4);

//            System.out.println("tamaño: " + f.getTamaño() + " c " + f.c + " position: " + f.position);
            Double dy = f.position.get(1);
            Double dx = f.position.get(0);
            Integer x = dx.intValue();
            Integer y = dy.intValue();
//            AffineTransform tx4 = AffineTransform.getShearInstance(Math.pow(Math.E,f.radius), Math.pow(f.radius, 2));
            g2D.drawImage(f.imagen.getImage(), x, y, this);
        }

        Vector<Double> v = new Vector<Double>();

        System.out.println("ejecuciones: " + ejecuciones);
        if (ejecuciones < 500) {
            v.add((double) width * Math.random() / 2);
            v.add((double) Math.random() * height / 2);
            System.out.println("if arriba x: " + v.get(0) + " y: " + v.get(1));
        }
        if (ejecuciones >= 500 && ejecuciones <= 1000) {
            v.add((double) (width * Math.random() / 2) + (width / 2));
            v.add((double) (Math.random() * height / 2) + (height / 2));
            System.out.println("if abajo x: " + v.get(0) + " y: " + v.get(1));
            if (ejecuciones == 1000) {
                ejecuciones = 0;
            }
        }

        Food f = new Food(4, v, Color.YELLOW);
        foods.add(f);

        for (Bird b : birds) {
            Double dy = b.position.get(1);
            Double dx = b.position.get(0);
            Integer x = dx.intValue();
            Integer y = dy.intValue();
            b.tiempoVida--;
//            AffineTransform tx4 = AffineTransform.getShearInstance(Math.pow(Math.E,f.radius), Math.pow(f.radius, 2));
            g2D.drawImage(b.imagen.getImage(), x, y, this);
        }
        for (Obstacle o : obstacles) {
            Ellipse2D.Double myCircle = new Ellipse2D.Double(o.getX() - o.radius, o.getY() - o.radius, 2 * o.radius, 2 * o.radius);
            g2D.setPaint(o.color);
            g2D.fill(myCircle);
            g2D.setPaint(Color.BLACK);
            g2D.draw(myCircle);
        }

        for (Predator p : predators) {
            Double dy = p.position.get(1);
            Double dx = p.position.get(0);
            Integer x = dx.intValue();
            Integer y = dy.intValue();
            p.tiempoVida--;//            AffineTransform tx4 = AffineTransform.getRotateInstance();
            double ang = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

//            Double dw = p.align(birds, (int)alignmentParameter).get(1);
//            Double dz = p.align(birds, (int)alignmentParameter).get(0);
//            Integer w = dx.intValue();
//            Integer z = dy.intValue();
//            
//            double ang2= Math.sqrt(Math.pow(w,2)+Math.pow(z,2));
//            double angulo= Math.acos(10/(ang*ang2));
//            AffineTransform tx4 = AffineTransform.getRotateInstance(angulo);
            g2D.drawImage(p.imagen.getImage(), x, y, this);
        }
    }

    public static void generatePredator() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(width - 10) + 5;
            y = random.nextInt(width - 10) + 5;
        } while (overlap(x, y, 15));
        Predator predator = new Predator((double) x, (double) y, maxVelocityPredators);
        predators.add(predator);
    }

    public static void removePredators() {
        predators = new ArrayList<Predator>();
    }

    public static void generateObstacle() {
        Random random = new Random();
        int size, x, y;
        size = random.nextInt(39) + 30;
        do {
            x = random.nextInt(width - 50 - 2 * size) + 25 + size;
            y = random.nextInt(height - 50 - 2 * size) + 25 + size;
        } while (overlap(x, y, size));
        Vector<Double> v = new Vector<Double>();
        v.add((double) x);
        v.add((double) y);
        Obstacle o = new Obstacle(size, v);
        obstacles.add(o);

    }

    private static boolean overlap(int a, int b, int s) {
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
        for (Bird o : birds) {
            int dist = (int) (Math.sqrt(Math.pow(x - o.getX(), 2) + Math.pow(y - o.getY(), 2)));
            if (dist >= Math.abs(size - o.radius) && dist <= Math.abs(size + o.radius)) {
                //eliminar el boid o
                System.out.println("o: " + o.color + " if birds ");
                birds.remove(o);
                System.out.println("birds.size: " + birds.size());
                return true;
            }
        }
        return false;
    }

    private static boolean overlapFood(int a, int b, int s) {
        double x = (double) a;
        double y = (double) b;
        double size = (double) s;

        for (Food f : arregloAuxFood) {
            int dist = (int) (Math.sqrt(Math.pow(x - f.getX(), 2) + Math.pow(y - f.getY(), 2)));
            if (dist >= Math.abs(size - f.radius) && dist <= Math.abs(size + f.radius)) {
                //eliminar el boid o
                System.out.println("f: " + f.color + " if foods ");
                foods.remove(f);
                System.out.println("if overlaf foods.size: " + foods.size());
                return true;
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
