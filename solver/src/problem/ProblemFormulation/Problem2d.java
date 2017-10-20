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
}
