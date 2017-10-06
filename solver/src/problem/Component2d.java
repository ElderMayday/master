package problem;

/**
 * Created by Aldar on 01-Oct-17.
 */
public class Component2d extends Component
{
    protected int row;
    protected int column;

    public Component2d(int row, int column, double heuristic, double pheromone)
    {
        super(heuristic, pheromone);
        this.row = row;
        this.column = column;
    }

    public Component2d(Coordinate2d coordinate2d, double heuristic, double pheromone)
    {
        super(heuristic, pheromone);
        this.row = coordinate2d.row;
        this.column = coordinate2d.column;
    }

    //------------------------------------------------

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getColumn()
    {
        return column;
    }

    public void setColumn(int column)
    {
        this.column = column;
    }
}
