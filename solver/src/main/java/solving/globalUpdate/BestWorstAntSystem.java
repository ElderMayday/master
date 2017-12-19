package solving.globalUpdate;

import problem.component.Component;
import problem.componentStructure.ComponentStructure;
import problem.problemFormulation.Problem;
import solving.solution.Solution;

import java.util.List;

/**
 * Created by Aldar on 27-Nov-17.
 */
public class BestWorstAntSystem extends GlobalUpdate
{
    protected Solution globalBest;

    public BestWorstAntSystem(ComponentStructure structure, double evaporationRemains)
    {
        super(structure, evaporationRemains);
    }

    public BestWorstAntSystem(Problem problem, double evaporationRemains)
    {
        super(problem, evaporationRemains);
    }


    @Override
    public void update(List<Solution> solutions)
    {
        depositBestGlobal(solutions);

        evaporateWorstMinusBest(solutions);
    }



    public void depositBestGlobal(List<Solution> solutions)
    {
        // find the global-best solution

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

        double addedValue = evaporationStrength / globalBest.objective();

        for (Component component : globalBest)
            component.setPheromone(component.getPheromone() + addedValue);
    }



    public void evaporateWorstMinusBest(List<Solution> solutions)
    {
        // find worst iteration solution

        Solution worstIteration = solutions.get(0);

        for (int index = 1; index < solutions.size(); index++)
        {
            Solution currentSolution = solutions.get(index);

            if (worstIteration.betterThan(currentSolution))
                worstIteration = currentSolution;
        }

        // evaporate

        for (Component component : worstIteration)
            if (!globalBest.getComponents().contains(component))     // if this component from the iteration-worst is not in global-best
                component.setPheromone(component.getPheromone() * evaporationRemains);
    }
}
