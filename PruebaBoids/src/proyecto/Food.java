package proyecto;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Food extends AnimationObject {

    private int tamaño = 0;
    int c = 0;

    int posicionX = 0;
    int posiciony = 0;

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public Food(int radius, Vector<Double> position, int color) {
        
        tiempoVida = 300;
        
        this.color = color;
        this.radius = radius;
        this.position = position;
        try {
            name = "queso.png";
            imagen = new ImageIcon("" + name);
            File imageFile = new File("" + name);
            InputStream data = getClass().getResourceAsStream(imageFile.toString());
            imagen.setImage(ImageIO.read(data));
        } catch (IOException ex) {
            Logger.getLogger(Food.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
