import general.Main;
import org.junit.Before;
import org.junit.Test;
import problem.componentStructure.ComponentStructure2d;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.perturbation.DoubleBridge;
import solving.perturbation.Perturbation;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;


import java.io.File;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


/**
 * Created by Aldar on 16-Feb-18.
 */
public class TestDoubleBridge
{
    @Before
    public void init() {
        Main.random = new Random();
    }

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

            Perturbation perturbation = new DoubleBridge();

            solution = (SolutionVRP) perturbation.perturbate(solution);

            assertEquals(solution.getTours().size(), 1);

            Tour tour = solution.getTours().get(0);
            List<Integer> customers = tour.getCustomers();

            assertEquals(customers.size(), 12);

            boolean[] visited = new boolean[customers.size() - 1];

            for (Integer i : customers)
                visited[i] = true;

            for (Integer i : customers)
                assertTrue(visited[i]);

            int a = 1;
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }
}
