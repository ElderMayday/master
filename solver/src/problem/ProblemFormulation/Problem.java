package problem.problemFormulation;

import problem.component.Component;
import problem.componentStructure.ComponentStructure;
import solving.candidateList.CandidateDeterminer;
import solving.candidateList.CandidateList;
import solving.solution.Solution;

import java.io.File;
import java.io.FileNotFoundException;
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
     * Derives components to choose at the current step based on the current solution status
     * @param solution
     * @return
     */
    public abstract List<Component> getNextComponents(Solution solution);





    protected abstract void readDataFromFile(File file) throws Exception;

    protected abstract boolean checkFeasibility();




    public CandidateList getCandidateList()
    {
        return candidateList;
    }
}
