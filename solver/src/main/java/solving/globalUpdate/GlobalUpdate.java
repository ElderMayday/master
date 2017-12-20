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
        this.evaporationStrength = 1.0 - evaporationRemains;

        if ((evaporationRemains < 0.0) || (evaporationRemains > 1.0))
            throw new IllegalArgumentException("Wrong evaporation factor");
    }

    public GlobalUpdate(Problem problem, double evaporationRemains)
    {
        this.structure = problem.structure;
        this.evaporationRemains = evaporationRemains;
        this.evaporationStrength = 1.0 - evaporationRemains;

        if ((evaporationRemains < 0.0) || (evaporationRemains > 1.0))
            throw new IllegalArgumentException("Wrong evaporation factor");
    }


    /**
     * Performs pheromone update (normally evaporation and deposition)
     * @param solutions The solutions obtained at the last iteration. If one wants to implement global/reset best solutions, one has to memorize those inside of the sub-classes
     */
    public abstract void update(List<Solution> solutions) throws Exception;


    /**
     * Executes linear rho-based evaporation to ALL components of the structure
     * Can be overridden (e.g. in MMAS, AS, RAS, EAS to check the bounds)
     */
    protected void executeStandardEvaporationAll()
    {
        for (Component component : structure)
            component.setPheromone(evaporationRemains * component.getPheromone());
    }
}
