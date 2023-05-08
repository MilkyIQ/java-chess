package tools;
import java.awt.event.*;
import java.awt.*;

public class Mouse extends MouseAdapter{
    @Override
    public void mouseClicked(MouseEvent e) {
        Frame.panel.setBackground(Color.GREEN);
        Frame.panel.repaint();
    }
    public void mousePressed(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {

    }
}
