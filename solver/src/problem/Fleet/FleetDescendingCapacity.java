package problem.fleet;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Aldar on 09-Oct-17.
 */
public class FleetDescendingCapacity extends Fleet
{

    public FleetDescendingCapacity(List<Vehicle> vehicles)
    {
        super(vehicles);
    }

    @Override
    protected void orderVehicles()
    {
        vehicles.sort(new VehicleComparatorCapacityDescending());
    }
}


/**
 * Auxiliary class in order to sort the vehicles
 */
class VehicleComparatorCapacityDescending implements Comparator<Vehicle>
{
    @Override
    public int compare(Vehicle o1, Vehicle o2)
    {
        if (o1.capacity < o2.capacity)
            return 1;
        else
        {
            if (o1.capacity > o2.capacity)
                return -1;
            else
                return 0;
        }
    }
}