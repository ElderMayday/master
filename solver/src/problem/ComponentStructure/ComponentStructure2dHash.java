package problem.ComponentStructure;

import problem.component.Component;
import problem.component.Component2d;
import problem.Coordinate2d;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Aldar on 06-Oct-17.
 * Instances of this class with different columnNumbers may cause collisions for each other due to using same static multiplier values for hashing
 */
public class ComponentStructure2dHash extends ComponentStructure2d
{
    protected Map<Coordinate2d, Component2d> map;
    protected static Component2d nullComponent;  // stub component that is returned if trying to Get() non-initialized Component

    static
    {
        nullComponent = new Component2d(-1, -1, 1.0, 0.0);
    }

    //-----------------------------------------------------------------------


    public ComponentStructure2dHash()
    {

    }


    public void allocate(int rowNumber, int columnNumber)
    {
        super.allocate(rowNumber, columnNumber);
        map = new HashMap<Coordinate2d, Component2d>();
        Coordinate2d.setMaxColumnNumber(columnNumber);            // to avoid collisions while hashing
    }



    @Override
    public Component2d get(Coordinate2d coordinate2d)
    {
        if (map.containsKey(coordinate2d))
            return map.get(coordinate2d);
        else
            return ComponentStructure2dHash.nullComponent;
    }

    @Override
    public void setT(Coordinate2d coordinate2d, double pheromone)
    {
        if (map.containsKey(coordinate2d))
            map.get(coordinate2d).setPheromone(pheromone);
        else
            map.put(coordinate2d, new Component2d(coordinate2d, 1.0, pheromone));
    }

    @Override
    public void setH(Coordinate2d coordinate2d, double heuristic)
    {
        if (map.containsKey(coordinate2d))
            map.get(coordinate2d).setHeuristic(heuristic);
        else
            map.put(coordinate2d, new Component2d(coordinate2d, heuristic, 0.0));
    }

    @Override
    public void setDistance(Coordinate2d coordinate2d, double distance)
    {
        if (map.containsKey(coordinate2d))
            map.get(coordinate2d).setDistance(distance);
        else
        {
            Component2d newComponent = new Component2d(coordinate2d, 1.0, 0.0);
            map.put(coordinate2d, newComponent);
            newComponent.setDistance(distance);
        }
    }



    public Iterator<Component> iterator()
    {
        return new Structure2dHashIterator();
    }



    class Structure2dHashIterator implements Iterator<Component>
    {
        protected Iterator<Map.Entry<Coordinate2d, Component2d>> setIterator;

        public Structure2dHashIterator()
        {
            setIterator = map.entrySet().iterator();
        }

        public boolean hasNext()
        {
            return setIterator.hasNext();
        }

        public Component next()
        {
            return setIterator.next().getValue();
        }
    }
}
