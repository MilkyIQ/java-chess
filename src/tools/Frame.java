package tools;

import javax.swing.JFrame;
public class Frame extends JFrame{
    public static Frame frame = new Frame();
    public static Panel panel = new Panel();
    public static Mouse mouse = new Mouse();
    public Frame() {
        this.setSize(1000, 500);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        frame.add(panel);
        panel.addMouseListener(mouse);
        frame.setVisible(true);
        panel.repaint();
    }
}
