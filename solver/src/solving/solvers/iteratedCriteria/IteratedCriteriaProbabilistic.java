package solving.solvers.iteratedCriteria;

import general.Main;
import solving.solution.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Picks up the best of the pair with the specified probability
 * Created by Aldar on 22-Nov-17.
 */
public class IteratedCriteriaProbabilistic extends IteratedCriteria
{
    protected double probabilityBest;

    public IteratedCriteriaProbabilistic(double probabilityBest)
    {
        this.probabilityBest = probabilityBest;

        if ((probabilityBest < 0.0) || (probabilityBest > 1.0))
            throw new IllegalArgumentException("Wrong iterated-ants criteria probability");
    }

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

            if (Main.random.nextDouble() < probabilityBest)
                result.add(one.betterThan(two) ? one : two);
            else
                result.add(one.betterThan(two) ? two : one);
        }

        return result;
    }
}
