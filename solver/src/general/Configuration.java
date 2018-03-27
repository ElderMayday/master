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
    public int candidateNum = -1;

    public int globalUpdate = -1;
    public int localUpdate = -1;
    public int iteratedGreedy = -1;
    public int antNum = -1;

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
