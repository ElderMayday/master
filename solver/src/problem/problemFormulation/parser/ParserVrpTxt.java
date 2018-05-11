package problem.problemFormulation.parser;

import problem.componentStructure.ComponentStructure2d;
import problem.fleet.Vehicle;
import problem.problemFormulation.ProblemVRP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 03-Apr-18.
 */
public class ParserVrpTxt extends ParserVrp
{

    @Override
    public void parseVRP(String text, ProblemVRP problem) throws Exception
    {
        String[] line = text.split("\r\n|\n");

        int i = 0;

        double vehicleCapacity = -1.0;
        boolean distanceRestricted = false;
        double vehicleDistance = -1.0;

        boolean cvrpIsSpecified = false;
        String distanceString = "";

        problem.depotId = 0;

        while (!line[i].startsWith("NODE_COORD_SECTION")) // read specification loop
        {
            if (line[i].startsWith("TYPE"))
            {
                String[] tokens = line[i].split("(:|\\s)+");

                if (tokens[tokens.length - 1].startsWith("CVRP"))
                    cvrpIsSpecified = true;

                i++;
                continue;
            }

            if (line[i].startsWith("DIMENSION"))
            {
                String[] tokens = line[i].split("(:|\\s)+");

                problem.vertexNum = Integer.parseInt(tokens[tokens.length - 1]);
                problem.demands = new double[problem.vertexNum];

                i++;
                continue;
            }

            if (line[i].startsWith("CAPACITY"))
            {
                String[] tokens = line[i].split("(:|\\s)+");

                vehicleCapacity = Double.parseDouble(tokens[tokens.length - 1]);

                i++;
                continue;
            }

            if (line[i].startsWith("DISTANCE"))
            {
                String[] tokens = line[i].split("(:|\\s)+");

                distanceRestricted = true;
                vehicleDistance = Double.parseDouble(tokens[tokens.length - 1]);

                i++;
                continue;
            }

            if (line[i].startsWith("EDGE_WEIGHT_TYPE"))
            {
                String[] tokens = line[i].split("(:|\\s)+");

                distanceString = tokens[tokens.length - 1];

                i++;
                continue;
            }

            i++;
        }

        i++;

        // check some constraints

        if (!cvrpIsSpecified)
            throw new Exception("The problem instances is not tagged as CVRP");

        if (vehicleCapacity < 0.0)
            throw new Exception("Vehicle capacity is not specified");

        allocateVehicles(problem, vehicleCapacity, vehicleDistance, distanceRestricted);


        // read coordinate data

        double x[], y[];

        x = new double[problem.vertexNum];
        y = new double[problem.vertexNum];

        while (!line[i].startsWith("DEMAND_SECTION"))
        {
            String tokens[] = line[i].split("(:|\\s)+");

            int index = Integer.parseInt(tokens[tokens.length - 3]);

            x[index - 1] = Double.parseDouble(tokens[tokens.length - 2]);
            y[index - 1] = Double.parseDouble(tokens[tokens.length - 1]);

            i++;
        }

        i++;

        // read demands

        problem.demands = new double[problem.vertexNum];

        boolean firstIsSkipped = false;

        while (!line[i].startsWith("DEPOT_SECTION"))
        {
            String tokens[] = line[i].split("(:|\\s)+");

            int index = Integer.parseInt(tokens[tokens.length - 2]);

            problem.demands[index - 1] = Double.parseDouble(tokens[tokens.length - 1]);

            i++;
        }

        // check whether depot was skipped (it happens if there are coordinates after DEPOT_SECTION)

        i++;

        String tokens[] = line[i].split("\\s+");

        if (!tokens[0].isEmpty()) // depot needs to be added
        {
            for (int index = x.length - 1; index > 0; index--)  // shift distances and demands by one row down to free the [0]
            {
                x[index] = x[index - 1];
                y[index] = y[index - 1];
                problem.demands[index] = problem.demands[index - 1];
            }

            x[0] = Double.parseDouble(tokens[0]);
            y[0] = Double.parseDouble(tokens[1]);
            problem.demands[0] = 0.0;
        }

        // compute the distance matrix

        DistanceDeterminer distanceDeterminer = null;
        ComponentStructure2d structure2d = problem.structure2d;

        switch (distanceString)
        {
            case "EUC_2D":
                distanceDeterminer = new DistanceEuclidean2d();
                break;

            // ...

            default:
                distanceDeterminer = new DistanceEuclidean2d(); // by default
        }

        problem.structure2d.allocate(problem.vertexNum, problem.vertexNum);

        for (int index = 0; index < problem.vertexNum; index++)
            structure2d.setDistance(index, index, -1.0);

        for (int row = 0; row < problem.vertexNum; row++)
            for (int column = row + 1; column < problem.vertexNum; column++)
            {
                double distance = distanceDeterminer.distance(x[row], y[row], x[column], y[column]);

                structure2d.setDistance(row, column, distance);
                structure2d.setDistance(column, row, distance);
            }

        // depot is set to node #0

        problem.depotId = 0;

        // heuristics

        for (int row = 0; row < problem.vertexNum; row++)
            for (int column = 0; column < problem.vertexNum; column++)
                structure2d.setH(row, column, 1.0 / structure2d.get(row, column).getDistance());
    }

    protected void allocateVehicles(ProblemVRP problem, double capacity, double distance, boolean distanceRestricted)
    {
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();

        if (distanceRestricted)
            for (int index = 0; index < problem.vertexNum; index++)
                vehicleList.add(new Vehicle(capacity, distance, true));
        else
            for (int index = 0; index < problem.vertexNum; index++)
                vehicleList.add(new Vehicle(capacity, 0.0, false));

        problem.fleet.setVehicles(vehicleList);
    }




    interface DistanceDeterminer
    {
        double distance(double x1, double y1, double x2, double y2);
    }

    class DistanceEuclidean2d implements DistanceDeterminer
    {

        @Override
        public double distance(double x1, double y1, double x2, double y2)
        {
            return Math.sqrt(Math.pow(x1 - x2, 2.0) + Math.pow(y1 - y2, 2.0));
        }
    }
}
