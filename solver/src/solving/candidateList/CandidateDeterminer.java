package solving.candidateList;

import problem.problemFormulation.Problem;

/**
 * Created by Aldar on 20-Oct-17.
 */
public abstract class CandidateDeterminer
{
    public abstract CandidateList determine(Problem problem) throws Exception;
}
