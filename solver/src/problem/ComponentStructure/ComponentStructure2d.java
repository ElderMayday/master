package problem.ComponentStructure;

import problem.Component2d;
import problem.Coordinate2d;

/**
 * Created by Aldar on 04-Oct-17.
 */
public abstract class ComponentStructure2d extends ComponentStructure
{
    protected int rowNumber;
    protected int columnNumber;

    /**
     * Constructs a component matrix. !!! The components, however, must be set manually, since it does not know the component values and matrix sparsity.
     */
    public ComponentStructure2d()
    {
    }


    /**
     * Normally called after construction
     */
    public void allocate(int rowNumber, int columnNumber)
    {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }



    public int getRowNumber()
    {
        return rowNumber;
    }

    public int getColumnNumber()
    {
        return columnNumber;
    }



    public Component2d get(int row, int column)
    {
        return this.get(new Coordinate2d(row, column));
    }

    public abstract Component2d get(Coordinate2d coordinate2d);





    public void setT(int row, int column, double pheromone)
    {
        this.setT(new Coordinate2d(row, column), pheromone);
    }

    public abstract void setT(Coordinate2d coordinate2d, double pheromone);





    public void setH(int row, int column, double heuristic)
    {
        this.setH(new Coordinate2d(row, column), heuristic);
    }

    public abstract void setH(Coordinate2d coordinate2d, double heuristic);
}
