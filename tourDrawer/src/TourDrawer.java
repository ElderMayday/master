import problem.componentStructure.ComponentStructure2dStandard;
import problem.fleet.FleetDescendingCapacity;
import problem.problemFormulation.Problem;
import problem.problemFormulation.ProblemVRP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Aldar on 17-May-18.
 */
public class TourDrawer
{
    private JPanel mainPanel;
    private JButton openInstanceButton;
    private JButton openSolutionButton;
    private JButton drawButton;
    private JPanel buttonPanel;
    private DrawPanel drawPanel;

    public static final int size = 700;

    public TourDrawer()
    {
        drawPanel = new DrawPanel(size);
        mainPanel.add(drawPanel);
        drawPanel.setPreferredSize(new Dimension(size, size));
        buttonPanel.setPreferredSize(new Dimension(size,100));
        mainPanel.setPreferredSize(new Dimension(size, size + 100));

        drawButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                drawPanel.drawing();
            }
        });


        openInstanceButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("irace\\test\\"));
                int ret = fileChooser.showDialog(null, "Open problem instance file");

                if (ret == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();

                    try
                    {
                        ProblemVRP problem = new ProblemVRP(new ComponentStructure2dStandard(), new FleetDescendingCapacity(), null);
                        problem.load(file);
                        drawPanel.setNodes(problem.x, problem.y);
                    } catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });


        openSolutionButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("solver\\output\\"));
                int ret = fileChooser.showDialog(null, "Open solution file");

                if (ret == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();

                    ParserSolution parserSolution = new ParserSolution();
                    try
                    {
                        List<List<Integer>> nodes = parserSolution.getSolutionNodes(new File("solver\\output\\out-CMT1.vrp.txt"));
                        drawPanel.setTours(nodes);
                    }
                    catch (FileNotFoundException exc)
                    {
                    }



                }
            }
        });
    }






    public static void main(String[] args)
    {
        JFrame frame = new JFrame("TourDrawer");
        frame.setContentPane(new TourDrawer().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
