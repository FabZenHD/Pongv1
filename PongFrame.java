import java.awt.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.util.*;

public class PongFrame extends JFrame
        implements KeyListener
{
    DrawPong draw;
    int height = 400;
    int width = 500;
    PongFrame frame;
    
    public void PongFrame() {

    }
    
    public void run() {
        frame = this;

        
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocation(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Pong");
        
        
        
        
        
        frame.addKeyListener(this);
        
        draw = new DrawPong(width, height);
        frame.add(draw, BorderLayout.CENTER);
        
        
        frame.setVisible(true);     
        
        Timer paint = new Timer();
        paint.scheduleAtFixedRate(new TimeTask(), 0, 1000 / 60);
    }
    class TimeTask extends TimerTask {
        public void run() {
            draw.repaint();
        }
    }
    
    
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: draw.p2Up();
                    break;
            case KeyEvent.VK_DOWN: draw.p2Down();
                    break;
            case KeyEvent.VK_W: draw.p1Up();
                    break;
            case KeyEvent.VK_S: draw.p1Down();
                    break;
            default: break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: 
            case KeyEvent.VK_DOWN: draw.p2Stop();
                    break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_S: draw.p1Stop();
                    break;
            default: break;
        }
    }
    
    public void keyTyped(KeyEvent e) {}
}
