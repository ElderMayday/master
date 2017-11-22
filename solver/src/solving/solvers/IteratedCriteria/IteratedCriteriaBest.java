package solving.solvers.IteratedCriteria;

import solving.solution.Solution;
import solving.solvers.IteratedCriteria.IteratedCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Picks up the best from each pair
 * Created by Aldar on 22-Nov-17.
 */
public class IteratedCriteriaBest extends IteratedCriteria
{
    @Override
    public List<Solution> Decide(List<Solution> listOne, List<Solution> listTwo)
    {
        if (listOne.size() != listTwo.size())
            throw new IllegalArgumentException("List sizes do not match");

        List<Solution> result = new ArrayList<Solution>();

        for (int i = 0; i < listOne.size(); i++)
        {
            Solution one = listOne.get(i);
            Solution two = listTwo.get(i);

            result.add(one.betterThan(two) ? one : two);
        }

        return result;
    }
}
