package solving.solvers;

import problem.problemFormulation.Problem;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.selectors.Selector;
import solving.solution.ComparatorSolution;
import solving.solution.Solution;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.terminationCriteria.TerminationCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 08-Nov-17.
 */
public class SolverExternalMemory extends Solver
{
    protected SolutionDestroyer destroyer;
    protected int antNum;
    protected int memorySize;
    protected GlobalUpdate update;
    protected int topK;

    public SolverExternalMemory(Problem problem, Selector selector, boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializer initializer, LocalSearch localSearch, SolutionDestroyer destroyer, GlobalUpdate update, int antNum, int memorySize, int topK)
    {
        super(problem, selector, localSearch, precomputeValues, terminationCriteria, initializer);

        this.destroyer = destroyer;
        this.antNum = antNum;
        this.memorySize = memorySize;
        this.update = update;
        this.topK = topK;
    }

    @Override
    public List<Solution> solve() throws Exception
    {
        initializer.initialize(problem.structure);

        List<Solution> segments = new ArrayList<Solution>();

        do
        {
            List<Solution> newComponents = constructSolutionList(antNum);

            newComponents.sort(new ComparatorSolution());

            newComponents = newComponents.subList(0, topK);

            segments.addAll(newComponents);

            update.update(segments);
        }
        while (segments.size() < memorySize);       // until the memory is full

        segments = segments.subList(0, memorySize); // trim the memory to the fixed size

        segments.sort(new ComparatorSolution());

        do
        {
            // to do Concatenation and Tournament selection before doing this
        }
        while (!terminationCriteria.isFullfilled());

        return null;
    }
}
