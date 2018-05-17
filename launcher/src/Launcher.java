import general.Main;

/**
 * Created by Aldar on 24-Apr-18.
 */
public class Launcher
{
    public static void main(String[] args)
    {
        /*String configurationString = "testConfig2 instance1 1234567 [path]  " +
                "--selector-dorigo --alpha 0.2 --beta 3.56 --dorigo-probability 0.72 --candidate-no --elitist-ant-s " +
                "--local-update-yes --iterated-ants --ant-num 6 --lupd-epsilon 0.46 --tau0 0.19 --local-search-twohalf " +
                "--evaporation-remains 0.66 --eas-m-elite 90 --eas-bounded-yes --eas-k 4.02 --reinitialization-yes " +
                "--reinitialization-time 0.13 --iterated-criteria-best --destruction-probability 0.15 --end";*/

        String configurationString = "testConfig2 instance1 1234567 [path]  " +
                "--selector-standard --alpha 1.0 --beta 5.0 --candidate-yes --candidate-ratio 0.25 --ant-s " +
                "--local-update-no --standard --ant-num 30 --local-search-twohalf --evaporation-remains 0.75 " +
                "--ant-s-bounded-no --reinitialization-no --output solver\\output\\out --end";

        String[] arguments = configurationString.split("\\s+");

        String path = "irace\\test\\";

        //String[] names = {"CMT13.vrp.txt"}; // TODO remove

        String[] names = {"CMT1.vrp.txt",
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
                "CMT14.vrp.txt"};

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

        /*String[] names = {"X-n101-k25.vrp.txt",
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
                "X-n284-k15.vrp.txt"};*/

        for (String name : names)
        {
            arguments[3] = path + name;
            System.out.println(arguments[3]);
            Main.main(arguments);
            System.out.println();
        }
    }
}
