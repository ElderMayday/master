package solving.localSearch.localMove;

import general.Main;
import problem.component.Component;
import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2d;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs 2.5-opt convergence for ILS
 * Completely equivalent to a pure 2.5-opt local search
 * Created by Aldar on 20-Feb-18.
 */
public class MoveTwoHalf extends Move
{
    public Solution move(Solution solution)
    {
        SolutionVRP solutionVRP = (SolutionVRP) solution;

        ComponentStructure2d structure = solutionVRP.getProblemVRP().structure2d;

        for (Tour tour : solutionVRP.getTours())
            converge(solutionVRP, structure, tour);

        return solutionVRP;
    }


    /**
     * Performs full iterated convergence on the particular tour of the solution
     * @param solution
     * @param tour
     * @return Flag if the move was improving and actually applied
     */
    protected boolean converge(SolutionVRP solution, ComponentStructure2d structure, Tour tour)
    {
        boolean iterationImproved = false; // was improved during the current iteration
        boolean totalImproved = false;     // was improved throughout all iterations at least once (to return)
        List<Integer> customers = tour.getCustomers();

        do
        {
            iterationImproved = false;

            if (convergeTwoHalf(solution, structure, tour))
                iterationImproved = true;

            if (convergeCrossExchange(solution, structure, tour))
                iterationImproved = true;

            if (iterationImproved)
                totalImproved = true;
        }
        while (iterationImproved);


        return totalImproved;
    }


    protected boolean convergeCrossExchange(SolutionVRP solution, ComponentStructure2d structure, Tour tour)
    {
        boolean improved = false;
        List<Integer> customers = tour.getCustomers();
        int size = customers.size();

        if (size < 4)     // at least 4 nodes are required to perform crose exchange
            return false;

        for (int x1 = 0; x1 <= size - 4; x1++)
            for (int y1 = x1 + 2; y1 <= size - 2; y1++)
            {
                int x2 = x1 + 1;
                int y2 = y1 + 1;

                // supposing symmetric problem, length x2->y1 == y1->x2

                double sumBefore = structure.get(customers.get(x1), customers.get(x2)).getDistance() +
                        structure.get(customers.get(y1), customers.get(y2)).getDistance();

                double sumAfter = structure.get(customers.get(x1), customers.get(y1)).getDistance() +
                        structure.get(customers.get(x2), customers.get(y2)).getDistance();

                if (sumAfter < sumBefore)  // if profitable
                {
                    List<Component2d> components = solution.getComponents2d();

                    // remove old components

                    components.remove(structure.get(customers.get(x1), customers.get(x2)));
                    components.remove(structure.get(customers.get(y1), customers.get(y2)));

                    for (int i = x2; i < y1; i++)
                        components.remove(structure.get(customers.get(i), customers.get(i + 1)));

                    // add new components

                    components.add(structure.get(customers.get(x1), customers.get(y1)));
                    components.add(structure.get(customers.get(x2), customers.get(y2)));

                    for (int i = x2; i < y1; i++)
                        components.add(structure.get(customers.get(i + 1), customers.get(i)));

                    // optimized objective value

                    solution.objective += sumAfter - sumBefore;

                    // substitute the tour list by the new one

                    List<Integer> newCustomers = new ArrayList<Integer>();

                    for (int i = 0; i <= x1; i++)
                        newCustomers.add(customers.get(i));

                    for (int i = y1; i >= x2; i--)
                        newCustomers.add(customers.get(i));

                    for (int i = y1 + 1; i < customers.size(); i++)
                        newCustomers.add(customers.get(i));

                    tour.setCustomers(newCustomers);

                    customers = newCustomers;

                    improved = true;
                }
            }

        return improved;
    }


    protected boolean convergeTwoHalf(SolutionVRP solution, ComponentStructure2d structure, Tour tour)
    {
        boolean improved = false;
        List<Integer> customers = tour.getCustomers();
        int size = customers.size();

        if (size < 5)       // at least 5 nodes are required to perform 2.5-opt
            return false;

        for (int sequenceIndex = 0; sequenceIndex <= size - 5; sequenceIndex++)   // SI in [0; n-5]
            for (int exchangeIndex = sequenceIndex + 3; exchangeIndex <= size - 2; exchangeIndex++)  // EI in [SI+3; n-2]
            {
                int sequenceLengthMax = Math.min(3, exchangeIndex - sequenceIndex - 2);
                int sequenceLength = Main.random.nextInt(sequenceLengthMax) + 1;     // SL = [1; SLMax]


                double sumBefore = structure.get(customers.get(exchangeIndex), customers.get(exchangeIndex + 1)).getDistance();

                for (int index = sequenceIndex; index <= sequenceIndex + sequenceLength; index++)
                    sumBefore += structure.get(customers.get(index), customers.get(index + 1)).getDistance();

                double sumAfter = structure.get(customers.get(sequenceIndex), customers.get(sequenceIndex + sequenceLength + 1)).getDistance()
                        + structure.get(customers.get(exchangeIndex), customers.get(sequenceIndex + sequenceLength)).getDistance()
                        + structure.get(customers.get(sequenceIndex + 1), customers.get(exchangeIndex + 1)).getDistance();

                for (int index = sequenceIndex + sequenceLength; index >= sequenceIndex + 2; index--)
                    sumAfter += structure.get(customers.get(index), customers.get(index - 1)).getDistance();

                if (sumAfter < sumBefore)  // if profitable
                {
                    List<Component2d> components = solution.getComponents2d();

                    // remove the components

                    components.remove(structure.get(customers.get(exchangeIndex), customers.get(exchangeIndex + 1)));

                    for (int index = sequenceIndex; index <= sequenceIndex + sequenceLength; index++)
                        components.remove(structure.get(customers.get(index), customers.get(index + 1)));

                    // add the components

                    components.add(structure.get(customers.get(sequenceIndex), customers.get(sequenceIndex + sequenceLength + 1)));
                    components.add(structure.get(customers.get(exchangeIndex), customers.get(sequenceIndex + sequenceLength)));
                    components.add(structure.get(customers.get(sequenceIndex + 1), customers.get(exchangeIndex + 1)));

                    for (int index = sequenceIndex + sequenceLength; index >= sequenceIndex + 2; index--)
                        components.add(structure.get(customers.get(index), customers.get(index - 1)));

                    // optimized objective adjustment

                    solution.objective += sumAfter - sumBefore;

                    // substitute the tour list by the new one

                    List<Integer> newCustomers = new ArrayList<Integer>();

                    for (int index = 0; index <= sequenceIndex; index++)
                        newCustomers.add(customers.get(index));

                    for (int index = sequenceIndex + sequenceLength + 1; index <= exchangeIndex; index++)
                        newCustomers.add(customers.get(index));

                    for (int index = sequenceIndex + sequenceLength; index >= sequenceIndex + 1; index--)
                        newCustomers.add(customers.get(index));

                    for (int index = exchangeIndex + 1; index < customers.size(); index++)
                        newCustomers.add(customers.get(index));

                    tour.setCustomers(newCustomers);

                    customers = newCustomers;

                    improved = true;
                }
            }

        return improved;
    }
}
