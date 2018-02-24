package solving.localSearch.perturbation;

import general.Main;
import problem.componentStructure.ComponentStructure2d;
import problem.problemFormulation.ProblemVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 16-Feb-18.
 */
public class DoubleBridge extends Perturbation
{
    public Solution perturbate(Solution solution)
    {
        SolutionVRP solutionVRP = (SolutionVRP) solution;

        SolutionVRP solutionCopy = (SolutionVRP) solutionVRP.deepCopy();

        boolean isModified = false;

        for (Tour tour : solutionCopy.getTours())
            if (perturbateTour(solutionCopy, tour))   // perturbate every tour if possible and memorize if it was really done
                isModified = true;

        return isModified ? solutionCopy : solutionVRP; // if not modified the copy, then return the original
    }


    /**
     * Perturbates the tour by selecting 4 random edges and permutating their sequence.
     * Distance constraint is ensured by the fact that it is only applied if tour length shortens
     * Capacity constraint is ensured by the fact that the vehicle visits the same clients in different order
     * @param solutionVRP the solution being perturbated
     * @param tour the tour os solutionVRP being perturbated
     * @return whether the solution has been modified
     */
    protected boolean perturbateTour(SolutionVRP solutionVRP, Tour tour)
    {
        boolean isModified = false;

        int size = tour.getCustomers().size();
        List<Integer> customers = tour.getCustomers();

        if (size >= 8)  // is applicable with at least 8 tour nodes
        {
            int index1 = Main.random.nextInt(size - 7);    // index1 is in [0; n-8]
            int start1 = customers.get(index1);
            int end1 = customers.get(index1 + 1);

            int index2 = customers.get(Main.random.nextInt(size - index1 - 7) + index1 + 2);   // index2 is in [index1+2; n-6]
            int start2 = customers.get(index2);
            int end2 = customers.get(index2 + 1);

            int index3 = customers.get(Main.random.nextInt(size - index2 - 5) + index2 + 2);   // index3 is in [index2+2; n-4]
            int start3 = customers.get(index3);
            int end3 = customers.get(index3 + 1);

            int index4 = customers.get(Main.random.nextInt(size - index3 - 3) + index3 + 2);   // index4 is in [index3+2; n-2]
            int start4 = customers.get(index4);
            int end4 = customers.get(index4 + 1);

            ProblemVRP problem = solutionVRP.getProblemVRP();
            ComponentStructure2d structure = problem.structure2d;

            double sum4original = structure.get(start1, end1).getDistance() +
                    structure.get(start2, end2).getDistance() +
                    structure.get(start3, end3).getDistance() +
                    structure.get(start4, end4).getDistance();

            double sum4new = structure.get(start1, end3).getDistance() +
                    structure.get(start4, end2).getDistance() +
                    structure.get(start3, end1).getDistance() +
                    structure.get(start2, end4).getDistance();

            if (sum4new < sum4original)   // is an improving move
            {
                isModified = true;

                // modify the component set

                solutionVRP.components2d.remove(structure.get(start1, end1));
                solutionVRP.components2d.remove(structure.get(start2, end2));
                solutionVRP.components2d.remove(structure.get(start3, end3));
                solutionVRP.components2d.remove(structure.get(start4, end4));

                solutionVRP.components2d.add(structure.get(start1, end3));
                solutionVRP.components2d.add(structure.get(start4, end2));
                solutionVRP.components2d.add(structure.get(start3, end1));
                solutionVRP.components2d.add(structure.get(start2, end4));

                // modify the customer sequence

                List<Integer> newCustomers = new ArrayList<Integer>();

                for (int i = 0; i < index1; i++)
                    newCustomers.add(customers.get(i));

                newCustomers.add(start1);
                newCustomers.add(end3);

                for (int i = index3 + 2; i < index4; i++)
                    newCustomers.add(customers.get(i));

                newCustomers.add(start4);
                newCustomers.add(end2);

                for (int i = index2 + 2; i < index3; i++)
                    newCustomers.add(customers.get(i));

                newCustomers.add(start3);
                newCustomers.add(end1);

                for (int i = index1 + 2; i < index2; i++)
                    newCustomers.add(customers.get(i));

                newCustomers.add(start2);
                newCustomers.add(end4);

                for (int i = index4 + 2; i < customers.size(); i++)
                    newCustomers.add(customers.get(i));

                tour.setCustomers(newCustomers);

                // optimized objective recomputing (instead of full recomputing)

                solutionVRP.objective += sum4new - sum4original;
            }
        }

        return isModified;
    }
}
