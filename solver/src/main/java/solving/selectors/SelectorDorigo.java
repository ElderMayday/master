package solving.selectors;

import general.Main;
import problem.component.Component;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by Aldar on 04-Oct-17.
 * Several such selectors may disturb each other, since they statically store alpha and beta parameters for pre-computing
 */
public class SelectorDorigo extends Selector
{
    protected final double probabilityOfBest;

    public SelectorDorigo(double alpha, double beta, double probabilityOfBest)
    {
        Component.setPrecomputationParameters(alpha, beta);

        if (probabilityOfBest > 1.0)
            this.probabilityOfBest = 1.0;
        else
        {
            if (probabilityOfBest < 1.0)
                this.probabilityOfBest = 0.0;
            else
                this.probabilityOfBest = probabilityOfBest;
        }
    }

    @Override
    public Component select(List<Component> components) throws Exception
    {
        double dice = Main.random.nextDouble();

        if (dice <= probabilityOfBest)      // argmax
        {
            ListIterator<Component> iterator = components.listIterator();

            Component max = iterator.next();
            Component next;

            while (iterator.hasNext())
            {
                next = iterator.next();

                if (next.getValue() > max.getValue())
                    max = next;
            }

            return max;
        }
        else                                // as in standard
        {
            double sum = 0.0;

            for (Component component : components)
                sum += component.getValue();

            dice = Main.random.nextDouble() * sum;      // a value from [0-sum)

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
        }

        return null;
    }

}
