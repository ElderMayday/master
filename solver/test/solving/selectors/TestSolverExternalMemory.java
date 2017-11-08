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
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.pheromoneInitializer.PheromoneInitializerRange;
import solving.solution.Solution;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.solutionDestroyer.SolutionDestroyerVrpRandom;
import solving.solvers.Solver;
import solving.solvers.SolverExternalMemory;
import solving.terminationCriteria.TerminationCriteria;
import solving.terminationCriteria.TerminationCriteriaCounter;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by Aldar on 08-Nov-17.
 */
public class TestSolverExternalMemory
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
            LocalSearch localSearch = new LocalSearchNone();
            GlobalUpdate update = new AntSystem();
            PheromoneInitializer initializer = new PheromoneInitializerRange(1.0, 2.0);
            SolutionDestroyer destroyer = new SolutionDestroyerVrpRandom(0.5);

            Solver solver = new SolverExternalMemory(problem, selector, true, terminationCriteria, initializer, localSearch, destroyer, update, 5, 5, 2);

            List<Solution> solutions = solver.solve();

            int a = 1;

        } catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
