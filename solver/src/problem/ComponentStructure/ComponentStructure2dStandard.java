package problem.ComponentStructure;

import problem.Component2d;
import problem.Coordinate2d;

/**
 * Created by Aldar on 05-Oct-17.
 */
public class ComponentStructure2dStandard extends ComponentStructure2d
{
    protected final Component2d[][] matrix;

    public ComponentStructure2dStandard(int rowNumber, int columnNumber)
    {
        super(rowNumber, columnNumber);

        matrix = new Component2d[rowNumber][columnNumber];

        for (int row = 0; row < rowNumber; row++)
            for (int column = 0; column < columnNumber; column++)
                matrix[row][column] = new Component2d(row, column, 0.0, 0.0);
    }

    @Override
    public Component2d Get(Coordinate2d coordinate2d)
    {
        return matrix[coordinate2d.row][coordinate2d.column];
    }

    @Override
    public void SetT(Coordinate2d coordinate2d, double pheromone)
    {
        matrix[coordinate2d.row][coordinate2d.column].setPheromone(pheromone);
    }

    @Override
    public void SetH(Coordinate2d coordinate2d, double heuristic)
    {
        matrix[coordinate2d.row][coordinate2d.column].setHeuristic(heuristic);
    }
}
