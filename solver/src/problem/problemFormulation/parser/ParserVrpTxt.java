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

        double capacity = -1.0;
        boolean distanceRestricted = false;
        double distance = -1.0;

        boolean cvrpIsSpecified = false;
        String distanceString = "";

        problem.depotId = 0;

        while (!line[i].startsWith("NODE_COORD_SECTION")) // read specification loop
        {
            if (line[i].startsWith("TYPE"))
            {
                String[] tokens = line[i].split(": ");

                if (tokens[tokens.length - 1].equals("CVRP"))
                    cvrpIsSpecified = true;

                i++;
                continue;
            }

            if (line[i].startsWith("DIMENSION"))
            {
                String[] tokens = line[i].split(": ");

                problem.vertexNum = Integer.parseInt(tokens[tokens.length - 1]);
                problem.demands = new double[problem.vertexNum];

                i++;
                continue;
            }

            if (line[i].startsWith("CAPACITY"))
            {
                String[] tokens = line[i].split(": ");

                capacity = Double.parseDouble(tokens[tokens.length - 1]);

                i++;
                continue;
            }

            if (line[i].startsWith("DISTANCE"))
            {
                String[] tokens = line[i].split(": ");

                distanceRestricted = true;
                distance = Double.parseDouble(tokens[tokens.length - 1]);

                i++;
                continue;
            }

            if (line[i].startsWith("EDGE_WEIGHT_TYPE"))
            {
                String[] tokens = line[i].split(": ");

                distanceString = tokens[tokens.length - 1];
                distanceString = distanceString.replaceAll("\\s+","");

                i++;
                continue;
            }

            i++;
        }

        i++;

        // check some constraints

        if (!cvrpIsSpecified)
            throw new Exception("The problem instances is not tagged as CVRP");

        if (capacity < 0.0)
            throw new Exception("Vehicle capacity is not specified");

        allocateVehicles(problem, capacity, distance, distanceRestricted);


        // read distance data

        double x[], y[];

        x = new double[problem.vertexNum];
        y = new double[problem.vertexNum];

        while (!line[i].startsWith("DEMAND_SECTION"))
        {
            String tokens[];

            tokens = line[i].split(" ");

            int index = Integer.parseInt(tokens[tokens.length - 3]);

            x[index - 1] = Double.parseDouble(tokens[tokens.length - 2]);
            y[index - 1] = Double.parseDouble(tokens[tokens.length - 1]);

            i++;
        }

        i++;

        DistanceDeterminer distanceDeterminer = null;
        ComponentStructure2d structure2d = problem.structure2d;

        switch (distanceString)
        {
            case "EUC_2D":
                distanceDeterminer = new DistanceEuclidean2d();
                break;

            default:
                throw new Exception("Distance function is not recognized");
        }

        problem.structure2d.allocate(problem.vertexNum, problem.vertexNum);

        for (int index = 0; index < problem.vertexNum; index++)
            structure2d.setDistance(index, index, -1.0);

        int a = 1;
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
