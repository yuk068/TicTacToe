package tictactoe;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SFX {

    private static final Map<Integer, Float> decibelMap = new HashMap<>();
    public static boolean mute;

    static {
        mute = false;
        decibelMap.put(0, -40.0f);
        decibelMap.put(1, -30.0f);
        decibelMap.put(2, -20.0f);
        decibelMap.put(3, -10.0f);
        decibelMap.put(4, -1.0f);
        decibelMap.put(5, 2.0f);
        decibelMap.put(6, 6.0f);
    }

    public static void playSound(String fileName, int volume) {
        if (mute) return;
        try {
            ClassLoader classLoader = Main.class.getClassLoader();

            String filePath = Main.class.getPackageName().replace('.', '/')+ fileName;
            URL soundURL = classLoader.getResource(filePath);

            if (soundURL != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(decibelMap.get(volume));
                clip.start();
            } else {
                System.err.println("Sound file not found: " + fileName);
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

}

