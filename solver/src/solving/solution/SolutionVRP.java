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
    protected List<Component2d> components2d;   // Chosen components2d candidates
    protected ProblemVRP problemVRP;            // The problem this solving belongs to

    protected boolean[] visited;                // Flag array of whether the corresponding custumer has been visited
    protected int visitedNum;                   // Number of visited customers

    protected Tour currentTour;                 // The tour where the new components will be added
    protected List<Tour> tours;                 // Every tour of the solution
    protected int currentCustomerId;            // The ID of the customer that was added
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
        visitedNum = 1;

        components2d = new ArrayList<Component2d>();
        vehicleIterator = problemVRP.fleet.getVehiclesIterator();

        tours = new ArrayList<Tour>();
        currentTour = null;

        startNewTour();
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
     * @param component
     */
    @Override
    public void addCurrentTourComponent(Component component) throws Exception
    {
        if (isPartiallyDestroyed)
            throw new Exception("Cannot use construction method for partially destroyed solution");

        Component2d component2d = (Component2d) component;

        components2d.add(component2d);

        if (component2d.getRow() != currentCustomerId)
            throw new Exception("New component does not procede the current tour");

        currentCustomerId = component2d.getColumn();

        if (currentCustomerId != problemVRP.getDepotId())
        {
            visitedNum++;
            currentTour.getCustomers().add(component2d.getColumn());

            if (visited[currentCustomerId])
                throw new Exception("Vehicle has revisited a customer");
        }

        visited[currentCustomerId] = true;

        currentTour.addCapacity(problemVRP.getDemands()[currentCustomerId]);
        currentTour.addDistance(component2d.getDistance());

        if (currentCustomerId == problemVRP.getDepotId())    // if returned to the depot
        {
            currentTour.setFinished(true);

            if (component2d.getRow() == problemVRP.getDepotId()) // could not even start a tour (vehicle stays in the depot)
                currentTour.getCustomers().clear();              // delete the only component c[depotId;depotId] since it is not necessary

            if (visitedNum != problemVRP.getVertexNum())    // if more tours expected to finish the solution
                startNewTour();
            else                                            // if visited everyhting
            {
                isComplete = true;
                currentTour = null;
            }
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




    @Override
    public void reconstruct() throws Exception
    {
        if (!isPartiallyDestroyed)
            throw new Exception("Cannot reconstruct not partially destroyed solution");

        // TO-DO reconstruct here

        isPartiallyDestroyed = false;
        isComplete = true;
    }


    public boolean getVisited(int index)
    {
        return visited[index];
    }

    public void setVisited(int index, boolean value)
    {
        visited[index] = value;
    }

    public ProblemVRP getProblemVRP()
    {
        return problemVRP;
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





    public String toString()
    {
        String result = "";

        for (Tour tour : tours)
            result += tour.toString() + " ";

        return result;
    }
}
