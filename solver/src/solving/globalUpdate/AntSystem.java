package solving.globalUpdate;

import problem.component.Component;
import problem.componentStructure.ComponentStructure;
import problem.problemFormulation.Problem;
import solving.solution.Solution;

import java.util.List;

/**
 * Created by Aldar on 17-Oct-17.
 */
public class AntSystem extends GlobalUpdate
{
    protected ComponentStructure structure;

    public AntSystem(ComponentStructure structure, double evaporationRate)
    {
        super(structure, evaporationRate);
    }

    public AntSystem(Problem problem, double evaporationRate)
    {
        super(problem, evaporationRate);
    }


    @Override
    public void update(List<Solution> solutions)
    {
        executeStandardEvaporationAll();

        executeDepositAll(solutions);
    }



    protected void executeDepositAll(List<Solution> solutions)
    {
        for (Solution solution : solutions)
        {
            double addedValue = 1.0 / solution.objective();

            for (Component component : solution.getComponents())
                component.setPheromone(component.getPheromone() + addedValue);
        }
    }
}
