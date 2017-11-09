package solving.globalUpdate;

import problem.componentStructure.ComponentStructure;
import problem.problemFormulation.Problem;
import solving.solution.Solution;

import java.util.List;

/**
 * Created by Aldar on 09-Nov-17.
 */
public class MinMaxAntSystem extends GlobalUpdate
{
    protected ComponentStructure structure;
    protected double evaporationStrength;    // aka Ro
    protected double tMin, tMax;
    protected double problemSize; // aka-n, aka vertex number



    public MinMaxAntSystem(Problem problem, double evaporationRate)
    {
        super(problem, evaporationRate);
        this.problemSize = problem.ProblemSize();
    }


    @Override
    public void update(List<Solution> solutions)
    {
        executeStandardEvaporationAll();


    }
}
