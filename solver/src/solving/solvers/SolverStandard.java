package solving.solvers;

import problem.component.Component;
import problem.problemFormulation.Problem;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.solution.Solution;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.selectors.Selector;
import solving.terminationCriteria.TerminationCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 16-Oct-17.
 */
public class SolverStandard extends Solver
{
    protected GlobalUpdate globalUpdate;
    protected int antNum;


    public SolverStandard(Problem problem, Selector selector, boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializer initializer, LocalSearch localSearch, GlobalUpdate update, int antNum)
    {
        super(problem, selector, localSearch, precomputeValues, terminationCriteria, initializer);

        this.globalUpdate = update;
        this.antNum = antNum;
    }


    @Override
    public List<Solution> solve() throws Exception
    {
        List<Solution> solutions;

        initializer.initialize(problem.structure);

        do
        {
            solutions = constructAllSolutions();

            solutions = executeLocalSearch(solutions);

            globalUpdate.update(solutions);
        }
        while (!terminationCriteria.isFullfilled());

        return solutions;
    }

    protected List<Solution> executeLocalSearch(List<Solution> solutions)
    {
        List<Solution> solutionsLocalSearched = new ArrayList<Solution>();

        for (Solution solution : solutions)
            solutionsLocalSearched.add(localSearch.search(problem, solution));

        return solutionsLocalSearched;
    }


    protected List<Solution> constructAllSolutions() throws Exception
    {
        List<Solution> solutions = new ArrayList<Solution>();

        for (int i = 0; i < antNum; i++)
            solutions.add(constructOneSolution());

        return solutions;
    }
}
