import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH=600 ;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25; // Size of each unit in the game grid
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE); // Total number of units in the game
    static final int DELAY=75; // Delay for the game loop, controls the speed of the game
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts=6; // Initial length of the snake
    int applesEaten=0; // Counter for the number of apples eaten
    int appleX; // X coordinate of the apple
    int appleY; // Y coordinate of the apple
    char direction='R'; // Initial direction of the snake (Right)
    boolean running=false; // Flag to check if the game is running
    Timer timer; // Timer to control the game loop
    Random random; // Random number generator for apple placement
    //boolean isBlueApple;

    

    public GamePanel() {
        

        random = new Random(); // Initialize the random number generator
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // Set the size of the game panel
        this.setBackground(Color.black); // Set the background color of the panel
        this.setFocusable(true); // Make the panel focusable to receive key events
        this.addKeyListener(new MykeyAdater()); // Add a key listener to handle key events
        StartGame(); // Start the game
    
    }
    public void StartGame(){
        running = true; // Set the game state to running
        newApple(); // Generate the first apple
        timer = new Timer(DELAY, this); // Create a timer to control the game loop
        timer.start(); // Start the timer
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g); // Call the superclass method to clear the panel
        // Debug
        draw(g); // Call the draw method to render the game components
        if (!running) { // If the game is not running, display the game over message
            gameOver(g);
        }
    }
    // This method is called to draw the game components
    public void draw( Graphics g) {
         g.setColor(Color.gray);
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); // Draw vertical grid lines
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); // Draw horizontal grid lines
        }
       //if (isBlueApple) {
       //      g.setColor(Color.BLUE);
      // } else {
            g.setColor(Color.RED);
      // }
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
     if (running) {   
        for (int i = 0; i < bodyParts; i++) { // Draw the snake's body
            if (i == 0) { // Draw the head of the snake
                g.setColor(Color.GREEN);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else { // Draw the body parts of the snake
                g.setColor(new Color(45, 180, 0)); // Set a different color for the body
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
        g.setColor(Color.RED); // Set the color for the game over message
        g.setFont(new Font("Ink Free", Font.BOLD, 40)); // Set the font for the game over message
        FontMetrics metrics = getFontMetrics(g.getFont()); // Get the font metrics to center the text
        g.drawString("Score:" + applesEaten , (SCREEN_WIDTH - metrics.stringWidth("Score:" + applesEaten)) / 2,g.getFont().getSize()); // Draw the score at the top center of the panel
 
    } else {
        gameOver(g); // If the game is not running, display the game over message
    }
        
    }// This method draws the snake, apple, and score on the game panel

    public void move(){
        for (int i = bodyParts; i > 0; i--) { // Move the snake's body parts
            x[i] = x[i - 1]; // Shift each body part to the position of the previous one
            y[i] = y[i - 1];
        }
        switch (direction) { // Change the head position based on the current direction
            case 'U':
                y[0] -= UNIT_SIZE; // Move up
                break;
            case 'D':
                y[0] += UNIT_SIZE; // Move down
                break;
            case 'L':
                x[0] -= UNIT_SIZE; // Move left
                break;
            case 'R':
                x[0] += UNIT_SIZE; // Move right
                break;
        }
    } // This method is called to update the game state, such as moving the snake  

    public void checkApple(){
        if (x[0] == appleX && y[0] == appleY) { // Check if the snake's head is at the same position as the apple
            bodyParts++; // Increase the length of the snake
            applesEaten++; // Increment the score
            newApple(); // Generate a new apple
        }
        /*if (isBlueApple) {
            applesEaten -= 1; // If it's a green apple, decrease the score by1
        }*/
       

    } // This method checks if the snake has eaten an apple and updates the score
   
    public void newApple(){
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE; // Generate a random X coordinate for the apple
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE; // Generate a random Y coordinate for the apple
      
         //isBlueApple = random.nextBoolean();
    } // This method generates a new apple at a random position on the game grid
   
    
    public void checkCollision(){
        // Check if the snake collides with itself
        for (int i = bodyParts; i > 0; i--) { 
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false; // Set the game state to not running
            }
        }
        // Check if the snake collides with the walls
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH ||
            y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false; // Set the game state to not running
        }
        if (!running) {
            timer.stop(); // Stop the timer if the game is not running
        }

    }// This method checks for collisions between the snake and the walls or itself

    public void gameOver(Graphics g) {
        g.setColor(Color.RED); // Set the color for the game over message
        g.setFont(new Font("Ink Free", Font.BOLD, 75)); // Set the font for the game over message
        FontMetrics metrics = getFontMetrics(g.getFont()); // Get the font metrics to center the text
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2 - 50); // Draw the game over message at the center of the panel
        //the score
        g.setColor(Color.WHITE); 
        g.setFont(new Font("Ink Free", Font.BOLD, 50)); // Set the font for the final score
        metrics = getFontMetrics(g.getFont()); // Get the font metrics for the final score
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, SCREEN_HEIGHT / 2 + 50); // Draw the final score below the game over message
       //restart message
        g.setFont(new Font("Ink Free", Font.BOLD, 30)); // Set the font for the restart message
        metrics = getFontMetrics(g.getFont()); // Get the font metrics for the restart message
        g.drawString("Press Space to Restart", (SCREEN_WIDTH - metrics.stringWidth("Press Space to Restart")) / 2, SCREEN_HEIGHT / 2 + 100); // Draw the restart message below the final score
    

    }// This method is called when the game is over, displaying the game over message and final score
    public void resetGame() {
        bodyParts = 3; // Reset the length of the snake
        applesEaten = 3; // Reset the score
        direction = 'R'; // Reset the direction to right
        running = true; // Set the game state to running
        for (int i = 0; i < GAME_UNITS; i++) { // Clear the snake's body
            x[i] = 0;
            y[i] = 0;
        }
        newApple(); // Generate a new apple
        timer.start(); // Restart the timer
    } // This method resets the game state to start a new game
    public class MykeyAdater extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) { // Handle key events to change the snake's direction
                case KeyEvent.VK_UP:
                    if (direction != 'D') direction = 'U'; // Prevent reversing direction
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') direction = 'D'; // Prevent reversing direction
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') direction = 'L'; // Prevent reversing direction
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') direction = 'R'; // Prevent reversing direction
                    break;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Check if the space key is pressed
                    resetGame(); // Reset the game
                }

    
        }

    }// Handle key events here, such as changing the direction of the snake
     @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move(); // Move the snake
            checkApple(); // Check if the snake has eaten an apple
            checkCollision(); // Check for collisions
        }
        repaint(); // Repaint the game panel to update the graphics
        // Handle action events here
    }
 
}
    
