import java.awt.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.util.*;

public class PongFrameAI extends JFrame
        implements KeyListener
{
    DrawPongAI draw;
    int height = 400;
    int width = 500;
    PongFrameAI frame;
    
    public void PongFrameAI() {

    }
    
    public void run() {
        frame = this;

        
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocation(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pong");
        
        
        
        
        
        frame.addKeyListener(this);
        
        draw = new DrawPongAI(width, height);
        frame.add(draw, BorderLayout.CENTER);
        
        
        frame.setVisible(true);     
        
        Timer paint = new Timer();
        paint.scheduleAtFixedRate(new TimeTask(), 0, 1000 / 60);
    }
    class TimeTask extends TimerTask {
        public void run() {
            draw.AIMotion();
            draw.repaint();
        }
    }
    
    
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: draw.playerUp();
                    break;
            case KeyEvent.VK_DOWN: draw.playerDown();
                    break;
            default: break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: 
            case KeyEvent.VK_DOWN: draw.playerStop();
                    break;
            default: break;
        }
    }
    
    public void keyTyped(KeyEvent e) {}
}
