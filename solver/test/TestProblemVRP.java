import org.junit.Test;
import problem.component.Component;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.*;
import problem.problemFormulation.ProblemVRP;
import solving.candidateList.CandidateDeterminerVrpSorting;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;
import solving.solutionDestroyer.SolutionDestroyer;
import solving.solutionDestroyer.SolutionDestroyerVrpFixed;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Aldar on 10-Oct-17.
 */
public class TestProblemVRP
{
    @Test
    public void testProblemVrpLoadInstanceJson()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-unit-test.json"));

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

    @Test
    public void testProblemVrpLoadInstanceMy()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-10-customers.my"));

            assertEquals(problem.fleet.getVehicles().size(), 10);
            assertEquals(problem.fleet.getVehicles().get(0).capacity, 90.4, 0.0001);
            assertEquals(problem.fleet.getVehicles().get(0).length, 62.7, 0.0001);

            assertEquals(problem.lowerIsBetter, true);

            assertEquals(problem.getDepotId(), 0);

            assertEquals(problem.getVertexNum(), 11);
            assertEquals(problem.getDemands().length, 11);
            assertEquals(problem.getDemands()[1], 29.1, 0.001);
            assertEquals(problem.getDemands()[10], 29.3, 0.0001);

            assertEquals(problem.structure2d.get(0, 0).getDistance(), -1.0, 0.0001);
            assertEquals(problem.structure2d.get(0, 0).getHeuristic(), 1.0, 0.0001);

            assertEquals(problem.structure2d.get(0, 1).getDistance(), 14.3, 0.0001);
            assertEquals(problem.structure2d.get(0, 1).getHeuristic(), 1.0, 0.0001);

            assertEquals(problem.structure2d.get(10, 9).getDistance(), 52.1, 0.0001);
            assertEquals(problem.structure2d.get(10, 9).getHeuristic(), 1.0, 0.0001);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testProblemVrpLoadInstanceTai()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("irace/test/tai75a.tai"));

            int a = 1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testProblemVrpLoadInstanceTxt()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/X-n101-k25.vrp.txt"));

            assertEquals(problem.fleet.getVehicles().size(), 101);
            assertEquals(problem.fleet.getVehicles().get(0).capacity, 206.0, 0.0001);
            assertEquals(problem.fleet.getVehicles().get(0).length, 0.0, 0.0001);
            assertEquals(problem.fleet.getVehicles().get(0).hasLengthRestriction, false);

            assertEquals(problem.lowerIsBetter, true);

            assertEquals(problem.getDepotId(), 0);

            assertEquals(problem.getVertexNum(), 101);
            assertEquals(problem.getDemands().length, 101);
            assertEquals(problem.getDemands()[0], 0.0, 0.001);
            assertEquals(problem.getDemands()[10], 98.0, 0.0001);

            assertEquals(problem.structure2d.get(0, 0).getDistance(), -1.0, 0.0001);
            assertEquals(problem.structure2d.get(0, 0).getHeuristic(), -1.0, 0.0001);

            assertEquals(problem.structure2d.get(0, 1).getDistance(), 554.11, 0.01);
            assertEquals(problem.structure2d.get(0, 1).getHeuristic(), 0.0018, 0.0001);

            assertEquals(problem.structure2d.get(10, 9).getDistance(), 440.696, 0.0001);
            assertEquals(problem.structure2d.get(10, 9).getHeuristic(), 0.0023, 0.0001);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    public void testProblemVrpLoadInstanceTxt_SkipDepotDemand()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/CMT1.vrp.txt"));

            assertEquals(0.0, problem.demands[0], 0.001);
            assertEquals(7.0, problem.demands[1], 0.001);
            assertEquals(10.0, problem.demands[50], 0.001);

            assertEquals(13.8924, problem.structure2d.get(0, 1).getDistance(), 0.001);

            int a = 1;
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
    public void testProblemVrp_getConstructionComponents_withoutCandidates_checkCapacityRestriction()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-unit-test-2.json"));

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
    public void testProblemVrp_getConstructionComponents_withoutCandidates_checkLengthRestriction()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-unit-test-3.json"));

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
    public void testProblemVrp_getConstructionComponents__withCandidates_checkCapacityRestriction()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(0.5));
            problem.load(new File("solver/problem-samples/vrp-unit-test-2.json"));

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
    public void testProblemVrp_getConstructionComponents_withCandidates_checkLengthRestriction()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(0.5));
            problem.load(new File("solver/problem-samples/vrp-unit-test-3.json"));

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
    public void testProblemVrp_getConstructionComponent_withCandidates_checkCandidateListExhausting()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(0.5));
            problem.load(new File("solver/problem-samples/vrp-unit-test-4.json"));

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
    public void testProblemVrp_getConstructionComponents_withoutCandidates_checkEmptyTourSolutionAdditionToSolution()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-unit-test-5.json"));

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

    @Test
    public void testProblemVrp_getReconstructionComponents_withoutCandidates_justFinishTheOldTours()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-unit-test.json"));

            SolutionVRP solutionVRP = new SolutionVRP(problem);

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 2));
            solutionVRP.addConstructionComponent(problem.structure2d.get(2, 3));
            solutionVRP.addConstructionComponent(problem.structure2d.get(3, 1));
            solutionVRP.addConstructionComponent(problem.structure2d.get(1, 0));

            SolutionDestroyer destroyer = new SolutionDestroyerVrpFixed(2);

            solutionVRP = (SolutionVRP) destroyer.destroy(solutionVRP);

            List<Component> components = problem.getReconstructionComponents(solutionVRP);

            assertEquals(components.size(), 2);
            assertEquals(components.get(0), problem.structure2d.get(2, 1));
            assertEquals(components.get(1), problem.structure2d.get(2, 3));
            assertEquals(solutionVRP.getCurrentTour(), solutionVRP.getTours().get(0));
            assertEquals(solutionVRP.getCurrentCustomerId(), 2);

            solutionVRP.addReconstructionComponent(components.get(0));

            assertEquals(solutionVRP.getTours().size(), 1);
            Tour tour0 = solutionVRP.getTours().get(0);
            assertEquals(tour0.isFinished(), false);
            assertEquals(tour0.getCustomers().size(), 3);
            assertEquals(tour0.getCustomers().get(0).intValue(), 0);
            assertEquals(tour0.getCustomers().get(1).intValue(), 2);
            assertEquals(tour0.getCustomers().get(2).intValue(), 1);
            assertEquals(tour0.getLeftCapacity(), 70.0, 0.001);
            assertEquals(tour0.getLeftDistance(), 0.0, 0.001);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }



    @Test
    public void testProblemVrp_getReconstructionComponents_withoutCandidates_addNewTour()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-unit-test.json"));

            SolutionVRP solutionVRP = new SolutionVRP(problem);

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 2));
            solutionVRP.addConstructionComponent(problem.structure2d.get(2, 3));
            solutionVRP.addConstructionComponent(problem.structure2d.get(3, 1));
            solutionVRP.addConstructionComponent(problem.structure2d.get(1, 0));

            SolutionDestroyer destroyer = new SolutionDestroyerVrpFixed(2);

            solutionVRP = (SolutionVRP) destroyer.destroy(solutionVRP);

            List<Component> components = problem.getReconstructionComponents(solutionVRP);

            solutionVRP.addReconstructionComponent(problem.structure2d.get(2, 0));

            components = problem.getReconstructionComponents(solutionVRP);

            assertEquals(components.size(), 2);
            assertEquals(components.get(0), problem.structure2d.get(0, 1));
            assertEquals(components.get(1), problem.structure2d.get(0, 3));
        } catch (Exception e)
        {
            assertTrue(false);
        }
    }

    @Test
    public void testProblemVrp_getReconstructionComponents_withoutCandidates_withTourPruning()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-unit-test.json"));

            SolutionVRP solutionVRP = new SolutionVRP(problem);

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 2));
            solutionVRP.addConstructionComponent(problem.structure2d.get(2, 0));

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 3));
            solutionVRP.addConstructionComponent(problem.structure2d.get(3, 0));

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 1));
            solutionVRP.addConstructionComponent(problem.structure2d.get(1, 0));

            SolutionDestroyer destroyer = new SolutionDestroyerVrpFixed(1);

            solutionVRP = (SolutionVRP) destroyer.destroy(solutionVRP);

            List<Component> components = problem.getReconstructionComponents(solutionVRP);

            assertEquals(components.size(), 3);
            assertEquals(components.get(0), problem.structure2d.get(0, 1));
            assertEquals(components.get(1), problem.structure2d.get(0, 2));
            assertEquals(components.get(2), problem.structure2d.get(0, 3));

            solutionVRP.addReconstructionComponent(components.get(1));

            components = problem.getReconstructionComponents(solutionVRP);

            assertEquals(components.size(), 2);
            assertEquals(components.get(0), problem.structure2d.get(2, 1));
            assertEquals(components.get(1), problem.structure2d.get(2, 3));

            solutionVRP.addReconstructionComponent(components.get(1));

            components = problem.getReconstructionComponents(solutionVRP);

            assertEquals(components.size(), 1);
            assertEquals(components.get(0), problem.structure2d.get(3, 1));

            solutionVRP.addReconstructionComponent(components.get(0));

            components = problem.getReconstructionComponents(solutionVRP);

            assertEquals(components.size(), 1);
            assertEquals(components.get(0), problem.structure2d.get(1, 0));

            solutionVRP.addReconstructionComponent(components.get(0));

            assertEquals(solutionVRP.getTours().size(), 1);  // so the tours #1 and #2 were pruned since they were empty
            assertEquals(solutionVRP.getTours().get(0).getUsedDistance(), 184.0 , 0.001);
            assertEquals(solutionVRP.getTours().get(0).getUsedCapacity(), 90.0 , 0.001);
            assertEquals(solutionVRP.getTours().get(0).getLeftDistance(), 0.0 , 0.001);
            assertEquals(solutionVRP.getTours().get(0).getLeftCapacity(), 30.0 , 0.001);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }

    @Test
    public void testProblemVrp_getReconstructionComponents_withoutCandidates_withCandidates()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(1));
            problem.load(new File("solver/problem-samples/vrp-unit-test.json"));

            SolutionVRP solutionVRP = new SolutionVRP(problem);

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 2));
            solutionVRP.addConstructionComponent(problem.structure2d.get(2, 3));
            solutionVRP.addConstructionComponent(problem.structure2d.get(3, 1));
            solutionVRP.addConstructionComponent(problem.structure2d.get(1, 0));

            SolutionDestroyer destroyer = new SolutionDestroyerVrpFixed(2);
            solutionVRP = (SolutionVRP) destroyer.destroy(solutionVRP);

            List<Component> components = problem.getReconstructionComponents(solutionVRP);

            assertEquals(solutionVRP.getTours().size(), 1);
            assertEquals(components.get(0), problem.structure2d.get(2, 1));

            solutionVRP.addReconstructionComponent(problem.structure2d.get(2, 1));

            components = problem.getReconstructionComponents(solutionVRP);

            assertEquals(solutionVRP.getTours().size(), 1);
            assertEquals(components.get(0), problem.structure2d.get(1, 3));

            solutionVRP.addReconstructionComponent(problem.structure2d.get(1, 3));

            components = problem.getReconstructionComponents(solutionVRP);

            assertEquals(solutionVRP.getTours().size(), 1);
            assertEquals(components.get(0), problem.structure2d.get(3, 0));

            solutionVRP.addReconstructionComponent(problem.structure2d.get(3, 0));

            assertEquals(solutionVRP.isPartiallyDestroyed(), false);
            assertEquals(solutionVRP.getComplete(), true);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }


    @Test
    public void testProblemVrp_getReconstructionComponents_withoutCandidates_withCandidatesExhausting()
    {
        ProblemVRP problem;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), new CandidateDeterminerVrpSorting(0.25));
            problem.load(new File("solver/problem-samples/vrp-unit-test-4.json"));

            SolutionVRP solutionVRP = new SolutionVRP(problem);

            solutionVRP.addConstructionComponent(problem.structure2d.get(0, 2));
            solutionVRP.addConstructionComponent(problem.structure2d.get(2, 3));
            solutionVRP.addConstructionComponent(problem.structure2d.get(3, 1));
            solutionVRP.addConstructionComponent(problem.structure2d.get(1, 0));

            SolutionDestroyer destroyer = new SolutionDestroyerVrpFixed(1);
            solutionVRP = (SolutionVRP) destroyer.destroy(solutionVRP);

            List<Component> preselected = problem.getReconstructionComponents(solutionVRP);
            assertEquals(preselected.size(), 1);
            assertEquals(preselected.get(0), problem.structure2d.get(0, 1));
            solutionVRP.addReconstructionComponent(problem.structure2d.get(0, 3)); // disobeying preselection

            preselected = problem.getReconstructionComponents(solutionVRP);
            assertEquals(preselected.size(), 1);
            assertEquals(preselected.get(0), problem.structure2d.get(3, 2));
            solutionVRP.addReconstructionComponent(problem.structure2d.get(3, 0));

            preselected = problem.getReconstructionComponents(solutionVRP);
            assertEquals(preselected.size(), 1);
            assertEquals(preselected.get(0), problem.structure2d.get(0, 2));  // for #1 capacity is not enough, #3 was already visited, so the only choice is to take the closest from the rest-list
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }
}
