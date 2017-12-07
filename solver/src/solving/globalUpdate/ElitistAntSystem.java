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

    protected Solution globalBest;




    public ElitistAntSystem(Problem problem, double evaporationRemains, double elitistFactor)
    {
        super(problem, evaporationRemains);

        this.elitistFactor = elitistFactor;

        if (elitistFactor < 0.0)
            throw new IllegalArgumentException("Elitist factor cannot be negative");
    }



    @Override
    public void update(List<Solution> solutions)
    {
        executeStandardEvaporationAll();

        executeDepositAll(solutions);  // deposit from all iteration solutions

        executeEasDeposit(solutions);  // find global-best and deposit additionally
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


    protected void executeEasDeposit(List<Solution> solutions)
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

        double lengthMultiply = 1.0 / globalBest.objective();

        for (Component component : globalBest.getComponents())
            component.setPheromone(component.getPheromone() + elitistFactor * lengthMultiply);    // + m_elite / quality(solution)
    }
}
