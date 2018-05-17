import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Aldar on 18-May-18.
 */
public class ParserSolution
{
    public List<List<Integer>> getSolutionNodes(File file) throws FileNotFoundException
    {
        String text = new Scanner(file).useDelimiter("\\Z").next();

        String[] tourTexts = text.split("(\\{|\\}|\\s)+");

        List<List<Integer>> result = new ArrayList<List<Integer>>();

        for (int i = 3; i < tourTexts.length; i += 3)
        {
            List<Integer> newTour = new ArrayList<Integer>();

            String tour = tourTexts[i];

            String[] nodeText = tour.split("(\\s|-)+");

            for (int j = 0; j < nodeText.length; j++)
                newTour.add(Integer.parseInt(nodeText[j]));

            result.add(newTour);
        }

        return result;
    }
}
