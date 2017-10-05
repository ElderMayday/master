package problem;

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

    public void Set(int row, int column, Component2d component2d)
    {
        this.Set(new Coordinate2d(row, column), component2d);
    }

    public abstract void Set(Coordinate2d coordinate2d, Component2d component2d);
}
