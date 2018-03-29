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
                    throw new IllegalArgumentException("Selector parameters cannot be defined before the selector type");

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

                this.candidate = 0;
                i++;
                continue;
            }

            if (args[i].equals("--candidate-ratio"))
            {
                this.candidateRatio = Double.parseDouble(args[i + 1]);
                i += 2;

                if (candidate != 1)
                    throw new IllegalArgumentException("Candidate number cannot be defined without candidate flag");

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

                if (!((selector == 1) || (selector == 2)))
                    throw new IllegalArgumentException("Local update must be only specified in case of standard or dorigo selector");

                this.localUpdate = 0;
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
                if (this.ilsIterations != 1)
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
                if (this.antSystemK != -1)
                    throw new IllegalArgumentException("Redefinition of ant-s-k is not allowed");

                if (this.globalUpdate != 1)
                    throw new IllegalArgumentException("ant-s-k cannot be specified if other global update was specified");

                if (this.antSystemIsBounded != 1)
                    throw new IllegalArgumentException("ant-s-bounded-yes must be specified");

                this.antSystemK = Integer.parseInt(args[i + 1]);
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
    }
}
