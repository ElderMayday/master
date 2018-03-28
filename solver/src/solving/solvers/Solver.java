package solving.solvers;

import problem.component.Component;
import problem.componentStructure.ComponentStructure2d;
import problem.problemFormulation.MinMaxResult;
import problem.problemFormulation.Problem;
import solving.globalUpdate.BestWorstAntSystem;
import solving.globalUpdate.GlobalUpdate;
import solving.localSearch.LocalSearch;
import solving.localSearch.LocalSearchNone;
import solving.localUpdate.LocalUpdate;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.pheromoneInitializer.PheromoneInitializerRange;
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
    protected LocalUpdate localUpdate;
    protected GlobalUpdate globalUpdate;
    protected int antNum;

    /**
     *
     * @param problem
     * @param selector
     * @param precomputeValues
     */
    public Solver(Problem problem, Selector selector, int antNum, GlobalUpdate globalUpdate, LocalUpdate localUpdate, LocalSearch localSearch, boolean precomputeValues, TerminationCriteria terminationCriteria, PheromoneInitializer initializer)
    {
        this.problem = problem;
        this.selector = selector;
        this.antNum = antNum;
        this.globalUpdate = globalUpdate;
        this.localUpdate = localUpdate;
        this.terminationCriteria = terminationCriteria;
        this.localSearch = localSearch;
        this.initializer = initializer;

        if (antNum <= 0)
            throw new IllegalArgumentException("AntNum must be positive");

        if (globalUpdate instanceof BestWorstAntSystem)
        {
            BestWorstAntSystem bwas = (BestWorstAntSystem) globalUpdate;
            bwas.setTerminationCriteria(terminationCriteria);
        }

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

            localUpdate.update(component);
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

                localUpdate.update(component);
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
        if (!(localSearch instanceof LocalSearchNone))
        {
            List<Solution> solutionsLocalSearched = new ArrayList<Solution>();

            for (Solution solution : solutions)
                solutionsLocalSearched.add(localSearch.search(problem, solution));

            return solutionsLocalSearched;
        }
        else
            return solutions;
    }


    /**
     * Performs an initializing run
     * Recommended to be done by all heuristics after one experimental run
     */
    protected void experimentalRun() throws Exception
    {
        initializer.initialize(problem.structure);
        List<Solution> solutions = constructSolutionList(antNum);
        globalUpdate.update(solutions);
        reinitializeByCurrentValues();
    }


    /**
     * Sets a new pheromone initializer according to the minimal and maximal trail values at the moment.
     */
    protected void reinitializeByCurrentValues()
    {
        MinMaxResult minMaxResult = problem.findMinMax();

        initializer = new PheromoneInitializerRange(minMaxResult.min, minMaxResult.max);

        initializer.initialize(problem.structure);
    }
}
