import java.awt.geom.Arc2D;

public class ImmunityRing {
    public Arc2D.Double ring;

    public ImmunityRing(double x, double y, double extent) {
        ring = new Arc2D.Double(Arc2D.OPEN);
        ring.setFrame(x, y, 40, 40);
        ring.setAngleStart(0);
        ring.setAngleExtent(extent);
    }
}
