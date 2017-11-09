package solving.selectors;

import org.junit.Test;
import problem.component.Component;
import problem.component.Component2d;
import solving.localUpdate.LocalUpdate;
import solving.localUpdate.LocalUpdateStandard;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 09-Nov-17.
 */
public class TestLocalUpdateStandard
{
    @Test
    public void testLocalUpdateStadard()
    {
        Component component = new Component2d(1, 1, 1.0, 1.0);

        LocalUpdate update = new LocalUpdateStandard(0.5, 0.5);

        update.update(component);

        assertEquals(component.getPheromone(), 0.75, 0.001);
    }
}
