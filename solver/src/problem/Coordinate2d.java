package problem;

/**
 * Created by Aldar on 05-Oct-17.
 */
public class Coordinate2d
{
    public static int maxColumnNumber;

    /**
     * Should be set equal to the table row number before using ComponentStructure2dHash in order to avoid collisions
     * @param maxColumnNumber
     */
    public static void setMaxColumnNumber(int maxColumnNumber)
    {
        Coordinate2d.maxColumnNumber = maxColumnNumber;
    }

    //----------------------------------------------------



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
        return Coordinate2d.maxColumnNumber * row + column;
    }
}
