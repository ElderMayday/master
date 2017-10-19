package solving.selectors;

import problem.component.Component;
import java.util.List;

/**
 * Created by Aldar on 01-Oct-17.
 * Several such selectors may disturb each other, since they statically store alpha and beta parameters for pre-computing
 */
public class SelectorStandard extends Selector
{
    public SelectorStandard(double alpha, double beta)
    {
        Component.setPrecomputationParameters(alpha, beta);
    }

    @Override
    public Component select(List<Component> components) throws Exception
    {
        double sum = 0.0;

        for (Component component : components)
            sum += component.getValue();

        double dice = random.nextDouble() * sum;      // a value from [0-sum)

        int componentsLeft = components.size();
        for (Component component : components)
        {
            if (componentsLeft > 1)
            {
                dice -= component.getValue();

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
