package problem;

/**
 * Created by Aldar on 05-Oct-17.
 */
public class Coordinate2d
{
    public static int colNumber;

    /**
     * Should be set equal to the table row number before using ComponentStructure2dHash in order to avoid collisions
     * @param colNumber
     */
    public static void setColNumber(int colNumber)
    {
        Coordinate2d.colNumber = colNumber;
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
        return Coordinate2d.colNumber * row + column;
    }
}
