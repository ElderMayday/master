import javax.annotation.processing.SupportedSourceVersion;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class GeneratorVrpMain
{

    public static void main(String[] args)
    {
        if ((args.length == 1) && (args[0].equals("--help")))
        {
            System.out.println("Flags:");
            System.out.println("--file [filename]");
            System.out.println("--open [yes/no]");
            System.out.println("--vehicles [vehicle_number]");
            System.out.println("--min_vehicle_capacity [min_vehicle_capacity]");
            System.out.println("--max_vehicle_capacity [max_vehicle_capacity]");
            System.out.println("--min_vehicle_length [min_vehicle_length]");
            System.out.println("--max_vehicle_length [max_vehicle_length]");
            System.out.println("--min_demand [min_demand]");
            System.out.println("--max_demand [max_demand]");
            System.out.println("--heuristic_information [uniform/inverse]");
            System.out.println("--min_heuristic_information [min_heuristics] (only for uniform heuristic_information)");
            System.out.println("--max_heuristic_information [max_heuristics] (only for uniform heuristic_information)");
            System.out.println("--probability_length_nonrestricted [probability_length_nonrestricted]");
            System.out.println("--customers_num [customers_num]");
            System.out.println("--area_size [area_size]");
        }
        else
        {
            Random random = new Random();
            String filename = "default.txt";
            boolean isOpenVrp = false;
            int vehicleNum = 10;
            double minVehicleCapacity = 10.0, maxVehicleCapacity = 100.0, rangeVehicleCapacity = 90.0;
            double minVehicleLength = 50.0, maxVehicleLength = 100.0, rangeVehicleLength = 50.0;
            double minDemand = 1.0, maxDemand = 5.0, rangeDemand = 40.0;
            double minHeuristicInformation = 1.0, maxHeuristicInformation = 1.5, rangeHeuristicInformation = 0.0;
            double probabilityLengthNonrestricted = 0.0;
            int customersNum = 10;
            double areaSize = 10.0;
            boolean isUniformHeuristicInformation = false;

            for (int index = 0; index < args.length; index += 2)
            {
                if (args[index].equals("--file"))
                    filename = args[index + 1];

                if (args[index].equals("--open") && args[index + 1].equals("yes"))
                    isOpenVrp = true;

                if (args[index].equals("--vehicles"))
                    vehicleNum = Integer.parseInt(args[index + 1]);

                if (args[index].equals("--min_vehicle_capacity"))
                    minVehicleCapacity = Double.parseDouble(args[index + 1]);

                if (args[index].equals("--max_vehicle_capacity"))
                    maxVehicleCapacity = Double.parseDouble(args[index + 1]);

                if (args[index].equals("--min_vehicle_length"))
                    minVehicleLength = Double.parseDouble(args[index + 1]);

                if (args[index].equals("--max_vehicle_length"))
                    maxVehicleLength = Double.parseDouble(args[index + 1]);

                if (args[index].equals("--heuristic_information"))
                    isUniformHeuristicInformation = args[index + 1].equals("uniform");

                if (args[index].equals("--min_heuristic_information"))
                    minHeuristicInformation = Double.parseDouble(args[index + 1]);

                if (args[index].equals("--max_heuristic_information"))
                    maxHeuristicInformation = Double.parseDouble(args[index + 1]);

                if (args[index].equals("--probability_length_nonrestricted"))
                    probabilityLengthNonrestricted = Double.parseDouble(args[index + 1]);

                if (args[index].equals("--customers_num"))
                    customersNum = Integer.parseInt(args[index + 1]);

                if (args[index].equals("--area_size"))
                    areaSize = Double.parseDouble(args[index + 1]);

                if (args[index].equals("--min_demand"))
                    minDemand = Double.parseDouble(args[index + 1]);

                if (args[index].equals("--max_demand"))
                    maxDemand = Double.parseDouble(args[index + 1]);
            }


            rangeVehicleCapacity = maxVehicleCapacity - minVehicleCapacity;
            rangeVehicleLength = maxVehicleLength - minVehicleLength;
            rangeDemand = maxDemand - minDemand;
            rangeHeuristicInformation = maxHeuristicInformation - minHeuristicInformation;


            System.out.println("filename=" + filename);
            System.out.println(isOpenVrp ? "openVRP=yes" : "openVRP=no");
            System.out.println("vehicle_number=" + vehicleNum);
            System.out.println("min_vehicle_capacity=" + minVehicleCapacity);
            System.out.println("max_vehicle_capacity=" + maxVehicleCapacity);
            System.out.println("min_vehicle_length=" + minVehicleLength);
            System.out.println("max_vehicle_length=" + maxVehicleLength);

            if (isUniformHeuristicInformation)
            {
                System.out.println("heuristic_information=uniform");
                System.out.println("min_heuristic_information=" + minHeuristicInformation);
                System.out.println("max_heuristic_information=" + maxHeuristicInformation);
            }
            else
            {
                System.out.println("heuristic_information=inverse");
            }

            System.out.println("probability_length_nonrestricted=" + probabilityLengthNonrestricted);
            System.out.println("customers_num=" + customersNum);
            System.out.println("area_size=" + areaSize);

            try
            {
                PrintWriter writer = new PrintWriter(filename, "UTF-8");

                // vehicle number -----------------------------------------------

                writer.write(Integer.toString(vehicleNum) + "\n");
                writer.write("---\n");

                // vehicles --------------------------------------------------

                for (int index = 0; index < vehicleNum; index++)
                {
                    writer.write(String.format("%1$,.1f", random.nextDouble() * rangeVehicleCapacity + minVehicleCapacity) + " ");

                    if (random.nextDouble() < probabilityLengthNonrestricted)
                        writer.write("-1.0");
                    else
                        writer.write(String.format("%1$,.1f", random.nextDouble() * rangeVehicleLength + minVehicleLength));

                    writer.write("\n");
                }

                writer.write("---\n");

                // customer number -------------------------------

                writer.write(Integer.toString(customersNum) + "\n");
                writer.write("---\n");


                // demands ------------------------------------

                for (int i = 0; i < customersNum; i++)
                    writer.write(String.format("%1$,.1f", random.nextDouble() * rangeDemand + minDemand) + "\n");
                writer.write("---\n");


                // distances -------------------------------------------------

                double[] x = new double[customersNum + 1];
                double[] y = new double[customersNum + 1];

                for (int index = 0; index < customersNum + 1; index++)
                {
                    x[index] = random.nextDouble() * areaSize;
                    y[index] = random.nextDouble() * areaSize;
                }

                for (int i = 0; i < customersNum + 1; i++)
                {
                    for (int j = 0; j < customersNum + 1; j++)
                    {
                        if (i == j)
                            writer.write("-1.0");
                        else
                        {
                            if (isOpenVrp && (j == 0))
                                writer.write("0.0");
                            else
                                writer.write(String.format("%1$,.1f", Math.sqrt(Math.pow(y[i] - y[j], 2.0)) + Math.pow(x[i] - x[j], 2.0)));

                        }

                        writer.write(" ");
                    }
                    writer.write("\n");
                }

                // heuristic information

                writer.write("---\n");

                if (isUniformHeuristicInformation)
                {
                    // uniform range heuristic information

                    for (int i = 0; i < customersNum + 1; i++)
                    {
                        for (int j = 0; j < customersNum + 1; j++)
                        {
                            writer.write(String.format("%1$,.1f", random.nextDouble() * rangeHeuristicInformation + minHeuristicInformation));
                            writer.write(" ");
                        }
                        writer.write("\n");
                    }
                }
                else
                {
                    // heuristic information is equal to distance^-1

                    for (int i = 0; i < customersNum + 1; i++)
                    {
                        for (int j = 0; j < customersNum + 1; j++)
                        {
                            if (i != j)
                                writer.write(String.format("%1$,.3f", 1.0 / (Math.sqrt(Math.pow(y[i] - y[j], 2.0)) + Math.pow(x[i] - x[j], 2.0))));
                            else
                                writer.write("0.0");

                            writer.write(" ");
                        }
                        writer.write("\n");
                    }
                }

                writer.close();

                System.out.println("");
                System.out.println("Writing complete");
            }
            catch (Exception e)
            {
                System.out.println("Exception");
            }




        }
    }
}
