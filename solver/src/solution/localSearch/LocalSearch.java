package solution.localSearch;

import problem.problemFormulation.Problem;
import solution.Solution;

/**
 * Created by Aldar on 16-Oct-17.
 */
public abstract class LocalSearch
{
    public abstract Solution search(Problem problem, Solution solution);
}
