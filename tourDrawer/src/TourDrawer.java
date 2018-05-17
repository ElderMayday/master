import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.io.File;

/**
 * Created by Aldar on 17-May-18.
 */
public class TourDrawer
{
    private JPanel panel1;
    private JButton button1;
    private JButton openSolutionButton;
    private JButton drawButton;
    private JPanel panel2;
    private DrawPanel panel3;

    public TourDrawer()
    {

        //panel2 = new JPanel();

        panel2.setPreferredSize(new Dimension(1000, 100));

        panel3 = new DrawPanel();

        panel1.add(panel3);



        panel3.setPreferredSize(new Dimension(1000, 500));





        drawButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panel3.drawing();
                panel3.repaint();
                panel3.revalidate();
            }
        });




        button1.addActionListener(new ActionListener()
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
                    //
                }
            }
        });
    }




    public static void main(String[] args)
    {
        JFrame frame = new JFrame("TourDrawer");
        frame.setContentPane(new TourDrawer().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
