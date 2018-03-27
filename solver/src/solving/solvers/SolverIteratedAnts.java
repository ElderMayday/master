package solving.solvers;

import problem.problemFormulation.Problem;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.localSearch.LocalSearchNone;
import solving.localUpdate.LocalUpdate;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.selectors.Selector;
import solving.solution.Solution;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.solvers.iteratedCriteria.IteratedCriteria;
import solving.terminationCriteria.TerminationCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 02-Nov-17.
 */
public class SolverIteratedAnts extends Solver
{
    protected SolutionDestroyer destroyer;
    protected IteratedCriteria criteria;

    public SolverIteratedAnts(Problem problem, Selector selector, GlobalUpdate globalUpdate, LocalUpdate localUpdate,
                              boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializer initializer,
                              LocalSearch localSearch, SolutionDestroyer destroyer, int antNum, IteratedCriteria criteria)
    {
        super(problem, selector, antNum, globalUpdate, localUpdate, localSearch, precomputeValues, terminationCriteria, initializer);

        this.destroyer = destroyer;
        this.antNum = antNum;
        this.criteria = criteria;
    }





    @Override
    public List<Solution> solve() throws Exception
    {
        double minObjective = -1.0;

        terminationCriteria.initialize();

        initializer.initialize(problem.structure);

        List<Solution> solutions = constructSolutionList(antNum);
        List<Solution> newSolutions;

        solutions = executeMultipleLocalSearch(solutions);

        do
        {
            newSolutions = new ArrayList<Solution>();
            for (Solution solution : solutions)
                newSolutions.add(solution.deepCopy());

            for (int index = 0; index < newSolutions.size(); index++)
                newSolutions.set(index, destroyer.destroy(newSolutions.get(index)));

            for (int index = 0; index < newSolutions.size(); index++)
                reconstructOneSolution(newSolutions.get(index));

            newSolutions = executeMultipleLocalSearch(newSolutions);

            solutions = criteria.Decide(solutions, newSolutions);

            globalUpdate.update(solutions);

            if (terminationCriteria.needReinitialize())
                initializer.initialize(problem.structure);
        }
        while (!terminationCriteria.isFulfilled());

        return solutions;
    }
}
