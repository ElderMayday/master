package solving.terminationCriteria;

/**
 * Created by Aldar on 16-Oct-17.
 */
public class TerminationCriteriaCounter extends TerminationCriteria
{
    protected int count;

    public TerminationCriteriaCounter(int count)
    {
        this.count = count > 0 ? count : 0;
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public boolean needReinitialize()
    {
        return false;
    }

    @Override
    public boolean isFulfilled()
    {
        count--;

        return count == 0;
    }




    /**
     * Does not return actual factor, and therefore cannot be used for BWAS
     * @return
     */
    public double mutationBwasFactor()
    {
        return 1.0;
    }

    @Override
    public double elapsed()
    {
        return 0;
    }
}
