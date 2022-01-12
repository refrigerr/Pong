package pongv2;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MenuPanel extends JPanel implements ActionListener {
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private int x;
    private int y;
    private int xDirection;
    private int yDirection;
    private boolean running;
    Timer timer;
    long timePassed=1300;

    public MenuPanel(MyFrame myFrame, int SCREEN_WIDTH,int SCREEN_HEIGHT) {
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.setPreferredSize(new Dimension(this.SCREEN_WIDTH, this.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        running = true;
        timer = new Timer(1,this);
        timer.start();

        JLabel label1 = new JLabel("PONG");
        label1.setFont(new Font("SANS_SERIF", Font.BOLD, 40));
        label1.setForeground(Color.GREEN);
        label1.setBounds(SCREEN_WIDTH/2-56, 10, 150, 50);
        label1.setVisible(true);
        this.add(label1);


        MyButton exitButton = new MyButton(SCREEN_WIDTH/2-100, SCREEN_HEIGHT-100, 200, 50, "EXIT");
        exitButton.setBackground(Color.green);
        exitButton.setLayout(null);
        exitButton.setVisible(true);
        exitButton.addActionListener(e -> System.exit(0));
        this.add(exitButton);

        String[] colors = {"WHITE","CYAN","YELLOW","GREEN","RED","PINK"};
        JComboBox chooseP1color = new JComboBox(colors);
        chooseP1color.setBounds(this.SCREEN_WIDTH/2-210,this.SCREEN_HEIGHT-215,200,50);
        chooseP1color.setBackground(Color.green);
        chooseP1color.setFont(new Font("Serif", Font.BOLD, 30));
        chooseP1color.addActionListener(e -> {
            //playing music when selecting
            try {
                playSoundEffect("mixkit-video-game-retro-click-237.wav");
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });
        chooseP1color.setVisible(true);
        this.add(chooseP1color);

        JLabel label2 = new JLabel("Player 1 color:");
        label2.setFont(new Font("SANS_SERIF", Font.BOLD, 21));
        label2.setForeground(Color.GREEN);
        label2.setBounds(this.SCREEN_WIDTH/2-210, this.SCREEN_HEIGHT-270, 150, 50);
        label2.setVisible(true);
        this.add(label2);

        JComboBox chooseP2color = new JComboBox(colors);
        chooseP2color.setBounds(this.SCREEN_WIDTH/2+10,this.SCREEN_HEIGHT-215,200,50);
        chooseP2color.setBackground(Color.green);
        chooseP2color.setFont(new Font("Serif", Font.BOLD, 30));
        chooseP2color.addActionListener(e -> {
            //playing music when selecting
            try {
                playSoundEffect("mixkit-video-game-retro-click-237.wav");
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });
        chooseP2color.setVisible(true);
        this.add(chooseP2color);

        JLabel label3 = new JLabel("Player 2 color:");
        label3.setFont(new Font("SANS_SERIF", Font.BOLD, 21));
        label3.setForeground(Color.GREEN);
        label3.setBounds(this.SCREEN_WIDTH/2+10, this.SCREEN_HEIGHT-270, 150, 50);
        label3.setVisible(true);
        this.add(label3);

        MyButton startGame = new MyButton(SCREEN_WIDTH/2-100, 85, 200, 50, "PLAY");
        startGame.setBackground(Color.green);
        startGame.setLayout(null);
        startGame.setVisible(true);
        startGame.addActionListener(e -> {
            BackgroundMusic.clip.stop();
            timer.stop();
            timePassed=1300;
            myFrame.switchToGame(setColor(Objects.requireNonNull(chooseP1color.getSelectedItem()).toString()),setColor(Objects.requireNonNull(chooseP2color.getSelectedItem()).toString()));
        });
        this.add(startGame);

        x=SCREEN_WIDTH/2;
        y=SCREEN_HEIGHT/2;
        xDirection=2;
        yDirection=2;

        this.setVisible(true);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x,y,20,20);
    }
    public void move(){
        x+=xDirection;
        y+=yDirection;
    }
    public void checkCollisions(){
        if(x<0||x>SCREEN_WIDTH-20) xDirection=-xDirection;
        if(y<0||y>SCREEN_HEIGHT-20) yDirection=-yDirection;

    }
    public Color setColor(String string){
        switch (string) {
            case "WHITE" -> {
                return Color.WHITE;
            }
            case "CYAN" -> {
                return Color.CYAN;
            }
            case "YELLOW" -> {
                return Color.YELLOW;
            }
            case "RED" -> {
                return Color.RED;
            }
            case "GREEN" -> {
                return Color.GREEN;
            }
            default -> {
                return Color.PINK;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timePassed+=2;
        if(timePassed>1300){
            try {
                BackgroundMusic.asssignMusic("menuMusic.wav");
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
            timePassed=0;
        }
        move();
        checkCollisions();
        repaint();
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
        gainControl.setValue(-10.0f); // Reduce volume by 30 decibels.
        // play the sound clip
        clip.start();
    }

}
