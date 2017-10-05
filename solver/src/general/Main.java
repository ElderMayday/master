package general;

import problem.Component2d;
import problem.ComponentStructure2d;
import problem.ComponentStructure2dStandard;

public class Main
{

    public static void main(String[] args)
    {

        ComponentStructure2d structure2d = new ComponentStructure2dStandard(1, 3);

        structure2d.Set(0, 0, new Component2d(0, 0, 1.0, 1.0));
        structure2d.Set(0, 1, new Component2d(0, 1, 1.0, 2.0));
        structure2d.Set(0, 2, new Component2d(0, 2, 1.0, 3.0));

        System.out.println("Finished");
    }
}
