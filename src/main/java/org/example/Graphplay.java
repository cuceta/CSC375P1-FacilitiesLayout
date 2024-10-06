package org.example;
import javax.swing.*;
import java.awt.*;
public class Graphplay extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = 800;
        int height = 800;
        int gridSize = 50; // Adjust this for grid spacing

        // Draw horizontal lines
        for (int y = gridSize; y <= height+gridSize; y += gridSize) {
            g.drawLine(gridSize, y, width+gridSize, y);
        }

        // Draw vertical lines
        for (int x = gridSize; x <= width+gridSize; x += gridSize) {
            g.drawLine(x, gridSize, x, height+gridSize);
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);

        g2.fillRect(gridSize, gridSize, gridSize,gridSize);
        g2.setColor(Color.PINK);
        g2.fillRect(gridSize,gridSize,gridSize,gridSize*16);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Facilities Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 900);
        frame.add(new Graphplay());
        frame.setVisible(true);
    }
}