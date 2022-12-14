package go.rule;

import go.Game;
import go.stone.Stone;

import java.util.ArrayList;

public class Rules {
    Game _game;

    private class Maze {
        private enum Block {
            EXIT,
            ROAD,
            WALL,
        }

        private Block _maze[][];
        private boolean _alive[][];
        private boolean _visit[][];
        
        public Maze(Stone.Color road) {
            _maze = this.initMaze(road);
            _alive = this.initAlive();
        }

        private Block[][] initMaze(Stone.Color road) {
            Block[][] ary = new Block[21][21];
            for(int i = 0; i < ary.length; i++) {
                for(int j = 0; j < ary[i].length; j++) {
                    if(_game._board[i][j]._color == Stone.Color.NONE)
                        ary[i][j] = Block.EXIT;
                    else if(_game._board[i][j]._color == road)
                        ary[i][j] = Block.ROAD;
                    else
                        ary[i][j] = Block.WALL;
                }
            }
            return ary;
        }

        private boolean[][] initAlive() {
            boolean ary[][] = new boolean[21][21];
            for(int i = 0; i < ary.length; i++) {
                for(int j = 0; j < ary[i].length; j++) {
                    ary[i][j] = false;
                }
            }
            return ary;
        }

        public void walking() {
            for(int i = 0; i < _maze.length; i++) {
                for(int j = 0; j < _maze[i].length; j++) {
                    _visit = this.initVisit();
                    _alive[i][j] = this.exitFinding(i, j);
                }
            }
        }

        private boolean[][] initVisit() {
            boolean ary[][] = new boolean[21][21];
            for(int i = 0; i < ary.length; i++) {
                for(int j = 0; j < ary[i].length; j++) {
                    ary[i][j] = false;
                }
            }
            return ary;
        }

        private boolean exitFinding(int x, int y) {
            if(_visit[x][y]) {
                return _alive[x][y];
            }

            _visit[x][y] = true;

            if(_maze[x][y] == Block.WALL) {
                return false;
            }

            if(_maze[x][y] == Block.EXIT) {
                return true;
            }

            boolean left   = this.exitFinding(x-1, y);
            boolean right  = this.exitFinding(x+1, y);
            boolean top    = this.exitFinding(x, y-1);
            boolean bottom = this.exitFinding(x, y+1);

            return (left || right || top || bottom);
        }
    }

    public Rules(Game game) {
        _game = game;
    }

    public boolean isEmpty(int x, int y) {
        boolean isEmpty = true;
        if(_game._board[x][y]._color != Stone.Color.NONE) {
            isEmpty = false;
        }
        return isEmpty;
    }

    public boolean isSuicide(int x, int y) {
        Stone.Color nowColor = (_game._isBlack)? Stone.Color.BLACK: Stone.Color.WHITE;
        Stone.Color opponent = (nowColor == Stone.Color.BLACK)? Stone.Color.WHITE: Stone.Color.BLACK;

        _game._board[x][y]._color = nowColor;

        Maze maze1 = new Maze(opponent);
        maze1.walking();
        _game.pickStones(maze1._alive, opponent);
        
        Maze maze2 = new Maze(nowColor);
        maze2.walking();
        
        if(maze2._alive[x][y] == false) {
            _game._board[x][y]._color = Stone.Color.NONE;
            return true;
        }

        return false;
    }
}