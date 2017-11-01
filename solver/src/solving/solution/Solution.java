package solving.solution;

import problem.component.Component;
import problem.problemFormulation.Problem;

/**
 * Created by Aldar on 14-Oct-17.
 */
public abstract class Solution implements Iterable<Component>
{
    protected boolean isComplete;               // Flag: visitedNum == problem customer number
    protected Problem problem;


    public Solution()
    {
    }

    public Solution(Problem problem)
    {
        this.problem = problem;
        isComplete = false;
    }

    public abstract void addCurrentTourComponent(Component component) throws Exception;

    public abstract double objective();






    public boolean isComplete()
    {
        return isComplete;
    }
}
