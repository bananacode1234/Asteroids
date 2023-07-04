public class Asteroid extends VectorSprite {
    public double rotation;
    private double speedFactor;

    public int iteration;

    public Asteroid(double xposition, double yposition, int iteration) {
        super(new int[][]{
                {30 / iteration, 3 / iteration},
                {5 / iteration, (int) ((Math.random() + 1) * 15 / iteration)},
                {-25 / iteration, 10 / iteration},
                {-17 / iteration, -15 / iteration},
                {20 / iteration, -(int) ((Math.random() + 1) * 15 / iteration)}
        });
        this.iteration = iteration;
        initializeAsteroid(xposition, yposition);
    }

    public Asteroid(int[][] vertices) {
        super(vertices);
        active = true;
        iteration = 0;

        rotation = Math.random() / 5 - 0.1;
        speedFactor = 7.5;
        
        xSpeed = (Math.random() - 0.5) * speedFactor;
        ySpeed = (Math.random() - 0.5) * speedFactor;

        double h, a;
        a = Math.random() * 2 * Math.PI;
        h = 2 * Math.random() * 400 + 250;

        initializeAsteroid(
                Math.cos(a) * h + (double) Game.width / 2,
                Math.sin(a) * h + (double) Game.height / 2
        );
    }

    void initializeAsteroid(double xposition, double yposition) {
        active = true;

        rotation = Math.random() / 5 - 0.1;
        speedFactor = 7.5;

        xSpeed = (Math.random() - 0.5) * speedFactor;
        ySpeed = (Math.random() - 0.5) * speedFactor;

        this.xPosition = xposition;
        this.yPosition = yposition;
    }

    public void updateAsteroid() {
        rotateAsteroid();
    }

    private void rotateAsteroid() {
        angle += rotation;
    }
}
