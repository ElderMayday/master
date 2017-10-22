package solving.pheromoneInitializer;

import problem.componentStructure.ComponentStructure;

import java.util.Random;

/**
 * Created by Aldar on 22-Oct-17.
 */
public abstract class PheromoneInitializer
{
    protected Random random;

    public PheromoneInitializer()
    {
        random = new Random();
    }


    public abstract void initialize(ComponentStructure structure);
}
