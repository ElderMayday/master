package problem.component;

import problem.Coordinate2d;
import problem.component.Component;

/**
 * Created by Aldar on 01-Oct-17.
 */
public class Component2d extends Component
{
    protected int row;
    protected int column;

    protected double distance;   // for graph-distanced problems (including VRP)

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

    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }


    public String toString()
    {
        return "(" + row + ";" + column + ") t=" + String.format("%1$,.4f", pheromone) + "; h=" + String.format("%1$,.2f", heuristic);
    }
}
