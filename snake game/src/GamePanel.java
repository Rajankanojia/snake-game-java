import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 1200;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 50; // size of snakes unit and food apple
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE); // total size for game units
	static final int DELAY = 150;// snake moment control 
	final int x[] = new int[GAME_UNITS]; // array is used for the coordinate at where the snake and other component on x axis
	final int y[] = new int[GAME_UNITS]; // array is used for the coordinate at where the snake and other component on y axis
	int bodyParts = 5; // initial boadypart will always startt with 6 body part
	int applesEaten;
	int appleX; // we made a obj where apple is on x axis
	int appleY; // we made a obj where apple is on y axis
	char direction = 'R'; // when game will start snake will begin with right diresction R for roght U for up D for down Left
	boolean running = true; // this will stop the game when ever we compile program n e wil get the fresh start
	Timer timer; // creating instance for timer
	Random random; // creating instance of the random
 	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.setFocusable(true); // it used to gather all attention to the snake components so as active
		this.addKeyListener(new MyKeyAdapter()); // this is the method my the the adapter class which will take input from our input keyboard key
		startGame(); // after completion of the above constructor now it will pass on too the below constructor
	}
	public void startGame() {
		newApple();// to create apple on screen
		running = true;
		timer = new Timer(DELAY,this); // this will used to get total time for snakes speed we made use od this cause its a action listner
		timer.start(); // it will take the timer to start
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		if(running)
		{
			
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) // this is used to get the grid inside our framrs by drawing lines on x axis and y axis
			 {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); //g.drawLine(x1,y1,x2,y2); this will give vertical lines
				g.drawLine(0, i*UNIT_SIZE, i*SCREEN_WIDTH, i*UNIT_SIZE); //g.drawLine(x1,y1,x2,y2);	this will give horizontals lines
			}
			
			g.setColor(Color.MAGENTA); // this we are making apple and giving the color
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		// creating body part for snake
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.YELLOW);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					//g.setColor(new Color(45,180,0)); // this is the color of the body 
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}			
			}
			g.setColor(Color.red);
			g.setFont( new Font("SansSerif",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}
		else
		{
			gameOver(g); // g is grphics 
		}
		
	}
	public void newApple()// this used to generate apple randomly inside our screen
	{
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move() 	{	
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1]; // we r decrementing snake for movement on coardinate.
			y[i] = y[i-1];
		}
		
		switch(direction) // this is for the movement of the snake with help of the key now we will draw head of snake and body
		{
		case 'U':
			y[0] = y[0] - UNIT_SIZE; // just imagine the garph
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	public void checkApple() // we will just examin the cordinate of x and y position of the apple and snake
	{
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++; //this will increment the snakes body by 1 when ever snake eats the apple
			applesEaten++; // this will store for the score
			newApple();
		}
	}
	public void checkCollisions() {
		//checks if head collides with body
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		//check if head touches left border
		if(x[0] < 0) {
			running = false;
		}
		//check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//check if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		//check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop(); // this will stop the timer  now we will examin keyelent
		}
	}
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.green);
		g.setFont( new Font("SansSerif",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.ORANGE);
		g.setFont( new Font("SansSerif",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont()); // to allign text in center
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2); // x cordinates-y cordinates(Game over)/2,
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// to show the body movement of the snake 
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint(); // when it is not running 
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break; // now we will examin and set the apple to grabbed by the snake
			}
		}
	}
}