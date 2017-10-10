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
    public final boolean mustDetermineCandidates;
    public final boolean mustPrecompute;

    public Problem(ComponentStructure structure, boolean lowerIsBetter, boolean mustDetermineCandidates, boolean mustPrecompute) throws FileNotFoundException
    {
        this.lowerIsBetter = lowerIsBetter;
        this.structure = structure;
        this.mustDetermineCandidates = mustDetermineCandidates;
        this.mustPrecompute = mustPrecompute;
    }

    public void load(File file) throws FileNotFoundException
    {
        readDataFromFile(file);

        if (checkFeasibility())
        {
            if (mustDetermineCandidates)
                determineCandidates();

            if (mustPrecompute)
                precomputeValues();
        }
    }

    protected abstract void readDataFromFile(File file) throws FileNotFoundException;

    protected abstract boolean checkFeasibility();

    protected abstract void determineCandidates();

    protected void precomputeValues()
    {
        for (Component component : structure)
            component.precompute();
    }
}
