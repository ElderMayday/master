package solution.localSearch;

import problem.problemFormulation.Problem;
import solution.Solution;

/**
 * Created by Aldar on 16-Oct-17.
 * Stub local search. Does not modify the solution.
 */
public class LocalSearchNone extends LocalSearch
{
    @Override
    public Solution search(Problem problem, Solution solution)
    {
        return solution;
    }
}
