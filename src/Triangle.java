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

        for (int i = 0; i < 2; i++) {
            int secondVertexIndex = 0;

            while (secondVertexIndex != loneVertexIndex && secondVertexIndex != used) {
                secondVertexIndex++;

                if (secondVertexIndex == 3)
                    secondVertexIndex = 0;
            }

            used = secondVertexIndex;

            endpoints[i] = findIntersection(vertices[loneVertexIndex], vertices[secondVertexIndex], layerZ);
        }

        return endpoints;
    }

    public double[] findIntersection(double[] vertex1, double[] vertex2, double layerZ) {
        double interpolationValue = (layerZ - vertex1[2])/(vertex2[2] - vertex1[2]);

        double x = ((vertex2[0] - vertex1[0]) * interpolationValue) + vertex1[0];
        double y = ((vertex2[1] - vertex1[1]) * interpolationValue) + vertex1[0];

        return new double[] {x, y};
    }
}













