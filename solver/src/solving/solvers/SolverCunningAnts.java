package solving.solvers;

import problem.problemFormulation.Problem;
import solving.globalUpdate.MinMaxAntSystem;
import solving.localSearch.LocalSearch;
import solving.localUpdate.LocalUpdate;
import solving.pheromoneInitializer.PheromoneInitializerConstant;
import solving.selectors.Selector;
import solving.solution.Solution;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.terminationCriteria.TerminationCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Demands PheromoneInitializerConstant as initializer
 * Created by Aldar on 08-Nov-17.
 */
public class SolverCunningAnts extends Solver
{
    protected SolutionDestroyer destroyer;


    public SolverCunningAnts(Problem problem, Selector selector, LocalUpdate localUpdate, boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializerConstant initializer, LocalSearch localSearch, MinMaxAntSystem globalUpdate, int antNum, SolutionDestroyer destroyer)
    {
        super(problem, selector, antNum, globalUpdate, localUpdate, localSearch, precomputeValues, terminationCriteria, initializer);

        this.destroyer = destroyer;
    }


    @Override
    public List<Solution> solve() throws Exception
    {
        List<Solution> solutions = new ArrayList<Solution>();
        List<Solution> newSolutions = new ArrayList<Solution>();

        terminationCriteria.initialize();

        initializer.initialize(problem.structure);

        for (int i = 0; i < antNum; i++)
        {
            Solution solution1 = constructOneSolution();
            Solution solution2 = constructOneSolution();

            solutions.add(solution1.betterThan(solution2) ? solution1 : solution2);
        }

        do
        {
            globalUpdate.update(solutions);

            newSolutions = new ArrayList<Solution>();
            for (Solution solution : solutions)
                newSolutions.add(solution.deepCopy());

            // c-ant construction

            for (int index = 0; index < newSolutions.size(); index++)
                newSolutions.set(index, destroyer.destroy(newSolutions.get(index)));

            for (int index = 0; index < newSolutions.size(); index++)
                reconstructOneSolution(newSolutions.get(index));

            // local-search for c-ant

            for (int index = 0; index < newSolutions.size(); index++)
                localSearch.search(problem, newSolutions.get(index));

            // pained result comparison

            for (int index = 0; index < solutions.size(); index++)
                if (newSolutions.get(index).betterThan(solutions.get(index)))
                    solutions.set(index, newSolutions.get(index));

            if (terminationCriteria.needReinitialize())
                initializer.initialize(problem.structure);
        }
        while (!terminationCriteria.isFulfilled());

        return solutions;
    }
}
