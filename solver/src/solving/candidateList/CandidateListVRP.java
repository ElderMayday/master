package solving.candidateList;

import java.util.ArrayList;

/**
 * Created by Aldar on 20-Oct-17.
 */
public class CandidateListVRP extends CandidateList
{
    public CandidateListVRP()
    {
        list = new ArrayList<ArrayList<Integer>>();
    }


    public ArrayList<ArrayList<Integer>> list;     // matrix of indexes of the candidates for each of the customers
}
