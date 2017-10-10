package problem.ProblemFormulation;

import org.json.JSONObject;
import problem.ComponentStructure.ComponentStructure2d;
import problem.Fleet.Fleet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Aldar on 09-Oct-17.
 */
public class ProblemVRP extends Problem2d
{
    public final Fleet fleet;

    public ProblemVRP(File file, ComponentStructure2d structure2d, Fleet fleet, boolean mustDetermineCandidates, boolean mustPrecompute) throws FileNotFoundException
    {
        super(file, structure2d, true, mustDetermineCandidates, mustPrecompute);

        this.fleet = fleet;
    }

    @Override
    protected void loadInstance(File file) throws FileNotFoundException
    {
        String jsonData = null;

        jsonData = new Scanner(file).useDelimiter("\\Z").next();

        JSONObject json = new JSONObject(jsonData);
    }

    @Override
    protected boolean checkFeasibility()
    {
        return false;
    }

    @Override
    protected void determineCandidates()
    {

    }
}
