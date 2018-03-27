package solving.solutionDestroyer;

import general.Main;
import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2d;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Destroys solution by leaving one of the initial tours
 * Created by Aldar on 11-Nov-17.
 */
public class SolutionDestroyerVrpByTour extends SolutionDestroyer
{
    @Override
    public Solution destroy(Solution solution) throws Exception
    {
        if (!solution.getComplete())
            throw new Exception("Cannot destroy not constructed solution");

        SolutionVRP solutionVRP = (SolutionVRP) solution;
        ComponentStructure2d structure2d = solutionVRP.getProblemVRP().structure2d;
        ProblemVRP problemVRP = solutionVRP.getProblemVRP();
        List<Component2d> components = solutionVRP.getComponents2d();

        List<Tour> tours = solutionVRP.getTours();

        if (tours.size() > 1)
        {
            if (!solution.isPartiallyDestroyed())
                super.destroyCommon(solution);

            int dice = Main.random.nextInt(tours.size());
            Tour newTour = tours.get(dice);
            List<Tour> newTours = new ArrayList<Tour>();
            newTours.add(newTour);
            solutionVRP.setTours(newTours);
            solutionVRP.setVehicleIteratorToIndex(1);
        }

        return solution;
    }
}
