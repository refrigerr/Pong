package pongv2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class EndGamePanel extends JPanel {
    private int p1Score;
    private int p2Score;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    MyFrame myFrame;

    public EndGamePanel(MyFrame myFrame,int SCREEN_WIDTH,int SCREEN_HEIGHT){
        this.myFrame=myFrame;
        this.SCREEN_HEIGHT=SCREEN_HEIGHT;
        this.SCREEN_WIDTH=SCREEN_WIDTH;
        this.setPreferredSize(new Dimension(this.SCREEN_WIDTH,this.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        repaint();
        this.setVisible(true);
    }

    public void setScors(int p1Score, int p2Score){
        this.p1Score = p1Score;
        this.p2Score = p2Score;
    }

    public void endGameScreen(Graphics g){

        g.setColor(Color.red);
        g.setFont( new Font("Sherif",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString(p1Score>p2Score?"Player 1 won!":"Player 2 won!", (SCREEN_WIDTH - metrics2.stringWidth("Player 2 won!"))/2, SCREEN_HEIGHT/2);

        g.setColor(Color.green);
        g.setFont( new Font("SANS_SERIF",Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(p1Score+":"+p2Score, (SCREEN_WIDTH - metrics.stringWidth(p1Score+":"+p2Score))/2, SCREEN_HEIGHT/2+50);

        MyButton returnButton = new MyButton(SCREEN_WIDTH/2-125,SCREEN_HEIGHT/2+100,250,50,"Main Menu");
        returnButton.setBackground(Color.green);
        returnButton.setVisible(true);
        returnButton.addActionListener(e->{
            myFrame.switchToMenu();
        });
        this.add(returnButton);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        endGameScreen(g);
    }
}
