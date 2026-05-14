public class LineSegment {
    public double[] endpoint1;
    public double[] endpoint2;
    public double diagonalYIntercept;
    public boolean isDiagonal;

    public LineSegment (double[] endpoint1, double[] endpoint2) {
        this.endpoint1 = endpoint1;
        this.endpoint2 = endpoint2;
        isDiagonal = false;
    }

    public LineSegment (double[] endpoint1, double[] endpoint2, double diagonalYIntercept) {
        this.endpoint1 = endpoint1;
        this.endpoint2 = endpoint2;
        isDiagonal = true;
        this.diagonalYIntercept = diagonalYIntercept;
    }

    public boolean isPointAboveLine(double[] point) {
        double ptX = point[0];
        double ptY = point[1];

        double slope = (endpoint2[1] - endpoint1[1])/(endpoint2[0] - endpoint1[0]);

        if(ptY - endpoint2[1] > slope * (ptX - endpoint2[0])) {
            return true;
        }
        return false;

    }

    public double[] findIntersectionWithLine(LineSegment line) {
        double[] intersection = new double[2];

        double pt2Y = line.endpoint2[1];
        double pt2X = line.endpoint2[0];

        double my2Y = endpoint2[1];
        double my2X = endpoint2[0];

        double lineSlope = (pt2Y - line.endpoint1[1])/(pt2X - line.endpoint1[0]);
        double mySlope = (endpoint2[1] - endpoint1[1])/(endpoint2[0] - endpoint1[0]);

        intersection[0] = (pt2Y - my2Y + (mySlope*my2X) - (lineSlope*pt2X) )/(mySlope-lineSlope);
        intersection[1] = (mySlope * (intersection[0] - my2X)) + my2Y;

        return intersection;
    }

    public double getLength() {
        return Math.sqrt(Math.pow(endpoint2[1] - endpoint1[1], 2) + Math.pow(endpoint2[0] - endpoint1[0], 2));
    }



}
