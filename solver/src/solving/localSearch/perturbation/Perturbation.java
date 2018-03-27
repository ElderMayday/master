package solving.localSearch.perturbation;

import solving.solution.Solution;

/**
 * Created by Aldar on 15-Feb-18.
 */
public abstract class Perturbation
{
    public abstract Solution perturbate(Solution solution);
}
