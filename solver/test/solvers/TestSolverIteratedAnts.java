package solvers;

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
    public void testSolverIteratedAnts()
    {
        try
        {
            Problem problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-10-customers.my"));

            Selector selector = new SelectorStandard(1.0, 1.0);
            TerminationCriteria terminationCriteria = new TerminationCriteriaCounter(3);
            LocalUpdate localUpdate = new LocalUpdateNone();
            LocalSearch localSearch = new LocalSearchNone();
            GlobalUpdate update = new AntSystem(problem, 0.9, false, 0.0);
            PheromoneInitializer initializer = new PheromoneInitializerRange(1.0, 2.0);
            SolutionDestroyer destroyer = new SolutionDestroyerVrpRandom(0.5);

            Solver solver = new SolverIteratedAnts(problem, selector, update, localUpdate, true, terminationCriteria, initializer, localSearch, destroyer, 5, new IteratedCriteriaBest());

            List<Solution> solutions = solver.solve();

            assertEquals(solutions.size(), 5);
            assertEquals(solutions.get(0).getComplete(), true);
            assertEquals(solutions.get(1).getComplete(), true);
            assertEquals(solutions.get(2).getComplete(), true);
            assertEquals(solutions.get(3).getComplete(), true);
            assertEquals(solutions.get(4).getComplete(), true);

            double objective = solutions.get(0).objective;    // black-box check whether the new objective was computed after copying
            solutions.get(0).recomputeObjective();
            assertEquals(objective, solutions.get(0).objective, 0.0001);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
