package pongv2;

import java.awt.*;
import java.util.Random;

public class PowerUp extends Rectangle {
    private Random random;
    private int id;

    public PowerUp(int x, int y, int width, int height){
        super(x,y,width,height);
        random = new Random();
        this.id=random.nextInt(11);
    }
    public void doPowerUp(Paddle paddle,Ball ball){
        if(id<5&&paddle.height<300) paddle.height+=30;
        else if(id<10) paddle.setMaxSpeed(paddle.getMaxSpeed()+2);
        else{
            ball.width=ball.icon.getIconWidth();
            ball.height=ball.icon.getIconHeight();
            ball.poweredUp=true;
        }

    }
    public void draw(Graphics g) {
        if(id<5) g.setColor(Color.GREEN);
        else if(id<10)g.setColor(Color.YELLOW);
        else g.setColor(Color.RED);
        g.fillRect(x, y,width,height);
    }

}
