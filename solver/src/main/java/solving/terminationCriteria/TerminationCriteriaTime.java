package solving.terminationCriteria;

/**
 * Created by Aldar on 12-Dec-17.
 */
public class TerminationCriteriaTime extends TerminationCriteria
{
    protected long tStart;
    protected long tLastReinitialization;

    protected long timeExecution;
    protected long timeBetweenReinitializations;


    public TerminationCriteriaTime(long timeExecution, long timeBetweenReinitializations)
    {
        this.timeExecution = timeExecution;
        this.timeBetweenReinitializations = timeBetweenReinitializations;
    }

    @Override
    public void initialize()
    {
        tStart = System.currentTimeMillis();
        tLastReinitialization = System.currentTimeMillis();
    }

    /**
     * returns flag of reinitialization and recaptures last reinitialization moment
     * @return whether reinitialization should be done by the solver
     */
    @Override
    public boolean needReinitialize()
    {
        long timeSpan = System.currentTimeMillis() - tLastReinitialization;

        if (timeSpan > timeBetweenReinitializations)
        {
            tLastReinitialization = System.currentTimeMillis();

            return true;
        }
        else
            return false;
    }

    @Override
    public boolean isFulfilled()
    {
        return System.currentTimeMillis() - tStart > timeExecution;
    }

    @Override
    public long timeAfterLastReinitialization()
    {
        return System.currentTimeMillis() - tLastReinitialization;
    }
}
