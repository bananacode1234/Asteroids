import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JPanel {
    private Game game;
    private JButton restart;

    public Window(Game game) {
        this.game = game;

        this.setDoubleBuffered(true);

        setPreferredSize(new Dimension(Game.width, Game.height));

        this.setLayout(null);

        restart = new JButton("Restart");

        restart.setFocusable(false);

        Dimension restartSize = restart.getPreferredSize();

        restart.setBounds(Game.width / 2 - restartSize.width / 2, Game.height - 250, restartSize.width, restartSize.height);

        restart.setBackground(Color.GREEN);
        restart.setForeground(Color.BLACK);

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.timer.stop();
                game.restartGame();
                restart.setVisible(false);
            }
        });

        this.add(restart);
        restart.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, Game.width, Game.height);
        g2d.setColor(Color.GREEN);

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

            if (game.upKey) game.thrusterSprite.paint(g2d);
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
            restart.setVisible(true);
            g2d.setFont(new Font("sans-serif", Font.PLAIN, 25));
            printSimpleString("Game over - You lose", Game.width / 2, Game.height / 2, g2d);
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
