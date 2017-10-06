package problem.ComponentStructure;

import problem.Component2d;
import problem.Coordinate2d;

/**
 * Created by Aldar on 04-Oct-17.
 */
public abstract class ComponentStructure2d extends ComponentStructure
{
    public final int rowNumber;
    public final int columnNumber;

    /**
     * Constructs a component matrix. !!! The components, however, must be set manually, since it does not know the component values and matrix sparsity.
     * @param rowNumber
     * @param columnNumber
     */
    public ComponentStructure2d(int rowNumber, int columnNumber)
    {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public Component2d Get(int row, int column)
    {
        return this.Get(new Coordinate2d(row, column));
    }

    public abstract Component2d Get(Coordinate2d coordinate2d);





    public void SetT(int row, int column, double pheromone)
    {
        this.SetT(new Coordinate2d(row, column), pheromone);
    }

    public abstract void SetT(Coordinate2d coordinate2d, double pheromone);





    public void SetH(int row, int column, double heuristic)
    {
        this.SetH(new Coordinate2d(row, column), heuristic);
    }

    public abstract void SetH(Coordinate2d coordinate2d, double heuristic);
}
