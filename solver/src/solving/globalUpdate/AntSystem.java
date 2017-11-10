package solving.globalUpdate;

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
        // TO-DO
    }
}
