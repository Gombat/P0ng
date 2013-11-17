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
	static int gamemode = 0; // If gamemode == 0 -> Singelplayer; gamemode == 1 -> Multiplayer
	static float yPosOld = 0;
	static int cpuMaxSpeed = 5;
	
	Circle ball;
	Rectangle paddlePlayer;
	Rectangle paddlePlayer2;
	Rectangle paddleCPU;
	
	Vector2f ballVelocity;
	int scorePlayer;
	int scorePlayer2;
	int scoreCPU;
	float speedPlayer = 0.0f, speedPlayer2 = 0.0f;

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
		   if (paddlePlayer.getMinY() > 0) {
			   speedPlayer -= 1;
			   paddlePlayer.setY(paddlePlayer.getY() - 10.0f);
		   }
		   
		} else if (gc.getInput().isKeyDown(Input.KEY_S)) {
		   if (paddlePlayer.getMaxY() < height) {
			   speedPlayer += 1;
			   paddlePlayer.setY(paddlePlayer.getY() + 10.0f);
		   }
			   
		} else speedPlayer = 0;
		
		if (gamemode == 1) {
		
			if (gc.getInput().isKeyDown(Input.KEY_UP)) {
			   if (paddlePlayer2.getMinY() > 0) {
				   speedPlayer2 -= 1;
				   paddlePlayer2.setY(paddlePlayer2.getY() - 10.0f);
			   }
			} else if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
			   if (paddlePlayer2.getMaxY() < height) {
				   speedPlayer2 += 1;
				   paddlePlayer2.setY(paddlePlayer2.getY() + 10.0f);
			   }
			} else speedPlayer2 = 0;
			
		}
	
		ball.setLocation(ball.getX() + ballVelocity.getX(), ball.getY() + ballVelocity.getY());
		
		if (ball.getMinX() <= 0) {
		   ballVelocity.x = 3;
		   ball.setLocation(width / 2, height / 2);
		   ballVelocity.y /= Math.abs(ballVelocity.getY());
		   
		   if (gamemode == 1) {
			   scorePlayer2++;
		   } else { scoreCPU++; }
			   
		}
		if (ball.getMaxX() >= width) {
		   ballVelocity.x = -3;
		   ball.setLocation(width / 2, height / 2);
		   ballVelocity.y /= Math.abs(ballVelocity.getY());
		   scorePlayer++;
		}
		
		if (ball.getMinY() <= 0 || ball.getMaxY() >= height)
		   ballVelocity.y = -ballVelocity.getY();
		
		if (ball.intersects(paddlePlayer)) {
		   ballVelocity.x = - (ballVelocity.getX() * 1.1f);
		   ballVelocity.y += speedPlayer;
		}
		
		if (gamemode == 1) {
			if (ball.intersects(paddlePlayer2)) {
				ballVelocity.x = - (ballVelocity.getX() * 1.1f);
				ballVelocity.y += speedPlayer2;
			}
		}
		else {
			if (ball.intersects(paddleCPU)) {
				ballVelocity.x = - (ballVelocity.getX() * 1.1f);
			}
			float yPos = ball.getCenterY() - paddleCPU.getHeight() / 2;
			float deltaY = yPos - yPosOld;
			if(deltaY > cpuMaxSpeed) yPos = yPosOld + cpuMaxSpeed;
			else if(deltaY < -cpuMaxSpeed) yPos = yPosOld - cpuMaxSpeed;
			paddleCPU.setY(yPos); 
			yPosOld = yPos; }
	}
	
	public void render(GameContainer gc, Graphics g) 
	{ 
		g.drawString("Player 1: " + scorePlayer, 10, 25);
		
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

