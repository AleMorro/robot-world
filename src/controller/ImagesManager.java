package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class ImagesManager {
	
	// Class for organized management of various images 
	
	protected List<Image> imagesElements = new ArrayList<>();
	protected List<Image> imagesBackGround = new ArrayList<>();
	protected List<Image> imagesPlayer = new ArrayList<>();
	
	public ImagesManager(int size) {
		
		if(size == 10) { // images for 10x10 map
			// from 0 to 8
			this.imagesElements.add(new Image("/resource/images/dieci/brick-wall.png")); // 0
			this.imagesElements.add(new Image("/resource/images/dieci/washing-machine.png")); // 1
			this.imagesElements.add(new Image("/resource/images/dieci/washing-machine-broken.png")); // 2
			this.imagesElements.add(new Image("/resource/images/dieci/sink.png")); // 3
			this.imagesElements.add(new Image("/resource/images/dieci/sink-open.png")); // 4
			this.imagesElements.add(new Image("/resource/images/dieci/water.png")); // 5
			this.imagesElements.add(new Image("/resource/images/dieci/stove.png")); // 6
			this.imagesElements.add(new Image("/resource/images/dieci/stove-open.png")); // 7
			this.imagesElements.add(new Image("/resource/images/dieci/dog.png")); // 8
			// from 0 to 1
			this.imagesBackGround.add(new Image("resource/images/dieci/white-square.png"));
			this.imagesBackGround.add(new Image("resource/images/dieci/black-square.png"));
			// from 0 to 3
			this.imagesPlayer.add(new Image("/resource/images/dieci/robotUp.png"));
			this.imagesPlayer.add(new Image("/resource/images/dieci/robotRight.png"));
			this.imagesPlayer.add(new Image("/resource/images/dieci/robotDown.png"));
			this.imagesPlayer.add(new Image("/resource/images/dieci/robotLeft.png"));
		} 
		else if(size == 12) { // images for 12x12 map
			// from 0 to 8
			this.imagesElements.add(new Image("/resource/images/dodici/brick-wall.png")); // 0
			this.imagesElements.add(new Image("/resource/images/dodici/washing-machine.png")); // 1
			this.imagesElements.add(new Image("/resource/images/dodici/washing-machine-broken.png")); // 2
			this.imagesElements.add(new Image("/resource/images/dodici/sink.png")); // 3
			this.imagesElements.add(new Image("/resource/images/dodici/sink-open.png")); // 4
			this.imagesElements.add(new Image("/resource/images/dodici/water.png")); // 5
			this.imagesElements.add(new Image("/resource/images/dodici/stove.png")); // 6
			this.imagesElements.add(new Image("/resource/images/dodici/stove-open.png")); // 7
			this.imagesElements.add(new Image("/resource/images/dodici/dog.png")); // 8
			// from 0 to 1
			this.imagesBackGround.add(new Image("resource/images/dodici/white-square.png"));
			this.imagesBackGround.add(new Image("resource/images/dodici/black-square.png"));
			// from 0 to 3
			this.imagesPlayer.add(new Image("/resource/images/dodici/robotUp.png"));
			this.imagesPlayer.add(new Image("/resource/images/dodici/robotRight.png"));
			this.imagesPlayer.add(new Image("/resource/images/dodici/robotDown.png"));
			this.imagesPlayer.add(new Image("/resource/images/dodici/robotLeft.png"));
		}
	}
}
