package solving.selectors;

import problem.component.Component;

import java.util.List;

/**
 * Created by Aldar on 04-Oct-17.
 */
public class SelectorManiezzo extends Selector
{
    protected final double alpha;
    protected final double alphaComplement;

    public SelectorManiezzo(double alpha)
    {
        this.alpha = alpha;
        this.alphaComplement = 1.0 - alpha;
    }


    @Override
    public Component select(List<Component> components) throws Exception
    {
        double sum = 0.0;

        for (Component component : components)
            sum += alpha * component.getPheromone() + alphaComplement * component.getHeuristic();

        double dice = random.nextDouble() * sum;      // a value from [0-sum)

        int componentsLeft = components.size();
        for (Component component : components)
        {
            if (componentsLeft > 1)
            {
                dice -= alpha * component.getPheromone() + alphaComplement * component.getHeuristic();

                if (dice < 0)                         // examining whether current component was chosen
                    return component;

                componentsLeft--;
            }
            else
                return component;                    // the last component left is sure to be chosen
        }

        throw new Exception("Solution component has not been chosen");  // 0 components were proposed
    }
}
