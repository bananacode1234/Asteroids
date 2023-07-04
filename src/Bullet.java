public class Bullet extends VectorSprite {
    public Bullet(int[][] vertices, double shipX, double shipY, double shipAngle, double shipXSpeed, double shipYSpeed) {
        super(vertices);
        active = true;
        xPosition = shipX;
        yPosition = shipY;
        angle = shipAngle;

        double shipSpeed = Math.sqrt(Math.pow(shipXSpeed, 2) + Math.pow(shipYSpeed, 2));

        xSpeed = Math.cos(angle) * (shipSpeed + 6);
        ySpeed = Math.sin(angle) * (shipSpeed + 6);
    }

    @Override
    public void screenWrap() {
        if (!(xPosition < -15 || xPosition > Game.width + 15)) return;
        if (!(yPosition < -15 || yPosition > Game.height + 15)) return;

        active = false;
    }
}
