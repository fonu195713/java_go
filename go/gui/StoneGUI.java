package go.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class StoneGUI extends JPanel {
    private int _size;
    private boolean _isBlack;

    public StoneGUI(int size, boolean isBlack) {
        _size = size;
        _isBlack = isBlack;

        this.setSize(_size, _size);
        this.setBackground(Color.orange);
        this.setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color color = (_isBlack)? Color.black: Color.white;

        g.setColor(Color.black);
        g.drawOval(0, 0, _size, _size);
        g.setColor(color);
        g.fillOval(0, 0, _size, _size);
    }
}