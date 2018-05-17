import javax.swing.*;
import java.awt.*;

/**
 * Created by Aldar on 17-May-18.
 */
public class DrawPanel extends JPanel
{
    public void drawing()
    {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawOval(10,10,1000,1000);

        int a = 1;
    }
}
