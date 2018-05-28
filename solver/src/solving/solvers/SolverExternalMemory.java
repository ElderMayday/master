package solving.solvers;

import problem.problemFormulation.Problem;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.localUpdate.LocalUpdate;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.selectors.Selector;
import solving.solution.ComparatorSolution;
import solving.solution.ComparatorSolutionLast;
import solving.solution.Solution;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.solvers.tournamentSelector.TournamentSelector;
import solving.terminationCriteria.TerminationCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * REMARK!!! No local search is used in this Solver
 * REMARK!!! Deviates from the paper version:
 * - The destruction comes after the phase one
 * - Does not perform concatenation due to unclear consistency assurance
 * - Appends all new solutions to the segments, sorts and trims instead of diversification
 * Created by Aldar on 08-Nov-17.
 */
public class SolverExternalMemory extends Solver
{
    protected SolutionDestroyer destroyer;
    protected int memorySize;
    protected int topK;
    protected TournamentSelector tournamentSelector;

    public SolverExternalMemory(Problem problem, Selector selector, LocalUpdate localUpdate, boolean precomputeValues,
                                TerminationCriteria terminationCriteria, PheromoneInitializer initializer, LocalSearch localSearch,
                                SolutionDestroyer destroyer, GlobalUpdate globalUpdate, int antNum, int memorySize,
                                int topK, TournamentSelector tournamentSelector)
    {
        super(problem, selector, antNum, globalUpdate, localUpdate, localSearch, precomputeValues, terminationCriteria, initializer);

        this.destroyer = destroyer;
        this.memorySize = memorySize;
        this.globalUpdate = globalUpdate;
        this.topK = topK;
        this.tournamentSelector = tournamentSelector;

        if ((topK <= 0) || (antNum < topK))
            throw new IllegalArgumentException("Wrong topK value");
    }


    /**
     * @return
     * @throws Exception
     */
    @SuppressWarnings("Since15")
    @Override
    public List<Solution> solve() throws Exception
    {
        terminationCriteria.initialize();

        experimentalRun();

        List<Solution> segments = new ArrayList<Solution>();

        do
        {
            List<Solution> newComponents = constructSolutionList(antNum);

            newComponents.sort(new ComparatorSolution());

            newComponents = newComponents.subList(0, topK);

            segments.addAll(newComponents);

            globalUpdate.update(segments);
        }
        while (segments.size() < memorySize);       // until the memory is full

        segments = segments.subList(0, memorySize); // trim the memory to the fixed size

        segments.sort(new ComparatorSolution());

        for (int index = 0; index < segments.size(); index++)
            destroyer.destroy(segments.get(index));

        List<Solution> solutions = new ArrayList<Solution>();
        
        do
        {
            List<Solution> selectedSolutions = tournamentSelector.select(segments, antNum);

            // must copy segments, because they are not a subject to reconstruction

            solutions = new ArrayList<Solution>();
            for (Solution solution : selectedSolutions)
                solutions.add(solution.deepCopy());

            for (int index = 0; index < solutions.size(); index++)
                reconstructOneSolution(solutions.get(index));

            // update pheromone matrix

            globalUpdate.update(solutions);

            // update memory

            List<Solution> newSegments = new ArrayList<Solution>();    // we copy, so we return complete solutions if we finish
            for (Solution solution : solutions)
                newSegments.add(solution.deepCopy());

            for (int index = 0; index < newSegments.size(); index++)
                destroyer.destroy(newSegments.get(index));

            segments.addAll(newSegments);

            segments.sort(new ComparatorSolutionLast());

            segments = trimList(segments, memorySize);

            if (terminationCriteria.needReinitialize())
                initializer.initialize(problem.structure);
        }
        while (!terminationCriteria.isFulfilled());

        return solutions;
    }


    protected List<Solution> trimList(List<Solution> segments, int memorySize)
    {
        List<Solution> result = new ArrayList<Solution>();

        for (int i = 0; i < memorySize; i++)
            result.add(segments.get(i));

        return result;
    }
}
