import org.junit.Test;
import problem.componentStructure.ComponentStructure2d;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;


import java.io.File;

import static org.junit.Assert.*;


/**
 * Created by Aldar on 16-Feb-18.
 */
public class TestDoubleBridge
{
    @Test
    public void testDoubleBridge_NoLengthLimit()
    {
        try
        {
            ProblemVRP problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-10-customers-no-length.txt"));

            SolutionVRP solution = new SolutionVRP(problem);
            ComponentStructure2d structure2d = problem.structure2d;
            solution.addConstructionComponent(structure2d.get(0, 1));
            solution.addConstructionComponent(structure2d.get(1, 2));
            solution.addConstructionComponent(structure2d.get(2, 3));
            solution.addConstructionComponent(structure2d.get(3, 4));
            solution.addConstructionComponent(structure2d.get(4, 5));
            solution.addConstructionComponent(structure2d.get(5, 6));
            solution.addConstructionComponent(structure2d.get(6, 7));
            solution.addConstructionComponent(structure2d.get(7, 8));
            solution.addConstructionComponent(structure2d.get(8, 9));
            solution.addConstructionComponent(structure2d.get(9, 10));
            solution.addConstructionComponent(structure2d.get(10, 0));

            int a = 1;
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }
}
