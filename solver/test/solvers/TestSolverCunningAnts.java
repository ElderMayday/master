package solvers;

import org.junit.Test;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.globalUpdate.MinMaxAntSystem;
import solving.globalUpdate.pheromoneTrailSmoothing.PheromoneTrailSmoothingNone;
import solving.localSearch.LocalSearch;
import solving.localSearch.LocalSearchNone;
import solving.localUpdate.LocalUpdate;
import solving.localUpdate.LocalUpdateNone;
import solving.pheromoneInitializer.PheromoneInitializerConstant;
import solving.selectors.Selector;
import solving.selectors.SelectorStandard;
import solving.solution.Solution;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.solutionDestroyer.SolutionDestroyerVrpRandom;
import solving.solvers.Solver;
import solving.solvers.SolverCunningAnts;
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
    public void testSolverCunningAnts()
    {
        try
        {
            Problem problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-10-customers.my"));

            Selector selector = new SelectorStandard(1.0, 1.0);
            TerminationCriteria terminationCriteria = new TerminationCriteriaCounter(5);
            LocalUpdate localUpdate = new LocalUpdateNone();
            LocalSearch localSearch = new LocalSearchNone();
            MinMaxAntSystem update = new MinMaxAntSystem(problem, 0.9, 1.0, new PheromoneTrailSmoothingNone(), true, 1, true, 1);
            PheromoneInitializerConstant initializer = new PheromoneInitializerConstant(10.0);
            SolutionDestroyer destroyer = new SolutionDestroyerVrpRandom(0.5);

            Solver solver = new SolverCunningAnts(problem, selector, localUpdate, true, terminationCriteria, initializer, localSearch, update, 3, destroyer);

            List<Solution> solutions = solver.solve();

            assertEquals(solutions.size(), 3);
            assertEquals(solutions.get(0).getComplete(), true);
            assertEquals(solutions.get(1).getComplete(), true);
            assertEquals(solutions.get(2).getComplete(), true);

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
