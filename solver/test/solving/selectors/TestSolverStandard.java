package solving.selectors;

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
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.pheromoneInitializer.PheromoneInitializerRange;
import solving.solution.Solution;
import solving.solvers.Solver;
import solving.solvers.SolverStandard;
import solving.terminationCriteria.TerminationCriteria;
import solving.terminationCriteria.TerminationCriteriaCounter;

import java.io.File;
import java.util.List;


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

            LocalSearch localSearch = new LocalSearchNone();

            GlobalUpdate update = new AntSystem();

            PheromoneInitializer initializer = new PheromoneInitializerRange(1.0, 2.0);

            Solver solver = new SolverStandard(problem, selector, true, terminationCriteria, initializer, localSearch, update, 3);

            List<Solution> solutions = solver.solve();
        } catch (Exception e)
        {
            e.printStackTrace();
        }



    }
}
