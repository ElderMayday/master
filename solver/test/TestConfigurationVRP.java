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

        int a = 1;
    }
}
