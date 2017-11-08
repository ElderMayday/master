package solving.solutionDestroyer;

import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2d;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;

import java.lang.ref.Reference;
import java.util.List;

/**
 * Created by Aldar on 02-Nov-17.
 */
public class SolutionDestroyerVrpRandom extends SolutionDestroyer
{
    protected double tourDestructionProbability;  // probability that a tour that can be destroyed will be destroyed


    public SolutionDestroyerVrpRandom(double tourDestructionProbability)
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
        List<Component2d> components = solutionVRP.getComponents2d();
        solution.memorizeLastObjective();

        for (Tour tour : solutionVRP.getTours())
        {
            List<Integer> customers = tour.getCustomers();

            if ((customers.size() > 0) && (random.nextDouble() <= tourDestructionProbability))
            {
                solution.setPartiallyDestroyed(true); // solutionVRP is destroyed when at least one tour is destroyed
                solution.setComplete(false);

                tour.setFinished(false);

                int numberOfCustomersToRemove = random.nextInt(customers.size()) + 1;  // how many first visited customers to leave

                // remove the last tour-edge to the depot
                Component2d toRemove = structure2d.get(customers.get(customers.size() - 1), problemVRP.getDepotId());
                components.remove(toRemove);

                for (int index = 0; index < numberOfCustomersToRemove ; index++)
                {
                    // remove the component preceding to the current customer deleted
                    if (customers.size() > 1)
                        toRemove = structure2d.get(customers.get(customers.size() - 2), customers.get(customers.size() - 1));
                    else
                        toRemove = structure2d.get(problemVRP.getDepotId(), customers.get(customers.size() - 1));
                    components.remove(toRemove);

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


        return solution;
    }
}
