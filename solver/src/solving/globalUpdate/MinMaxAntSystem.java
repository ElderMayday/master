package solving.globalUpdate;

import problem.component.Component;
import problem.componentStructure.ComponentStructure;
import problem.problemFormulation.Problem;
import solving.solution.ComparatorSolution;
import solving.solution.Solution;

import java.util.*;

/**
 * Created by Aldar on 09-Nov-17.
 */
public class MinMaxAntSystem extends GlobalUpdate
{
    protected ComponentStructure structure;
    protected double tMin, tMax;
    protected double problemSize; // aka-n, aka vertex number
    protected double pBest;       // minimum value control parameter

    protected double tMaxCoefficient;  //    1 / (1 - evaporationStrength)
    protected double tMinCoefficient;  // (1 - pow(pBest, 1/n)) / ((n/2-1) x pow(pBest, 1/n))

    public MinMaxAntSystem(Problem problem, double evaporationRate, double pBest)
    {
        super(problem, evaporationRate);
        this.problemSize = problem.ProblemSize();
        this.structure = problem.structure;
        this.pBest = pBest;
        tMaxCoefficient = 1 / (1 - evaporationStrength);
        tMinCoefficient = (1 - Math.pow(pBest, 1 / problemSize)) / ((problemSize / 2 - 1) * Math.pow(pBest, 1 / problemSize));
    }


    @Override
    public void update(List<Solution> solutions)
    {
        defineBounds(solutions);

        executeStandardEvaporationAll();

        executeCumulativeDeposit(solutions);
    }



    protected void defineBounds(List<Solution> solutions)
    {

        /*  Max case
        double max = solutions.get(0).objective();

        for (int i = 1; 1 < solutions.size(); i++)
        {
            double objective = solutions.get(i).objective();

            if (max < objective)
                max = objective;
        }

        tMax = tMaxCoefficient / max    */

        double sum = 0.0;

        for (int index = 0; index < solutions.size(); index++)
            sum += 1 / solutions.get(index).objective();

        tMax = tMaxCoefficient * sum;

        tMin = tMax * tMinCoefficient;
    }


    /**
     * Executes linear ro-based evaporation to ALL components of the structure
     * Takes into account the associated upper and lower pheromone bounds
     */
    @Override
    protected void executeStandardEvaporationAll()
    {
        for (Component component : structure)
        {
            double newPheromone = evaporationStrength * component.getPheromone();

            if (newPheromone > tMax)
                newPheromone = tMax;
            else
                if (newPheromone < tMin)
                    newPheromone = tMin;

            component.setPheromone(newPheromone);
        }
    }


    /**
     * Every component is deposited some pheromone value, depending on which solutions have chosen it
     * @param solutions
     */
    protected void executeCumulativeDeposit(List<Solution> solutions)
    {
        for (Solution solution : solutions)
        {
            double addedValue = 1.0 / solution.objective();

            for (Component component : solution.getComponents())
            {
                double newPheromone = component.getPheromone() + addedValue;

                if (newPheromone > tMax)
                    newPheromone = tMax;
                else
                if (newPheromone < tMin)
                    newPheromone = tMin;

                component.setPheromone(newPheromone);
            }
        }
    }
}
