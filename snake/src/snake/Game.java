package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener{
	private boolean play = true;
	private Timer timer;
	private int delay = 10;
	private int score = 0;
	private boolean snakeLeftDir = false;
	private boolean snakeRightDir = true;
	private boolean snakeUpDir = false;
	private boolean snakeDownDir = false;
	private int screenHeight = 700;
	private int screenWidth = 700;
	private int unitSize = 35;
	private int snakeUnits = 15;
	private int totalUnits = (screenHeight*screenWidth)/unitSize;
	private int appleX = 18*(screenWidth/unitSize);
	private int appleY = 16*(screenHeight/unitSize);
	private int[] x = new int[totalUnits];
	private int[] y = new int[totalUnits];
	public Game() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {		
		// background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, screenWidth - 10, screenHeight - 10);
		
		// score
		g.setColor(Color.ORANGE);
		g.setFont(new Font("serif", Font.BOLD, 30));
		g.drawString(""+score, screenWidth - 100, 40);
		
		// apple
		g.setColor(Color.RED);
		g.fillRect(appleX, appleY, unitSize, unitSize);
		
		// snake
		for (int i = 0; i < snakeUnits - 1; i++) {
			g.setColor(Color.GREEN);
			g.fillRect(x[i], y[i], unitSize, unitSize);
		}
				
		if ((x[0] < 0) || (x[0] >= screenWidth - unitSize) || (y[0] < 0) || (y[0] >= screenHeight - 2*unitSize)) {
			play = false;
			g.setColor(Color.ORANGE);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("GAME OVER. YOUR SCORE IS "+score, 125, 350);
			g.drawString("PRESS SPACE TO RESTART", 160, 400);
		}		
		
		for (int i = snakeUnits; i > 0; i--) {
			if (x[i] == x[0] && y[i] == y[0]) {
				play = false;
				g.setColor(Color.ORANGE);
				g.setFont(new Font("serif", Font.BOLD, 30));
				g.drawString("GAME OVER. YOUR SCORE IS "+score, 125, 350);
				g.drawString("PRESS SPACE TO RESTART", 160, 400);
				break;
			}
		}
		
		g.dispose();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// if left key clicked
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (x[0] >= 0 && play && !snakeLeftDir && !snakeRightDir) {
				snakeLeftDir = true;
				snakeRightDir = false;
				snakeUpDir = false;
				snakeDownDir = false;
			} 
		}
		
		// if right key clicked
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (x[0] < screenWidth - unitSize && play && !snakeLeftDir && !snakeRightDir) {
				snakeLeftDir = false;
				snakeRightDir = true;
				snakeUpDir = false;
				snakeDownDir = false; 
			} 
		}
		
		// if up key clicked
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (y[0] >= 0 && play && !snakeUpDir && !snakeDownDir) {
				snakeLeftDir = false;
				snakeRightDir = false;
				snakeUpDir = true;
				snakeDownDir = false; 
			}
		}
		
		// if down key clicked
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (y[0] <= screenHeight - 2*unitSize && play && !snakeUpDir && !snakeDownDir) {
				snakeLeftDir = false;
				snakeRightDir = false;
				snakeUpDir = false;
				snakeDownDir = true;
			}
		}
		
		// if space clicked (after play is false)
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (!play) {
				play = true;
				score = 0;
				snakeLeftDir = false;
				snakeRightDir = true;
				snakeUpDir = false;
				snakeDownDir = false;
				screenHeight = 700;
				screenWidth = 700;
				unitSize = 35;
				snakeUnits = 15;
				totalUnits = (screenHeight*screenWidth)/unitSize;
				appleX = 18*(screenWidth/unitSize);
				appleY = 16*(screenHeight/unitSize);
				x = new int[totalUnits];
				y = new int[totalUnits];
				repaint();
			}
		}
		snakeMoving();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (play) {
			Random rand = new Random();
			// if snake ate apple
			if (new Rectangle(x[0], y[0], unitSize, unitSize).intersects(new Rectangle(appleX, appleY, unitSize, unitSize))) {
				appleX = rand.nextInt(screenWidth-unitSize);
				appleY = rand.nextInt(screenHeight-3*unitSize);
				snakeUnits += 3;
				score += 1;
			}
			snakeMoving();
		}
		repaint();
	}
	
	public void snakeMoving() {
		for (int i = snakeUnits; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		if (snakeLeftDir && play) {
			x[0] = x[0] - 5;
		} else if (snakeRightDir && play) {
			x[0] = x[0] + 5;
		} else if (snakeUpDir && play) {
			y[0] = y[0] - 5;
		} else if (snakeDownDir && play) {
			y[0] = y[0] + 5;
		}
	}
}
