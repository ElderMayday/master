package solving.solvers.IteratedCriteria;

import solving.solution.Solution;

import java.util.List;

/**
 * Receives two equal-size solution lists and forms a new list by picking a certain solution from each pair
 * Created by Aldar on 22-Nov-17.
 */
public abstract class IteratedCriteria
{
    public abstract List<Solution> Decide(List<Solution> listOne, List<Solution> listTwo);
}
