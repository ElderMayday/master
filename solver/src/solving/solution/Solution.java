package solving.solution;

import problem.component.Component;
import problem.problemFormulation.Problem;
import solving.selectors.Selector;

import java.util.List;

/**
 * Created by Aldar on 14-Oct-17.
 */
public abstract class Solution implements Iterable<Component>
{
    protected boolean isComplete;               // Flag: visitedNum == problem customer number
    protected boolean isPartiallyDestroyed;
    public Problem problem;

    /**
     * Consistency not guaranteed, exterior use has to maintain it.
     * Made for optimization sake in local search. Local search computes the new objective without overall recomputing
     */
    public double objective;

    protected double lastObjective;  // objective value before destroying (only effective if destroyed)




    public Solution()
    {
    }

    public Solution(Problem problem)
    {
        this.problem = problem;
        isComplete = false;
        isPartiallyDestroyed = false;
    }




    public abstract void addConstructionComponent(Component component) throws Exception;

    public abstract void addReconstructionComponent(Component component) throws Exception;

    public abstract Solution deepCopy();

    public abstract List<Component> getComponents();

    public abstract int getNumberOfComponents();

    public abstract void recomputeObjective();



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

    public void memorizeLastObjective()
    {
        lastObjective = objective;
    }


    /**
     *
     * @return sum of all solution components pheromone trails
     */
    public double sumPheromone()
    {
        double result = 0.0;

        List<Component> components = getComponents();

        for (Component component : components)
            result += component.getPheromone();

        return result;
    }


    /**
     * Compares this and that solution
     * @param that
     * @return True if is better than that
     */
    public boolean betterThan(Solution that)
    {
        if (problem.lowerIsBetter)
            return this.objective < that.objective;
        else
            return this.objective > that.objective;
    }



    /**
     * Compares this and that solution according to the last captured objectives (useful for partially destroyed solutions comparison)
     * @param that
     * @return True if is better than that
     */
    public boolean betterThanLast(Solution that)
    {
        if (problem.lowerIsBetter)
            return this.lastObjective < that.lastObjective;
        else
            return this.lastObjective > that.lastObjective;
    }



    public static Solution findBestSolution(List<Solution> solutions)
    {
        Solution best = solutions.get(0);

        for (int i = 1; i < solutions.size(); i++)
            if (solutions.get(i).betterThan(best))
                best = solutions.get(i);

        return best;
    }
}
