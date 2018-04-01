package general;

/**
 * Run configuration designed for any problem types
 * Created by Aldar on 15-Mar-18.
 */
public class Configuration
{
    protected int seed = 28;

    public int selector = -1; // -1 - not defined, 1 - standard, 2 - dorigo, 3 - maniezzo
    public double alpha = -1.0, beta = -1.0;
    public double probabilityDorigo = -1.0;

    public int candidate = -1;  // -1 - not defined, 0 - no, 1 - yes
    public double candidateRatio = -1;

    public int globalUpdate = -1;
    public int localUpdate = -1;
    public int iteratedGreedy = -1;
    public int antNum = -1;
    public int runtime = -1;

    public double lupdEpsilon = -1.0;
    public double lupdTau0 = -1.0;

    public int localSearch = 1;
    public int ilsIterations = -1;

    public double evaporationRemains = -1.0;

    public int antSystemIsBounded = -1;
    public double antSystemK = -1;

    public double minMaxSystemPbest = -1.0;
    public int minMaxSystemGlobalBest = -1;
    public int minMaxSystemGlobalIterations = -1;
    public int minMaxSystemPts = -1;
    public int minMaxSystemPtsIterations = -1;

    public double ptsLambda = -1.0;
    public double ptsRatio = -1.0;
    public double ptsDelta = -1.0;

    public int rasW = -1;
    public int rasBounded = -1;
    public double rasK = -1.0;

    public int easM = -1;
    public int easIsBounded = -1;
    public double easK = -1.0;

    public double bwasProbability = -1.0;

    public int reinitialization = -1;
    public int reinitializationTime = -1;

    public int topK = -1;
    public int memorySize = -1;
    public int tournamentSelectorSize = -1;

    public int iteratedCriteria = -1;
    public double probabilisticBest = -1.0;

    public double destructionProbability = -1.0;

    /**
     * Checks the consistency of the read configuration and throws an exception if found
     */
    protected void checkConfiguration()
    {
        if (selector == -1)  // selector was not selected
            throw new IllegalArgumentException("Selector was not selected");

        if (candidate == -1)  // candidate option was no selected
            throw new IllegalArgumentException("Candidate option was not selected");

        // TODO finish all the constraints
    }
}
