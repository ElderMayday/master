package problem.problemFormulation;

import problem.componentStructure.*;

import java.io.FileNotFoundException;

/**
 * Created by Aldar on 07-Oct-17.
 */
public abstract class Problem2d extends Problem
{
    public final ComponentStructure2d structure2d;

    public Problem2d(ComponentStructure2d structure2d, boolean lowerIsBetter, boolean mustDetermineCandidates) throws FileNotFoundException
    {
        super(structure2d, lowerIsBetter, mustDetermineCandidates);

        this.structure2d = structure2d;
    }
}
