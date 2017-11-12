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
    protected double evaporationRemains;  // what fraction of pheromone remains (aka Ro)
    protected double evaporationStrength; // what fraction of pheromone is evaporated (1 - Ro)

    public GlobalUpdate(ComponentStructure structure, double evaporationRemains)
    {
        this.structure = structure;
        this.evaporationRemains = evaporationRemains;
    }

    public GlobalUpdate(Problem problem, double evaporationRemains)
    {
        this.structure = problem.structure;
        this.evaporationRemains = evaporationRemains;
        this.evaporationStrength = 1.0 - evaporationRemains;
    }

    public abstract void update(List<Solution> solutions);


    /**
     * Executes linear ro-based evaporation to ALL components of the structure
     * Can be overridden (e.g. in MMAS to check the bounds)
     */
    protected void executeStandardEvaporationAll()
    {
        for (Component component : structure)
            component.setPheromone(evaporationRemains * component.getPheromone());
    }
}
