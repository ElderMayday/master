package problem.ProblemFormulation;

import problem.Component;
import problem.ComponentStructure.*;

import java.io.File;

/**
 * Created by Aldar on 07-Oct-17.
 */
public abstract class Problem2d extends Problem
{
    public final ComponentStructure2d structure2d;

    public Problem2d(File file, ComponentStructure2d structure2d, boolean lowerIsBetter, boolean mustDetermineCandidates, boolean mustPrecomputeHeuristic)
    {
        super(file, structure2d, lowerIsBetter, mustDetermineCandidates, mustPrecomputeHeuristic);

        this.structure2d = structure2d;
    }

    @Override
    protected void precomputeHeuristic()
    {
        for (Component component : structure2d)
            component.precompute();
    }
}
