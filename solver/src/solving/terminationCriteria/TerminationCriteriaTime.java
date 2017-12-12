package solving.terminationCriteria;

/**
 * Created by Aldar on 12-Dec-17.
 */
public class TerminationCriteriaTime extends TerminationCriteria
{
    protected long tStart;
    protected long tLastReinitialization;

    protected long millisecondsForTermination;
    protected long millisecondsForReinitialization;


    public TerminationCriteriaTime(long millisecondsForTermination, long millisecondsForReinitialization)
    {
        this.millisecondsForTermination = millisecondsForTermination;
        this.millisecondsForReinitialization = millisecondsForReinitialization;
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

        if (timeSpan > millisecondsForReinitialization)
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
        return System.currentTimeMillis() - tStart > millisecondsForTermination;
    }

    @Override
    public long millisecondsAfterLastReinitialization()
    {
        return System.currentTimeMillis() - tLastReinitialization;
    }
}
