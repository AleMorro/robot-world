package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import model.RobotWorld;

public class PlayableMapController implements Initializable{

	@FXML
	private GridPane myPlayableMap;

	@FXML
	private Button rotateSxButton, rotateDxButton, moveButton, interactionButton;
	
	private RobotWorld model;
	
	private int RPlayer;
 	private int CPlayer;
 	private String dirPlayer;
 	
 	private int r;
 	private int c;
 	
 	private ImagesManager imgMan;
 	
 	/*
	 * Function to set the sharedModel
	 */
	public void setModel(RobotWorld sharedModel) {

		this.model = sharedModel;
	}
	
 	@Override
 	public void initialize(URL arg0, ResourceBundle arg1) {

 		Platform.runLater(() -> {
			
 			/* Symbols in the map:
 			 * 0 = wall;
 			 * 1 = washing machine
 			 * 2 = washing machine broken
 			 * 3 = sink off
 			 * 4 = sink on
 			 * 5 = water
 			 * 6 = stove off
 			 * 7 = stove open
 			 * 8 = dog
 			 * 9 = player
 			 * 10 = free space
 			 */
 			
 			this.imgMan = new ImagesManager(model.getMap().length);
 			// Initialize the basicMap on the base of the model map

			this.RPlayer = model.getPlayerRPos();
			this.CPlayer = model.getPlayerCPos();

			this.dirPlayer = model.getDirection();

			switch(dirPlayer) {
			case "up":
				myPlayableMap.add(new ImageView(imgMan.imagesPlayer.get(0)), CPlayer, RPlayer);
				break;
			case "right":
				myPlayableMap.add(new ImageView(imgMan.imagesPlayer.get(1)), CPlayer, RPlayer);
				break;
			case "down":
				myPlayableMap.add(new ImageView(imgMan.imagesPlayer.get(2)), CPlayer, RPlayer);
				break;
			case "left":
				myPlayableMap.add(new ImageView(imgMan.imagesPlayer.get(3)), CPlayer, RPlayer);
				break;
			}

			revealMap(RPlayer-1, CPlayer);
			revealMap(RPlayer+1, CPlayer);
			revealMap(RPlayer, CPlayer-1);
			revealMap(RPlayer, CPlayer+1);
		});
	}

	/*
	 * Method that changes the image to the corresponding 
	 * location in the map
	 * @param r -> row index for the model map
	 * @param c -> column index for the model map
	 */
	private void revealMap(int r, int c) {

		ImageView imgView = new ImageView();

		switch(model.getMapElement(r, c)) {
		case 0:
			imgView.setImage(imgMan.imagesElements.get(0));
			break;
		case 1:
			imgView.setImage(imgMan.imagesElements.get(1));
			break;
		case 2:
			imgView.setImage(imgMan.imagesElements.get(2));
			break;
		case 3:
			imgView.setImage(imgMan.imagesElements.get(3));
			break;
		case 4:
			imgView.setImage(imgMan.imagesElements.get(4));
			break;
		case 5:
			imgView.setImage(imgMan.imagesElements.get(5));
			break;
		case 6:
			imgView.setImage(imgMan.imagesElements.get(6));
			break;
		case 7:
			imgView.setImage(imgMan.imagesElements.get(7));
			break;
		case 8:
			imgView.setImage(imgMan.imagesElements.get(8));
		}

		myPlayableMap.add(new ImageView(imgMan.imagesBackGround.get(0)), c, r);
		myPlayableMap.add(imgView, c, r);
	}

	/*
	 * Method to rotate left action
	 */
	public void rotateLeft(ActionEvent event) {

		// System.out.println("Rotate left");
		model.rotateLeft();
		this.dirPlayer = model.getDirection();
		revealMap(RPlayer-1, CPlayer);
		revealMap(RPlayer+1, CPlayer);
		revealMap(RPlayer, CPlayer-1);
		revealMap(RPlayer, CPlayer+1);
		setPlayerImg();
	}

	/*
	 * Merhod to rotate right action
	 */
	public void rotateRight(ActionEvent event) {

		// System.out.println("Rotate right");
		model.rotateRight();
		this.dirPlayer = model.getDirection();
		revealMap(RPlayer-1, CPlayer);
		revealMap(RPlayer+1, CPlayer);
		revealMap(RPlayer, CPlayer-1);
		revealMap(RPlayer, CPlayer+1);
		setPlayerImg();
	}

	/*
	 * Function to move the player on the direction
	 */
	public void move(ActionEvent event) {
		
		try {
			// System.out.println("Moved");
			model.move();
			myPlayableMap.add(new ImageView(imgMan.imagesBackGround.get(0)), CPlayer, RPlayer);
			refreshPlayerData();

			setPlayerImg();
			
			revealMap(RPlayer-1, CPlayer);
			revealMap(RPlayer+1, CPlayer);
			revealMap(RPlayer, CPlayer-1);
			revealMap(RPlayer, CPlayer+1);
		} catch(RobotWorld.IllegalMoveException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("BUMP!");
			alert.setHeaderText("OH NO!");
			alert.setContentText("You cannot move in a occupied square!");
			alert.show();
		}
	}

	/*
	 * Function to interact with the element of the map
	 */
	public void interaction(ActionEvent event) {
		
		try {
			// System.out.println("Interact");
			model.interact();
			
			switch(checkAdjSquare()) {
			// case the robot repaired the washing machine
			case 1:
				myPlayableMap.add(new ImageView(imgMan.imagesBackGround.get(0)), c, r);
				myPlayableMap.add(new ImageView(imgMan.imagesElements.get(1)), c, r);
				revealMap(r-1, c);
				revealMap(r+1, c);
				revealMap(r, c-1);
				revealMap(r, c+1);
				setPlayerImg();
				break;
			// case the robot repaired the sink
			case 3:
				myPlayableMap.add(new ImageView(imgMan.imagesBackGround.get(0)), c, r);
				myPlayableMap.add(new ImageView(imgMan.imagesElements.get(3)), c, r);
				revealMap(r-1, c);
				revealMap(r+1, c);
				revealMap(r, c-1);
				revealMap(r, c+1);
				setPlayerImg();
				break;
			// case the robot turn off the stove
			case 6:
				myPlayableMap.add(new ImageView(imgMan.imagesBackGround.get(0)), c, r);
				myPlayableMap.add(new ImageView(imgMan.imagesElements.get(6)), c, r);
				break;
			// case the robot turn on the stove
			case 7:
				myPlayableMap.add(new ImageView(imgMan.imagesBackGround.get(0)), c, r);
				myPlayableMap.add(new ImageView(imgMan.imagesElements.get(7)), c, r);
				revealMap(r-1, c);
				revealMap(r+1, c);
				revealMap(r, c-1);
				revealMap(r, c+1);
				setPlayerImg();
				break;
			}
			
		} catch(RobotWorld.IllegalMoveException e) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("NO INTERACTION AVAILABLE!");
			alert.setHeaderText("OH NO!");
			alert.setContentText("There is no element to interact with!");
			alert.show();
		}
	}
	
	// Method that returns the element adjacent to the robot based 
	// on the direction of it
	private int checkAdjSquare() {
		
		switch(dirPlayer) {
		case "up":
			r = RPlayer-1;
			c = CPlayer;
			return model.getMapElement(r, c);
		case "right":
			r = RPlayer;
			c = CPlayer+1;
			return model.getMapElement(r, c);
		case "down":
			r = RPlayer+1;
			c = CPlayer;
			return model.getMapElement(r, c);
		case "left":
			r = RPlayer;
			c = CPlayer-1;
			return model.getMapElement(r, c);
		}
		return -1;	
	}

	/*
	 * Method to retrieve the new information from the model
	 * after a change event
	 */
	private void refreshPlayerData() {

		RPlayer = model.getPlayerRPos();
		CPlayer = model.getPlayerCPos();
		this.dirPlayer = model.getDirection();
	}
	
	/*
	 * Method to place the right player image according to the direction
	 */
	private void setPlayerImg() {
		
		switch(dirPlayer) {
		case "up":
			myPlayableMap.add(new ImageView(imgMan.imagesPlayer.get(0)), CPlayer, RPlayer);
			break;
		case "right":
			myPlayableMap.add(new ImageView(imgMan.imagesPlayer.get(1)), CPlayer, RPlayer);
			break;
		case "down":
			myPlayableMap.add(new ImageView(imgMan.imagesPlayer.get(2)), CPlayer, RPlayer);
			break;
		case "left":
			myPlayableMap.add(new ImageView(imgMan.imagesPlayer.get(3)), CPlayer, RPlayer);
			break;
		}
	}
}