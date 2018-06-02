package proyecto;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import javax.swing.ImageIcon;

public abstract class AnimationObject {

    int color;
    int radius;
    Vector<Double> position;

    Vector<Double> velocity;
    int width, height;
    boolean obstacle;
    String name;
    ImageIcon imagen = null;
    
    int tiempoVida = 100;
    
    int edad;
    int initEdadRepro;
    int finEdadRepro;
    int radioReprod;
    boolean otroHijo = false; //pueden tener otro hijo?
    
    int amplitud;
    int verticesLimit;
    int iterTuringMorph;
    int id;
    int hambre;
    boolean isPadre = false;
    int vidaInicial;

    static final int X = 0;
    static final int Y = 1;

    double getX() {
        return position.get(X);
    }

    double getY() {
        return position.get(1);
    }

    Vector<Double> separate(List<Prey> neighbours, int max) {
        Vector<Double> s = init();
        int i = 0;
        for (Prey b : neighbours) {
            if (!b.equals(this)) {
                double d = distance(b.position, this.position);
                if (d < max && d >= 0) {
                    s = sub(s, sub(b.position, this.position));
                    i++;
                }
            }
        }
        if (i > 0) {
            s = divide(s, i);
        }
        return s;
    }

    Vector<Double> separatePredators(List<Predator> neighbours, int max) {
        Vector<Double> s = init();
        int i = 0;
        for (Predator b : neighbours) {
            if (!b.equals(this)) {
                double d = distance(b.position, this.position);
                if (d < max && d >= 0) {
                    s = sub(s, sub(b.position, this.position));
                    i++;
                }
            }
        }
        if (i > 0) {
            s = divide(s, i);
        }
        return s;
    }

    Vector<Double> align(List<Prey> neighbours, int k) {
        Vector<Double> c = init();
        int i = 0;
        for (Prey b : neighbours) {
            if (!b.equals(this)) {
                double d = distance(b.position, this.position);
                if (d < k && d > 0) {
                    c = add(c, b.velocity);
                    i++;
                }
            }
        }
        if (i > 0) {
            c = divide(c, i);
            c = divide(sub(c, this.velocity), 5);
        }
        return c;
    }

    Vector<Double> cohesion(List<Prey> neighbours, int k) {
        Vector<Double> a = init();
        int i = 0;
        for (Prey b : neighbours) {
            if (!b.equals(this)) {
                double d = distance(b.position, this.position);
                if (d < k && d > 0) {
                    a = add(a, b.position);
                    i++;
                }
            }
        }
        if (i > 0) {
            a = divide(a, i);
            a = divide(sub(a, this.position), 100.0);
        }

        return a;
    }

    void limitSpeed(int max) {
        if (distance(init(), this.velocity) > max) {
            //velocity = (velocity / length) * max
            this.velocity = multiply(divide(this.velocity, distance(init(), this.velocity)), max);

        }
    }

    void limitBorders(int width, int height) {
        if (getX() < 0.0) {
            position.set(0, width - 5.0);
            position = add(position, velocity);
        }
        if (getX() > width - 1) {
            position.set(0, 5.0);
            position = add(position, velocity);
        }
        if (getY() < 0.0) {
            position.set(1, height - 5.0);
            position = add(position, velocity);
        }
        if (getY() > height - 1) {
            position.set(1, 5.0);
            position = add(position, velocity);
        }
    }
    Vector<Double> add(Vector<Double> a, Vector<Double> b) {
        Vector<Double> c = new Vector<Double>();
        c.add(a.get(0) + b.get(0));
        c.add(a.get(1) + b.get(1));
        return c;
    }

    double dotProduct(Vector<Double> a, Vector<Double> b) {
        return ((a.get(X) * b.get(X)) + (a.get(Y) * b.get(Y)));
    }

    Vector<Double> add(Vector<Double> a, Vector<Double> b, Vector<Double> c, Vector<Double> d) {
        return add(add(a, b), add(c, d));
    }

    Vector<Double> divide(Vector<Double> a, double n) {
        Vector<Double> v = new Vector<Double>();
        v.add(a.get(0) / n);
        v.add(a.get(1) / n);
        return v;
    }

    Vector<Double> sub(Vector<Double> a, Vector<Double> b) {
        Vector<Double> v = new Vector<Double>();
        v.add(a.get(0) - b.get(0));
        v.add(a.get(1) - b.get(1));
        return v;
    }

    Vector<Double> multiply(Vector<Double> a, double d) {
        Vector<Double> v = new Vector<Double>();
        v.add((a.get(0) * d));
        v.add((a.get(1) * d));
        return v;
    }

    double distance(Vector<Double> a, Vector<Double> b) {
        return Math.sqrt(Math.pow(a.get(0) - b.get(0), 2)
                + Math.pow(a.get(1) - b.get(1), 2));
    }

    Vector<Double> init() {
        Vector<Double> v = new Vector<Double>();
        v.add(0.0);
        v.add(0.0);
        return v;
    }

    Vector<Double> init(double x, double y) {
        Vector<Double> v = new Vector<Double>();
        v.add(x);
        v.add(y);
        return v;
    }

    Vector<Double> randomVelocity(int maxVelocity) {
        Vector<Double> v = new Vector<Double>();
        Random random = new Random();
        v.add((double) random.nextInt(maxVelocity) + 1);
        v.add((double) random.nextInt(maxVelocity) + 1);
        return v;
    }

    public void calculatePosition(int width, int height, List<Prey> neighbours, double sepParam, double alParam,
            double cohParam, int maxVelocity, int maxCloseness, int kNeighboursAl, int kNeighboursCoh,
            List<Predator> predators) {
        // TODO Auto-generated method stub

    }
}
