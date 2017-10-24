package solving.selectors;

import org.junit.Test;
import problem.component.Component;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.*;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Aldar on 10-Oct-17.
 */
public class TestProblemVRP
{
    @Test
    public void testProblemVrpLoadInstance()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test.json"));

            Iterator<Vehicle> vehicleIterator = problem.fleet.getVehiclesIterator();
            Vehicle vehicle = vehicleIterator.next();

            assertEquals(vehicle.capacity, 120.0, 0.001);
            assertEquals(vehicle.hasLengthRestriction, false);

            assertEquals(problem.getVertexNum(), 4);
            assertEquals(problem.getDemands()[1], 20.0, 0.001);
            assertEquals(problem.getDepotId(), 0);
            assertEquals(problem.structure2d.get(0, 0).getDistance(), -1.0, 0.001);
            assertEquals(problem.structure2d.get(0, 0).getHeuristic(), 1.2, 0.001);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }


    /**
     *  Integration test of how does ProblemVrp define possible components based on the current SolutionVRP
     */
    @Test
    public void testProblemVrpGetNextComponents_WithoutCandidates()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test.json"));

            Solution solution = new SolutionVRP(problem);

            List<Component> candidateList = problem.getNextComponents(solution);

            assertEquals(candidateList.size(), 3);
            assertEquals(candidateList.get(0), problem.structure2d.get(0, 1));
            assertEquals(candidateList.get(1), problem.structure2d.get(0, 2));
            assertEquals(candidateList.get(2), problem.structure2d.get(0, 3));

            solution.addCurrentTourComponent(problem.structure2d.get(0, 2));
            candidateList = problem.getNextComponents(solution);

            assertEquals(candidateList.get(0), problem.structure2d.get(2, 1));
            assertEquals(candidateList.get(1), problem.structure2d.get(2, 3));

            candidateList = problem.getNextComponents(solution);
        } catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
