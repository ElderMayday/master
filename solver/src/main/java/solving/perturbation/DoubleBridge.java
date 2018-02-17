package solving.perturbation;

import general.Main;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;

/**
 * Created by Aldar on 16-Feb-18.
 */
public class DoubleBridge extends Perturbation
{
    public void perturbate(Solution solution)
    {
        SolutionVRP solutionVRP = (SolutionVRP) solution;

        for (Tour tour : solutionVRP.getTours())
            perturbateTour(solutionVRP, tour);
    }


    protected void perturbateTour(SolutionVRP solutionVRP, Tour tour)
    {
        int size = tour.getCustomers().size();

        if (size >= 8)
        {
            int index1 = Main.random.nextInt(size - 6);    // index1 is in [0; n-7]

            int index2 = Main.random.nextInt(size - index1 - 6) + index1 + 2;   // index2 is in [index1+2; n-5]

            int index3 = Main.random.nextInt(size - index2 - 4) + index2 + 2;   // index3 is in [index2+2; n-3]

            int index4 = Main.random.nextInt(size - index3 - 2) + index3 + 2;   // index4 is in [index3+2; n-1]
        }


        // TODO check distance constraint

        // TODO set distance and capacity left, sync the component set
    }
}
