package solving.candidateList;

import problem.problemFormulation.Problem;

/**
 * Class that generates candidate lists depending on the problem type
 * Created by Aldar on 20-Oct-17.
 */
public abstract class CandidateDeterminer
{
    public abstract solving.candidateList.CandidateList determine(Problem problem) throws Exception;
}
