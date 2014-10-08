import java.awt.*;
import javax.swing.JComponent;
import java.util.*;

public class DrawPong extends JComponent
{
    //make pi
    private final double PI = Math.PI;
    
    //The width and height of the frame this is in
    private int frameHeight;
    private int frameWidth;

    //y-coordinates of and directions for players 1 and 2
    private double p1y;
    private double p2y;
    private int dir1 = 0;//0 is stationary, 1 is up, -1 is down
    private int dir2 = 0;//0 is stationary, 1 is up, -1 is down

    //x-coordinates for each player
    //final because the paddles can't move side-to-side
    private final int p1x;
    private final int p2x;

    //number of points for each player, and the countdown to game start
    private int p1Points = 0;
    private int p2Points = 0;
    private int counter;

    //allows movement of players and the ball, respectively
    private boolean playersCanMove = true;
    private boolean ballCanMove = true;

    //speed of the ball and players, respectively
    private double ballSpeed = 3;
    private final double BALL_SPEED = ballSpeed;
    private double ballSpeedX = ballSpeed * Math.cos(PI / 4);
    private double ballSpeedY = ballSpeed * Math.sin(PI / 4);
    private double ballSpeedIncrement = .15;
    private double playerSpeed = 2.5;

    //position, direction, and size of the ball
    private double ballX;
    private double ballY;
    //private int dirBallY = 1;//1 is up, -1 is down
    //private int dirBallX = 1;//1 is right, -1 is left
    private int size;

    //width and height of the pong paddles
    private int height = 100;
    private int width = 20;
    
    Timer cd = new Timer();
    TimeTask countdown = new TimeTask();

    public DrawPong(int frameWidth, int frameHeight) {
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;

        p1x = 0;
        p2x = frameWidth - width;

        size = 20;
        
        resetPositions();
    }


    public void p1Up() {
        dir1 = 1;//direction is set to up
    }

    public void p1Stop() {
        dir1 = 0;//makes p1's paddle motionless
    }

    public void p1Down() {
        dir1 = -1;//direction is set to down
    }

    public void p2Up() {
        dir2 = 1;//direction is set to up
        repaint();
    }

    public void p2Stop() {
        dir2 = 0;//makes p1's paddle motionless
    }

    public void p2Down() {
        dir2 = -1;//direction is set to down
    }

    private void resetPositions() { //centers the paddles and the ball
        p1y = (frameHeight - height) / 2;
        p2y = p1y;

        //reset the ball to original position and speed
        ballX = (frameWidth - size) / 2;
        ballY = (frameHeight - size) / 2;
        ballSpeed = BALL_SPEED;
        
        ballSpeedY = ballSpeed * Math.sin(PI / 4);

        ballCanMove = false;

        counter = 4;
        Timer cd = new Timer();
        cd.scheduleAtFixedRate(countdown
        , 0
        , 1000 );

    }
    class TimeTask extends TimerTask {
        public void run() {
            counter--;
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //if the countdown is done, cancel the timer and 
        if (counter <= 0) {
            ballCanMove = true;
            countdown.cancel();
            countdown = new TimeTask();
        }
        
        //create a black backgorund
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, frameWidth, frameHeight);

        //make paddles and the ball move
        move();

        g2.setColor(Color.WHITE);
        //if the ball can't move (i.e. game just started or a player just scored) display score and countdown until start
        if (!ballCanMove) {
            //show points for each player
            g2.drawString(Integer.toString(p1Points), width + 50, 50);
            g2.drawString(Integer.toString(p2Points), frameWidth - (width + 50), 50);

            //show countdown to ball moving
            g2.drawString(Integer.toString(counter), frameWidth / 2 - 20, frameHeight / 2 - 50);
        }

        //draw the components in white

        g2.fillRect(p1x, (int) p1y, width, height);//first player's paddle
        g2.fillRect(p2x, (int) p2y, width, height);//second player's paddle
        g2.fillOval((int) ballX, (int) ballY, size, size);//the ball
    }

    private void p1Score() {
        p1Points++;
        ballSpeedX = BALL_SPEED * Math.cos(PI / 4);
        resetPositions();
    }

    private void p2Score() {
        p2Points++;
        ballSpeedX = BALL_SPEED * Math.cos(3 * PI / 4);
        resetPositions();
    }

    private void collisionCheck() {
        if ( (ballY <= 0)  ||  (ballY >= frameHeight - size - 22) ) 
            ballSpeedY *= -1;//reverses y direction when the ball hits the sides

        if (ballX < width) {
            if ((new Rectangle(p1x, (int) p1y, width, height).contains(new Point((int)ballX, (int) ballY + (size / 2) ))) )  
                ballBounce(1);
            else p2Score();
        }

        if (ballX > frameWidth - size - width) {
            if (new Rectangle(p2x, (int) p2y, width, height).contains(new Point((int) ballX + size, (int)ballY + (size / 2) )) )  
                ballBounce(2);
            else p1Score();
        }
    }
    
    private void ballBounce(int player) {
        if (player == 1) {
            double p1CenterY = p1y + height / 2;
            double ballCenterY = ballY + size / 2;
            
            double xDiff = width / 2 + size / 2;
            double yDiff = p1CenterY - ballCenterY;
            
            double angle = Math.atan2(yDiff, xDiff);
            
            ballSpeedX = ballSpeed * Math.cos(angle);
            ballSpeedY = ballSpeed * Math.sin(angle) * -1;
            
            ballX = width;
            
            ballSpeed += ballSpeedIncrement;
        }
        else {
            double p2CenterY = p2y + height / 2;
            double ballCenterY = ballY + size / 2;
            
            double xDiff = -width / 2 - size / 2;
            double yDiff = p2CenterY - ballCenterY;
            
            double angle = Math.atan2(yDiff, xDiff);
            
            ballSpeedX = ballSpeed * Math.cos(angle);
            ballSpeedY = ballSpeed * Math.sin(angle) * -1;
            
            ballX = frameWidth - (size + width);
            
            ballSpeed += ballSpeedIncrement;
        }
    }

    private void move() {
        collisionCheck();

        if (playersCanMove) {
            switch(dir1) {//up subtracts and down adds because y increases as the point moves down
                case 1: if (p1y > 0) p1y-=playerSpeed;
                break;
                case -1: if (p1y < (frameHeight - height - 22) )p1y+=playerSpeed;
                break;
                default: break;
            }

            switch(dir2) {
                case 1: if (p2y > 0)p2y-=playerSpeed;
                break;
                case -1: if (p2y < (frameHeight - height - 22) )p2y+=playerSpeed;
                break;
                default: break;
            }
        }

        if (ballCanMove) {
            ballY += ballSpeedY;
            ballX += ballSpeedX;
        }
    }
}

