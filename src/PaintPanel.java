import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaintPanel extends JPanel {

    private ArrayList<ArrayList<ColoredPoint>> linesWithColor = new ArrayList<>();
    private ArrayList<ColoredPoint> currentLineWithColor;
    private Image image;
    private Color color;

    public PaintPanel() {

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentLineWithColor.add(new ColoredPoint(e.getPoint(), color));
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                currentLineWithColor = new ArrayList<>();
                linesWithColor.add(currentLineWithColor);
            }
        };
        addMouseMotionListener(adapter);
        addMouseListener(adapter);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g.create();
        if (!Objects.isNull(image)) {
            graphics.drawImage(image, null, null);
        }

        for (ArrayList<ColoredPoint> line : linesWithColor) {
            Point previous = null;
            for (ColoredPoint point : line) {
                graphics.setColor(point.getColor());

                if (previous != null) {
                    graphics.draw(new Line2D.Float(previous, point));
                }
                previous = point;
            }
        }
        graphics.dispose();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }


    public void clear() {
        linesWithColor.clear();
        currentLineWithColor.clear();
        image = null;
        repaint();
    }


}
