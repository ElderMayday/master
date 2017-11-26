package solving.globalUpdate.pheromoneTrailSmoothing;

import problem.componentStructure.ComponentStructure;


/**
 * REMARK!!! Exclusively for MMAS
 * Performs PTS if a certain criteria is true
 * Created by Aldar on 25-Nov-17.
 */
public abstract class PheromoneTrailSmoothing
{
    public abstract void doIfRequired(ComponentStructure structure, double minP, double maxP);
}
