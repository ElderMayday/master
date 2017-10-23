package solving.solvers;

import problem.problemFormulation.Problem;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.solution.Solution;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.selectors.Selector;
import solving.terminationCriteria.TerminationCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 16-Oct-17.
 */
public class SolverStandard extends Solver
{
    protected LocalSearch localSearch;
    protected GlobalUpdate update;
    protected int antNum;



    public SolverStandard(Problem problem, Selector selector, boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializer initializer, LocalSearch localSearch, GlobalUpdate update, int antNum)
    {
        super(problem, selector, localSearch, precomputeValues, terminationCriteria, initializer);

        this.localSearch = localSearch;
        this.update = update;
        this.antNum = antNum;
    }



    @Override
    public List<Solution> solve()
    {
        List<Solution> solutions;

        initializer.initialize(problem.structure);

        do
        {
            solutions = constructAllSolutions();
        }
        while (!terminationCriteria.isFullfilled());

        return solutions;
    }





    protected List<Solution> constructAllSolutions()
    {
        List<Solution> solutions = new ArrayList<Solution>();

        for (int i = 0; i < antNum; i++)
        {
            solutions.add(constructOneSolution());
        }

        return solutions;
    }


    protected Solution constructOneSolution()
    {
        Solution solution = null;



        return solution;
    }
}
