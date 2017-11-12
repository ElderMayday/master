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

    public RankBasedAntSystem(Problem problem, double evaporationStrength, int numberOfDepositing)
    {
        super(problem, evaporationStrength);
        this.numberOfDepositing = numberOfDepositing;
        this.globalBest = null;
    }


    @Override
    public void update(List<Solution> solutions)
    {
        executeStandardEvaporationAll();

        executeRankBasedDeposition(solutions);
    }



    protected void executeRankBasedDeposition(List<Solution> solutions)
    {
        List<Solution> sorted = new ArrayList<Solution>(solutions);

        sorted.sort(new ComparatorSolution());

        // depositing to top [numberOfDepositing - 1] iterated-best solutions

        int index = 0;
        for (int factor = numberOfDepositing - 1; factor > 0; factor--)
        {
            Solution solution = sorted.get(index);

            double addValue = factor / solution.objective();

            for (Component component : solution.getComponents())
                component.setPheromone(component.getPheromone() + addValue);

            index--;
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
