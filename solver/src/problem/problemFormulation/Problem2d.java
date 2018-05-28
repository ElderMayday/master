package problem.problemFormulation;

import problem.componentStructure.*;
import solving.candidateList.CandidateDeterminer;

import java.io.FileNotFoundException;

/**
 * Created by Aldar on 07-Oct-17.
 */
public abstract class Problem2d extends Problem
{
    public final ComponentStructure2d structure2d;

    public Problem2d(ComponentStructure2d structure2d, boolean lowerIsBetter, CandidateDeterminer candidateDeterminer) throws FileNotFoundException
    {
        super(structure2d, lowerIsBetter, candidateDeterminer);

        this.structure2d = structure2d;
    }

    public MinMaxResult findMinMax()
    {
        double min, max;

        min = structure2d.get(0, 0).getPheromone();
        max = min;

        for (int i = 0; i < structure2d.getRowNumber(); i++)
            for (int j = 0; j < structure2d.getColumnNumber(); j++)
            {
                double pheromone = structure2d.get(i, j).getPheromone();

                if (min > pheromone)
                    min = pheromone;

                if (max < pheromone)
                    max = pheromone;
            }

        return new MinMaxResult(min, max);
    }
}
