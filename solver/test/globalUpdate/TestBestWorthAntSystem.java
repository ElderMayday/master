package globalUpdate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import general.Main;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import problem.componentStructure.ComponentStructure2d;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.ProblemVRP;
import solving.globalUpdate.BestWorstAntSystem;
import solving.globalUpdate.GlobalUpdate;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.pheromoneInitializer.PheromoneInitializerConstant;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.terminationCriteria.TerminationCriteria;
import solving.terminationCriteria.TerminationCriteriaTime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 27-Nov-17.
 */
public class TestBestWorthAntSystem
{
    @Before
    public void setUp()
    {
        Main.random = new Random(28);
    }

    @Test
    public void testBestWorthAntSystem()
    {
        ProblemVRP problem = null;
        SolutionVRP solution1 = null, solution2 = null, solution3 = null;

        try
        {
            problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
            problem.load(new File("solver/problem-samples/vrp-unit-test.json"));

            ComponentStructure2d structure2d = problem.structure2d;

            PheromoneInitializer initializer = new PheromoneInitializerConstant(1.0);

            initializer.initialize(structure2d);

            List<Solution> solutionList = new ArrayList<Solution>();

            solution1 = new SolutionVRP(problem);
            solution1.addConstructionComponent(structure2d.get(0, 3));
            solution1.addConstructionComponent(structure2d.get(3, 2));
            solution1.addConstructionComponent(structure2d.get(2, 0));
            solution1.addConstructionComponent(structure2d.get(0, 1));
            solution1.addConstructionComponent(structure2d.get(1, 0));
            solutionList.add(solution1);

            solution2 = new SolutionVRP(problem);
            solution2.addConstructionComponent(structure2d.get(0, 1));
            solution2.addConstructionComponent(structure2d.get(1, 2));
            solution2.addConstructionComponent(structure2d.get(2, 3));
            solution2.addConstructionComponent(structure2d.get(3, 0));
            solutionList.add(solution2);

            solution3 = new SolutionVRP(problem);
            solution3.addConstructionComponent(structure2d.get(0, 1));
            solution3.addConstructionComponent(structure2d.get(1, 2));
            solution3.addConstructionComponent(structure2d.get(2, 3));
            solution3.addConstructionComponent(structure2d.get(3, 0));
            solutionList.add(solution3);

            //TerminationCriteria terminationCriteria = new TerminationCriteriaTime(2000, 100);
            TerminationCriteria terminationCriteria = Mockito.mock(TerminationCriteria.class);
            when(terminationCriteria.mutationBwasFactor()).thenReturn(0.05);
            terminationCriteria.initialize();
            BestWorstAntSystem update = new BestWorstAntSystem(problem, 0.9, 0.3);
            update.setTerminationCriteria(terminationCriteria);

            update.update(solutionList);

            assertEquals(structure2d.get(0, 0).getPheromone(), 1.0, 0.0001);
            assertEquals(structure2d.get(0, 1).getPheromone(), 1.0007, 0.0001);
            assertEquals(structure2d.get(0, 2).getPheromone(), 1.0, 0.0001);
            assertEquals(structure2d.get(0, 3).getPheromone(), 0.7061, 0.0001);

            update.update(solutionList);

            assertEquals(structure2d.get(0, 0).getPheromone(), 1.0, 0.0001);
            assertEquals(structure2d.get(0, 1).getPheromone(), 1.0013, 0.0001);
            assertEquals(structure2d.get(0, 2).getPheromone(), 1.0, 0.0001);
            assertEquals(structure2d.get(0, 3).getPheromone(), 0.4502, 0.0001);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
