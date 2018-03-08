package problem.problemFormulation;

import org.json.JSONObject;
import problem.component.Component;
import problem.componentStructure.ComponentStructure2d;
import problem.fleet.Fleet;
import problem.fleet.Vehicle;
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

    protected int vertexNum;
    protected double[] demands;
    protected int depotId;             // number of the vertex that represents depot

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
        else
            parserVrp = new ParserVrpTxt();

        parserVrp.parseVRP(text);
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





    abstract class ParserVrp
    {
        public abstract void parseVRP(String text) throws Exception;
    }

    class ParserVrpJson extends ParserVrp
    {

        @Override
        public void parseVRP(String text) throws Exception
        {
            JSONObject jProblem = new JSONObject(text);

            this.parseVehicles(jProblem);
            this.parseMap(jProblem);
        }

        protected void parseVehicles(JSONObject jProblem)
        {
            JSONObject jVehicles = jProblem.getJSONObject("vehicles");

            List<Vehicle> vehicleList = new ArrayList<Vehicle>();
            for (Iterator<String> vehicleIterator = jVehicles.keys(); vehicleIterator.hasNext();)
            {
                String vehicleID = vehicleIterator.next();
                JSONObject jVehicle = jVehicles.getJSONObject(vehicleID);

                double capacity = jVehicle.getDouble("capacity");
                double length = jVehicle.getDouble("length");     // negative length is considered as missing of length restriction

                Vehicle newVehicle = new Vehicle(capacity, length >= 0 ? length : 0.0, length > 0);
                vehicleList.add(newVehicle);
            }

            fleet.setVehicles(vehicleList);
        }

        protected void parseMap(JSONObject jProblem) throws Exception
        {
            JSONObject jMap = jProblem.getJSONObject("map");

            vertexNum = jMap.getInt("vertexNum");

            demands = new double[vertexNum];
            depotId = -1;
            structure2d.allocate(vertexNum, vertexNum);

            JSONObject jVertexes = jMap.getJSONObject("vertexes");

            for (Iterator<String> vertexIterator = jVertexes.keys(); vertexIterator.hasNext();)
            {
                JSONObject jVertex = jVertexes.getJSONObject(vertexIterator.next());

                int vertexId = jVertex.getInt("id");

                double demand = jVertex.getDouble("demand");

                if (demand < 0.0)
                {
                    if (depotId == -1)
                        depotId = vertexId;
                    else
                        throw new Exception("Duplicate depot found at vertex #" + vertexId);
                }

                demands[vertexId] = demand;

                JSONObject jTargets = jVertex.getJSONObject("targets");

                for (Iterator<String> targetIterator = jTargets.keys(); targetIterator.hasNext();)
                {
                    JSONObject jTarget = jTargets.getJSONObject(targetIterator.next());

                    int targetId = jTarget.getInt("id");
                    double distance = jTarget.getDouble("distance");

                    structure2d.setDistance(vertexId, targetId, distance);

                    if (vertexId == targetId)
                        if (distance >= 0.0)
                            throw new Exception("Self path should be marked by negative distance");

                    if (jTarget.has("heuristic"))
                        structure2d.setH(vertexId, targetId, jTarget.getDouble("heuristic"));
                }
            }
        }
    }

    class ParserVrpTxt extends ParserVrp
    {

        @Override
        public void parseVRP(String text) throws Exception
        {
            String token[] = text.split("(\\s|\\n)+");

            depotId = 0;

            // vehicle number -----------------------------

            int vehicleNum = Integer.parseInt(token[0]);

            int index = 2;

            // vehicles parameters -------------------------------------------

            List<Vehicle> vehicleList = new ArrayList<Vehicle>();
            for (int i = 0; i < vehicleNum; i++)
            {
                double capacity = Double.parseDouble(token[index]);
                double length = Double.parseDouble(token[index + 1]);     // negative length is considered as missing of length restriction

                Vehicle newVehicle = new Vehicle(capacity, length >= 0 ? length : 0.0, length > 0);
                vehicleList.add(newVehicle);

                index += 2;
            }
            fleet.setVehicles(vehicleList);

            index++;

            // customer number ----------------------------

            int customerNum = Integer.parseInt(token[index]);

            index += 2;

            // demands ------------------

            vertexNum = customerNum + 1;
            demands = new double[vertexNum];

            for (int i = 1; i < vertexNum; i++, index++)
                demands[i] = Double.parseDouble(token[index]);

            index++;

            // distances -----------------------------

            structure2d.allocate(vertexNum, vertexNum);

            for (int i = 0; i < vertexNum; i++)
                for (int j = 0; j < vertexNum; j++)
                {
                    double distance = Double.parseDouble(token[index]);

                    structure2d.setDistance(i, j, distance);

                    if (i == j)
                        if (distance >= 0.0)
                            throw new Exception("Self path should be marked by negative distance");

                    index++;
                }

            index++;

            // heuristics -----------------------------------

            for (int i = 0; i < vertexNum; i++)
                for (int j = 0; j < vertexNum; j++)
                {
                    structure2d.setH(i, j, Double.parseDouble(token[index]));
                    index++;
                }
        }
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
