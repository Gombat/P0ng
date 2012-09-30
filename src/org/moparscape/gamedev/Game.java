package org.moparscape.gamedev;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Game extends BasicGame
{
	static int width = 640;
	static int height = 480;
	static boolean fullscreen = false;
	static String title = "P0ng";
	static int fpsLimit = 60;
	static int scoretxtright = width - 120;
	static int txth1posx = width / 2 - 75;
	static int gamemode = 1; // If gamemode == 0 -> Singelplayer; gamemode == 1 -> Multiplayer
	
	Circle ball;
	Rectangle paddlePlayer;
	Rectangle paddlePlayer2;
	Rectangle paddleCPU;
	
	Vector2f ballVelocity;
	int scorePlayer;
	int scorePlayer2;
	int scoreCPU;

	public Game(String title) {
	   super(title);
	}
	public void init(GameContainer gc) 
	{  
		gc.getInput().enableKeyRepeat();
		paddlePlayer = new RoundedRectangle(5, height / 2, 10, 80, 3);
		
		if (gamemode == 1) {
			paddlePlayer2 = new RoundedRectangle(width - 15, height / 2, 10, 80, 3);
		} else { paddleCPU = new RoundedRectangle(width - 15, height / 2, 10, 80, 3); }
			
		ball = new Circle(width / 2, height / 2, 6);
		ballVelocity = new Vector2f(-3, 1);
	}
	
	public void update(GameContainer gc, int delta) 
	{
		if (gc.getInput().isKeyDown(Input.KEY_W)) {
		   if (paddlePlayer.getMinY() > 0)
		      paddlePlayer.setY(paddlePlayer.getY() - 10.0f);
		} else if (gc.getInput().isKeyDown(Input.KEY_S)) {
		   if (paddlePlayer.getMaxY() < height)
		      paddlePlayer.setY(paddlePlayer.getY() + 10.0f);
		}
		
		if (gamemode == 1) {
		
			if (gc.getInput().isKeyDown(Input.KEY_UP)) {
			   if (paddlePlayer2.getMinY() > 0)
			      paddlePlayer2.setY(paddlePlayer2.getY() - 10.0f);
			} else if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
			   if (paddlePlayer2.getMaxY() < height)
			      paddlePlayer2.setY(paddlePlayer2.getY() + 10.0f);
			}
			
		}
	
		ball.setLocation(ball.getX() + ballVelocity.getX(), ball.getY() + ballVelocity.getY());
		
		if (ball.getMinX() <= 0) {
		   ballVelocity.x = -ballVelocity.getX();
		   
		   if (gamemode == 1) {
			   scorePlayer2++;
		   } else { scoreCPU++; }
			   
		}
		if (ball.getMaxX() >= width) {
		   ballVelocity.x = -ballVelocity.getX();
		   scorePlayer++;
		}
		
		if (ball.getMinY() <= 0)
		   ballVelocity.y = -ballVelocity.getY();
		if (ball.getMaxY() >= height)
		   ballVelocity.y = -ballVelocity.getY();
		
		if (ball.intersects(paddlePlayer) || ball.intersects(paddlePlayer2)) {
		   ballVelocity.x = -ballVelocity.getX();
		}
		
		if (gamemode == 0) {
			float ypos = ball.getCenterY() - paddleCPU.getHeight() / 2;
			paddleCPU.setY(ypos); }
	}
	
	public void render(GameContainer gc, Graphics g) 
	{ 
		g.drawString("Player 1: "+scorePlayer, 10, 25);
		
		if (gamemode == 1) {
			g.drawString("Player 2: "+scorePlayer2, scoretxtright, 25);
		} else { g.drawString("CPU: "+scoreCPU, scoretxtright, 25); }
		
		if (gamemode == 1) {
			g.drawString("2 Player Game", txth1posx, 5);
		} else { g.drawString("1 Player Game", txth1posx, 5); }
		
		g.fill(paddlePlayer);
		
		if (gamemode == 1) {
			g.fill(paddlePlayer2);
		} else { g.fill(paddleCPU); }
		
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

