package solution.selectors;

import org.junit.Test;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solution.globalUpdate.AntSystem;
import solution.globalUpdate.GlobalUpdate;
import solution.localSearch.LocalSearch;
import solution.localSearch.LocalSearchNone;
import solution.solvers.Solver;
import solution.solvers.SolverStandard;
import solution.terminationCriteria.TerminationCriteria;
import solution.terminationCriteria.TerminationCriteriaCounter;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;


/**
 * Created by Aldar on 17-Oct-17.
 */
public class TestSolverStandard
{
    @Test
    public void testSolverStandard()
    {
        try
        {
            Problem problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(null), true);
            problem.load(new File("problem-samples/vrp-unit-test.json"));


            Selector selector = new SelectorStandard(1.0, 1.0);

            TerminationCriteria terminationCriteria = new TerminationCriteriaCounter(5);

            LocalSearch localSearch = new LocalSearchNone();

            GlobalUpdate update = new AntSystem();

            Solver solver = new SolverStandard(problem, selector, true, terminationCriteria, localSearch, update, 3);

            solver.solve();
        } catch (Exception e)
        {
            e.printStackTrace();
        }



    }
}
