package main;
import controller.BasicMapController;

import controller.PlayableMapController;
import controller.StartingController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.RobotWorld;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class RobotWorldApp extends Application {
	
	private int mapWidth;
	
	Image icon = new Image("/resource/images/iconRobot.png");
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/view/StartingView.fxml"));
			Parent rootStart = (Parent)startLoader.load();
			StartingController startingController = startLoader.getController();
			startingController.setMainApp(this);
			Scene sceneStart = new Scene(rootStart);
			primaryStage.setScene(sceneStart);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMapWidth() {
		return mapWidth;
	}
	
	public void startNewStage() {
		
		Stage stage1 = new Stage();
		Stage stage2 = new Stage();
		
		RobotWorld sharedModel = new RobotWorld(mapWidth);
		
		String viewPathControl = null;
		String viewPathPlayable = null;
		
		try {
			// choose wich .fxml file to charge
			if(mapWidth == 10) {
				
				viewPathControl = "/view/BasicMap10.fxml";
				viewPathPlayable = "/view/PlayableMap10.fxml";
			} 
			else if(mapWidth == 12) {
				
				viewPathControl = "/view/BasicMap12.fxml";
				viewPathPlayable = "/view/PlayableMap12.fxml";
			}
			
			FXMLLoader basicLoader = new FXMLLoader(getClass().getResource(viewPathControl));
		    Parent root1 = (Parent)basicLoader.load();
		    BasicMapController basicController = basicLoader.<BasicMapController>getController();
		    basicController.setModel(sharedModel);
		    Scene scene1 = new Scene(root1);
		    // set Title and icon
		    stage1.setTitle("ROBOT WORLD(Control)");
		    stage1.getIcons().add(icon);
		    stage1.setResizable(false);
		    stage1.setScene(scene1); 
		    
		    FXMLLoader playableLoader = new FXMLLoader(getClass().getResource(viewPathPlayable));
		    Parent root2 = (Parent)playableLoader.load();
		    PlayableMapController playableController = playableLoader.<PlayableMapController>getController();
		    playableController.setModel(sharedModel);
		    Scene scene2 = new Scene(root2);
		    // set Title and icon
		    stage2.setTitle("ROBOT WORLD");
		    stage2.getIcons().add(icon);
		    stage2.setResizable(false);
		    stage2.setScene(scene2);
		    
		    stage1.show();
		    stage2.show();
		    
		} catch(Exception e) {
			
			e.printStackTrace();
		}	
	}
}
