package solving.globalUpdate.pheromoneTrailSmoothing;

import problem.component.Component;
import problem.componentStructure.ComponentStructure;
import problem.componentStructure.ComponentStructure2d;

/**
 * Performs standard PTS if the branching factor exceeds the specified value
 * Created by Aldar on 26-Nov-17.
 */
public class PheromoneTrailSmoothingMMAS extends PheromoneTrailSmoothing
{
    protected double countingThreshold;  // aka lambda
    protected double minRatioToSmooth;   // minimal average fraction of the trails, exceeding the threshold, required to execute smoothing
    protected double elevationParameter;   // aka delta


    public PheromoneTrailSmoothingMMAS(double countingThreshold, double minRatioToSmooth, double elevationParameter)
    {
        this.countingThreshold = countingThreshold;
        this.minRatioToSmooth = minRatioToSmooth;
        this.elevationParameter = elevationParameter;

        if ((countingThreshold > 1.0) || (countingThreshold < 0.0))
            throw new IllegalArgumentException("Lambda parameter must be between 0 and 1");

        if ((minRatioToSmooth > 1.0) || (minRatioToSmooth < 0.0))
            throw new IllegalArgumentException("Min ratio parameter must be between 0 and 1");

        if ((elevationParameter > 1.0) || (elevationParameter < 0.0))
            throw new IllegalArgumentException("Elevation parameter parameter must be between 0 and 1");
    }


    @Override
    public void doIfRequired(ComponentStructure structure, double minP, double maxP)
    {
        double threshold = minP + (maxP - minP) * countingThreshold;

        int count = 0;

        for (Component component : structure)
            if (component.getPheromone() > threshold)
                count++;

        double ratio = (double) count / structure.numberOfComponents();

        if (ratio > minRatioToSmooth)       // execute PTS
        {
            for (Component component : structure)
            {
                double currentPheromone = component.getPheromone();

                component.setPheromone(currentPheromone + elevationParameter * (maxP - currentPheromone));
            }
        }
    }
}
