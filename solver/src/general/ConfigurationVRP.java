package general;

/**
 * Run configuration designed for VRP problems
 * Created by Aldar on 15-Mar-18.
 */
public class ConfigurationVRP extends Configuration
{
    public ConfigurationVRP(String[] args)
    {
        if (args.length <= 4)
            throw new IllegalArgumentException("Not all arguments are specified:\njava -jar solver.jar [configuration id] " +
                    "[instance id] [seed] [instance path] {rest of the arguments}");

        seed = Integer.parseInt(args[2]);

        path = args[3];

        for (int i = 4; i < args.length;)
        {
            if (args[i].equals("--selector-standard"))
            {
                if (this.selector != -1)
                    throw new IllegalArgumentException("Redefinition of selector type is not allowed");

                this.selector = 1;
                i++;
                continue;
            }

            if (args[i].equals("--selector-dorigo"))
            {
                if (this.selector != -1)
                    throw new IllegalArgumentException("Redefinition of selector type is not allowed");

                this.selector = 2;
                i++;
                continue;
            }

            if (args[i].equals("--selector-maniezzo"))
            {
                if (this.selector != -1)
                    throw new IllegalArgumentException("Redefinition of selector type is not allowed");

                this.selector = 3;
                i++;
                continue;
            }

            if (args[i].equals("--alpha"))
            {
                this.alpha = Double.parseDouble(args[i + 1]);
                i += 2;

                if (selector == -1)
                    throw new IllegalArgumentException("Alpha cannot be defined before the selector type");

                continue;
            }

            if (args[i].equals("--beta"))
            {
                this.beta = Double.parseDouble(args[i + 1]);
                i += 2;

                if (!((selector == 1) || (selector == 2)))
                    throw new IllegalArgumentException("Beta parameters cannot be not in standard or dorigo selector");

                continue;
            }

            if (args[i].equals("--dorigo-probability"))
            {
                this.probabilityDorigo = Double.parseDouble(args[i + 1]);
                i += 2;

                if (selector != 2)
                    throw new IllegalArgumentException("dorigo-probability can be only defined if dorigo selector is specified");

                continue;
            }

            if (args[i].equals("--candidate-yes"))
            {
                if (this.candidate != -1)
                    throw new IllegalArgumentException("Redefinition of candidate flag is not allowed");

                this.candidate = 1;
                i++;
                continue;
            }

            if (args[i].equals("--candidate-no"))
            {
                if (this.candidate != -1)
                    throw new IllegalArgumentException("Redefinition of candidate flag is not allowed");

                this.candidate = 2;
                i++;
                continue;
            }

            if (args[i].equals("--candidate-ratio"))
            {
                this.candidateRatio = Double.parseDouble(args[i + 1]);
                i += 2;

                if (candidate != 1)
                    throw new IllegalArgumentException("Candidate ratio cannot be defined without candidate flag");

                continue;
            }

            if (args[i].equals("--ant-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 1;
                i++;
                continue;
            }

            if (args[i].equals("--ant-colony-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 2;
                i++;
                continue;
            }

            if (args[i].equals("--min-max-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 3;
                i++;
                continue;
            }

            if (args[i].equals("--rank-based-ant-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 4;
                i++;
                continue;
            }

            if (args[i].equals("--elitist-ant-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 5;
                i++;
                continue;
            }

            if (args[i].equals("--best-worst-ant-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 6;
                i++;
                continue;
            }

            if (args[i].equals("--local-update-yes"))
            {
                if (this.localUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of local update is not allowed");

                if (!((selector == 1) || (selector == 2)))
                    throw new IllegalArgumentException("Local update must be only specified in case of standard or dorigo selector");

                this.localUpdate = 1;
                i++;
                continue;
            }

            if (args[i].equals("--local-update-no"))
            {
                if (this.localUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of local update is not allowed");

                this.localUpdate = 2;
                i++;
                continue;
            }

            if (args[i].equals("--standard"))
            {
                if (this.iteratedGreedy != -1)
                    throw new IllegalArgumentException("Redefinition of iterated greedy mode is not allowed");

                this.iteratedGreedy = 1;
                i++;
                continue;
            }

            if (args[i].equals("--external-memory"))
            {
                if (this.iteratedGreedy != -1)
                    throw new IllegalArgumentException("Redefinition of iterated greedy mode is not allowed");

                this.iteratedGreedy = 2;
                i++;
                continue;
            }

            if (args[i].equals("--iterated-ants"))
            {
                if (this.iteratedGreedy != -1)
                    throw new IllegalArgumentException("Redefinition of iterated greedy mode is not allowed");

                this.iteratedGreedy = 3;
                i++;
                continue;
            }

            if (args[i].equals("--cunning-ants"))
            {
                if (this.iteratedGreedy != -1)
                    throw new IllegalArgumentException("Redefinition of iterated greedy mode is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("Cunning ants can only use MMAS");

                this.iteratedGreedy = 4;
                i++;
                continue;
            }

            if (args[i].equals("--ant-num"))
            {
                if (this.antNum != -1)
                    throw new IllegalArgumentException("Redefinition of ant num is not allowed");

                this.antNum = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--runtime"))
            {
                if (this.runtime != -1)
                    throw new IllegalArgumentException("Redefinition of runtime is not allowed");

                this.runtime = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--lupd-epsilon"))
            {
                if (this.lupdEpsilon >= 0.0)
                    throw new IllegalArgumentException("Redefinition of local update epsilon");

                if (this.localUpdate != 1)
                    throw new IllegalArgumentException("Local update epsilon must be only specified in case if local update is required");

                this.lupdEpsilon = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--tau0"))
            {
                if (this.lupdTau0 >= 0.0)
                    throw new IllegalArgumentException("Redefinition of local update epsilon");

                if (this.localUpdate != 1)
                    throw new IllegalArgumentException("Local update tau0 must be only specified in case if local update is required");

                this.lupdTau0 = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--local-search-none"))
            {
                if (this.localSearch != 1)
                    throw new IllegalArgumentException("Redefinition of local search is not allowed");

                i++;
                continue;
            }

            if (args[i].equals("--local-search-ils-twohalf"))
            {
                if (this.localSearch != 1)
                    throw new IllegalArgumentException("Redefinition of local search is not allowed");

                this.localSearch = 2;
                i++;
                continue;
            }

            if (args[i].equals("--local-search-twohalf"))
            {
                if (this.localSearch != 1)
                    throw new IllegalArgumentException("Redefinition of local search is not allowed");

                this.localSearch = 3;
                i++;
                continue;
            }

            if (args[i].equals("--ils-iterations"))
            {
                if (this.ilsIterations != -1)
                    throw new IllegalArgumentException("Redefinition of ILS iteration number is not allowed");

                if (this.localSearch != 2)
                    throw new IllegalArgumentException("ILS iterations must be only specified if --local-search-ils-twohalf is specified");

                this.ilsIterations = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--evaporation-remains"))
            {
                if (this.evaporationRemains >= 0)
                    throw new IllegalArgumentException("Redefinition of evaporation remaining fraction is not allowed");

                this.evaporationRemains = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--ant-s-bounded-yes"))
            {
                if (this.antSystemIsBounded != -1)
                    throw new IllegalArgumentException("Redefinition of ant-s-bounded is not allowed");

                if (this.globalUpdate != 1)
                    throw new IllegalArgumentException("ant-s-bounded cannot be specified if other global update was specified");

                this.antSystemIsBounded = 1;
                i++;
                continue;
            }

            if (args[i].equals("--ant-s-bounded-no"))
            {
                if (this.antSystemIsBounded != -1)
                    throw new IllegalArgumentException("Redefinition of ant-s-bounded is not allowed");

                if (this.globalUpdate != 1)
                    throw new IllegalArgumentException("ant-s-bounded cannot be specified if other global update was specified");

                this.antSystemIsBounded = 2;
                i++;
                continue;
            }

            if (args[i].equals("--ant-s-k"))
            {
                if (this.antSystemK >= 0.0)
                    throw new IllegalArgumentException("Redefinition of ant-s-k is not allowed");

                if (this.globalUpdate != 1)
                    throw new IllegalArgumentException("ant-s-k cannot be specified if other global update was specified");

                if (this.antSystemIsBounded != 1)
                    throw new IllegalArgumentException("ant-s-bounded-yes must be specified");

                this.antSystemK = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--min-max-s-p-best"))
            {
                if (this.minMaxSystemPbest > 0.0)
                    throw new IllegalArgumentException("Redefinition of min-max-s-p-best is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("min-max-s-global-iterations cannot be specified if Min-Max global update was not specified");

                this.minMaxSystemPbest = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--min-max-s-global-best-yes"))
            {
                if (this.minMaxSystemGlobalBest != -1)
                    throw new IllegalArgumentException("Redefinition of min-max-s-global-best is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("min-max-s-global-iterations cannot be specified if Min-Max global update was not specified");

                this.minMaxSystemGlobalBest = 1;
                i++;
                continue;
            }

            if (args[i].equals("--min-max-s-global-best-no"))
            {
                if (this.minMaxSystemGlobalBest != -1)
                    throw new IllegalArgumentException("Redefinition of min-max-s-global-best is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("min-max-s-global-iterations cannot be specified if Min-Max global update was not specified");

                this.minMaxSystemGlobalBest = 2;
                i++;
                continue;
            }

            if (args[i].equals("--min-max-s-global-iterations"))
            {
                if (this.minMaxSystemGlobalIterations != -1)
                    throw new IllegalArgumentException("Redefinition of min-max-s-global-iterations is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("min-max-s-global-iterations cannot be specified if Min-Max global update was not specified");

                if (this.minMaxSystemGlobalBest != 1)
                    throw new IllegalArgumentException("min-max-s-global-best-yes must be specified");

                this.minMaxSystemGlobalIterations = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--min-max-s-pts-yes"))
            {
                if (this.minMaxSystemPts != -1)
                    throw new IllegalArgumentException("Redefinition of min-max-s-pts is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("min-max-s-global-iterations cannot be specified if Min-Max global update was not specified");

                this.minMaxSystemPts = 1;
                i++;
                continue;
            }

            if (args[i].equals("--min-max-s-pts-no"))
            {
                if (this.minMaxSystemPts != -1)
                    throw new IllegalArgumentException("Redefinition of min-max-s-pts is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("min-max-s-global-iterations cannot be specified if Min-Max global update was not specified");

                this.minMaxSystemPts = 2;
                i++;
                continue;
            }

            if (args[i].equals("--min-max-s-pts-iterations"))
            {
                if (this.minMaxSystemPtsIterations != -1)
                    throw new IllegalArgumentException("Redefinition of min-max-s-pts-iterations is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("min-max-s-pts-iterations cannot be specified if Min-Max global update was not specified");

                if (this.minMaxSystemPts != 1)
                    throw new IllegalArgumentException("min-max-s-pts-yes must be specified");

                this.minMaxSystemPtsIterations = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--pts-lambda"))
            {
                if (this.ptsLambda >= 0.0)
                    throw new IllegalArgumentException("Redefinition of pts-lambda is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("pts-lambda cannot be specified if Min-Max global update was not specified");

                if (this.minMaxSystemPts != 1)
                    throw new IllegalArgumentException("min-max-s-pts-yes must be specified");

                this.ptsLambda = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--pts-ratio"))
            {
                if (this.ptsRatio >= 0.0)
                    throw new IllegalArgumentException("Redefinition of --pts-ratio is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("pts-ratio cannot be specified if Min-Max global update was not specified");

                if (this.minMaxSystemPts != 1)
                    throw new IllegalArgumentException("min-max-s-pts-yes must be specified");

                this.ptsRatio = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--pts-delta"))
            {
                if (this.ptsDelta >= 0.0)
                    throw new IllegalArgumentException("Redefinition of pts-lambda is not allowed");

                if (this.globalUpdate != 3)
                    throw new IllegalArgumentException("pts-delta cannot be specified if Min-Max global update was not specified");

                if (this.minMaxSystemPts != 1)
                    throw new IllegalArgumentException("min-max-s-pts-yes must be specified");

                this.ptsDelta = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--ras-w"))
            {
                if (this.rasW != -1)
                    throw new IllegalArgumentException("Redefinition of ras-w is not allowed");

                if (this.globalUpdate != 4)
                    throw new IllegalArgumentException("ras-w cannot be specified if RAS global update was not specified");

                this.rasW = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--ras-bounded-yes"))
            {
                if (this.rasBounded != -1)
                    throw new IllegalArgumentException("Redefinition of ras-bounded is not allowed");

                if (this.globalUpdate != 4)
                    throw new IllegalArgumentException("ras-bounded cannot be specified if RAS global update was not specified");

                this.rasBounded = 1;
                i++;
                continue;
            }

            if (args[i].equals("--ras-bounded-no"))
            {
                if (this.rasBounded != -1)
                    throw new IllegalArgumentException("Redefinition of ras-bounded is not allowed");

                if (this.globalUpdate != 4)
                    throw new IllegalArgumentException("ras-bounded cannot be specified if RAS global update was not specified");

                this.rasBounded = 2;
                i++;
                continue;
            }

            if (args[i].equals("--ras-k"))
            {
                if (this.rasK >= 0)
                    throw new IllegalArgumentException("Redefinition of ras-k is not allowed");

                if (this.globalUpdate != 4)
                    throw new IllegalArgumentException("ras-k cannot be specified if RAS global update was not specified");

                this.rasK = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--eas-m-elite"))
            {
                if (this.easM != -1)
                    throw new IllegalArgumentException("Redefinition of eas-m-elite is not allowed");

                if (this.globalUpdate != 5)
                    throw new IllegalArgumentException("eas-m-elite cannot be specified if EAS global update was not specified");

                this.easM = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--eas-bounded-yes"))
            {
                if (this.easIsBounded != -1)
                    throw new IllegalArgumentException("Redefinition of eas-bounded is not allowed");

                if (this.globalUpdate != 5)
                    throw new IllegalArgumentException("eas-bounded cannot be specified if EAS global update was not specified");

                this.easIsBounded = 1;
                i++;
                continue;
            }

            if (args[i].equals("--eas-bounded-no"))
            {
                if (this.easIsBounded != -1)
                    throw new IllegalArgumentException("Redefinition of eas-bounded is not allowed");

                if (this.globalUpdate != 5)
                    throw new IllegalArgumentException("eas-bounded cannot be specified if EAS global update was not specified");

                this.easIsBounded = 2;
                i++;
                continue;
            }

            if (args[i].equals("--eas-k"))
            {
                if (this.easK >= 0.0)
                    throw new IllegalArgumentException("Redefinition of eas-k is not allowed");

                if (this.globalUpdate != 5)
                    throw new IllegalArgumentException("eas-k cannot be specified if EAS global update was not specified");

                this.easK = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--bwas-mutation-probability"))
            {
                if (this.bwasProbability >= 0.0)
                    throw new IllegalArgumentException("Redefinition of bwas-mutation-probability is not allowed");

                if (this.globalUpdate != 6)
                    throw new IllegalArgumentException("bwas-mutation-probability cannot be specified if EAS global update was not specified");

                this.bwasProbability = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--reinitialization-yes"))
            {
                if (this.reinitialization != -1)
                    throw new IllegalArgumentException("Redefinition of reinitialization is not allowed");

                this.reinitialization = 1;
                i++;
                continue;
            }

            if (args[i].equals("--reinitialization-no"))
            {
                if (this.reinitialization != -1)
                    throw new IllegalArgumentException("Redefinition of reinitialization is not allowed");

                this.reinitialization = 2;
                i++;
                continue;
            }

            if (args[i].equals("--reinitialization-time"))
            {
                if (this.reinitializationTime != -1)
                    throw new IllegalArgumentException("Redefinition of reinitialization-time is not allowed");

                if (this.reinitialization != 1)
                    throw new IllegalArgumentException("reinitialization-time cannot be specified without reinitialization-yes");

                this.reinitializationTime = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--top-k"))
            {
                if (this.topK != -1)
                    throw new IllegalArgumentException("Redefinition of top-k is not allowed");

                if (this.iteratedGreedy != 2)
                    throw new IllegalArgumentException("top-k cannot be specified without external-memory");

                this.topK = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--memory-size"))
            {
                if (this.memorySize != -1)
                    throw new IllegalArgumentException("Redefinition of memory-size is not allowed");

                if (this.iteratedGreedy != 2)
                    throw new IllegalArgumentException("memory-size cannot be specified without external-memory");

                this.memorySize = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--tournament-selector-size"))
            {
                if (this.tournamentSelectorSize != -1)
                    throw new IllegalArgumentException("Redefinition of tournament-selector-size is not allowed");

                if (this.iteratedGreedy != 2)
                    throw new IllegalArgumentException("tournament-selector-size cannot be specified without external-memory");

                this.tournamentSelectorSize = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--iterated-criteria-best"))
            {
                if (this.iteratedCriteria != -1)
                    throw new IllegalArgumentException("Redefinition of iterated-criteria is not allowed");

                if (this.iteratedGreedy != 3)
                    throw new IllegalArgumentException("iterated-criteria cannot be specified without iterated-ants");

                this.iteratedCriteria = 1;
                i++;
                continue;
            }

            if (args[i].equals("--iterated-criteria-probabilistic"))
            {
                if (this.iteratedCriteria != -1)
                    throw new IllegalArgumentException("Redefinition of iterated-criteria-probabilistic is not allowed");

                if (this.iteratedGreedy != 3)
                    throw new IllegalArgumentException("iterated-criteria-probabilistic cannot be specified without iterated-ants");

                this.iteratedCriteria = 2;
                i++;
                continue;
            }

            if (args[i].equals("--probabilistic-best"))
            {
                if (this.probabilisticBest >= 0.0)
                    throw new IllegalArgumentException("Redefinition of probabilistic-best is not allowed");

                if (this.iteratedGreedy != 3)
                    throw new IllegalArgumentException("probabilistic-best cannot be specified without iterated-ants");

                if (this.iteratedCriteria != 2)
                    throw new IllegalArgumentException("probabilistic-best cannot be specified without iterated-criteria-probabilistic");

                this.probabilisticBest = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            if (args[i].equals("--destruction-probability"))
            {
                if (this.destructionProbability >= 0.0)
                    throw new IllegalArgumentException("Redefinition of destruction-probability is not allowed");

                if (!((this.iteratedGreedy == 2) || (this.iteratedGreedy == 3) || (this.iteratedGreedy == 4)))
                    throw new IllegalArgumentException("destruction-probability can only be specified in iterated greedy heuristics");

                this.destructionProbability = Double.parseDouble(args[i + 1]);
                i += 2;
                continue;
            }

            throw new IllegalArgumentException("Unknown parameter [" + args[i] + "] has been found");
        }

        checkConfiguration();
    }



    /**
     * Checks the consistency of the read configuration and throws an exception if found
     */
    protected void checkConfiguration()
    {
        super.checkConfiguration();

        checkGeneralConstraints();

        checkGlobalUpdateConstraints();

        if (selector == 2)
            if (probabilityDorigo < 0.0)
                throw new IllegalArgumentException("probabilityDorigo must be specified");

        if (localUpdate == 1)
        {
            if (lupdEpsilon < 0.0)
                throw new IllegalArgumentException("lupdEpsilon must be specified");

            if (lupdTau0 < 0.0)
                throw new IllegalArgumentException("lupdTau0 must be specified");
        }

        if (localSearch == 2)
            if (ilsIterations == -1)
                throw new IllegalArgumentException("ilsIterations must be specified");

        if (iteratedGreedy == 2)
        {
            if (topK == -1)
                throw new IllegalArgumentException("topK must be specified");

            if (memorySize == -1)
                throw new IllegalArgumentException("memorySize must be specified");

            if (tournamentSelectorSize == -1)
                throw new IllegalArgumentException("tournamentSelectorSize must be specified");
        }

        if (iteratedGreedy == 3)
        {
            if (iteratedCriteria == -1)
                throw new IllegalArgumentException("iteratedCriteria must be specified");

            if (iteratedCriteria == 2)
                if (probabilisticBest < 0.0)
                    throw new IllegalArgumentException("probabilisticBest must be specified");
        }

        if (iteratedGreedy != 1)
            if (destructionProbability < 0.0)
                throw new IllegalArgumentException("destructionProbability must be specified");
    }

    protected void checkGeneralConstraints()
    {
        if (this.selector == -1)
            throw new IllegalArgumentException("selector must be specified");

        if (this.candidate == -1)
            throw new IllegalArgumentException("candidate must be specified");

        if (this.globalUpdate == -1)
            throw new IllegalArgumentException("globalUpdate must be specified");

        if (this.iteratedGreedy == -1)
            throw new IllegalArgumentException("iteratedGreedy must be specified");

        if (this.localSearch == -1)
            throw new IllegalArgumentException("localSearch must be specified");

        if (this.reinitialization == -1)
            throw new IllegalArgumentException("reinitialization must be specified");

        if (this.antNum == -1)
            throw new IllegalArgumentException("antNum must be specified");

        if (this.runtime == -1)
            throw new IllegalArgumentException("runtime must be specified");

        if (reinitialization == 1)
            if (reinitializationTime == -1)
                throw new IllegalArgumentException("reinitializationTime must be specified");
    }

    protected void checkGlobalUpdateConstraints()
    {
        if (this.alpha == -1)
            throw new IllegalArgumentException("alpha must be initialized");

        if ((this.selector == 1) || (this.selector == 2))
        {
            if (this.beta == -1)
                throw new IllegalArgumentException("beta must be initialized");

            if (this.localUpdate == -1)
                throw new IllegalArgumentException("localUpdate must be initialized");
        }

        if (this.candidate == 1)
            if (this.candidateRatio < 0.0)
                throw new IllegalArgumentException("candidateRatio must be initialized");

        if (this.globalUpdate == 1)
        {
            if (antSystemIsBounded == -1)
                throw new IllegalArgumentException("antSystemIsBounded must be initialized");

            if (antSystemIsBounded == 1)
                if (antSystemK < 0.0)
                    throw new IllegalArgumentException("antSystemK must be initialized");
        }

        if (this.globalUpdate == 3)
        {
            if (minMaxSystemPbest < 0.0)
                throw new IllegalArgumentException("minMaxSystemPbest must be initialized");

            if (minMaxSystemGlobalBest == -1)
                throw new IllegalArgumentException("minMaxSystemGlobalBest must be initialized");

            if (minMaxSystemGlobalBest == 1)
                if (minMaxSystemGlobalIterations == -1)
                    throw new IllegalArgumentException("minMaxSystemGlobalIterations must be initialized");

            if (minMaxSystemPts == -1)
                throw new IllegalArgumentException("minMaxSystemPts must be initialized");

            if (minMaxSystemPts == 1)
                if (minMaxSystemPtsIterations == -1)
                    throw new IllegalArgumentException("minMaxSystemPtsIterations must be initialized");

            if (minMaxSystemPts == 1)
            {
                if (ptsLambda < 0.0)
                    throw new IllegalArgumentException("ptsLambda must be initialized");

                if (ptsRatio < 0.0)
                    throw new IllegalArgumentException("ptsRatio must be initialized");

                if (ptsDelta < 0.0)
                    throw new IllegalArgumentException("ptsDelta must be initialized");
            }
        }

        if (this.globalUpdate == 4)
        {
            if (this.rasW == -1)
                throw new IllegalArgumentException("rasW must be initialized");

            if (this.rasBounded == -1)
                throw new IllegalArgumentException("rasBounded must be initialized");

            if (this.rasK == -1)
                throw new IllegalArgumentException("rasK must be initialized");
        }

        if (this.globalUpdate == 5)
        {
            if (this.easM == -1)
                throw new IllegalArgumentException("easM must be initialized");

            if (this.easIsBounded == -1)
                throw new IllegalArgumentException("easIsBounded must be initialized");

            if (this.easK < 0.0)
                throw new IllegalArgumentException("easK must be initialized");
        }

        if (this.globalUpdate == 6)
            if (this.bwasProbability < 0.0)
                throw new IllegalArgumentException("bwasProbability must be initialized");
    }
}
