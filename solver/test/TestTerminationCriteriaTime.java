import org.junit.Test;
import solving.terminationCriteria.TerminationCriteria;
import solving.terminationCriteria.TerminationCriteriaTime;


/**
 * Cannot assert anything time-related
 * This test case is designed for manual debug test session
 * Created by Aldar on 12-Dec-17.
 */
public class TestTerminationCriteriaTime
{
    @Test
    public void testSolutionVrpIterator()
    {
        TerminationCriteria timer = new TerminationCriteriaTime(10000, 5000);

        timer.initialize();

        int a = 1; // stub-line for debugging, put a break-point here

        for (int i = 1; i <= 5; i++)
        {
            boolean needReinitialize = timer.needReinitialize();
            boolean needTerminate = timer.isFulfilled();
            long millisecondsSinceLastReinitialize = timer.timeAfterLastReinitialization();
            int b = 1;   // stub-line for debugging, put a break-point here
        }
    }
}
