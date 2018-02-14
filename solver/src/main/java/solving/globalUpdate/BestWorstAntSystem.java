package solving.globalUpdate;

import general.Main;
import problem.component.Component;
import problem.componentStructure.ComponentStructure;
import problem.problemFormulation.Problem;
import solving.solution.Solution;
import solving.terminationCriteria.TerminationCriteria;

import java.util.List;

/**
 * REMARK!!! No local search is performed here (deviation), it is only at solver level.
 * REMARK!!! Does not mutate the reverse components(deviation), such as (y;x) for (x;y)
 * Created by Aldar on 27-Nov-17.
 */
public class BestWorstAntSystem extends GlobalUpdate
{
    protected Solution globalBest;
    protected TerminationCriteria terminationCriteria;
    protected double mutatedProbability;

    public BestWorstAntSystem(ComponentStructure structure, double evaporationRemains, double mutatedProbability)
    {
        super(structure, evaporationRemains);

        this.mutatedProbability = mutatedProbability;
    }

    public BestWorstAntSystem(Problem problem, double evaporationRemains, double mutatedProbability)
    {
        this(problem.structure, evaporationRemains, mutatedProbability);
    }


    @Override
    public void update(List<Solution> solutions) throws Exception
    {
        if (terminationCriteria == null)
            throw new Exception("The termination criteria was not set for the BWAS");

        depositBestGlobal(solutions);

        evaporateWorstMinusBest(solutions);

        doMutation(solutions);
    }



    public void doMutation(List<Solution> solutions)
    {
        // compute the average pheromone trail value

        double sum = 0.0;

        for (Solution solution : solutions)
            sum += solution.sumPheromone();

        int totalNumberOfComponents = 0;

        for (Solution solution : solutions)
            totalNumberOfComponents += solution.getNumberOfComponents();

        double average = sum / totalNumberOfComponents; // all solutions have the same number of components

        // compute mutationStrength and mutationNumber

        double mutationStrength = 4.0 * average * terminationCriteria.mutationBwasFactor();

        int mutatedNumber = (int) Math.round(totalNumberOfComponents * mutatedProbability);


        for (int i = 0; i < mutatedNumber; i++)
        {
            Solution solution = solutions.get(Main.random.nextInt(solutions.size()));

            List<Component> components = solution.getComponents();

            Component component = components.get(Main.random.nextInt(components.size()));

            double newValue = component.getValue();

            if (Main.random.nextDouble() < 0.5)
                newValue -= mutationStrength;
            else
                newValue += mutationStrength;

            if (newValue < 0.0)
                newValue = 0.0;

            component.setPheromone(newValue);
        }
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

        double addedValue = evaporationStrength / globalBest.objective;

        for (Component component : globalBest)
            component.setPheromone(component.getPheromone() + addedValue);
    }



    public void setTerminationCriteria(TerminationCriteria terminationCriteria)
    {
        this.terminationCriteria = terminationCriteria;
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
