package solving.solvers.tournamentSelector;

import solving.solution.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates uniformly random BS-size bags and selects the best one for every ant
 * Created by Aldar on 23-Nov-17.
 */
public class TournamentSelectorBag extends TournamentSelector
{
    protected int bagSize;    // aka BS
    protected Random random;

    public TournamentSelectorBag(int bagSize)
    {
        this.bagSize = bagSize;

        random = new Random();
    }

    @Override
    public List<Solution> select(List<Solution> solutions, int antNum)
    {
        List<Solution> result = new ArrayList<Solution>();

        for (int i = 0; i < antNum; i++)
        {
            // fill the BS-size bag by taking random solutions from the list

            List<Solution> bag = new ArrayList<Solution>();
            List<Solution> leftCandidates = new ArrayList<Solution>(solutions);

            for (int bagIndex = 0; bagIndex < bagSize; bagIndex++)
            {
                int dice = random.nextInt(leftCandidates.size());

                bag.add(leftCandidates.get(dice));
                leftCandidates.remove(dice);
            }

            //  find the best solution of the bag

            Solution bestSolution = bag.get(0);

            for (int bagIndex = 1; bagIndex < bagSize; bagIndex++)
                if (bag.get(bagIndex).betterThanLast(bestSolution))
                    bestSolution = bag.get(bagIndex);

            result.add(bestSolution);
        }

        return result;
    }
}
