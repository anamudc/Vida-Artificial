package boids2.src;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Food extends AnimationObject {

    private int tamaño = 0;
    int c= 0;
    
    int posicionX = 0;
    int posiciony = 0;
    
    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public Food(int radius, Vector<Double> position, Color color) {
        
            this.color = color;
            this.radius = radius;
            this.position = position;
        try {
            name="pasto.png";
            imagen = new ImageIcon("" + name);
            File imageFile = new File("" + name);
            imagen.setImage(ImageIO.read(getClass().getResourceAsStream(imageFile.toString())));
        } catch (IOException ex) {
            Logger.getLogger(Food.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sandPile(int tamañoMax, ArrayList<Food> neighbours, int kNeighboursAl) {
        this.tamaño++;
        if (this.tamaño > tamañoMax) {
            this.tamaño = 0;
            for (Food b : neighbours) {
                if (!b.equals(this)) {
                    double d = distance(b.position, this.position);
                    if (d < kNeighboursAl && d > 0) {
                        c++;
                        b.sandPile(tamañoMax, neighbours, kNeighboursAl);
                    }
                }
            }
        }
    }
    
}
