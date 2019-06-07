package game;

public class PolarVector {
	private double Vt;
	private double rotation;
	public PolarVector(double Vx, double Vy){
		Vt = Math.sqrt(Math.pow(Vx, 2) + Math.pow(Vy, 2));
		rotation = fixRotation(Math.atan2(Vy, Vx));
	}
	public double getRotation() {
		return rotation;
	}
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	public double getVt() {
		return Vt;
	}
	public void setVt(double vt) {
		Vt = vt;
	}
	public double getVx(){
		return Vt * Math.cos(rotation);
	}
	public double getVy(){
		return Vt * Math.sin(rotation);
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
