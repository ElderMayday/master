package solution.selectors;

import org.junit.Test;
import problem.ComponentStructure.ComponentStructure2dStandard;
import problem.Fleet.FleetDescendingCapacity;
import problem.Fleet.Vehicle;
import problem.ProblemFormulation.Problem;
import problem.ProblemFormulation.ProblemVRP;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Created by Aldar on 10-Oct-17.
 */
public class TestProblemVRP
{
    @Test
    public void testProblemVRPLoadInstance()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(null), true, true);
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
}
