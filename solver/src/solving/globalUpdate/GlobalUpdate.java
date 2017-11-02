package solving.globalUpdate;

import problem.problemFormulation.Problem;
import solving.solution.Solution;

import java.util.List;

/**
 * Created by Aldar on 16-Oct-17.
 */
public abstract class GlobalUpdate
{
    protected Problem problem;

    public abstract void update(List<Solution> solutions);
}
