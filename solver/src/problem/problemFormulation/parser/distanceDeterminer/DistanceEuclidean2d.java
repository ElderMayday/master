package problem.problemFormulation.parser.distanceDeterminer;

/**
 * Created by Aldar on 25-May-18.
 */
public class DistanceEuclidean2d implements DistanceDeterminer
{
    @Override
    public double distance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x1 - x2, 2.0) + Math.pow(y1 - y2, 2.0));
    }
}