package general;

import solving.selectors.Selector;
import solving.selectors.SelectorDorigo;
import solving.selectors.SelectorManiezzo;
import solving.selectors.SelectorStandard;

import java.util.Random;


// Intellij Idea may loose the source, test and output folder assignment
// If compilation does not work, check "Project Structure"

// Java 1.8.0_101
// org.mockito:mockito-core 2.8.9 for white-box testing
// org.json:json:20131018 for json problem file reading



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
