package org.example;
import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = 800;
        int height = 800;
        int gridSize = 25; // Adjust this for grid spacing

        // Draw horizontal lines
        for (int y = gridSize; y <= height+gridSize; y += gridSize) {
            g.drawLine(gridSize, y, width+gridSize, y);
        }

        // Draw vertical lines
        for (int x = gridSize; x <= width+gridSize; x += gridSize) {
            g.drawLine(x, gridSize, x, height+gridSize);
        }

        Graphics2D g2 = (Graphics2D) g;

        Floorplan floorplan = FacilityFloorPlan.pickBestFloorPlan();
//        Floorplan floorplan = FacilityFloorPlan.createFloorplan();

        for (Station s : floorplan.getStations()) {
                if (s.getFunction().equals("Small")) {
                    g2.setColor(Color.CYAN);
                } else if (s.getFunction().equals("Big")) {
                    g2.setColor(Color.RED);
                } else if (s.getFunction().equals("Long")) {
                    g2.setColor(Color.orange);
                } else if (s.getFunction().equals("Wide")) {
                    g2.setColor(Color.MAGENTA);
                }
                g2.fillRect(s.getxCoordinate(), s.getyCoordinate(), s.getWidth(), s.getHeight());
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(s.getxCoordinate(), s.getyCoordinate(), s.getWidth(), s.getHeight());

            }
//            System.out.println(floorplan.getFloorPlanAffinity());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Facilities Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 900);
        frame.add(new GUI());
        frame.setVisible(true);
    }
}