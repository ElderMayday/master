package solution.selectors;

import problem.Component;
import java.util.List;

/**
 * Created by Aldar on 01-Oct-17.
 */
public class SelectorStandard extends Selector
{
    @Override
    public Component select(List<Component> components) throws Exception
    {
        double sum = 0.0;

        for (Component component : components)
            sum += component.getValue();

        double dice = random.nextDouble() * sum;

        int componentsLeft = components.size();
        for (Component component : components)
        {
            if (componentsLeft > 1)
            {
                sum -= component.getValue();

                if (sum < 0)
                    return component;

                componentsLeft--;
            }
            else
                return component;
        }

        throw new Exception("Solution component has not been chosen");
    }
}
