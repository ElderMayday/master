package solving.solvers;

import problem.problemFormulation.Problem;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.localUpdate.LocalUpdate;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.selectors.Selector;
import solving.solution.Solution;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.terminationCriteria.TerminationCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 02-Nov-17.
 */
public class SolverIteratedAnts extends Solver
{
    protected SolutionDestroyer destroyer;
    protected int antNum;
    protected boolean mustExecuteIntermediateSearch;

    public SolverIteratedAnts(Problem problem, Selector selector, LocalUpdate localUpdate, boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializer initializer, LocalSearch localSearch, SolutionDestroyer destroyer, int antNum, boolean mustExecuteIntermediateSearch)
    {
        super(problem, selector, localUpdate, localSearch, precomputeValues, terminationCriteria, initializer);

        this.destroyer = destroyer;
        this.antNum = antNum;
        this.mustExecuteIntermediateSearch = mustExecuteIntermediateSearch;
    }





    @Override
    public List<Solution> solve() throws Exception
    {
        double minObjective = -1.0;

        initializer.initialize(problem.structure);

        List<Solution> solutions = constructSolutionList(antNum);
        List<Solution> newSolutions;

        solutions = executeMultipleLocalSearch(solutions);

        do
        {
            newSolutions = new ArrayList<Solution>();
            for (Solution solution : solutions)
                newSolutions.add(solution.deepCopy());

            for (int index = 0; index < newSolutions.size(); index++)
                newSolutions.set(index, destroyer.destroy(newSolutions.get(index)));

            for (int index = 0; index < newSolutions.size(); index++)
                reconstructOneSolution(newSolutions.get(index));

            if (mustExecuteIntermediateSearch)
                newSolutions = executeMultipleLocalSearch(newSolutions);

            for (int index = 0; index < solutions.size(); index++)
                if (newSolutions.get(index).betterThan(solutions.get(index)))
                    solutions.set(index, newSolutions.get(index));


            /* // statistics optional part

            for (int index = 0; index < solutions.size(); index++)
            {
                if (minObjective < 0)
                    minObjective = solutions.get(index).objective();
                else
                {
                    double newObjective = solutions.get(index).objective();
                    if (minObjective > newObjective)
                        minObjective = newObjective;
                }
            }
            */

        }
        while (!terminationCriteria.isFullfilled());

        return solutions;
    }
}
