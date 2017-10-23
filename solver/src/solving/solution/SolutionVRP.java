package solving.solution;

import problem.component.Component;
import problem.component.Component2d;
import problem.fleet.Vehicle;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Aldar on 14-Oct-17.
 */
public class SolutionVRP extends Solution
{
    protected List<Component2d> components2d;   // Chosen components2d list
    protected ProblemVRP problemVRP;            // The problem this solving belongs to

    protected boolean[] visited;                // Flag array of whether the corresponding custumer has been visited
    protected Tour currentTour;                 // The tour where the new components will be added
    protected List<Tour> tours;                 // Every tour of the solution
    protected int currentCustomerId;            // the id of the customer that was added
    protected Iterator<Vehicle> vehicleIterator; // The iterator, that automatically fetches the vehicles from the problemVrp fleet


    public SolutionVRP(Problem problem)
    {
        super(problem);

        problemVRP = (ProblemVRP) problem;

        visited = new boolean[problemVRP.getVertexNum()];
        for (int i = 0; i < visited.length; i++)
            visited[i] = false;

        currentCustomerId = 0;                                 // to ensure the creation of a first new tour for the first component
        visited[((ProblemVRP) problem).getDepotId()] = true;

        components2d = new ArrayList<Component2d>();
        vehicleIterator = problemVRP.fleet.getVehiclesIterator();

        tours = new ArrayList<Tour>();
        currentTour = null;
    }


    public SolutionVRP()
    {
        components2d = new ArrayList<Component2d>();
    }

    protected void startNewTour()
    {
        Vehicle vehicle = vehicleIterator.next();

        Tour tour = new Tour(vehicle);

        tours.add(tour);

        if (currentTour != null)
            currentTour.setFinished(true);

        currentTour = tour;
    }


    /**
     * Adding a component to the current tour
     * REMARKS!!!
     * - Does not check whether the component is really from the problem components structure
     * - Does not check whether the customer was already passed (must be ensured at correct pre-selecting)
     * - Does not check whether vehicle capacity or length constraint are violated, however distracts those values
     * @param component
     */
    @Override
    public void addComponent(Component component) throws Exception
    {
        Component2d component2d = (Component2d) component;

        components2d.add(component2d);

        if (component2d.getRow() == problemVRP.getDepotId())
            startNewTour();
        else if (component2d.getRow() != currentCustomerId)
            throw new Exception("New component neither finishes the current tour, nor starts a new one");

        currentCustomerId = component2d.getColumn();
        visited[currentCustomerId] = true;

        currentTour.getCustomers().add(component2d.getColumn());
        currentTour.addCapacity(problemVRP.getDemands()[currentCustomerId]);
        currentTour.addDistance(component2d.getDistance());

        if (currentCustomerId == problemVRP.getDepotId())
        {
            currentTour.setFinished(true);
            currentTour = null;
        }
    }


    @Override
    public double objective()
    {
        double objective = 0.0;

        for (Component2d component2d : components2d)
            objective += component2d.getDistance();

        return objective;
    }

    public boolean getVisited(int index)
    {
        return visited[index];
    }




    public List<Component2d> getComponents2d()
    {
        return components2d;
    }

    public List<Tour> getTours()
    {
        return tours;
    }

    public Tour getCurrentTour()
    {
        return currentTour;
    }

    public int getCurrentCustomerId()
    {
        return currentCustomerId;
    }



    @Override
    public Iterator<Component> iterator()
    {
        return new SolutionVrpIterator();
    }


    /**
     * Wrapper iterator which casts Component2d to Component (for handling solutions of general problems)
     */
    class SolutionVrpIterator implements Iterator<Component>
    {
        protected Iterator<Component2d> component2dIterator;

        public SolutionVrpIterator()
        {
            component2dIterator = components2d.iterator();
        }

        public boolean hasNext()
        {
            return component2dIterator.hasNext();
        }

        public Component next()
        {
            return component2dIterator.next();
        }
    }
}
