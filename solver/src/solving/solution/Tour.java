package solving.solution;

import problem.fleet.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 19-Oct-17.
 */
public class Tour
{
    protected boolean finished;           // flag if the tour is finished. At construction stage solution may have only one non-finished tour, but in iterated-greedy one may have several non-finished
    protected Vehicle vehicle;            // the vehicle used for passing the tour
    protected List<Integer> customers;    // IDs of the customers in order of their visiting

    protected double totalDistance, leftDistance;
    protected double totalCapacity, leftCapacity;

    public Tour(Vehicle vehicle)
    {
        this.finished = false;
        this.vehicle = vehicle;
        this.customers = new ArrayList<Integer>();

        totalDistance = 0.0;
        totalCapacity = 0.0;

        leftDistance = vehicle.length;
        leftCapacity = vehicle.capacity;
    }


    public void addDistance(double distance)
    {
        this.totalDistance += distance;

        if (vehicle.hasLengthRestriction)
            leftDistance -= distance;
    }

    public void addCapacity(double capacity)
    {
        if (capacity > 0.0)   // if not arrive to a depot
            this.totalCapacity += capacity;
    }


    public double getTotalDistance()
    {
        return totalDistance;
    }

    public double getTotalCapacity()
    {
        return totalCapacity;
    }

    public boolean isFinished()
    {
        return finished;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }

    public List<Integer> getCustomers()
    {
        return customers;
    }
}
