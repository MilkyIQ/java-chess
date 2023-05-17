package graphics;
import javax.swing.JPanel;
import java.awt.*;

public class Panel extends JPanel
{
    public Panel()
    {
        setBackground(Color.BLACK); // this is probably an issue because the name for tools.Color and JFrame color are the same
        setBounds(40,80,200,200);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawRect(250, 100, 300, 300);
        g.fillRect(250, 100, 300, 300);
    }
}
