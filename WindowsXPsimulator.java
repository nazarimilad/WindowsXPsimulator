package windowsxpsimulator;

import java.awt.Toolkit;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WindowsXPsimulator extends Application {
	private final Canvas canvas;
	private final GraphicsContext gc;
	private final Image desktop, fout;
	private final EventHandler<MouseEvent> onMouseMovedEventHandler;

	public WindowsXPsimulator() {
		canvas = new Canvas(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());		
											// parameters of the constructor = resolution of screen
		gc = canvas.getGraphicsContext2D();
		fout = new Image(getClass().getResourceAsStream("fout.png"));		// error dialog
		desktop = new Image(getClass().getResourceAsStream("desktop.png"));	//desktop background
		
		onMouseMovedEventHandler = (MouseEvent event) -> {
			gc.drawImage(fout,event.getX(),event.getY());		// put a new error dialog on position of mouse
		};
		canvas.setOnMouseClicked((MouseEvent event) -> {
			if(event.getButton() == MouseButton.PRIMARY)
				canvas.setOnMouseMoved(onMouseMovedEventHandler);	// if primary mouse button is clicked, use the onMouseMovedEventHandler 
											// for mouse movement handling in the canvas
			else if(event.getButton() == MouseButton.SECONDARY) {
				gc.drawImage(desktop, 0, 0);		// else remove the error dialogs, reset the screen
				canvas.setOnMouseMoved(null);		// and unregister onMouseMovedEventHandler from the canvas
			}
		});
	}
	
	@Override
	public void start(Stage primaryStage) {
		gc.drawImage(desktop, 0, 0);
		
		Scene scene = new Scene(new StackPane(canvas));
		
		primaryStage.setTitle("Windows XP");
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint("");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}