package org.moparscape.gamedev;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Game extends BasicGame
{
	static int width = 640;
	static int height = 480;
	static boolean fullscreen = false;
	static String title = "Mongo";
	static int fpsLimit = 60;
	
	Circle ball;
	Rectangle paddlePlayer;
	Rectangle paddleCPU;
	
	Vector2f ballVelocity;
	int scorePlayer;
	int scoreCPU;

	public Game(String title) {
	   super(title);
	}
	public void init(GameContainer gc) 
	{  
		gc.getInput().enableKeyRepeat();
		paddlePlayer = new RoundedRectangle(5, height / 2, 10, 80, 3);
		paddleCPU = new RoundedRectangle(width - 15, height / 2, 10, 80, 3);
		ball = new Circle(width / 2, height / 2, 6);
		ballVelocity = new Vector2f(-3, 1);
	}
	
	public void update(GameContainer gc, int delta) 
	{
		if (gc.getInput().isKeyDown(Input.KEY_UP)) {
		   if (paddlePlayer.getMinY() > 0)
		      paddlePlayer.setY(paddlePlayer.getY() - 10.0f);
		} else if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
		   if (paddlePlayer.getMaxY() < height)
		      paddlePlayer.setY(paddlePlayer.getY() + 10.0f);
		}
		
		ball.setLocation(ball.getX() + ballVelocity.getX(), ball.getY() + ballVelocity.getY());
		
		if (ball.getMinX() <= 0) {
		   ballVelocity.x = -ballVelocity.getX();
		   scoreCPU++;
		}
		if (ball.getMaxX() >= width) {
		   ballVelocity.x = -ballVelocity.getX();
		   scorePlayer++;
		}
		
		if (ball.getMinY() <= 0)
		   ballVelocity.y = -ballVelocity.getY();
		if (ball.getMaxY() >= height)
		   ballVelocity.y = -ballVelocity.getY();
		
		if (ball.intersects(paddlePlayer) || ball.intersects(paddleCPU)) {
		   ballVelocity.x = -ballVelocity.getX();
		}
		
		float ypos = ball.getCenterY() - paddleCPU.getHeight() / 2;
		paddleCPU.setY(ypos);
	}
	
	public void render(GameContainer gc, Graphics g) 
	{ 
		g.fill(paddlePlayer);
		g.fill(paddleCPU);
		g.fill(ball);
	}
	
	public static void main(String[] args) throws SlickException 
	{ 
		AppGameContainer app = new AppGameContainer(new Game(title));
		app.setDisplayMode(width, height, fullscreen);
		app.setTargetFrameRate(fpsLimit);
		app.start();
	}
}

