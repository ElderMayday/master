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
 * If one uses O(n^2) sorting and then selecting top-K elements then the whole candidates determination algorithm will consume O(n^3)
 */
public class CandidateDeterminerVrpSorting extends CandidateDeterminer
{
    protected double ratioOfCandidatesDesired;

    public CandidateDeterminerVrpSorting(double ratioOfCandidatesDesired)
    {
        this.ratioOfCandidatesDesired = ratioOfCandidatesDesired;
    }


    /**
     * Uses QuickSort to get the customers
     * @param problem
     * @return candidateList based on the <numberOfCandidatesDesired> closest customers for each customers
     * Does not include edge to back-to-the-depot and self-loop edges
     * @throws Exception
     */
    @SuppressWarnings("Since15")
    @Override
    public CandidateList determine(Problem problem) throws Exception
    {
        if (problem instanceof ProblemVRP)
        {
            CandidateListVRP candidateList = new CandidateListVRP();

            ProblemVRP problemVRP = (ProblemVRP) problem;
            ComponentStructure2d structure2d = problemVRP.structure2d;
            int depotId = problemVRP.getDepotId();
            int numberOfCandidatesDesired = (int) (problem.ProblemSize() * ratioOfCandidatesDesired);

            for (int row = 0; row < problemVRP.getVertexNum(); row++)
            {
                ArrayList<Integer> candidateRow = new ArrayList<Integer>();
                ArrayList<Integer> restRow = new ArrayList<Integer>();

                candidateList.candidates.add(candidateRow);
                candidateList.rest.add(restRow);

                List<Component2d> allPossibleComponents = new ArrayList<Component2d>();

                for (int column = 0; column < problemVRP.getVertexNum(); column++)
                    if ((column != row) && (column != depotId))
                        allPossibleComponents.add(structure2d.get(row, column));

                allPossibleComponents.sort(new Component2dComparatorByDistance());

                int borderIndex = Math.min(numberOfCandidatesDesired, allPossibleComponents.size()); // when candidates end and rest starts

                for (int column = 0; column < borderIndex; column++)
                    candidateRow.add(allPossibleComponents.get(column).getColumn());   // get for ArrayList is O(1), so it's fine

                for (int column = borderIndex; column < allPossibleComponents.size(); column++)
                    restRow.add(allPossibleComponents.get(column).getColumn());
            }

            return candidateList;
        }
        else
            throw new Exception("Wrong problem type");
    }
}



class Component2dComparatorByDistance implements Comparator<Component2d>
{
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
