import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;

    private Program backend;

    private LineSegment[][] model;
    private int layerIndex;

    public Window(Program backend) {
        this.backend = backend;
        layerIndex = 0;

        // Setup the window and the buffer strategy.
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("ATCS Slicer");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public void setModel(LineSegment[][] model) {
        this.model = model;
    }

    public void setLayerIndex(int layerIndex) {
        this.layerIndex = layerIndex;
    }

    public void shiftLayerUp(int amt) {
        layerIndex = Math.min(model.length, layerIndex+amt);
    }

    public void shiftLayerDown(int amt) {
        layerIndex = Math.max(0, layerIndex-amt);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.black);

        if (model != null) {
            for (int i = 0; i < model[layerIndex].length; i++) {
//                g.fillRect(150 + (int)(10 * model[layerIndex][i][0]), 350 +  (int)(10 * model[layerIndex][i][1]), 5, 5);
                LineSegment line = model[layerIndex][i];
                g.drawLine(250 + (int)(10.0*line.endpoint1[0]), 300 + (int)(10.0*line.endpoint1[1]),
                        250 + (int)(10.0*line.endpoint2[0]), 300 + (int)(10.0*line.endpoint2[1]));
            }
        }

        g.fillRect(WINDOW_WIDTH/2, 0, 10, WINDOW_HEIGHT);



        Font standardFont = new Font("arial", Font.PLAIN, 60);
        g.setFont(standardFont);
        g.drawString("Layer Height: " + layerIndex, (WINDOW_WIDTH/2) + 50, 100);


    }


}
