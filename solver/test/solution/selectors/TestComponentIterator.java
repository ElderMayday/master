package solution.selectors;

import org.junit.Test;
import problem.Component;
import problem.Component2d;
import problem.ComponentStructure.ComponentStructure2d;
import problem.ComponentStructure.ComponentStructure2dHash;
import problem.ComponentStructure.ComponentStructure2dStandard;

import static org.junit.Assert.*;

/**
 * Created by Aldar on 09-Oct-17.
 * Tests Iterators over the Components of the ComponentStructures
 */
public class TestComponentIterator
{
    @Test
    public void testComponentStructure2dStandartIterator()
    {
        ComponentStructure2d structure2d = new ComponentStructure2dStandard(2, 2);

        structure2d.SetT(0, 0, 4.0);
        structure2d.SetH(0, 0, 3.0);

        structure2d.SetH(0, 1, 2.0);

        structure2d.SetT(1, 0, 1.0);

        boolean has00 = false, has01 = false, has10 = false, has11 = false;    // flag whether the matrix component was returned

        for (Component component : structure2d)
        {
            Component2d component2d = (Component2d) component;

            if ((component2d.getRow() == 0) && (component2d.getColumn() == 0))
                has00 = true;

            if ((component2d.getRow() == 0) && (component2d.getColumn() == 1))
                has01 = true;

            if ((component2d.getRow() == 1) && (component2d.getColumn() == 0))
                has10 = true;

            if ((component2d.getRow() == 1) && (component2d.getColumn() == 1))
                has11 = true;
        }

        // asserts that every matrix component should have been visited (since it is a dense structure)

        assertEquals(true, has00 && has01 && has10 && has11);
    }



    @Test
    public void testComponentStructure2dStandartHash()
    {
        ComponentStructure2d structure2d = new ComponentStructure2dHash(2, 2);

        structure2d.SetT(0, 0, 4.0);
        structure2d.SetH(0, 0, 3.0);

        structure2d.SetH(0, 1, 2.0);

        structure2d.SetT(1, 0, 1.0);

        boolean has00 = false, has01 = false, has10 = false, has11 = false;    // flag whether the matrix component was returned

        for (Component component : structure2d)
        {
            Component2d component2d = (Component2d) component;

            if ((component2d.getRow() == 0) && (component2d.getColumn() == 0))
                has00 = true;

            if ((component2d.getRow() == 0) && (component2d.getColumn() == 1))
                has01 = true;

            if ((component2d.getRow() == 1) && (component2d.getColumn() == 0))
                has10 = true;

            if ((component2d.getRow() == 1) && (component2d.getColumn() == 1))
                has11 = true;
        }

        // (1;1) should have not been iterated since it was not initialized in this sparse structure

        assertEquals(true, has00 && has01 && has10 && !has11);
    }
}
