package solution.selectors;

import org.junit.Test;
import problem.Component;
import problem.Component2d;
import solution.Solution;
import solution.SolutionVRP;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 14-Oct-17.
 */
public class TestSolutionVrp
{
    @Test
    public void testSolutionVrpIterator()
    {
        Solution solution = new SolutionVRP();

        Component c1 = new Component2d(0, 0, 1.0, 1.0);
        Component c2 = new Component2d(0, 0, 1.0, 2.0);
        Component c3 = new Component2d(0, 0, 1.0, 3.0);

        solution.addComponent(c1);
        solution.addComponent(c2);
        solution.addComponent(c3);

        Iterator<Component> componentIterator = solution.iterator();
        for (int i = 0; i < 3; i++)
        {
            Component component = componentIterator.next();

            if (i == 0)
                assertEquals(component, c1);

            if (i == 1)
                assertEquals(component, c2);

            if (i == 2)
                assertEquals(component, c3);
        }
    }
}
