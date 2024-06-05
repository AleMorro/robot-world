package controller;

import java.net.URL;
import java.util.ResourceBundle;
import main.RobotWorldApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import model.RobotWorld;

public class StartingController implements Initializable{

	@FXML
	private ChoiceBox<Integer> myChoiceBox;
	@FXML
	private Button startButton;
	
	private RobotWorldApp mainApp;
	
	private Integer[] sizes = {10, 12};
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		myChoiceBox.getItems().addAll(sizes);
	}
	
	/**
	 * Method that allows the game to start with the size chosen by the 
	 * user as a result of pressing the start button
	 * @param event -> press of the startButton
	 */
	public void startGame(ActionEvent event) {
		
		int selectedWidth = myChoiceBox.getValue();
		System.out.println("Larghezza scelta è: "+selectedWidth);
		mainApp.setMapWidth(selectedWidth);
		myChoiceBox.getScene().getWindow().hide();
		mainApp.startNewStage();
		
	}
	
	/**
	 * Method to set the initial application instance
	 * @param main
	 */
	public void setMainApp(RobotWorldApp main) {
		
		this.mainApp = main;
	}
}
