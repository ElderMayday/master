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

            iterator.next().distractCapacity(20.0);
            iterator.next().distractLength(30.0);

            fleet.resetVehicleParameters();

            Vehicle current;
            iterator = fleet.getVehiclesIterator();

            current = iterator.next();
            assertEquals(current.getCapacityLeft(), 3.0, 0.001);
            assertEquals(current.getLengthLeft(), 1.0, 0.001);

            current = iterator.next();
            assertEquals(current.getCapacityLeft(), 2.0, 0.001);
            assertEquals(current.getLengthLeft(), 3.0, 0.001);

            current = iterator.next();
            assertEquals(current.getCapacityLeft(), 1.0, 0.001);
            assertEquals(current.getLengthLeft(), 2.0, 0.001);
        }
        catch (Exception exception)
        {
            assertTrue(false);    // no exception expected
        }
    }
}