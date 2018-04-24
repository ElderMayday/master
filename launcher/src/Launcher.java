import general.Main;

/**
 * Created by Aldar on 24-Apr-18.
 */
public class Launcher
{
    public static void main(String[] args)
    {
        String configurationString = "testConfig2 instance1 1234567 [path]  " +
                "--selector-standard --alpha 3.83 --beta 1.38 --candidate-no --ant-s --local-update-yes --external-memory " +
                "--ant-num 49 --lupd-epsilon 0.62 --tau0 0.52 --local-search-twohalf --evaporation-remains 0.27 " +
                "--ant-s-bounded-no --reinitialization-no --top-k 1 --memory-size 30 --tournament-selector-size 8 " +
                "--destruction-probability 0.23 --end\n";

        String[] arguments = configurationString.split("\\s+");

        String path = "D:\\master\\irace\\instances\\";

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
            System.out.println(arguments[3]);
            Main.main(arguments);
            System.out.println();
        }
    }
}
