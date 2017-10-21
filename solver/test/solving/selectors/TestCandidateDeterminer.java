package solving.selectors;

import org.junit.Test;

import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.candidateList.CandidateDeterminer;
import solving.candidateList.CandidateDeterminerVrpSorting;
import solving.candidateList.CandidateList;
import solving.candidateList.CandidateListVRP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 21-Oct-17.
 */
public class TestCandidateDeterminer
{
    @Test
    public void testCandidateDeterminerVrpSorting()
    {
        ProblemVRP problem = null;

        try
        {
            CandidateDeterminer candidateDeterminer = new CandidateDeterminerVrpSorting(2);
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), candidateDeterminer);
            problem.load(new File("problem-samples/vrp-unit-test.json"));
        }
        catch (Exception e)
        {
            assertTrue(false);
        }

        CandidateListVRP allLists = (CandidateListVRP) problem.getCandidateList();

        assertEquals(allLists.list.size(), 4);

        List<Integer> list0 = allLists.list.get(0);
        assertEquals(list0.size(), 2);
        assertEquals(list0.get(0).intValue(), 1);
        assertEquals(list0.get(1).intValue(), 3);

        List<Integer> list1 = allLists.list.get(1);
        assertEquals(list1.size(), 2);
        assertEquals(list1.get(0).intValue(), 3);
        assertEquals(list1.get(1).intValue(), 2);

        List<Integer> list2 = allLists.list.get(2);
        assertEquals(list2.size(), 2);
        assertEquals(list2.get(0).intValue(), 1);
        assertEquals(list2.get(1).intValue(), 3);

        List<Integer> list3 = allLists.list.get(3);
        assertEquals(list3.size(), 2);
        assertEquals(list3.get(0).intValue(), 2);
        assertEquals(list3.get(1).intValue(), 1);
    }
}
