package solving.globalUpdate;

import problem.component.Component;
import problem.componentStructure.ComponentStructure;
import problem.problemFormulation.Problem;
import solving.solution.Solution;

import java.util.List;

/**
 * Created by Aldar on 16-Oct-17.
 */
public abstract class GlobalUpdate
{
    protected ComponentStructure structure;
    protected double evaporationStrength;  // what fraction of pheromone remains

    public GlobalUpdate(ComponentStructure structure, double evaporationRate)
    {
        this.structure = structure;
        this.evaporationStrength = evaporationRate;
    }

    public GlobalUpdate(Problem problem, double evaporationStrength)
    {
        this.structure = problem.structure;
        this.evaporationStrength = evaporationStrength;
    }

    public abstract void update(List<Solution> solutions);


    /**
     * Executes linear ro-based evaporation to ALL components of the structure
     * Can be overridden (e.g. in MMAS to check the bounds)
     */
    protected void executeStandardEvaporationAll()
    {
        for (Component component : structure)
            component.setPheromone(evaporationStrength * component.getPheromone());
    }
}
