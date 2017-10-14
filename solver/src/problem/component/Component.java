package problem.component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 28-Sep-17.
 */
public class Component
{
    protected static double alpha = 1.0;
    protected static double beta = 1.0;

    /**
     * Sets alpha and beta selection parameters. IMPORTANT!!! After calling this, the old component re-computation should be called manually if existed
     * @param alpha
     * @param beta
     */
    public static void setPrecomputationParameters(double alpha, double beta)
    {
        Component.alpha = alpha;
        Component.beta = beta;
    }

    //------------------------------------------------------------




    protected double heuristic;                   // H-value
    protected double pheromone;                   // T-value
    protected double value;                       // t^alpha x h^beta value (if has been precomputed)
    protected boolean isPrecomputed;              // true if the current <value> corresponds to the value of t^alpha x h^beta
    protected final List<Component> candidates;   // List of the solution components that have been chosen as candidates


    public Component(double heuristic, double pheromone)
    {
        this.heuristic = heuristic;
        this.pheromone = pheromone;
        this.isPrecomputed = false;
        this.candidates = new ArrayList<Component>();
    }

    public void precompute()
    {
        this.value = Math.pow(pheromone, alpha) * Math.pow(heuristic, beta);    // 0^0 = 1 in java (just to remember)
        this.isPrecomputed = true;
    }


    //---------------------------------------------

    public double getHeuristic() {
        return heuristic;
    }

    public double getPheromone() {
        return pheromone;
    }

    public void setPheromone(double pheromone)
    {
        this.isPrecomputed = false;
        this.pheromone = pheromone;
    }

    public void setHeuristic(double heuristic)
    {
        this.isPrecomputed = false;
        this.heuristic = heuristic;
    }

    public double getValue()
    {
        if (!isPrecomputed)
            precompute();

        return this.value;
    }
}
