package problem.ProblemFormulation;

import problem.Component;
import problem.ComponentStructure.ComponentStructure;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Aldar on 07-Oct-17.
 */
public abstract class Problem
{
    public final boolean lowerIsBetter;   // lower goal function values correspond to better solution
    public final ComponentStructure structure;
    public final boolean candidatesDetermined;

    public Problem(File file, ComponentStructure structure, boolean lowerIsBetter, boolean mustDetermineCandidates, boolean mustPrecompute) throws FileNotFoundException
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

            if (mustPrecompute)
                this.precomputeValues();
        }
        else
            this.candidatesDetermined = false;
    }

    protected abstract void loadInstance(File file) throws FileNotFoundException;

    protected abstract boolean checkFeasibility();

    protected abstract void determineCandidates();

    protected void precomputeValues()
    {
        for (Component component : structure)
            component.precompute();
    }
}
