package solution.solvers;

import problem.problemFormulation.Problem;
import solution.Solution;
import solution.selectors.Selector;
import solution.terminationCriteria.TerminationCriteria;

import java.util.List;

/**
 * Created by Aldar on 30-Sep-17.
 */
public abstract class Solver
{

    protected Problem problem;
    protected Selector selector;
    protected TerminationCriteria terminationCriteria;


    /**
     *
     * @param problem
     * @param selector
     * @param precomputeValues
     */
    public Solver(Problem problem, Selector selector, boolean precomputeValues, TerminationCriteria terminationCriteria)
    {
        this.problem = problem;
        this.selector = selector;
        this.terminationCriteria = terminationCriteria;

        this.pheromonePreset();

        if (precomputeValues)
            problem.precomputeValues();
    }


    public abstract void pheromonePreset();

    public abstract List<Solution> solve();


}
