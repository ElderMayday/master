package solving.solutionDestroyer;

import general.Main;
import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2d;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;

import java.lang.ref.Reference;
import java.util.List;
import java.util.Random;

/**
 * Removes random number of final customers from every tours with certain probability
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


        for (Tour tour : solutionVRP.getTours())
        {
            List<Integer> customers = tour.getCustomers();

            if ((customers.size() > 2) && (Main.random.nextDouble() <= tourDestructionProbability))  // at least one client is visited
            {
                if (!solution.isPartiallyDestroyed())
                    super.destroyCommon(solution);

                tour.setFinished(false);

                int numberOfCustomersToRemove = Main.random.nextInt(customers.size() - 1) + 1;  // how many first visited customers to leave [1; N-1]

                Component2d toRemove;

                for (int index = 0; index < numberOfCustomersToRemove ; index++)
                {
                    toRemove = structure2d.get(customers.get(customers.size() - 2), customers.get(customers.size() - 1));

                    components.remove(toRemove);

                    int customerToRemove = customers.get(customers.size() - 1);

                    if (customerToRemove != problemVRP.getDepotId())           // tag as non-visited if not depot
                        solutionVRP.setVisited(customerToRemove, false);

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
