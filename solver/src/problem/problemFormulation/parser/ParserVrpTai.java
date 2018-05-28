package problem.problemFormulation.parser;

import problem.fleet.Vehicle;
import problem.problemFormulation.ProblemVRP;
import problem.problemFormulation.parser.distanceDeterminer.DistanceDeterminer;
import problem.problemFormulation.parser.distanceDeterminer.DistanceEuclidean2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Aldar on 25-May-18.
 */
public class ParserVrpTai extends ParserVrp
{
    @Override
    public void parseVRP(String text, ProblemVRP problem) throws Exception
    {
        String[] line = text.split("\r\n|\n");

        // read first two lines

        Scanner scanner = new Scanner(line[0]);
        problem.vertexNum = scanner.nextInt() + 1;
        double bestDistance = scanner.nextDouble();

        scanner = new Scanner(line[1]);
        double capacity = scanner.nextDouble();

        // line 3 is suppose to be coordinates of the depot - (0;0)

        problem.depotId = 0;

        // read customer lines

        problem.demands = new double[problem.vertexNum];

        problem.x = new double[problem.vertexNum];
        problem.y = new double[problem.vertexNum];

        for (int index = 3; index < line.length; index++)
        {
            scanner = new Scanner(line[index]);

            int node = scanner.nextInt();
            problem.x[node] = scanner.nextDouble();
            problem.y[node] = scanner.nextDouble();
            problem.demands[node] = scanner.nextDouble();
        }

        DistanceDeterminer distanceDeterminer = new DistanceEuclidean2d();

        problem.structure2d.allocate(problem.vertexNum, problem.vertexNum);

        for (int index = 0; index < problem.vertexNum; index++)
            problem.structure2d.setDistance(index, index, -1.0);

        for (int row = 0; row < problem.vertexNum; row++)
            for (int column = row + 1; column < problem.vertexNum; column++)
            {
                double distance = distanceDeterminer.distance(problem.x[row], problem.y[row], problem.x[column], problem.y[column]);

                problem.structure2d.setDistance(row, column, distance);
                problem.structure2d.setDistance(column, row, distance);
            }

        // heuristics

        for (int row = 0; row < problem.vertexNum; row++)
            for (int column = 0; column < problem.vertexNum; column++)
                problem.structure2d.setH(row, column, 1.0 / problem.structure2d.get(row, column).getDistance());

        allocateVehicles(problem, capacity, -1.0, false);
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
}
