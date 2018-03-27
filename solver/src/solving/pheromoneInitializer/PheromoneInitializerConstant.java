package solving.pheromoneInitializer;

import problem.component.Component;
import problem.componentStructure.ComponentStructure;

import java.util.Iterator;

/**
 * Created by Aldar on 08-Nov-17.
 */
public class PheromoneInitializerConstant extends PheromoneInitializer
{
    protected double value;


    public PheromoneInitializerConstant(double value)
    {
        this.value = value;
    }

    @Override
    public void initialize(ComponentStructure structure)
    {
        for (Iterator<Component> iterator = structure.iterator(); iterator.hasNext();)
            iterator.next().setPheromone(value);
    }
}
