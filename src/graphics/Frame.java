package graphics;
import javax.swing.JFrame;

import java.awt.GridLayout;

public class Frame extends JFrame
{
    public Frame()
    {
        this.setSize(1000, 500);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout());
    }
}
