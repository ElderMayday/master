package solving.globalUpdate.pheromoneTrailSmoothing;

import problem.componentStructure.ComponentStructure;
import problem.componentStructure.ComponentStructure2d;

/**
 * Never performs PTS
 * Created by Aldar on 26-Nov-17.
 */
public class PheromoneTrailSmoothingNone extends PheromoneTrailSmoothing
{
    @Override
    public void doIfRequired(ComponentStructure structure, double minP, double maxP)
    {

    }
}
