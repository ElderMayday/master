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



    public SolutionVRP(Problem problem) throws Exception
    {
        super(problem);

        if (problem instanceof ProblemVRP)
        {
            problemVRP = (ProblemVRP) problem;

            visited = new boolean[problemVRP.getVertexNum()];
            for (int i = 0; i < visited.length; i++)
                visited[i] = false;
            visited[((ProblemVRP) problem).getDepotId()] = true;

            tours = new ArrayList<Tour>();
            currentTour = null;

            components2d = new ArrayList<Component2d>();
        }
        else
            throw new Exception("Type mismatch");
    }

    public SolutionVRP()
    {
        components2d = new ArrayList<Component2d>();
    }

    public void startNewTour(Vehicle vehicle)
    {
        Tour tour = new Tour(vehicle);

        tours.add(tour);

        if (currentTour != null)
            currentTour.setFinished(true);


        currentTour = tour;
    }

    public void finishCurrentTour()
    {
        currentTour.setFinished(true);
        currentTour = null;
    }

    /**
     * Adding a component to the current tour
     * REMARKS!!!
     * - Does not check whether the customer was already passed
     * - Does not check whether neighbour components2d match each other
     * @param component
     */
    @Override
    public void addComponent(Component component)
    {
        if (component instanceof Component2d)
        {
            Component2d component2d = (Component2d) component;

            components2d.add(component2d);

            int column = component2d.getColumn();

            visited[column] = true;

            currentTour.getCustomers().add(component2d.getColumn());
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
