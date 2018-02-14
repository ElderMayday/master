package solving.globalUpdate;

import problem.component.Component;
import problem.problemFormulation.Problem;
import solving.solution.Solution;

import java.util.List;

/**
 * Created by Aldar on 26-Nov-17.
 */
public class ElitistAntSystem extends GlobalUpdate
{
    protected Problem problem;

    protected double elitistFactor;   // aka m_elite, aka elitist ant number

    protected boolean isBounded;        // defines whether min and max bounds are applied
    protected double kFactor;           // aka k,    min = max / (k x problemSize)

    protected double maxToMinFactor;    // min = max x maxToMinFactor,   maxToMinFactor = 1 / (k x problemSize)
    protected double maxP, minP;        // max and min pheromone values
    protected double rhoToMaxFactor;    // max = [e + m] x [1 / f_opt] x [1 / (1 - rho)]

    protected Solution globalBest;




    public ElitistAntSystem(Problem problem, double evaporationRemains, double elitistFactor, boolean isBounded, double kFactor)
    {
        super(problem, evaporationRemains);

        this.elitistFactor = elitistFactor;

        this.isBounded = isBounded;
        this.kFactor = kFactor;

        rhoToMaxFactor = 1.0 / (1.0 - evaporationRemains);
        maxToMinFactor = 1.0 / (kFactor * structure.numberOfComponents());

        if (elitistFactor < 0.0)
            throw new IllegalArgumentException("Elitist factor cannot be negative");
    }



    @Override
    public void update(List<Solution> solutions)
    {
        defineGlobalBest(solutions);

        maxP = (elitistFactor + solutions.size()) * rhoToMaxFactor / globalBest.objective;   // (e + m) * (1 / f_opt) * (1 / (1 - rho))
        minP = maxP * maxToMinFactor;

        executeStandardEvaporationAll();

        executeDepositAll(solutions);  // deposit from all iteration solutions

        executeEasDeposit();  // find global-best and deposit additionally
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


    protected void defineGlobalBest(List<Solution> solutions)
    {
        if (globalBest == null)
        {
            globalBest = solutions.get(0);

            for (int index = 1; index < solutions.size(); index++)
            {
                Solution currentSolution = solutions.get(index);

                if (currentSolution.betterThan(globalBest))
                    globalBest = currentSolution;
            }
        }
        else
        {
            for (int index = 0; index < solutions.size(); index++)
            {
                Solution currentSolution = solutions.get(index);

                if (currentSolution.betterThan(globalBest))
                    globalBest = currentSolution;
            }
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


    protected void executeEasDeposit()
    {
        double lengthMultiply = 1.0 / globalBest.objective;

        for (Component component : globalBest.getComponents())
        {
            double newPheromone = component.getPheromone() + elitistFactor * lengthMultiply;

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
