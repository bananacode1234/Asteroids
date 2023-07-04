import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AudioUtil {
    private static AudioUtil instance = new AudioUtil();

    public static AudioUtil getInstance() {
        return instance;
    }

    public AudioUtil() {

    }

    public URL transform(File audioFile) throws MalformedURLException {
        if (audioFile.canRead()) {
            return audioFile.toURI().toURL();
        }
        throw new IllegalArgumentException();
    }
}
