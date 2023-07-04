import java.awt.*;
import java.awt.event.WindowEvent;

public class Main {
    public static Game asteroids;

    public static void main(String[] args) {
        asteroids = new Game();
    }

    public static void restart() {
        asteroids.dispatchEvent(new WindowEvent(asteroids, WindowEvent.WINDOW_CLOSING));
        asteroids = new Game();
    }
}