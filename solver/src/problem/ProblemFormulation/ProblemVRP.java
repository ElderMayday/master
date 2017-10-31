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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

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


    public ProblemVRP(ComponentStructure2d structure2d, Fleet fleet, CandidateDeterminer candidateDeterminer) throws FileNotFoundException
    {
        super(structure2d, true, candidateDeterminer);

        this.fleet = fleet;

        constructionPreselector = candidateDeterminer == null ? new ConstructionPreselectorVrpNoCandidates() : new ConstructionPreselectorVrpCandidates();
    }

    @Override
    public List<Component> getNextComponents(Solution solution)
    {
        return constructionPreselector.getNextComponents((SolutionVRP) solution);
    }

    @Override
    protected void readDataFromFile(File file) throws Exception
    {
        String text = new Scanner(file).useDelimiter("\\Z").next();

        ParserVrp parserVrp;

        if (file.getAbsolutePath().endsWith(".json"))
            parserVrp = new ParserVrpJson();
        else
            throw new Exception("File format is not expected");

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
        List<Component> getNextComponents(SolutionVRP solutionVRP);
    }

    class ConstructionPreselectorVrpNoCandidates implements ConstructionPreselectorVRP
    {

        @Override
        public List<Component> getNextComponents(SolutionVRP solutionVRP)
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

        @Override
        public List<Component> getNextComponents(SolutionVRP solutionVRP)
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
}
