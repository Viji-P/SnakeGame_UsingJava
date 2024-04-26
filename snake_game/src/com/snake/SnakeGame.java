package com.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener,KeyListener{
	
	static final int SCREEN_WIDTHX = 600;
	static final int SCREEN_HEIGHTY = 600;
	static final int SIZE = 30;
	static final int DELAY = 300;
	
	final int[] x = new int[SCREEN_WIDTHX*SCREEN_HEIGHTY];
	final int[] y = new int[SCREEN_WIDTHX*SCREEN_HEIGHTY];
	
	int bodyPart = 2;
	int foodEattern;
	int foodX;
	int foodY;
	int score = 0;
	int currentDirectionX = 1;
	int currentDirectionY = 0;
	boolean running = false;
	Timer timer;
	Random random;
	
	public SnakeGame() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTHX,SCREEN_HEIGHTY));
		this.setBackground(Color.green);
		this.setFocusable(true);
		this.addKeyListener(this);
		startGame();
	}

	public void startGame() {
		createFood();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g) {
		if(running) {
		for(int i=0;i<SCREEN_HEIGHTY;i++) {
			g.drawLine(i*SIZE, 0, i*SIZE, SCREEN_HEIGHTY);
			g.drawLine(0, i*SIZE, SCREEN_HEIGHTY, i*SIZE);

		}
		g.setColor(Color.black);
		g.fillOval(foodX, foodY, SIZE, SIZE);
		
		for(int i=0;i<bodyPart;i++) {
			g.setColor(Color.red);
			g.fillRect(x[i], y[i], SIZE, SIZE);
		}
		}else
			gameOver(g);
	}
	
	public void gameOver(Graphics g) {
		g.setColor(Color.gray);
		g.setFont(new Font("serif",Font.BOLD,75));
		//FontMetrics met = getFontMetrics(g.getFont());
		g.drawString("Score :"+score, SCREEN_WIDTHX/5, SCREEN_HEIGHTY/3);
		g.drawString("Game Over", SCREEN_WIDTHX/5, SCREEN_HEIGHTY/2);

		
	}
	
	public void move() {
		for(int i=bodyPart;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		x[0] = x[0] + currentDirectionX*SIZE;
		y[0] = y[0] + currentDirectionY*SIZE;

		
		
	}
	public void createFood() {
		foodX = random.nextInt(((int)SCREEN_WIDTHX/SIZE))*SIZE;
		foodY = random.nextInt(((int)SCREEN_HEIGHTY/SIZE))*SIZE;
     }
	public void checkFood() {
		if((x[0] == foodX) && (y[0] == foodY)) {
		
		score++;
		bodyPart++;
		createFood();
		
		}
	}
	public void checkCollied() {
		for(int i = bodyPart;i>0;i--) {
			if(x[0] == x[i] && y[0] == y[i])
				running = false;
			if((x[0] < 0) || (x[0] >= SCREEN_WIDTHX) || (y[0] < 0) || (y[0] >= SCREEN_HEIGHTY))
				running = false;
			
			if(!running) {
				timer.stop();
			}
			
		}
	}
	


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_RIGHT:
			if(currentDirectionX != -1) {
			currentDirectionX = 1;
			currentDirectionY = 0;
			}
			break;
			
		case KeyEvent.VK_LEFT:
			if(currentDirectionX != 1) {
			currentDirectionX = -1;
			currentDirectionY = 0;
			}
			break;
			
		case KeyEvent.VK_UP:
			if(currentDirectionY != 1) {
			currentDirectionX = 0;
			currentDirectionY = -1;
			}
			break;
			
		case KeyEvent.VK_DOWN:
			if(currentDirectionY != -1) {
			currentDirectionX = 0;
			currentDirectionY = 1;
			}
			break;
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkFood();
			checkCollied();
		}
		repaint();
		
	}

}
