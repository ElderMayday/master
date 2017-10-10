package solution.selectors;

import org.junit.Test;
import problem.Fleet.FleetDescendingCapacity;
import problem.ProblemFormulation.Problem;
import problem.ProblemFormulation.ProblemVRP;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Aldar on 10-Oct-17.
 */
public class TestProblemVRP
{
    @Test
    public void testProblemVRPLoadInstance()
    {
        Problem problem;

        try
        {
            problem = new ProblemVRP(null, new FleetDescendingCapacity(null), true, true);
            problem.load(new File("problem-samples/vrp-unit-test.json"));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            assertTrue(false);
        }


    }
}
