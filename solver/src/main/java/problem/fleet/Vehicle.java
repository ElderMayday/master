package problem.fleet;

/**
 * Created by Aldar on 09-Oct-17.
 */
public class Vehicle
{
    public final double capacity;    // initial vehicle values
    public final double length;
    public final boolean hasLengthRestriction;

    public Vehicle(double capacity, double length, boolean hasLengthRestriction)
    {
        if ((capacity < 0) || (length < 0))
            throw new IllegalArgumentException("Negative value of a vehicle parameter");

        this.capacity = capacity;
        this.length = length;
        this.hasLengthRestriction = hasLengthRestriction;
    }
}
