public class Position {
    private int x, y;
    //private int d; // represents 4 directions as integer value, 0: (0, -1), 1: (-1, -1), 2: (-1, 0), 3: (1, -1)
    private boolean blank; // indicates that current position is blank,
                           // which means not being occupied by any gomoku stone

    public Position() {
        this(0, 0, false);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.blank = false;
    }

    public Position(int x, int y, boolean b) {
        this.x = x;
        this.y = y;
        this.blank = b;
    }

    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public boolean isBlank() {
        return blank;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %s)", x, y, blank);
    }
}
