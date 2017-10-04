package solution.selectors;

import problem.Component;
import java.util.List;
import java.util.Random;

/**
 * Created by Aldar on 28-Sep-17.
 */
public abstract class Selector
{
    protected final Random random;

    public Selector()
    {
        random = new Random();
    }

    public abstract Component select(List<Component> components) throws Exception;
}

