package problem.ProblemFormulation;

import org.json.JSONObject;
import problem.ComponentStructure.ComponentStructure2d;
import problem.Fleet.Fleet;
import problem.Fleet.Vehicle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Aldar on 09-Oct-17.
 */
public class ProblemVRP extends Problem2d
{
    public final Fleet fleet;

    public ProblemVRP(ComponentStructure2d structure2d, Fleet fleet, boolean mustDetermineCandidates, boolean mustPrecompute) throws FileNotFoundException
    {
        super(structure2d, true, mustDetermineCandidates, mustPrecompute);

        this.fleet = fleet;
    }

    @Override
    protected void readDataFromFile(File file) throws FileNotFoundException
    {
        String fileText = new Scanner(file).useDelimiter("\\Z").next();

        JSONObject jProblem = new JSONObject(fileText);
        JSONObject jVehicles = jProblem.getJSONObject("vehicles");

        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        for (Iterator<String> vehicleIterator = jVehicles.keys(); vehicleIterator.hasNext();)
        {
            String vehicleID = vehicleIterator.next();
            JSONObject jVehicle = jVehicles.getJSONObject(vehicleID);

            double capacity = jVehicle.getDouble("capacity");
            double length = jVehicle.getDouble("length");     // negative length is considered as missing of length restriction

            Vehicle newVehicle = new Vehicle(capacity, length >= 0 ? length : 0.0, length > 0);
            vehicleList.add(newVehicle);
        }

        fleet.setVehicles(vehicleList);
    }

    @Override
    protected boolean checkFeasibility()
    {
        return true;
    }

    @Override
    protected void determineCandidates()
    {

    }
}
