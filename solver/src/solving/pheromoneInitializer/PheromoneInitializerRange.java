package solving.pheromoneInitializer;

import general.Main;
import problem.component.Component;
import problem.componentStructure.ComponentStructure;

import java.util.Iterator;

/**
 * Created by Aldar on 22-Oct-17.
 */
public class PheromoneInitializerRange extends PheromoneInitializer
{
    protected double min, max, range;


    public PheromoneInitializerRange(double min, double max)
    {
        this.min = min;
        this.max = max;
        this.range = max - min;

        if (min > max)
            throw new IllegalArgumentException("Min cannot be more than max pheromone");
    }

    @Override
    public void initialize(ComponentStructure structure)
    {
        for (Iterator<Component> iterator = structure.iterator(); iterator.hasNext();)
            iterator.next().setPheromone(min + range * Main.random.nextDouble());
    }
}
