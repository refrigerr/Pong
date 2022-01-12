package pongv2;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyButton extends JButton {
    public MyButton(int x, int y, int width,int height,String text){
        setBounds(x,y,width,height);
        setText(text);
        setFont(new Font("Serif", Font.BOLD, 38));
        addActionListener(e -> {
            //playing music when clicked
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
        setVisible(true);
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
