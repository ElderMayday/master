package solution.selectors;

import org.junit.Test;
import problem.component.Component;
import problem.component.Component2d;
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

        Component2d c1 = new Component2d(0, 0, 1.0, 1.0);
        Component2d c2 = new Component2d(0, 0, 1.0, 2.0);
        Component2d c3 = new Component2d(0, 0, 1.0, 3.0);

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


    @Test
    public void testSolutionVrpObjective()
    {
        Solution solution = new SolutionVRP();

        Component2d c1 = new Component2d(0, 0, 1.0, 1.0);
        c1.setDistance(1.0);

        Component2d c2 = new Component2d(0, 0, 1.0, 2.0);
        c2.setDistance(2.0);

        Component2d c3 = new Component2d(0, 0, 1.0, 3.0);
        c3.setDistance(3.0);

        solution.addComponent(c1);
        solution.addComponent(c2);
        solution.addComponent(c3);

        assertEquals(solution.objective(), 6.0, 0.001);
    }
}
