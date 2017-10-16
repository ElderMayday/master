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
    protected boolean precomputeHeuristic;
    protected TerminationCriteria terminationCriteria;


    /**
     *
     * @param problem
     * @param selector
     * @param precomputeHeuristic
     */
    public Solver(Problem problem, Selector selector, boolean precomputeHeuristic, TerminationCriteria terminationCriteria)
    {
        this.problem = problem;
        this.selector = selector;
        this.precomputeHeuristic = precomputeHeuristic;
        this.terminationCriteria = terminationCriteria;
    }




    public abstract List<Solution> solve();

}
