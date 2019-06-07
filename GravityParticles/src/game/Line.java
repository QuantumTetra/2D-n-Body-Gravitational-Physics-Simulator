package game;

import java.awt.Color;

public class Line {
	PolarPoint position;
	double rotationalVelocity = 0;
	Color color;
	public Line(double length , double rotation, Color c){
		position = new PolarPoint(length, rotation, true);
		color=c;
	}
}
