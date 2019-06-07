package game;

public class deltaV {
	private double deltaX;
	private double deltaY;
	
	public deltaV (double dx, double dy){
		deltaX = dx;
		deltaY = dy;
	}
	
	public double getDX(){
		return deltaX;
	}
	
	public double getDY(){
		return deltaY;
	}
}
