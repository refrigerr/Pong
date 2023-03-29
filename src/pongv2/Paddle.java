package pongv2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle {
    private final int id;
    private int maxSpeed;
    private int currentSpeed;
    private Color color;

    public Paddle(int x, int y, int width, int height, int id){
        super(x,y,width,height);
        this.id=id;
        maxSpeed =4;
    }

    public void move(){
        y+=currentSpeed;
    }
    public void draw(Graphics g){
        g.setColor(this.color);
        g.fillRect(x,y,width,height);
    }

    public void keyPressed(KeyEvent e) {
        switch(id) {
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W) {
                    currentSpeed=-maxSpeed;
                }
                if(e.getKeyCode()==KeyEvent.VK_S) {
                    currentSpeed=maxSpeed;
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP) {
                    currentSpeed=-maxSpeed;
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN) {
                    currentSpeed=maxSpeed;
                }
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch(id) {
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_W) {
                    currentSpeed=0;
                }
                if(e.getKeyCode()==KeyEvent.VK_S) {
                    currentSpeed=0;
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP) {
                    currentSpeed=0;
                }
                if(e.getKeyCode()==KeyEvent.VK_DOWN) {
                    currentSpeed=0;
                }
                break;
        }
    }
    public void setMaxSpeed(int speed){
        this.maxSpeed=speed;
    }
    public int getMaxSpeed(){return maxSpeed;}
    public void setColor(Color color) {
        this.color = color;
    }


}
