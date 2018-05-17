import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Aldar on 17-May-18.
 */
public class DrawPanel extends JPanel
{
    protected int size;

    protected boolean set = false;
    protected double[] x, y;

    protected double scaleFactor;
    protected double startX, startY;


    public DrawPanel(int size)
    {
        this.size = size;
    }

    public void setNodes(double[] x, double[] y)
    {
        this.x = x;
        this.y = y;
    }

    public void drawing()
    {
        set = true;
        repaint();
    }

    private Random random = new Random();

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (set)
        {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, size, size);

            g.setColor(Color.BLACK);
            g.drawRect(0, 0, size, size);

            double minX, maxX, minY, maxY;

            minX = x[0];
            maxX = x[0];

            for (int i = 1; i < x.length; i++)
            {
                if (minX > x[i])
                    minX = x[i];

                if (maxX < x[i])
                    maxX = x[i];
            }

            minY = y[0];
            maxY = y[0];

            for (int i = 1; i < y.length; i++)
            {
                if (minY > y[i])
                    minY = y[i];

                if (maxY < y[i])
                    maxY = y[i];
            }

            double middleX = (maxX + minX) / 2.0;
            double middleY = (maxY + minY) / 2.0;
            double areaSize = Math.max(maxX - minX, maxY - minY);

            startX = middleX - areaSize / 2.0;
            startY = middleY - areaSize / 2.0;
            scaleFactor = size / areaSize;

            for (int i = 0; i < x.length; i++)
                g.fillOval(scaleX(x[i]), scaleY(y[i]), 5, 5);
        }
    }


    protected int scaleX(double x)
    {
        return (int) ((x - startX) * scaleFactor);
    }

    protected int scaleY(double y)
    {
        return (int) ((y - startY) * scaleFactor);
    }
}
