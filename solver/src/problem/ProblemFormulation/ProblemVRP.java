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

    protected int vertexNum;



    protected double[][] distances;    // distance matrix of size (vertexNum; vertexNum)
    protected double[] demands;
    protected int depotId;             // number of the vertex that represents depot


    public ProblemVRP(ComponentStructure2d structure2d, Fleet fleet, boolean mustDetermineCandidates, boolean mustPrecompute) throws FileNotFoundException
    {
        super(structure2d, true, mustDetermineCandidates, mustPrecompute);

        this.fleet = fleet;
    }

    @Override
    protected void readDataFromFile(File file) throws Exception
    {
        String fileText = new Scanner(file).useDelimiter("\\Z").next();

        JSONObject jProblem = new JSONObject(fileText);

        this.readVehciles(jProblem);
        this.readMap(jProblem);

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





    private void readVehciles(JSONObject jProblem)
    {
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

    private void readMap(JSONObject jProblem) throws Exception
    {
        JSONObject jMap = jProblem.getJSONObject("map");

        vertexNum = jMap.getInt("vertexNum");

        distances = new double[vertexNum][vertexNum];
        demands = new double[vertexNum];
        depotId = -1;
        structure2d.allocate(vertexNum, vertexNum);

        JSONObject jVertexes = jMap.getJSONObject("vertexes");

        for (Iterator<String> vertexIterator = jVertexes.keys(); vertexIterator.hasNext();)
        {
            JSONObject jVertex = jVertexes.getJSONObject(vertexIterator.next());

            int vertexId = jVertex.getInt("id");

            double demand = jVertex.getDouble("demand");

            if (demand < 0.0)
            {
                if (depotId == -1)
                    depotId = vertexId;
                else
                    throw new Exception("Duplicate depot found at vertex #" + vertexId);
            }

            demands[vertexId] = demand;

            JSONObject jTargets = jVertex.getJSONObject("targets");

            for (Iterator<String> targetIterator = jTargets.keys(); targetIterator.hasNext();)
            {
                JSONObject jTarget = jTargets.getJSONObject(targetIterator.next());

                int targetId = jTarget.getInt("id");
                double distance = jTarget.getDouble("distance");

                distances[vertexId][targetId] = distance;

                if (vertexId == targetId)
                    if (distance >= 0.0)
                        throw new Exception("Self path should be marked by negative distance");

                if (jTarget.has("heuristic"))
                    structure2d.setH(vertexId, targetId, jTarget.getDouble("heuristic"));
            }
        }
    }











    public int getVertexNum()
    {
        return vertexNum;
    }

    public double[][] getDistances()
    {
        return distances;
    }

    public double[] getDemands()
    {
        return demands;
    }

    public int getDepotId()
    {
        return depotId;
    }
}
