package problem;

/**
 * Created by Aldar on 05-Oct-17.
 */
public class Coordinate2d
{
    public final int row;
    public final int column;

    public Coordinate2d(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
            return true;

        if (!(object instanceof Coordinate2d))
            return false;

        Coordinate2d secondCoordinate = (Coordinate2d) object;

        return row == secondCoordinate.row && column == secondCoordinate.column;
    }

    @Override
    public int hashCode()
    {
        int result = row;
        result = 47 * result + column;
        return result;
    }
}
