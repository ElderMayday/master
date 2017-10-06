package solution.selectors;

import org.junit.Test;
import problem.ComponentStructure.*;

import static org.junit.Assert.*;


/**
 * Created by Aldar on 05-Oct-17.
 */
public class TestComponentStructure
{
    @Test
    public void testComponentStructure2dStandartSet()
    {
        ComponentStructure2d structure2d = new ComponentStructure2dStandard(2, 2);

        structure2d.SetT(0, 0, 4.0);
        structure2d.SetH(0, 0, 3.0);

        structure2d.SetH(0, 1, 2.0);

        structure2d.SetT(1, 0, 1.0);

        assertEquals(structure2d.Get(0, 0).getHeuristic(), 3.0, 0.0001);
        assertEquals(structure2d.Get(0, 0).getPheromone(), 4.0, 0.0001);

        assertEquals(structure2d.Get(0, 1).getHeuristic(), 2.0, 0.0001);
        assertEquals(structure2d.Get(0, 1).getPheromone(), 0.0, 0.0001);

        assertEquals(structure2d.Get(1, 0).getHeuristic(), 0.0, 0.0001);
        assertEquals(structure2d.Get(1, 0).getPheromone(), 1.0, 0.0001);

        assertEquals(structure2d.Get(1, 1).getHeuristic(), 0.0, 0.0001);
        assertEquals(structure2d.Get(1, 1).getPheromone(), 0.0, 0.0001);
    }

    @Test
    public void testComponentStructure2dHashSet()
    {
        ComponentStructure2d structure2d = new ComponentStructure2dHash(2, 2);

        structure2d.SetT(0, 0, 4.0);
        structure2d.SetH(0, 0, 3.0);

        structure2d.SetH(0, 1, 2.0);

        structure2d.SetT(1, 0, 1.0);

        assertEquals(structure2d.Get(0, 0).getHeuristic(), 3.0, 0.0001);
        assertEquals(structure2d.Get(0, 0).getPheromone(), 4.0, 0.0001);

        assertEquals(structure2d.Get(0, 1).getHeuristic(), 2.0, 0.0001);
        assertEquals(structure2d.Get(0, 1).getPheromone(), 0.0, 0.0001);

        assertEquals(structure2d.Get(1, 0).getHeuristic(), 0.0, 0.0001);
        assertEquals(structure2d.Get(1, 0).getPheromone(), 1.0, 0.0001);

        assertEquals(structure2d.Get(1, 1).getHeuristic(), 0.0, 0.0001);
        assertEquals(structure2d.Get(1, 1).getPheromone(), 0.0, 0.0001);
    }
}
