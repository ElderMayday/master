import org.junit.Test;
import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2d;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.ProblemVRP;
import solving.candidateList.CandidateDeterminerVrpSorting;
import solving.localSearch.LocalSearch;
import solving.localSearch.LocalSearchTwoHalf;
import solving.localSearch.localMove.Move;
import solving.localSearch.localMove.MoveTwoHalf;
import solving.solution.Solution;
import solving.solution.SolutionVRP;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Aldar on 03-Mar-18.
 */
public class TestLocalSearchTwoHalf
{
    @Test
    public void testMoveTwoHalf_MinimalSetup()
    {
        try
        {
            ProblemVRP problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(2));
            problem.load(new File("solver/problem-samples/vrp-5-customers-no-length.my"));

            SolutionVRP solution = new SolutionVRP(problem);
            ComponentStructure2d structure = problem.structure2d;
            solution.addConstructionComponent(structure.get(0, 1));
            solution.addConstructionComponent(structure.get(1, 2));
            solution.addConstructionComponent(structure.get(2, 3));
            solution.addConstructionComponent(structure.get(3, 0));

            LocalSearch localSearch = new LocalSearchTwoHalf();
            localSearch.search(problem, solution);

            assertEquals(solution.getTours().size(), 1);

            List<Integer> customers = solution.getTours().get(0).getCustomers();

            assertEquals(customers.get(0).intValue(), 0);
            assertEquals(customers.get(1).intValue(), 2);
            assertEquals(customers.get(2).intValue(), 1);
            assertEquals(customers.get(3).intValue(), 3);
            assertEquals(customers.get(4).intValue(), 0);

            assertEquals(solution.objective, 49.2, 0.0001);

            List<Component2d> components = solution.getComponents2d();
            assertEquals(components.size(), 4);
            assertTrue(components.contains(structure.get(0, 2)));
            assertTrue(components.contains(structure.get(2, 1)));
            assertTrue(components.contains(structure.get(1, 3)));
            assertTrue(components.contains(structure.get(3, 0)));
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }



    @Test
    public void testMoveTwoHalf_10customers()
    {
        try
        {
            ProblemVRP problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(2));
            problem.load(new File("solver/problem-samples/vrp-10-customers-no-length.my"));

            SolutionVRP solution = new SolutionVRP(problem);
            ComponentStructure2d structure2d = problem.structure2d;
            solution.addConstructionComponent(structure2d.get(0, 1));
            solution.addConstructionComponent(structure2d.get(1, 2));
            solution.addConstructionComponent(structure2d.get(2, 3));
            solution.addConstructionComponent(structure2d.get(3, 4));
            solution.addConstructionComponent(structure2d.get(4, 5));
            solution.addConstructionComponent(structure2d.get(5, 6));
            solution.addConstructionComponent(structure2d.get(6, 7));
            solution.addConstructionComponent(structure2d.get(7, 8));
            solution.addConstructionComponent(structure2d.get(8, 9));
            solution.addConstructionComponent(structure2d.get(9, 10));
            solution.addConstructionComponent(structure2d.get(10, 0));

            LocalSearch localSearch = new LocalSearchTwoHalf();
            localSearch.search(problem, solution);

            double objective = solution.objective;
            solution.recomputeObjective();
            assertEquals(objective, solution.objective, 0.00001);
        } catch (Exception e)
        {
            assertTrue(false);
        }
    }



    @Test
    public void testMoveTwoHalf_10customers_2tours()
    {
        try
        {
            ProblemVRP problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(2));
            problem.load(new File("solver/problem-samples/vrp-10-customers-no-length.my"));

            SolutionVRP solution = new SolutionVRP(problem);
            ComponentStructure2d structure2d = problem.structure2d;
            solution.addConstructionComponent(structure2d.get(0, 1));
            solution.addConstructionComponent(structure2d.get(1, 2));
            solution.addConstructionComponent(structure2d.get(2, 3));
            solution.addConstructionComponent(structure2d.get(3, 4));
            solution.addConstructionComponent(structure2d.get(4, 5));
            solution.addConstructionComponent(structure2d.get(5, 0));

            solution.addConstructionComponent(structure2d.get(0, 6));
            solution.addConstructionComponent(structure2d.get(6, 7));
            solution.addConstructionComponent(structure2d.get(7, 8));
            solution.addConstructionComponent(structure2d.get(8, 9));
            solution.addConstructionComponent(structure2d.get(9, 10));
            solution.addConstructionComponent(structure2d.get(10, 0));

            LocalSearch localSearch = new LocalSearchTwoHalf();
            localSearch.search(problem, solution);

            double objective = solution.objective;
            solution.recomputeObjective();
            assertEquals(objective, solution.objective, 0.00001);
        } catch (Exception e)
        {
            assertTrue(false);
        }
    }

    @Test
    public void testMoveTwoHalf_CrossExchange()
    {
        try
        {
            ComponentStructure2d structure = new ComponentStructure2dStandard();
            ProblemVRP problem = new ProblemVRP(structure, new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(2));
            problem.load(new File("irace\\test\\CMT1.vrp.txt"));

            SolutionVRP solution = new SolutionVRP(problem);

            int[][] nodes = {{0,46,12,47,4,18,14,25,0},
                    {0,1,22,28,31,8,26,23,24,43,7,48,27,0},
                    {0,32,11,38,9,50,34,30,39,33,45,0},
                    {0,6,17,13,41,19,40,42,44,15,37,5,10,0},
                    {0,2,3,36,35,20,21,29,16,49,0}};

            for (int i = 0; i < nodes.length; i++)
                for (int j = 0; j < nodes[i].length - 1; j++)
                {
                    solution.addConstructionComponent(structure.get(nodes[i][j], nodes[i][j + 1]));
                }

            LocalSearch localSearch = new LocalSearchTwoHalf();

            localSearch.search(problem, solution);

            double objective = solution.objective;
            solution.recomputeObjective();
            assertEquals(objective, solution.objective, 0.00001);

        } catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
