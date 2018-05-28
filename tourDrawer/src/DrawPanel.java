import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

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

    protected List<List<Integer>> nodes;


    public DrawPanel(int size)
    {
        this.size = size;
    }

    public void setNodes(double[] x, double[] y)
    {
        set = false;
        this.x = x;
        this.y = y;
    }

    public void setTours(List<List<Integer>> nodes)
    {
        set = false;
        this.nodes = nodes;
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

            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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

            if (nodes != null)
            {
                for (List<Integer> list : nodes)
                {
                    int red = random.nextInt(256);
                    int green = random.nextInt(256);
                    int blue = random.nextInt(256);
                    Color randomColour = new Color(red,green,blue);
                    g.setColor(randomColour);

                    for (int i = 0; i < list.size() - 1; i++)
                    {
                        int index1 = list.get(i);
                        int index2 = list.get(i + 1);

                        g2.drawLine(scaleX(x[index1]), scaleY(y[index1]), scaleX(x[index2]), scaleY(y[index2]));
                    }
                }
            }

            g.setColor(Color.BLUE);
            g.fillOval(scaleX(x[0]) - 5, scaleY(y[0]) - 5, 10, 10);

            for (int i = 1; i < x.length; i++)
            {
                int xPoint = scaleX(x[i]) - 5;
                int yPoint = scaleY(y[i]) - 5;
                g.setColor(Color.RED);
                g.fillOval(xPoint, yPoint, 10, 10);
                g.setColor(Color.BLACK);
                g2.drawString(Integer.toString(i), xPoint, yPoint - 5);
            }


        }
        else
        {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, size, size);
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
