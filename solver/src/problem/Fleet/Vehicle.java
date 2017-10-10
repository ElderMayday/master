package problem.Fleet;

/**
 * Created by Aldar on 09-Oct-17.
 */
public class Vehicle
{
    public final double capacity;    // initial vehicle values
    public final double length;
    public final boolean hasLengthRestriction;

    protected double capacityLeft;   // solution-time current vehicle values
    protected double lengthLeft;

    public Vehicle(double capacity, double length, boolean hasLengthRestriction)
    {
        if ((capacity < 0) || (length < 0))
            throw new IllegalArgumentException("Negative value of a vehicle parameter");

        this.capacity = capacity;
        this.length = length;
        this.hasLengthRestriction = hasLengthRestriction;

        reset();
    }



    public double getCapacityLeft()
    {
        return capacityLeft;
    }

    public double getLengthLeft()
    {
        return lengthLeft;
    }

    public void reset()
    {
        capacityLeft = capacity;
        lengthLeft = length;
    }

    public void distractCapacity(double quantity) throws Exception
    {
        capacityLeft -= quantity;

        if (capacity < 0)
            throw new Exception("Cannot distract such a quantity from the vehicle capacity");
    }

    public void distractLength(double distance) throws Exception
    {
        lengthLeft -= distance;

        if (capacity < 0)
            throw new Exception("Cannot distract such a distance from the vehicle length");
    }
}
