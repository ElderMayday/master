package solving.selectors;

import org.junit.Test;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.globalUpdate.AntSystem;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.localSearch.LocalSearchNone;
import solving.localUpdate.LocalUpdate;
import solving.localUpdate.LocalUpdateNone;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.pheromoneInitializer.PheromoneInitializerConstant;
import solving.pheromoneInitializer.PheromoneInitializerRange;
import solving.solution.Solution;
import solving.solvers.Solver;
import solving.solvers.SolverCunningAnts;
import solving.solvers.SolverStandard;
import solving.terminationCriteria.TerminationCriteria;
import solving.terminationCriteria.TerminationCriteriaCounter;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 08-Nov-17.
 */
public class TestSolverCunningAnts
{
    @Test
    public void testSolverExternalMemory()
    {
        try
        {
            Problem problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-10-customers.txt"));

            Selector selector = new SelectorStandard(1.0, 1.0);
            TerminationCriteria terminationCriteria = new TerminationCriteriaCounter(5);
            LocalUpdate localUpdate = new LocalUpdateNone();
            LocalSearch localSearch = new LocalSearchNone();
            GlobalUpdate update = new AntSystem(problem, 0.9);
            PheromoneInitializerConstant initializer = new PheromoneInitializerConstant(10.0);

            Solver solver = new SolverCunningAnts(problem, selector, localUpdate, true, terminationCriteria, initializer, localSearch, update, 3);

            List<Solution> solutions = solver.solve();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
