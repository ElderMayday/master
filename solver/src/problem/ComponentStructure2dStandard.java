package problem;

/**
 * Created by Aldar on 05-Oct-17.
 */
public class ComponentStructure2dStandard extends ComponentStructure2d
{
    public final Component2d[][] matrix;

    public ComponentStructure2dStandard(int rowNumber, int columnNumber)
    {
        super(rowNumber, columnNumber);

        matrix = new Component2d[rowNumber][columnNumber];
    }

    @Override
    public Component2d Get(Coordinate2d coordinate2d)
    {
        return matrix[coordinate2d.row][coordinate2d.column];
    }

    // TO-DO switch to (Coordinate2d, pheromone) implementation instead of substituting components every time

    @Override
    public void Set(Coordinate2d coordinate2d, Component2d component2d)
    {
        matrix[coordinate2d.row][coordinate2d.column] = component2d;
    }


}
