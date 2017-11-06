package solving.solvers;

import problem.component.Component;
import problem.problemFormulation.Problem;
import solving.localSearch.LocalSearch;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.solution.Solution;
import solving.selectors.Selector;
import solving.terminationCriteria.TerminationCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 30-Sep-17.
 */
public abstract class Solver
{
    protected Problem problem;
    protected Selector selector;
    protected TerminationCriteria terminationCriteria;
    protected LocalSearch localSearch;
    protected PheromoneInitializer initializer;


    /**
     *
     * @param problem
     * @param selector
     * @param precomputeValues
     */
    public Solver(Problem problem, Selector selector, LocalSearch localSearch, boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializer initializer)
    {
        this.problem = problem;
        this.selector = selector;
        this.terminationCriteria = terminationCriteria;
        this.localSearch = localSearch;
        this.initializer = initializer;

        if (precomputeValues)
            problem.precomputeValues();
    }

    public abstract List<Solution> solve() throws Exception;

    protected Solution constructOneSolution() throws Exception
    {
        Solution solution = problem.createSolution();

        while (!solution.getComplete())
        {
            List<Component> components = problem.getConstructionComponents(solution);

            Component component = selector.select(components);

            solution.addConstructionComponent(component);
        }

        return solution;
    }



    protected Solution reconstructOneSolution(Solution solution) throws Exception
    {
        if (solution.isPartiallyDestroyed()) // reconstruct only of it was partially destroyed, otherwise leave it same
        {
            while (!solution.getComplete())
            {
                List<Component> components = problem.getReconstructionComponents(solution);

                Component component = selector.select(components);

                solution.addReconstructionComponent(component);
            }

            solution.setPartiallyDestroyed(false);
            solution.setComplete(true);
        }

        return solution;
    }

    protected List<Solution> constructSolutionList(int antNum) throws Exception
    {
        List<Solution> solutions = new ArrayList<Solution>();

        for (int i = 0; i < antNum; i++)
            solutions.add(constructOneSolution());

        return solutions;
    }

    protected List<Solution> executeMultipleLocalSearch(List<Solution> solutions)
    {
        List<Solution> solutionsLocalSearched = new ArrayList<Solution>();

        for (Solution solution : solutions)
            solutionsLocalSearched.add(localSearch.search(problem, solution));

        return solutionsLocalSearched;
    }
}
