package solving.candidateList;

import problem.componentStructure.ComponentStructure2d;
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
    public CandidateList determine(Problem problem) throws Exception
    {
        if (problem instanceof ProblemVRP)
        {
            CandidateListVRP list = new CandidateListVRP();

            ProblemVRP problemVRP = (ProblemVRP) problem;
            ComponentStructure2d structure2d = problemVRP.structure2d;
            int depotId = problemVRP.getDepotId();

            for (int row = 0; row < problemVRP.getVertexNum(); row++)
            {


                for (int column = 0; column < problemVRP.getVertexNum(); column++)
                {
                    //if ((column != row) && (column != depotId))

                }
            }

            return list;
        }
        else
            throw new Exception("Wrong problem type");
    }
}
