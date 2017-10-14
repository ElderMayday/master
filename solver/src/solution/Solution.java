package solution;

import problem.component.Component;

/**
 * Created by Aldar on 14-Oct-17.
 */
public abstract class Solution implements Iterable<Component>
{
    public Solution()
    {
    }

    public abstract void addComponent(Component component);

    public abstract double objective();
}
