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
    public boolean isFullfilled()
    {
        count--;

        return count == 0;
    }
}
