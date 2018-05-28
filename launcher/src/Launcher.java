import general.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aldar on 24-Apr-18.
 */
public class Launcher
{
    public static void main(String[] args)
    {
        List<String> configurations = new ArrayList<String>();

        // AS (Bullnheimer)
        configurations.add("testConfig2 instance1 1234567 [path]  " +
                "--selector-standard --alpha 5.0 --beta 5.0 --candidate-yes --candidate-ratio 0.25 --ant-s " +
                "--local-update-no --standard --ant-num 30 --local-search-twohalf --evaporation-remains 0.75 " +
                "--ant-s-bounded-no --reinitialization-no --end");

        // RAS (Bullnheimer)
        configurations.add("testConfig2 instance1 1234567 [path]  " +
                "--selector-standard --alpha 5.0 --beta 5.0 --candidate-yes --candidate-ratio 0.25 --rank-based-ant-s " +
                "--local-update-no --standard --ant-num 30 --local-search-twohalf --evaporation-remains 0.75 " +
                "--ras-w 5 --ras-k 1.0 --ras-bounded-no --reinitialization-no --end");

        // MMAS
        configurations.add("testConfig2 instance1 1234567 [path]  " +
                "--selector-standard --alpha 1.0 --beta 2.0 --candidate-yes --candidate-ratio 0.25 --min-max-s " +
                "--local-update-no --standard --ant-num 30 --local-search-twohalf --evaporation-remains 0.8 " +
                "--min-max-s-p-best 0.005 --min-max-s-global-best-no --min-max-s-pts-no --reinitialization-no --end");

        // ACS
        configurations.add("testConfig2 instance1 1234567 [path]  " +
                "--selector-standard --alpha 1.0 --beta 2.0 --candidate-yes --candidate-ratio 0.25 --ant-colony-s " +
                "--local-update-yes --standard --ant-num 30 --local-search-twohalf --evaporation-remains 0.9 " +
                "--lupd-epsilon 0.1 --tau0 0.000004166 --reinitialization-no --end");

        // BWAS
        configurations.add("testConfig2 instance1 1234567 [path]  " +
                "--selector-standard --alpha 1.0 --beta 2.0 --candidate-yes --candidate-ratio 0.25 --best-worst-ant-s " +
                "--local-update-no --standard --ant-num 40 --local-search-twohalf --evaporation-remains 0.9 " +
                "--bwas-mutation-probability 0.01 --reinitialization-no --end");

        // Tuned
        configurations.add("testConfig2 instance1 1234567 [path]  " +
                "--selector-dorigo --alpha 3.04 --beta 4.52 --dorigo-probability 0.87 --candidate-yes --candidate-ratio  " +
                "0.65 --min-max-s --local-update-yes --cunning-ants --ant-num 10 --lupd-epsilon 0.62 --tau0 0.57 " +
                "--local-search-twohalf --evaporation-remains 0.35 --min-max-s-p-best 0.14 --min-max-s-global-best-no " +
                "--min-max-s-pts-no --reinitialization-no --destruction-probability 0.14 --end");

        int index = 0;
        for (String configurationString : configurations)
        {
            index++;
            System.out.println(index);
            System.out.println(configurationString);

            String[] arguments = configurationString.split("\\s+");

            String path = "irace/instances/";

            //String[] names = {"CMT13.vrp.txt"}; // TODO remove

            /*String[] names = {"CMT1.vrp.txt",
                    "CMT2.vrp.txt",
                    "CMT3.vrp.txt",
                    "CMT4.vrp.txt",
                    "CMT5.vrp.txt",
                    "CMT6.vrp.txt",
                    "CMT7.vrp.txt",
                    "CMT8.vrp.txt",
                    "CMT9.vrp.txt",
                    "CMT10.vrp.txt",
                    "CMT11.vrp.txt",
                    "CMT12.vrp.txt",
                    "CMT13.vrp.txt",
                    "CMT14.vrp.txt"};*/

            /*String[] names = {"tai75a.tai",
                    "tai75b.tai",
                    "tai75c.tai",
                    "tai75d.tai",
                    "tai100a.tai",
                    "tai100b.tai",
                    "tai100c.tai",
                    "tai100d.tai",
                    "tai150a.tai",
                    "tai150b.tai",
                    "tai150c.tai",
                    "tai150d.tai",
                    "tai385.tai"};*/

        /*String[] names = {"Golden_1.vrp.txt",
                "Golden_2.vrp.txt",
                "Golden_3.vrp.txt",
                "Golden_4.vrp.txt",
                "Golden_5.vrp.txt",
                "Golden_6.vrp.txt",
                "Golden_7.vrp.txt",
                "Golden_8.vrp.txt",
                "Golden_9.vrp.txt",
                "Golden_10.vrp.txt",
                "Golden_11.vrp.txt",
                "Golden_12.vrp.txt",
                "Golden_13.vrp.txt",
                "Golden_14.vrp.txt",
                "Golden_15.vrp.txt",
                "Golden_16.vrp.txt",
                "Golden_17.vrp.txt",
                "Golden_18.vrp.txt",
                "Golden_19.vrp.txt",
                "Golden_20.vrp.txt"};*/

        String[] names = {"X-n101-k25.vrp.txt",
                "X-n125-k30.vrp.txt",
                "X-n148-k46.vrp.txt",
                "X-n172-k51.vrp.txt",
                "X-n195-k51.vrp.txt",
                "X-n219-k73.vrp.txt",
                "X-n242-k48.vrp.txt",
                "X-n266-k58.vrp.txt",
                "X-n289-k60.vrp.txt",
                "X-n106-k14.vrp.txt",
                "X-n129-k18.vrp.txt",
                "X-n153-k22.vrp.txt",
                "X-n176-k26.vrp.txt",
                "X-n200-k36.vrp.txt",
                "X-n223-k34.vrp.txt",
                "X-n247-k50.vrp.txt",
                "X-n270-k35.vrp.txt",
                "X-n294-k50.vrp.txt",
                "X-n110-k13.vrp.txt",
                "X-n134-k13.vrp.txt",
                "X-n157-k13.vrp.txt",
                "X-n181-k23.vrp.txt",
                "X-n204-k19.vrp.txt",
                "X-n228-k23.vrp.txt",
                "X-n251-k28.vrp.txt",
                "X-n275-k28.vrp.txt",
                "X-n298-k31.vrp.txt",
                "X-n115-k10.vrp.txt",
                "X-n139-k10.vrp.txt",
                "X-n162-k11.vrp.txt",
                "X-n186-k15.vrp.txt",
                "X-n209-k16.vrp.txt",
                "X-n233-k16.vrp.txt",
                "X-n256-k16.vrp.txt",
                "X-n280-k17.vrp.txt",
                "X-n120-k6.vrp.txt",
                "X-n143-k7.vrp.txt",
                "X-n167-k10.vrp.txt",
                "X-n190-k8.vrp.txt",
                "X-n214-k11.vrp.txt",
                "X-n237-k14.vrp.txt",
                "X-n261-k13.vrp.txt",
                "X-n284-k15.vrp.txt"};

            for (String name : names)
            {
                arguments[3] = path + name;
                //System.out.println(arguments[3]);
                Main.main(arguments);
                //System.out.println();
            }
        }
    }
}
