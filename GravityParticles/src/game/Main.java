package game;

import java.io.IOException;

public class Main {
	public static void main (String []args) throws IOException{
		String classPath  = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		Game g = new Game(classPath);
		try{
			g.playGame();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.exit(0);
	}
}
