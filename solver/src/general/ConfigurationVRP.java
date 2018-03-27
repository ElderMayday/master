package general;

/**
 * Run configuration designed for VRP problems
 * Created by Aldar on 15-Mar-18.
 */
public class ConfigurationVRP extends Configuration
{
    public ConfigurationVRP(String[] args)
    {
        if (args.length < 4)
            throw new IllegalArgumentException("Not all arguments are specified:\njava -jar solver.jar [configuration id] " +
                    "[instance id] [seed] [instance path] {rest of the arguments}");

        seed = Integer.parseInt(args[2]);

        for (int i = 4; i < args.length;)
        {
            if (args[i].equals("--selector-standard"))
            {
                if (this.selector != -1)
                    throw new IllegalArgumentException("Redefinition of selecor type is not allowed");

                this.selector = 1;
                i++;
                continue;
            }

            if (args[i].equals("--selector-dorigo"))
            {
                if (this.selector != -1)
                    throw new IllegalArgumentException("Redefinition of selecor type is not allowed");

                this.selector = 2;
                i++;
                continue;
            }

            if (args[i].equals("--selector-maniezzo"))
            {
                if (this.selector != -1)
                    throw new IllegalArgumentException("Redefinition of selecor type is not allowed");

                this.selector = 3;
                i++;
                continue;
            }

            if (args[i].equals("--alpha"))
            {
                this.alpha = Double.parseDouble(args[i + 1]);
                i += 2;

                if (!((selector == 1) || (selector == 2) || (selector == 3)))
                    throw new IllegalArgumentException("Selector parameters cannot be defined before the selector type");

                continue;
            }

            if (args[i].equals("--beta"))
            {
                this.beta = Double.parseDouble(args[i + 1]);
                i += 2;

                if (!((selector == 1) || (selector == 2)))
                    throw new IllegalArgumentException("Selector parameters cannot be defined before the selector type");

                continue;
            }

            if (args[i].equals("--dorigo-probability"))
            {
                this.probabilityDorigo = Double.parseDouble(args[i + 1]);
                i += 2;

                if (selector != 2)
                    throw new IllegalArgumentException("Selector parameters cannot be defined before the selector type");

                continue;
            }

            if (args[i].equals("--candidate-yes"))
            {
                if (this.candidate != -1)
                    throw new IllegalArgumentException("Redefinition of candidate flag is not allowed");

                this.candidate = 1;
                i++;
                continue;
            }

            if (args[i].equals("--candidate-no"))
            {
                if (this.candidate != -1)
                    throw new IllegalArgumentException("Redefinition of candidate flag is not allowed");

                this.candidate = 0;
                i++;
                continue;
            }

            if (args[i].equals("--candidate-num"))
            {
                this.candidateNum = Integer.parseInt(args[i + 1]);
                i += 2;

                if (candidate != 1)
                    throw new IllegalArgumentException("Candidate number cannot be defined without candidate flag");

                continue;
            }

            if (args[i].equals("--ant-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 1;
                i++;
                continue;
            }

            if (args[i].equals("--ant-colony-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 2;
                i++;
                continue;
            }

            if (args[i].equals("--min-max-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 3;
                i++;
                continue;
            }

            if (args[i].equals("--rank-based-ant-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 4;
                i++;
                continue;
            }

            if (args[i].equals("--elitist-ant-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 5;
                i++;
                continue;
            }

            if (args[i].equals("--best-worst-ant-s"))
            {
                if (this.globalUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of global update is not allowed");

                this.globalUpdate = 6;
                i++;
                continue;
            }

            if (args[i].equals("--local-update-yes"))
            {
                if (this.localUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of local update is not allowed");

                if (!((selector == 1) || (selector == 2)))
                    throw new IllegalArgumentException("Local update must be only specified in case of standard or dorigo selector");

                this.localUpdate = 1;
                i++;
                continue;
            }

            if (args[i].equals("--local-update-no"))
            {
                if (this.localUpdate != -1)
                    throw new IllegalArgumentException("Redefinition of local update is not allowed");

                if (!((selector == 1) || (selector == 2)))
                    throw new IllegalArgumentException("Local update must be only specified in case of standard or dorigo selector");

                this.localUpdate = 0;
                i++;
                continue;
            }

            if (args[i].equals("--standard"))
            {
                if (this.iteratedGreedy != -1)
                    throw new IllegalArgumentException("Redefinition of iterated greedy mode is not allowed");

                this.iteratedGreedy = 1;
                i++;
                continue;
            }

            if (args[i].equals("--external-memory"))
            {
                if (this.iteratedGreedy != -1)
                    throw new IllegalArgumentException("Redefinition of iterated greedy mode is not allowed");

                this.iteratedGreedy = 2;
                i++;
                continue;
            }

            if (args[i].equals("--iterated-ants"))
            {
                if (this.iteratedGreedy != -1)
                    throw new IllegalArgumentException("Redefinition of iterated greedy mode is not allowed");

                this.iteratedGreedy = 3;
                i++;
                continue;
            }

            if (args[i].equals("--cunning-ants"))
            {
                if (this.iteratedGreedy != -1)
                    throw new IllegalArgumentException("Redefinition of iterated greedy mode is not allowed");

                this.iteratedGreedy = 4;
                i++;
                continue;
            }

            if (args[i].equals("--ant-num"))
            {
                this.antNum = Integer.parseInt(args[i + 1]);
                i += 2;
                continue;
            }

            throw new IllegalArgumentException("Unknown parameter [" + args[i] + "] has been found");
        }
    }



    /**
     * Checks the consistency of the read configuration and throws an exception if found
     */
    protected void checkConfiguration()
    {
        super.checkConfiguration();
    }
}
