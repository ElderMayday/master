package solving.selectors;

import org.junit.Test;
import problem.fleet.Fleet;
import problem.fleet.FleetDescendingCapacity;
import problem.fleet.Vehicle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 09-Oct-17.
 */
public class TestFleet
{
    @Test
    public void testFleetByCapacity()
    {
        try
        {
            List<Vehicle> vehicleList = new ArrayList<Vehicle>();
            vehicleList.add(new Vehicle(3.0, 1.0, true));
            vehicleList.add(new Vehicle(1.0, 2.0, true));
            vehicleList.add(new Vehicle(2.0, 3.0, true));

            Fleet fleet = new FleetDescendingCapacity();
            fleet.setVehicles(vehicleList);

            Iterator<Vehicle> iterator = fleet.getVehiclesIterator();

            Vehicle current;
            iterator = fleet.getVehiclesIterator();

            current = iterator.next();
            assertEquals(current.capacity, 3.0, 0.001);
            assertEquals(current.length, 1.0, 0.001);

            current = iterator.next();
            assertEquals(current.capacity, 2.0, 0.001);
            assertEquals(current.length, 3.0, 0.001);

            current = iterator.next();
            assertEquals(current.capacity, 1.0, 0.001);
            assertEquals(current.length, 2.0, 0.001);
        }
        catch (Exception exception)
        {
            assertTrue(false);    // no exception expected
        }
    }
}
