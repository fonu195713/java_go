package go.gui;

import go.gui.Frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class Background extends JPanel {
    private Frame _frame;

    public Background(Frame frame, int size) {
        _frame = frame;
        this.setSize(size, size);
        this.setBackground(Color.orange);
        this.setOpaque(true);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = getIndex(e.getX());
                int y = getIndex(e.getY());
                _frame._game.attemptPut(x, y);
            }

            private int getIndex(int coordinate) {
                double step = size / 19.0;
                double point = step / 2;

                for(int index = 1; index <= 19; index++) {
                    if(Math.abs(coordinate - point) <= step / 2) {
                        return index;
                    }
                    point += step;
                }
                return 19;
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        this.drawLines(g);
        this.drawStars(g);
    }

    private void drawLines(Graphics g) {
        double cell = this.getWidth() / 19.0;
        double start = cell / 2;

        for(int i = 1; i <= 19; i++) {
            int startX = (int)(start);
            int startY = (int)(start + cell * (i-1));
            int endX = (int)(start + cell * 18);
            int endY = (int)(start + cell * (i-1));

            g.drawLine(startX, startY, endX, endY);
        }

        for(int i = 1; i <= 19; i++) {
            int startX = (int)(start + cell * (i-1));
            int startY = (int)(start);
            int endX = (int)(start + cell * (i-1));
            int endY = (int)(start + cell * 18);

            g.drawLine(startX, startY, endX, endY);
        }
    }

    private void drawStars(Graphics g) {
        double cell = this.getWidth() / 19.0;
        double start = cell / 2;

        int[] star = new int[] {4, 10, 16};
        for(int i : star) {
            for(int j : star) {
                int startX = (int)(start + cell * (i-1)) - 3;
                int startY = (int)(start + cell * (j-1)) - 3;
                g.drawOval(startX, startY, 6, 6);
                g.fillOval(startX, startY, 6, 6);
            }
        }
    }
}