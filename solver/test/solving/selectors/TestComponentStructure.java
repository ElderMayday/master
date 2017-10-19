package solving.selectors;

import org.junit.Test;
import problem.componentStructure.*;

import static org.junit.Assert.*;


/**
 * Created by Aldar on 05-Oct-17.
 */
public class TestComponentStructure
{
    @Test
    public void testComponentStructure2dStandartSet()
    {
        ComponentStructure2d structure2d = new ComponentStructure2dStandard();
        structure2d.allocate(2, 2);

        structure2d.setT(0, 0, 4.0);
        structure2d.setH(0, 0, 3.0);

        structure2d.setH(0, 1, 2.0);

        structure2d.setT(1, 0, 1.0);

        assertEquals(structure2d.get(0, 0).getHeuristic(), 3.0, 0.0001);
        assertEquals(structure2d.get(0, 0).getPheromone(), 4.0, 0.0001);

        assertEquals(structure2d.get(0, 1).getHeuristic(), 2.0, 0.0001);
        assertEquals(structure2d.get(0, 1).getPheromone(), 0.0, 0.0001);

        assertEquals(structure2d.get(1, 0).getHeuristic(), 1.0, 0.0001);
        assertEquals(structure2d.get(1, 0).getPheromone(), 1.0, 0.0001);

        assertEquals(structure2d.get(1, 1).getHeuristic(), 1.0, 0.0001);
        assertEquals(structure2d.get(1, 1).getPheromone(), 0.0, 0.0001);
    }

    @Test
    public void testComponentStructure2dHashSet()
    {
        ComponentStructure2d structure2d = new ComponentStructure2dHash();
        structure2d.allocate(2, 2);

        structure2d.setT(0, 0, 4.0);
        structure2d.setH(0, 0, 3.0);

        structure2d.setH(0, 1, 2.0);

        structure2d.setT(1, 0, 1.0);

        assertEquals(structure2d.get(0, 0).getHeuristic(), 3.0, 0.0001);
        assertEquals(structure2d.get(0, 0).getPheromone(), 4.0, 0.0001);

        assertEquals(structure2d.get(0, 1).getHeuristic(), 2.0, 0.0001);
        assertEquals(structure2d.get(0, 1).getPheromone(), 0.0, 0.0001);

        assertEquals(structure2d.get(1, 0).getHeuristic(), 1.0, 0.0001);
        assertEquals(structure2d.get(1, 0).getPheromone(), 1.0, 0.0001);

        assertEquals(structure2d.get(1, 1).getHeuristic(), 1.0, 0.0001);
        assertEquals(structure2d.get(1, 1).getPheromone(), 0.0, 0.0001);
    }
}
