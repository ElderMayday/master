package problem.Fleet;


import java.util.Iterator;
import java.util.List;


/**
 * Created by Aldar on 09-Oct-17.
 */
public abstract class Fleet
{
    protected final List<Vehicle> vehicles;


    public Fleet(List<Vehicle> vehicles)
    {
        this.vehicles = vehicles;

        orderVehicles();
    }



    protected abstract void orderVehicles();



    public void resetAllVehicles()
    {
        for (Vehicle vehicle : vehicles)
            vehicle.reset();
    }




    public Iterator<Vehicle> getVehiclesIterator()
    {
        return vehicles.iterator();
    }
}
