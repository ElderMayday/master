import org.junit.Test;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.solutionDestroyer.SolutionDestroyerVrpByTour;
import solving.solutionDestroyer.SolutionDestroyerVrpFixed;
import solving.solutionDestroyer.SolutionDestroyerVrpRandom;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 02-Nov-17.
 */
public class TestSolutionDestroyer
{
    @Test
    public void testSolutionDestroyerVrpRandom()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test.json"));

            SolutionVRP solutionVRP = new SolutionVRP(problem);

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 2));
            solutionVRP.addConstructionComponent(problem.structure2d.get(2, 1));
            solutionVRP.addConstructionComponent(problem.structure2d.get(1, 0));

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 3));
            solutionVRP.addConstructionComponent(problem.structure2d.get(3, 0));

            SolutionDestroyer destroyer = new SolutionDestroyerVrpRandom(1.0);

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

            // components should have also been removed

            assertTrue(!solutionVRP.getComponents2d().contains(problem.structure2d.get(0, 3)));
            assertTrue(!solutionVRP.getComponents2d().contains(problem.structure2d.get(3, 0)));

            assertTrue(!solutionVRP.getComponents2d().contains(problem.structure2d.get(1, 0)));
            assertTrue(!solutionVRP.getComponents2d().contains(problem.structure2d.get(2, 1)));
        } catch (Exception e)
        {
            assertTrue(false);
        }
    }

    @Test
    public void testSolutionDestroyerVrpFixed()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test.json"));

            SolutionVRP solutionVRP = new SolutionVRP(problem);

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 2));
            solutionVRP.addConstructionComponent(problem.structure2d.get(2, 1));
            solutionVRP.addConstructionComponent(problem.structure2d.get(1, 0));

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 3));
            solutionVRP.addConstructionComponent(problem.structure2d.get(3, 0));

            SolutionDestroyer destroyer = new SolutionDestroyerVrpFixed(1);

            solutionVRP = (SolutionVRP) destroyer.destroy(solutionVRP);

            assertEquals(solutionVRP.getVisitedNum(), 3);
            assertEquals(solutionVRP.getVisited(0), true);
            assertEquals(solutionVRP.getVisited(1), false);
            assertEquals(solutionVRP.getVisited(2), true);
            assertEquals(solutionVRP.getVisited(3), true);

            assertEquals(solutionVRP.getTours().size(), 2);

            List<Tour> tours = solutionVRP.getTours();

            assertEquals(tours.get(0).getCustomers().size(), 1);
            assertEquals(tours.get(1).getCustomers().size(), 1);

            assertEquals(tours.get(0).isFinished(), false);
            assertEquals(tours.get(1).isFinished(), true);

            assertEquals(solutionVRP.getComponents2d().size(), 3);
            assertEquals(solutionVRP.getComponents2d().get(0), problem.structure2d.get(0, 2));
            assertEquals(solutionVRP.getComponents2d().get(1), problem.structure2d.get(0, 3));
            assertEquals(solutionVRP.getComponents2d().get(2), problem.structure2d.get(3, 0));
        } catch (Exception e)
        {
            assertTrue(false);
        }
    }

    @Test
    public void testSolutionDestroyerVrpByTour()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test.json"));

            SolutionVRP solutionVRP = new SolutionVRP(problem);

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 2));
            solutionVRP.addConstructionComponent(problem.structure2d.get(2, 1));
            solutionVRP.addConstructionComponent(problem.structure2d.get(1, 0));

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 3));
            solutionVRP.addConstructionComponent(problem.structure2d.get(3, 0));

            SolutionDestroyer destroyer = new SolutionDestroyerVrpByTour();

            solutionVRP = (SolutionVRP) destroyer.destroy(solutionVRP);

            Tour tour = solutionVRP.getTours().get(0);

            if (tour.getCustomers().size() == 2)  // {2-1} is left
            {
                assertEquals(solutionVRP.getVisitedNum(), 3);
                assertEquals(solutionVRP.getVisited(0), true);
                assertEquals(solutionVRP.getVisited(1), true);
                assertEquals(solutionVRP.getVisited(2), true);
                assertEquals(solutionVRP.getVisited(3), false);

                assertEquals(tour.getLeftDistance(), 0.0, 0.001);
                assertEquals(tour.getLeftCapacity(), 70.0, 0.001);

                assertEquals(solutionVRP.getComponents2d().size(), 3);
                assertEquals(solutionVRP.getComponents2d().get(0), problem.structure2d.get(0, 2));
                assertEquals(solutionVRP.getComponents2d().get(1), problem.structure2d.get(2, 1));
                assertEquals(solutionVRP.getComponents2d().get(2), problem.structure2d.get(1, 0));
            }
            else   // {1} is left
            {
                assertEquals(solutionVRP.getVisitedNum(), 2);
                assertEquals(solutionVRP.getVisited(0), true);
                assertEquals(solutionVRP.getVisited(1), false);
                assertEquals(solutionVRP.getVisited(2), false);
                assertEquals(solutionVRP.getVisited(3), true);

                assertEquals(tour.getLeftDistance(),  22.0, 0.001);
                assertEquals(tour.getLeftCapacity(), 60.0, 0.001);

                assertEquals(solutionVRP.getComponents2d().size(), 2);
                assertEquals(solutionVRP.getComponents2d().get(0), problem.structure2d.get(0, 3));
                assertEquals(solutionVRP.getComponents2d().get(1), problem.structure2d.get(3, 0));
            }

            assertEquals(tour.isFinished(), true);
            assertEquals(solutionVRP.getComplete(), false);
            assertEquals(solutionVRP.isPartiallyDestroyed(), true);

            int a = 1;
        } catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
