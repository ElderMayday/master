package problem;

import java.util.List;

/**
 * Created by Aldar on 28-Sep-17.
 */
public class Component {

    protected static double alpha;
    protected static double beta;

    public static void setPrecomputationParameters(double alpha, double beta)
    {
        Component.alpha = alpha;
        Component.beta = beta;
    }

    //------------------------------------------------------------




    protected double heuristic;
    protected double pheromone;
    protected double value;
    protected boolean isPrecomputed;
    protected List<Component> candidates;


    public Component(double heuristic, double pheromone)
    {
        this.heuristic = heuristic;
        this.pheromone = pheromone;
        this.isPrecomputed = false;
    }

    public void precompute()
    {
        this.value = Math.pow(pheromone, alpha) * Math.pow(heuristic, beta);
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

    public double getPrecomputed()
    {
        if (!isPrecomputed)
            precompute();

        return this.value;
    }
}
