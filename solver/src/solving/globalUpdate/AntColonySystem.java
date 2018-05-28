package solving.globalUpdate;

import problem.component.Component;
import problem.componentStructure.ComponentStructure;
import problem.problemFormulation.Problem;
import solving.solution.Solution;

import java.util.List;

/**
 * REMARK! In classic implementation must be used with SelectorStandard or SelectorDorigo, and LocalUpdate
 * However it must be ensured when one creates Solver
 * Created by Aldar on 12-Nov-17.
 */
public class AntColonySystem extends GlobalUpdate
{
    public AntColonySystem(ComponentStructure structure, double evaporationRemains)
    {
        super(structure, evaporationRemains);
    }

    public AntColonySystem(Problem problem, double evaporationRemains)
    {
        super(problem, evaporationRemains);
    }


    @Override
    public void update(List<Solution> solutions)
    {
        // no total evaporation is applied

        executeAcsUpdate(solutions);
    }


    /**
     * Evaporates and deposits the global-best components according to the ACS rule
     * @param solutions
     */
    protected void executeAcsUpdate(List<Solution> solutions)
    {
        // determine the global best

        determineBest(solutions);

        for (Component component : best.getComponents())
            component.setPheromone(this.evaporationRemains * component.getPheromone() + this.evaporationStrength / best.objective);
    }
}
