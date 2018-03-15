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
        ConfigurationVRP configuration = new ConfigurationVRP(args);

        random = new Random(configuration.seed);

        Selector selector = null;

        switch (configuration.selector)
        {
            case 1:
                selector = new SelectorStandard(configuration.alpha, configuration.beta);
                break;
            case 2:
                selector = new SelectorDorigo(configuration.alpha, configuration.beta, configuration.probabilityDorigo);
                break;
            case 3:
                selector = new SelectorManiezzo(configuration.alpha);
                break;
        }
    }

}
