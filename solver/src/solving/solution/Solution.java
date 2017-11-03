package solving.solution;

import problem.component.Component;
import problem.problemFormulation.Problem;
import solving.selectors.Selector;

/**
 * Created by Aldar on 14-Oct-17.
 */
public abstract class Solution implements Iterable<Component>
{
    protected boolean isComplete;               // Flag: visitedNum == problem customer number
    protected boolean isPartiallyDestroyed;
    protected Problem problem;


    public Solution()
    {
    }

    public Solution(Problem problem)
    {
        this.problem = problem;
        isComplete = false;
        isPartiallyDestroyed = false;
    }

    public abstract void addCurrentTourComponent(Component component) throws Exception;

    public abstract double objective();

    public abstract void reconstruct(Selector selector) throws Exception;  // reconstructs the solution using given selector, if it was partially destroyed before




    public abstract Solution deepCopy();



    public void setComplete(boolean complete)
    {
        isComplete = complete;
    }

    public boolean isPartiallyDestroyed()
    {
        return isPartiallyDestroyed;
    }

    public void setPartiallyDestroyed(boolean partiallyDestroyed)
    {
        isPartiallyDestroyed = partiallyDestroyed;
    }

    public boolean getComplete()
    {
        return isComplete;
    }



    /**
     * Compares this and that solution
     * @param that
     * @return True if is better than that
     */
    public boolean betterThan(Solution that)
    {
        if (problem.lowerIsBetter)
            return this.objective() < that.objective();
        else
            return this.objective() > that.objective();
    }
}
