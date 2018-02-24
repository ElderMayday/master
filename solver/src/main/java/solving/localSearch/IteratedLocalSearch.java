package solving.localSearch;

import com.sun.security.auth.SolarisNumericUserPrincipal;
import problem.problemFormulation.Problem;
import solving.localSearch.localMove.Move;
import solving.localSearch.perturbation.Perturbation;
import solving.solution.Solution;

/**
 * Created by Aldar on 20-Feb-18.
 */
public class IteratedLocalSearch extends LocalSearch
{
    protected Perturbation perturbation;

    protected int iterations;

    public IteratedLocalSearch(Move move, Perturbation perturbation, int iterations)
    {
        super(move);

        this.perturbation = perturbation;
        this.iterations = iterations;
    }

    public Solution search(Problem problem, Solution solution)
    {
        Solution sStar = solution.deepCopy();
        sStar = move.move(sStar);

        int count = 0;

        do
        {
            count++;

            Solution sApo = sStar.deepCopy();
            sApo = perturbation.perturbate(sApo);

            sApo = move.move(sApo);

            if (sApo.objective < sStar.objective)
                sStar = sApo;
        }
        while (count < iterations);

        return null;
    }
}
