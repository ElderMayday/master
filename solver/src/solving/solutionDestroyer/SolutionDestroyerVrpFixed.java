package solving.solutionDestroyer;

import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2d;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;

import java.lang.ref.Reference;
import java.util.List;

/**
 * Trims every tour to the fixed number of customers if it is longer
 * This destroyer is created for testing purposes rather than practical
 * Created by Aldar on 04-Nov-17.
 */
public class SolutionDestroyerVrpFixed extends SolutionDestroyer
{
    protected int toLeave;  // maximum number of customers per tour that should be left


    public SolutionDestroyerVrpFixed(int toLeave)
    {
        if (toLeave < 1)
            throw new IllegalArgumentException("Tour max customer number is wrong. Must leave at least the first depot node.");

        this.toLeave = toLeave;
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

            if ((customers.size() > 0) && (customers.size() > toLeave))
            {
                if (!solution.isPartiallyDestroyed())
                    super.destroyCommon(solution);

                tour.setFinished(false);

                int numberOfCustomersToRemove = customers.size() - toLeave;

                // remove the last tour-edge to the depot
                //Component2d toRemove = structure2d.get(customers.get(customers.size() - 1), problemVRP.getDepotId());
                //components.remove(toRemove);

                Component2d toRemove;

                for (int index = 0; index < numberOfCustomersToRemove ; index++)
                {
                    // remove the component preceding to the current customer deleted
                    // if (customers.size() > 1)
                    //     toRemove = structure2d.get(customers.get(customers.size() - 2), customers.get(customers.size() - 1));
                    // else
                    //     toRemove = structure2d.get(problemVRP.getDepotId(), customers.get(customers.size() - 1));

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
