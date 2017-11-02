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

    protected double usedDistance;



    protected double leftDistance;
    protected double usedCapacity, leftCapacity;

    public Tour(Vehicle vehicle)
    {
        this.finished = false;
        this.vehicle = vehicle;
        this.customers = new ArrayList<Integer>();

        resetTourParameters();
    }


    public void resetTourParameters()
    {
        usedDistance = 0.0;
        usedCapacity = 0.0;

        leftDistance = vehicle.length;
        leftCapacity = vehicle.capacity;
    }


    public void addDistance(double distance) throws Exception
    {
        if (distance > 0.0)
        {
            this.usedDistance += distance;

            if (vehicle.hasLengthRestriction)
            {
                leftDistance -= distance;

                if (distance < 0)
                    throw new Exception("Distance constraint violation");
            }
        }
    }

    public void addCapacity(double capacity) throws Exception
    {
        if (capacity > 0.0)   // if did not arrive to the depot
        {
            this.usedCapacity += capacity;
            this.leftCapacity -= capacity;

            if (capacity < 0)
                throw new Exception("Distance constraint violation");
        }
    }




    public double getLeftDistance()
    {
        return leftDistance;
    }

    public double getLeftCapacity()
    {
        return leftCapacity;
    }

    public double getUsedDistance()
    {
        return usedDistance;
    }

    public double getUsedCapacity()
    {
        return usedCapacity;
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


    public String toString()
    {
        String result = "{";

        for (int i = 0; i < customers.size() - 1; i++)
            result += customers.get(i).toString() + "-";

        if (customers.size() > 0)
            result += customers.get(customers.size() - 1).toString();

        return result + "}";
    }
}
