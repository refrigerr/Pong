package pongv2;

import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle {
    private int xVelocity;
    private int yVelocity;
    private int maxYSpeed;
    int timesBounced;
    private Random random;
    public Ball(int x, int y, int width, int height){
        super(x,y,width,height);
        random = new Random();
        xVelocity = 3;
        yVelocity = 2;
        randomizeDirection();
        maxYSpeed=2;
        timesBounced=0;
    }
    public void randomizeDirection(){
        int direction = random.nextInt(4);
        if(direction==0) xVelocity=-xVelocity;
        else if(direction==1) yVelocity=-yVelocity;
        else if(direction==2){
            xVelocity=-xVelocity;
            yVelocity=-yVelocity;
        }

    }
    public void moveBall(){
        if(timesBounced<5) this.x+=xVelocity;
        else if(timesBounced<10) {
            this.x+=2*xVelocity;
            setMaxYSpeed(3);
        }
        else if(timesBounced<15) {
            this.x+=3*xVelocity;
            setMaxYSpeed(4);
        }
        else {
            this.x+=4*xVelocity;
            setMaxYSpeed(5);
        }
        this.y+=yVelocity;
    }
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        if(timesBounced>=10) g2d.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        g2d.fillOval(x, y,width,height);
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
    public int getMaxYSpeed() {
        return maxYSpeed;
    }

    public void setMaxYSpeed(int maxSpeed) {
        this.maxYSpeed = maxSpeed;
    }
}
