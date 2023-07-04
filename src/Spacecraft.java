public class Spacecraft extends VectorSprite {
    private final double thrusterPower;
    private final double frictionFactor;
    private final double rotationFactor;
    public int lives;
    public int immuneTime;
    public int immuneTimer;

    public Spacecraft(int[][] vertices) {
        super(vertices);
        active = true;
        lives = 3;
        immuneTimer = 0;
        immuneTime = 75;
        thrusterPower = 1;
        frictionFactor = 0.975;
        rotationFactor = Math.PI / 20;
    }

    public void accelerate() {
        xSpeed += Math.cos(angle) * thrusterPower;
        ySpeed += Math.sin(angle) * thrusterPower;
    }

    public void decelerate() {
        xSpeed -= Math.cos(angle) * thrusterPower;
        ySpeed -= Math.sin(angle) * thrusterPower;
    }

    public void applyFriction() {
        xSpeed = xSpeed * frictionFactor;
        ySpeed = ySpeed * frictionFactor;
    }

    public void rotateLeft() {
        angle -= rotationFactor;
    }

    public void rotateRight() {
        angle += rotationFactor;
    }

    public void reset() {
        xPosition = (double) Game.width / 2;
        yPosition = (double) Game.height / 2;
        xSpeed = 0;
        ySpeed = 0;
        angle = 0;
        active = true;
        immuneTimer = 0;
    }
}