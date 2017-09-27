package problem;

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
    protected double precomputed;



    public Component(double heuristic, double pheromone)
    {
        this.heuristic = heuristic;
        this.pheromone = pheromone;
    }

    public void precompute()
    {
        precomputed = Math.pow(pheromone, alpha) * Math.pow(heuristic, beta);
    }


    //---------------------------------------------

    public double getHeuristic() {
        return heuristic;
    }

    public double getPheromone() {
        return pheromone;
    }

    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }

    public double getPrecomputed() {
        return precomputed;
    }
}
