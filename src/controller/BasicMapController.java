package controller;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.RobotWorld;

public class BasicMapController implements Initializable, RobotWorldObserver {
	
	@FXML
	private GridPane myMap;
	
	private RobotWorld model;
	
	private ImagesManager imgMan;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			
			int[][] mapModel = model.getMap();
			int r, c;
			
			this.imgMan = new ImagesManager(model.getMap().length);
		
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
			
			// Initialize the basicMap on the base of the model map
			for(int i=1; i<mapModel.length-1; i++) {
				for(int j=1; j<mapModel.length-1; j++) {
					
					ImageView imgView = new ImageView();
			        r = i;
			        c = j;

			        switch(mapModel[i][j]) {
			        case 1:
			           imgView.setImage(imgMan.imagesElements.get(1));
			           break;
			        case 3:
			           imgView.setImage(imgMan.imagesElements.get(3));
			           break;
			        case 6:
			           imgView.setImage(imgMan.imagesElements.get(6));
			           break;
			        case 8:
			           imgView.setImage(imgMan.imagesElements.get(8));
			           break;
			        case 9:
			           String dir = model.getDirection();

			           if(dir == "up") {
			        	   imgView.setImage(imgMan.imagesPlayer.get(0));
			        	   break;
			           }
			           else if(dir == "down") {
			        	   imgView.setImage(imgMan.imagesPlayer.get(1));
			        	   break;
			           }
			           else if(dir == "right") {
			        	   imgView.setImage(imgMan.imagesPlayer.get(2));
			        	   break;
			           }
			           else {
			        	   imgView.setImage(imgMan.imagesPlayer.get(3));
			        	   break;
			           }
			       }
			       myMap.add(imgView, c, r);
				}
			}
			model.addObserver(this);
		});
	}
	
	/*
	 * Methos to set the sharedModel
	 */
	public void setModel(RobotWorld sharedModel) {
		
		this.model = sharedModel;
	}
	
	/**
	 * Method that updates the view whenever there is a 
	 * change in the model class
	 */
	@Override
	public void updateView() {
		int[][] mapModel = model.getMap();
		
		for(int i=1; i<mapModel.length-1; i++) {
			for(int j=1; j<mapModel.length-1; j++) {
				
				ImageView imgView = new ImageView();
		        int r = i;
		        int c = j;
		        
		        switch(mapModel[i][j]) {
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
		        	break;
		       
		        case 9:
		        	String dir = model.getDirection();

		        	if(dir == "up") {
		        		imgView.setImage(imgMan.imagesPlayer.get(0));
		        		break;
		        	}
		        	else if(dir == "right") {
		        		imgView.setImage(imgMan.imagesPlayer.get(1));
		        		break;
		        	}
		        	else if(dir == "down") {
		        		imgView.setImage(imgMan.imagesPlayer.get(2));
		        		break;
		        	}
		        	else {
		        		imgView.setImage(imgMan.imagesPlayer.get(3));
		        		break;
		        	}
		        case 10:
		        	imgView.setImage(imgMan.imagesBackGround.get(0));
		        	break;
		        }
		        
		        myMap.add(new ImageView(imgMan.imagesBackGround.get(0)), c, r);
		        myMap.add(imgView, c, r);
			}
		}
	}
}
