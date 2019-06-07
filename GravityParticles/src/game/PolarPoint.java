package game;


public class PolarPoint {
	double radius;
	double rotation;
	public PolarPoint(double x, double y){
		radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		rotation = fixRotation(Math.atan2(y, x));
	}
	public PolarPoint(double Radius, double Rotation, boolean differentConstructor){
		radius = Radius;
		rotation = Rotation;
	}
	public double getRadius(){
		return radius;
	}
	public void setRadius(double r){
		radius = r;
	}
	public double getX(){
		return radius * Math.cos(rotation);
	}
	public double getY(){
		return radius * Math.sin(rotation);
	}
	public double getRotation(){
		return rotation;
	}
	public void setRotation(double r){
		rotation =r;
	}
	public Point getPoint(){
		return new Point((int)(radius * Math.cos(rotation)), (int)(radius*Math.sin(rotation)));
	}
	public double fixRotation(double rotation){
		if(rotation>2*Math.PI){
			int howfar = (int)(rotation/Math.PI/2);
			for(int c=0;c<howfar;c++){
				rotation-=2*Math.PI;
			}
		}
		if(rotation<0){
			int howfar = 1+ (int)((rotation*-1.0)/Math.PI/2);
			for(int c=0;c<howfar;c++){
				rotation+=2*Math.PI;
			}
		}
		return rotation;
	}
}
