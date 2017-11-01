package solving.solvers;

import problem.problemFormulation.Problem;
import solving.localSearch.LocalSearch;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.solution.Solution;
import solving.selectors.Selector;
import solving.terminationCriteria.TerminationCriteria;

import java.util.List;

/**
 * Created by Aldar on 30-Sep-17.
 */
public abstract class Solver
{

    protected Problem problem;
    protected Selector selector;
    protected TerminationCriteria terminationCriteria;
    protected LocalSearch localSearch;
    protected PheromoneInitializer initializer;


    /**
     *
     * @param problem
     * @param selector
     * @param precomputeValues
     */
    public Solver(Problem problem, Selector selector, LocalSearch localSearch, boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializer initializer)
    {
        this.problem = problem;
        this.selector = selector;
        this.terminationCriteria = terminationCriteria;
        this.localSearch = localSearch;
        this.initializer = initializer;

        if (precomputeValues)
            problem.precomputeValues();
    }

    public abstract List<Solution> solve() throws Exception;


}
