package solving.solutionDestroyer;

import problem.componentStructure.ComponentStructure2d;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;

import java.util.List;

/**
 * Created by Aldar on 02-Nov-17.
 */
public class SolutionDestroyerVRP extends SolutionDestroyer
{
    protected double tourDestructionProbability;  // probability that a tour that can be destroyed will be destroyed


    public SolutionDestroyerVRP(double tourDestructionProbability)
    {
        if ((tourDestructionProbability < 0.0) || (tourDestructionProbability > 1.0))
            throw new IllegalArgumentException("Tour destruction probability out of range");

        this.tourDestructionProbability = tourDestructionProbability;
    }



    @Override
    public Solution destroy(Solution solution) throws Exception
    {
        if (!solution.getComplete())
            throw new Exception("Cannot destroy not constructed solution");

        SolutionVRP solutionVRP = (SolutionVRP) solution;
        ComponentStructure2d structure2d = solutionVRP.getProblemVRP().structure2d;
        ProblemVRP problemVRP = solutionVRP.getProblemVRP();

        for (Tour tour : solutionVRP.getTours())
        {
            List<Integer> customers = tour.getCustomers();

            if ((customers.size() > 0) && (random.nextDouble() <= tourDestructionProbability))
            {
                tour.setFinished(false);

                int toLeave = random.nextInt(customers.size()) + 1;  // how many first visited customers to leave

                for (int index = 0; index < toLeave ; index++)
                {
                    solutionVRP.setVisited(customers.get(customers.size() - 1), false);   // tag as non-visited
                    customers.remove(customers.size() - 1);  // remove last customer
                }

                // recalculate the tour parameters

                tour.resetTourParameters();

                boolean hasLengthRestriction = tour.getVehicle().hasLengthRestriction;

                int currentId = problemVRP.getDepotId();

                for (int index = 0; index < customers.size(); index++)
                {
                    tour.addDistance(structure2d.get(currentId, customers.get(index)).getDistance());
                    tour.addCapacity(problemVRP.getDemands()[customers.get(index)]);
                    currentId = index;
                }
            }
        }

        solutionVRP.setPartiallyDestroyed(true);
        solution.setComplete(false);

        return solution;
    }
}
