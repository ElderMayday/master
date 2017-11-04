package solving.selectors;

import org.junit.Test;
import problem.component.Component;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.*;
import problem.problemFormulation.ProblemVRP;
import solving.candidateList.CandidateDeterminerVrpSorting;
import solving.solution.Solution;
import solving.solution.SolutionVRP;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Aldar on 10-Oct-17.
 */
public class TestProblemVRP
{
    @Test
    public void testProblemVrpLoadInstance()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test.json"));

            Iterator<Vehicle> vehicleIterator = problem.fleet.getVehiclesIterator();
            Vehicle vehicle = vehicleIterator.next();

            assertEquals(vehicle.capacity, 120.0, 0.001);
            assertEquals(vehicle.hasLengthRestriction, false);

            assertEquals(problem.getVertexNum(), 4);
            assertEquals(problem.getDemands()[1], 20.0, 0.001);
            assertEquals(problem.getDepotId(), 0);
            assertEquals(problem.structure2d.get(0, 0).getDistance(), -1.0, 0.001);
            assertEquals(problem.structure2d.get(0, 0).getHeuristic(), 1.2, 0.001);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }


    /**
     *  Integration test of how does ProblemVrp define possible components based on the current SolutionVRP
     */
    @Test
    public void testProblemVrpGetNextComponents_WithoutCandidates_WithCapacityRestriction()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test-2.json"));

            Solution solution = new SolutionVRP(problem);

            List<Component> preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.size(), 3);
            assertEquals(preselected.get(0), problem.structure2d.get(0, 1));
            assertEquals(preselected.get(1), problem.structure2d.get(0, 2));
            assertEquals(preselected.get(2), problem.structure2d.get(0, 3));

            solution.addConstructionComponent(problem.structure2d.get(0, 2));
            preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.get(0), problem.structure2d.get(2, 1));
            assertEquals(preselected.get(1), problem.structure2d.get(2, 3));

            solution.addConstructionComponent(problem.structure2d.get(2, 3));
            preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.size(), 1);
            assertEquals(preselected.get(0), problem.structure2d.get(3, 0));   // Customer #1 is still unvisited but cannot be selected due to capacity constraint
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    public void testProblemVrpGetNextComponents_WithoutCandidates_WithLengthRestriction()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test-3.json"));

            Solution solution = new SolutionVRP(problem);

            List<Component> preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.size(), 3);
            assertEquals(preselected.get(0), problem.structure2d.get(0, 1));
            assertEquals(preselected.get(1), problem.structure2d.get(0, 2));
            assertEquals(preselected.get(2), problem.structure2d.get(0, 3));

            solution.addConstructionComponent(problem.structure2d.get(0, 2));
            preselected = problem.getConstructionComponents(solution);

            // cannot move to #1 cause won't be able to return to the depot then
            // but can move to #3 cause have 50 spare length units, which is more than 49 + 0

            assertEquals(preselected.size(), 1);
            assertEquals(preselected.get(0), problem.structure2d.get(2, 3));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    /**
     *  Integration test of how does ProblemVrp define possible components based on the current SolutionVRP
     */
    @Test
    public void testProblemVrpGetNextComponents_WithCandidates_WithCapacityRestriction()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(2));
            problem.load(new File("problem-samples/vrp-unit-test-2.json"));

            Solution solution = new SolutionVRP(problem);

            List<Component> preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.size(), 2);
            assertEquals(preselected.get(0), problem.structure2d.get(0, 1));  // c[0;2] must drop off since not in candidate-candidates
            assertEquals(preselected.get(1), problem.structure2d.get(0, 3));

            solution.addConstructionComponent(problem.structure2d.get(0, 2)); // disobeys the advice (not what will happen IRL, but for tests will go)
            preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.get(0), problem.structure2d.get(2, 1));
            assertEquals(preselected.get(1), problem.structure2d.get(2, 3));

            solution.addConstructionComponent(problem.structure2d.get(2, 3));
            preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.size(), 1);
            assertEquals(preselected.get(0), problem.structure2d.get(3, 0));   // Customer #1 is still unvisited but cannot be selected due to capacity constraint
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    public void testProblemVrpGetNextComponents_WithCandidates_WithLengthRestriction()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(2));
            problem.load(new File("problem-samples/vrp-unit-test-3.json"));

            Solution solution = new SolutionVRP(problem);

            List<Component> preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.size(), 2);
            assertEquals(preselected.get(0), problem.structure2d.get(0, 1));     // c[0;2] must drop off since not in candidate-candidates
            assertEquals(preselected.get(1), problem.structure2d.get(0, 3));

            solution.addConstructionComponent(problem.structure2d.get(0, 2));
            preselected = problem.getConstructionComponents(solution);

            // cannot move to #1 cause won't be able to return to the depot then, despite being in the candidate candidates
            // but can move to #3 cause have 50 spare length units, which is more than 49 + 0

            assertEquals(preselected.size(), 1);
            assertEquals(preselected.get(0), problem.structure2d.get(2, 3));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testProblemVrpGetNextComponents_WithCandidates_WithCandidateListExhausting()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(2));
            problem.load(new File("problem-samples/vrp-unit-test-4.json"));

            Solution solution = new SolutionVRP(problem);

            List<Component> preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.size(), 2);
            assertEquals(preselected.get(0), problem.structure2d.get(0, 1));     // c[0;2] must drop off since not in candidate-candidates
            assertEquals(preselected.get(1), problem.structure2d.get(0, 3));

            solution.addConstructionComponent(problem.structure2d.get(0, 3));
            preselected = problem.getConstructionComponents(solution);

            solution.addConstructionComponent(problem.structure2d.get(3, 0));
            preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.size(), 1);
            assertEquals(preselected.get(0), problem.structure2d.get(0, 2));  // for #1 capacity is not enough, #3 was already visited, so the only choice is to take the closest from the rest-list
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testProblemVrpGetNextComponents_WithCandidates_()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("problem-samples/vrp-unit-test-5.json"));

            SolutionVRP solution = new SolutionVRP(problem);

            List<Component> preselected = problem.getConstructionComponents(solution);

            assertEquals(preselected.size(), 1);
            assertEquals(preselected.get(0), problem.structure2d.get(0, 0));

            solution.addConstructionComponent(preselected.get(0));

            assertEquals(solution.getTours().size(), 2);
            assertEquals(solution.getTours().get(0).getCustomers().size(), 0);
            assertEquals(solution.getTours().get(0).getLeftCapacity(), 201.0, 0.001);
            assertEquals(solution.getTours().get(0).getLeftDistance(), 1.0, 0.001);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
