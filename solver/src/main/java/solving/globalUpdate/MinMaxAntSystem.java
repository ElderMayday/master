package solving.globalUpdate;

import problem.component.Component;
import problem.problemFormulation.Problem;
import solving.globalUpdate.pheromoneTrailSmoothing.PheromoneTrailSmoothing;
import solving.solution.Solution;

import java.util.*;

/**
 * Created by Aldar on 09-Nov-17.
 */
public class MinMaxAntSystem extends GlobalUpdate
{
    protected double tMin, tMax;

    protected double tMaxCoefficient;  //    1 / (1 - evaporationRemains)
    protected double tMinCoefficient;  // (1 - pow(pBest, 1/n)) / ((n/2-1) x pow(pBest, 1/n))

    protected PheromoneTrailSmoothing pts;

    protected int iterationsBetweenGlobal; // how many iterated-best are used between two global-best usages, -1 for no global
    protected int countSchedule;

    protected int iterationsBetweenPTS; // how many iterations do not apply PTS between two iterations with applying of PTS, -1 for no applying
    protected int countPTS;

    protected Solution globalBest;

    public MinMaxAntSystem(Problem problem, double evaporationRemains, double pBest, PheromoneTrailSmoothing pts, int iterationsBetweenGlobal, int iterationsBetweenPTS)
    {
        super(problem, evaporationRemains);

        int problemSize = problem.ProblemSize(); // aka-n, aka vertex number
        tMaxCoefficient = 1.0 / this.evaporationStrength;
        tMinCoefficient = (1.0 - Math.pow(pBest, 1.0 / problemSize)) / ((problemSize / 2.0 - 1.0) * Math.pow(pBest, 1.0 / problemSize));

        this.pts = pts;

        this.iterationsBetweenGlobal = iterationsBetweenGlobal;
        this.iterationsBetweenPTS = iterationsBetweenPTS;

        this.countSchedule = iterationsBetweenGlobal + 1;
        this.countPTS = iterationsBetweenPTS + 1;
    }


    @Override
    public void update(List<Solution> solutions)
    {
        defineBounds(solutions);

        executeStandardEvaporationAll();

        executeCumulativeDeposit(solutions);

        // do PTS if iteration has come and criteria is fulfilled

        if (iterationsBetweenPTS > 0)
        {
            countPTS--;

            if (countPTS <= 0)
            {
                countPTS = iterationsBetweenPTS + 1;

                pts.doIfRequired(structure, tMin, tMax);
            }
        }
    }




    protected void defineBounds(List<Solution> solutions)
    {
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
            double newPheromone = evaporationRemains * component.getPheromone();

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
        // find iteration-best

        Solution iterationBest;

        iterationBest = solutions.get(0);

        for (int index = 1; index < solutions.size(); index++)
        {
            Solution current = solutions.get(index);

            if (current.betterThan(iterationBest))
                iterationBest = current;
        }


        if (iterationsBetweenGlobal >= 0)  // switch is applied
        {
            if ((globalBest == null) || iterationBest.betterThan(globalBest))
                globalBest = iterationBest;

            countSchedule--;

            if (countSchedule <= 0)
            {
                countSchedule = iterationsBetweenGlobal + 1;

                // deposit global best

                double addedValue = 1.0 / globalBest.objective();

                for (Component component : globalBest.getComponents())
                {
                    double newPheromone = component.getPheromone() + addedValue;

                    if (newPheromone > tMax)
                        newPheromone = tMax;
                    else if (newPheromone < tMin)
                        newPheromone = tMin;

                    component.setPheromone(newPheromone);
                }
            }
            else
            {
                // deposit iterated best

                double addedValue = 1.0 / iterationBest.objective();

                for (Component component : iterationBest.getComponents())
                {
                    double newPheromone = component.getPheromone() + addedValue;

                    if (newPheromone > tMax)
                        newPheromone = tMax;
                    else if (newPheromone < tMin)
                        newPheromone = tMin;

                    component.setPheromone(newPheromone);
                }
            }
        }
        else
        {
            // deposit iterated best

            double addedValue = 1.0 / iterationBest.objective();

            for (Component component : iterationBest.getComponents())
            {
                double newPheromone = component.getPheromone() + addedValue;

                if (newPheromone > tMax)
                    newPheromone = tMax;
                else if (newPheromone < tMin)
                    newPheromone = tMin;

                component.setPheromone(newPheromone);
            }
        }
    }
}
