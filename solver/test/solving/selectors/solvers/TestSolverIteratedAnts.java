package solving.selectors.solvers;

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
import solving.pheromoneInitializer.PheromoneInitializerRange;
import solving.selectors.Selector;
import solving.selectors.SelectorStandard;
import solving.solution.Solution;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.solutionDestroyer.SolutionDestroyerVrpRandom;
import solving.solvers.iteratedCriteria.IteratedCriteriaBest;
import solving.solvers.Solver;
import solving.solvers.SolverIteratedAnts;
import solving.terminationCriteria.TerminationCriteria;
import solving.terminationCriteria.TerminationCriteriaCounter;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 03-Nov-17.
 */
public class TestSolverIteratedAnts
{

    @Test
    public void testSolverStandard()
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
            PheromoneInitializer initializer = new PheromoneInitializerRange(1.0, 2.0);
            SolutionDestroyer destroyer = new SolutionDestroyerVrpRandom(0.5);

            Solver solver = new SolverIteratedAnts(problem, selector, update, localUpdate, true, terminationCriteria, initializer, localSearch, destroyer, 5, true, new IteratedCriteriaBest());

            List<Solution> solutions = solver.solve();

            assertEquals(solutions.size(), 5);
            assertEquals(solutions.get(0).getComplete(), true);
            assertEquals(solutions.get(1).getComplete(), true);
            assertEquals(solutions.get(2).getComplete(), true);
            assertEquals(solutions.get(3).getComplete(), true);
            assertEquals(solutions.get(4).getComplete(), true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
