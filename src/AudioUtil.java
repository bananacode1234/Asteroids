import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioUtil implements LineListener {
    AudioInputStream audioInputStream;

    Clip[] clips;

    public AudioUtil() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        clips = new Clip[5];

        audioInputStream = AudioSystem.getAudioInputStream(new File("./src/Sounds/laser79.wav").getAbsoluteFile());

        clips[0] = AudioSystem.getClip();
        clips[0].open(audioInputStream);

        audioInputStream = AudioSystem.getAudioInputStream(new File("./src/Sounds/laser80.wav").getAbsoluteFile());

        clips[1] = AudioSystem.getClip();
        clips[1].open(audioInputStream);

        audioInputStream = AudioSystem.getAudioInputStream(new File("./src/Sounds/thruster.wav").getAbsoluteFile());

        AudioFormat format = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);

        clips[2] = AudioSystem.getClip();
        clips[2].open(audioInputStream);

        audioInputStream = AudioSystem.getAudioInputStream(new File("./src/Sounds/explode0.wav").getAbsoluteFile());

        clips[3] = AudioSystem.getClip();
        clips[3].open(audioInputStream);

        audioInputStream = AudioSystem.getAudioInputStream(new File("./src/Sounds/explode1.wav").getAbsoluteFile());

        clips[4] = AudioSystem.getClip();
        clips[4].open(audioInputStream);
    }

    @Override
    public void update(LineEvent e) {

    }
}
