package pongv2;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private MyFrame myFrame;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int DELAY = 10;
    private Paddle paddle1;
    private Paddle paddle2;
    private Ball ball;
    private PowerUp powerUp;
    private int p1Score=0;
    private int p2Score=0;
    private boolean running;
    private boolean paused=false;
    private boolean bouncedByP1=true;
    private boolean powerUpOnBoard=false;
    private int bouncedAfterPowerUp=0;
    private Random random;
    long timePassed=4000;
    Timer timer;

    public GamePanel(MyFrame myFrame, int SCREEN_WIDTH,int SCREEN_HEIGHT){
        this.myFrame=myFrame;
        this.SCREEN_HEIGHT=SCREEN_HEIGHT;
        this.SCREEN_WIDTH=SCREEN_WIDTH;
        this.setPreferredSize(new Dimension(this.SCREEN_WIDTH, this.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.addKeyListener(new MyActionListener());
        this.setLayout(null);
        this.setVisible(true);
        startGame();
    }
    public void startGame(){
        running=true;
        timer = new Timer(DELAY,this);
        random=new Random();
        newPaddles();
        newBall();
        newRound();
    }
    public void newPaddles(){
        paddle1 = new Paddle(0,SCREEN_HEIGHT/2-25,15,70,1);
        paddle2 = new Paddle(SCREEN_WIDTH-15,SCREEN_HEIGHT/2-25,15,70,2);
    }
    public void newBall(){
        ball = new Ball(random.nextInt(50)+SCREEN_WIDTH/2-25,random.nextInt(50)+SCREEN_HEIGHT/2-25,20,20);
    }
    public void newPowerUp() {
        powerUp = new PowerUp(random.nextInt(SCREEN_WIDTH-100)+50,random.nextInt(SCREEN_HEIGHT-100)+50,40,40);
        powerUpOnBoard=true;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(paused){
            g.setColor(Color.BLUE);
            g.setFont(new Font("SANS_SERIF",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Paused", (SCREEN_WIDTH - metrics.stringWidth("Paused"))/2, g.getFont().getSize()-200);
        }
        if(running){
            g.setColor(Color.green);
            g.setFont(new Font("SANS_SERIF",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(p1Score+":"+p2Score, (SCREEN_WIDTH - metrics.stringWidth(p1Score+":"+p2Score))/2, g.getFont().getSize());
            paddle1.draw(g);
            paddle2.draw(g);
            ball.draw(g);
            if(powerUpOnBoard) powerUp.draw(g);
        }else {
            timer.stop();
            BackgroundMusic.clip.stop();
            myFrame.switchToEndGame(p1Score,p2Score);
        }
    }
    public void setPlayersColors(Color p1Color, Color p2Color) {
        paddle1.setColor(p1Color);
        paddle2.setColor(p2Color);
    }
    public void move(){
        paddle1.move();
        paddle2.move();
        ball.moveBall();
    }
    public void restartStats(Color p1Color, Color p2Color){
        p1Score=p2Score=0;
        running=true;
        setPlayersColors(p1Color,p2Color);
    }
    public void newRound(){
        paddle1.y=paddle2.y=SCREEN_HEIGHT/2-25;
        paddle1.height=paddle2.height=50;
        paddle1.setMaxSpeed(4);
        paddle2.setMaxSpeed(4);
        ball.x=SCREEN_WIDTH/2-10;
        ball.y=SCREEN_HEIGHT/2-10;
        ball.randomizeDirection();
        ball.width=20;
        ball.height=20;
        ball.timesBounced=0;
        ball.setMaxYSpeed(2);
        bouncedAfterPowerUp=0;
        if(powerUpOnBoard) powerUp.setLocation(SCREEN_WIDTH+100,SCREEN_HEIGHT+100);
        powerUpOnBoard=false;
    }
    public void checkCollisions() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(ball.y<0 || (ball.y>SCREEN_HEIGHT-ball.height) ) {
            ball.setyVelocity(-ball.getyVelocity());
            //plays sound when ball hits wall
            //playSoundEffect("ball_hit_wall.wav");
        }
        if(ball.y>SCREEN_HEIGHT-ball.height+2) ball.y=SCREEN_HEIGHT-ball.height;
        if(ball.intersects(paddle1))
        {
            if(ball.y+ball.height/2>paddle1.y+(paddle1.height/3)&&(ball.y+ball.height/2<paddle1.y+2*paddle1.height/3)){
                if(ball.getyVelocity()>1) ball.setyVelocity(ball.getyVelocity()-1);
                else if (ball.getyVelocity()<-1) ball.setyVelocity(ball.getyVelocity()+1);
            }
            else if(ball.y+ball.height/2<paddle1.y+paddle1.height/3){
                if(Math.abs(ball.getyVelocity())<ball.getMaxYSpeed()) ball.setyVelocity(ball.getyVelocity()-1);
            }
            else if(ball.y+ball.height/2>paddle1.y+2*paddle1.height/3){
                if(Math.abs(ball.getyVelocity())<ball.getMaxYSpeed()) ball.setyVelocity(ball.getyVelocity()+1);
            }
            ball.setxVelocity(-ball.getxVelocity());
            ball.timesBounced++;
            bouncedAfterPowerUp++;
            bouncedByP1=true;
            //playing sound when ball hits paddle
            //playSoundEffect("ball_hit_paddle.wav");
        }
        if(ball.intersects(paddle2)){
            if(ball.y+ball.height/2>paddle2.y+(paddle2.height/3)&&(ball.y+ball.height/2<paddle2.y+2*paddle2.height/3)){
                if(ball.getyVelocity()>1) ball.setyVelocity(ball.getyVelocity()-1);
                else if (ball.getyVelocity()<-1) ball.setyVelocity(ball.getyVelocity()+1);
            }
            else if(ball.y+ball.height/2<paddle2.y+paddle2.height/3){
                if(Math.abs(ball.getyVelocity())<ball.getMaxYSpeed()) ball.setyVelocity(ball.getyVelocity()-1);
            }
            else if(ball.y+ball.height/2>paddle2.y+2*paddle2.height/3){
                if(Math.abs(ball.getyVelocity())<ball.getMaxYSpeed()) ball.setyVelocity(ball.getyVelocity()+1);
            }
            ball.setxVelocity(-ball.getxVelocity());
            ball.timesBounced++;
            bouncedAfterPowerUp++;
            bouncedByP1=false;
            //playing sound when ball hits paddle
            //playSoundEffect("ball_hit_paddle.wav");
        }
        int maximumScore = 5;
        if(ball.x<0) {
            p2Score++;
            if(p2Score>= maximumScore) {

                running=false;
            }
            newRound();
        }
        if(ball.x>SCREEN_WIDTH-ball.width){
            p1Score++;
            if(p1Score>= maximumScore) {

                running = false;
            }
            newRound();
        }
        if(powerUpOnBoard){
            if(ball.intersects(powerUp)) {
                powerUp.doPowerUp(bouncedByP1 ? paddle1 : paddle2,ball);
                powerUp.x=SCREEN_WIDTH+10;
                powerUp.y=SCREEN_HEIGHT+10;
                playSoundEffect("get_powerup.wav");
                powerUpOnBoard=false;
                bouncedAfterPowerUp=0;
            }
        }

        if(paddle1.y<=0)
            paddle1.y=0;
        if(paddle1.y >= (SCREEN_HEIGHT-paddle1.height))
            paddle1.y = SCREEN_HEIGHT-paddle1.height;
        if(paddle2.y<=0)
            paddle2.y=0;
        if(paddle2.y >= (SCREEN_HEIGHT-paddle2.height))
            paddle2.y = SCREEN_HEIGHT-paddle2.height;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timePassed+=2;
        move();
        try {
            checkCollisions();
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(!powerUpOnBoard&&bouncedAfterPowerUp>2) newPowerUp();
        repaint();
        if(timePassed>3900) {
            try {
                BackgroundMusic.asssignMusic("gameMusic.wav");
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
            timePassed=0;
        }
    }
    public class MyActionListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            /*if(e.getKeyCode()==KeyEvent.VK_ESCAPE&&!paused){
                paused=true;
                repaint();
                timer.stop();

            }else if(e.getKeyCode()==KeyEvent.VK_ESCAPE&&paused){
                paused=false;
                timer.start();
            }
            */
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);

        }
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
    public static void playSoundEffect(String file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File soundFile = new File(file);
        AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
        // load the sound into memory (a Clip)
        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(sound);

        // due to bug in Java Sound, explicitly exit the VM when
        // the sound has stopped.
        clip.addLineListener(new LineListener() {
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                }
            }
        });
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-20.0f); // Reduce volume by 30 decibels.
        // play the sound clip
        clip.start();
    }
}
