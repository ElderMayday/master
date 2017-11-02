package solving.selectors;

import org.junit.Test;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.ProblemVRP;
import solving.solution.SolutionVRP;
import solving.solution.Tour;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.solutionDestroyer.SolutionDestroyerVRP;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 02-Nov-17.
 */
public class TestSolutionDestroyer
{
    @Test
    public void testSolutionDestroyer()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test.json"));

            SolutionVRP solutionVRP = new SolutionVRP(problem);

            solutionVRP.addCurrentTourComponent(problem.structure2d.get(0, 2));
            solutionVRP.addCurrentTourComponent(problem.structure2d.get(2, 1));
            solutionVRP.addCurrentTourComponent(problem.structure2d.get(1, 0));

            solutionVRP.addCurrentTourComponent(problem.structure2d.get(0, 3));
            solutionVRP.addCurrentTourComponent(problem.structure2d.get(3, 0));

            SolutionDestroyer destroyer = new SolutionDestroyerVRP(1.0);

            solutionVRP = (SolutionVRP) destroyer.destroy(solutionVRP);

            assertEquals(solutionVRP.getVisited(1), false);
            assertEquals(solutionVRP.getVisited(3), false);

            assertEquals(solutionVRP.getTours().size(), 2);

            List<Tour> tours = solutionVRP.getTours();

            assertTrue(tours.get(0).getCustomers().size() < 2);
            assertEquals(tours.get(1).getCustomers().size(), 0);

            assertEquals(tours.get(0).isFinished(), false);
            assertEquals(tours.get(1).isFinished(), false);

            assertEquals(tours.get(1).getLeftCapacity(), 100.0, 0.001);
            assertEquals(tours.get(1).getLeftDistance(), 100.0, 0.001);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }
}
