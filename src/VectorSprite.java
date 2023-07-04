import java.awt.*;

public class VectorSprite {
    double xPosition, yPosition;
    double xSpeed, ySpeed;
    double angle;
    boolean active;

    int frameCounter = 0;

    Polygon shape, drawShape;

    public VectorSprite(int[][] vertices) {
        shape = new Polygon();

        for (int[] vertex : vertices) {
            shape.addPoint(vertex[0], vertex[1]);
        }

        drawShape = new Polygon();

        for (int[] vertex : vertices) {
            drawShape.addPoint(vertex[0], vertex[1]);
        }

        xPosition = (double) Game.width / 2;
        yPosition = (double) Game.height / 2;
    }

    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawPolygon(drawShape);
    }

    public void paint(Graphics g, Color fill, Color outline) {
        g.setColor(outline);
        g.drawPolygon(drawShape);
        g.setColor(fill);
        g.fillPolygon(drawShape);
    }

    public void updatePosition() {
        frameCounter++;
        xPosition += xSpeed;
        yPosition += ySpeed;

        screenWrap();

        int x, y;

        for (int i = 0; i < shape.npoints; i++) {
            x = (int) Math.round(shape.xpoints[i] * Math.cos(angle) - shape.ypoints[i] * Math.sin(angle));
            y = (int) Math.round(shape.xpoints[i] * Math.sin(angle) + shape.ypoints[i] * Math.cos(angle));

            drawShape.xpoints[i] = x;
            drawShape.ypoints[i] = y;
        }

        drawShape.translate((int) Math.round(xPosition), (int) Math.round(yPosition));
        drawShape.invalidate();
    }

    public void hit() {
        active = false;
        frameCounter = 0;
    }

    public void screenWrap() {
        if (xPosition < -15) xPosition = Game.width + 15;
        else if (xPosition > Game.width + 15) xPosition = -15;

        if (yPosition < -15) yPosition = Game.height + 15;
        else if (yPosition > Game.height + 15) yPosition = -15;
    }
}
