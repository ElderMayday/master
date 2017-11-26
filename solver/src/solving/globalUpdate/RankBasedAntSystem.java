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

    protected Solution globalBest;

    public RankBasedAntSystem(Problem problem, double evaporationRemains, int numberOfDepositing)
    {
        super(problem, evaporationRemains);
        this.numberOfDepositing = numberOfDepositing;
        this.globalBest = null;
    }


    @Override
    public void update(List<Solution> solutions)
    {
        executeStandardEvaporationAll();

        executeRankBasedDeposit(solutions);
    }



    protected void executeRankBasedDeposit(List<Solution> solutions)
    {
        List<Solution> sorted = new ArrayList<Solution>(solutions);

        sorted.sort(new ComparatorSolution());

        // depositing to top [numberOfDepositing - 1] iterated-best solutions

        int factor = numberOfDepositing - 1;
        for (int index = 0; index < numberOfDepositing - 1; index++)
        {
            Solution solution = sorted.get(index);

            double addValue = (double) factor / solution.objective();

            for (Component component : solution.getComponents())
                component.setPheromone(component.getPheromone() + addValue);

            factor--;
        }

        // check whether global best has changed

        if (globalBest == null)
            globalBest = sorted.get(0);
        else
            if (sorted.get(0).betterThan(globalBest))
                globalBest = sorted.get(0);

        // deposit to the global best

        double addValue = numberOfDepositing / globalBest.objective();

        for (Component component : globalBest.getComponents())
            component.setPheromone(component.getPheromone() + addValue);
    }
}
