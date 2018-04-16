package solving.solution;

import problem.component.Component;
import problem.component.Component2d;
import problem.componentStructure.ComponentStructure2d;
import problem.fleet.Vehicle;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;
import solving.selectors.Selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Aldar on 14-Oct-17.
 */
public class SolutionVRP extends Solution
{
    public List<Component2d> components2d;      // Chosen components2d candidates (while constructing and reconstructing, use the corresponding methods)
    protected ProblemVRP problemVRP;            // The problem this solving belongs to

    protected boolean[] visited;                // Flag array of whether the corresponding customer has been visited
    protected int visitedNum;                   // Number of visited customers (except depot) max = N - 1

    protected List<Tour> tours;                 // Every tour of the solution

    protected Tour currentTour;                 // The tour where the new components will be added
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
        visited[problemVRP.getDepotId()] = true;
        visitedNum = 0;

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

    public Tour startNewTour()
    {
        Vehicle vehicle = vehicleIterator.next();

        Tour tour = new Tour(vehicle);
        tour.getCustomers().add(problemVRP.getDepotId());

        tours.add(tour);

        if (currentTour != null)
            currentTour.setFinished(true);

        currentTour = tour;

        return tour;
    }

    public void setVehicleIteratorToIndex(int numberOfUsed)
    {
        vehicleIterator = problemVRP.fleet.getVehiclesIterator();

        for (int i = 0; i < numberOfUsed; i++)
            vehicleIterator.next();
    }


    /**
     * Adding a component to the current tour
     * REMARKS!!!
     * - Does not check whether the component is really from the problem components structure
     * - Does not check whether the customer was already passed (must be ensured at correct pre-selecting)
     * @param component
     */
    @Override
    public void addConstructionComponent(Component component) throws Exception
    {
        if (isPartiallyDestroyed)
            throw new Exception("Cannot use construction method for partially destroyed solution");

        Component2d component2d = (Component2d) component;
        components2d.add(component2d);

        if (component2d.getRow() != currentCustomerId)
            throw new Exception("New component does not proceed the current tour");

        currentCustomerId = component2d.getColumn();

        currentTour.getCustomers().add(component2d.getColumn());

        if (currentCustomerId != problemVRP.getDepotId())
        {
            visitedNum++;

            if (visited[currentCustomerId])
                throw new Exception("Vehicle has revisited a customer");

            visited[currentCustomerId] = true;
        }

        currentTour.addCapacity(problemVRP.getDemands()[currentCustomerId]);
        currentTour.addDistance(component2d.getDistance());

        if (currentCustomerId == problemVRP.getDepotId())    // if returned to the depot
        {
            currentTour.setFinished(true);

            if (component2d.getRow() == problemVRP.getDepotId()) // could not even start a tour (vehicle stays in the depot)
                currentTour.getCustomers().clear();              // delete the only component c[depotId;depotId] since it is not necessary

            if (visitedNum != (problemVRP.getVertexNum() - 1))    // if more tours expected to finish the solution
                startNewTour();
            else                                            // if visited everything
            {
                isComplete = true;
                currentTour = null;
                recomputeObjective();
            }
        }
    }


    /**
     * Adding a component to the current tour
     * REMARKS!!!
     * - Does not check whether the component is really from the problem components structure
     * - Does not check whether the customer was already passed (must be ensured at correct pre-selecting)
     * - Must add new tours if not finished
     * - Must prune empty tours if finished with them
     * REQUIREMENTS!!
     * - currentTour must be correctly set by the ProblemVRP while pre-selecting
     * - currentCustomerId must be correctly set by the ProblemVRP (corresponding to the currentTour last customer or to the depotId
     * @param component
     */
    @SuppressWarnings("Since15")
    @Override
    public void addReconstructionComponent(Component component) throws Exception
    {
        if (!isPartiallyDestroyed)
            throw new Exception("Cannot use construction method for partially non-destroyed solution");

        Component2d component2d = (Component2d) component;
        components2d.add(component2d);

        int depotId = problemVRP.getDepotId();

        if (currentTour == null)  // reconstruction status requires to involve an additional vehicle
        {
            if (component2d.getRow() != depotId)
                throw new Exception("Component that is supposed to start a new tour does not start it");

            startNewTour();
        }

        if (component2d.getRow() != currentCustomerId)
            throw new Exception("New component does not proceed the current tour");

        currentCustomerId = component2d.getColumn();

        if (currentCustomerId != problemVRP.getDepotId())
        {
            visitedNum++;
            currentTour.getCustomers().add(component2d.getColumn());

            if (visited[currentCustomerId])
                throw new Exception("Vehicle has revisited a customer");

            visited[currentCustomerId] = true;
        }

        currentTour.addCapacity(problemVRP.getDemands()[currentCustomerId]);
        currentTour.addDistance(component2d.getDistance());

        if (currentCustomerId == problemVRP.getDepotId())    // if returned to the depot
        {
            currentTour.setFinished(true);

            // solution is reconstructed, now let us remove the tours which have been left empty (no need to reconstruct them) if such exist

            if (component2d.getRow() == problemVRP.getDepotId()) // could not even start a tour (vehicle stays in the depot)
                currentTour.getCustomers().clear();              // delete the only component c[depotId;depotId] since it is not necessary

            if (visitedNum == problemVRP.getVertexNum() - 1)  // if visited everything (does not cause a new tour, preselection should cause it, as it is written higher)
            {
                isComplete = true;
                isPartiallyDestroyed = false;
                currentTour.getCustomers().add(problemVRP.getDepotId());
                currentTour = null;
                recomputeObjective();

                tours.removeIf(new Predicate<Tour>()
                {
                    public boolean test(Tour tour)
                    {
                        return tour.getCustomers().size() == 1;
                    }
                });
            }
        }
    }


    @Override
    public void recomputeObjective()
    {
        objective = 0.0;

        for (Component2d component2d : components2d)
            objective += component2d.getDistance();
    }




    public boolean getVisited(int index)
    {
        return visited[index];
    }

    /**
     * if visited is set by external caller, then visitedNum is automatically incremented/decremented, interior methods should care themselves
     * @param index
     * @param value
     */
    public void setVisited(int index, boolean value)
    {
        if (value)
        {
            if (!visited[index])
            {
                visitedNum++;
                visited[index] = true;
            }
        }
        else
        {
            if (visited[index])
            {
                visitedNum--;
                visited[index] = false;
            }
        }
    }

    /**
     * Sets all visited to false, except the depotId
     */
    public void unsetAllVisited()
    {
        for (int i = 0; i < visited.length; i++)
            visited[i] = false;

        visited[problemVRP.getDepotId()] = true;

        visitedNum = 0;
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

    public int getVisitedNum()
    {
        return visitedNum;
    }

    public void setTours(List<Tour> tours) throws Exception
    {
        this.tours = tours;

        this.unsetAllVisited();

        for (Tour tour : tours)
            for (Integer index : tour.getCustomers())
                this.setVisited(index, true);

        components2d = new ArrayList<Component2d>();
        for (Tour tour : tours)
        {
            List<Integer> customers = tour.getCustomers();
            ComponentStructure2d structure2d = problemVRP.structure2d;

            //components2d.add(structure2d.get(problemVRP.getDepotId(), customers.get(0)));

            for (int i = 0; i < customers.size() - 1; i++)
            {
                int nextCustomer = customers.get(i + 1);

                Component2d component2d = structure2d.get(customers.get(i), nextCustomer);
                components2d.add(structure2d.get(customers.get(i), nextCustomer));
            }
        }
    }

    public Tour getCurrentTour()
    {
        return currentTour;
    }

    public int getCurrentCustomerId()
    {
        return currentCustomerId;
    }

    public void setCurrentTour(Tour currentTour)
    {
        this.currentTour = currentTour;
    }

    public void setCurrentCustomerId(int currentCustomerId)
    {
        this.currentCustomerId = currentCustomerId;
    }


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


    /**
     * Performs deep copy of the solution include all its tour.
     * @return
     */
    @Override
    public Solution deepCopy()
    {
        SolutionVRP solutionVRP = new SolutionVRP(problem);

        solutionVRP.isComplete = this.isComplete;
        solutionVRP.isPartiallyDestroyed = this.isPartiallyDestroyed;

        solutionVRP.components2d = new ArrayList<Component2d>(this.components2d); // only list is copied, the instances are the same from the problem definition

        solutionVRP.visited = Arrays.copyOf(this.visited, this.visited.length);

        solutionVRP.visitedNum = this.visitedNum;

        solutionVRP.tours = new ArrayList<Tour>();

        for (Tour tour : this.tours)
            solutionVRP.tours.add(tour.deepCopy());  // every tour has to be copied

        if (currentTour != null)
        {
            int index = tours.indexOf(currentTour);
            solutionVRP.currentTour = solutionVRP.tours.get(index);
        }
        else
            solutionVRP.currentTour = null;

        solutionVRP.objective = this.objective;
        solutionVRP.lastObjective = this.lastObjective;

        solutionVRP.currentCustomerId = this.currentCustomerId;

        if (solutionVRP.isComplete)
            solutionVRP.setVehicleIteratorToIndex(tours.size()); // to set the vehicle iterator at the respective position

        return solutionVRP;
    }

    @Override
    public List<Component> getComponents()
    {
        List<Component> result = new ArrayList<Component>();

        for (Component2d component2d : components2d)
            result.add(component2d);

        return result;
    }

    public int getNumberOfComponents()
    {
        return components2d.size();
    }


    public String toString()
    {
        String result = "";

        for (Tour tour : tours)
            result += tour.toString() + " ";

        if (isPartiallyDestroyed)
            result += "par=" + String.format("%.2f", lastObjective);
        else
            result += "obj=" + String.format("%.2f", objective);

        return result;
    }
}
