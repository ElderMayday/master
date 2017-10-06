package problem.ComponentStructure;

import problem.Component2d;
import problem.Coordinate2d;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aldar on 06-Oct-17.
 * Instances of this class with different columnNumbers may cause collisions for each other due to using same static multiplier values for hashing
 */
public class ComponentStructure2dHash extends ComponentStructure2d
{
    protected final Map<Coordinate2d, Component2d> map;
    protected final static Component2d nullComponent;  // stub component that is returned if trying to Get() non-initialized Component

    static
    {
        nullComponent = new Component2d(-1, -1, 0.0, 0.0);
    }

    //-----------------------------------------------------------------------


    public ComponentStructure2dHash(int rowNumber, int columnNumber)
    {
        super(rowNumber, columnNumber);
        map = new HashMap<Coordinate2d, Component2d>();
        Coordinate2d.setColNumber(columnNumber);            // to avoid collisions while hashing
    }

    @Override
    public Component2d Get(Coordinate2d coordinate2d)
    {
        if (map.containsKey(coordinate2d))
            return map.get(coordinate2d);
        else
            return ComponentStructure2dHash.nullComponent;
    }

    @Override
    public void SetT(Coordinate2d coordinate2d, double pheromone)
    {
        if (map.containsKey(coordinate2d))
            map.get(coordinate2d).setPheromone(pheromone);
        else
            map.put(coordinate2d, new Component2d(coordinate2d, 0.0, pheromone));
    }

    @Override
    public void SetH(Coordinate2d coordinate2d, double heuristic)
    {
        if (map.containsKey(coordinate2d))
            map.get(coordinate2d).setHeuristic(heuristic);
        else
            map.put(coordinate2d, new Component2d(coordinate2d, heuristic, 0.0));
    }
}
