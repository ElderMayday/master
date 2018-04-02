package problem.problemFormulation.parser;

import org.json.JSONObject;
import problem.fleet.Vehicle;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Aldar on 02-Apr-18.
 */
public class ParserVrpJson extends ParserVrp
{

    @Override
    public void parseVRP(String text, ProblemVRP problem) throws Exception
    {
        JSONObject jProblem = new JSONObject(text);

        this.parseVehicles(jProblem, problem);
        this.parseMap(jProblem, problem);
    }

    protected void parseVehicles(JSONObject jProblem, ProblemVRP problem)
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

        problem.fleet.setVehicles(vehicleList);
    }

    protected void parseMap(JSONObject jProblem, ProblemVRP problem) throws Exception
    {
        JSONObject jMap = jProblem.getJSONObject("map");

        problem.vertexNum = jMap.getInt("vertexNum");

        problem.demands = new double[problem.vertexNum];
        problem.depotId = -1;
        problem.structure2d.allocate(problem.vertexNum, problem.vertexNum);

        JSONObject jVertexes = jMap.getJSONObject("vertexes");

        for (Iterator<String> vertexIterator = jVertexes.keys(); vertexIterator.hasNext();)
        {
            JSONObject jVertex = jVertexes.getJSONObject(vertexIterator.next());

            int vertexId = jVertex.getInt("id");

            double demand = jVertex.getDouble("demand");

            if (demand < 0.0)
            {
                if (problem.depotId == -1)
                    problem.depotId = vertexId;
                else
                    throw new Exception("Duplicate depot found at vertex #" + vertexId);
            }

            problem.demands[vertexId] = demand;

            JSONObject jTargets = jVertex.getJSONObject("targets");

            for (Iterator<String> targetIterator = jTargets.keys(); targetIterator.hasNext();)
            {
                JSONObject jTarget = jTargets.getJSONObject(targetIterator.next());

                int targetId = jTarget.getInt("id");
                double distance = jTarget.getDouble("distance");

                problem.structure2d.setDistance(vertexId, targetId, distance);

                if (vertexId == targetId)
                    if (distance >= 0.0)
                        throw new Exception("Self path should be marked by negative distance");

                if (jTarget.has("heuristic"))
                    problem.structure2d.setH(vertexId, targetId, jTarget.getDouble("heuristic"));
            }
        }
    }
}