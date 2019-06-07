package game;

import java.awt.Image;
import java.util.ArrayList;

public class Animation {
	private ArrayList<Image> images = new ArrayList<>();
	private int onFrame;
	private int maxFrame;
	private int frameTime;
	private int timeInAnimation = 0;
	public Animation(ArrayList<Image> image, int frames){
		images = image;
		onFrame = 0;
		maxFrame = images.size()-1;
		frameTime = frames;
	}
	private void incrementFrame(){
		if(onFrame<maxFrame){
			onFrame++;
		}else{
			onFrame = 0;
		}
	}
	public void setFrame(int newFrame){
		if(newFrame<=maxFrame){
			onFrame = newFrame;
		}else{
			onFrame = 0;
		}
	}
	public void incrementTime(){
		timeInAnimation++;
		updateAnimation();
	}
	private void updateAnimation(){
		if(timeInAnimation%frameTime==0){
			incrementFrame();
		}
	}
	public Image getCurrentImage(){
		return images.get(onFrame);
	}
	public int getFrameTime(){
		return frameTime;
	}
}
