package solving.solvers;

import problem.problemFormulation.Problem;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.localUpdate.LocalUpdate;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.pheromoneInitializer.PheromoneInitializerConstant;
import solving.selectors.Selector;
import solving.solution.Solution;
import solving.terminationCriteria.TerminationCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Demands PheromoneInitializerConstant as initializer
 * Created by Aldar on 08-Nov-17.
 */
public class SolverCunningAnts extends Solver
{
    protected GlobalUpdate globalUpdate;
    protected int antNum;


    public SolverCunningAnts(Problem problem, Selector selector, LocalUpdate localUpdate, boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializerConstant initializer, LocalSearch localSearch, GlobalUpdate update, int antNum)
    {
        super(problem, selector, localUpdate, localSearch, precomputeValues, terminationCriteria, initializer);

        this.globalUpdate = update;
        this.antNum = antNum;
    }


    @Override
    public List<Solution> solve() throws Exception
    {
        List<Solution> solutions = new ArrayList<Solution>();

        initializer.initialize(problem.structure);

        for (int i = 0; i < antNum; i++)
        {
            Solution solution1 = constructOneSolution();
            Solution solution2 = constructOneSolution();

            solutions.add(solution1.betterThan(solution2) ? solution1 : solution2);
        }

        return solutions;
    }
}
