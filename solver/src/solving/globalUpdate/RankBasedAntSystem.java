package solving.globalUpdate;

import problem.component.Component;
import problem.problemFormulation.Problem;
import solving.solution.ComparatorSolution;
import solving.solution.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 12-Nov-17.
 */
public class RankBasedAntSystem extends GlobalUpdate
{
    protected int numberOfDepositing; // aka w

    protected boolean isBounded;        // defines whether min and max bounds are applied
    protected double kFactor;           // aka k,    min = max / (k x problemSize)

    protected double maxToMinFactor;    // min = max x maxToMinFactor,   maxToMinFactor = 1 / (k x problemSize)
    protected double maxP, minP;        // max and min pheromone values
    protected double rhoToMaxFactor;    // max = [w x (w + 1) / 2] x [1 / f_opt] x [1 / (1 - rho)]

    public RankBasedAntSystem(Problem problem, double evaporationRemains, int numberOfDepositing, boolean isBounded, double kFactor)
    {
        super(problem, evaporationRemains);
        this.numberOfDepositing = numberOfDepositing;

        if (numberOfDepositing < 1)
            throw new IllegalArgumentException("Wrong number of depositing");

        //if (kFactor < 1.0)
         //   throw new IllegalArgumentException("kFactor must be > 1.0");

        this.isBounded = isBounded;
        this.kFactor = kFactor;

        rhoToMaxFactor = 1.0 / (1.0 - evaporationRemains) * numberOfDepositing * (numberOfDepositing + 1.0) / 2.0;
        maxToMinFactor = 1.0 / (kFactor * structure.numberOfComponents());
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

        // sort the solutions

        List<Solution> sorted = new ArrayList<Solution>(solutions);

        sorted.sort(new ComparatorSolution());

        // check whether global best has changed in an optimized way, does not call determineBest()

        if (best == null)
            best = sorted.get(0).deepCopy();
        else if (sorted.get(0).betterThan(best))
            best = sorted.get(0).deepCopy();

        maxP = rhoToMaxFactor / best.objective;   // m * (1 / f_opt) * (1 / (1 - rho))
        minP = maxP * maxToMinFactor;

        executeStandardEvaporationAll();

        executeRankBasedDeposit(sorted);
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


    protected void executeRankBasedDeposit(List<Solution> sorted)
    {
        // depositing to top [numberOfDepositing - 1] iterated-best solutions

        int factor = numberOfDepositing - 1;

        int iteratedBestToDeposit = Math.min(numberOfDepositing - 1, sorted.size());

        for (int index = 0; index < iteratedBestToDeposit; index++)
        {
            Solution solution = sorted.get(index);

            double addValue = (double) factor / solution.objective;

            for (Component component : best.getComponents())
            {
                double newPheromone = component.getPheromone() + addValue;

                if (isBounded)
                {
                    if (newPheromone > maxP)
                        newPheromone = maxP;
                    else if (newPheromone < minP)
                        newPheromone = minP;
                }

                component.setPheromone(newPheromone);
            }

            factor--;
        }

        // deposit to the global best

        double addValue = numberOfDepositing / best.objective;

        for (Component component : best.getComponents())
        {
            double newPheromone = component.getPheromone() + addValue;

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
