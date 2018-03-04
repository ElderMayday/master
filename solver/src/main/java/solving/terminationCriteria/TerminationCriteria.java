package solving.terminationCriteria;

/**
 * Created by Aldar on 16-Oct-17.
 */
public abstract class TerminationCriteria
{

    /**
     * Must be called once in the beginning of the solving
     */
    public abstract void initialize();


    /**
     * Can be called during the iteration to checkout whether reinitialization should be done at this iteration
     * @return
     */
    public abstract boolean needReinitialize();


    /**
     * Must be called once in the end of every iteration as the loop condition
     * @return
     */
    public abstract boolean isFulfilled();



    /**
     * Multiplier that is applied for computing mutation strength in BWAS
     * @return
     */
    public abstract double mutationBwasFactor();
}
