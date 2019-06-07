package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.swing.*;




public class Game {
	Scanner scan = new Scanner(System.in);
	public JFrame f = new JFrame();
	@SuppressWarnings("serial")
	public JPanel p = new JPanel(){
		@Override
		protected void paintComponent (Graphics g){
			super.paintComponent(g);
			g.drawImage(i, 0, 0, null);
		}
	};
	public JPanel p2 = new JPanel(){
		@Override
		protected void paintComponent (Graphics g){
			super.paintComponent(g);
			g.drawImage(i, 0, 0, null);
		}
	};
	public Image i = f.createImage(1920,1080);
	public Image i_R = f.createImage(1920,1080);
	public int TargetFPS = 60;
	public int pauseTime = 1000/TargetFPS;
	public double timeStep = 1.0 /TargetFPS;
	public double MX = 0;
	public double MY = 0;
	public boolean mousePressed = false;
	public boolean mouseReleased = true;
	public boolean rightClick = false;
	public boolean exit = false;
	public int ticks = 0;
	public int fps = 0;
	public String fpsString = "";
	public boolean[] keyMap = new boolean[256];
	boolean[] releasedMap = new boolean[256];

	public double G = 6.674*Math.pow(10, -11);
	public double timeMultiplier = 1.0;
	public ArrayList<Particle> particle = new ArrayList<>();
	public Particle mainParticle;
	boolean pressed = true;
	double previousTime = 0.0;
	boolean pausedThisTick=false;
	boolean paused =false;
	boolean tracks = false;




	public Game(String classPath) throws IOException{
		Scanner scan =new Scanner(System.in);
		System.out.println("Which would you like?\n1. cubic random\n2. cubic\n3. galactic\n4. binary");
		String input = scan.nextLine();
		//-Dsun.java2d.xrender=True -Dsun.java2d.opengl=True
		f.addMouseListener(new MouseClickCheck(this));
		f.addKeyListener(new Keychecker(this));
		//p.addKeyListener(new Keychecker(this));
		p.addMouseListener(new MouseClickCheck(this));
		f.setSize(1920, 1080);
		f.setUndecorated(true);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setVisible(true);
		p.setLayout(new GridLayout(20,20));
		p.setBackground(new Color (255,255,255));
		p2.setLayout(new GridLayout(5,5));
		p2.setBackground(new Color (255,255,0));
		f.add(p);
		//f.addMouseMotionListener(new MousePosCheck(this));
		p.addMouseMotionListener(new MousePosCheck(this));
		f.setName("Slide Show");
		p.repaint();
		i = f.createImage(1920,1080);
		i_R = f.createImage(1920, 1080);
		f.toFront();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(input.toLowerCase().equals("1")){
			for(int c=0;c<14;c++){
				double spacingX = (c + 1.0) * 16.0 * 4.0 * 2.0;
				for(int c2=0;c2<14;c2++){
					double spacingY = (c2+1.0)*9.0* 4.0 *2.0;
					particle.add(new Particle(this,spacingX, spacingY, 0, 0,100  +Math.random() * 1000.0,3));
				}//Math.random() * .0002 - .0001
			}
		}else if(input.toLowerCase().equals("2")){
			for(int c=0;c<14;c++){
				double spacingX = (c + 1.0) * 16.0 * 4.0 * 2.0;
				for(int c2=0;c2<14;c2++){
					double spacingY = (c2+1.0)*9.0* 4.0 *2.0;
					particle.add(new Particle(this,spacingX, spacingY, 0, 0,1000,3));
				}
			}
		}else if(input.toLowerCase().equals("3")){
			timeMultiplier = 0.000000000001;
			particle.add(new Particle(this,960,540, 0,0,1.0 * Math.pow(10, 28),14.84));
			mainParticle = particle.get(0);
			for(int c=0;c<50;c++){
				double radius = Math.random()*300.0 + 15.0;
				double rotation = Math.random()*2.0*Math.PI;
				particle.add(new Particle(this,960 + radius*Math.cos(rotation),540 + radius*Math.sin(rotation), mainParticle,100  +Math.random() * 1000.0,1));
			}
			for(int c=0;c<50;c++){
				double radius = Math.random()*550.0 + 15.0;
				double rotation = Math.random()*2.0*Math.PI;
				particle.add(new Particle(this,960 + radius*Math.cos(rotation),540 + radius*Math.sin(rotation), mainParticle,100  +Math.random() * 2000.0,2));
			}
		}else if(input.toLowerCase().equals("4")){
			Particle star1 = new Particle(this,960.0, 490, 0,0,125,3);
			Particle star2 = new Particle(this,960.0, 490, 0,0,1000,3);
			particle.add(new Particle(this,950.0, 490, star1,500,3));
			particle.add(new Particle(this,970.0, 490, star1,500,3));
			
			particle.add(new Particle(this,960, 490 + 100, star2,50,3));
			
			
		}else if(input.toLowerCase().equals("secret")){
			particle.add(new Particle(this,950.0, 490, 0,0,100,3));
			particle.add(new Particle(this,970.0, 490, 0,0,100,3));

		}else{
			
		}
	}
	public void playGame() throws IOException{
		
		for(int c= 0; c<keyMap.length;c++){
			keyMap[c]=false;
			releasedMap[c] = true;
		}

		while(exit == false){
			ticks++;
			pausedThisTick=false;
			if(rightClick && ticks%10==0&&mainParticle!=null){
				particle.add(new Particle(this,MX, MY, mainParticle,100  +Math.random() * 500.0,3));
			}
			
			/*if(mainParticle!=null && ticks==2000){
				particle.add(new Particle(this,960, 540+7, 0,299792458,50,2));
			}*/
			
			long timestart = 0;
			timestart = System.currentTimeMillis();
			//Graphics here
			
			runGraphics();

			//Game
			runKeys();
			runPhysics();

			//key system
			runReleaseSystems();
			//Time
			
			long time = (System.currentTimeMillis()-timestart);
			long timeToPause = pauseTime-2-time;
			if(timeToPause<4){
				timeToPause=4;
			}
			pause(timeToPause);
			resetImage();

			long endtime = 0;
			if(ticks%2==0){
				//printKeyMap();
				endtime = System.currentTimeMillis();
				int totaltime = (int)(endtime-timestart);
				fps = 1000 / totaltime;
				fpsString = ""+fps+" timemultiplier: " + timeMultiplier+" "+ticks+" "+MX+" "+MY;
			}
		}
	}
	public void runReleaseSystems(){
		for(int c=0;c<keyMap.length;c++){
			if(keyMap[c])
				releasedMap[c] = false;
			else
				releasedMap[c] = true;
		}
		if(mousePressed){
			mouseReleased = false;
		}
		if(!mousePressed){
			mouseReleased = true;
		}
	}
	public void runKeys(){
		if(keyMap[46]&&releasedMap[46]){
			increaseTime();
		}
		boolean trackChanged=false;
		if(keyMap[84]&&releasedMap[84]){
			if(tracks){
				trackChanged=true;
				tracks=false;
			}
			if(!tracks && !trackChanged){
				trackChanged=true;
				tracks=true;
			}
		}

		if(keyMap[44]&&releasedMap[44]){
			decreaseTime();
		}
		if(keyMap[32]&&releasedMap[32]&&timeMultiplier==0.0){
			timeMultiplier=previousTime;
			pausedThisTick=true;
			paused=true;
		}
		if(keyMap[32]&&releasedMap[32]&&timeMultiplier!=0.0&&!pausedThisTick){
			previousTime=timeMultiplier;
			timeMultiplier = 0.0;
			paused=false;
		}
		if(keyMap[27]==true){
			exit=true;
		}
	}
	public void runGraphics(){
		f.toFront();
		f.requestFocusInWindow();
		f.requestFocus();
		ArrayList<Particle> removes = new ArrayList<>();
		Graphics g = i.getGraphics();
		g.setColor(new Color(0,0,0));
		g.drawString(fpsString, 10, 10);
		for(Particle p: particle){
			fillCircle(p.x,p.y,p.radius,new Color(0,0,0));
			double distance = Math.sqrt(Math.pow(p.x - MX, 2) + Math.pow(p.y - MY, 2));
			if(distance<=p.radius&&mousePressed&&mouseReleased){
				if(p.clicked){
					p.clicked=false;
				}else{
					p.clicked=true;
					p.clickX=(int) MX;
					p.clickY=(int) MY;
				}
			}
			if(p.clicked && pointCheck((int)MX,p.clickX,p.clickX +200) && pointCheck((int)MY,p.clickY-10,p.clickY) && mousePressed && !p.dragging&&mouseReleased){
				p.dragging=true;
				p.difX = (int)(MX) - p.clickX;
				p.difY = (int)(MY) - p.clickY;
			}
			if(p.clicked && pointCheck((int)MX,p.clickX+200,p.clickX +200+10) && pointCheck((int)MY,p.clickY-10,p.clickY) && mousePressed&&mouseReleased){
				p.clicked=false;
			}
			if(p.clicked && pointCheck((int)MX,p.clickX,p.clickX +50) && pointCheck((int)MY,p.clickY+80,p.clickY+80+10) && mousePressed&&mouseReleased){
				removes.add(p);
			}
			if(!mousePressed && p.dragging){
				p.dragging=false;
			}
			if(p.dragging){
				p.clickX = (int)MX - p.difX;
				p.clickY = (int)MY - p.difY;
			}
		}
		for(Particle p: particle){
			if(p.clicked){
				g.drawLine((int)p.x, (int)p.y, p.clickX, p.clickY);
				g.fillRect(p.clickX, p.clickY - 10, 200, 10);
				g.setColor(new Color(255,0,0));
				g.fillRect(p.clickX + 200, p.clickY-10, 10, 10);
				g.setColor(new Color(150,150,150));
				g.drawLine(p.clickX+200+1, p.clickY-10+1, p.clickX+200+10-1, p.clickY-1);
				g.drawLine(p.clickX+200+1, p.clickY-1, p.clickX+200+10-1, p.clickY-10+1);
				g.setColor(new Color(150,150,150));
				g.fillRect((int)p.clickX, (int)p.clickY, 200, 80);
				g.setColor(new Color(255,0,0));
				g.fillRect((int)p.clickX, (int)p.clickY+80, 50, 10);
				g.setColor(new Color(0,0,0));
				g.drawString("mass: "+p.mass, (int)p.clickX, (int)p.clickY+10);
				g.drawString("rad: "+p.radius, (int)p.clickX, (int)p.clickY+20);
				g.drawString("x: "+p.x, (int)p.clickX, (int)p.clickY+30);
				g.drawString("y: "+p.y, (int)p.clickX, (int)p.clickY + 40);
				g.drawString("vx: "+p.vx, (int)p.clickX, (int)p.clickY + 50);
				g.drawString("vy: "+p.vy, (int)p.clickX, (int)p.clickY + 60);
				g.drawString("vt: "+Math.sqrt(Math.pow(p.vx, 2) + Math.pow(p.vy, 2)), (int)p.clickX, (int)p.clickY + 70);
				g.drawString("%c: "+(int)(Math.sqrt(Math.pow(p.vx, 2) + Math.pow(p.vy, 2)) / 299792458.0 * 100.0)+"%", (int)p.clickX, (int)p.clickY + 80);
				g.drawString("Remove",p.clickX, (int)p.clickY + 90);
			}
		}
		for(int c=0;c<removes.size();c++){
			particle.remove(removes.get(c));
		}
		p.repaint();
	}
	public void runPhysics(){
		for( Particle P : particle){
			for(Particle P2: particle){
				if(particle.indexOf(P)!=particle.indexOf(P2)){
					deltaV D = calcGrav(P.x-P2.x,P.y-P2.y,P.mass);
					P2.vx = (P2.vx+D.getDX());
					P2.vy = (P2.vy+D.getDY());
				}
			}
		}

		//move planets

		for(Particle P: particle){
			//System.out.println("tick: "+ticks+"\n\n Planet "+planet.indexOf(P)+":\nX: "+P.getX()+"\nY: "+P.getY()+"\nVX: "+P.getVX()+"\nVY: "+P.getVY());
			P.x = (P.x+(P.vx/timeStep*timeMultiplier));
			P.y = (P.y+(P.vy/timeStep*timeMultiplier));
		}
		ArrayList<Particle> toRemove = new ArrayList<>();
		ArrayList<Particle> toAdd = new ArrayList<>();
		for(int c=0;c<particle.size();c++){
			Particle P = particle.get(c);
			for(int c2=c;c2<particle.size();c2++){
				Particle P2 = particle.get(c2);
				if(P!=P2){
					if(distance(P,P2) < P.radius + P2.radius){
						toRemove.add(P);
						toRemove.add(P2);
						 double mass = P.mass + P2.mass;
						 double radius = Math.sqrt( (Math.pow(P.radius, 2) + Math.pow(P2.radius, 2)));
						 double x = (P.mass * P.x + P2.mass * P2.x) / mass;
						 double y = (P.mass * P.y + P2.mass * P2.y) / mass;
						 double vx = (P.vx * P.mass + P2.vx * P2.mass) / mass;
						 double vy = (P.vy * P.mass + P2.vy * P2.mass) / mass;
						 toAdd.add(new Particle(this , x,y,vx,vy,mass,radius));
					}
				}
				for(int c3=0;c3<toRemove.size();c3++){
					particle.remove(toRemove.get(c3));
				}
				for(int c3=0;c3<toAdd.size();c3++){
					particle.add(toAdd.get(c3));
				}
				toRemove.clear();
				toAdd.clear();
			}
		}
	}
	public boolean pointCheck(int point, int leftBound, int rightBound){
		return point>=leftBound && point<=rightBound;
	}
	public double distance(Particle p, Particle p2){
		return Math.sqrt(Math.pow(p.x - p2.x, 2) + Math.pow(p.y - p2.y, 2));
	}
	public double calcSpeedX(Particle orbiter, Particle orbiting){
		double distance = getDistance(orbiter.x,orbiter.y,orbiting.x,orbiting.y);
		return Math.sqrt(G*orbiting.mass*(2.0/distance -1.0/distance)) * Math.sin((Math.atan2(orbiter.y - orbiting.y, orbiter.x - orbiting.x)));
	}
	public double calcSpeedY(Particle orbiter, Particle orbiting){
		double distance = getDistance(orbiter.x,orbiter.y,orbiting.x,orbiting.y);
		return Math.sqrt(G*orbiting.mass*(2.0/distance -1.0/distance)) * -1.0 * Math.cos((Math.atan2(orbiter.y - orbiting.y, orbiter.x - orbiting.x)));
	}
	public double getDistance(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	public  deltaV calcGrav(double x1_minus_x2 ,double y1_minus_y2 ,double mass){
		double angle = fixRotation(Math.atan2((y1_minus_y2*-1.0),(x1_minus_x2*-1.0)));
		double deltaX = ((G*(mass/Math.pow(Math.sqrt((Math.pow(x1_minus_x2, 2)+Math.pow(y1_minus_y2, 2))),2) ))*Math.cos(angle))/timeStep*timeMultiplier;
		double deltaY = ((G*(mass/Math.pow(Math.sqrt((Math.pow(x1_minus_x2, 2)+Math.pow(y1_minus_y2, 2))),2) ))*Math.sin(angle))/timeStep*timeMultiplier;
		deltaX*=-1.0;
		deltaY*=-1.0;
		return new deltaV(deltaX, deltaY);
	}
	public void increaseTime(){
		if(timeMultiplier<1*Math.pow(10, 4)){
			timeMultiplier*=10.0;
		}
	}
	public void decreaseTime(){
		if(timeMultiplier>0.000000000001)
			timeMultiplier*=0.1;
	}
	public void writeToFile(String message, File file,ArrayList<String> strings){
		BufferedWriter writer = null;
		try {

			writer = new BufferedWriter(new FileWriter(file));
			for(int c=0; c<strings.size();c++){
				writer.write(strings.get(c));
				writer.newLine();
			}
			writer.write(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}
	}
	public void clearLineFromFile(File file, ArrayList<String> strings){
		BufferedWriter writer = null;
		try {

			writer = new BufferedWriter(new FileWriter(file));
			for(int c=0; c<strings.size();c++){
				writer.write(strings.get(c));
				writer.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}
	}
	public String getPathFromUser(String message){
		scan.nextLine();
		boolean exit = false;
		String ans = "";
		while(exit==false){
			System.out.println(message);
			ans = scan.nextLine();
			System.out.println(ans);
			File test = new File(ans);
			if(test.isDirectory())break;
			System.out.println("Error, not a directory, please try again:");
		}
		return ans;
	}
	public double differenceInRotation(double first, double second){
		double rot1 = fixRotation(first);
		double rot2 = fixRotation(second);
		double rotationNeeded = rot1 - rot2;
		if(Math.abs(rot1-rot2+Math.PI*2.0)<Math.abs(rotationNeeded)){
			rotationNeeded=rot1-(rot2+Math.PI*2.0);
		}
		if(Math.abs(rot1-rot2-Math.PI*2.0)<Math.abs(rotationNeeded)){
			rotationNeeded=rot1-(rot2-Math.PI*2.0);
		}
		return rotationNeeded;
	}
	public double toRadians(double degrees){
		return degrees  * (Math.PI / 180.0);
	}
	public double toDegrees(double radians){
		return radians * (180.0 / Math.PI);
	}
	public double upTo0(double num){
		if(num<0){
			num = 0;
		}
		return num;
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
	public void resetImage(){
		i = null;
		i = f.createImage(f.getSize().width, f.getSize().height);
	}
	public void drawCircle(double x, double y, double radius, Color c){
		Graphics g = i.getGraphics();
		g.setColor(c);
		g.drawOval((int)x - (int)radius, (int)y - (int)radius, (int)radius *2, (int)radius *2);
	}
	public void fillCircle(double x, double y, double radius, Color c){
		Graphics g = i.getGraphics();
		g.setColor(c);
		g.fillOval((int)x - (int)radius, (int)y - (int)radius, (int)radius *2, (int)radius *2);
	}
	public void scaleDraw(Image image, int x, int y , double scale){
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		Graphics g = i.getGraphics();
		g.drawImage(op.filter((BufferedImage)image, null), x, y , null);
	}
	public Image getScaledImage(Image image, double scale){
		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		return op.filter((BufferedImage)image, null);
	}
	public void horizontalFlipDraw(Image image, int x, int y, boolean right){
		AffineTransform tx = new AffineTransform();
		if(!right){
			tx.scale(-1, 1);
			tx.translate(-image.getWidth(null), 0);
		}
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		Graphics g = i.getGraphics();
		g.drawImage(op.filter((BufferedImage)image, null), x, y , null);
	}
	public void drawToImage( Image image, int x, int y){
		Graphics g = i.getGraphics();
		g.drawImage(image, x, y, null);
	}
	public void drawBlock(int x, int y, int width, int height, Color c){
		Graphics g = i.getGraphics();
		g.setColor(c);
		g.fillRect(x,y,width,height);
	}
	public void drawLine(Point main, Point trail, Color c){
		Graphics g = i.getGraphics();
		g.setColor(c);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));
		g2d.drawLine((int)main.getX(), (int)main.getY(), (int)trail.getX(), (int)trail.getY());
	}
	public void drawToImageRot(double rot, double rotPosX, double rotPosY, Image image, int x , int y, boolean right){
		Image test = image;
		BufferedImage test2 = (BufferedImage)test;
		double X = rotPosX;
		double Y = rotPosY;		
		AffineTransform tx = AffineTransform.getRotateInstance(rot,X,Y);
		if(!right){
			tx.scale(1, -1);
			tx.translate(0, -image.getHeight(null));
		}
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		Graphics g = i.getGraphics();
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(op.filter(test2,  null), x, y, null);
	}
	public void drawPolygon(Polygon poly){
		Graphics g = i.getGraphics();
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color (200,0,0));
		g2d.drawPolygon(poly);
	}
	public void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// do nothing
		}
	}
	public boolean isInside(Point block, int width, int height, Point pos){
		return ( (block.getX() <= pos.getX() && (block.getX()+width) >= pos.getX()) && (block.getY() <= pos.getY() && (block.getY()+height) >= pos.getY()) );
	}
	public void changeMX(double x){
		MX = x;
	}
	public void changeMY(double y){
		MY = y;
	}
	public void mousePressed(){
		mousePressed = true;
	}
	public void mouseUnpressed(){
		mousePressed = false;
	}
	public void rightPressed(){
		rightClick = true;
	}
	public void rightUnpressed(){
		rightClick = false;
	}
	public void playSound(File file){
		try {
			File yourFile = file;
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;
			Clip clip;

			stream = AudioSystem.getAudioInputStream(yourFile);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
			clip.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void printKeyMap(){
		for(int c = 0; c<keyMap.length;c++){
			if(keyMap[c]==true){
				System.out.println(c);
			}
		}
	}
}
class MousePosCheck implements MouseMotionListener{
	Game m;
	public MousePosCheck (Game M ){
		m = M;
	}
	@Override
	public void mouseDragged(MouseEvent event) {
		// TODO Auto-generated method stub
		m.changeMX(event.getX());
		m.changeMY(event.getY());
	}
	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		m.changeMX(event.getX());
		m.changeMY(event.getY());
	}
}
class MouseClickCheck implements MouseListener{
	Game m;
	public MouseClickCheck (Game M){
		m=M;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton()==1)
			m.mousePressed();
		if(arg0.getButton()==3)
			m.rightPressed();

	}		
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton()==1)
			m.mouseUnpressed();
		if(arg0.getButton()==3)
			m.rightUnpressed();
	}
}
class Keychecker extends KeyAdapter { // all
	Game m;
	public Keychecker (Game o){
		m=o;
	}
	@Override
	public void keyPressed(KeyEvent event) {
		m.keyMap[event.getKeyCode()] = true;
		System.out.println("" + KeyEvent.getKeyText(event.getKeyCode()) + " " + event.getKeyCode());
		//right arrow = 39
		//left arrow = 37
		//space is = 32
		//period = 46
		//comma = 44
	}
	@Override
	public void keyReleased(KeyEvent event){
		m.keyMap[event.getKeyCode()] = false;
	}
}
