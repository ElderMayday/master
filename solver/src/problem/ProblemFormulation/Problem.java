package problem.problemFormulation;

import problem.component.Component;
import problem.componentStructure.ComponentStructure;
import solving.candidateList.CandidateDeterminer;
import solving.candidateList.CandidateList;
import solving.solution.Solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.Reference;
import java.util.List;

/**
 * Created by Aldar on 07-Oct-17.
 */
public abstract class Problem
{
    public final boolean lowerIsBetter;   // lower goal function values correspond to better solving
    public final ComponentStructure structure;
    public final CandidateDeterminer candidateDeterminer;
    protected CandidateList candidateList;



    public Problem(ComponentStructure structure, boolean lowerIsBetter, CandidateDeterminer candidateDeterminer) throws FileNotFoundException
    {
        this.lowerIsBetter = lowerIsBetter;
        this.structure = structure;
        this.candidateDeterminer = candidateDeterminer;
        this.candidateList = null;
    }

    public void load(File file) throws Exception
    {
        readDataFromFile(file);

        if (checkFeasibility())
        {
            if (candidateDeterminer != null)
                candidateList = candidateDeterminer.determine(this);
        }
        else
            throw new Exception("Non-feasible instance");
    }

    public void precomputeValues()
    {
        for (Component component : structure)
            component.precompute();
    }


    /**
     * Pre-selects components to choose at the current step based on the current solution status
     * @param solution
     * @return
     */
    public abstract List<Component> getConstructionComponents(Solution solution);


    public abstract List<Component> getReconstructionComponents(Solution solution);




    /**
     * Factory method generating a solution of the corresponding problem variation (for the sake of generality in Solver class)
     * @return
     */
    public abstract Solution createSolution();

    /**
     * Required for MMAS
     * @return
     */
    public abstract int ProblemSize();


    protected abstract void readDataFromFile(File file) throws Exception;

    protected abstract boolean checkFeasibility();




    public CandidateList getCandidateList()
    {
        return candidateList;
    }
}
