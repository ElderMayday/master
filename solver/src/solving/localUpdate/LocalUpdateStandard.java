package solving.localUpdate;

import problem.component.Component;

/**
 * Standard local update, normally performed in combination with ACS
 * Created by Aldar on 09-Nov-17.
 */
public class LocalUpdateStandard extends LocalUpdate
{
    protected double epsilon; // strength of the local update
    protected double epsilonComplement;  // 1 - epsilon
    protected double tau0;   // minimal possible pheromone value
    protected double addedValue; // epsilon x tau0

    public LocalUpdateStandard(double epsilon, double tau0)
    {
        if ((epsilon < 0.0) || (epsilon > 1.0))
            throw new IllegalArgumentException("Wrong epsilon value");

        if (tau0 < 0.0)
            throw new IllegalArgumentException("Wrong tau0 value");

        this.epsilon = epsilon;
        this.epsilonComplement = 1 - epsilon;
        this.tau0 = tau0;
        this.addedValue = epsilon * tau0;
    }

    @Override
    public void update(Component component)
    {
        component.setPheromone(epsilonComplement * component.getPheromone() + addedValue);
    }
}
