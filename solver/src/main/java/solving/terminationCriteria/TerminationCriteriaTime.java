package solving.terminationCriteria;

/**
 * Created by Aldar on 12-Dec-17.
 */
public class TerminationCriteriaTime extends TerminationCriteria
{
    protected long tStart;
    protected long tLastReinitialization;
    protected long tEndExpected; // does not mean the precise execution instance

    protected long spanExecution;
    protected long spanBetweenReinitializations;

    protected boolean reinitializationEnabled;

    public TerminationCriteriaTime(long spanExecution, long spanBetweenReinitializations, boolean reinitializationEnabled)
    {
        this.spanExecution = spanExecution;
        this.spanBetweenReinitializations = spanBetweenReinitializations;
        this.reinitializationEnabled = reinitializationEnabled;
    }

    @Override
    public void initialize()
    {
        tStart = System.currentTimeMillis();
        tLastReinitialization = System.currentTimeMillis();
        tEndExpected = tStart + spanExecution;
    }

    /**
     * returns flag of reinitialization and recaptures last reinitialization moment
     * @return whether reinitialization should be done by the solver
     */
    @Override
    public boolean needReinitialize()
    {
        if (reinitializationEnabled)
        {
            long timeSpan = System.currentTimeMillis() - tLastReinitialization;

            if (timeSpan > spanBetweenReinitializations)
            {
                tLastReinitialization = System.currentTimeMillis();

                return true;
            } else
                return false;
        }
        else
            return false;
    }

    @Override
    public boolean isFulfilled()
    {
        return System.currentTimeMillis() - tStart > spanExecution;
    }



    public double mutationBwasFactor()
    {
        return (System.currentTimeMillis() - tLastReinitialization) / (tEndExpected - tLastReinitialization + 0.0001); // 0.0001 allows to prevent 0-division
    }
}
