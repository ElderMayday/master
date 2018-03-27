package solving.solution;

import java.util.Comparator;

/**
 * Compares two solutions according to their last memorized objective values
 * Created by Aldar on 23-Nov-17.
 */
public class ComparatorSolutionLast implements Comparator<Solution>
{
    public int compare(Solution o1, Solution o2)
    {
        if (o1.betterThanLast(o2))
            return -1;
        else
        {
            if (o2.betterThanLast(o1))
                return 1;
            else
                return 0;
        }
    }
}
