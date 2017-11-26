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
    protected Solution globalBest;


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

        if (globalBest == null)
        {
            globalBest = solutions.get(0);

            for (int index = 1; index < solutions.size(); index++)
                if (solutions.get(index).betterThan(globalBest))
                    globalBest = solutions.get(index);
        }
        else
        {
            for (Solution solution : solutions)
                if (solution.betterThan(globalBest))
                    globalBest = solution;
        }

        for (Component component : globalBest.getComponents())
            component.setPheromone(this.evaporationRemains * component.getPheromone() + this.evaporationStrength / globalBest.objective());
    }
}
