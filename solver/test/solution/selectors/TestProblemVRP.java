package solution.selectors;

import org.junit.Test;
import problem.Component;
import problem.Component2d;
import problem.ProblemFormulation.Problem;
import problem.ProblemFormulation.ProblemVRP;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
            problem = new ProblemVRP(new File("problem-samples/vrp1.json"), null, null, true, true);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            assertTrue(false);
        }


    }
}
