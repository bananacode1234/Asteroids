import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Window extends JPanel {
    private Game game;
    private JButton start;
    private JButton quit;
    private Dimension startSize;

    public Window(Game game) {
        this.game = game;

        this.setDoubleBuffered(true);

        setPreferredSize(new Dimension(Game.width, Game.height));

        this.setLayout(null);

        start = new JButton("Start");

        start.setFocusable(false);

        startSize = start.getPreferredSize();

        start.setBounds(Game.width / 2 - startSize.width / 2, Game.height - 250, startSize.width, startSize.height);

        start.setBackground(Color.GREEN);
        start.setForeground(Color.BLACK);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.started) game.timer.stop();
                game.startGame();
                start.setVisible(false);
                quit.setVisible(false);
            }
        });

        this.add(start);

        start.setVisible(false);

        quit = new JButton("Quit");

        quit.setFocusable(false);

        quit.setBounds(Game.width / 2 - startSize.width / 2, Game.height - 200, startSize.width, startSize.height);

        quit.setBackground(Color.GREEN);
        quit.setForeground(Color.BLACK);

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.dispatchEvent(new WindowEvent(game, WindowEvent.WINDOW_CLOSING));
            }
        });

        this.add(quit);

        quit.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, Game.width, Game.height);
        g2d.setColor(Color.GREEN);

        if (game.started) {
            if (game.asteroidList.isEmpty()) {
                g2d.setColor(Color.GREEN);
                g2d.setFont(new Font("sans-serif", Font.PLAIN, 25));
                printSimpleString("Game over - You win", Game.width / 2, Game.height / 2, g2d);
            }

            if (game.ship.active) {
                if (game.ship.immuneTimer < game.ship.immuneTime) {
                    ImmunityRing ring = new ImmunityRing(game.ship.xPosition - 20, game.ship.yPosition - 20, 360 - game.ship.immuneTimer * ((double) 360 / game.ship.immuneTime));
                    g2d.draw(ring.ring);
                }

                game.ship.paint(g2d);
            }

            for (Asteroid asteroid : game.asteroidList) {
                if (asteroid.active) asteroid.paint(g2d);
            }

            for (Bullet bullet : game.bulletList) {
                if (bullet.active) bullet.paint(g2d);
            }

            g2d.setColor(Color.GREEN);
            if (game.ship.lives > 0) {
                g2d.setFont(new Font("sans-serif", Font.BOLD, 12));
                printSimpleString("Lives: " + game.ship.lives, Game.width / 2, 25, g2d);
            } else {
                start.setText("Restart");
                startSize = start.getPreferredSize();
                start.setBounds(Game.width / 2 - startSize.width / 2, Game.height - 250, startSize.width, startSize.height);
                start.setVisible(true);
                quit.setVisible(true);
                g2d.setFont(new Font("sans-serif", Font.PLAIN, 25));
                printSimpleString("Game over - You lose", Game.width / 2, Game.height / 2, g2d);
            }
        } else {
            g2d.setFont(new Font("sans-serif", Font.PLAIN, 25));
            printSimpleString("Welcome to rock go brrrr!", Game.width / 2, Game.height / 2, g2d);
            startSize = start.getPreferredSize();
            start.setBounds(Game.width / 2 - startSize.width / 2, Game.height - 250, startSize.width, startSize.height);
            start.setText("Start");
            start.setVisible(true);
        }
    }

    public static void printSimpleString(String s, int XPos, int YPos, Graphics2D g2d) {
        String[] sList = s.split("\n");

        for (int i = 0; i < sList.length; i++) {
            int stringLen = (int) g2d.getFontMetrics().getStringBounds(sList[i], g2d).getWidth();
            int start = -stringLen / 2;
            g2d.drawString(sList[i], start + XPos, YPos + i * 20);
        }
    }
}
