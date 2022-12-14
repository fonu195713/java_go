package go.gui;

import go.Game;
import go.gui.Background;
import go.gui.StoneGUI;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Frame extends JFrame {
    public Game _game;
    private int _size;
    private JLayeredPane _jLayeredPane;
    private Component[][] _components;

    public Frame(Game game, int size) {
        _game = game;
        _size = size;
        _components = new Component[21][21];

        _jLayeredPane = new JLayeredPane();   
        _jLayeredPane.setPreferredSize(new Dimension(_size, _size));

        Background background = new Background(this, _size);
        _jLayeredPane.add(background, Integer.MIN_VALUE);
        this.getContentPane().add(_jLayeredPane);

        this.pack();
        this.setResizable(false);
        this.setTitle("Go Game");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void drawStone(int x, int y) {
        double zoom = 0.75;
        int size = (int)(Math.round(_size / 19.0) * zoom);

        double cell = _size / 19.0;
        int guiX = (int)(Math.round(((x-1) + (0.5-(zoom/2))) * cell));
        int guiY = (int)(Math.round(((y-1) + (0.5-(zoom/2))) * cell));
        
        StoneGUI stone = new StoneGUI(size, _game._isBlack);
        stone.setBounds(guiX, guiY, size, size);

        _jLayeredPane.add(stone, Integer.valueOf(1));
        _components[x][y] = stone;
    }

    public void eraseStone(int x, int y) {
        _jLayeredPane.remove(_components[x][y]);
        _components[x][y] = null;
        this.revalidate();
        this.repaint();
    }
}