import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

public class Slicer {

    private double layerHeight;
    private int layerCount;
    private LineSegment[][] slicedModel;

    public Slicer (double lHeight, double mHeight) {
        layerHeight = lHeight;
        layerCount = (int)(mHeight / lHeight);
    }

    public LineSegment[][] slice(Triangle[] model) {
        slicedModel = new LineSegment[layerCount][];

        for (int i = 0; i < layerCount; i++) {
            slicedModel[i] = sliceLayer(model, (i * layerHeight));
        }

        return slicedModel;
    }

    public LineSegment[] sliceLayer (Triangle[] model, double layerZ) {

        ArrayList<LineSegment> lines = new ArrayList<>();

        // Left Right Top Bottom
        double[] bounds = new double[] {Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE, Double.MAX_VALUE};

//        double[][] debugUnorderedEndpoints = new double[model.length * 2][];

        for (int i = 0, n = model.length; i < n; i++) {
            if (model[i].checkLayerPresence(layerZ)) {

                double[][] endpoints = model[i].findEnpoints(layerZ);

                boundsUpdate(endpoints[0], bounds);
                boundsUpdate(endpoints[1], bounds);

                LineSegment nextLine = new LineSegment(endpoints[0], endpoints[1]);
                lines.add(nextLine);
            }
        }

        LineSegment[] perimeter = lines.toArray(new LineSegment[0]);

        LineSegment[] infill = createInfillForLayer(perimeter, 1, bounds);

        LineSegment[] aggregate = new LineSegment[perimeter.length + infill.length];

        for (int i = 0; i < perimeter.length; i++) {
            aggregate[i] = perimeter[i];
        }
        for (int i = 0; i < infill.length; i++) {
            aggregate[i + perimeter.length] = infill[i];
        }

        return aggregate;
    }

    public void boundsUpdate(double[] endpoint, double[] boundsArray) {
        boundsArray[0] = Math.min(boundsArray[0], endpoint[0]);
        boundsArray[1] = Math.max(boundsArray[1], endpoint[0]);

        boundsArray[2] = Math.max(boundsArray[2], endpoint[1]);
        boundsArray[3] = Math.min(boundsArray[3], endpoint[1]);
    }

    public LineSegment[] createInfillForLayer (LineSegment[] slicedLayer, double infillSpacing, double[] bounds) {
        LineSegment[] diagonals = createDiagonalsForInfill(infillSpacing, bounds);

        ArrayList<LineSegment> innerLines = new ArrayList<>();

        // Loop thru every line in diagonals
        // find every intersection the line has with the sliced layer
        // for each intersection, order it in a list of intersections
        // then create new line segments based on the fact that those intersections have to alternate
        for (int i = 0; i < diagonals.length; i++) {
            for (int j = 0; j < slicedLayer.length; j++) {
                boolean firstEndpointAboveLine = false;
                boolean secondEndpointAboveLine = false;

//                if (slicedLayer[j].endpoint1)
            }
        }


    }

    public LineSegment[] createDiagonalsForInfill (double infillSpacing, double[] bounds) {
        double xOrigin = bounds[0];
        double yOrigin = bounds[2];
        boolean inverted = false;

        double increment = 0;

        double length = Math.max(bounds[1] - bounds[0], bounds[3] - bounds[2]);

        ArrayList<LineSegment> infillArrayList = new ArrayList<>();

        while (increment < length) {

            double[] endpoint1 = new double[]{bounds[0], bounds[2] - (increment*infillSpacing)};
            double[] endpoint2 = new double[]{bounds[0] + (increment*infillSpacing), bounds[2]};
            LineSegment infillLine = new LineSegment(endpoint1, endpoint2);

            infillArrayList.add(infillLine);

            increment += infillSpacing;
        }

        while (increment < length * 2) {

            double[] endpoint1 = new double[]{bounds[0] + ((increment-length)*infillSpacing), bounds[2] - (length*infillSpacing)};
            double[] endpoint2 = new double[]{bounds[0] + (length*infillSpacing), bounds[2] - ((increment-length)*infillSpacing)};
            LineSegment infillLine = new LineSegment(endpoint1, endpoint2);

            infillArrayList.add(infillLine);

            increment += infillSpacing;
        }

        return infillArrayList.toArray(new LineSegment[0]);
    }
}
















