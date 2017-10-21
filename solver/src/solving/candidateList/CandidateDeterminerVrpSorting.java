package solving.candidateList;

import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2d;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Aldar on 20-Oct-17.
 * If one uses O(n^2) sorting and then selecting top-K elements then the whole list determination algorithm will consume O(n^3)
 */
public class CandidateDeterminerVrpSorting extends CandidateDeterminer
{
    protected int numberOfCandidates;

    public CandidateDeterminerVrpSorting(int numberOfCandidates)
    {
        this.numberOfCandidates = numberOfCandidates;
    }


    /**
     * Uses QuickSort to get the customers
     * @param problem
     * @return candidateList based on the <numberOfCandidates> closest customers for each customes
     * @throws Exception
     */
    @Override
    public CandidateList determine(Problem problem) throws Exception
    {
        if (problem instanceof ProblemVRP)
        {
            CandidateListVRP candidateList = new CandidateListVRP();

            ProblemVRP problemVRP = (ProblemVRP) problem;
            ComponentStructure2d structure2d = problemVRP.structure2d;
            int depotId = problemVRP.getDepotId();


            for (int row = 0; row < problemVRP.getVertexNum(); row++)
            {
                ArrayList<Integer> listRow = new ArrayList<Integer>();

                candidateList.list.add(listRow);

                List<Component2d> allPossibleComponents = new ArrayList<Component2d>();

                for (int column = 0; column < problemVRP.getVertexNum(); column++)
                {
                    if ((column != row) && (column != depotId))
                        allPossibleComponents.add(structure2d.get(row, column));
                }

                allPossibleComponents.sort(new Component2dComparatorByDistance());

                for (int column = 0; column < numberOfCandidates; column++)
                    listRow.add(allPossibleComponents.get(column).getColumn());
            }

            return candidateList;
        }
        else
            throw new Exception("Wrong problem type");
    }
}



class Component2dComparatorByDistance implements Comparator<Component2d>
{
    @Override
    public int compare(Component2d o1, Component2d o2)
    {
        if (o1.getDistance() > o2.getDistance())
            return 1;
        else
        {
            if (o1.getDistance() < o2.getDistance())
                return -1;
            else
                return 0;
        }
    }
}
