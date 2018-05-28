import org.junit.Test;
import problem.componentStructure.ComponentStructure2d;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.solution.SolutionVRP;
import solving.solution.Tour;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by Aldar on 10-May-18.
 */
public class TestSpecialRun
{
    /**
     * It is tested because we have found a better result for this instance than the publicly known
     */
    @Test
    public void TestCMT13()
    {

        try
        {
            ComponentStructure2d structure = new ComponentStructure2dStandard();
            ProblemVRP problem = new ProblemVRP(structure, new FleetDescendingCapacity(), null);
            problem.load(new File("irace\\test\\CMT13.vrp.txt"));
            SolutionVRP solution = new SolutionVRP(problem);

            solution.addConstructionComponent(structure.get(0,88));
            solution.addConstructionComponent(structure.get(88,82));
            solution.addConstructionComponent(structure.get(82,111));
            solution.addConstructionComponent(structure.get(111,86));
            solution.addConstructionComponent(structure.get(86,87));
            solution.addConstructionComponent(structure.get(87,92));
            solution.addConstructionComponent(structure.get(92,89));
            solution.addConstructionComponent(structure.get(89,91));
            solution.addConstructionComponent(structure.get(91,90));
            solution.addConstructionComponent(structure.get(90,18));
            solution.addConstructionComponent(structure.get(18,118));
            solution.addConstructionComponent(structure.get(118,114));
            solution.addConstructionComponent(structure.get(114,108));
            solution.addConstructionComponent(structure.get(108,109));
            solution.addConstructionComponent(structure.get(109,115));
            solution.addConstructionComponent(structure.get(115,110));
            solution.addConstructionComponent(structure.get(110,98));
            solution.addConstructionComponent(structure.get(98,116));
            solution.addConstructionComponent(structure.get(116,0));
            solution.addConstructionComponent(structure.get(0,119));
            solution.addConstructionComponent(structure.get(119,81));
            solution.addConstructionComponent(structure.get(81,112));
            solution.addConstructionComponent(structure.get(112,117));
            solution.addConstructionComponent(structure.get(117,113));
            solution.addConstructionComponent(structure.get(113,83));
            solution.addConstructionComponent(structure.get(83,84));
            solution.addConstructionComponent(structure.get(84,94));
            solution.addConstructionComponent(structure.get(94,96));
            solution.addConstructionComponent(structure.get(96,93));
            solution.addConstructionComponent(structure.get(93,95));
            solution.addConstructionComponent(structure.get(95,102));
            solution.addConstructionComponent(structure.get(102,101));
            solution.addConstructionComponent(structure.get(101,99));
            solution.addConstructionComponent(structure.get(99,100));
            solution.addConstructionComponent(structure.get(100,103));
            solution.addConstructionComponent(structure.get(103,104));
            solution.addConstructionComponent(structure.get(104,107));
            solution.addConstructionComponent(structure.get(107,106));
            solution.addConstructionComponent(structure.get(106,105));
            solution.addConstructionComponent(structure.get(105,120));
            solution.addConstructionComponent(structure.get(120,0));
            solution.addConstructionComponent(structure.get(0,85));
            solution.addConstructionComponent(structure.get(85,6));
            solution.addConstructionComponent(structure.get(6,7));
            solution.addConstructionComponent(structure.get(7,9));
            solution.addConstructionComponent(structure.get(9,10));
            solution.addConstructionComponent(structure.get(10,11));
            solution.addConstructionComponent(structure.get(11,15));
            solution.addConstructionComponent(structure.get(15,14));
            solution.addConstructionComponent(structure.get(14,13));
            solution.addConstructionComponent(structure.get(13,8));
            solution.addConstructionComponent(structure.get(8,12));
            solution.addConstructionComponent(structure.get(12,20));
            solution.addConstructionComponent(structure.get(20,32));
            solution.addConstructionComponent(structure.get(32,35));
            solution.addConstructionComponent(structure.get(35,36));
            solution.addConstructionComponent(structure.get(36,34));
            solution.addConstructionComponent(structure.get(34,26));
            solution.addConstructionComponent(structure.get(26,21));
            solution.addConstructionComponent(structure.get(21,49));
            solution.addConstructionComponent(structure.get(49,97));
            solution.addConstructionComponent(structure.get(97,0));
            solution.addConstructionComponent(structure.get(0,1));
            solution.addConstructionComponent(structure.get(1,2));
            solution.addConstructionComponent(structure.get(2,3));
            solution.addConstructionComponent(structure.get(3,5));
            solution.addConstructionComponent(structure.get(5,4));
            solution.addConstructionComponent(structure.get(4,16));
            solution.addConstructionComponent(structure.get(16,17));
            solution.addConstructionComponent(structure.get(17,19));
            solution.addConstructionComponent(structure.get(19,25));
            solution.addConstructionComponent(structure.get(25,24));
            solution.addConstructionComponent(structure.get(24,22));
            solution.addConstructionComponent(structure.get(22,27));
            solution.addConstructionComponent(structure.get(27,30));
            solution.addConstructionComponent(structure.get(30,33));
            solution.addConstructionComponent(structure.get(33,31));
            solution.addConstructionComponent(structure.get(31,28));
            solution.addConstructionComponent(structure.get(28,29));
            solution.addConstructionComponent(structure.get(29,79));
            solution.addConstructionComponent(structure.get(79,0));
            solution.addConstructionComponent(structure.get(0,67));
            solution.addConstructionComponent(structure.get(67,69));
            solution.addConstructionComponent(structure.get(69,70));
            solution.addConstructionComponent(structure.get(70,71));
            solution.addConstructionComponent(structure.get(71,74));
            solution.addConstructionComponent(structure.get(74,75));
            solution.addConstructionComponent(structure.get(75,53));
            solution.addConstructionComponent(structure.get(53,55));
            solution.addConstructionComponent(structure.get(55,58));
            solution.addConstructionComponent(structure.get(58,60));
            solution.addConstructionComponent(structure.get(60,57));
            solution.addConstructionComponent(structure.get(57,59));
            solution.addConstructionComponent(structure.get(59,54));
            solution.addConstructionComponent(structure.get(54,52));
            solution.addConstructionComponent(structure.get(52,0));
            solution.addConstructionComponent(structure.get(0,73));
            solution.addConstructionComponent(structure.get(73,76));
            solution.addConstructionComponent(structure.get(76,68));
            solution.addConstructionComponent(structure.get(68,77));
            solution.addConstructionComponent(structure.get(77,78));
            solution.addConstructionComponent(structure.get(78,72));
            solution.addConstructionComponent(structure.get(72,80));
            solution.addConstructionComponent(structure.get(80,56));
            solution.addConstructionComponent(structure.get(56,63));
            solution.addConstructionComponent(structure.get(63,64));
            solution.addConstructionComponent(structure.get(64,62));
            solution.addConstructionComponent(structure.get(62,61));
            solution.addConstructionComponent(structure.get(61,65));
            solution.addConstructionComponent(structure.get(65,66));
            solution.addConstructionComponent(structure.get(66,43));
            solution.addConstructionComponent(structure.get(43,50));
            solution.addConstructionComponent(structure.get(50,0));
            solution.addConstructionComponent(structure.get(0,40));
            solution.addConstructionComponent(structure.get(40,45));
            solution.addConstructionComponent(structure.get(45,48));
            solution.addConstructionComponent(structure.get(48,51));
            solution.addConstructionComponent(structure.get(51,47));
            solution.addConstructionComponent(structure.get(47,46));
            solution.addConstructionComponent(structure.get(46,44));
            solution.addConstructionComponent(structure.get(44,41));
            solution.addConstructionComponent(structure.get(41,42));
            solution.addConstructionComponent(structure.get(42,39));
            solution.addConstructionComponent(structure.get(39,38));
            solution.addConstructionComponent(structure.get(38,37));
            solution.addConstructionComponent(structure.get(37,23));
            solution.addConstructionComponent(structure.get(23,0));

            checkConsistency(problem, solution);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void checkConsistency(ProblemVRP problem, SolutionVRP solution)
    {
        // check visited correctness

        boolean[] visited = new boolean[problem.problemSize()];

        visited[0] = true;

        for (Tour tour : solution.getTours())
        {
            List<Integer> customer = tour.getCustomers();

            // all tours start and end in the depot

            assertEquals(problem.getDepotId(), customer.get(0).intValue());
            assertEquals(problem.getDepotId(), customer.get(customer.size() - 1).intValue());
            assertTrue(tour.getLeftCapacity() >= 0);
            assertTrue(tour.getLeftCapacity() <= tour.getVehicle().capacity);

            assertTrue(tour.getLeftDistance() >= 0);
            assertTrue(!tour.getVehicle().hasLengthRestriction || (tour.getLeftDistance() <= tour.getVehicle().length));

            for (Integer i : customer)
            {
               if (i != 0)
               {
                   assertEquals(false, visited[i]);    // the customer was not visited several times
                   visited[i] = true;
               }
            }
        }

        // check the capacity consumption

        for (Tour tour : solution.getTours())
        {
            double consumed = 0.0;

            List<Integer> customer = tour.getCustomers();

            for (int i = 1; i < customer.size() - 1; i++)
                consumed += problem.getDemands()[customer.get(i)];

            assertEquals(consumed, tour.getUsedCapacity(), 0.01);
        }

        // check totalDistance

        double totalDistance = 0.0;

        for (Tour tour : solution.getTours())
        {
            double tourDistance = 0.0;
            List<Integer> customer = tour.getCustomers();

            for (int i = 0; i < customer.size() - 1; i++)
            {
                int from = customer.get(i);
                int to = customer.get(i + 1);

                tourDistance += problem.structure2d.get(from, to).getDistance();
            }

            totalDistance += tourDistance;

            assertEquals(tourDistance, tour.getUsedDistance(), 0.01);
        }

        assertEquals(totalDistance, solution.objective, 0.01);
    }
}
