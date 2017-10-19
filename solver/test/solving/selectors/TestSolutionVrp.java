package solving.selectors;

import org.junit.Test;
import problem.component.Component;
import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.fleet.Vehicle;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
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

        Component2d c1 = new Component2d(0, 3, 1.0, 1.0);
        c1.setDistance(1.0);

        Component2d c2 = new Component2d(3, 2, 1.0, 2.0);
        c2.setDistance(2.0);

        Component2d c3 = new Component2d(2, 1, 1.0, 3.0);
        c3.setDistance(3.0);

        Vehicle vehicle1 = new Vehicle(10.0, 10.0, true);
        Vehicle vehicle2 = new Vehicle(20.0, 20.0, true);

        assertEquals(solution.getVisited(0), true);
        assertEquals(solution.getVisited(1), false);
        assertEquals(solution.getVisited(2), false);
        assertEquals(solution.getVisited(3), false);

        solution.startNewTour(vehicle1);

        solution.addComponent(c1);
        assertEquals(solution.getVisited(3), true);

        solution.addComponent(c2);
        assertEquals(solution.getVisited(2), true);

        solution.startNewTour(vehicle2);

        solution.addComponent(c3);
        assertEquals(solution.getVisited(1), true);

        assertEquals(solution.getTours().size(), 2);

        Tour tour1 = solution.getTours().get(0);
        assertEquals(tour1.isFinished(), true);
        assertEquals(tour1.getVehicle(), vehicle1);
        assertEquals(tour1.getCustomers().get(0).intValue(), 3);
        assertEquals(tour1.getCustomers().get(1).intValue(), 2);

        Tour tour2 = solution.getTours().get(1);
        assertEquals(tour2.isFinished(), false);
        assertEquals(tour2.getVehicle(), vehicle2);

        solution.finishCurrentTour();
        assertEquals(tour2.getCustomers().get(0).intValue(), 1);
        assertEquals(tour2.isFinished(), true);
    }
}
