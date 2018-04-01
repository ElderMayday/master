import general.ConfigurationVRP;
import org.junit.Test;

import static org.junit.Assert.*;



/**
 * Created by Aldar on 30-Mar-18.
 */
public class TestConfigurationVRP
{
    @Test
    public void testParameters1()
    {
        String configurationString = "testConfig2 instance1 1234567 D:/master/irace-test/instances/1.txt --selector-standard --alpha 4.34 --beta 4.63 --candidate-yes --candidate-ratio 0.82 --rank-based-ant-s --local-update-yes --cunning-ants --ant-num 5 --runtime 1000 --lupd-epsilon 0.82 --tau0 0.84 --local-search-none --evaporation-remains 0.62 --ras-w 26 --ras-bounded-no --ras-k 1.78 --reinitialization-yes --reinitialization-time 797 --destruction-probability 0.01";
        String[] args = configurationString.split(" ");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 1);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.beta, 4.63, 0.01);
        assertEquals(conf.candidate, 1);
        assertEquals(conf.candidateRatio, 0.82, 0.01);
        assertEquals(conf.globalUpdate, 4);
        assertEquals(conf.localUpdate, 1);
        assertEquals(conf.iteratedGreedy, 4);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.lupdEpsilon, 0.82, 0.01);
        assertEquals(conf.lupdTau0, 0.84, 0.01);
        assertEquals(conf.localSearch, 1);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.rasW, 26);
        assertEquals(conf.rasBounded, 2);
        assertEquals(conf.rasK, 1.78, 0.01);
        assertEquals(conf.reinitialization, 1);
        assertEquals(conf.reinitializationTime, 797);
        assertEquals(conf.destructionProbability, 0.01, 0.01);
    }

    @Test
    public void testParameters2()
    {
        String configurationString = "testConfig2\n" +
                "instance1\n" +
                "1234567\n" +
                "D:/master/irace-test/instances/1.txt\n" +
                "--selector-dorigo\n" +
                "--alpha\n" +
                "4.34\n" +
                "--beta\n" +
                "4.63\n" +
                "--dorigo-probability\n" +
                "0.5\n" +
                "--candidate-no\n" +
                "--ant-s\n" +
                "--local-update-yes\n" +
                "--external-memory\n" +
                "--ant-num\n" +
                "5\n" +
                "--runtime\n" +
                "1000\n" +
                "--lupd-epsilon\n" +
                "0.82\n" +
                "--tau0\n" +
                "0.84\n" +
                "--local-search-none\n" +
                "--evaporation-remains\n" +
                "0.62\n" +
                "--ant-s-bounded-yes\n" +
                "--ant-s-k\n" +
                "0.5\n" +
                "--reinitialization-no\n" +
                "--top-k\n" +
                "3\n" +
                "--tournament-selector-size\n" +
                "4\n" +
                "--memory-size\n" +
                "10\n" +
                "--destruction-probability\n" +
                "0.01";

        String[] args = configurationString.split("\n");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 2);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.beta, 4.63, 0.01);
        assertEquals(conf.candidate, 2);
        assertEquals(conf.globalUpdate, 1);
        assertEquals(conf.localUpdate, 1);
        assertEquals(conf.iteratedGreedy, 2);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.lupdEpsilon, 0.82, 0.01);
        assertEquals(conf.lupdTau0, 0.84, 0.01);
        assertEquals(conf.localSearch, 1);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.antSystemK, 0.5, 0.01);
        assertEquals(conf.antSystemIsBounded, 1);
        assertEquals(conf.reinitialization, 2);
        assertEquals(conf.topK, 3);
        assertEquals(conf.memorySize, 10);
        assertEquals(conf.tournamentSelectorSize, 4);
        assertEquals(conf.destructionProbability, 0.01, 0.01);
    }

    @Test
    public void testParameters3()
    {
        String configurationString = "testConfig2\n" +
                "instance1\n" +
                "1234567\n" +
                "D:/master/irace-test/instances/1.txt\n" +
                "--selector-dorigo\n" +
                "--alpha\n" +
                "4.34\n" +
                "--beta\n" +
                "4.63\n" +
                "--dorigo-probability\n" +
                "0.5\n" +
                "--candidate-no\n" +
                "--ant-s\n" +
                "--local-update-yes\n" +
                "--iterated-ants\n" +
                "--ant-num\n" +
                "5\n" +
                "--runtime\n" +
                "1000\n" +
                "--lupd-epsilon\n" +
                "0.82\n" +
                "--tau0\n" +
                "0.84\n" +
                "--local-search-ils-twohalf\n" +
                "--ils-iterations\n" +
                "10\n" +
                "--evaporation-remains\n" +
                "0.62\n" +
                "--ant-s-bounded-no\n" +
                "--reinitialization-no\n" +
                "--iterated-criteria-best\n" +
                "--destruction-probability\n" +
                "0.01";

        String[] args = configurationString.split("\n");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 2);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.beta, 4.63, 0.01);
        assertEquals(conf.candidate, 2);
        assertEquals(conf.globalUpdate, 1);
        assertEquals(conf.localUpdate, 1);
        assertEquals(conf.iteratedGreedy, 3);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.lupdEpsilon, 0.82, 0.01);
        assertEquals(conf.lupdTau0, 0.84, 0.01);
        assertEquals(conf.localSearch, 2);
        assertEquals(conf.ilsIterations, 10);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.reinitialization, 2);
        assertEquals(conf.antSystemIsBounded, 2);
        assertEquals(conf.iteratedCriteria, 1);
        assertEquals(conf.destructionProbability, 0.01, 0.01);
    }

    @Test
    public void testParameters4()
    {
        String configurationString = "testConfig2\n" +
                "instance1\n" +
                "1234567\n" +
                "D:/master/irace-test/instances/1.txt\n" +
                "--selector-maniezzo\n" +
                "--alpha\n" +
                "4.34\n" +
                "--candidate-no\n" +
                "--ant-colony-s\n" +
                "--local-update-no\n" +
                "--iterated-ants\n" +
                "--ant-num\n" +
                "5\n" +
                "--runtime\n" +
                "1000\n" +
                "--local-search-twohalf\n" +
                "--evaporation-remains\n" +
                "0.62\n" +
                "--reinitialization-no\n" +
                "--iterated-criteria-best\n" +
                "--destruction-probability\n" +
                "0.01";

        String[] args = configurationString.split("\n");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 3);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.candidate, 2);
        assertEquals(conf.globalUpdate, 2);
        assertEquals(conf.localUpdate, 2);
        assertEquals(conf.iteratedGreedy, 3);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.localSearch, 3);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.reinitialization, 2);
        assertEquals(conf.iteratedCriteria, 1);
        assertEquals(conf.destructionProbability, 0.01, 0.01);
    }


    @Test
    public void testParameters5()
    {
        String configurationString = "testConfig2\n" +
                "instance1\n" +
                "1234567\n" +
                "D:/master/irace-test/instances/1.txt\n" +
                "--selector-maniezzo\n" +
                "--alpha\n" +
                "4.34\n" +
                "--candidate-no\n" +
                "--min-max-s\n" +
                "--local-update-no\n" +
                "--iterated-ants\n" +
                "--ant-num\n" +
                "5\n" +
                "--runtime\n" +
                "1000\n" +
                "--local-search-twohalf\n" +
                "--evaporation-remains\n" +
                "0.62\n" +
                "--min-max-s-p-best\n" +
                "0.5\n" +
                "--min-max-s-global-best-yes\n" +
                "--min-max-s-global-iterations\n" +
                "10\n" +
                "--min-max-s-pts-yes\n" +
                "--min-max-s-pts-iterations\n" +
                "5\n" +
                "--pts-lambda\n" +
                "0.4\n" +
                "--pts-ratio\n" +
                "0.5\n" +
                "--pts-delta\n" +
                "0.6\n" +
                "--reinitialization-no\n" +
                "--iterated-criteria-best\n" +
                "--destruction-probability\n" +
                "0.01";

        String[] args = configurationString.split("\n");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 3);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.candidate, 2);
        assertEquals(conf.globalUpdate, 3);
        assertEquals(conf.localUpdate, 2);
        assertEquals(conf.iteratedGreedy, 3);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.localSearch, 3);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.minMaxSystemPbest, 0.5, 0.01);
        assertEquals(conf.minMaxSystemGlobalBest, 1);
        assertEquals(conf.minMaxSystemGlobalIterations, 10);
        assertEquals(conf.minMaxSystemPts, 1);
        assertEquals(conf.minMaxSystemPtsIterations, 5);
        assertEquals(conf.reinitialization, 2);
        assertEquals(conf.iteratedCriteria, 1);
        assertEquals(conf.destructionProbability, 0.01, 0.01);
    }

    @Test
    public void testParameters6()
    {
        String configurationString = "testConfig2\n" +
                "instance1\n" +
                "1234567\n" +
                "D:/master/irace-test/instances/1.txt\n" +
                "--selector-maniezzo\n" +
                "--alpha\n" +
                "4.34\n" +
                "--candidate-no\n" +
                "--min-max-s\n" +
                "--local-update-no\n" +
                "--iterated-ants\n" +
                "--ant-num\n" +
                "5\n" +
                "--runtime\n" +
                "1000\n" +
                "--local-search-twohalf\n" +
                "--evaporation-remains\n" +
                "0.62\n" +
                "--min-max-s-p-best\n" +
                "0.5\n" +
                "--min-max-s-global-best-no\n" +
                "--min-max-s-pts-no\n" +
                "--reinitialization-no\n" +
                "--iterated-criteria-best\n" +
                "--destruction-probability\n" +
                "0.01";

        String[] args = configurationString.split("\n");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 3);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.candidate, 2);
        assertEquals(conf.globalUpdate, 3);
        assertEquals(conf.localUpdate, 2);
        assertEquals(conf.iteratedGreedy, 3);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.localSearch, 3);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.minMaxSystemPbest, 0.5, 0.01);
        assertEquals(conf.minMaxSystemGlobalBest, 2);
        assertEquals(conf.minMaxSystemPts, 2);
        assertEquals(conf.reinitialization, 2);
        assertEquals(conf.iteratedCriteria, 1);
        assertEquals(conf.destructionProbability, 0.01, 0.01);
    }

    @Test
    public void testParameters7()
    {
        String configurationString = "testConfig2 instance1 1234567 D:/master/irace-test/instances/1.txt --selector-standard --alpha 4.34 --beta 4.63 --candidate-yes --candidate-ratio 0.82 --rank-based-ant-s --local-update-yes --cunning-ants --ant-num 5 --runtime 1000 --lupd-epsilon 0.82 --tau0 0.84 --local-search-none --evaporation-remains 0.62 --ras-w 26 --ras-bounded-yes --ras-k 1.78 --reinitialization-yes --reinitialization-time 797 --destruction-probability 0.01";
        String[] args = configurationString.split(" ");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 1);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.beta, 4.63, 0.01);
        assertEquals(conf.candidate, 1);
        assertEquals(conf.candidateRatio, 0.82, 0.01);
        assertEquals(conf.globalUpdate, 4);
        assertEquals(conf.localUpdate, 1);
        assertEquals(conf.iteratedGreedy, 4);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.lupdEpsilon, 0.82, 0.01);
        assertEquals(conf.lupdTau0, 0.84, 0.01);
        assertEquals(conf.localSearch, 1);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.rasW, 26);
        assertEquals(conf.rasBounded, 1);
        assertEquals(conf.rasK, 1.78, 0.01);
        assertEquals(conf.reinitialization, 1);
        assertEquals(conf.reinitializationTime, 797);
        assertEquals(conf.destructionProbability, 0.01, 0.01);
    }

    @Test
    public void testParameters8()
    {
        String configurationString = "testConfig2\n" +
                "instance1\n" +
                "1234567\n" +
                "D:/master/irace-test/instances/1.txt\n" +
                "--selector-dorigo\n" +
                "--alpha\n" +
                "4.34\n" +
                "--beta\n" +
                "4.63\n" +
                "--dorigo-probability\n" +
                "0.5\n" +
                "--candidate-no\n" +
                "--ant-s\n" +
                "--local-update-yes\n" +
                "--iterated-ants\n" +
                "--ant-num\n" +
                "5\n" +
                "--runtime\n" +
                "1000\n" +
                "--lupd-epsilon\n" +
                "0.82\n" +
                "--tau0\n" +
                "0.84\n" +
                "--local-search-ils-twohalf\n" +
                "--ils-iterations\n" +
                "10\n" +
                "--evaporation-remains\n" +
                "0.62\n" +
                "--ant-s-bounded-no\n" +
                "--reinitialization-no\n" +
                "--iterated-criteria-probabilistic\n" +
                "--probabilistic-best\n" +
                "0.9\n" +
                "--destruction-probability\n" +
                "0.01";

        String[] args = configurationString.split("\n");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 2);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.beta, 4.63, 0.01);
        assertEquals(conf.candidate, 2);
        assertEquals(conf.globalUpdate, 1);
        assertEquals(conf.localUpdate, 1);
        assertEquals(conf.iteratedGreedy, 3);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.lupdEpsilon, 0.82, 0.01);
        assertEquals(conf.lupdTau0, 0.84, 0.01);
        assertEquals(conf.localSearch, 2);
        assertEquals(conf.ilsIterations, 10);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.reinitialization, 2);
        assertEquals(conf.antSystemIsBounded, 2);
        assertEquals(conf.iteratedCriteria, 2);
        assertEquals(conf.probabilisticBest, 0.9, 0.01);
        assertEquals(conf.destructionProbability, 0.01, 0.01);
    }

    @Test
    public void testParameters9()
    {
        String configurationString = "testConfig2\n" +
                "instance1\n" +
                "1234567\n" +
                "D:/master/irace-test/instances/1.txt\n" +
                "--selector-dorigo\n" +
                "--alpha\n" +
                "4.34\n" +
                "--beta\n" +
                "4.63\n" +
                "--dorigo-probability\n" +
                "0.5\n" +
                "--candidate-no\n" +
                "--ant-s\n" +
                "--local-update-yes\n" +
                "--standard\n" +
                "--ant-num\n" +
                "5\n" +
                "--runtime\n" +
                "1000\n" +
                "--lupd-epsilon\n" +
                "0.82\n" +
                "--tau0\n" +
                "0.84\n" +
                "--local-search-ils-twohalf\n" +
                "--ils-iterations\n" +
                "10\n" +
                "--evaporation-remains\n" +
                "0.62\n" +
                "--ant-s-bounded-no\n" +
                "--reinitialization-no\n";

        String[] args = configurationString.split("\n");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 2);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.beta, 4.63, 0.01);
        assertEquals(conf.candidate, 2);
        assertEquals(conf.globalUpdate, 1);
        assertEquals(conf.localUpdate, 1);
        assertEquals(conf.iteratedGreedy, 1);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.lupdEpsilon, 0.82, 0.01);
        assertEquals(conf.lupdTau0, 0.84, 0.01);
        assertEquals(conf.localSearch, 2);
        assertEquals(conf.ilsIterations, 10);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.reinitialization, 2);
        assertEquals(conf.antSystemIsBounded, 2);
    }


    @Test
    public void testParameters10()
    {
        String configurationString = "testConfig2\n" +
                "instance1\n" +
                "1234567\n" +
                "D:/master/irace-test/instances/1.txt\n" +
                "--selector-dorigo\n" +
                "--alpha\n" +
                "4.34\n" +
                "--beta\n" +
                "4.63\n" +
                "--standard\n" +
                "--dorigo-probability\n" +
                "0.5\n" +
                "--candidate-no\n" +
                "--local-update-yes\n" +
                "--elitist-ant-s\n" +
                "--ant-num\n" +
                "5\n" +
                "--runtime\n" +
                "1000\n" +
                "--lupd-epsilon\n" +
                "0.82\n" +
                "--tau0\n" +
                "0.84\n" +
                "--local-search-ils-twohalf\n" +
                "--ils-iterations\n" +
                "10\n" +
                "--eas-m-elite\n" +
                "33\n" +
                "--eas-bounded-yes\n" +
                "--eas-k\n" +
                "5.0\n" +
                "--evaporation-remains\n" +
                "0.62\n" +
                "--reinitialization-no\n";

        String[] args = configurationString.split("\n");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 2);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.beta, 4.63, 0.01);
        assertEquals(conf.candidate, 2);
        assertEquals(conf.globalUpdate, 5);
        assertEquals(conf.localUpdate, 1);
        assertEquals(conf.iteratedGreedy, 1);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.lupdEpsilon, 0.82, 0.01);
        assertEquals(conf.lupdTau0, 0.84, 0.01);
        assertEquals(conf.localSearch, 2);
        assertEquals(conf.ilsIterations, 10);
        assertEquals(conf.easM, 33);
        assertEquals(conf.easIsBounded, 1);
        assertEquals(conf.easK, 5.0, 0.01);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.reinitialization, 2);
    }

    @Test
    public void testParameters11()
    {
        String configurationString = "testConfig2\n" +
                "instance1\n" +
                "1234567\n" +
                "D:/master/irace-test/instances/1.txt\n" +
                "--selector-dorigo\n" +
                "--alpha\n" +
                "4.34\n" +
                "--beta\n" +
                "4.63\n" +
                "--standard\n" +
                "--dorigo-probability\n" +
                "0.5\n" +
                "--candidate-no\n" +
                "--local-update-yes\n" +
                "--elitist-ant-s\n" +
                "--ant-num\n" +
                "5\n" +
                "--runtime\n" +
                "1000\n" +
                "--lupd-epsilon\n" +
                "0.82\n" +
                "--tau0\n" +
                "0.84\n" +
                "--local-search-ils-twohalf\n" +
                "--ils-iterations\n" +
                "10\n" +
                "--eas-m-elite\n" +
                "33\n" +
                "--eas-bounded-no\n" +
                "--eas-k\n" +
                "5.0\n" +
                "--evaporation-remains\n" +
                "0.62\n" +
                "--reinitialization-no\n";

        String[] args = configurationString.split("\n");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 2);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.beta, 4.63, 0.01);
        assertEquals(conf.candidate, 2);
        assertEquals(conf.globalUpdate, 5);
        assertEquals(conf.localUpdate, 1);
        assertEquals(conf.iteratedGreedy, 1);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.lupdEpsilon, 0.82, 0.01);
        assertEquals(conf.lupdTau0, 0.84, 0.01);
        assertEquals(conf.localSearch, 2);
        assertEquals(conf.ilsIterations, 10);
        assertEquals(conf.easM, 33);
        assertEquals(conf.easIsBounded, 2);
        assertEquals(conf.easK, 5.0, 0.01);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.reinitialization, 2);
    }


    @Test
    public void testParameters12()
    {
        String configurationString = "testConfig2\n" +
                "instance1\n" +
                "1234567\n" +
                "D:/master/irace-test/instances/1.txt\n" +
                "--selector-dorigo\n" +
                "--alpha\n" +
                "4.34\n" +
                "--beta\n" +
                "4.63\n" +
                "--standard\n" +
                "--dorigo-probability\n" +
                "0.5\n" +
                "--candidate-no\n" +
                "--local-update-yes\n" +
                "--best-worst-ant-s\n" +
                "--ant-num\n" +
                "5\n" +
                "--runtime\n" +
                "1000\n" +
                "--lupd-epsilon\n" +
                "0.82\n" +
                "--tau0\n" +
                "0.84\n" +
                "--local-search-ils-twohalf\n" +
                "--ils-iterations\n" +
                "10\n" +
                "--bwas-mutation-probability\n" +
                "0.2\n" +
                "--evaporation-remains\n" +
                "0.62\n" +
                "--reinitialization-no\n";

        String[] args = configurationString.split("\n");

        ConfigurationVRP conf = new ConfigurationVRP(args);

        assertEquals(conf.selector, 2);
        assertEquals(conf.alpha, 4.34, 0.01);
        assertEquals(conf.beta, 4.63, 0.01);
        assertEquals(conf.candidate, 2);
        assertEquals(conf.globalUpdate, 6);
        assertEquals(conf.localUpdate, 1);
        assertEquals(conf.iteratedGreedy, 1);
        assertEquals(conf.antNum, 5);
        assertEquals(conf.runtime, 1000);
        assertEquals(conf.lupdEpsilon, 0.82, 0.01);
        assertEquals(conf.lupdTau0, 0.84, 0.01);
        assertEquals(conf.localSearch, 2);
        assertEquals(conf.ilsIterations, 10);
        assertEquals(conf.bwasProbability, 0.2, 0.01);
        assertEquals(conf.evaporationRemains, 0.62, 0.01);
        assertEquals(conf.reinitialization, 2);
    }

}
