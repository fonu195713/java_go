package go.stone;

public class Stone {
    public enum Color {
        WHITE,
        BLACK,
        NONE,
        BORDER,
    }
    
    public Color _color;

    public Stone() {
        this._color = Color.NONE;
    }
}