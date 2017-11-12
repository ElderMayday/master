package solving.selectors.solvers;

import org.junit.Test;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.candidateList.CandidateDeterminerVrpSorting;
import solving.globalUpdate.AntSystem;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.localSearch.LocalSearchNone;
import solving.localUpdate.LocalUpdate;
import solving.localUpdate.LocalUpdateNone;
import solving.pheromoneInitializer.*;
import solving.selectors.Selector;
import solving.selectors.SelectorStandard;
import solving.solution.Solution;
import solving.solvers.*;
import solving.terminationCriteria.*;

import java.io.File;
import java.util.List;
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
            Problem problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(2));
            problem.load(new File("problem-samples/vrp-unit-test-2.json"));

            Selector selector = new SelectorStandard(1.0, 1.0);
            TerminationCriteria terminationCriteria = new TerminationCriteriaCounter(5);
            LocalUpdate localUpdate = new LocalUpdateNone();
            LocalSearch localSearch = new LocalSearchNone();
            GlobalUpdate update = new AntSystem(problem, 0.9);
            PheromoneInitializer initializer = new PheromoneInitializerRange(1.0, 2.0);

            Solver solver = new SolverStandard(problem, selector, update, localUpdate, true, terminationCriteria, initializer, localSearch, update, 3);

            List<Solution> solutions = solver.solve();

            assertEquals(solutions.size(), 3);

            assertEquals(solutions.get(0).getComplete(), true);
            assertEquals(solutions.get(1).getComplete(), true);
            assertEquals(solutions.get(2).getComplete(), true);
        } catch (Exception e)
        {
            e.printStackTrace();
        }



    }
}
