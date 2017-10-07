package problem.ProblemFormulation;

import problem.ComponentStructure.ComponentStructure;

import java.io.File;

/**
 * Created by Aldar on 07-Oct-17.
 */
public abstract class Problem
{
    public final boolean lowerIsBetter;   // lower goal function values correspond to better solution
    public final ComponentStructure structure;
    public final boolean candidatesDetermined;

    public Problem(File file, ComponentStructure structure, boolean lowerIsBetter, boolean mustDetermineCandidates, boolean mustPrecomputeHeuristic)
    {
        this.lowerIsBetter = lowerIsBetter;
        this.structure = structure;

        this.loadInstance(file);

        if (this.checkFeasibility())
        {
            if (mustDetermineCandidates)
            {
                this.determineCandidates();
                this.candidatesDetermined = true;
            }
            else
                this.candidatesDetermined = false;

            if (mustPrecomputeHeuristic)
                this.precomputeHeuristic();
        }
        else
            this.candidatesDetermined = false;
    }

    protected abstract void loadInstance(File file);

    protected abstract boolean checkFeasibility();

    protected abstract void determineCandidates();

    protected abstract void precomputeHeuristic();
}
