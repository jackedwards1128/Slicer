import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class Program implements ActionListener, KeyListener {

    private Slicer slicer;
    private Window window;

    public Program(byte[] data)  {
        ByteBuffer buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);

        // The first 80 bytes are the header which we don't care about

        buf.position(80);
        int numTriangles = buf.getInt();

        System.out.println("Total triangle count: " + numTriangles);

        Triangle[] model;

        model = new Triangle[numTriangles];

        double highestPoint = -1;

        for (int i = 0; i < numTriangles; i++) {
            double[][] vertices = new double[3][3];

            // Burn the first 12 bytes which represent the normal vector, which we do not need
            for (int j = 0; j < 3; j++) {
                buf.getFloat();
            }

            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    vertices[j][k] = buf.getFloat();
                    if (k == 2) {
                        if (vertices[j][k] > highestPoint) {
                            highestPoint = vertices[j][k];
                        }
                    }
                }
            }

            // Burn the last 2 bytes which represent the attribute bytes
            buf.getChar();

            model[i] = new Triangle(vertices);
        }

        slicer = new Slicer(0.3, highestPoint);

        LineSegment[][] sliced = slicer.slice(model);

        window = new Window(this);

        window.addKeyListener(this);

//        System.out.println("sliced length: " + sliced.length);
//        System.out.println("sliced[30] length: " + sliced[30].length);
//        System.out.println("sliced[30][10] length: " + sliced[30][10].length);

//        for (int i = 0; i < sliced[30].length; i++) {
//            for (int j = 0; j < sliced[30][i].length; j++) {
//                System.out.print(sliced[30][i][j] + " ");
//            }
//            System.out.println();
//        }

        window.setModel(sliced);
        window.setLayerIndex(45);
        window.repaint();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Program started");

        if (args.length != 1) {
            System.out.println("\n!!ERROR!! Usage: java Program.java <file>\n");
        }

        String fileName = args[0];

        byte[] data = Files.readAllBytes(Path.of(fileName));

        Program newProgram = new Program(data);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key");
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                window.shiftLayerUp(1);
                break;
            case KeyEvent.VK_DOWN:
                window.shiftLayerDown(1);
                break;
        }
        window.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
