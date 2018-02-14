package solving.solutionDestroyer;

import problem.problemFormulation.Problem;
import solving.solution.Solution;

import java.lang.ref.Reference;
import java.util.Random;

/**
 * Created by Aldar on 02-Nov-17.
 */
public abstract class SolutionDestroyer
{
    /**
     * Partially destroys the given solution according to a certain algorithm
     * REMARK!!! all implementation MUST call destroyCommon() method
     * @param solution
     * @return
     */
    public abstract Solution destroy(Solution solution) throws Exception;


    /**
     * Common part of all destroyers
     */
    public void destroyCommon(Solution solution)
    {
        solution.memorizeLastObjective();
        solution.objective = -1.0;
    }
}
