package pongv2;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class MyFrame extends JFrame {
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 450;
    JPanel panelCont = new JPanel();
    MenuPanel menu = new MenuPanel(this,SCREEN_WIDTH,SCREEN_HEIGHT);
    GamePanel game = new GamePanel(this,SCREEN_WIDTH,SCREEN_HEIGHT);
    EndGamePanel egp = new EndGamePanel(this,SCREEN_WIDTH,SCREEN_HEIGHT);
    CardLayout cl = new CardLayout();
    public MyFrame(){
        panelCont.setLayout(cl);
        panelCont.add(menu,"1");
        panelCont.add(game,"2");
        panelCont.add(egp,"3");
        cl.show(panelCont,"1");
        this.add(panelCont);
        this.setTitle("Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void switchToGame(Color color1, Color color2){
        game.restartStats(color1,color2);
        cl.show(panelCont,"2");
        game.requestFocusInWindow();
        game.timer.start();
        game.timePassed=4000;
    }
    public void switchToMenu() {
        cl.show(panelCont,"1");
        menu.requestFocusInWindow();
        menu.timer.start();
    }
    public void switchToEndGame(int p1Score, int p2Score){
        egp.setScors(p1Score,p2Score);
        cl.show(panelCont,"3");
        egp.requestFocusInWindow();
    }


}
