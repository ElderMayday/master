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
    protected double evaporationStrength;

    public GlobalUpdate(ComponentStructure structure, double evaporationRate)
    {
        this.structure = structure;
        this.evaporationStrength = evaporationRate;
    }

    public GlobalUpdate(Problem problem, double evaporationRate)
    {
        this.structure = problem.structure;
        this.evaporationStrength = evaporationRate;
    }

    public abstract void update(List<Solution> solutions);


    /**
     * Executes linear ro-based evaporation to ALL components of the structure
     */
    protected void executeStandardEvaporationAll()
    {
        for (Component component : structure)
            component.setPheromone(evaporationStrength * component.getPheromone());
    }
}
