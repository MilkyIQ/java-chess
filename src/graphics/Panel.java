package graphics;
import javax.swing.JPanel;
import java.awt.*;

public class Panel extends JPanel
{
    public Panel()
    {
        setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.drawRect(10, 10, 30, 30);
    }
}
