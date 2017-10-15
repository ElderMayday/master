package solution.solvers;

import problem.problemFormulation.Problem;
import solution.selectors.Selector;

/**
 * Created by Aldar on 30-Sep-17.
 */
public abstract class Solver
{

    protected Problem problem;

    protected Selector selector;

    protected boolean precomputeHeuristic;


    /**
     *
     * @param problem
     * @param selector
     * @param precomputeHeuristic
     */
    public Solver(Problem problem, Selector selector, boolean precomputeHeuristic, double alpha, double beta)
    {
        this.problem = problem;
        this.selector = selector;
        this.precomputeHeuristic = precomputeHeuristic;
    }



}
