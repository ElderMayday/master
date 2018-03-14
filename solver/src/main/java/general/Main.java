package general;

import solving.selectors.Selector;
import solving.selectors.SelectorDorigo;
import solving.selectors.SelectorManiezzo;
import solving.selectors.SelectorStandard;

import java.util.Random;

public class Main
{
    public static Random random = random = new Random(28);;


    public static void main(String[] args)
    {
        if (args.length < 4)
        {
            System.out.println("EXCEPTION: Not all arguments are specified:\njava -jar solver.jar [configuration id] " +
                    "[instance id] [seed] [instance path] {rest of the arguments}");

            return;
        }

        random = new Random(Integer.parseInt(args[2]));

        int selectorID = 1;
        Selector selector = null;
        double alpha = 1.0, beta = 1.0;
        double probabilityDorigo = 0.5;

        for (int i = 4; i < args.length;)
        {
            if (args[i].equals("--selector-standard"))
            {
                selectorID = 1;
                i++;
            }

            if (args[i].equals("--selector-dorigo"))
            {
                selectorID = 2;
                i++;
            }

            if (args[i].equals("--selector-maniezzo"))
            {
                selectorID = 3;
                i++;
            }
        }

        switch (selectorID)
        {
            case 1:
                selector = new SelectorStandard(alpha, beta);
                break;
            case 2:
                selector = new SelectorDorigo(alpha, beta, probabilityDorigo);
                break;
            case 3:
                selector = new SelectorManiezzo(alpha);
                break;
        }
    }
}
