import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.BatchUpdateException;

public class PaintFrame extends JFrame {

    private PaintPanel paintPanel;

    private JButton openButton;
    private JButton colorButton;
    private JButton saveButton;
    private JButton clearButton;


    public PaintFrame(String title) throws HeadlessException {
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createButton();
        addButtonEvent();

        addElements();
    }

    private void createButton() {
        openButton = new JButton("Open");
        colorButton = new JButton("Select color");
        saveButton = new JButton("Save");
        clearButton = new JButton("Clear");
    }

    public void addElements()
    {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(clearButton);
        buttonPanel.add(colorButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(openButton);

        paintPanel = new PaintPanel();
        paintPanel.setLayout(new BoxLayout(paintPanel, BoxLayout.Y_AXIS));
        paintPanel.setPreferredSize(new Dimension(500, 500));
        JScrollPane scrollPane = new JScrollPane(paintPanel);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addButtonEvent() {
        MouseAdapter openAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JFileChooser chooser = new JFileChooser(".");
                int result = chooser.showSaveDialog(PaintFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    paintPanel.setImage(Toolkit.getDefaultToolkit().getImage(chooser.getSelectedFile().toString()));
                }
            }
        };

        MouseAdapter colorAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paintPanel.setColor(JColorChooser.showDialog(null, "Color", null));
            }
        };

        MouseAdapter saveAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = bi.createGraphics();
                paintPanel.print(g2d);
                g2d.dispose();

                JFileChooser chooser = new JFileChooser(".");
                int result = chooser.showSaveDialog(PaintFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        ImageIO.write(bi, "png", chooser.getSelectedFile());
                    } catch (IOException exception) {
                        System.err.println(exception);
                    }
                }
            }
        };

        MouseAdapter clearAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paintPanel.clear();
            }
        };

        openButton.addMouseListener(openAdapter);
        saveButton.addMouseListener(saveAdapter);
        colorButton.addMouseListener(colorAdapter);
        clearButton.addMouseListener(clearAdapter);
    }
}
