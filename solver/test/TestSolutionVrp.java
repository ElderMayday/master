import org.junit.Test;
import problem.component.Component;
import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.solution.SolutionVRP;
import solving.solution.Tour;


import java.io.File;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 14-Oct-17.
 */
public class TestSolutionVrp
{
    @Test
    public void testSolutionVrpIterator()
    {
        Problem problem = null;
        SolutionVRP solution = null;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test.json"));
            solution = new SolutionVRP(problem);


            Component2d c1 = new Component2d(0, 1, 1.0, 1.0);
            Component2d c2 = new Component2d(1, 2, 1.0, 2.0);
            Component2d c3 = new Component2d(2, 3, 1.0, 3.0);

            solution.addConstructionComponent(c1);
            solution.addConstructionComponent(c2);
            solution.addConstructionComponent(c3);

            Iterator<Component> componentIterator = solution.iterator();
            for (int i = 0; i < 3; i++)
            {
                Component component = componentIterator.next();

                if (i == 0)
                    assertEquals(component, c1);

                if (i == 1)
                    assertEquals(component, c2);

                if (i == 2)
                    assertEquals(component, c3);
            }
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }


    @Test
    public void testSolutionVrpObjective()
    {
        Problem problem = null;
        SolutionVRP solution = null;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test.json"));
            solution = new SolutionVRP(problem);


            Component2d c1 = new Component2d(0, 1, 1.0, 1.0);
            c1.setDistance(1.0);

            Component2d c2 = new Component2d(1, 2, 1.0, 2.0);
            c2.setDistance(2.0);

            Component2d c3 = new Component2d(2, 3, 1.0, 3.0);
            c3.setDistance(3.0);

            solution.addConstructionComponent(c1);
            solution.addConstructionComponent(c2);
            solution.addConstructionComponent(c3);

            assertEquals(solution.objective(), 6.0, 0.001);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }

    @Test
    public void testSolutionVrpFilling()
    {
        ProblemVRP problem = null;
        SolutionVRP solution = null;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test.json"));
            solution = new SolutionVRP(problem);

            Component2d c1 = problem.structure2d.get(0, 3);
            Component2d c2 = problem.structure2d.get(3, 2);
            Component2d c3 = problem.structure2d.get(2, 0);

            Component2d c4 = problem.structure2d.get(0, 1);
            Component2d c5 = problem.structure2d.get(1, 0);

            assertEquals(solution.getVisited(0), true);
            assertEquals(solution.getVisited(1), false);
            assertEquals(solution.getVisited(2), false);
            assertEquals(solution.getVisited(3), false);

            solution.addConstructionComponent(c1);
            assertEquals(solution.getVisited(3), true);
            assertEquals(solution.getCurrentTour().getLeftCapacity(), 80.0, 0.001);
            assertEquals(solution.getCurrentTour().getUsedDistance(), 45.0, 0.001);
            assertEquals(solution.getCurrentTour().isFinished(), false);

            solution.addConstructionComponent(c2);
            assertEquals(solution.getVisited(2), true);
            assertEquals(solution.getCurrentTour().isFinished(), false);

            solution.addConstructionComponent(c3);
            assertEquals(solution.getTours().get(0).isFinished(), true);

            assertEquals(solution.getTours().size(), 2);  // one finished and new pre-prepared

            solution.addConstructionComponent(c4);

            Tour tour2 = solution.getTours().get(1);
            assertEquals(tour2.isFinished(), false);
            assertEquals(tour2.getUsedCapacity(), 20.0, 0.001);
            assertEquals(tour2.getUsedDistance(), 30.0, 0.001);
            assertEquals(tour2.getCustomers().get(0).intValue(), 1);
            assertEquals(tour2.isFinished(), false);
            assertEquals(solution.getComplete(), false);

            solution.addConstructionComponent(c5);
            assertEquals(tour2.getCustomers().size(), 1);
            assertEquals(tour2.isFinished(), true);
            assertEquals(tour2.getUsedCapacity(), 20.0, 0.001);
            assertEquals(tour2.getUsedDistance(), 80.0, 0.001);
            assertEquals(solution.getComplete(), true);

            Tour tour1 = solution.getTours().get(0);
            assertEquals(tour1.isFinished(), true);
            assertEquals(tour1.getCustomers().size(), 2);
            assertEquals(tour1.getCustomers().get(0).intValue(), 3);
            assertEquals(tour1.getCustomers().get(1).intValue(), 2);
            assertEquals(tour1.getUsedCapacity(), 70.0, 0.001); // only capacity restriction
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }
}
