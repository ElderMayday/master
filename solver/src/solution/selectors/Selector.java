package solution.selectors;

import problem.Component;
import java.util.List;

/**
 * Created by Aldar on 28-Sep-17.
 */
public interface Selector
{
    Component select(List<Component> components);
}

