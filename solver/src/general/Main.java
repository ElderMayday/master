package general;

import problem.component.Component;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.candidateList.CandidateDeterminer;
import solving.candidateList.CandidateDeterminerVrpSorting;
import solving.globalUpdate.*;
import solving.globalUpdate.pheromoneTrailSmoothing.PheromoneTrailSmoothing;
import solving.globalUpdate.pheromoneTrailSmoothing.PheromoneTrailSmoothingMMAS;
import solving.globalUpdate.pheromoneTrailSmoothing.PheromoneTrailSmoothingNone;
import solving.localSearch.IteratedLocalSearch;
import solving.localSearch.LocalSearch;
import solving.localSearch.LocalSearchNone;
import solving.localSearch.LocalSearchTwoHalf;
import solving.localSearch.localMove.MoveTwoHalf;
import solving.localSearch.perturbation.DoubleBridge;
import solving.localUpdate.LocalUpdate;
import solving.localUpdate.LocalUpdateNone;
import solving.localUpdate.LocalUpdateStandard;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.pheromoneInitializer.PheromoneInitializerConstant;
import solving.selectors.Selector;
import solving.selectors.SelectorDorigo;
import solving.selectors.SelectorManiezzo;
import solving.selectors.SelectorStandard;
import solving.solution.Solution;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.solutionDestroyer.SolutionDestroyerVrpRandom;
import solving.solvers.*;
import solving.solvers.iteratedCriteria.IteratedCriteria;
import solving.solvers.iteratedCriteria.IteratedCriteriaBest;
import solving.solvers.iteratedCriteria.IteratedCriteriaProbabilistic;
import solving.solvers.tournamentSelector.TournamentSelector;
import solving.solvers.tournamentSelector.TournamentSelectorBag;
import solving.terminationCriteria.TerminationCriteria;
import solving.terminationCriteria.TerminationCriteriaTime;

import java.io.File;
import java.util.List;
import java.util.Random;


// Intellij Idea may loose the source, test and output folder assignment
// If compilation does not work, check "Project Structure"

// Java 1.8.0_101
// org.mockito:mockito-core 2.8.9 for white-box testing
// org.json:json:20131018 for json problem file reading



public class Main
{
    public static Random random = new Random(28);;



    public static void main(String[] args)
    {
        try
        {
            ConfigurationVRP conf = new ConfigurationVRP(args);

            random = new Random(conf.seed);
            Selector selector = getSelector(conf);
            LocalUpdate localUpdate = getLocalUpdate(conf);
            LocalSearch localSearch = getLocalSearch(conf);
            CandidateDeterminer candidateDeterminer = getCandidateDeterminer(conf);

            if ((conf.selector == 1) || (conf.selector == 2))
                Component.setPrecomputationParameters(conf.alpha, conf.beta);

            ProblemVRP problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), candidateDeterminer);
            problem.load(new File(conf.path));

            GlobalUpdate globalUpdate = getGlobalUpdate(conf, problem);
            Solver solver = getSolver(conf, problem, selector, globalUpdate, localUpdate, localSearch);

            List<Solution> solutions = solver.solve();

            Solution best = Solution.findBestSolution(solutions);

            System.out.println(best.objective);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Solver getSolver(Configuration conf, Problem problem, Selector selector, GlobalUpdate globalUpdate,
                                   LocalUpdate localUpdate, LocalSearch localSearch)
    {
        Solver solver = null;

        TerminationCriteria terminationCriteria = new TerminationCriteriaTime(conf.runtime, conf.reinitializationTime, conf.reinitialization == 1);
        PheromoneInitializerConstant pheromoneInitializer = new PheromoneInitializerConstant(10.0);
        SolutionDestroyer solutionDestroyer = null;

        if ((conf.iteratedGreedy == 2) || (conf.iteratedGreedy == 3) || (conf.iteratedGreedy == 4))
            solutionDestroyer = new SolutionDestroyerVrpRandom(conf.destructionProbability);

        int antNum = conf.antNum;

        switch (conf.iteratedGreedy)
        {
            case 1:
                solver = new SolverStandard(problem, selector, globalUpdate, localUpdate, true,
                        terminationCriteria, pheromoneInitializer, localSearch, antNum);
                break;

            case 2:
                TournamentSelector tournamentSelector = new TournamentSelectorBag(conf.tournamentSelectorSize);

                solver = new SolverExternalMemory(problem, selector, localUpdate, true, terminationCriteria, pheromoneInitializer,
                        localSearch, solutionDestroyer, globalUpdate, antNum, conf.memorySize, conf.topK, tournamentSelector);
                break;

            case 3:
                IteratedCriteria iteratedCriteria = null;

                if (conf.iteratedCriteria == 1)
                    iteratedCriteria = new IteratedCriteriaBest();
                else
                    iteratedCriteria = new IteratedCriteriaProbabilistic(conf.probabilisticBest);

                solver = new SolverIteratedAnts(problem, selector, globalUpdate, localUpdate, true, terminationCriteria,
                        pheromoneInitializer, localSearch, solutionDestroyer, antNum, iteratedCriteria);
                break;

            case 4:
                solver = new SolverCunningAnts(problem, selector, localUpdate, true, terminationCriteria, pheromoneInitializer,
                        localSearch, (MinMaxAntSystem) globalUpdate, antNum, solutionDestroyer);
                break;
        }

        return solver;
    }

    public static GlobalUpdate getGlobalUpdate(ConfigurationVRP conf, Problem problem)
    {
        GlobalUpdate globalUpdate = null;

        switch (conf.globalUpdate)
        {
            case 1:
                globalUpdate = new AntSystem(problem, conf.evaporationRemains, conf.antSystemIsBounded == 1, conf.antSystemK);
                break;

            case 2:
                globalUpdate = new AntColonySystem(problem, conf.evaporationRemains);
                break;

            case 3:
                PheromoneTrailSmoothing pts = getPTS(conf);

                globalUpdate = new MinMaxAntSystem(problem, conf.evaporationRemains, conf.minMaxSystemPbest,
                        pts, conf.minMaxSystemGlobalBest == 1, conf.minMaxSystemGlobalIterations,
                        conf.minMaxSystemPts == 1, conf.minMaxSystemPtsIterations);
                break;

            case 4:
                globalUpdate = new RankBasedAntSystem(problem, conf.evaporationRemains, conf.rasW, conf.rasBounded == 1, conf.rasK);
                break;

            case 5:
                globalUpdate = new ElitistAntSystem(problem, conf.evaporationRemains, conf.easM, conf.easIsBounded == 1, conf.easK);
                break;

            case 6:
                globalUpdate = new BestWorstAntSystem(problem, conf.evaporationRemains, conf.bwasProbability);
                break;
        }

        return globalUpdate;
    }

    public static PheromoneTrailSmoothing getPTS(ConfigurationVRP conf)
    {
        PheromoneTrailSmoothing pts = null;

        if (conf.minMaxSystemPts == 1)
            pts = new PheromoneTrailSmoothingMMAS(conf.ptsLambda, conf.ptsRatio, conf.ptsDelta);
        else
            pts = new PheromoneTrailSmoothingNone();

        return pts;
    }

    public static CandidateDeterminer getCandidateDeterminer(ConfigurationVRP conf)
    {
        CandidateDeterminer candidateDeterminer = null;

        if (conf.candidate == 1)
            candidateDeterminer = new CandidateDeterminerVrpSorting(conf.candidateRatio);

        return candidateDeterminer;
    }

    public static Selector getSelector(ConfigurationVRP conf)
    {
        Selector selector = null;

        switch (conf.selector)
        {
            case 1:
                selector = new SelectorStandard(conf.alpha, conf.beta);
                break;
            case 2:
                selector = new SelectorDorigo(conf.alpha, conf.beta, conf.probabilityDorigo);
                break;
            case 3:
                selector = new SelectorManiezzo(conf.alpha);
                break;
        }

        return selector;
    }

    public static LocalUpdate getLocalUpdate(ConfigurationVRP conf)
    {
        LocalUpdate localUpdate = null;

        if (conf.localUpdate == 1)
            localUpdate = new LocalUpdateStandard(conf.lupdEpsilon, conf.lupdTau0);
        else
            localUpdate = new LocalUpdateNone();

        return localUpdate;
    }

    public static LocalSearch getLocalSearch(ConfigurationVRP conf)
    {
        LocalSearch localSearch = null;

        switch (conf.localSearch)
        {
            case 1:
                localSearch = new LocalSearchNone();
                break;
            case 2:
                localSearch = new IteratedLocalSearch(new MoveTwoHalf(), new DoubleBridge(), conf.ilsIterations);
                break;
            case 3:
                localSearch = new LocalSearchTwoHalf();
                break;
        }

        return localSearch;
    }
}
