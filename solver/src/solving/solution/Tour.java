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


    public Tour(Vehicle vehicle)
    {
        this.finished = false;
        this.vehicle = vehicle;
        this.customers = new ArrayList<Integer>();
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
