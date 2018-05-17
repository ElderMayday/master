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
    protected boolean isBounded;        // defines whether min and max bounds are applied
    protected double kFactor;           // aka k,    min = max / (k x problemSize)

    protected double maxToMinFactor;    // min = max x maxToMinFactor,   maxToMinFactor = 1 / (k x problemSize)
    protected double maxP, minP;        // max and min pheromone values
    protected double rhoToMaxFactor;    // max = m x [1 / f_opt] x [1 / (1 - rho)]


    public AntSystem(ComponentStructure structure, double evaporationRemains, boolean isBounded, double kFactor)
    {
        super(structure, evaporationRemains);

        this.isBounded = isBounded;
        this.kFactor = kFactor;

        rhoToMaxFactor = 1.0 / (1.0 - evaporationRemains);
        maxToMinFactor = 1.0 / (kFactor * structure.numberOfComponents());

        //if (kFactor < 1.0)
        //    throw new IllegalArgumentException("kFactor must be > 1.0");
    }

    public AntSystem(Problem problem, double evaporationRemains, boolean isBounded, double kFactor)
    {
        this(problem.structure, evaporationRemains, isBounded, kFactor);
    }


    @Override
    public void update(List<Solution> solutions)
    {
        // find min and max pheromone values

        double minObjective = solutions.get(0).objective;
        for (int index = 1; index < solutions.size(); index++)
        {
            double newObjective = solutions.get(index).objective;

            if (minObjective > newObjective)
                minObjective = newObjective;
        }

        maxP = solutions.size() * rhoToMaxFactor / minObjective;   // m * (1 / f_opt) * (1 / (1 - rho))
        minP = maxP * maxToMinFactor;

        executeStandardEvaporationAll();

        executeDepositAll(solutions);
    }


    /**
     * Executes linear rho-based evaporation to ALL components of the structure with respect to min and max
     */
    @Override
    protected void executeStandardEvaporationAll()
    {
        for (Component component : structure)
        {
            double newPheromone = evaporationRemains * component.getPheromone();

            if (isBounded)
            {
                if (newPheromone > maxP)
                    newPheromone = maxP;
                else if (newPheromone < minP)
                    newPheromone = minP;
            }

            component.setPheromone(newPheromone);
        }
    }



    protected void executeDepositAll(List<Solution> solutions)
    {
        for (Solution solution : solutions)
        {
            double addedValue = 1.0 / solution.objective;

            for (Component component : solution.getComponents())
            {
                double newPheromone = component.getPheromone() + addedValue;

                if (isBounded)
                {
                    if (newPheromone > maxP)
                        newPheromone = maxP;
                    else if (newPheromone < minP)
                        newPheromone = minP;
                }

                component.setPheromone(newPheromone);
            }
        }
    }
}
