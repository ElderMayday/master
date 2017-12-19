package solving.solution;

import java.util.Comparator;

/**
 * Created by Aldar on 08-Nov-17.
 */
public class ComparatorSolution implements Comparator<Solution>
{
    @Override
    public int compare(Solution o1, Solution o2)
    {
        if (o1.betterThan(o2))
            return -1;
        else
        {
            if (o2.betterThan(o1))
                return 1;
            else
                return 0;
        }
    }
}
