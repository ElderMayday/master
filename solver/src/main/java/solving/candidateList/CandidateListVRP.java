package solving.candidateList;

import java.util.ArrayList;

/**
 * Created by Aldar on 20-Oct-17.
 */
public class CandidateListVRP extends CandidateList
{
    public CandidateListVRP()
    {
        candidates = new ArrayList<ArrayList<Integer>>();
        rest = new ArrayList<ArrayList<Integer>>();
    }


    public ArrayList<ArrayList<Integer>> candidates;     // matrix of indexes of the candidates for each of the customers (sorted by preference)

    public ArrayList<ArrayList<Integer>> rest;     // edges that did were not selected as candidates (sorted y preference)
}
