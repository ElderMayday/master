package solving.solutionDestroyer;

import problem.problemFormulation.Problem;
import solving.solution.Solution;

import java.util.Random;

/**
 * Created by Aldar on 02-Nov-17.
 */
public abstract class SolutionDestroyer
{
    protected Random random;

    public SolutionDestroyer()
    {
        random = new Random();
    }


    /**
     * Partially destroys the given solution according to a certain algorithm
     * @param solution
     * @return
     */
    public abstract Solution destroy(Solution solution) throws Exception;
}