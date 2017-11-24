package solving.solvers.tournamentSelector;

import solving.solution.Solution;

import java.util.List;

/**
 * Selects specified number of solutions from the solution list according to a certain rule
 * Created by Aldar on 23-Nov-17.
 */
public abstract class TournamentSelector
{
    public abstract List<Solution> select(List<Solution> solutions, int antNum);
}
