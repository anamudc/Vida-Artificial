package view;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JComponent;
import model.Flock;


/**
 * World class creates the canvas. Which represents the boids environment.
 * @author Shaun
 *
 */
public class World extends Application {
	private Flock flock;
	private static final int width = 1200;
	private static final int height = 800;
	
	public World() {
		this.flock = new Flock();
	}
	
	/**
	 * Run the animation
	 * 
	 * Every boid is represented by an <Code>ImageView</Code> object. Each 
	 * is added to the scene. By using keyframes the positions of the 
	 * boids are updated in the model and their new representations displayed.
	 */
	public void animateFlock(final Scene scene) {
    	List<ImageView> images = flock.getBoidsRepresentation();

        final Group root = (Group) scene.getRoot();        
        root.getChildren().addAll(images);

        
        Timeline tl = new Timeline();
        tl.setCycleCount(Animation.INDEFINITE);
        KeyFrame update = new KeyFrame(Duration.seconds(.0200),
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                    	flock.updateBoidsPostion();
                    }
                });

        tl.getKeyFrames().add(update);
        tl.play();
        
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Boids Flocking Algorithm");
        Group root = new Group();
        
        
        Scene scene = new Scene(root, Color.WHITE);
        
        primaryStage.setScene(scene);
        animateFlock(scene); 
        primaryStage.show();		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
