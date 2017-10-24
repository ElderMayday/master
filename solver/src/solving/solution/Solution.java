package solving.solution;

import problem.component.Component;
import problem.problemFormulation.Problem;

/**
 * Created by Aldar on 14-Oct-17.
 */
public abstract class Solution implements Iterable<Component>
{
    public Solution()
    {

    }

    public Solution(Problem problem)
    {
    }

    public abstract void addCurrentTourComponent(Component component) throws Exception;

    public abstract double objective();
}
