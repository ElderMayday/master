package solving.selectors;

import problem.component.Component;
import java.util.List;
import java.util.Random;

/**
 * Created by Aldar on 28-Sep-17.
 */
public abstract class Selector
{
    public abstract Component select(List<Component> components) throws Exception;
}

