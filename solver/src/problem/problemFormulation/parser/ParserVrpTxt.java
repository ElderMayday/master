package problem.problemFormulation.parser;

import problem.fleet.Vehicle;
import problem.problemFormulation.ProblemVRP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 02-Apr-18.
 */
public class ParserVrpTxt extends ParserVrp
{
    @Override
    public void parseVRP(String text, ProblemVRP problem) throws Exception
    {
        String token[] = text.split("(\\s|\\n)+");

        problem.depotId = 0;

        // vehicle number -----------------------------

        int vehicleNum = Integer.parseInt(token[0]);

        int index = 2;

        // vehicles parameters -------------------------------------------

        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        for (int i = 0; i < vehicleNum; i++)
        {
            double capacity = Double.parseDouble(token[index]);
            double length = Double.parseDouble(token[index + 1]);     // negative length is considered as missing of length restriction

            Vehicle newVehicle = new Vehicle(capacity, length >= 0 ? length : 0.0, length > 0);
            vehicleList.add(newVehicle);

            index += 2;
        }
        problem.fleet.setVehicles(vehicleList);

        index++;

        // customer number ----------------------------

        int customerNum = Integer.parseInt(token[index]);

        index += 2;

        // demands ------------------

        problem.vertexNum = customerNum + 1;
        problem.demands = new double[problem.vertexNum];

        for (int i = 1; i < problem.vertexNum; i++, index++)
            problem.demands[i] = Double.parseDouble(token[index]);

        index++;

        // distances -----------------------------

        problem.structure2d.allocate(problem.vertexNum, problem.vertexNum);

        for (int i = 0; i < problem.vertexNum; i++)
            for (int j = 0; j < problem.vertexNum; j++)
            {
                double distance = Double.parseDouble(token[index]);

                problem.structure2d.setDistance(i, j, distance);

                if (i == j)
                    if (distance >= 0.0)
                        throw new Exception("Self path should be marked by negative distance");

                index++;
            }

        index++;

        // heuristics -----------------------------------

        for (int i = 0; i < problem.vertexNum; i++)
            for (int j = 0; j < problem.vertexNum; j++)
            {
                problem.structure2d.setH(i, j, Double.parseDouble(token[index]));
                index++;
            }
    }
}
