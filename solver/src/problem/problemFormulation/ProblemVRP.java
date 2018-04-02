package problem.problemFormulation;

import org.json.JSONObject;
import problem.component.Component;
import problem.componentStructure.ComponentStructure2d;
import problem.fleet.Fleet;
import problem.fleet.Vehicle;
import problem.problemFormulation.parser.*;
import solving.candidateList.CandidateDeterminer;
import solving.candidateList.CandidateListVRP;
import solving.solution.Solution;
import solving.solution.SolutionVRP;
import solving.solution.Tour;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.Reference;
import java.util.*;

/**
 * Created by Aldar on 09-Oct-17.
 */
public class ProblemVRP extends Problem2d
{
    public final Fleet fleet;

    public int vertexNum;
    public double[] demands;
    public int depotId;             // number of the vertex that represents depot

    protected ConstructionPreselectorVRP constructionPreselector; // nested class single instance to construct solutions depending on the mode
    protected ReconstructionPreselectorVRP reconstructionPreselector;

    public ProblemVRP(ComponentStructure2d structure2d, Fleet fleet, CandidateDeterminer candidateDeterminer) throws FileNotFoundException
    {
        super(structure2d, true, candidateDeterminer);

        this.fleet = fleet;

        constructionPreselector = candidateDeterminer == null ? new ConstructionPreselectorVrpNoCandidates() : new ConstructionPreselectorVrpCandidates();
        reconstructionPreselector = candidateDeterminer == null ? new ReconstructionPreselectorVrpNoCandidates() : new ReconstructionPreselectorVrpCandidates();
    }

    @Override
    public List<Component> getConstructionComponents(Solution solution)
    {
        return constructionPreselector.getConstructionComponents((SolutionVRP) solution);
    }

    @Override
    public List<Component> getReconstructionComponents(Solution solution)
    {
        return reconstructionPreselector.getReconstructionComponents((SolutionVRP) solution);
    }

    @Override
    public Solution createSolution()
    {
        return new SolutionVRP(this);
    }

    @Override
    public int ProblemSize()
    {
        return vertexNum;
    }

    @Override
    protected void readDataFromFile(File file) throws Exception
    {
        String text = new Scanner(file).useDelimiter("\\Z").next();

        ParserVrp parserVrp;

        if (file.getAbsolutePath().endsWith(".json"))
            parserVrp = new ParserVrpJson();
        else if (file.getAbsolutePath().endsWith(".my"))
            parserVrp = new ParserVrpTxt();
        else
            throw new Exception("Problem format is not recognized");

        parserVrp.parseVRP(text, this);
    }

    @Override
    protected boolean checkFeasibility()
    {
        double totalCapacity = 0.0;
        double maxCapacity = 0.0;

        for (Iterator<Vehicle> iteratorVehicles = fleet.getVehiclesIterator(); iteratorVehicles.hasNext();)
        {
            double capacity = iteratorVehicles.next().capacity;

            totalCapacity += capacity;

            if (maxCapacity < capacity)
                maxCapacity = capacity;
        }

        double totalDemand = 0.0;
        double maxDemand = 0.0;

        for (double demand : demands)
        {
            if (demand >= 0.0)          // because depot is marked by negative demand
                totalDemand += demand;

            if (maxDemand < demand)     // find minDemand
                maxDemand = demand;
        }

        if (totalDemand > totalCapacity)
            return false;                   // non-feasible instance because total demand is higher than total capacity

        if (maxCapacity < maxDemand)
            return false;                   // one of the clients cannot be satisfied by any of the vehicles

        return true;
    }













    public int getVertexNum()
    {
        return vertexNum;
    }

    public double[] getDemands()
    {
        return demands;
    }

    public int getDepotId()
    {
        return depotId;
    }


    /**
     * Preselects components for the next construction step (!NOT RECONSTRUCTION), depending on the construction mode
     */
    interface ConstructionPreselectorVRP
    {
        List<Component> getConstructionComponents(SolutionVRP solutionVRP);
    }

    class ConstructionPreselectorVrpNoCandidates implements ConstructionPreselectorVRP
    {
        public List<Component> getConstructionComponents(SolutionVRP solutionVRP)
        {
            List<Component> result = new ArrayList<Component>();

            int row = solutionVRP.getCurrentCustomerId();

            Tour currentTour = solutionVRP.getCurrentTour();

            for (int column = 0; column < vertexNum; column++)
                if (!solutionVRP.getVisited(column) && (row != column) && (column != depotId))
                    if (currentTour.getLeftCapacity() >= demands[column]) // if can satisfy that customer
                        if (!currentTour.getVehicle().hasLengthRestriction ||
                                (currentTour.getLeftDistance() >= structure2d.get(row, column).getDistance() + structure2d.get(column, depotId).getDistance()))  // relies on triangle inequality in the graph
                            result.add(structure2d.get(row, column));

            if (result.size() == 0)
                result.add(structure2d.get(row, depotId));

            return result;
        }
    }

    class ConstructionPreselectorVrpCandidates implements ConstructionPreselectorVRP
    {
        public List<Component> getConstructionComponents(SolutionVRP solutionVRP)
        {
            List<Component> result = new ArrayList<Component>();

            CandidateListVRP allCandidateListsVRP = (CandidateListVRP)  ProblemVRP.this.candidateList;

            int row = solutionVRP.getCurrentCustomerId();

            List<Integer> currentCandidateListVRP = allCandidateListsVRP.candidates.get(row);

            Tour currentTour = solutionVRP.getCurrentTour();

            for (Integer column : currentCandidateListVRP)
                if (!solutionVRP.getVisited(column))  // supposing back-to-the-depot and loop edges are excluded
                    if (currentTour.getLeftCapacity() >= demands[column]) // if can satisfy that customer
                        if (!currentTour.getVehicle().hasLengthRestriction ||
                                (currentTour.getLeftDistance() >= structure2d.get(row, column).getDistance() + structure2d.get(column, depotId).getDistance()))  // relies on triangle inequality in the graph
                            result.add(structure2d.get(row, column));

            if (result.size() == 0)      // if none of candidates was selected, then pick first-fitting from the rest
            {
                List<Integer> currentRestListVRR = allCandidateListsVRP.rest.get(row);

                for (Integer column : currentRestListVRR)
                    if (!solutionVRP.getVisited(column))  // supposing back-to-the-depot and loop edges are excluded
                        if (currentTour.getLeftCapacity() >= demands[column]) // if can satisfy that customer
                            if (!currentTour.getVehicle().hasLengthRestriction ||
                                    (currentTour.getLeftDistance() >= structure2d.get(row, column).getDistance() + structure2d.get(column, depotId).getDistance()))  // relies on triangle inequality in the graph
                            {
                                result.add(structure2d.get(row, column));
                                break;
                            }
            }

            if (result.size() == 0)
                result.add(structure2d.get(row, depotId));

            return result;
        }
    }


    /**
     * Preselects components for the next reconstruction step (!NOT CONSTRUCTION), depending on the construction mode
     * REMARK: this pre-selector MUST be called before the component addition to the solution, since it sets current tour and customer (always the first possible)
     */
    interface ReconstructionPreselectorVRP
    {
        List<Component> getReconstructionComponents(SolutionVRP solutionRef);
    }

    class ReconstructionPreselectorVrpNoCandidates implements ReconstructionPreselectorVRP
    {
        public List<Component> getReconstructionComponents(SolutionVRP solutionVRP)
        {
            List<Component> result = new ArrayList<Component>();

            // find first non-finished tour

            Tour tour = null;
            for (Tour searchTour : solutionVRP.getTours())
                if (!searchTour.isFinished())
                {
                    tour = searchTour;
                    break;
                }

            if (tour != null) // if has a tour to finish
            {
                List<Integer> tourCustomers = tour.getCustomers();
                solutionVRP.setCurrentTour(tour);
                int row = tourCustomers.size() > 0 ? tourCustomers.get(tourCustomers.size() - 1) : depotId;
                solutionVRP.setCurrentCustomerId(row);

                Tour currentTour = solutionVRP.getCurrentTour();

                for (int column = 0; column < vertexNum; column++)
                    if (!solutionVRP.getVisited(column) && (row != column) && (column != depotId))
                        if (currentTour.getLeftCapacity() >= demands[column]) // if can satisfy that customer
                            if (!currentTour.getVehicle().hasLengthRestriction ||
                                    (currentTour.getLeftDistance() >= structure2d.get(row, column).getDistance() + structure2d.get(column, depotId).getDistance()))  // relies on triangle inequality in the graph
                                result.add(structure2d.get(row, column));

                if (result.size() == 0)
                    result.add(structure2d.get(row, depotId));
            }
            else  // if has no tour to finish, but the solution is still incomplete, so requires a new vehicle
            {
                solutionVRP.setCurrentCustomerId(depotId);
                int row = depotId;
                Tour currentTour = solutionVRP.startNewTour();

                for (int column = 0; column < vertexNum; column++)
                    if (!solutionVRP.getVisited(column) && (row != column) && (column != depotId))
                        if (currentTour.getLeftCapacity() >= demands[column]) // if can satisfy that customer
                            if (!currentTour.getVehicle().hasLengthRestriction ||
                                    (currentTour.getLeftDistance() >= structure2d.get(row, column).getDistance() + structure2d.get(column, depotId).getDistance()))  // relies on triangle inequality in the graph
                                result.add(structure2d.get(row, column));

                if (result.size() == 0)
                    result.add(structure2d.get(row, depotId));
            }

            return result;
        }
    }

    class ReconstructionPreselectorVrpCandidates implements ReconstructionPreselectorVRP
    {
        public List<Component> getReconstructionComponents(SolutionVRP solutionVRP)
        {
            List<Component> result = new ArrayList<Component>();

            // find first non-finished tour

            Tour tour = null;
            for (Tour searchTour : solutionVRP.getTours())
                if (!searchTour.isFinished())
                {
                    tour = searchTour;
                    break;
                }

            if (tour != null) // if has a tour to finish
            {
                List<Integer> tourCustomers = tour.getCustomers();
                solutionVRP.setCurrentTour(tour);
                int row = tourCustomers.size() > 0 ? tourCustomers.get(tourCustomers.size() - 1) : depotId;
                solutionVRP.setCurrentCustomerId(row);

                Tour currentTour = solutionVRP.getCurrentTour();

                CandidateListVRP allCandidateListsVRP = (CandidateListVRP)  ProblemVRP.this.candidateList;
                List<Integer> currentCandidateListVRP = allCandidateListsVRP.candidates.get(row);

                for (Integer column : currentCandidateListVRP)
                    if (!solutionVRP.getVisited(column))  // supposing back-to-the-depot and loop edges are excluded
                        if (currentTour.getLeftCapacity() >= demands[column]) // if can satisfy that customer
                            if (!currentTour.getVehicle().hasLengthRestriction ||
                                    (currentTour.getLeftDistance() >= structure2d.get(row, column).getDistance() + structure2d.get(column, depotId).getDistance()))  // relies on triangle inequality in the graph
                                result.add(structure2d.get(row, column));

                if (result.size() == 0)      // if none of candidates was selected, then pick first-fitting from the rest
                {
                    List<Integer> currentRestListVRR = allCandidateListsVRP.rest.get(row);

                    for (Integer column : currentRestListVRR)
                        if (!solutionVRP.getVisited(column))  // supposing back-to-the-depot and loop edges are excluded
                            if (currentTour.getLeftCapacity() >= demands[column]) // if can satisfy that customer
                                if (!currentTour.getVehicle().hasLengthRestriction ||
                                        (currentTour.getLeftDistance() >= structure2d.get(row, column).getDistance() + structure2d.get(column, depotId).getDistance()))  // relies on triangle inequality in the graph
                                {
                                    result.add(structure2d.get(row, column));
                                    break;
                                }
                }

                if (result.size() == 0)
                    result.add(structure2d.get(row, depotId));
            }
            else  // if has no tour to finish, but the solution is still incomplete, so requires a new vehicle
            {
                solutionVRP.setCurrentCustomerId(depotId);
                int row = depotId;
                Tour currentTour = solutionVRP.startNewTour();

                CandidateListVRP allCandidateListsVRP = (CandidateListVRP)  ProblemVRP.this.candidateList;
                List<Integer> currentCandidateListVRP = allCandidateListsVRP.candidates.get(row);

                for (Integer column : currentCandidateListVRP)
                    if (!solutionVRP.getVisited(column))  // supposing back-to-the-depot and loop edges are excluded
                        if (currentTour.getLeftCapacity() >= demands[column]) // if can satisfy that customer
                            if (!currentTour.getVehicle().hasLengthRestriction ||
                                    (currentTour.getLeftDistance() >= structure2d.get(row, column).getDistance() + structure2d.get(column, depotId).getDistance()))  // relies on triangle inequality in the graph
                                result.add(structure2d.get(row, column));

                if (result.size() == 0)      // if none of candidates was selected, then pick first-fitting from the rest
                {
                    List<Integer> currentRestListVRR = allCandidateListsVRP.rest.get(row);

                    for (Integer column : currentRestListVRR)
                        if (!solutionVRP.getVisited(column))  // supposing back-to-the-depot and loop edges are excluded
                            if (currentTour.getLeftCapacity() >= demands[column]) // if can satisfy that customer
                                if (!currentTour.getVehicle().hasLengthRestriction ||
                                        (currentTour.getLeftDistance() >= structure2d.get(row, column).getDistance() + structure2d.get(column, depotId).getDistance()))  // relies on triangle inequality in the graph
                                {
                                    result.add(structure2d.get(row, column));
                                    break;
                                }
                }

                if (result.size() == 0)
                    result.add(structure2d.get(row, depotId));
            }

            return result;
        }
    }
}
