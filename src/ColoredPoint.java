import java.awt.*;

public class ColoredPoint extends Point {
    private Color color;
    public ColoredPoint(Point p, Color color) {
        super(p);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
