import org.junit.Test;

import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.ProblemVRP;
import solving.candidateList.CandidateDeterminer;
import solving.candidateList.CandidateDeterminerVrpSorting;
import solving.candidateList.CandidateListVRP;

import java.io.File;
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

        assertEquals(allLists.candidates.size(), 4);

        List<Integer> list0 = allLists.candidates.get(0);
        assertEquals(list0.size(), 2);
        assertEquals(list0.get(0).intValue(), 1);
        assertEquals(list0.get(1).intValue(), 3);

        list0 = allLists.rest.get(0);
        assertEquals(list0.size(), 1);
        assertEquals(list0.get(0).intValue(), 2);

        List<Integer> list1 = allLists.candidates.get(1);
        assertEquals(list1.size(), 2);
        assertEquals(list1.get(0).intValue(), 3);
        assertEquals(list1.get(1).intValue(), 2);

        list1 = allLists.rest.get(1);
        assertEquals(list1.size(), 0);

        List<Integer> list2 = allLists.candidates.get(2);
        assertEquals(list2.size(), 2);
        assertEquals(list2.get(0).intValue(), 1);
        assertEquals(list2.get(1).intValue(), 3);

        list1 = allLists.rest.get(2);
        assertEquals(list1.size(), 0);

        List<Integer> list3 = allLists.candidates.get(3);
        assertEquals(list3.size(), 2);
        assertEquals(list3.get(0).intValue(), 2);
        assertEquals(list3.get(1).intValue(), 1);

        list1 = allLists.rest.get(3);
        assertEquals(list1.size(), 0);
    }
}
