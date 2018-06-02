package proyecto;

import pieles.Transform;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Predator extends AnimationObject {

    String ruta = "imgs/";
    File imageFile;

    public Predator(double x, double y, int maxVelocity, int id) {
        this.id = id;
        edad = 1;
        tiempoVida = (int) (Math.random() * 700);
        vidaInicial = tiempoVida;
        radioReprod = 40;//temporal 
        initEdadRepro = (int) (vidaInicial / 4);
        finEdadRepro = (int) (vidaInicial - vidaInicial / 4);
        hambre = tiempoVida-(tiempoVida/7);
        //codigo

        this.color = 5;//(int)(1+Math.random()*5);
        amplitud = (int) (5 + Math.random() * 10);
        verticesLimit = (int) (2 + Math.random() * 4);
        iterTuringMorph = (int) (2000 + Math.random() * 3000);

        Transform transformacion = new Transform();
        imagen = transformacion.cambioPiel("gato.png", "turingmorph3.png", "transparente.png", verticesLimit, amplitud, iterTuringMorph, this.color, false, this.id);

        name = ruta + "true" + id;
        radius = 15;
        position = init(x, y);
        velocity = randomVelocity(maxVelocity);

    }

    public Predator(double x, double y, int velocityB, int id, int tVida, int color, int ampli, int vertL, int iterTuring) {
        this.id = id;
        edad = 1;
        radioReprod = 15;//temporal 
        vidaInicial = tVida;
        tiempoVida = vidaInicial;
        initEdadRepro = (int) (vidaInicial / 3);
        finEdadRepro = (int) (vidaInicial - vidaInicial / 3);
        //codigo

//        isPadre = true;
        
        this.color = (int) (color + (Math.random() + 0.5)%5);
        amplitud = ampli;
        if(vertL>0){
        verticesLimit = vertL;
        }else{
            verticesLimit = (int) (vertL+ (Math.random()+(0.5)+1));
        }
        iterTuringMorph = iterTuring;

        Transform transformacion = new Transform();
        imagen = transformacion.cambioPiel("gato.png", "turingmorph3.png", "transparente.png", verticesLimit, amplitud, iterTuringMorph, this.color, false, this.id);

        name = ruta + "true" + id;
        radius = 15;
        position = init(x, y);
        velocity = randomVelocity(velocityB);

    }

    public void calculatePosition(int width, int height, List<Prey> neighbours, double sepParam, double alParam,
            double cohParam, int maxVelocity, int maxCloseness, int kNeighboursAl, int kNeighboursCoh,
            List<Predator> predators, int nbFood) {
        // TODO Auto-generated method stub

        Vector<Double> s = separatePredators(predators, maxCloseness + 50);
        Vector<Double> a = align(neighbours, kNeighboursAl + 150); //bigger range for chasing birds
        Vector<Double> c = cohesion(neighbours, kNeighboursCoh + 150); //the same as above
        Vector<Double> r = preferirApariencia(predators, kNeighboursCoh + 150);//nbFood+150 

        s = multiply(s, 1.0);
        a = multiply(a, 3.0);
        c = multiply(c, 7.0);
        r = multiply(r, 7.0);

        this.velocity = add(this.velocity, s);

        if (!this.isPadre) {
            velocity = add(this.velocity, r);
        }
        if (this.hambre > tiempoVida) {
            this.velocity = add(this.velocity, a);
            this.velocity = add(this.velocity, c);
        }
        
        limitSpeed(maxVelocity);
        this.position = add(this.position, this.velocity);
        limitBorders(width, height);
        obstacle = false;

    }

    private Vector<Double> preferirApariencia(List<Predator> neighbours, int kNeighboursCoh) {

        Vector<Double> a = init();
        int i = 0;
        synchronized (neighbours) {
            Iterator<Predator> iterator = neighbours.iterator();
            while (iterator.hasNext()) {
                Predator b = iterator.next();
                if (!b.equals(this)) {
                    double d = distance(b.position, this.position);
                    if (d < kNeighboursCoh && d > 0 && b.color == this.color && !isPadre && !b.isPadre) { //
                        a = add(a, b.position);
                        i++;
                    }
                }
            }
        }
        if (i > 0) {
            a = divide(a, i);
            a = divide(sub(a, this.position), 100.0);
        }
        return a;
    }
}
