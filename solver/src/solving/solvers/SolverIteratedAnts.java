package solving.solvers;

import problem.problemFormulation.Problem;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.selectors.Selector;
import solving.solution.Solution;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.terminationCriteria.TerminationCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 02-Nov-17.
 */
public class SolverIteratedAnts extends Solver
{
    protected SolutionDestroyer destroyer;


    public SolverIteratedAnts(Problem problem, Selector selector, boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializer initializer, LocalSearch localSearch, SolutionDestroyer destroyer)
    {
        super(problem, selector, localSearch, precomputeValues, terminationCriteria, initializer);

        this.destroyer = destroyer;
    }





    @Override
    public List<Solution> solve() throws Exception
    {
        List<Solution> solutions = new ArrayList<Solution>();

        return solutions;
    }
}
