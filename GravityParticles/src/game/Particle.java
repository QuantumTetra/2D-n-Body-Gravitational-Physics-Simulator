package game;

public class Particle {
	double x;
	double y;
	double vx;
	double vy;
	double mass;
	double radius;
	private Game m;
	boolean clicked = false;
	int clickX = 0;
	int clickY = 0;
	int difX = 0;
	int difY = 0;
	boolean dragging = false;
	public Particle(Game game, double X, double Y, double VX, double VY, double MASS, double RADIUS){
		m=game;
		x=X;
		y=Y;
		vx=VX;
		vy=VY;
		mass=MASS;
		radius=RADIUS;
	}
	public Particle(Game game, double X, double Y, Particle orbiting, double MASS, double RADIUS){
		m=game;
		x=X;
		y=Y;
		vx=m.calcSpeedX(this, orbiting);
		vy=m.calcSpeedY(this, orbiting);
		mass=MASS;
		radius=RADIUS;
	}
}
