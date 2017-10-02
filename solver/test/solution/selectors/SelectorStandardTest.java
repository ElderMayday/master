package solution.selectors;

import org.junit.Test;
import problem.Component;
import problem.Component2d;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 02-Oct-17.
 */
public class SelectorStandardTest
{
    @Test
    public void select() throws Exception
    {
        Selector selector = new SelectorStandard();

        List<Component> components = new ArrayList<Component>();

        Component expectedComponent = new Component2d(1, 1, 1.0, 100.0);
        components.add(new Component2d(1, 2, 0.0, 100.0));
        components.add(expectedComponent);
        components.add(new Component2d(1, 3, 0.0, 100.0));

        Component result = selector.select(components);

        assertEquals(result, expectedComponent);
    }

}