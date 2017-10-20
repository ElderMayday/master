package solving.candidateDeterminer;

import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;

/**
 * Created by Aldar on 20-Oct-17.
 *
 * If one uses O(n^2) sorting and then selecting top-K elements then the whole candidate determination algorithm will consume O(n^3)
 */
public class CandidateDeterminerVrpClosest extends CandidateDeterminer
{
    protected int numberOfCandidates;

    public CandidateDeterminerVrpClosest(int numberOfCandidates)
    {
        this.numberOfCandidates = numberOfCandidates;
    }


    @Override
    public void determine(Problem problem) throws Exception
    {
        if (problem instanceof ProblemVRP)
        {

        }
        else
            throw new Exception("Wrong problem type");
    }
}
