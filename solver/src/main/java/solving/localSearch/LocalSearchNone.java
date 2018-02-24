package solving.localSearch;

import problem.problemFormulation.Problem;
import solving.solution.Solution;

/**
 * Created by Aldar on 16-Oct-17.
 * Stub local search. Does not modify the solving.
 */
public class LocalSearchNone extends LocalSearch
{
    public LocalSearchNone()
    {
        super(null);
    }

    @Override
    public Solution search(Problem problem, Solution solution)
    {
        return solution;
    }
}
