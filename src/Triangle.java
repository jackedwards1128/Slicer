public class Triangle {
    private double[][] vertices;

    public Triangle(double[][] vertices) {
        this.vertices = vertices;
    }


    public boolean checkLayerPresence (double layerZ) {

        boolean vertexBelowLayer = false;
        boolean vertexAboveLayer = false;

        for(int i = 0; i < 3; i++) {
            if (vertices[i][2] > layerZ)
                vertexAboveLayer = true;
            else
                vertexBelowLayer = true;
        }

        if (vertexAboveLayer && vertexBelowLayer) {
            return true;
        }

        return false;
    }


    public double[][] findEnpoints (double layerZ) {
        double[][] endpoints = new double[2][3];

        int loneVertexIndex = -1;

        boolean[] vertexAboveLayer = new boolean[3];
        int verticesAbove = 0;

        for(int i = 0; i < 3; i++) {
            if (vertices[i][2] > layerZ) {
                vertexAboveLayer[i] = true;
                verticesAbove++;
            } else
                vertexAboveLayer[i] = false;
        }

        // If there's only one vertex above the layer
        if (verticesAbove == 1) {
            // Find the vertex above layer and set loneVertexIndex
            for(int i = 0; i < 3; i++) {
                if (vertexAboveLayer[i]) {
                    loneVertexIndex = i;
                    break;
                }
            }
            // If there's only one vertex below the layer
        } else {
            // Find the vertex below layer and set loneVertexIndex
            for(int i = 0; i < 3; i++) {
                if (!vertexAboveLayer[i]) {
                    loneVertexIndex = i;
                    break;
                }
            }
        }

        int used = -1;

//        System.out.println("lone vertex index: " + loneVertexIndex);

        int[] unlonelyVertices;

        switch (loneVertexIndex) {
            case 0:
                unlonelyVertices = new int[]{1, 2};
                break;
            case 1:
                unlonelyVertices = new int[]{0, 2};
                break;
            default:
                unlonelyVertices = new int[]{0, 1};
                break;
        }

        for (int i = 0; i < 2; i++) {

            endpoints[i] = findIntersection(vertices[unlonelyVertices[i]], vertices[loneVertexIndex], layerZ);
//            System.out.println("endpoint " + i + ": lone=" + loneVertexIndex + "  second=" + unlonelyVertices[i]);
        }

        return endpoints;
    }

    public double[] findIntersection(double[] vertex1, double[] vertex2, double layerZ) {
        double interpolationValue = (layerZ - vertex1[2])/(vertex2[2] - vertex1[2]);
//        System.out.println("interp value: (" + layerZ + " - " + vertex1[2] + ")/(" + vertex2[2] + " - " + vertex1[2] + ")" );

        double x = ((vertex2[0] - vertex1[0]) * interpolationValue) + vertex1[0];
        double y = ((vertex2[1] - vertex1[1]) * interpolationValue) + vertex1[1];

        return new double[] {x, y};
    }
}













