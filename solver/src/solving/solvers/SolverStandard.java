package solving.solvers;

import problem.problemFormulation.Problem;
import solving.localUpdate.LocalUpdate;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.solution.Solution;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.selectors.Selector;
import solving.solution.SolutionVRP;
import solving.solution.Tour;
import solving.terminationCriteria.TerminationCriteria;

import java.util.List;

/**
 * Created by Aldar on 16-Oct-17.
 */
public class SolverStandard extends Solver
{
    public SolverStandard(Problem problem, Selector selector, GlobalUpdate globalUpdate, LocalUpdate localUpdate,
                          boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializer initializer,
                          LocalSearch localSearch, int antNum)
    {
        super(problem, selector, antNum, globalUpdate, localUpdate, localSearch, precomputeValues, terminationCriteria, initializer);

        this.antNum = antNum;
    }


    @Override
    public List<Solution> solve() throws Exception
    {
        terminationCriteria.initialize();

        experimentalRun();

        List<Solution> solutions;

        long iteration = 1;

        Solution best = null;

        do
        {
            solutions = constructSolutionList(antNum);

            solutions = executeMultipleLocalSearch(solutions);

            globalUpdate.update(solutions);

            if (terminationCriteria.needReinitialize())
                initializer.initialize(problem.structure);

            /*SolutionVRP currentBest = (SolutionVRP) Solution.findBestSolution(solutions);
            if (iteration % 1000 == 0)  // TODO remove
                System.out.println(iteration + " " + String.format("%1$.3f", currentBest.objective));
            iteration++;*/


            /*SolutionVRP currentBest = (SolutionVRP) Solution.findBestSolution(solutions);

            if (best == null)
                best = currentBest;

            if (currentBest.betterThan(best))
            {
                best = currentBest;
                System.out.println(iteration + " " + String.format("%1$.3f", currentBest.objective));
            }
            iteration++;*/
        }
        while (!terminationCriteria.isFulfilled());

        return solutions;
    }
}
