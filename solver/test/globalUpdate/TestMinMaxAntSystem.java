package globalUpdate;

import org.junit.Test;
import problem.componentStructure.ComponentStructure2d;
import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.ProblemVRP;
import solving.globalUpdate.GlobalUpdate;
import solving.globalUpdate.MinMaxAntSystem;
import solving.globalUpdate.pheromoneTrailSmoothing.PheromoneTrailSmoothingMMAS;
import solving.pheromoneInitializer.PheromoneInitializer;
import solving.pheromoneInitializer.PheromoneInitializerConstant;
import solving.solution.Solution;
import solving.solution.SolutionVRP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 26-Nov-17.
 */
public class TestMinMaxAntSystem
{
    @Test
    public void testMinMaxAntSystem_withPTS()
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

            GlobalUpdate update = new MinMaxAntSystem(problem, 0.9, 0.5, new PheromoneTrailSmoothingMMAS(0.9, 0.1, 0.8), true, 1, true,1);

            update.update(solutionList);

            assertEquals(structure2d.get(0, 0).getPheromone(), 0.1898, 0.0001);
            assertEquals(structure2d.get(0, 1).getPheromone(), 0.1898, 0.0001);
            assertEquals(structure2d.get(0, 2).getPheromone(), 0.1898, 0.0001);
            assertEquals(structure2d.get(0, 3).getPheromone(), 0.1898, 0.0001);

            update.update(solutionList);

            assertEquals(structure2d.get(0, 0).getPheromone(), 0.1860, 0.0001);
            assertEquals(structure2d.get(0, 1).getPheromone(), 0.1874, 0.0001);
            assertEquals(structure2d.get(0, 2).getPheromone(), 0.1860, 0.0001);
            assertEquals(structure2d.get(0, 3).getPheromone(), 0.1860, 0.0001);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
