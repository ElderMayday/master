package solution.solvers;

import problem.problemFormulation.Problem;
import solution.Solution;
import solution.globalUpdate.GlobalUpdate;
import solution.localSearch.LocalSearch;
import solution.selectors.Selector;
import solution.terminationCriteria.TerminationCriteria;

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

    public SolverStandard(Problem problem, Selector selector, boolean precomputeHeuristic, TerminationCriteria terminationCriteria, LocalSearch localSearch, GlobalUpdate update, int antNum)
    {
        super(problem, selector, precomputeHeuristic, terminationCriteria);

        this.localSearch = localSearch;
        this.update = update;
        this.antNum = antNum;
    }



    @Override
    public List<Solution> solve()
    {
        List<Solution> solutions;

        do
        {
            solutions = construct();
        }
        while (terminationCriteria.isFullfilled());

        return solutions;
    }





    protected List<Solution> construct()
    {
        List<Solution> solutions = new ArrayList<Solution>();

        for (int i = 0; i < antNum; i++)
        {

        }

        return solutions;
    }
}
