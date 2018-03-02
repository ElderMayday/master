package solving.localSearch.localMove;

import solving.solution.Solution;

/**
 * Iteratively performs local search moves by searching all the neighbourhood until convergence FOR EVERY TOUR
 * Created specifically for IteratedLocalSearch family
 * Created by Aldar on 20-Feb-18.
 */
public abstract class Move
{
    public abstract Solution move(Solution solution);
}
