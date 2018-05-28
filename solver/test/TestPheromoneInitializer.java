import org.junit.Test;
import problem.componentStructure.ComponentStructure2d;
import problem.componentStructure.ComponentStructure2dStandard;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.pheromoneInitializer.PheromoneInitializerRange;

import static org.junit.Assert.*;



/**
 * Created by Aldar on 22-Oct-17.
 */
public class TestPheromoneInitializer
{
    @Test
    public void testPheromoneInitializerRange()
    {
        ComponentStructure2d structure2d = new ComponentStructure2dStandard();
        structure2d.allocate(3, 3);

        PheromoneInitializer initializer = new PheromoneInitializerRange(1.5, 2.0);
        initializer.initialize(structure2d);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
            {
                assertTrue(structure2d.get(i, j).getPheromone() >= 1.5);
                assertTrue(structure2d.get(i, j).getPheromone() <= 2.0);
            }
    }
}
