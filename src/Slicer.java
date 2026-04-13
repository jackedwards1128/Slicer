import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Slicer {

    private double layerHeight;
    private int layerCount;
    private double[][][] slicedModel;

    public Slicer (double lHeight, double mHeight) {
        layerHeight = lHeight;
        layerCount = (int)(mHeight / lHeight);
    }


    public double[][][] slice(Triangle[] model) {
        slicedModel = new double[layerCount][][];

        for (int i = 0; i < layerCount; i++) {
            slicedModel[i] = sliceLayer(model, (i * layerHeight));
        }

        return slicedModel;
    }

    public double[][] sliceLayer (Triangle[] model, double layerZ) {

        ArrayList<LineSegment> lines = new ArrayList<>();

        for (int i = 0, n = model.length; i < n; i++) {
            if (model[i].checkLayerPresence(layerZ)) {

                double[][] endpoints = model[i].findEnpoints(layerZ);

                LineSegment nextLine = new LineSegment(endpoints[0], endpoints[1]);
                lines.add(nextLine);
            }
        }


        double[][] layer = new double[lines.size()][];

        layer[0] = lines.remove(0).endpoint1;

        for (int i = 0, n = lines.size() - 1; i < n; i++) {

            for (int j = 0; j < lines.size(); j++) {
                if (lines.get(j).endpoint2 == layer[i]) {
                    layer[i + 1] = lines.remove(j).endpoint1;
                    break;
                }
            }
        }

        return layer;
    }
}
















