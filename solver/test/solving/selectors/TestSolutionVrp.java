package solving.selectors;

import org.junit.Test;
import problem.component.Component;
import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;


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
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), true);
            problem.load(new File("problem-samples/vrp-unit-test.json"));
            solution = new SolutionVRP(problem);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }

        Component2d c1 = new Component2d(0, 0, 1.0, 1.0);
        Component2d c2 = new Component2d(0, 0, 1.0, 2.0);
        Component2d c3 = new Component2d(0, 0, 1.0, 3.0);

        solution.startNewTour(null);
        solution.addComponent(c1);
        solution.addComponent(c2);
        solution.addComponent(c3);

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


    @Test
    public void testSolutionVrpObjective()
    {
        Problem problem = null;
        SolutionVRP solution = null;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), true);
            problem.load(new File("problem-samples/vrp-unit-test.json"));
            solution = new SolutionVRP(problem);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }

        Component2d c1 = new Component2d(0, 0, 1.0, 1.0);
        c1.setDistance(1.0);

        Component2d c2 = new Component2d(0, 0, 1.0, 2.0);
        c2.setDistance(2.0);

        Component2d c3 = new Component2d(0, 0, 1.0, 3.0);
        c3.setDistance(3.0);

        solution.startNewTour(null);
        solution.addComponent(c1);
        solution.addComponent(c2);
        solution.addComponent(c3);

        assertEquals(solution.objective(), 6.0, 0.001);
    }

    @Test
    public void testSolutionVrpFilling()
    {

    }
}
