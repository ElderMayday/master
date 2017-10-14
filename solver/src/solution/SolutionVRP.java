package solution;

import problem.Component;
import problem.Component2d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Aldar on 14-Oct-17.
 */
public class SolutionVRP extends Solution
{
    protected List<Component2d> components2d;


    public SolutionVRP()
    {
        components2d = new ArrayList<Component2d>();
    }

    @Override
    public void addComponent(Component component)
    {
        if (component instanceof Component2d)
            components2d.add((Component2d) component);
    }


    @Override
    public double objective()
    {
        return 0;
    }





    @Override
    public Iterator<Component> iterator()
    {
        return new SolutionVrpIterator();
    }




    class SolutionVrpIterator implements Iterator<Component>
    {
        protected Iterator<Component2d> component2dIterator;

        public SolutionVrpIterator()
        {
            component2dIterator = components2d.iterator();
        }

        public boolean hasNext()
        {
            return component2dIterator.hasNext();
        }

        public Component next()
        {
            return component2dIterator.next();
        }
    }
}
