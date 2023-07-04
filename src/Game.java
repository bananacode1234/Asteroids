import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JFrame implements KeyListener, ActionListener, MouseListener {

    public final static int width = 900;
    public final static int height = 600;

    final int startingAsteroids = 6;

    public boolean started = false;

    public double fps = 60;

    public Window panel;

    public Spacecraft ship;

    public ArrayList<Asteroid> asteroidList;

    public ArrayList<Bullet> bulletList;

    Timer timer;

    boolean thrusterPlaying;

    int fireDelay = 5;

    AudioUtil audioUtil;

    boolean upKey, rightKey, leftKey, downKey, spaceKey, impulseKey;

    public Game() {
        started = false;

        this.setVisible(true);
        this.setSize(width, height);
        this.setTitle("rock go brrrrr");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.addKeyListener(this);
        this.addMouseListener(this);

        try {
            audioUtil = new AudioUtil();
        } catch (Exception ignored) {}

        this.add(this.panel = new Window(this), BorderLayout.CENTER);

        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        keyCheck();
        respawnShip();

        ship.applyFriction();
        ship.updatePosition();

        if (ship.active) {
            ship.immuneTimer++;
        }

        for (int i = asteroidList.size() - 1; i >= 0; i--) {
            asteroidList.get(i).updateAsteroid();
            asteroidList.get(i).updatePosition();

            if (!asteroidList.get(i).active) {
                if (Math.random() >= 0.5) {
                    audioUtil.clips[3].stop();
                    audioUtil.clips[3].setFramePosition(0);
                    audioUtil.clips[3].start();
                } else {
                    audioUtil.clips[4].stop();
                    audioUtil.clips[4].setFramePosition(0);
                    audioUtil.clips[4].start();
                }

                if (asteroidList.get(i).iteration <= 1) {
                    asteroidList.add(new Asteroid(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition, ++asteroidList.get(i).iteration));
                    asteroidList.add(new Asteroid(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition, asteroidList.get(i).iteration));
                    asteroidList.add(new Asteroid(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition, asteroidList.get(i).iteration));
                }
                asteroidList.remove(i);
            }
        }

        for (int i = bulletList.size() - 1; i >= 0; i--) {
            bulletList.get(i).updatePosition();
            if (bulletList.get(i).frameCounter >= 90 || !bulletList.get(i).active) {
                bulletList.remove(i);
            }
        }

        checkCollision();

        repaint();
    }

    public void respawnShip() {
        if (!ship.active && ship.frameCounter > 5 && ship.lives > 0) {
            ship.reset();
            impulseForce(200);
        }
    }

    public void impulseForce(int range) {
        for (Asteroid asteroid : asteroidList) {
            double xDist = asteroid.xPosition - ship.xPosition;
            double yDist = asteroid.yPosition - ship.yPosition;
            double distanceFromShip = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));

            if (distanceFromShip < range) {
                double angle = Math.atan2(yDist, xDist);
                asteroid.xSpeed = Math.cos(angle) * (Math.random() + 1) / 2 * 8;
                asteroid.ySpeed = Math.sin(angle) * (Math.random() + 1) / 2 * 8;
            }
        }
    }

    public void checkCollision() {
        for (Asteroid asteroid : asteroidList) {
            if (collision(ship, asteroid) && ship.immuneTimer > ship.immuneTime) {
                ship.hit();
                ship.immuneTimer = 0;
                ship.lives--;
            }

            for (Bullet bullet : bulletList) {
                if (collision(asteroid, bullet)) {
                    asteroid.active = false;
                    bullet.active = false;
                }
            }
        }
    }

    public boolean collision(VectorSprite p1, VectorSprite p2) {
        for (int i = 0; i < p1.drawShape.npoints; i++) {
            if (p2.drawShape.contains(p1.drawShape.xpoints[i], p1.drawShape.ypoints[i])) {
                return true;
            }
        }

        for (int i = 0; i < p2.drawShape.npoints; i++) {
            if (p1.drawShape.contains(p2.drawShape.xpoints[i], p2.drawShape.ypoints[i])) {
                return true;
            }
        }

        return false;
    }

    void fireBullet() {
        if (ship.frameCounter > fireDelay && ship.active) {
            ship.frameCounter = 0;

            if (Math.random() >= 0.5) {
                audioUtil.clips[1].stop();
                audioUtil.clips[0].setFramePosition(0);
                audioUtil.clips[0].start();
            } else {
                audioUtil.clips[1].stop();
                audioUtil.clips[1].setFramePosition(0);
                audioUtil.clips[1].start();
            }

            bulletList.add(new Bullet(
                    new int[][]{
                            {2, 2},
                            {-2, 2},
                            {-2, -2},
                            {2, -2},
                            {4, 0}
                    },
                    ship.xPosition + Math.cos(ship.angle) * 10,
                    ship.yPosition + Math.sin(ship.angle) * 10,
                    ship.angle,
                    ship.xSpeed,
                    ship.ySpeed
            ));
        }
    }

    public void startGame() {
        ship = new Spacecraft(
                new int[][]{
                        {15, 0},
                        {-10, 10},
                        {-10, -10}
                });

        asteroidList = new ArrayList<>();

        for (int i = 0; i < startingAsteroids; i++) {
            asteroidList.add(new Asteroid(new int[][]{
                    {30, 12},
                    {30, (int) ((Math.random() + 1) * 20)},
                    {-25, 17},
                    {-22, -19},
                    {20, -(int) ((Math.random() + 1) * 15)}
            }));
        }

        bulletList = new ArrayList<>();

        timer = new Timer((int) (1000 / fps), this);
        timer.start();

        started = true;
    }

    public void keyCheck() {
        if (upKey) ship.accelerate();

        if (downKey) ship.decelerate();

        if (leftKey) ship.rotateLeft();

        if (rightKey) ship.rotateRight();

        if (spaceKey) fireBullet();

        if (impulseKey) impulseForce(100);

        if (started && ship.active && (upKey || downKey)) {
            if (!thrusterPlaying) {
                thrusterPlaying = true;
                audioUtil.clips[2].setFramePosition(0);
                audioUtil.clips[2].loop(Clip.LOOP_CONTINUOUSLY);
            }
        } else {
            thrusterPlaying = false;
            audioUtil.clips[2].stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                upKey = true;
            }
            case KeyEvent.VK_A -> {
                leftKey = true;
            }
            case KeyEvent.VK_S -> {
                downKey = true;
            }
            case KeyEvent.VK_D -> {
                rightKey = true;
            }
            case KeyEvent.VK_SPACE -> {
                spaceKey = true;
            }
            case KeyEvent.VK_E -> {
                impulseKey = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                upKey = false;
            }
            case KeyEvent.VK_A -> {
                leftKey = false;
            }
            case KeyEvent.VK_D -> {
                rightKey = false;
            }
            case KeyEvent.VK_S -> {
                downKey = false;
            }
            case KeyEvent.VK_SPACE -> {
                spaceKey = false;
            }
            case KeyEvent.VK_E -> {
                impulseKey = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        spaceKey = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        spaceKey = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
