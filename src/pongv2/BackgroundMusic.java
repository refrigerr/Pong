package pongv2;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {
    public static Clip clip;
    public static void asssignMusic(String file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File soundFile = new File(file);
        AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
        // load the sound into memory (a Clip)
        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
        clip = (Clip) AudioSystem.getLine(info);
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
