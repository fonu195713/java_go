package go;

import go.gui.Frame;
import go.rule.Rules;
import go.stone.Stone;
import javax.swing.JFrame;

public class Game {
    public boolean _isBlack;
    public Frame _frame;
    public Stone[][] _board;

    public Game(int size) {
        _frame = new Frame(this, size);
        _isBlack = true;
        _board = this.initBoard();
    }

    private Stone[][] initBoard() {
        Stone[][] ary = new Stone[21][21];
        for(int i = 0; i < ary.length; i++) {
            for(int j = 0; j < ary[i].length; j++) {
                ary[i][j] = new Stone();
            }
        }

        for(int i = 0; i < 21; i++) {
            ary[i][0]._color = Stone.Color.BORDER;
            ary[0][i]._color = Stone.Color.BORDER;
            ary[i][20]._color = Stone.Color.BORDER;
            ary[20][i]._color = Stone.Color.BORDER;
        }
        return ary;
    }

    private int[][] initHand() {
        int[][] ary = new int[21][21];
        for(int i = 0; i < ary.length; i++) {
            for(int j = 0; j < ary[i].length; j++) {
                ary[i][j] = 0;
            }
        }
        return ary;
    }

    public void attemptPut(int x, int y) {
        Rules rules = new Rules(this);

        if(!rules.isEmpty(x, y)) {
            return;
        }

        if(rules.isSuicide(x, y)) {
            return;
        }

        this.setStone(x, y);
    }

    private void setStone(int x, int y) {
        _board[x][y]._color = (_isBlack)? Stone.Color.BLACK: Stone.Color.WHITE;
        _frame.drawStone(x, y);
        _isBlack = !_isBlack;

        // System.out.printf("(%d, %d)\n", x, y);
    }

    public void pickStones(boolean[][] alive, Stone.Color color) {
        int deadIndex = 0;
        for(int i = 0; i < alive.length; i++) {
            for(int j = 0; j < alive[i].length; j++) {
                if(!alive[i][j] && _board[i][j]._color == color) {
                    _frame.eraseStone(i, j);
                    _board[i][j]._color = Stone.Color.NONE;
                }
            }
        }
    }
}